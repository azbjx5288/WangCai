package com.wangcai.lottery.pattern;

import android.view.View;
import android.widget.TextView;

import com.wangcai.lottery.R;

/**
 * 提示选择注数 订单金额
 * Created by ACE-PC on 2016/2/5.
 */
public class ChooseTips  {

    private TextView noticeText;		// 提示信息
    private TextView moneyText;			// 金额

    public ChooseTips(View tipView){
        noticeText = (TextView) tipView.findViewById(R.id.shopping_lottery_notice);
        moneyText = (TextView) tipView.findViewById(R.id.shopping_lottery_money);
    }

    /** 提示信息 */
    public void setTipsText(String notice,String money){
        noticeText.setText(notice);
        moneyText.setText(money);
    }
}
