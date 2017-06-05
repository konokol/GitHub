package com.ivan.github.app;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ivan.github.BuildConfig;
import com.ivan.github.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


/**
 * CrashHandler to handle crashes
 *
 * @author  Ivan on 2017/6/1 10:15.
 * @version v0.1
 * @since   v1.0
 */

class CrashHandler implements Thread.UncaughtExceptionHandler {

    @SuppressLint("StaticFieldLeak")
    private static volatile CrashHandler sInstance;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private boolean mReportToLocal;

    private static final String TAG = "CrashHandler";
    private static String LOG_PATH = App.getApplication().getExternalFilesDir(null) + "/crash/";

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new CrashHandler();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void setReportToLocal(boolean reportToLocal) {
        this.mReportToLocal = reportToLocal;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpLog(e);
        report(e);
        reportToLocal(e);
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        }
    }

    /**
     * send a notification to show a crash information
     */
    private void reportToLocal(Throwable e) {
        if (mReportToLocal) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.drawable.ic_warning)
                    .setTicker(mContext.getText(R.string.crash_ticker))
                    .setContentTitle(mContext.getString(R.string.crash_content_title))
                    .setContentText(mContext.getString(R.string.crash_content_text));
            NotificationManager mNotificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());
        }
    }

    /**
     * save log to sdcard
     */
    @SuppressLint("SimpleDateFormat")
    private void dumpLog(Throwable e) {
        @SuppressLint("SimpleDateFormat")
        String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis()) + ".log";
        File file = new File(LOG_PATH, filename);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
            pw.write("crash at: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "\n");
            e.printStackTrace(pw);
            pw.flush();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * report exception
     */
    private void report(Throwable e) {
        String detail = getDetails(e);
        // TODO: 2017/6/1 report details
    }

    /**
     * get crash details
     */
    @SuppressLint("SimpleDateFormat")
    private String getDetails(Throwable e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()))
                .append("version code: ").append(BuildConfig.VERSION_CODE).append('\n')
                .append("version name: ").append(BuildConfig.VERSION_NAME).append('\n')
                .append("flavor: ").append(BuildConfig.FLAVOR).append('\n')
                .append("os version: ").append(Build.VERSION.SDK_INT).append('\n')
                .append("vendor: ").append(Build.MODEL).append('\n')
                .append("brand: ").append(Build.BRAND).append('\n')
                .append("message: ").append(e.getMessage()).append('\n')
                .append("cause: ").append(e.getCause()).append('\n');
        return stringBuilder.toString();
    }
}