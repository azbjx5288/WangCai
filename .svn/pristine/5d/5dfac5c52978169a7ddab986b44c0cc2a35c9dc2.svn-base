package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/4.
 */

@RequestConfig(api = "service?packet=Game&action=getAvailablePrizeGroups")
public class RegChildRebateCommand extends CommonAttribute{
    @SerializedName("lottery_id")
    private int lotteryId;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }
}
