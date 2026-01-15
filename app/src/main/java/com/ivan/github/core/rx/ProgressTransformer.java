package com.ivan.github.core.rx;

import com.github.log.Logan;
import com.ivan.github.core.mvp.IBaseStateView;

import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * com.ivan.github.core.rx
 * <p>
 * handle loading on request
 * <p>
 *
 * @author Ivan J. Lee on 2022-01-04 23:27
 * @since v1.0
 */
public class ProgressTransformer {

    private static final String TAG = "ProgressTransformer-HTTP";

    public static <T> ObservableTransformer<T, T> apply(IBaseStateView<?> view) {
        return upstream -> upstream.doOnSubscribe(disposable -> {
            Logan.d(TAG, "doOnSubscribe");
            if (view != null && view.isAlive()) {
                view.showLoading();
            }
        }).doFinally(() -> {
            Logan.d(TAG, "doFinally");
            if (view != null && view.isAlive()) {
                view.dismissLoading();
            } else {
                Logan.d(TAG, "doFinally view not alive");
            }
        }).doOnDispose(() -> {
            Logan.d(TAG, "doOnDispose");
            if (view != null && view.isAlive()) {
                view.dismissLoading();
            }
        });
    }

    public static <T> ObservableTransformer<T, T> empty() {
        return upstream -> upstream.doOnNext(t -> {
            Logan.d(TAG, "empty");
        });
    }
}
