package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/4/6.
 */
@RequestConfig(api = "service?packet=Game&action=getAvailableIssues")
public class TraceIssueCommand extends CommonAttribute{
    @SerializedName("lottery_id")
    private int lotteryId;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

}
