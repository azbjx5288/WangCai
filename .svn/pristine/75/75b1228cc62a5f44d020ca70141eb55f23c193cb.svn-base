package com.wangcai.lottery.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class UiUtils
{
	private final static int UPPER_LEFT_X = 0;
	private final static int UPPER_LEFT_Y = 0;

	public static Drawable convertViewToDrawable(View view) {
		int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(spec, spec);
		view.layout(UPPER_LEFT_X, UPPER_LEFT_Y, view.getMeasuredWidth(), view.getMeasuredHeight());
		Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.translate(-view.getScrollX(), -view.getScrollY());
		view.draw(c);
		view.setDrawingCacheEnabled(true);
		Bitmap cacheBmp = view.getDrawingCache();
		Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
		cacheBmp.recycle();
		view.destroyDrawingCache();
		return new BitmapDrawable(viewBmp);
	}

	/** dip转换px */
	public static int dip2px(Context context,int dip) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, int pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

    /**
     * 通过资源引用 获得颜色
     */
	public  static int  getColor(Context context, int colorId){
		int color = 0x00ffffff;
		try {
			color = context.getResources().getColor(colorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return color;
	}

	/**
	 * 通过资源引用 获得Drawable图片
	 */
	public  static Drawable  getDrawable(Context context, int colorId){
		Drawable statusQuestionDrawable = null;
		try {
			statusQuestionDrawable = context.getResources().getDrawable(colorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusQuestionDrawable;
	}


}


