package com.wangcai.lottery.pattern;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.view.NumberGroupView;

import java.util.ArrayList;

/**
 * 对选号列的View的相关操作的封装
 * Created by Alashi on 2016/1/13.
 */
public class PickNumber implements View.OnClickListener {
    private static final String TAG = PickNumber.class.getSimpleName();
    private RelativeLayout layout;
    private NumberGroupView numberGroupView;
    private LinearLayout viewGroup;
    private TextView columnTitle;
    private NumberGroupView.OnChooseItemClickListener onChooseItem;

    public PickNumber(View topView, String title) {
        layout = topView.findViewById(R.id.pick_column_area);
        columnTitle = topView.findViewById(R.id.pick_column_title);
        columnTitle.setText(title);
        numberGroupView = topView.findViewById(R.id.pick_column_NumberGroupView);
        viewGroup = topView.findViewById(R.id.pick_column_control);
        for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
            viewGroup.getChildAt(i).setOnClickListener(this);
        }
    }

    public NumberGroupView getNumberGroupView() {
        return numberGroupView;
    }

    public ArrayList<Integer> getCheckedNumber() {
        return numberGroupView.getCheckedNumber();
    }

    public void setNumberStyle(boolean numberStyle) {
        numberGroupView.setNumberStyle(numberStyle);
    }

    /**
     * 选号数量
     *
     * @return
     */
    public int getNumberCount() {
        return numberGroupView.getNumberCount();
    }

    public void onRandom(ArrayList<Integer> list) {
        numberGroupView.setCheckNumber(list);
    }

    /**
     * 选择监听
     */
    public void setChooseItemClickListener(NumberGroupView.OnChooseItemClickListener onChooseItem) {
        this.onChooseItem = onChooseItem;
        numberGroupView.setChooseItemListener(onChooseItem);
    }

    @Override
    public void onClick(View v) {
        int max = numberGroupView.getMaxNumber();
        int min = numberGroupView.getMinNumber();
        ArrayList<Integer> list = new ArrayList<>();
        switch (v.getId()) {
            case R.id.pick_column_big:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    list.add(i);
                }
                break;
            case R.id.pick_column_small:
                for (int i = min, end = min + (max - min + 1) / 2; i < end; i++) {
                    list.add(i);
                }
                break;
            case R.id.pick_column_singular:
                for (int i = min; i <= max; i++) {
                    if (i % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.pick_column_evenNumbers:
                for (int i = min; i <= max; i++) {
                    if (i % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.pick_column_all:
                for (int i = min; i <= max; i++) {
                    list.add(i);
                }
                break;
            case R.id.pick_column_clear:
                break;
            default:
                Log.d(TAG, "onClick: nonsupport view id:" + v.getId());
                return;
        }
        numberGroupView.setCheckNumber(list);
        onChooseItem.onChooseItemClick(numberGroupView.getCheckedNumber().size() - 1);
    }

    public void setLableText(String text) {
        columnTitle.setText(text);
    }

    /**
     * 标题设置
     *
     * @param flag
     */
    public void setTitleHideOrShow(boolean flag) {
        if (!flag) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 控键设置
     *
     * @param hide
     */
    public void setControlBarHide(boolean hide) {
        if (hide) {
            viewGroup.setVisibility(View.INVISIBLE);
        } else {
            viewGroup.setVisibility(View.VISIBLE);
        }
    }
}
