package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 修改密码（登录密码或资金密码）
 * Created by Alashi on 2016/5/2.
 */
@RequestConfig(api = "service?packet=User&action=changeLoginPwd", method = Request.Method.POST)
public class ChangePasswordCommand extends CommonAttribute{
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
