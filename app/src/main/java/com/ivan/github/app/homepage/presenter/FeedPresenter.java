package com.ivan.github.app.homepage.presenter;

import android.text.TextUtils;

import com.github.log.Logan;
import com.github.utils.CollectionUtils;
import com.ivan.github.GitHub;
import com.ivan.github.app.homepage.DaggerFeedComponent;
import com.ivan.github.app.homepage.FeedContract;
import com.ivan.github.app.homepage.FeedModule;
import com.ivan.github.app.homepage.model.IFeedDataStore;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.core.mvp.RxBasePresenter;
import com.ivan.github.core.net.BizException;
import com.ivan.github.core.net.TransformerHelper;
import com.ivan.github.core.rx.ProgressTransformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Event Presenter
 *
 * @author  Ivan J. Lee on 2019-04-22 22:42.
 * @version v0.1
 * @since   v1.0
 */
public class FeedPresenter extends RxBasePresenter<FeedContract.View>
        implements FeedContract.Presenter {

    private static final String TAG = "FeedPresenter";

    @Inject
    IFeedDataStore mDataStore;
    private final List<Event> mData = new ArrayList<>(FeedContract.pageSize);

    public FeedPresenter(FeedContract.View mView) {
        super(mView);
        inject();
    }

    protected void inject() {
        DaggerFeedComponent.builder()
                .feedModule(new FeedModule())
                .build()
                .inject(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        Logan.d(TAG, "stop presenter");
    }

    @Override
    public void listUserEvents(int page) {
        listUserEvents(page, true);
    }

    private void listUserEvents(int page, boolean showLoading) {
        Observable<List<Event>> observable;
        if (GitHub.appComponent().userCenter().isLogin()) {
            observable = mDataStore.listUserEvents(page);
        } else {
            observable = mDataStore.listPublicEvents(page);
        }
        Disposable disposable = observable
                .compose(showLoading ? ProgressTransformer.apply(getView()) : ProgressTransformer.empty())
                .compose(TransformerHelper.schedulers())
                .subscribe(this::onGetUserEvents, this::onGetUserEventError);
        addDisposable(disposable);
    }

    private void onGetUserEvents(List<Event> list) {
        if (CollectionUtils.isEmpty(list)) {
            if (mData.isEmpty()) {
                getView().showEmptyView();
            } else {
                getView().showEnd();
            }
        } else if (mData.isEmpty()) {
            mData.addAll(list);
            getView().updateList(list);
        } else {
            Event e1 = CollectionUtils.getLast(mData);
            Event e2 = CollectionUtils.getLast(list);
            if (e1 == null || e2 == null || !TextUtils.equals(e1.getId(), e2.getId())) {
                mData.addAll(list);
                getView().updateList(list);
            }
        }
    }

    private void onGetUserEventError(Throwable throwable) {
        int code;
        String msg;
        if (throwable instanceof BizException) {
            code = ((BizException) throwable).getCode();
            msg = throwable.getLocalizedMessage();
            Logan.d(TAG, "error: " + msg);
        } else {
            code = -1;
            msg = "unknown error, " + throwable.getLocalizedMessage();
            Logan.d(TAG, "error: " + msg);
        }
        getView().showErrorPage(code, msg);
    }

    @Override
    public void refresh() {
        mData.clear();
        listUserEvents(0, false);
    }

    @Override
    public void loadMore() {
        int index = mData.size() / FeedContract.pageSize;
        if (mData.size() % FeedContract.pageSize == 0) { //not the last page
            listUserEvents(index);
        }
    }

}
