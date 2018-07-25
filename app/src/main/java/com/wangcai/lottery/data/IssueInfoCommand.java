package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 开奖信息获取
 * Created by ACE-PC on 2016/1/22.
 */

@RequestConfig(api = "service?packet=Game&action=getCurrentIssue")
public class IssueInfoCommand extends CommonAttribute{
    @SerializedName("lottery_id")
    private int lotteryId;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

}

