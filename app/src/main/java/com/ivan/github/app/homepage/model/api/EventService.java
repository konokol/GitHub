package com.ivan.github.app.homepage.model.api;

import com.ivan.github.app.homepage.model.entity.event.Event;

import java.util.List;

import retrofit2.Call;
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
public interface EventService {

    @GET("/events")
    Call<List<Event>> listEvents();

    @GET("/repos/{owner}/{repo}/events")
    Call<List<Event>> listRepoEvents(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @GET("/repos/{owner}/{repo}/issues/events")
    Call<List<Event>> listRepoIssueEvents(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @GET("/networks/{owner}/{repo}/events")
    Call<List<Event>> listRepoNetworkEvents(@Path("owner") String owner, @Path("repo") String repo, @Query("page") int page);

    @GET("/orgs/{org}/events")
    Call<List<Event>> listOrgEvents(@Path("org") String org, @Query("page") int page);

    @GET("/users/{username}/received_events")
    Call<Event[]> listUserEvents(@Path("username") String username, @Query("page") int page);

    @GET("/users/{username}/received_events/public")
    Call<List<Event>> listUserEventsPublic(@Path("username") String username, @Query("page") int page);

    @GET("/users/{username}/events")
    Call<List<Event>> listUserEventsPerformed(@Path("username") String username, @Query("page") int page);

    @GET("/users/{username}/events/orgs/{org}")
    Call<List<Event>> listUserOrgEvents(@Path("username") String username, @Path("org") String org, @Query("page") int page);
}
