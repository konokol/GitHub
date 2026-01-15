package com.ivan.github.common.util;

import android.annotation.SuppressLint;
import android.os.Build;

import com.ivan.github.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

/**
 * Some common tools to handle Exception
 *
 * @author  Ivan on 2017/6/8 11:46.
 * @version v0.1
 * @since   v1.0
 */

public final class ExceptionUtils {

    /**
     * get the exception and phone information
     *
     * @param throwable the exception
     * @return the detail information
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCrashInfo(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String detail = writer.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()))
                .append("version code: ").append(BuildConfig.VERSION_CODE).append('\n')
                .append("version name: ").append(BuildConfig.VERSION_NAME).append('\n')
                .append("flavor: ").append(BuildConfig.BUILD_TYPE).append('\n')
                .append("os version: ").append(Build.VERSION.SDK_INT).append('\n')
                .append("vendor: ").append(Build.MODEL).append('\n')
                .append("brand: ").append(Build.BRAND).append('\n')
                .append("detail: ").append(detail).append('\n');
        return stringBuilder.toString();
    }

    /**
     * get the exception stack
     * @param throwable the exception
     * @return the exception stack information
     */
    public static String getExceptionStack(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }
}
