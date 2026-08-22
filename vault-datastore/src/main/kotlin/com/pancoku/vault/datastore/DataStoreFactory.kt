package com.pancoku.vault.datastore

import android.content.Context
import com.pancoku.vault.EncryptionType
import com.pancoku.vault.KVStorage
import com.pancoku.vault.KVStorageException
import com.pancoku.vault.KVStorageFactory

class DataStoreFactory(private val context: Context) : KVStorageFactory {
    override fun create(name: String): KVStorage = DataStoreStorage(context, name)

    override fun create(name: String, encryption: EncryptionType): KVStorage {
        if (encryption != EncryptionType.NONE) {
            throw KVStorageException.UnsupportedOperationException("DataStore does not support encryption")
        }
        return DataStoreStorage(context, name)
    }
}