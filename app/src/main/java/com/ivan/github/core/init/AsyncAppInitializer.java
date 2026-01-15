package com.ivan.github.core.init;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.os.Trace;

import androidx.annotation.NonNull;
import androidx.startup.AppInitializer;
import androidx.startup.InitializationProvider;
import androidx.startup.Initializer;

import com.ivan.github.R;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.pm.PackageManager.GET_META_DATA;

/**
 * com.ivan.github.core.init.AsyncAppInitializer
 *
 * @author Ivan J. Lee on 2021-07-20 23:43
 * @version v0.1
 * @since v1.0
 **/
public class AsyncAppInitializer {

    // Tracing
    private static final String SECTION_NAME = "AsyncStartup";

    @NonNull
    final Context mContext;

    ExecutorService mExecutorService;

    /**
     * Creates an instance of {@link AsyncAppInitializer}
     *
     * @param context The application context
     */
    private AsyncAppInitializer(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    /**
     * the {@link AsyncAppInitializer} instance
     */
    private static volatile AsyncAppInitializer sInstance;

    @NonNull
    public static AsyncAppInitializer getInstance(@NonNull Context context) {
        if (sInstance == null) {
            synchronized (AsyncAppInitializer.class) {
                if (sInstance == null) {
                    sInstance = new AsyncAppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    public void asyncInit() {
        Thread initTask = new Thread(this::doAsyncInit, "T-" + SECTION_NAME);
        mExecutorService.submit(initTask);
    }

    @SuppressWarnings("unchecked")
    public void doAsyncInit() {
        try {
            Trace.beginSection(SECTION_NAME);
            ComponentName provider = new ComponentName(mContext.getPackageName(),
                    InitializationProvider.class.getName());
            ProviderInfo providerInfo = mContext.getPackageManager()
                    .getProviderInfo(provider, GET_META_DATA);
            Bundle metadata = providerInfo.metaData;
            String startup = mContext.getString(R.string.androidx_startup_async);
            if (metadata != null) {
                Set<String> keys = metadata.keySet();
                for (String key : keys) {
                    String value = metadata.getString(key, null);
                    if (startup.equals(value)) {
                        Class<?> clazz = Class.forName(key);
                        if (Initializer.class.isAssignableFrom(clazz)) {
                            Class<? extends Initializer<Object>> component =
                                    (Class<? extends Initializer<Object>>) clazz;
                            if (!AppInitializer.getInstance(mContext).isEagerlyInitialized(component)) {
                                AppInitializer.getInstance(mContext).initializeComponent(component);
                            }
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        } finally {
            Trace.endSection();
        }
    }
}
