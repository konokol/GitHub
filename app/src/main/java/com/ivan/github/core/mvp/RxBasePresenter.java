package com.ivan.github.core.mvp;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * BaseRxPresenter
 *
 * @author  Ivan J. Lee on 2019-04-22 00:23.
 * @version v0.1
 * @since   v1.0
 */
public abstract class RxBasePresenter<V extends IBaseView<?>> extends BasePresenter<V> {

    protected CompositeDisposable mDisposables;

    public RxBasePresenter(V mView) {
        super(mView);
    }

    protected void addDisposable(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    @Override
    public void stop() {
        if (mDisposables != null) {
            mDisposables.dispose();
        }
    }
}
