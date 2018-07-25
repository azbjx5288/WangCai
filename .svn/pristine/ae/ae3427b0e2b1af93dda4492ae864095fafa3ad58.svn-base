package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/3/2.
 */
@RequestConfig(api = "service?packet=User&action=checkBankCard", method = Request.Method.POST)
public class ReviseCommand extends CommonAttribute{
    @SerializedName("card_id")
    private int cardId;
    @SerializedName("fund_pwd")
    private String fundPwd;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account")
    private String account;
    private int type;

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setFundPwd(String fundPwd) {
        this.fundPwd = fundPwd;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setType(int type) {
        this.type = type;
    }
}
