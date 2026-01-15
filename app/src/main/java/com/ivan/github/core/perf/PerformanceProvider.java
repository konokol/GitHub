package com.ivan.github.core.perf;

/**
 * com.ivan.github.core.perf.PerformanceProvider
 *
 * @author  Ivan on 2019-11-20
 * @version v0.1
 * @since   v1.0
 **/
public class PerformanceProvider {

    public static ICrashReporter provideCrashReporter() {
        return new DefaultCrashReporter();
    }
}
