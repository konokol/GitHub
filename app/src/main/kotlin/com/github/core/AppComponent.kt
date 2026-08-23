package com.github.core

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.github.GitHubApplication
import com.github.account.IUserCenter
import com.github.app.GHStorage
import com.github.core.net.NetModule
import com.github.utils.SecureSharedPreference
import com.google.gson.Gson
import dagger.Component
import dagger.android.AndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent : AndroidInjector<GitHubApplication> {

    fun applicationContext(): Context

    @Deprecated("Use ghStorage() instead")
    fun preference(): SharedPreferences

    fun resources(): Resources

    fun retrofit(): Retrofit

    fun gson(): Gson

    fun secureSharedPreference(): SecureSharedPreference

    fun userCenter(): IUserCenter

    fun ghStorage(): GHStorage
}
