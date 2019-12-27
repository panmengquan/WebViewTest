package com.laiyifen.capital.inhouse.jiguang;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.laiyifen.capital.inhouse.MainActivity;
import com.laiyifen.capital.inhouse.MyApplication;
import com.laiyifen.capital.inhouse.bean.JPushMessageBean;
import com.laiyifen.capital.inhouse.utils.DabgeUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyJPushMessageReceiver  extends JPushMessageReceiver {
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Log.v("myTag","onNotifyMessageArrived");

        try {
            String notifiMessage =  notificationMessage.notificationExtras;
            JSONObject json = new JSONObject(notifiMessage);
            String badge = json.getString("badge");
            String url = json.getString("url");
            String title = notificationMessage.notificationTitle;
            DabgeUtil.SetDabge(context,Integer.parseInt(badge));
            if(!"".equals(MyApplication.getUserId())) {
                JPushMessageBean jPushMessageBean = new JPushMessageBean();
                jPushMessageBean.setTitle(title);
                jPushMessageBean.setUrl(url);
                jPushMessageBean.setBadge(badge);
                EventBus.getDefault().post(jPushMessageBean);
            }else {
                DabgeUtil.SetDabge(context,Integer.parseInt(badge));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        DabgeUtil.SetDabge(context,0);
        try {
            String notifiMessage =  notificationMessage.notificationExtras;
            JSONObject json = new JSONObject(notifiMessage);
            String badge = json.getString("badge");
            String url = json.getString("url");
            String title = notificationMessage.notificationTitle;

            //MyPreferencesUtils.getString(MyConstants.USER_ID)
            if("".equals(MyApplication.getUserId())) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(intent);
            }else {
                Intent dataIntent = new Intent(context, MainActivity.class);
                dataIntent.putExtra("myurl",url);
                dataIntent.putExtra("badge",badge);
                dataIntent.putExtra("title",title);
                dataIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(dataIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("myTag","onNotifyMessageOpened");
    }
}
