package com.ivan.github.core.net;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;

/**
 * com.ivan.github.core.net.SchedulerTransformer
 *
 * @author Ivan J. Lee on 2020-10-27 00:01
 * @version v0.1
 * @since v1.0
 **/
public class HttpTransformer<R, T> implements ObservableTransformer<R, T> {

    @Override
    public @NonNull ObservableSource<T> apply(@NonNull Observable<R> upstream) {
        return null;
    }
}
