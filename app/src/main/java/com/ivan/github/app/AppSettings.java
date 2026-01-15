package com.ivan.github.app;

import com.ivan.github.BuildConfig;
import com.ivan.github.GitHub;
import com.ivan.github.core.BaseConfig;

/**
 * global settings
 *
 * @author  Ivan on 2018-12-24 23:01.
 * @version v0.1
 * @since   v0.1.0
 */
public class AppSettings {

    private static final String PREFIX = BuildConfig.APPLICATION_ID;

    private static final String KEY_VERSION = PREFIX + ".version";

    public static void setVersion(int versionCode) {
        GitHub.appComponent().preference().edit().putInt(KEY_VERSION, versionCode).apply();
    }

    public static boolean isFirstLogin() {
        int v = GitHub.appComponent().preference().getInt(KEY_VERSION, -1);
        return BaseConfig.versionCode > v;
    }
}
