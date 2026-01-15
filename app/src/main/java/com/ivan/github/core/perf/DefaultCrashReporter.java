package com.ivan.github.core.perf;

/**
 * com.ivan.github.core.perf.DefaultCrashReporter
 *
 * @author  Ivan on 2019-11-20
 * @version v0.1
 * @since   v1.0
 **/
public class DefaultCrashReporter extends AbsCrashReporter {

    @Override
    public void sendNotification(Throwable throwable) {
        // do nothing
    }

    @Override
    public void report() {

    }
}
