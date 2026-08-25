package com.github.core

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.github.account.IUserCenter
import com.github.account.UserCenterImpl
import com.github.app.GHStorage
import com.pancoku.vault.mmkv.MMKVCryptKeyProvider
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideGHStorage(context: Context): GHStorage = GHStorage(context)

    @Provides
    @Singleton
    fun provideUserCenter(impl: UserCenterImpl): IUserCenter = impl

    @Provides
    @Singleton
    fun providePreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideSecureSharedPreference(context: Context): SharedPreferences {
        val cryptKey = MMKVCryptKeyProvider.getOrCreate(context)
        return MMKV.mmkvWithID("secure", MMKV.SINGLE_PROCESS_MODE, cryptKey)
    }
}
