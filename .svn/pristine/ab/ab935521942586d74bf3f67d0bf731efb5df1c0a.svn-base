package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 修改安全信息
 * Created by Alashi on 2016/5/2.
 */
@RequestConfig(api = "service?packet=User&action=getBankCardList")
public class EditSecurityCommand extends CommonAttribute{
    @SerializedName("real_name")
    private String realName;
    private String secpassword;
    private String secpassword2;
    private String email;
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setSecpassword(String secpassword) {
        this.secpassword = secpassword;
    }

    public void setSecpassword2(String secpassword2) {
        this.secpassword2 = secpassword2;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
