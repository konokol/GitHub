package com.ivan.github.core.net;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.ivan.github.GitHub;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * authorization
 *
 * @author  Ivan on 2018-11-21 23:34.
 * @version v0.1
 * @since   v0.1.0
 */
public class AuthorizationInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String auth = GitHub.appComponent().userCenter().getAuthorization();
        if (TextUtils.isEmpty(auth)) {
            auth = GitHub.appComponent().userCenter().getAuthorization();
        }
        String authorization = request.header("Authorization");
        if (!TextUtils.isEmpty(auth) && TextUtils.isEmpty(authorization)) {
            request = request.newBuilder()
                    .addHeader("Authorization", "token " + auth)
                    .build();
        }
        return chain.proceed(request);
    }
}
