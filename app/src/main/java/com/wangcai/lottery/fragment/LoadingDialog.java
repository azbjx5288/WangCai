package com.wangcai.lottery.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
/**
 * Created by ACE-PC on 2017/1/6.
 */

public class LoadingDialog extends Dialog {

    private static LoadingDialog custom;

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static LoadingDialog createDialog(Context context) {
        LoadingDialog custom = new LoadingDialog(context, R.style.CustomProgressDialog);
        custom.setContentView(R.layout.loading_layout);
        custom.getWindow().getAttributes().gravity = Gravity.CENTER;
        return custom;
    }

    public static LoadingDialog show(Context context, String message, boolean indeterminate, boolean cancleable, OnCancelListener cancleListener) {
        custom = createDialog(context);
        // 设置提示信息
        if (!(message == null || "".equals(message))) {
            TextView tip = (TextView) custom.findViewById(R.id.loading_msg);
            tip.setText(message);
        }
        // 点击回退按键是否可以释放对话框
        custom.setCancelable(cancleable);
        // 设置点击非窗口部分释放对话框
        custom.setCanceledOnTouchOutside(indeterminate);
        // 设置回退监听器
        if (cancleListener != null) {
            custom.setOnCancelListener(cancleListener);
        }
        return custom;
    }

    public static LoadingDialog show(Context context, String message, boolean indeterminate, boolean cancleabl) {
        return show(context, message, indeterminate, cancleabl, null);
    }

    public static LoadingDialog show(Context context, String message) {
        return show(context, message, false, true, null);
    }

    public static LoadingDialog show(Context context) {
        return show(context, null, false, true, null);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

}

