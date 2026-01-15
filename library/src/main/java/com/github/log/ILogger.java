package com.github.log;

/**
 * ILogger
 *
 * @author Ivan on 2020-04-14
 * @version v1.0
 * @since v1.0
 */
public interface ILogger {

    void log(int level, String tag, String msg, Throwable throwable);

    boolean isLoggable(String tag, int level);
}
