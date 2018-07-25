package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/10/19.
 */

public class ServiceParam {
    private String userid;
    @SerializedName("client_name")
    private String clientName;
    private String balance;
    @SerializedName("reg_time")
    private String regTime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }
}
