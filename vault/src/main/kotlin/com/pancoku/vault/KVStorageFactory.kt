package com.pancoku.vault

interface KVStorageFactory {
    fun create(name: String): KVStorage
    fun create(name: String, encryption: EncryptionType): KVStorage
}

enum class EncryptionType {
    NONE,
    AES_256_CFB,
    CUSTOM
}