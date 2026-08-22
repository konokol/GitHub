package com.github.app

import android.content.Context
import com.pankoku.ghstorage.EncryptionType
import com.pankoku.ghstorage.KVStorage
import com.pankoku.ghstorage.KVStorageFactory
import com.pankoku.ghstorage.datastore.DataStoreFactory
import com.pankoku.ghstorage.mmkv.MMKVFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GHStorage
 *
 * 提供统一的 GHStorage 使用接口，简化 KV 存储操作
 *
 * @author GHStorage Team
 * @version 1.0
 */
@Singleton
class GHStorage @Inject constructor(
    private val context: Context
) {

    private val storageCache = HashMap<String, KVStorage>()

    /**
     * 获取指定名称的存储实例
     *
     * @param name 存储名称
     * @return KVStorage 实例
     */
    fun getStorage(name: String): KVStorage {
        if (!storageCache.containsKey(name)) {
            // 创建 DataStore 工厂
            val factory: KVStorageFactory = DataStoreFactory(context)
            val storage = factory.create(name)
            storageCache[name] = storage
        }
        return storageCache[name]!!
    }

    /**
     * 获取默认存储实例
     *
     * @return 默认 KVStorage 实例
     */
    fun getDefaultStorage(): KVStorage = getStorage("default")

    /**
     * 获取用户设置存储实例
     *
     * @return 用户设置 KVStorage 实例
     */
    fun getUserSettingsStorage(): KVStorage = getStorage("user_settings")

    /**
     * 获取缓存存储实例
     *
     * @return 缓存 KVStorage 实例
     */
    fun getCacheStorage(): KVStorage = getStorage("cache")

    /**
     * 获取加密存储实例（使用 MMKV）
     *
     * @param name 存储名称
     * @param encryption 加密类型
     * @return 加密的 KVStorage 实例
     */
    fun getEncryptedStorage(name: String, encryption: EncryptionType): KVStorage {
        // 创建 MMKV 工厂用于加密存储
        val factory: KVStorageFactory = MMKVFactory(context)
        val cacheKey = "${name}_${encryption}"

        if (!storageCache.containsKey(cacheKey)) {
            val storage = factory.create(name, encryption)
            storageCache[cacheKey] = storage
        }
        return storageCache[cacheKey]!!
    }

    /**
     * 获取安全存储实例
     *
     * @return 安全 KVStorage 实例（加密）
     */
    fun getSecureStorage(): KVStorage =
        getEncryptedStorage("secure", EncryptionType.AES_256_CFB)

    /**
     * 清空所有缓存的存储实例
     */
    fun clearCache() {
        storageCache.clear()
    }
}