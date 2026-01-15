package com.ivan.github.perf;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;

import com.ivan.github.GitHub;
import com.ivan.github.R;
import com.ivan.github.core.perf.AbsCrashReporter;

import io.github.debug.CrashInfoViewActivity;

/**
 * com.ivan.github.core.perf.DefaultCrashReporter
 *
 * @author  Ivan on 2019-11-20
 * @version v0.1
 * @since   v1.0
 **/
public class DefaultCrashReporter extends AbsCrashReporter {

    @Override
    public void sendNotification(Throwable e) {
        Context context = GitHub.appComponent().applicationContext();
        Intent resultIntent = new Intent()
                .setAction("io.github.debug.crash_info")
                .addCategory("io.github.category.DEBUG")
                .putExtra(CrashInfoViewActivity.EXTRA_DETAIL, e);
        PendingIntent intent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_warning)
                .setTicker(context.getText(R.string.crash_ticker))
                .setContentTitle(context.getString(R.string.crash_content_title))
                .setContentText(context.getString(R.string.crash_content_text))
                .setContentIntent(intent)
                .setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }

    @Override
    public void report() {

    }
}
