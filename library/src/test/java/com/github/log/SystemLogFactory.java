package com.github.log;

import android.util.SparseArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * com.github.log.SystemLogFactory
 *
 * @author Ivan J. Lee on 2020-10-29 23:55
 * @version v0.1
 * @since v1.0
 **/
public class SystemLogFactory implements ILoggerFactory {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private final Map<Integer, String> logLevel = new HashMap<>();

    {
        logLevel.put(LogLevel.VERBOSE, "V");
        logLevel.put(LogLevel.DEBUG, "D");
        logLevel.put(LogLevel.INFO, "I");
        logLevel.put(LogLevel.WARN, "W");
        logLevel.put(LogLevel.ERROR, "E");
        logLevel.put(LogLevel.ASSERT, "A");
    }

    private String getLogLevel(@LogLevel int level) {
        String s = logLevel.get(level);
        if (s == null) {
            return "?";
        } else {
            return s;
        }
    }

    private String buildString(int level, CharSequence charSequence) {
        String color = "\033[0;37m%s\033[0m";
        switch (level) {
            case LogLevel.VERBOSE:
                color = "\033[0;37m%s\033[0m";
                break;
            case LogLevel.DEBUG:
                color = "\033[0;97m%s\033[0m";
                break;
            case LogLevel.INFO:
                color = "\033[0;34m%s\033[0m";
                break;
            case LogLevel.WARN:
                color = "\033[0;33m%s\033[0m";
                break;
            case LogLevel.ERROR:
                color = "\033[0;31m%s\033[0m";
                break;
            case LogLevel.ASSERT:
                color = "\033[0;91m%s\033[0m";
                break;
        }
        return String.format(color, charSequence);
    }

    @Override
    public ILogger create() {
        return new ILogger() {
            @Override
            public void log(int level, String tag, String msg, Throwable throwable) {
                StringBuilder sb = new StringBuilder()
                        .append(format.format(new Date(System.currentTimeMillis())))
                        .append(" Thread-")
                        .append(Thread.currentThread().getName())
                        .append("/")
                        .append(Thread.currentThread().getId())
                        .append(" ")
                        .append(getLogLevel(level))
                        .append("/")
                        .append(tag)
                        .append(": ")
                        .append(msg);
                System.out.println(buildString(level, sb));
                if (throwable != null) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public boolean isLoggable(String tag, int level) {
                return true;
            }
        };
    }
}
