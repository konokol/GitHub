package com.pancoku.vault.mmkv

import android.content.Context
import com.pancoku.vault.EncryptionType
import com.pancoku.vault.KVStorage
import com.pancoku.vault.KVStorageFactory
import com.tencent.mmkv.MMKV

class MMKVFactory(private val context: Context) : KVStorageFactory {
    init { initialize(context) }
    override fun create(name: String): KVStorage = MMKVStorage(context, name, EncryptionType.NONE)
    override fun create(name: String, encryption: EncryptionType): KVStorage = MMKVStorage(context, name, encryption)

    companion object {
        @Volatile private var initialized = false
        @JvmStatic fun initialize(context: Context) {
            if (initialized) return
            synchronized(this) {
                if (initialized) return
                MMKV.initialize(context.applicationContext)
                initialized = true
            }
        }
    }
}