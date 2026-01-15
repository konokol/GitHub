package com.ivan.github.core.perf;

/**
 * com.ivan.github.core.perf.ICrashReporter
 *
 * @author Ivan on 2019-11-20
 * @version v0.1
 * @since v1.0
 **/
public interface ICrashReporter {

    String getLogPath();

    void sendNotification(Throwable throwable);

    boolean isReportToLocal();

    void saveLog(Thread thread, Throwable throwable);

    void report();
}
