package com.wangcai.lottery.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.support.v7.widget.AppCompatImageView;

import com.wangcai.lottery.material.SaveImageUtils;
import com.wangcai.lottery.pattern.ClipImageUtils;

/**
 * Created by ACE-PC on 2017/3/22.
 */

public class ClipImageView extends AppCompatImageView {
    private float currX;
    private float currY;
    private float dX;
    private float dY;
    private float oldX;
    private float oldY;
    private int maxX;
    private int maxY;

    private final float density = getResources().getDisplayMetrics().density; // 密度
    private float mClipFrameBorderWidth = 1 * density; // 剪裁框的边框宽度
    private int mClipFrameWidth = 500; // 默认的剪裁框的宽度
    private int mClipFrameHeight = 500; // 默认的剪裁框的高度
    private int imWidth; // ClipImageView的宽度
    private int imHeight; // ClipImageView的高度
    private boolean showClipFrame = true; // 是否显示剪裁框
    private String mClipFrameColor = "#FFFFFFFF"; // 剪裁框的边框颜色
    private String mShadowColor = "#99000000"; // 阴影颜色

    private Paint mShadowPaint;
    private Paint mClipFramePaint;
    /** 剪裁框外的阴影 */
    private Rect mRectLeftShadow;
    private Rect mRectRightShadow;
    private Rect mRectTopShadow;
    private Rect mRectBottomShadow;
    /** 剪裁框 */
    private Rect mClipFrame;
    /** 设置在ImageView中的Bitmap */
    private Bitmap source;

    private OnCutListener onCutListener;

    public ClipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdjustViewBounds(true);

        initPaint();
        initRect();

        post(new Runnable() {
            @Override
            public void run() {
                imWidth = getWidth();
                imHeight = getHeight();

                resolveClipFrameSize(); // 必要步骤，校正剪裁框大小，且必须在计算maxX和maxY之前
                maxX = imWidth - imWidth;
                maxY = imHeight - imHeight;
                currX = (float) maxX / 2;
                currY = (float) maxY / 2;
                // 设置剪裁框显示在图片正中间
                setShadowRegion(currX, currY);
                setClipFramePosition(currX, currY);
            }
        });
    }

    private void initPaint() {
        mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.parseColor(mShadowColor));

        mClipFramePaint = new Paint();
        mClipFramePaint.setStyle(Paint.Style.STROKE); // 设置为空心
        mClipFramePaint.setStrokeWidth(mClipFrameBorderWidth); // 设置边框宽度
        setClipFrameColor(mClipFrameColor); // 设置颜色
    }

    private void initRect() {
        /** 阴影区域 */
        mRectLeftShadow = new Rect();
        mRectTopShadow = new Rect();
        mRectRightShadow = new Rect();
        mRectBottomShadow = new Rect();
        // 剪裁框
        mClipFrame = new Rect();
    }

    public void setOnCutListener(OnCutListener onCutListener) {
        this.onCutListener = onCutListener;
    }

    /**
     * 设置剪裁框的位置
     * @param x
     * @param y
     */
    private void setClipFramePosition(float x, float y) {
        int dx = (int) (mClipFrameBorderWidth / 2);
        mClipFrame.set((int) x + dx, (int) y + dx, (int) x + mClipFrameWidth - dx, (int) y + mClipFrameHeight - dx);
    }

    /**
     * 设置剪裁框外的阴影
     * @param x 剪裁框当前的左上角X坐标
     * @param y 剪裁框当前的左上角Y坐标
     */
    private void setShadowRegion(float x, float y) {
        mRectLeftShadow.set(0, 0, (int) x, imHeight);
        mRectTopShadow.set((int) x, 0, (int) x + mClipFrameWidth, (int) y);
        mRectRightShadow.set((int) x + mClipFrameWidth, 0, imWidth, imHeight);
        mRectBottomShadow.set((int) x, (int) y + mClipFrameHeight, (int) x + mClipFrameWidth, imHeight);
    }

    /**
     * 方法已对resId指向的图片进行压缩处理， 用此方法设置图片，剪裁后的相片质量相对 较差，但可简单避免Bitmap的OOM；如需
     * 对原图进行裁剪，请直接调用setImageResource（）
     * @param resId
     */
    public void setImageResourceSecure(int resId) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
        setImageBitmap(processBitmap(bm));
    }

    /**
     * 方法已对drawable指向的图片进行压缩处理， 用此方法设置图片，剪裁后的相片质量相对 较差，但可简单避免Bitmap的OOM；如需
     * 对原图进行裁剪，请直接调用setImageDrawable（）
     * @param drawable
     */
    public void setImageDrawableSecure(Drawable drawable) {
        if (drawable == null)
            throw new IllegalArgumentException("drawable 不能为null");
        BitmapDrawable bd = (BitmapDrawable) drawable;
        setImageBitmap(processBitmap(bd.getBitmap()));
    }

    /**
     * 方法已对bm指向的图片进行压缩处理， 用此方法设置图片，剪裁后的相片质量相对 较差，但可简单避免Bitmap的OOM；如需
     * 对原图进行裁剪，请直接调用setImageBitmap（）
     * @param bm
     */
    public void setImageBitmapSecure(Bitmap bm) {
        setImageBitmap(processBitmap(bm));
    }

    /**
     * 对Bitmap进行简单的处理，适当地压缩图片大小
     * @param bm
     * @return
     */
    private Bitmap processBitmap(Bitmap bm) {
        if (bm == null)
            throw new IllegalArgumentException("bitmap 不能为null");

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int bmWidth = bm.getWidth();
        int bmHeight = bm.getHeight();
        if (bmWidth < screenWidth || bmHeight < screenHeight)
            return bm;

        float scale = (float) screenWidth / bmWidth;
        Bitmap bitmap = Bitmap.createScaledBitmap(bm, screenWidth,
                (int) (bmHeight * scale), true);
        bm.recycle();
        return bitmap;
    }

    /**
     * 获取设置在ClipImageView中的Bitmap
     * @return
     */
    public Bitmap getSourceBitmap() {
        if (source != null)
            return source;

        Drawable d = getDrawable();
        if (d == null) {
            return null;
        }

        BitmapDrawable bd = (BitmapDrawable) d;
        source = bd.getBitmap();
        return source;
    }

    /**
     * 获取ImageView对原图的缩放比例
     * @return
     */
    public float getScale() {
        if (getSourceBitmap() == null)
            return 0f;
        int bmWidth = source.getWidth();
        int bmHeight = source.getHeight();
        float scale = Math.min((float) bmWidth / imWidth, (float) bmHeight / imHeight);
        return scale;
    }

    /**
     * 获取剪裁好的bitmap
     * @return
     */
    public Bitmap getClippedBitmap() {
        float scale = getScale();
        if (scale > 0 && source != null)// 剪裁图片
            return ClipImageUtils.clipImage(source, (int) currX, (int) currY, (int) mClipFrameWidth, (int) mClipFrameHeight, scale);
        return null;
    }

    /**
     * 设置剪裁框边框的颜色，支持#RRGGBB #AARRGGBB 'red', 'blue', 'green', 'black', 'white',
     * 'gray', 'cyan', 'magenta', 'yellow', 'lightgray', 'darkgray', 'grey',
     * 'lightgrey', 'darkgrey', 'aqua', 'fuschia', 'lime', 'maroon', 'navy',
     * 'olive', 'purple', 'silver', 'teal'
     * @param color
     */
    public void setClipFrameColor(String color) {
        mClipFramePaint.setColor(Color.parseColor(color));
    }

    /**
     * 设置剪裁框的宽度和高度
     * @param width  宽度
     * @param height 高度
     */
    public void setClipFrameSize(int width, int height) {
        mClipFrameWidth = width;
        mClipFrameHeight = height;

        maxX = imWidth -mClipFrameWidth;
        maxY = imHeight -mClipFrameHeight;
    }

    /**
     * 校正裁剪框的宽高，使其不能超过View的宽高
     */
    private void resolveClipFrameSize() {
        mClipFrameWidth = mClipFrameWidth >= imWidth ? imWidth : mClipFrameWidth;
        mClipFrameHeight = mClipFrameHeight >= imHeight ? imHeight : mClipFrameHeight;
    }

    /**
     * 设置剪裁框的边框宽度
     * @param w
     */
    public void setClipFrameBorderWidth(float w) {
        w = w < 0 ? 0 : w;
        mClipFrameBorderWidth = w;
        mClipFramePaint.setStrokeWidth(mClipFrameBorderWidth);
    }

    /**
     * 剪裁内容的左上角X坐标
     * @return
     */
    public float getContentX() {
        return currX;
    }

    /**
     * 剪裁内容的左上角Y坐标
     * @return
     */
    public float getContentY() {
        return currY;
    }

    /**
     * 获取剪裁内容的宽度
     * @return
     */
    public int getContentWidth() {
        return mClipFrameWidth;
    }

    /**
     * 获取剪裁内容的高度
     * @return
     */
    public int getContentHeight() {
        return mClipFrameHeight;
    }

    public int getImWidth() {
        return imWidth;
    }

    public int getImHeight() {
        return imHeight;
    }

    /**
     * 设置是否显示剪裁框
     * @param f
     */
    public void setShowClipFrame(boolean f) {
        showClipFrame = f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showClipFrame) {
            drawShadowRegion(canvas);
            drawClipFrame(canvas);
        }
    }

    /**
     * 绘制剪裁框外的阴影
     * @param canvas
     */
    private void drawShadowRegion(Canvas canvas) {
        canvas.drawRect(mRectLeftShadow, mShadowPaint);
        canvas.drawRect(mRectTopShadow, mShadowPaint);
        canvas.drawRect(mRectRightShadow, mShadowPaint);
        canvas.drawRect(mRectBottomShadow, mShadowPaint);
    }

    /**
     * 绘制剪裁框
     * @param canvas
     */
    private void drawClipFrame(Canvas canvas) {
        canvas.drawRect(mClipFrame, mClipFramePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                oldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mClipFrame.contains((int) oldX, (int) oldY)) {
                    dX = event.getX() - oldX;
                    dY = event.getY() - oldY;
                    oldX = event.getX();
                    oldY = event.getY();
                    currX += dX;
                    currY += dY;
                    // 确保剪裁框不会超出ImageView的范围
                    currX = currX > maxX ? maxX : currX;
                    currX = currX < 0 ? 0 : currX;
                    currY = currY > maxY ? maxY : currY;
                    currY = currY < 0 ? 0 : currY;

                    setShadowRegion(currX, currY); // 设置阴影区域
                    setClipFramePosition(currX, currY); // 设置剪裁框位置
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP://手指抬起时，记录当前坐标，然后进行换算，计算Rect；
                if (mClipFrame.contains((int) oldX, (int) oldY)) {
                    dX = event.getX() - oldX;
                    dY = event.getY() - oldY;
                    oldX = event.getX();
                    oldY = event.getY();
                    currX += dX;
                    currY += dY;
                    // 确保剪裁框不会超出ImageView的范围
                    currX = currX > maxX ? maxX : currX;
                    currX = currX < 0 ? 0 : currX;
                    currY = currY > maxY ? maxY : currY;
                    currY = currY < 0 ? 0 : currY;

                    setShadowRegion(currX, currY); // 设置阴影区域
                    setClipFramePosition(currX, currY); // 设置剪裁框位置
                    invalidate();
                }
                if(onCutListener!=null){
                    onCutListener.OnCutImage(getClippedBitmap());
                }
                break;
        }
        return true;
    }

    /**
     * 选中监听器
     */
    public interface OnCutListener{
        void OnCutImage(Bitmap bitmap);
    }
}
