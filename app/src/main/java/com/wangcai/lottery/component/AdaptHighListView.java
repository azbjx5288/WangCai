package com.wangcai.lottery.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ACE-PC on 2016/11/18.
 */

public class AdaptHighListView  extends ListView {

    public AdaptHighListView(Context context) {
        super(context);
    }

    public AdaptHighListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptHighListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
