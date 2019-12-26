package com.laiyifen.capital.inhouse.widgets;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * @author SevenCheng
 */
public class IOSDialog extends Dialog {
    private Context  context;
    private int      resId;

    public IOSDialog(Context context, int resLayout) {
        this(context, 0, 0);
    }

    public IOSDialog(Context context, int themeResId, int resLayout) {
        super(context, themeResId);
        this.context = context;
        this.resId = resLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(resId);
    }
}



