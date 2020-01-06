package com.laiyifen.capital.inhouse;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {
   private static Context context;
   public static String jpushId = "";
    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送初始化
        this.context = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
    public static  Context getContext(){
        return context;
    }

    public static String getJpushId() {
        return jpushId;
    }

    public static void setJpushId(String jpushId) {
        MyApplication.jpushId = jpushId;
    }
}
