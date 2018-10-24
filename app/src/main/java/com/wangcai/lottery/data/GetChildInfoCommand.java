package com.wangcai.lottery.data;


import com.wangcai.lottery.base.net.RequestConfig;

@RequestConfig(api = "service?packet=User&action=GetChildInfo")
public class GetChildInfoCommand extends CommonAttribute
{
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
