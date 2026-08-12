package com.pankoku.ghstorage

import kotlinx.coroutines.flow.Flow

/**
 * KV 存储接口，提供统一的键值存储抽象
 *
 * 支持多种数据类型的存储和查询，包括基本类型和复杂对象
 * 所有操作都是异步的，基于 Kotlin Coroutines
 */
interface KVStorage {

    /**
     * 存储数据
     *
     * @param key 键，不能为空
     * @param value 值，支持 Any 类型（会被序列化）
     * @return Result 表示操作成功或失败
     */
    suspend fun put(key: String, value: Any): Result<Unit>

    /**
     * 获取数据
     *
     * @param key 键
     * @param clazz 目标类型的 Class 对象
     * @return Result 包含获取的值，如果不存在则为 null
     */
    suspend fun <T> get(key: String, clazz: Class<T>): Result<T?>

    /**
     * 删除指定键的数据
     *
     * @param key 要删除的键
     * @return Result 表示操作成功或失败
     */
    suspend fun remove(key: String): Result<Unit>

    /**
     * 清空所有数据
     *
     * @return Result 表示操作成功或失败
     */
    suspend fun clear(): Result<Unit>

    /**
     * 检查键是否存在
     *
     * @param key 要检查的键
     * @return Result 包含布尔值，表示键是否存在
     */
    suspend fun contains(key: String): Result<Boolean>

    /**
     * 获取所有键
     *
     * @return Result 包含所有键的列表
     */
    suspend fun allKeys(): Result<List<String>>

    /**
     * 监听指定键的变化
     *
     * @param key 要监听的键
     * @return Flow 发送键值变化，初始时会发送当前值
     */
    fun <T> watch(key: String, clazz: Class<T>): Flow<T?>

    /**
     * 获取存储大小（字节数）
     *
     * @return Result 包含存储大小，如果不支持则返回 -1
     */
    suspend fun size(): Result<Long>
}