package com.wangcai.lottery.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ScrollView滚动
 * Created by ACE-PC on 2017/2/3.
 */
public class CustomScrollView extends ScrollView {
	/** 正向移动的单位像素距离 **/
	private final int POSITIVE_MOVE_Y = 1;
	/** 正向移动的单位像素距离 **/
	private final int NAGATIVE_MOVE_Y = -1;
	/** 移动单位距离需要睡眠的时间 **/
	private final int SLEEP_EVERY_TIME = 1;
	/** 正向移动信息标志 **/
	private final int POSITIVE_MARK = 0;
	/** 负向移动信息标志 **/
	private final int NAGATIVE_MARK = 1;
	/** 当前一次操作需要移动的总距离 **/
	private int range;
	/** 是否屏蔽ScrollView的触摸事件 1表示屏蔽 0表示不屏蔽 **/
	private int state = 0;
	/** 当我们的ScrollView正在自动滑动的时候 我们不能开启新的线程再次让其自动滑动 所以要有一个开关来控制 默认是没有正在自动滑动 **/
	private boolean isScrolling;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case POSITIVE_MARK:
				CustomScrollView.this.scrollBy(0, POSITIVE_MOVE_Y);
				break;
			case NAGATIVE_MARK:
				CustomScrollView.this.scrollBy(0, NAGATIVE_MOVE_Y);
				break;
			}
		}
	};

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomScrollView(Context context) {
		super(context);
	}

	public void roreydiuAotuScroll(final int moveToY) {
		if (moveToY < 0) {
			// Toast.makeText(getContext(), "您输入的位置不合法", 0).show();
			return;
		}
		if (moveToY == CustomScrollView.this.getScrollY()) {
			// Toast.makeText(getContext(), "与上一次的位置相同", 0).show();
			return;
		}
		if (isScrolling == true) {
			// Toast.makeText(getContext(), "正在自动滑动", 0).show();
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					isScrolling = true;
					state = 1;
					if ((moveToY - CustomScrollView.this.getScrollY()) >= 0) {
						/** 这里只是我的视图总高度是10500 因为我的手机像素密度是3.0的 做的只是一个简单的演示 没有写规范 **/
						// 这里的目的是当输入的值过大时 能够即时判断 不让线程做一些过多的无用工作 因为已经早就滑到了底部
						// 也避免触摸事件的长久屏蔽 实际运用中不会出现这样的问题 所以不加入此判断也是没有关系的
						if (moveToY > 10500) {
							range = 10500 - CustomScrollView.this.getScrollY();
						} else {
							range = (int) (moveToY - CustomScrollView.this
									.getScrollY());
						}
						for (int i = 0; i < range; i++) {
							Thread.sleep(SLEEP_EVERY_TIME);
							Log.i("===", POSITIVE_MOVE_Y + "");
							handler.sendEmptyMessage(POSITIVE_MARK);
						}
					} else {
						range = (int) (CustomScrollView.this.getScrollY() - moveToY);
						for (int i = 0; i < range; i++) {
							Thread.sleep(SLEEP_EVERY_TIME);
							handler.sendEmptyMessage(NAGATIVE_MARK);
						}
					}
					state = 0;
					isScrolling = false;// 滑动完毕可以再次开启
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/** 当线程开启移动的时候 屏蔽ScrollView的触摸事件 当线程结束的时候再次打开 这样一是保护线程 二是保证移动的位置准确性 **/
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (state == 0) {
			return super.onTouchEvent(ev);
		} else {
			return false;
		}

	}

}
