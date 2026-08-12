package com.pankoku.ghstorage

/**
 * KV 存储异常基类
 *
 * 所有存储相关的异常都继承自此类，提供统一的错误处理机制
 */
sealed class KVStorageException(message: String, cause: Throwable? = null) : Exception(message, cause) {

    /**
     * 序列化异常
     *
     * 在数据序列化或反序列化过程中发生错误时抛出
     */
    class SerializationException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)

    /**
     * 加密异常
     *
     * 在数据加密或解密过程中发生错误时抛出
     */
    class EncryptionException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)

    /**
     * 存储异常
     *
     * 在底层存储操作发生错误时抛出
     */
    class StorageException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)

    /**
     * 参数异常
     *
     * 在参数验证失败时抛出（如空键、无效类型等）
     */
    class IllegalArgumentException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)

    /**
     * 不支持的异常
     *
     * 当尝试执行不支持的操作时抛出
     */
    class UnsupportedOperationException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)
}