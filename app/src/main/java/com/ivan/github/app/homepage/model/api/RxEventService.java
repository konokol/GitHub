package com.ivan.github.app.homepage.model.api;

import com.ivan.github.app.homepage.model.entity.event.Event;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * api for events
 *
 * @author  Ivan J. Lee on 2019-04-21 22:40.
 * @version v0.1
 * @since   v1.0
 */
public interface RxEventService {

    @GET("/events")
    Observable<Result<Event[]>> listPublicEvents(@Query("page") int page,
                                                 @Query("per_page") int pageSize);

    @GET("/users/{username}/received_events")
    Observable<Result<Event[]>> listUserReceivedEvents(@Path("username") String username,
                                                       @Query("page") int page,
                                                       @Query("per_page") int pageSize);
}
