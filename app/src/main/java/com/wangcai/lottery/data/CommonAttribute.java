package com.wangcai.lottery.data;

import com.wangcai.lottery.app.WangCaiApp;

/**
 * Created by Ace.Dong on 2018/3/23.
 */
public class CommonAttribute {
    private String token= WangCaiApp.getUserCentre().getUserInfo().getToken();
    private String sign;
    public void setToken(String token) {
        this.token = token;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
