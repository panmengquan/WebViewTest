package com.laiyifen.capital.inhouse.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.laiyifen.capital.inhouse.MyApplication;

/**
 * Created by wisn on 2017/10/5.
 */

public class ToastUtils {
    private static Toast toast;

    public static void show(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showGravity(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static void show(int id) {
        show(MyApplication.getContext().getString(id));
    }


}
