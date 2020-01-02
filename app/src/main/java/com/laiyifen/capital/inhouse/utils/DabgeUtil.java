package com.laiyifen.capital.inhouse.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.laiyifen.capital.inhouse.MainActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;
import me.leolin.shortcutbadger.ShortcutBadger;

public class DabgeUtil {
    private static final String ID = "PUSH_NOTIFY_HT_ID";
    private static final String NAME = "PUSH_NOTIFY_HT_NAME";
    public static void SetMiDabge(Context context, int mCount) {
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.putExtra("testjson", "hahahaha");
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true);
        try {
            Notification notification = builder.build();
            Field field = notification.getClass().getDeclaredField("extraNotification");

            Object extraNotification = field.get(notification);

            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);

            method.invoke(extraNotification, mCount);
            mNotificationManager.notify(0, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("packageName", "com.hengtong.app.release");
//        intent.putExtra("className",  LoginActivity.class.getName());
//        intent.putExtra("notificationNum", mCount);
//        context.sendBroadcast(intent);

    }


    public static void SetDabge(Context context, int mCount) {
        try{
            if (RomUtil.isMiui()) {
                if (Build.VERSION.SDK_INT>=26){
                    createNotificationChannel(context);
                    SetMiDabge(context, mCount);
                }else{
                    Log.v("myTag","mCornerCount=="+mCount);
                    Log.v("myTag","MANUFACTURER="+Build.MANUFACTURER);
                    SetMiDabge(context, mCount);
                }
            } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
                setBadgeOfSony(context, mCount);
            } else if(Build.MANUFACTURER.toLowerCase().contains("samsung") ||Build.MANUFACTURER.toLowerCase().contains("lg")){
                Log.v("myTag","MANUFACTURER="+Build.MANUFACTURER);
                setBadgeOfSumsung(context, mCount);
            } else {
                Log.v("myTag","count="+mCount);
                Log.v("myTag","MANUFACTURER="+Build.MANUFACTURER);
                ShortcutBadger.applyCount(context, mCount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotificationChannel( Context context){
        Log.v("myTag","createNotificationChannel");
        NotificationChannel channel = null;
        channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(channel);
    }
    /**
     * 设置三星的Badge
     *
     * @param context context
     * @param count   count
     */
    private static void setBadgeOfSumsung(Context context, int count) {
        // 获取你当前的应用
        String launcherClassName = MainActivity.class.getName();
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }
    private static void setBadgeOfSony(Context context, int count) {
        String launcherClassName = MainActivity.class.getName();
        if (launcherClassName == null) {
            return;
        }
        boolean isShow = true;
        if (count == 0) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());//包名
        context.sendBroadcast(localIntent);
    }

}
