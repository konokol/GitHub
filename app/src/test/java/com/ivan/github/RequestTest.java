package com.ivan.github;

import com.ivan.github.account.model.Authorization;
import com.ivan.github.api.OAuthService;
import com.ivan.github.core.net.HttpClient;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Request test
 *
 * @author Ivan on 2018-11-27 22:55.
 * @version v0.1
 * @since v1.0
 */
public class RequestTest {

    @Before
    public void init() {

    }

    @Test
    public void testRequest() {
        HttpClient.service(OAuthService.class)
                .listAuthorizations(Credentials.basic("", ""))
                .enqueue(new Callback<List<Authorization>>() {
                    @Override
                    public void onResponse(Call<List<Authorization>> call, Response<List<Authorization>> response) {
                        List<Authorization> authorizations = response.body();
                        System.out.println("response: " + response.isSuccessful() + ": " + " size: " + authorizations.size());
                    }

                    @Override
                    public void onFailure(Call<List<Authorization>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }
}
