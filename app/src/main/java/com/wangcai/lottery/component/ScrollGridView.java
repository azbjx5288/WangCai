package com.wangcai.lottery.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Sakura on 2016/11/7.
 */

public class ScrollGridView extends GridView
{
    public ScrollGridView(Context context)
    {
        super(context);
    }

    public ScrollGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}