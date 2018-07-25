package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/8/3.
 */
@RequestConfig(api = "service?packet=Event&action=getValidEvent")
public class ActivityCommand extends CommonAttribute {
    @SerializedName("user_id")
    private int userdId;

    public void setUserdId(int userdId) {
        this.userdId = userdId;
    }

}