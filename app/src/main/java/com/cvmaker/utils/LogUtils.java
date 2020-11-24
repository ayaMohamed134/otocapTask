package com.cvmaker.utils;

import android.util.Log;

/**
 * Created by Aya Mohamed on 11/13/2019.
 */
public final class LogUtils {
    public static String TAG = "Const";
    public static boolean LOGGING_ENABLED = true;

    public static void LOGD(String message) {
        if (LOGGING_ENABLED) {
            Log.d(TAG, message);
        }
    }

    public static void LOGD(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.d(TAG, message, cause);
        }
    }

    public static void LOGV(String message) {
        if (LOGGING_ENABLED) {
            Log.v(TAG, message);
        }
    }

    public static void LOGV(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.v(TAG, message, cause);
        }
    }

    public static void LOGI(String message) {
        if (LOGGING_ENABLED) {
            Log.i(TAG, message);
        }
    }

    public static void LOGI(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.i(TAG, message, cause);
        }
    }

    public static void LOGW(String message) {
        if (LOGGING_ENABLED) {
            Log.w(TAG, message);
        }
    }

    public static void LOGW(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.w(TAG, message, cause);
        }
    }

    public static void LOGE(String message) {
        if (LOGGING_ENABLED) {
            Log.e(TAG, message);
        }
    }

    public static void LOGE(String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.e(TAG, message, cause);
        }
    }
}
