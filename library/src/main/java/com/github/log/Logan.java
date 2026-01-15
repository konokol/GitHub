package com.github.log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * tool class to print log
 *
 * @author  Ivan on 2020-04-14
 * @version v1.0
 * @since   v1.0
 */
public final class Logan {

    private final static List<ILogger> sLoggers = new ArrayList<>();

    public static void v(String tag, String msg, Object ... args) {
        print(LogLevel.VERBOSE, tag, null, msg, args);
    }

    public static void v(String tag, String msg, Throwable throwable) {
        print(LogLevel.VERBOSE, tag, throwable, msg);
    }

    public static void d(String tag, String msg, Object ... args) {
        print(LogLevel.DEBUG, tag, null, msg, args);
    }

    public static void d(String tag, String msg, Throwable throwable) {
        print(LogLevel.DEBUG, tag, throwable, msg);
    }

    public static void i(String tag, String msg, Object ... args) {
        print(LogLevel.INFO, tag, null, msg, args);
    }

    public static void i(String tag, String msg, Throwable throwable) {
        print(LogLevel.INFO, tag, throwable, msg);
    }

    public static void w(String tag, String msg, Object ... args) {
        print(LogLevel.WARN, tag, null, msg, args);
    }

    public static void w(String tag, String msg, Throwable throwable) {
        print(LogLevel.WARN, tag, throwable, msg);
    }

    public static void e(String tag, String msg, Object ... args) {
        print(LogLevel.ERROR, tag, null, msg, args);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        print(LogLevel.ERROR, tag, throwable, msg);
    }

    public static void wtf(String tag, String msg, Object ... args) {
        print(LogLevel.ASSERT, tag, null, msg, args);
    }

    public static void wtf(String tag, String msg, Throwable throwable) {
        print(LogLevel.ASSERT, tag, throwable, msg);
    }

    private static void print(int level, String tag, Throwable throwable, String format, Object ... args) {
        for (ILogger logger : sLoggers) {
            if (logger.isLoggable(tag, level)) {
                if (args == null || args.length == 0) {
                    logger.log(level, tag, format, throwable);
                } else {
                    logger.log(level, tag, MessageFormat.format(format, args), throwable);
                }
            }
        }
    }

    /**
     * init log
     * @param factories create logger
     */
    public static void init(ILoggerFactory ... factories) {
        sLoggers.clear();
        if (factories == null) {
            return;
        }

        for (ILoggerFactory factory : factories) {
            sLoggers.add(factory.create());
        }
    }
}
