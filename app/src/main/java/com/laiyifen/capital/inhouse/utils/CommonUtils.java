package com.laiyifen.capital.inhouse.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.laiyifen.capital.inhouse.MyApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.laiyifen.capital.inhouse.MainActivity.serviceAddress;

public class CommonUtils {
    public static void upLoadJpushId(String userid,String jpushRegistId,String status) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String url =serviceAddress+ "user/updateJpushDevice";
        OkHttpClient client = new OkHttpClient();
        Map map = new HashMap();
        map.put("udid","1111111");
        map.put("userid",MyPreferencesUtils.getString(MyConstants.USER_ID));
        map.put("jpushId",jpushRegistId);
        map.put("osType","0");
        map.put("status",status);
        String json = com.alibaba.fastjson.JSON.toJSONString(map,true);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                request.toString();

            }
            @Override
            public void onResponse(Response response) throws IOException {

                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.i("HomeFragment", "json=" + json);

                }
            }
        });
    }
 public  static void getOpenNotify(){
     try {
         // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
         Intent intent = new Intent();
         intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
         //这种方案适用于 API 26, 即8.0（含8.0）以上可以用

         if (Build.VERSION.SDK_INT >= 26) {
             intent.putExtra(Settings.EXTRA_APP_PACKAGE, MyApplication.getContext().getPackageName());
             intent.putExtra(Settings.EXTRA_CHANNEL_ID, MyApplication.getContext().getApplicationInfo().uid);
         }
         //android 5.0-7.0
         if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 26) {
             //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
             intent.putExtra("app_package", MyApplication.getContext().getPackageName());
             intent.putExtra("app_uid", MyApplication.getContext().getApplicationInfo().uid);
         }

         // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
         //  if ("MI 6".equals(Build.MODEL)) {
         //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
         //      Uri uri = Uri.fromParts("package", getPackageName(), null);
         //      intent.setData(uri);
         //      // intent.setAction("com.android.settings/.SubSettings");
         //  }
         MyApplication.getContext().startActivity(intent);
     } catch (Exception e) {
         e.printStackTrace();
         // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
         Intent intent = new Intent();

         //下面这种方案是直接跳转到当前应用的设置界面。
         //https://blog.csdn.net/ysy950803/article/details/71910806
         intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
         Uri uri = Uri.fromParts("package", MyApplication.getContext().getPackageName(), null);
         intent.setData(uri);
         MyApplication.getContext().startActivity(intent);
     }
    }
}
