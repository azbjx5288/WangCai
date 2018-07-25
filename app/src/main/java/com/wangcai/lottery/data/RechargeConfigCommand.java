package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/6/24.
 * 1 网银2 微信3 支付宝4 银行卡
 */
@RequestConfig(api = "service?packet=Fund&action=getDepositPlatformList")
public class RechargeConfigCommand extends CommonAttribute{
    @SerializedName("pament_type_id")
    private String pamentTypeId;

    public void setPamentTypeId(String pamentTypeId) {
        this.pamentTypeId = pamentTypeId;
    }
}
