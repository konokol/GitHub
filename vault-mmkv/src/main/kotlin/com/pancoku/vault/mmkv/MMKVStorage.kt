package com.pancoku.vault.mmkv

import android.content.Context
import com.google.gson.Gson
import com.pancoku.vault.EncryptionType
import com.pancoku.vault.KVStorage
import com.pancoku.vault.KVStorageException
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

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
                    aes256 = true
                }
                EncryptionType.CUSTOM -> throw KVStorageException.UnsupportedOperationException("CUSTOM encryption not implemented")
            }
        }
        mmkv = MMKV.mmkvWithID(name, config) ?: throw KVStorageException.StorageException("Failed to open MMKV")
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
                else -> mmkv.encode("${key}_json", gson.toJson(value))
            }
            keyChangeFlows[key]?.value = value.toString()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Put failed", e))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> get(key: String, clazz: Class<T>): Result<T?> {
        return try {
            val result = when (clazz) {
                String::class.java -> mmkv.decodeString(key)
                Int::class.java, Int::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeInt(key) else null
                Long::class.java, Long::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeLong(key) else null
                Boolean::class.java, Boolean::class.javaPrimitiveType -> if (mmkv.containsKey(key)) mmkv.decodeBool(key) else null
                else -> mmkv.decodeString("${key}_json")?.let { gson.fromJson(it, clazz) }
            } as T?
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(KVStorageException.SerializationException("Get failed", e))
        }
    }

    override suspend fun remove(key: String): Result<Unit> {
        mmkv.remove(key)
        mmkv.remove("${key}_json")
        keyChangeFlows[key]?.value = null
        return Result.success(Unit)
    }

    override suspend fun clear(): Result<Unit> {
        mmkv.clearAll()
        keyChangeFlows.values.forEach { it.value = null }
        return Result.success(Unit)
    }

    override suspend fun contains(key: String): Result<Boolean> =
        Result.success(mmkv.containsKey(key) || mmkv.containsKey("${key}_json"))

    override suspend fun allKeys(): Result<List<String>> =
        Result.success(mmkv.allKeys()?.map { it.removeSuffix("_json") }?.distinct() ?: emptyList())

    override fun <T> watch(key: String, clazz: Class<T>): Flow<T?> {
        if (!keyChangeFlows.containsKey(key)) {
            keyChangeFlows[key] = MutableStateFlow(null) // Simplified for refactoring
        }
        return keyChangeFlows[key]!!.map { null } // Logic will be restored after package fix
    }

    override suspend fun size(): Result<Long> = Result.success(mmkv.totalSize())
}