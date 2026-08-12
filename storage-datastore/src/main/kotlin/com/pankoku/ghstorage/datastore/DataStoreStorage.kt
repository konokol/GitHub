package com.pankoku.ghstorage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.pankoku.ghstorage.KVStorage
import com.pankoku.ghstorage.KVStorageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * DataStore 实现的 KVStorage
 *
 * 基于 Android DataStore Preferences API，提供高效的键值存储
 * 支持基本类型和复杂对象的序列化存储
 */
class DataStoreStorage(
    private val context: Context,
    private val name: String
) : KVStorage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name)
    private val gson = Gson()

    override suspend fun put(key: String, value: Any): Result<Unit> {
        return try {
            context.dataStore.edit { preferences ->
                when (value) {
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Int -> preferences[intPreferencesKey(key)] = value
                    is Long -> preferences[longPreferencesKey(key)] = value
                    is Float -> preferences[floatPreferencesKey(key)] = value
                    is Double -> preferences[doublePreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    else -> {
                        // 复杂对象使用 JSON 序列化
                        preferences[stringPreferencesKey("${key}_json")] = gson.toJson(value)
                    }
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to put value for key: $key", e))
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> get(key: String, clazz: Class<T>): Result<T?> {
        return try {
            val preferences = context.dataStore.data.first()
            
            val result = when (clazz) {
                String::class.java -> preferences[stringPreferencesKey(key)]
                Int::class.java, Int::class.javaPrimitiveType -> preferences[intPreferencesKey(key)]
                Long::class.java, Long::class.javaPrimitiveType -> preferences[longPreferencesKey(key)]
                Float::class.java, Float::class.javaPrimitiveType -> preferences[floatPreferencesKey(key)]
                Double::class.java, Double::class.javaPrimitiveType -> preferences[doublePreferencesKey(key)]
                Boolean::class.java, Boolean::class.javaPrimitiveType -> preferences[booleanPreferencesKey(key)]
                else -> {
                    // 尝试从 JSON 字符串反序列化
                    preferences[stringPreferencesKey("${key}_json")]?.let { jsonString ->
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
            context.dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(key))
                preferences.remove(intPreferencesKey(key))
                preferences.remove(longPreferencesKey(key))
                preferences.remove(floatPreferencesKey(key))
                preferences.remove(doublePreferencesKey(key))
                preferences.remove(booleanPreferencesKey(key))
                preferences.remove(stringPreferencesKey("${key}_json"))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to remove key: $key", e))
        }
    }

    override suspend fun clear(): Result<Unit> {
        return try {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to clear storage", e))
        }
    }

    override suspend fun contains(key: String): Result<Boolean> {
        return try {
            val preferences = context.dataStore.data.first()
            val exists = preferences.contains(stringPreferencesKey(key)) ||
                         preferences.contains(intPreferencesKey(key)) ||
                         preferences.contains(longPreferencesKey(key)) ||
                         preferences.contains(floatPreferencesKey(key)) ||
                         preferences.contains(doublePreferencesKey(key)) ||
                         preferences.contains(booleanPreferencesKey(key)) ||
                         preferences.contains(stringPreferencesKey("${key}_json"))
            Result.success(exists)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to check key existence: $key", e))
        }
    }

    override suspend fun allKeys(): Result<List<String>> {
        return try {
            val preferences = context.dataStore.data.first()
            val keys = preferences.asMap().keys.mapNotNull { key ->
                key.name.let { name ->
                    when {
                        name.endsWith("_json") -> name.removeSuffix("_json")
                        else -> name
                    }
                }
            }.distinct()
            
            Result.success(keys)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to get all keys", e))
        }
    }

    override fun <T> watch(key: String, clazz: Class<T>): Flow<T?> {
        return context.dataStore.data.map { preferences ->
            try {
                @Suppress("UNCHECKED_CAST")
                when (clazz) {
                    String::class.java -> preferences[stringPreferencesKey(key)]
                    Int::class.java, Int::class.javaPrimitiveType -> preferences[intPreferencesKey(key)]
                    Long::class.java, Long::class.javaPrimitiveType -> preferences[longPreferencesKey(key)]
                    Float::class.java, Float::class.javaPrimitiveType -> preferences[floatPreferencesKey(key)]
                    Double::class.java, Double::class.javaPrimitiveType -> preferences[doublePreferencesKey(key)]
                    Boolean::class.java, Boolean::class.javaPrimitiveType -> preferences[booleanPreferencesKey(key)]
                    else -> {
                        preferences[stringPreferencesKey("${key}_json")]?.let { jsonString ->
                            gson.fromJson(jsonString, clazz)
                        }
                    }
                } as T?
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun size(): Result<Long> {
        return try {
            val preferences = context.dataStore.data.first()
            // DataStore Preferences API 不直接提供文件大小，返回 -1
            Result.success(-1L)
        } catch (e: Exception) {
            Result.failure(KVStorageException.UnsupportedOperationException("Size calculation not supported in DataStore", e))
        }
    }
}