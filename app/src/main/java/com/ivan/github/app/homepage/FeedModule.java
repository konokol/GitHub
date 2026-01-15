package com.ivan.github.app.homepage;

import com.ivan.github.app.homepage.model.impl.FeedRepository;
import com.ivan.github.app.homepage.presenter.FeedPresenter;
import com.ivan.github.app.homepage.model.IFeedDataStore;

import dagger.Module;
import dagger.Provides;

/**
 * EventModule
 *
 * @author  Ivan J. Lee on 2019-04-22 21:45.
 * @version v0.1
 * @since   v1.0
 */
@Module
public class FeedModule {

    private FeedContract.View mView;

    public FeedModule() {
    }

    public FeedModule(FeedContract.View mView) {
        this.mView = mView;
    }

    @Provides
    public FeedPresenter providePresenter(FeedContract.View view) {
        return new FeedPresenter(view);
    }

    @Provides
    public FeedContract.View provideFeedView() {
        return mView;
    }

    @Provides
    public IFeedDataStore providerDataStore() {
        return new FeedRepository();
    }
}