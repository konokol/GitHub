package com.ivan.github.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.log.Logan;

/**
 * com.ivan.github.core
 * <p>
 *
 * @author lijun on 2023-03-03 22:33
 * @since v1.0
 */
public class LifecycleMonitor implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "LifecycleMonitor";

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Logan.i(TAG, "onActivityCreated: " + activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Logan.i(TAG, "onActivityStarted: " + activity.getClass().getName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Logan.i(TAG, "onActivityResumed: " + activity.getClass().getName());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Logan.i(TAG, "onActivityPaused: " + activity.getClass().getName());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Logan.i(TAG, "onActivityStopped: " + activity.getClass().getName());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Logan.i(TAG, "onActivitySaveInstanceState: " + activity.getClass().getName());
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Logan.i(TAG, "onActivityDestroyed: " + activity.getClass().getName());
    }
}
