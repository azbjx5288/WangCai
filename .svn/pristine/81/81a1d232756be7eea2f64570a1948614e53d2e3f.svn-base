package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/2/3.
 */
@RequestConfig(api = "service?packet=Fund&action=depositQuery")
public class RechargeCallbackCommand extends CommonAttribute{
    @SerializedName("order_no")
    private String orderNo;

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
