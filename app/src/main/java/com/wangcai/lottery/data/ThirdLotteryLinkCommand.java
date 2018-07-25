package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;

/**
 * Created by Ace.Dong on 2017/11/23.
 */
@RequestConfig(api = "service?packet=Game&action=getThirdLotteryLink")
public class ThirdLotteryLinkCommand extends CommonAttribute{
    private String lottery;

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

}
