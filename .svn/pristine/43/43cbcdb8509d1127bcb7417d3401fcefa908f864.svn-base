package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/27.
 */
@RequestConfig(api = "service?packet=User&action=changeFundPwd", method = Request.Method.POST)
public class ChangeFundCommand extends CommonAttribute{
    @SerializedName("current_password")
    private String currentPassword;
    @SerializedName("new_password")
    private String newPassword;

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
