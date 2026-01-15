package com.ivan.github.core.mvp;

/**
 * BasePresenter
 *
 * @author  Ivan J. Lee on 2019-04-22 00:23.
 * @version v0.1
 * @since   v1.0
 */
public abstract class BasePresenter<V extends IBaseView<?>> implements IPresenter<V> {

    private V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

    @Override
    public void setView(V view) {
        this.mView = view;
    }

    protected V getView() {
        return mView;
    }
}
