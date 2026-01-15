package com.ivan.github.core.mvp;

/**
 * com.ivan.github.core.mvp.IBaseStateView
 *
 * @author  Ivan J. Lee on 2019-11-20
 * @version v0.1
 * @since   v1.0
 **/
public interface IBaseStateView<P> extends IBaseView<P> {

    P getPresenter();

    void attach(P presenter);

    void detach(P presenter);

    void showLoading();

    void dismissLoading();

    void showToast(String msg);

}
