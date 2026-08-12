package com.pankoku.ghstorage

/**
 * KV 存储工厂接口，用于创建 KVStorage 实例
 *
 * 通过工厂模式支持不同的存储实现，便于扩展和切换
 */
interface KVStorageFactory {

    /**
     * 创建存储实例
     *
     * @param name 存储实例名称，用于区分不同的存储空间
     * @return KVStorage 实例
     */
    fun create(name: String): KVStorage

    /**
     * 创建存储实例并指定加密方式
     *
     * @param name 存储实例名称
     * @param encryption 加密类型
     * @return KVStorage 实例
     */
    fun create(name: String, encryption: EncryptionType): KVStorage
}

/**
 * 加密类型枚举
 *
 * 定义支持的加密方式
 */
enum class EncryptionType {
    /**
     * 无加密
     */
    NONE,

    /**
     * AES-256-GCM 加密
     */
    AES_256_GCM,

    /**
     * 自定义加密
     */
    CUSTOM
}