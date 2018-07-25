package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/2/3.
 */

public class RechargeCallback {

    /**
     * status : 1
     * description : 异常充值
     */

    @SerializedName("status")
    private int status;
    @SerializedName("description")
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
