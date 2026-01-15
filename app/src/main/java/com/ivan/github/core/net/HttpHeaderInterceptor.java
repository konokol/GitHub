package com.ivan.github.core.net;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * request and response log
 *
 * @author  Ivan on 2018-11-22 00:01.
 * @version v0.1
 * @since   v1.0
 */
public class HttpHeaderInterceptor implements Interceptor  {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (TextUtils.isEmpty(request.header("Content-Type"))) {
                builder.addHeader("Content-Type", "application/json");
        }
        if (TextUtils.isEmpty(request.header("Char-Set"))) {
            builder.addHeader("Char-Set", "UTF-8");
        }
        if (TextUtils.isEmpty(request.header("Accept"))) {
                builder.addHeader("Accept", "application/json");
        }
        return chain.proceed(builder.build());
    }
}
