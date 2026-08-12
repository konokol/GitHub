package com.pankoku.ghstorage.datastore

import android.content.Context
import com.pankoku.ghstorage.EncryptionType
import com.pankoku.ghstorage.KVStorage
import com.pankoku.ghstorage.KVStorageException
import com.pankoku.ghstorage.KVStorageFactory

/**
 * DataStore 存储工厂
 *
 * 用于创建 DataStore 实现的 KVStorage 实例
 * 注意：DataStore Preferences API 不支持加密，所以加密选项被忽略
 */
class DataStoreFactory(private val context: Context) : KVStorageFactory {

    override fun create(name: String): KVStorage {
        return DataStoreStorage(context, name)
    }

    override fun create(name: String, encryption: EncryptionType): KVStorage {
        // DataStore Preferences API 不支持加密
        // 如果用户指定了加密，返回一个警告但仍然创建实例
        if (encryption != EncryptionType.NONE) {
            throw KVStorageException.UnsupportedOperationException(
                "DataStore Preferences API does not support encryption. " +
                "Consider using MMKV or another encrypted storage solution."
            )
        }
        return DataStoreStorage(context, name)
    }
}