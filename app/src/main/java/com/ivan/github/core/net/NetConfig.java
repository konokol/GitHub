package com.ivan.github.core.net;

import com.ivan.github.BuildConfig;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * net work config
 *
 * @author  Ivan on 2018-11-21 23:18.
 * @version v0.1
 * @since   v0.1.0
 */
public class NetConfig {

    private static final NetConfig DEFAULT_CONFIG = new NetConfig();

    private String url = "https://api.github.com";
    private int timeout = 10_000;
    private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BASIC;

    private NetConfig(){
    }

    private NetConfig(Builder builder) {
        url = builder.url;
        timeout = builder.timeout;
        logLevel = builder.logLevel;
    }

    public static NetConfig defaultConfig() {
        if (BuildConfig.DEBUG) {
            DEFAULT_CONFIG.logLevel = HttpLoggingInterceptor.Level.BODY;
        } else {
            DEFAULT_CONFIG.logLevel = HttpLoggingInterceptor.Level.NONE;
        }
        return DEFAULT_CONFIG;
    }

    public String getUrl() {
        return url;
    }

    public int getTimeout() {
        return timeout;
    }

    public HttpLoggingInterceptor.Level getLogLevel() {
        return logLevel;
    }

    public static final class Builder {
        private String url;
        private int timeout;
        private HttpLoggingInterceptor.Level logLevel;

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder timeout(int val) {
            timeout = val;
            return this;
        }

        public Builder logLevel(HttpLoggingInterceptor.Level val) {
            logLevel = val;
            return this;
        }

        public NetConfig build() {
            return new NetConfig(this);
        }
    }
}
