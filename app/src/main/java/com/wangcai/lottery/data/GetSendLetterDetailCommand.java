package com.wangcai.lottery.data;


import com.wangcai.lottery.base.net.RequestConfig;

/**
 * 13.4.获得发件信息详情接口
 */
@RequestConfig(api = "service?packet=Message&action=GetSendLetterDetail")
public class GetSendLetterDetailCommand extends CommonAttribute{
    private int id;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}
