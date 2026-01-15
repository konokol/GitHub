package com.github.log;

import android.util.Log;
/**
 * LogcatLogger
 *
 * @author  Ivan on 2020-04-15
 * @version v1.0
 * @since   v1.0
 */
public class LogcatLogger implements ILogger {

    @Override
    public void log(int level, String tag, String info, Throwable throwable) {
        String msg;
        if (info.length() >= 4096) {
            msg = info.subSequence(0, 1024) + "... \nemmit " + (info.length() - 1024) + "characters";
        } else {
            msg = info;
        }
        switch (level) {
            case LogLevel.VERBOSE:
                Log.v(tag, msg, throwable);
                break;
            case LogLevel.DEBUG:
                Log.d(tag, msg, throwable);
                break;
            case LogLevel.INFO:
                Log.i(tag, msg, throwable);
                break;
            case LogLevel.WARN:
                Log.w(tag, msg, throwable);
                break;
            case LogLevel.ERROR:
                Log.e(tag, msg, throwable);
                break;
            case LogLevel.ASSERT:
                Log.wtf(tag, msg, throwable);
                break;
            default:
        }
    }

    @Override
    public boolean isLoggable(String tag, int level) {
        return true;
    }
}
