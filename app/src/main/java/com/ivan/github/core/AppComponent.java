package com.ivan.github.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.github.utils.SecureSharedPreference;
import com.google.gson.Gson;
import com.ivan.github.GitHubApplication;
import com.ivan.github.account.IUserCenter;
import com.ivan.github.core.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import retrofit2.Retrofit;

/**
 * AppComponent
 *
 * @author  Ivan on 2018-11-16 23:47.
 * @version v0.1
 * @since   v0.1.0
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent extends AndroidInjector<GitHubApplication> {

    Context applicationContext();

    SharedPreferences preference();

    Resources resources();

    Retrofit retrofit();

    Gson gson();

    SecureSharedPreference secureSharedPreference();

    IUserCenter userCenter();

}
