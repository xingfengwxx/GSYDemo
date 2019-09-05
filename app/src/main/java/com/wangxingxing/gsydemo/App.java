package com.wangxingxing.gsydemo;

import android.app.Application;
import android.util.Log;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

public class App extends Application {

    public static final String TAG = "wxx";

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        LogUtils.getConfig().setGlobalTag(TAG);
        CrashUtils.init((crashInfo, e) -> Log.e(TAG, crashInfo));
    }
}
