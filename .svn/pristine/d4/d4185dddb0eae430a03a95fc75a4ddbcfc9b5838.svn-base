package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 提交订单
 * Created by ACE-PC on 2016/2/4. ?packet=Game&action=bet
 */
@RequestConfig(api = "service", method = Request.Method.POST, response = String.class)
public class PayMoneyCommand extends CommonAttribute{
    private String packet = "Game";
    private String action = "bet";
    @SerializedName("betdata")
    private String betData;

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setBetData(String betData) {
        this.betData = betData;
    }
}
