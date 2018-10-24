package com.wangcai.lottery.data;


import com.wangcai.lottery.base.net.RequestConfig;

/**
 * 5.18.获用户列表接口（代理）
 */
@RequestConfig(api = "service?packet=User&action=SubUser")
public class UserListSubCommand extends CommonAttribute {
    private int page;

    private int pid;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
