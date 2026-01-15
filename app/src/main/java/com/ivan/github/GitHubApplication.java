package com.ivan.github;

import android.app.Application;
import android.content.Context;

import com.ivan.github.core.LifecycleMonitor;
import com.ivan.github.core.init.AsyncAppInitializer;

/**
 * Custom Application
 *
 * @author  Ivan at 2016-12-11  22:57
 * @version v0.1
 * @since   v0.1
 */

public class GitHubApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.registerActivityLifecycleCallbacks(new LifecycleMonitor());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AsyncAppInitializer.getInstance(this).asyncInit();
    }
}
