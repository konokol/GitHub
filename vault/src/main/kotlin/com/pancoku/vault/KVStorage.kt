package com.pancoku.vault

import kotlinx.coroutines.flow.Flow

/**
 * Vault 存储接口，提供统一的键值存储抽象
 */
interface KVStorage {
    suspend fun put(key: String, value: Any): Result<Unit>
    suspend fun <T> get(key: String, clazz: Class<T>): Result<T?>
    suspend fun remove(key: String): Result<Unit>
    suspend fun clear(): Result<Unit>
    suspend fun contains(key: String): Result<Boolean>
    suspend fun allKeys(): Result<List<String>>
    fun <T> watch(key: String, clazz: Class<T>): Flow<T?>
    suspend fun size(): Result<Long>
}