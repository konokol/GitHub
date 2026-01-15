package com.ivan.github.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.github.utils.DeviceUtils;
import com.ivan.github.BuildConfig;

public class BaseConfig {

    /**
     * versionCode
     */
    public static final int versionCode = BuildConfig.VERSION_CODE;

    /**
     * versionName
     */
    public static final String versionName = BuildConfig.VERSION_NAME;

    /**
     * build channel
     */
    public static String channel = BuildConfig.FLAVOR;

    /**
     * build time
     */
    public static long buildTime = BuildConfig.BUILD;

    /**
     * unique device id
     */
    public static final String deviceId = DeviceUtils.getDeviceId();

    /**
     * The time at which the app was first installed.  Units are as
     * per {@link System#currentTimeMillis()}.
     */
    public static long firstInstallTime;

    /**
     * The time at which the app was last updated.  Units are as
     * per {@link System#currentTimeMillis()}.
     */
    public static long lastUpdateTime;

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            firstInstallTime = packageInfo.firstInstallTime;
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
