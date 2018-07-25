package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 手机客户端登录验证
 * Created by Alashi on 2016/1/5.
 */
@RequestConfig(api = "service?packet=User&action=login", response = UserInfo.class, request = LoginRequest.class)
public class LoginCommand {
    /**String(10)	不可为空	用户名 （绑定手机号登录功能二期实现）*/
    private String username;
    /**String(32)	不可为空	用户密码，经过md5加密，长度为32的字符串。*/
    private String password;
    /** 终端 ID */
    @SerializedName("terminal_id")
    private int terminalId = 2;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }
}
