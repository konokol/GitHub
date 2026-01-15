package com.ivan.github.core.net;

import androidx.annotation.Nullable;

/**
 * com.ivan.github.core.net
 * business exception
 * <p>
 *
 * @author Ivan J. Lee on 2022-01-04 23:58
 * @since v1.0
 */
public class BizException extends Throwable {

    private final int code;
    private final String message;
    private Throwable throwable;

    public BizException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(int code, String message, Throwable throwable) {
        this.code = code;
        this.message = message;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    @Nullable
    @Override
    public String getLocalizedMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
