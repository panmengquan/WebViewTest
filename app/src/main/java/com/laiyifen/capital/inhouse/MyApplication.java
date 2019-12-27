package com.laiyifen.capital.inhouse;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
   private static Context context;
   public static String userId = "";
    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送初始化
        this.context = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    public static  Context getContext(){
        return context;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        MyApplication.userId = userId;
    }
}
