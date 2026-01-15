package com.github.log;

import org.junit.Test;

/**
 * com.github.log.LoganTestError
 *
 * @author Ivan J. Lee on 2020-10-31 10:58
 * @version v0.1
 * @since v1.0
 **/
public class LoganTestError {

    @Test
    public void testLoganNotInit() {
        Logan.init();
        Logan.e("TAG", "23333");
    }
}
