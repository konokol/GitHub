package com.pancoku.vault

sealed class KVStorageException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class SerializationException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)
    class EncryptionException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)
    class StorageException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)
    class IllegalArgumentException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)
    class UnsupportedOperationException(message: String, cause: Throwable? = null) : KVStorageException(message, cause)
}