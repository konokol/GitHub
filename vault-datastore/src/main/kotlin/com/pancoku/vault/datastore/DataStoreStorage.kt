package com.pancoku.vault.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import com.google.gson.Gson
import com.pancoku.vault.KVStorage
import com.pancoku.vault.KVStorageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreStorage(
    context: Context,
    private val name: String
) : KVStorage {

    private val dataStore = VaultDataStoreManager.get(context, name)
    private val gson = Gson()

    override suspend fun put(key: String, value: Any): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                when (value) {
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Int -> preferences[intPreferencesKey(key)] = value
                    is Long -> preferences[longPreferencesKey(key)] = value
                    is Float -> preferences[floatPreferencesKey(key)] = value
                    is Double -> preferences[doublePreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    else -> preferences[stringPreferencesKey("${key}_json")] = gson.toJson(value)
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
            val preferences = dataStore.data.first()
            val result = when (clazz) {
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
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(KVStorageException.SerializationException("Failed to get value for key: $key", e))
        }
    }

    override suspend fun remove(key: String): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
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
            dataStore.edit { preferences -> preferences.clear() }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to clear storage", e))
        }
    }

    override suspend fun contains(key: String): Result<Boolean> {
        return try {
            val preferences = dataStore.data.first()
            val exists = preferences.contains(stringPreferencesKey(key)) ||
                         preferences.contains(stringPreferencesKey("${key}_json"))
            Result.success(exists)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to check key existence: $key", e))
        }
    }

    override suspend fun allKeys(): Result<List<String>> {
        return try {
            val preferences = dataStore.data.first()
            val keys = preferences.asMap().keys.map { key ->
                key.name.removeSuffix("_json")
            }.distinct()
            Result.success(keys)
        } catch (e: Exception) {
            Result.failure(KVStorageException.StorageException("Failed to get all keys", e))
        }
    }

    override fun <T> watch(key: String, clazz: Class<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            try {
                @Suppress("UNCHECKED_CAST")
                when (clazz) {
                    String::class.java -> preferences[stringPreferencesKey(key)]
                    Int::class.java, Int::class.javaPrimitiveType -> preferences[intPreferencesKey(key)]
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

    override suspend fun size(): Result<Long> = Result.success(-1L)
}
