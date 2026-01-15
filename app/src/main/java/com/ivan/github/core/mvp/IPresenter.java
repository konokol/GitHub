package com.ivan.github.core.mvp;

/**
 * IPresenter
 *
 * @author  Ivan J. Lee on 2019-04-22 23:06.
 * @version v0.1
 * @since   v1.0
 */
public interface IPresenter<V> {

    void setView(V view);

    void start();

    void stop();
}
