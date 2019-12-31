package com.laiyifen.capital.inhouse.utils;

import android.util.Log;

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
        map.put("userid",userid);
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

}
