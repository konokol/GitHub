package com.github.app

import android.content.Context
import com.pancoku.vault.EncryptionType
import com.pancoku.vault.KVStorage
import com.pancoku.vault.KVStorageFactory
import com.pancoku.vault.datastore.DataStoreFactory
import com.pancoku.vault.mmkv.MMKVFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GHStorage
 *
 * 提供统一的 GHStorage 使用接口，简化 KV 存储操作
 */
@Singleton
class GHStorage @Inject constructor(
    private val context: Context
) {

    private val storageCache = HashMap<String, KVStorage>()

    fun getStorage(name: String): KVStorage {
        if (!storageCache.containsKey(name)) {
            val factory: KVStorageFactory = DataStoreFactory(context)
            val storage = factory.create(name)
            storageCache[name] = storage
        }
        return storageCache[name]!!
    }

    fun getDefaultStorage(): KVStorage = getStorage("default")
    fun getUserSettingsStorage(): KVStorage = getStorage("user_settings")
    fun getCacheStorage(): KVStorage = getStorage("cache")

    fun getEncryptedStorage(name: String, encryption: EncryptionType): KVStorage {
        val factory: KVStorageFactory = MMKVFactory(context)
        val cacheKey = "${name}_${encryption}"

        if (!storageCache.containsKey(cacheKey)) {
            val storage = factory.create(name, encryption)
            storageCache[cacheKey] = storage
        }
        return storageCache[cacheKey]!!
    }

    fun getSecureStorage(): KVStorage =
        getEncryptedStorage("secure", EncryptionType.AES_256_CFB)

    fun clearCache() {
        storageCache.clear()
    }
}