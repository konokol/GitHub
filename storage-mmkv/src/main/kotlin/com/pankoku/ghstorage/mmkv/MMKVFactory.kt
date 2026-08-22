package com.pankoku.ghstorage.mmkv

import android.content.Context
import com.pankoku.ghstorage.EncryptionType
import com.pankoku.ghstorage.KVStorage
import com.pankoku.ghstorage.KVStorageFactory
import com.tencent.mmkv.MMKV

/**
 * MMKV 存储工厂
 *
 * 用于创建 MMKV 实现的 KVStorage 实例
 * 支持加密存储
 */
class MMKVFactory(private val context: Context) : KVStorageFactory {

    init {
        initialize(context)
    }

    override fun create(name: String): KVStorage =
        MMKVStorage(context, name, EncryptionType.NONE)

    override fun create(name: String, encryption: EncryptionType): KVStorage =
        MMKVStorage(context, name, encryption)

    companion object {
        @Volatile
        private var initialized = false

        /** 初始化 MMKV，幂等。建议在 Application.onCreate 中调用 */
        @JvmStatic
        fun initialize(context: Context) {
            if (initialized) return
            synchronized(this) {
                if (initialized) return
                MMKV.initialize(context.applicationContext)
                initialized = true
            }
        }
    }
}
