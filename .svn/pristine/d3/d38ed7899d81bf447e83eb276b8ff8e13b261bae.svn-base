package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 资金明细
 * Created by Alashi on 2016/1/27.
 */
@RequestConfig(api = "service?packet=Fund&action=getTransactionList")
public class OrderListCommand extends CommonAttribute{
    @SerializedName("type_id")
    private Integer typeId = null;
    @SerializedName("begin_time")
    private Date beginTime;
    @SerializedName("end_time")
    private Date endTime;
    private int page = 1;
    private int pagesize = 30;
    /**
     * 0：全部
     * 1：投注，含撤单返款
     * 2：派奖，含撤销派奖
     * 3：充值
     * 4：提现， 含取消取现
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
