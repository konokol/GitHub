package com.github.app.homepage;

import com.github.app.homepage.model.IFeedDataStore;
import com.github.app.homepage.model.entity.event.Event;
import com.github.core.mvp.IBaseStateView;
import com.github.core.mvp.IPresenter;

import java.util.List;

/**
 * Event Contract
 *
 * @author  Ivan J. Lee on 2019-04-22 22:20.
 * @version v0.1
 * @since   v1.0
 */
public interface FeedContract {

    int pageSize = IFeedDataStore.PAGE_SIZE;

    interface View extends IBaseStateView<Presenter> {

        void initList(List<Event> list);

        void updateList(List<Event> list);

        void showEmptyView();

        void showErrorPage(int code, String error);

        void showEnd();
    }

    interface Presenter extends IPresenter<View> {

        void listUserEvents(int page);

        void refresh();

        void loadMore();
    }
}
