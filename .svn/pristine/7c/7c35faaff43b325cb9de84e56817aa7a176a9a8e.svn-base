package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 追号订单列表
 * Created by Alashi on 2016/1/21.
 */
@RequestConfig(api = "service?packet=Game&action=getTraceList")
public class TraceListCommand extends CommonAttribute{
    @SerializedName("lottery_id")
    private String lotteryId;
    private int page;
    private int pagesize = 20;

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
