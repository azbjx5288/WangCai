package com.wangcai.lottery.component;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.wangcai.lottery.R;

/**
 * 弹窗，内部类，仅供{@link BasicPopup}调用
 * Created by ACE-PC on 2016/10/24.
 */
class PopupDialog {
    private static final String TAG = PopupDialog.class.getSimpleName();

    private Dialog dialog;
    private FrameLayout contentLayout;

    PopupDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        contentLayout = new FrameLayout(context);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentLayout.setFocusable(true);
        contentLayout.setFocusableInTouchMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            //contentLayout.setFitsSystemWindows(true);
        }
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
        dialog.setCancelable(true);//按返回键取消窗体
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.Animation_Popup);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setContentView(contentLayout);
        }
    }

    Context getContext() {
        return contentLayout.getContext();
    }

    void setAnimationStyle(@StyleRes int animRes) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(animRes);
        }
    }

    boolean isShowing() {
        return dialog.isShowing();
    }

    @CallSuper
    void show() {
        dialog.show();
    }

    @CallSuper
    void dismiss() {
        dialog.dismiss();
    }

    void setContentView(View view) {
        contentLayout.removeAllViews();
        contentLayout.addView(view);
    }

    View getContentView() {
        return contentLayout.getChildAt(0);
    }

    void setSize(int width, int height) {
        Log.i(TAG,String.format("will set popup width/height to: %s/%s", width, height));
        ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, height);
        } else {
            params.width = width;
            params.height = height;
        }
        contentLayout.setLayoutParams(params);
    }

    void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        dialog.setOnDismissListener(onDismissListener);
    }

    void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(onKeyListener);
    }

    Window getWindow() {
        return dialog.getWindow();
    }

    ViewGroup getRootView() {
        return contentLayout;
    }

}
