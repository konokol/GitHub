package com.pankoku.ghstorage.mmkv

import android.content.Context
import com.google.gson.Gson
import com.pankoku.ghstorage.EncryptionType
import com.pankoku.ghstorage.KVStorage
import com.pankoku.ghstorage.KVStorageException
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * MMKV 实现的 KVStorage
 *
 * 基于 MMKV 高性能键值存储，提供快速的读写操作
 * 支持多种数据类型和加密存储
 */
class MMKVStorage(
    context: Context,
    private val name: String,
    encryption: EncryptionType = EncryptionType.NONE,
    multiProcess: Boolean = false
) : KVStorage {

    private val mmkv: MMKV
    private val gson = Gson()
    private val keyChangeFlows = mutableMapOf<String, MutableStateFlow<String?>>()

    init {
        val config = MMKVConfig().apply {
            mode = if (multiProcess) MMKV.MULTI_PROCESS_MODE else MMKV.SINGLE_PROCESS_MODE
            when (encryption) {
                EncryptionType.NONE -> Unit
                EncryptionType.AES_256_CFB -> {
                    cryptKey = MMKVCryptKeyProvider.getOrCreate(context)
                    // 不显式置 true 时 MMKV 会把密钥截断到 16 字节，退化为 AES-128
                    aes256 = true
                }
                EncryptionType.CUSTOM -> throw KVStorageException.UnsupportedOperationException(
                    "EncryptionType.CUSTOM has no key provider hook yet"
                )
            }
        }

        mmkv = try {
            MMKV.mmkvWithID(name, config) ?: throw RuntimeException("mmkvWithID returned null")
        } catch (e: Exception) {
            // 不能回退到 defaultMMKV()，那会把本应加密隔离的数据写入共享明文存储
            throw KVStorageException.StorageException("Failed to open MMKV instance: $name", e)
        }
    }

    override suspend fun put(key: String, value: Any): Result<Unit> {
        return try {
            when (value) {
                is String -> mmkv.encode(key, value)
                is Int -> mmkv.encode(key, value)
                is Long -> mmkv.encode(key, value)
                is Float -> mmkv.encode(key, value)
                is Double -> mmkv.encode(key, value)
                is Boolean -> mmkv.encode(key, value)
                is ByteArray -> mmkv.encode(key, value)
                else -> {
                    // 复杂对象使用 JSON 序列化
                    val jsonString = gson.toJson(value)
                    mmkv.encode("${key}_json", jsonString)
                }
            }
            
            // 通知监听器
            keyChangeFlows[key]?.value = value.toString()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to put value for key: $key", e))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> get(key: String, clazz: Class<T>): Result<T?> {
        return try {
            val result = when (clazz) {
                String::class.java -> mmkv.decodeString(key)
                Int::class.java, Int::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeInt(key) else null
                Long::class.java, Long::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeLong(key) else null
                Float::class.java, Float::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeFloat(key) else null
                Double::class.java, Double::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeDouble(key) else null
                Boolean::class.java, Boolean::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeBool(key) else null
                ByteArray::class.java -> mmkv.decodeBytes(key)
                else -> {
                    // 尝试从 JSON 字符串反序列化
                    mmkv.decodeString("${key}_json")?.let { jsonString ->
                        gson.fromJson(jsonString, clazz)
                    }
                }
            } as T?
            
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(KVStorageException.SerializationException("Failed to get value for key: $key", e))
        }
    }

    override suspend fun remove(key: String): Result<Unit> {
        return try {
            mmkv.remove(key)
            mmkv.remove("${key}_json")
            
            // 通知监听器
            keyChangeFlows[key]?.value = null
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to remove key: $key", e))
        }
    }

    override suspend fun clear(): Result<Unit> {
        return try {
            mmkv.clearAll()
            
            // 通知所有监听器
            keyChangeFlows.values.forEach { it.value = null }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to clear storage", e))
        }
    }

    override suspend fun contains(key: String): Result<Boolean> {
        return try {
            val exists = mmkv.containsKey(key) || mmkv.containsKey("${key}_json")
            Result.success(exists)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to check key existence: $key", e))
        }
    }

    override suspend fun allKeys(): Result<List<String>> {
        return try {
            val allKeys = mmkv.allKeys().orEmpty().mapNotNull { key ->
                when {
                    key.endsWith("_json") -> key.removeSuffix("_json")
                    else -> key
                }
            }.distinct()
            
            Result.success(allKeys)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to get all keys", e))
        }
    }

    override fun <T> watch(key: String, clazz: Class<T>): Flow<T?> {
        if (!keyChangeFlows.containsKey(key)) {
            // 初始化当前值
            val currentValue = when (clazz) {
                String::class.java -> mmkv.decodeString(key)
                Int::class.java, Int::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeInt(key).toString() else null
                Long::class.java, Long::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeLong(key).toString() else null
                Float::class.java, Float::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeFloat(key).toString() else null
                Double::class.java, Double::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeDouble(key).toString() else null
                Boolean::class.java, Boolean::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeBool(key).toString() else null
                else -> mmkv.decodeString("${key}_json")
            }
            keyChangeFlows[key] = MutableStateFlow(currentValue)
        }

        return keyChangeFlows[key]!!.map { stringValue ->
            try {
                @Suppress("UNCHECKED_CAST")
                when (clazz) {
                    String::class.java -> stringValue as T?
                    Int::class.java, Int::class.javaPrimitiveType -> stringValue?.toIntOrNull() as T?
                    Long::class.java, Long::class.javaPrimitiveType -> stringValue?.toLongOrNull() as T?
                    Float::class.java, Float::class.javaPrimitiveType -> stringValue?.toFloatOrNull() as T?
                    Double::class.java, Double::class.javaPrimitiveType -> stringValue?.toDoubleOrNull() as T?
                    Boolean::class.java, Boolean::class.javaPrimitiveType -> stringValue?.toBoolean() as T?
                    else -> {
                        stringValue?.let { gson.fromJson(it, clazz) }
                    }
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun size(): Result<Long> {
        return try {
            val size = mmkv.totalSize().toLong()
            Result.success(size)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to get storage size", e))
        }
    }
}
