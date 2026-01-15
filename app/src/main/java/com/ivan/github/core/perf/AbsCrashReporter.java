package com.ivan.github.core.perf;

import android.annotation.SuppressLint;

import com.ivan.github.GitHub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * com.ivan.github.core.perf.AbsCrashReporter
 *
 * @author Ivan on 2019-11-20
 * @version v0.1
 * @since v1.0
 **/
public abstract class AbsCrashReporter implements ICrashReporter {

    private static String LOG_PATH = GitHub.appComponent().applicationContext().getExternalFilesDir(null) + "/crash/";

    @Override
    public String getLogPath() {
        return LOG_PATH;
    }

    @Override
    public boolean isReportToLocal() {
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void saveLog(Thread thread, Throwable throwable) {
        String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis()) + ".log";
        File file = new File(LOG_PATH, filename);
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.write("Thread: " + thread.getName() + " id:" + thread.getId());
            pw.write("crash at: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "\n");
            throwable.printStackTrace(pw);
            pw.flush();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
