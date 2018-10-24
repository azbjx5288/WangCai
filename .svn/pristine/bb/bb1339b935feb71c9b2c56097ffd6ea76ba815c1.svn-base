package com.wangcai.lottery.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Sakura on 2017/12/12.
 */

public class ClipboardUtils
{
    public static void copy(Activity activity, String content, String label)
    {
        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null)
        {
            ClipData clipData = ClipData.newPlainText(label, content);
            clipboardManager.setPrimaryClip(clipData);
            ToastUtils.showShortToast(activity, "已复制到剪贴板");
        }
    }
}
