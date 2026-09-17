package com.github.core

import android.content.Context
import android.content.SharedPreferences
import com.github.account.IUserCenter
import com.github.app.GHStorage
import com.github.core.net.NetModule
import com.github.utils.SecureSharedPreference
import com.pancoku.vault.KVStorage
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun userCenter(): IUserCenter
    fun ghStorage(): GHStorage
    fun retrofit(): Retrofit
    @Deprecated("default preference is not supported")
    fun preference(): SharedPreferences
    @Deprecated("default preference is not supported")
    fun secureSharedPreference(): SecureSharedPreference
    fun applicationContext(): Context
}
