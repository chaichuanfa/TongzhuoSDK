package com.github.felix;

import com.tongzhuo.tzopengame.TZManager;

import android.app.Application;

/**
 * Created by chaichuanfa on 2017/10/27.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TZManager.onInit(this, BuildConfig.DEBUG);
    }
}
