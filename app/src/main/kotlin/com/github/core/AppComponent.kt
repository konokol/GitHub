package com.github.core

import android.content.Context
import android.content.SharedPreferences
import com.github.account.IUserCenter
import com.github.app.GHStorage
import com.github.core.net.NetModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun userCenter(): IUserCenter
    fun ghStorage(): GHStorage
    fun retrofit(): Retrofit
    fun preference(): SharedPreferences
    fun secureSharedPreference(): SharedPreferences
    fun applicationContext(): Context
}
