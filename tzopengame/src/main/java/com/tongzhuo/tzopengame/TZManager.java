package com.tongzhuo.tzopengame;

import com.tencent.smtt.sdk.QbSdk;
import com.tongzhuo.tzopengame.utils.TZLog;

import android.content.Context;


/**
 * Created by chaichuanfa on 2017/9/13.
 */

public class TZManager {

    public static void onInit(Context context, boolean debug) {
        try {
            QbSdk.initX5Environment(context, null);
        } catch (Exception e) {
        }
        TZLog.setDebug(debug);
    }
}