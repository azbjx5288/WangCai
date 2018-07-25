package com.wangcai.lottery.component;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.wangcai.lottery.R;
import com.wangcai.lottery.util.ConvertUtils;

/**
 * 可拖动的imageview
 * Created by ACE-PC on 2017/8/01.
 */

public class FragImageView extends AppCompatImageView {
    private static final String TAG = FragImageView.class.getSimpleName();

    private int screenHeight = 0;
    private int screenWidth = 0;
//    private SharedPreferences sp;
    /**
     * 判断是否移动了，如果移动为true，否则为false--点击事件
     */
    private boolean isMove = false;

    private OnActivityClickListener onActivityClickListener;

    public FragImageView(Context context) {
        super(context);
    }

    public FragImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnActivityClickListener(OnActivityClickListener onActivityClickListener) {
        this.onActivityClickListener = onActivityClickListener;
    }

    /**
     * 设置拖动事件
     *
     * @param context
     */
    @SuppressWarnings({"unused", "deprecation"})
    public void setDrag(Activity context, ImageView imageView) {

        playAnimation(imageView);
        // playAnimation2(imageView, context);
//        sp = context.getSharedPreferences("icon_saul", Context.MODE_PRIVATE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - (getStatusBarHeight() + getNavigationBarHeight()) - 200;

//        int initTop = dm.heightPixels - (getStatusBarHeight() + getNavigationBarHeight()  + 48);
        /*int x = this.sp.getInt("lastx", 40);
        int y = this.sp.getInt("lasty", initTop);*/

        LayoutParams params = new LayoutParams(120, 120);
        params.topMargin = getStatusBarHeight() + getNavigationBarHeight()  + 48;
        params.rightMargin = 80;
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, -1);
        imageView.setLayoutParams(params);
        imageView.setOnTouchListener(new OnTouchListener() {
            int lastX, firstX;
            int lastY, firstY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 手指第一次触摸到屏幕
                        this.firstX = this.lastX = (int) event.getRawX();
                        this.firstY = this.lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:// 手指移动
                        int newX = (int) event.getRawX();
                        int newY = (int) event.getRawY();

                        int dx = newX - this.lastX;
                        int dy = newY - this.lastY;

                        // 计算出来控件原来的位置
                        int l = imageView.getLeft();
                        int r = imageView.getRight();
                        int t = imageView.getTop();
                        int b = imageView.getBottom();

                        int newt = t + dy;
                        int newb = b + dy;
                        int newl = l + dx;
                        int newr = r + dx;

                        if ((newl < 0) || (newt < 0) || (newr > screenWidth) || (newb > screenHeight)) {
                            break;
                        }
                        // 更新iv在屏幕的位置.
                        imageView.layout(newl, newt, newr, newb);
                        this.lastX = (int) event.getRawX();
                        this.lastY = (int) event.getRawY();
                        if(firstX!=lastX||firstY!=lastY){
                            isMove = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP: // 手指离开屏幕的一瞬间
                        int lastx = imageView.getLeft();
                        int lasty = imageView.getTop();
                        /*Editor editor = sp.edit();
                        editor.putInt("lastx", lastx);
                        editor.putInt("lasty", lasty);
                        editor.commit();*/
                        // 通过手指是否移动判断是点击还是移动imageview
                        if ((int) event.getRawX() == firstX && (int) event.getRawY() == firstY && !isMove) {
                            if (onActivityClickListener != null) {
                                onActivityClickListener.onActivityClick();
                            }
                        }
                        // 弹起时为false，新的一轮开始
                        isMove = false;
                        break;
                }
                return true;
            }
        });

    }

    private void playAnimation(ImageView imageView) {
        // 动画效果
        final int transDuration = 3000;
        AnimationSet set = new AnimationSet(false);
        set.setRepeatMode(Animation.RESTART);
        // 从下往上平移动画
        // TranslateAnimation translateAnimationToUp = new TranslateAnimation(0,
        // 0, 0, -30);
        // translateAnimationToUp.setInterpolator(new Interpolator() {
        // @Override
        // public float getInterpolation(float arg0) {
        // float ret = arg0 / (1.0f * transDuration / (transDuration));
        // return ret < 1 ? ret : 1;
        // }
        // });
        // translateAnimationToUp.setRepeatCount(50);
        // translateAnimationToUp.setDuration(transDuration);

        // 从上往下平移动画
        // TranslateAnimation translateAnimationToDown = new
        // TranslateAnimation(0,
        // 0, -30, 0);
        // translateAnimationToDown.setInterpolator(new Interpolator() {
        // @Override
        // public float getInterpolation(float arg0) {
        // float ret = arg0 / (1.0f * transDuration / (transDuration));
        // return ret < 1 ? ret : 1;
        // }
        // });
        // translateAnimationToDown.setRepeatCount(50);
        // translateAnimationToDown.setDuration(transDuration);

        // 从小到大的缩放动画
        Animation scaleAnimationToBig = new ScaleAnimation(1f, 1.5f, 1f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimationToBig.setDuration(3000);
        scaleAnimationToBig.setFillAfter(true);
        scaleAnimationToBig.setRepeatCount(1000000);

        // 从大到小的缩放动画
        Animation scaleAnimationToSmall = new ScaleAnimation(1.5f, 1f, 1.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationToSmall.setDuration(3000);
        scaleAnimationToSmall.setFillAfter(true);
        scaleAnimationToSmall.setRepeatCount(1000000);

        // 设置动画集
        // set.addAnimation(translateAnimationToUp);
        set.addAnimation(scaleAnimationToBig);
        // set.addAnimation(translateAnimationToDown);
        set.addAnimation(scaleAnimationToSmall);
        imageView.startAnimation(set);
    }

    //顶部status bar 高度
    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    //底部 navigation bar 高度
    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 选中监听器
     */
    public interface OnActivityClickListener {
        void onActivityClick();
    }
}
