package com.laiyifen.capital.inhouse.jiguang;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.laiyifen.capital.inhouse.MainActivity;
import com.laiyifen.capital.inhouse.bean.JPushMessageBean;
import com.laiyifen.capital.inhouse.utils.DabgeUtil;
import com.laiyifen.capital.inhouse.utils.MyConstants;
import com.laiyifen.capital.inhouse.utils.MyPreferencesUtils;

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
            //通知到达,设置角标
            DabgeUtil.SetDabge(context,Integer.parseInt(badge));
            //有账号，正在浏览,弹出对话框
            if(!"".equals(MyPreferencesUtils.getString(MyConstants.USER_ID))) {
                JPushMessageBean jPushMessageBean = new JPushMessageBean();
                jPushMessageBean.setTitle(title);
                jPushMessageBean.setUrl(url);
                jPushMessageBean.setBadge(badge);
                EventBus.getDefault().post(jPushMessageBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);


        try {
            //点击时，角标设置为0
            DabgeUtil.SetDabge(context,0);

            String notifiMessage =  notificationMessage.notificationExtras;
            JSONObject json = new JSONObject(notifiMessage);
            String badge = json.getString("badge");
            String url = json.getString("url");
            String title = notificationMessage.notificationTitle;

            //点击通知，如果用户未登录，则跳转登录页面(不带参数);否则跳转登录页面(带参数)
            if("".equals(MyPreferencesUtils.getString(MyConstants.USER_ID))) {
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
    }
}
