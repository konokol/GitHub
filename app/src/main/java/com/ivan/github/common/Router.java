package com.ivan.github.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.github.log.Logan;

import java.util.Map;

/**
 * com.ivan.github.common
 * Router
 * <p>
 *
 * @author Ivan J. Lee on 2023-02-02 23:48
 * @since v1.0
 */
public class Router {
    public static final String TAG = "Router";

    public static void start(Context context, String path) {
        start(context, path, null);
    }

    public static void start(Context context, String path, Map<String, String> params) {
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(path)) {
            path = "/";
        }
        Uri.Builder builder = UriBuilder.with(path);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Logan.e(TAG, "failed to start Activity", exception);
        }
    }
}
