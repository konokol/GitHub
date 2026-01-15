package com.ivan.github;

import android.text.format.DateUtils;

import org.junit.Test;

/**
 * com.ivan.github
 * <p>
 *
 * @author Ivan J. Lee on 2023-02-04 21:38
 * @since v1.0
 */
public class DateTimeTest {

    @Test
    public void testDateFormatter() {
        System.out.println(DateUtils.getRelativeTimeSpanString(System.currentTimeMillis() , System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
    }
}
