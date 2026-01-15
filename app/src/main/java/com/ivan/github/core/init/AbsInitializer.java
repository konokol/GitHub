package com.ivan.github.core.init;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import java.util.Collections;
import java.util.List;

/**
 * com.ivan.github.core.init.AbsInitializer
 *
 * @author  Ivan J. Lee on 2021-07-20 23:11
 * @version v0.1
 * @since   v1.0
 **/
public abstract class AbsInitializer<T> implements Initializer<T> {

    @NonNull
    abstract public String getTag();

    abstract public T onCreate(Context context);

    @NonNull
    @Override
    public final T create(@NonNull Context context) {
        long start = System.currentTimeMillis();
        T t = onCreate(context);
        Log.v(getTag(), "init " + getClass().getSimpleName() + ", cost time: " + (System.currentTimeMillis() - start) + "ms");
        return t;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
