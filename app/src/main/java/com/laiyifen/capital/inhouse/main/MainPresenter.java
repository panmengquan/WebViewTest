package com.laiyifen.capital.inhouse.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.laiyifen.capital.inhouse.MyApplication;
import com.laiyifen.capital.inhouse.utils.CommonUtils;
import com.laiyifen.capital.inhouse.utils.MyConstants;
import com.laiyifen.capital.inhouse.utils.ToastUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

public class MainPresenter  {
    private final Context        mContext;
    private       MainView       mainView;
    private       ProgressDialog updateDialog;

    public MainPresenter(MainView mainView){
        this.mainView = mainView;
        this.mContext = (Context) mainView;
        updateDialog = new ProgressDialog(mContext);
        updateDialog.setTitle("正在下载");
        //dialog.setMessage(version.getDes() + "");
        updateDialog.setCancelable(false);
        updateDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


    }

    public void startDownload(String url) {
        showDilog();
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "aiwu.apk") {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show("下载更新包失败");
                        hideDilog();
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        try {
                            // 没有挂载SD卡，无法下载文件
                            updateDialog.setTitle("下载完成");
                            hideDilog();
                            mainView.onDownloadApkSuccess();
                        } catch (Exception e) {
                            e.toString();
                            hideDilog();
                        }

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {

                        Log.d("pro==========",progress + "");
                        super.inProgress(progress, total, id);
                        updateDialog.setProgress((int) (100 * progress));
                    }
                });
    }
    public  void getUpdateVersionInfo() {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(MyConstants.UPDATE_VERSION_INFO)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                request.toString();

            }
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        JSONObject jsObject = new JSONObject(json);
                        String currentVersion = CommonUtils.getVersion(MyApplication.getContext());
                        String fwqVersion = jsObject.getString("versionShort");
                        if( -1 == CommonUtils.compareVersion(currentVersion,fwqVersion)){
                            String updateUrl = jsObject.getString("installUrl");
                            String updateInfo = jsObject.getString("changelog");
                            mainView.getVersionInfoSuccess(updateUrl,updateInfo);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showDilog(){
        if(updateDialog!=null){
            updateDialog.show();

        }
    }
    private void  hideDilog(){
        if(updateDialog != null){
            updateDialog.dismiss();
        }
    }
}
