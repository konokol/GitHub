package com.github.log;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * com.github.log.LoganTest
 *
 * @author Ivan J. Lee on 2020-10-29 23:43
 * @version v0.1
 * @since v1.0
 **/
public class LoganTest {

    @Before
    public void setup() {
        Logan.init(new LogcatFactory(), new SystemLogFactory());
    }

    @Test
    public void testVerbose() {
        Logan.v("testVerbose", "verbose message");
        Logan.v("testVerbose", "priority={0}", Thread.currentThread().getPriority());
        UnsupportedOperationException exception = new UnsupportedOperationException("test verbose", new IOException("io exception"));
        Logan.v("testVerbose", "exception ", exception);
    }

    @Test
    public void testDebug() {
        Logan.d("testDebug", "debug message");
        Logan.d("testDebug", "name={0}", Thread.currentThread().getName());
        UnsupportedOperationException exception = new UnsupportedOperationException("test debug", new IOException("io exception"));
        Logan.d("testDebug", "exception ", exception);
    }

    @Test
    public void testInfo() {
        Logan.i("testInfo", "{\"client_id\":\"08d9cad09d2e1745edb4\",\"client_secret\":\"3943633750719013ae8208f6f001951566328218\",\"code\":\"558f9ac81d8b9ded5e51\",\"state\":\"16835062-623f-4256-b9e1-e9de85dc0894\"}");
        Logan.i("testInfo", "info message");
        Logan.i("testInfo", "thread state={0}", Thread.currentThread().getState().name());
        UnsupportedOperationException exception = new UnsupportedOperationException("for test info ", new IOException("io exception"));
        Logan.i("testInfo", "exception ", exception);
    }

    @Test
    public void testWarn() {
        Logan.w("testWarn", "warn message");
        Logan.w("testWarn", "thread state={0}", Thread.currentThread().getState().name());
        UnsupportedOperationException exception = new UnsupportedOperationException("for test warn", new IOException("io exception"));
        Logan.w("testWarn", "exception ", exception);
    }

    @Test
    public void testError() {
        Logan.e("testError", "error message");
        Logan.e("testError", "thread state={0}", Thread.currentThread().getState().name());
        UnsupportedOperationException exception = new UnsupportedOperationException("for test error", new IOException("io exception"));
        Logan.e("testError", "exception ", exception);
    }

    @Test
    public void testAssert() {
        Logan.wtf("testAssert", "assert message");
        Logan.wtf("testAssert", "thread state={0}", Thread.currentThread().getState().name());
        UnsupportedOperationException exception = new UnsupportedOperationException("for test assert", new IOException("io exception"));
        Logan.wtf("testAssert", "exception ", exception);
    }
}
