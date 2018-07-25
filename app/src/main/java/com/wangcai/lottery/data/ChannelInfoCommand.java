package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/12/29.
 */
@RequestConfig(api = "service?packet=Fund&action=deposit")
public class ChannelInfoCommand extends CommonAttribute{
    @SerializedName("platform_id")
    private int platformId;
    private double amount;
    @SerializedName("bank_id")
    private int bankId;
    @SerializedName("is_phone")
    private String isPhone;

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setIsPhone(String isPhone) {
        this.isPhone = isPhone;
    }
}