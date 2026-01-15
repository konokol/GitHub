package com.ivan.github.app.homepage.model.impl;

import com.ivan.github.GitHub;
import com.ivan.github.app.homepage.model.IFeedDataStore;
import com.ivan.github.app.homepage.model.api.RxEventService;
import com.ivan.github.app.homepage.model.entity.event.Event;
import com.ivan.github.core.net.HttpClient;
import com.ivan.github.core.net.TransformerHelper;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

/**
 * Event Model
 *
 * @author  Ivan J. Lee on 2019-04-22 22:56.
 * @version v0.1
 * @since   v1.0
 */
public class FeedRepository implements IFeedDataStore {

    @Override
    public Observable<List<Event>> listUserEvents(int page) {
        String username = GitHub.appComponent().userCenter().getUsername();
        return HttpClient.service(RxEventService.class)
                .listUserReceivedEvents(username, page, PAGE_SIZE)
                .compose(TransformerHelper.result())
                .flatMap((Function<Event[], ObservableSource<List<Event>>>) events
                        -> Observable.just(Arrays.asList(events)));
    }

    @Override
    public Observable<List<Event>> listPublicEvents(int page) {
        return HttpClient.service(RxEventService.class)
                .listPublicEvents(page, PAGE_SIZE)
                .compose(TransformerHelper.result())
                .flatMap((Function<Event[], ObservableSource<List<Event>>>) events
                        -> Observable.just(Arrays.asList(events)));

    }
}
