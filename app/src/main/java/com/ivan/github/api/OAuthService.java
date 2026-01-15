package com.ivan.github.api;

import com.ivan.github.account.model.Authorization;
import com.ivan.github.account.model.User;
import com.ivan.github.app.login.model.OAuthReq;
import com.ivan.github.app.login.model.OAuthResp;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * all requests
 *
 * @author  Ivan J. Lee on 2018-11-22 23:28.
 * @version v0.1
 * @since   v0.1.0
 */
public interface OAuthService {

    @GET("/authorizations")
    Call<List<Authorization>> listAuthorizations(@Header("Authorization") String auth);

    @GET("/user")
    Call<User> getAuthorizedUser(@Header("Authorization") String auth);

    @GET("/user")
    Call<User> getAuthorizedUser();

    @POST("https://github.com/login/oauth/access_token")
    Observable<Result<OAuthResp>> oauth(@Body OAuthReq req);

    @GET("/user")
    Observable<Result<User>> getUser(@Header("Authorization") String auth);
}
