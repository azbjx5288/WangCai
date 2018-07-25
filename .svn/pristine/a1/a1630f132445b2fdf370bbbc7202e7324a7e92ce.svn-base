package com.wangcai.lottery.component;

import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;


public class CycleViewPagerHandler {
    /**
     * 防止handler对activity有隐式引用，匿名内部类不会对外部类有引用
     */
    public static class UnleakHandler extends Handler {
        private final WeakReference<Context> context;

        public UnleakHandler(Context context) {
            this.context = new WeakReference<Context>(context);
        }
    }
}