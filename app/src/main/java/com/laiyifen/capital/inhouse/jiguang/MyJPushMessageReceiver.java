package com.laiyifen.capital.inhouse.jiguang;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyJPushMessageReceiver  extends JPushMessageReceiver {
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Log.v("myTag","onNotifyMessageArrived");
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        Log.v("myTag","onNotifyMessageOpened");
    }
}
