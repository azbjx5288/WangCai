package com.wangcai.lottery.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatRadioButton;

/**
 * Created by ACE-PC on 2017/2/21.
 */

public class MenuRadioButton extends AppCompatRadioButton {
    public MenuRadioButton(Context context) {
        super(context);
    }

    public MenuRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }

}