package com.pancoku.vault.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.util.concurrent.ConcurrentHashMap

/**
 * VaultDataStoreManager
 * 
 * 确保每个 DataStore 文件在全局只有一个实例，避免 Multiple DataStores active 异常。
 */
internal object VaultDataStoreManager {
    private val cache = ConcurrentHashMap<String, DataStore<Preferences>>()

    fun get(context: Context, name: String): DataStore<Preferences> {
        return cache.getOrPut(name) {
            Holder(context.applicationContext, name).dataStore
        }
    }

    private class Holder(context: Context, name: String) {
        private val Context.internalDataStore by preferencesDataStore(name)
        val dataStore: DataStore<Preferences> = context.internalDataStore
    }
}
