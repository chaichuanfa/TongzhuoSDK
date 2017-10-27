package com.tongzhuo.tzopengame.utils;

import android.util.Log;

/**
 * Created by chaichuanfa on 2017/10/27.
 */

public class TZLog {

    private static final String TAG = "TZOpenGameSDK";

    public static boolean isDebug;

    public static void d(String message) {
        if (isDebug) {
            Log.d(TAG, message);
        }
    }

    public static void e(String message) {
        if (isDebug) {
            Log.e(TAG, message);
        }
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }
}
