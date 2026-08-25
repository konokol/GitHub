package com.github;

import android.app.Application;
import android.content.Context;

import com.github.core.LifecycleMonitor;
import com.github.core.init.AsyncAppInitializer;
import com.pancoku.vault.mmkv.MMKVFactory;

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
        // 初始化 MMKV（用于支持加密存储）
        MMKVFactory.initialize(this);
        
        AsyncAppInitializer.getInstance(this).asyncInit();
    }
}