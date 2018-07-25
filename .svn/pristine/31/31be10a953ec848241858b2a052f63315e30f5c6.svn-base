package com.wangcai.lottery.component;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
* Created by ACE-PC on 2017/07/11.
* @author ACE
*/

public class DisInterceptNestedScrollView extends NestedScrollView {
    public DisInterceptNestedScrollView(Context context) {
        super(context);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }

}
