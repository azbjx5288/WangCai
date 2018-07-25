package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/3/3.
 */
@RequestConfig(api = "service?packet=User&action=lockBankCard", method = Request.Method.POST)
public class LockBankCardCommand extends CommonAttribute{
    @SerializedName("fund_pwd")
    private String fundPwd;
    @SerializedName("token")

    public void setFundPwd(String fundPwd) {
        this.fundPwd = fundPwd;
    }
}
