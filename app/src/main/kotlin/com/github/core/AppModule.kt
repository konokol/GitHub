package com.github.core

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.github.account.IUserCenter
import com.github.account.UserCenterImpl
import com.github.app.GHStorage
import com.github.utils.SecureSharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private val mContext: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideContext(): Context = mContext

    @Provides
    @Singleton
    fun providePreference(): SharedPreferences = 
        mContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideResources(): Resources = mContext.resources

    @Provides
    @Singleton
    fun provideSecureSharedPreference(): SecureSharedPreference =
        SecureSharedPreference(mContext, "app_settings", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideUserCenter(): IUserCenter = UserCenterImpl.getInstance()

    @Provides
    @Singleton
    fun provideGHStorage(context: Context): GHStorage = GHStorage(context)
}
