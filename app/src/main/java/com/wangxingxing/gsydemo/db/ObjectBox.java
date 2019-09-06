package com.wangxingxing.gsydemo.db;


import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.wangxingxing.gsydemo.BuildConfig;
import com.wangxingxing.gsydemo.db.table.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();

        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(context);
            LogUtils.i("Started: " + started);
        }
    }

    public static BoxStore get() {
        return boxStore;
    }
}

