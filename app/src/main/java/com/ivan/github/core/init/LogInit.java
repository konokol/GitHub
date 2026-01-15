package com.ivan.github.core.init;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.github.log.Logan;
import com.github.log.LogcatFactory;
import com.github.utils.L;
import com.ivan.github.BuildConfig;

import java.util.Collections;
import java.util.List;

/**
 * com.ivan.github.core.init.LogInit
 *
 * @author Ivan J. Lee on 2021-07-19 23:53
 * @version v0.1
 * @since v1.0
 **/
public class LogInit extends AbsInitializer<Class<Logan>> {

    @NonNull
    @Override
    public String getTag() {
        return "LogInit";
    }

    @Override
    public Class<Logan> onCreate(Context context) {
        L.setDebugLog(BuildConfig.DEBUG, L.VERBOSE);
        if (BuildConfig.DEBUG) {
            Logan.init(new LogcatFactory());
        }
        return Logan.class;
    }
}
