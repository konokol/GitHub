package com.github.log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.github.log.LogLevel.*;

/**
 * log level, the same as {@link android.util.Log}
 *
 * @author Ivan on 2020-04-14
 * @version v1.0
 * @since v1.0
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {
        VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT
})
public @interface LogLevel {
    /**
     * Priority constant for the println method; use Log.v.
     */
    int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    int ASSERT = 7;
}
