package com.ivan.github.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

import com.ivan.github.R;

/**
 * com.ivan.github.common.util
 * <p>
 *
 * @author Ivan J. Lee on 2023-02-01 23:22
 * @since v1.0
 */
public class StatusBarUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        if (result <= 0) {
            result = context.getResources().getDimensionPixelOffset(R.dimen.status_bar_default_height);
        }
        return result;
    }

    public static void setStatusBarColorRes(Activity activity, @ColorRes int colorId) {
        Window window = activity.getWindow();
        window.setStatusBarColor(activity.getResources().getColor(colorId));
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        window.setStatusBarColor(color);
    }

}
