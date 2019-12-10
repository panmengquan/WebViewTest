package com.laiyifen.capital.inhouse;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
