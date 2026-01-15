package com.ivan.github.core.init;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.ivan.github.GitHub;

import java.util.Collections;
import java.util.List;

/**
 * com.ivan.github.core.init.AppInit
 *
 * @author Ivan J. Lee on 2021-07-20 23:09
 * @version v0.1
 * @since v1.0
 **/
public class AppInit extends AbsInitializer<GitHub> {

    @NonNull
    @Override
    public String getTag() {
        return "AppInit";
    }

    @Override
    public GitHub onCreate(Context context) {
        return GitHub.init(context.getApplicationContext());
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.singletonList(LogInit.class);
    }
}
