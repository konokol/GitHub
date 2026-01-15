package com.ivan.github.common.util;

import android.text.format.DateUtils;

import java.util.Date;

import static android.text.format.DateUtils.FORMAT_ABBREV_ALL;
import static android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.FORMAT_ABBREV_TIME;
import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;
import static android.text.format.DateUtils.SECOND_IN_MILLIS;

/**
 * com.ivan.github.common.util.DateFormatUtils
 *
 * @author Ivan J. Lee on 2020-01-08 00:56
 * @version v0.1
 * @since v1.0
 **/
public class DateFormatUtils {

    public static CharSequence getTimeSpan(Date date) {
        if (date == null) {
            return "";
        } else {
            return DateUtils.getRelativeTimeSpanString(date.getTime(),
                    System.currentTimeMillis(),
                    SECOND_IN_MILLIS,
                    FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR | FORMAT_ABBREV_MONTH | FORMAT_ABBREV_TIME);
        }
    }
}
