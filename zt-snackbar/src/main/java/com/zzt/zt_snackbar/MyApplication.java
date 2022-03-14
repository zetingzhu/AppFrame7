package com.zzt.zt_snackbar;

import android.app.Application;

import com.trade.utilcode.util.Utils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
