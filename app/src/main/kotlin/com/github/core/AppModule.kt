package com.github.core

import android.content.Context
import android.content.SharedPreferences
import com.github.account.IUserCenter
import com.github.account.UserCenterImpl
import com.github.app.GHStorage
import com.github.utils.SecureSharedPreference
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
    fun providePreference(): SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE);

    @Provides
    @Singleton
    fun provideSecureSharedPreference(): SecureSharedPreference =
        SecureSharedPreference(context, "app_settings", Context.MODE_PRIVATE)
}
