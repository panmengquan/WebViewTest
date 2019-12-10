package com.laiyifen.capital.inhouse.jiguang;

import android.content.Context;

import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class CopassPushMessageReceiver extends JPushMessageReceiver {
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
    }
}
