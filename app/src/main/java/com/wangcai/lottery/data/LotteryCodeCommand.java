package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/3/1.
 */
@RequestConfig(api = "service?packet=Game&action=getWnNumberList")
public class LotteryCodeCommand extends CommonAttribute{
    @SerializedName("lottery_id")
    private int lotteryId;
    private int count = 20;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
