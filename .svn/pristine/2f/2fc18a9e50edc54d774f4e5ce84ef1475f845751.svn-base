package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 某彩种玩法信息查询
 * Created by ACE-PC on 2016/1/21.
 */
@RequestConfig(api = "service?packet=Game&action=getWaySettings")
public class MethodListCommand extends CommonAttribute{
    /**Int	不为空	lotteryID，空表示查所有彩种*/
    @SerializedName("lottery_id")
    private int lotteryId;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

}
