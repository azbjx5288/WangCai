package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 申请提现
 * Created by Alashi on 2016/3/17.
 */
@RequestConfig(api = "service?packet=Fund&action=withdraw",  method = Request.Method.POST)
public class WithdrawCommand extends CommonAttribute{

    @SerializedName("bankcard_id")
    private String bankcardId;
    @SerializedName("amount")
    private String amount;
    @SerializedName("fund_password")
    private String fundPassword;

    public void setBankcardId(String bankcardId) {
        this.bankcardId = bankcardId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setFundPassword(String fundPassword) {
        this.fundPassword = fundPassword;
    }

}
