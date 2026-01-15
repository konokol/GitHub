package com.ivan.github.core.net;

import androidx.annotation.NonNull;

import com.github.log.Logan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * handle net response
 *
 * @author Ivan on 2018-11-27 23:21.
 * @version v0.1
 * @since v0.1.0
 */
public abstract class ApiCallback<T> implements Callback<T> {

    public static final String TAG = "ApiCallback";

    public static final int ERROR_CODE_UNKNOWN = -1;

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            this.onSuccess(response.body());
        } else {
            this.onFailure(response.code(), response.message(), new HttpException(response));
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Logan.e(TAG, "request error: ", t);
        if (t instanceof HttpException) {
            this.onFailure(((HttpException) t).code(), t.getLocalizedMessage(), t);
        } else {
            this.onFailure(ERROR_CODE_UNKNOWN, t.getLocalizedMessage(), t);
        }
    }

    public abstract void onSuccess(T response);

    public abstract void onFailure(int code, String msg, Throwable throwable);
}
