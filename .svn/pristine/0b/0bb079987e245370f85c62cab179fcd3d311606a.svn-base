package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/2/20.
 */
@RequestConfig(api = "service?packet=User&action=setFundPwd", method = Request.Method.POST)
public class SetFundPwdCommand extends CommonAttribute{
    @SerializedName("fund_pwd")
    private String fundPwd;
    @SerializedName("confirm_fund_pwd")
    private String confirmFundPwd;

    public void setFundPwd(String fundPwd) {
        this.fundPwd = fundPwd;
    }

    public void setConfirmFundPwd(String confirmFundPwd) {
        this.confirmFundPwd = confirmFundPwd;
    }
}
