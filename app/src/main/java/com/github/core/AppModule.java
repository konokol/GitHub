package com.github.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.github.account.IUserCenter;
import com.github.account.UserCenterImpl;
import com.github.app.GHStorage;
import com.github.utils.SecureSharedPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module to hold application
 *
 * @author  Ivan on 2018-11-17 00:00.
 * @version v0.1
 * @since   v1.0
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    SharedPreferences providePreference() {
        return mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return mContext.getResources();
    }

    @Provides
    @Singleton
    SecureSharedPreference provideSecureSharedPreference() {
        return new SecureSharedPreference(mContext, "app_settings", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    IUserCenter provideUserCenter() {
        return UserCenterImpl.getInstance();
    }

    @Provides
    @Singleton
    GHStorage provideGHStorage(Context context) {
        return new GHStorage(context);
    }
}