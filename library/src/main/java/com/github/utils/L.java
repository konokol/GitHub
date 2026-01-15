package com.github.utils;

import android.text.TextUtils;
import android.util.Log;

import com.github.design.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A tool class for Logcat
 *
 * @author      Ivan at 2016-12-11  17:13
 * @version     v1.0
 * @since       v1.0
 */

@Deprecated
public class L {

    private static final String DEFAULT_TAG = BuildConfig.LIBRARY_PACKAGE_NAME;

    public static final int VERBOSE = 2;
    public static final int DEBUG   = 3;
    public static final int INFO    = 4;
    public static final int WARN    = 5;
    public static final int ERROR   = 6;
    public static final int ASSERT  = 7;

    private L() {
    }

    private static boolean sLogVerboseEnable = false;
    private static boolean sLogDebugEnable = false;
    private static boolean sLogInfoEnable = false;
    private static boolean sLogWarnEnable = false;
    private static boolean sLogErrorEnable = false;
    private static boolean sLogAssertEnable = false;

    public static void setDebugLog(boolean debug, int level) {
        if (!debug) {
            return;
        }
        if (level < VERBOSE || level > ASSERT) {
            System.out.println("check your parameter please");
            return;
        }
        if (level == ASSERT) {
            sLogAssertEnable = true;
        } else if (level == ERROR) {
            sLogAssertEnable = true;
            sLogErrorEnable = true;
        } else if (level == WARN) {
            sLogAssertEnable = true;
            sLogErrorEnable = true;
            sLogWarnEnable = true;
        } else if (level == INFO) {
            sLogAssertEnable = true;
            sLogErrorEnable = true;
            sLogWarnEnable = true;
            sLogInfoEnable = true;
        }else if (level == DEBUG) {
            sLogAssertEnable = true;
            sLogErrorEnable = true;
            sLogWarnEnable = true;
            sLogInfoEnable = true;
            sLogDebugEnable = true;
        } if (level == VERBOSE){
            sLogAssertEnable = true;
            sLogErrorEnable = true;
            sLogWarnEnable = true;
            sLogInfoEnable = true;
            sLogDebugEnable = true;
            sLogVerboseEnable = true;
        }
    }

    public static int v(String tag, String msg) {
        if (sLogVerboseEnable) {
            return Log.v(tag, msg);
        }
        return VERBOSE;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (sLogVerboseEnable) {
            return Log.v(tag, msg, tr);
        }
        return VERBOSE;
    }

    public static int d(String tag, String msg) {
        if (sLogDebugEnable) {
            return Log.d(tag, msg);
        }
        return DEBUG;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (sLogDebugEnable) {
            return Log.d(tag, msg, tr);
        }
        return DEBUG;
    }

    public static void d(String tag, String msg, Object ... args) {
        d(tag, formatString(msg, args));
    }

    public static int i(String tag, String msg) {
        if (sLogInfoEnable) {
            return Log.i(tag, msg);
        }
        return INFO;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (sLogInfoEnable) {
            return Log.i(tag, msg, tr);
        }
        return INFO;
    }

    public static void i(String tag, String msg, Object ... args) {
        i(tag, formatString(msg, args));
    }

    public static int w(String tag, String msg) {
        if (sLogWarnEnable) {
            return Log.w(tag, msg);
        }
        return INFO;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (sLogWarnEnable) {
            return Log.w(tag, msg, tr);
        }
        return WARN;
    }

    public static int w(String tag, Throwable tr) {
        if (sLogWarnEnable) {
            return Log.w(tag, tr);
        }
        return WARN;
    }

    public static void w(String tag, String msg, Object ... args) {
        w(tag, formatString(msg, args));
    }

    public static int e(String tag, String msg) {
        if (sLogErrorEnable) {
            return Log.e(tag, msg);
        }
        return ERROR;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (sLogErrorEnable) {
            return Log.e(tag, msg, tr);
        }
        return ERROR;
    }

    public static void e(String tag, String msg, Object ... args) {
        e(tag, formatString(msg, args));
    }

    public static int e(String tag, Throwable tr) {
        if (sLogErrorEnable) {
            Log.e(DEFAULT_TAG, Log.getStackTraceString(tr));
        }
        return ERROR;
    }

    public static int e(Throwable tr) {
        e(DEFAULT_TAG, tr);
        return ERROR;
    }

    public static int wtf(String tag, String msg) {
        if (sLogAssertEnable) {
            return Log.wtf(tag, msg);
        }
        return ASSERT;
    }

    public static int wtf(String tag, Throwable tr) {
        if (sLogAssertEnable) {
            return Log.wtf(tag, tr);
        }
        return ASSERT;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (sLogAssertEnable) {
            return Log.wtf(tag, msg, tr);
        }
        return ASSERT;
    }

    public static void json(String tag, String jsonStr) {
        if (sLogErrorEnable) {
            if (TextUtils.isEmpty(jsonStr)) {
                return;
            }
            try {
                if (jsonStr.startsWith("{")) {
                    json(tag, new JSONObject(jsonStr));
                } else if (jsonStr.startsWith("[")) {
                    json(tag, new JSONArray(jsonStr));
                } else {
                    e(tag, jsonStr);
                }
            } catch (Exception e) {
                printStackTrace(e);
            }
        }
    }

    public static void json(String tag, JSONObject jsonObject) {
        if (sLogErrorEnable) {
            try {
                String formattedJsonObject = jsonObject.toString(4);
                if (formattedJsonObject.length() > 2048) {
                    for (int i = 0; i < formattedJsonObject.length(); i += 2048) {
                        e(tag, formattedJsonObject.substring(i, Math.min(i + 2048, formattedJsonObject.length())));
                    }
                } else {
                    e(tag, formattedJsonObject);
                }
            } catch (Exception e) {
                printStackTrace(e);
            }
        }
    }

    public static void json(String tag, JSONArray jsonArray) {
        if (sLogErrorEnable) {
            try {
                String formattedJsonObject = jsonArray.toString(4);
                if (formattedJsonObject.length() > 2048) {
                    for (int i = 0; i < formattedJsonObject.length(); i += 2048) {
                        e(tag, formattedJsonObject.substring(i, Math.min(i + 2048, formattedJsonObject.length())));
                    }
                } else {
                    e(tag, formattedJsonObject);
                }
            } catch (Exception e) {
                printStackTrace(e);
            }
        }
    }

    public static void printStackTrace(Exception e) {
        if (sLogErrorEnable) {
            e.printStackTrace();
        }
    }

    static String formatString(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }
}
