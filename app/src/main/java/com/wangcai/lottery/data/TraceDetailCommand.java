package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;

/**
 * 追号订单详情
 * Created by Alashi on 2016/1/21.
 */
@RequestConfig(api = "service?packet=Game&action=getTraceDetail")
public class TraceDetailCommand extends CommonAttribute{
    private int id;

    public void setId(int id) {
        this.id = id;
    }

}
