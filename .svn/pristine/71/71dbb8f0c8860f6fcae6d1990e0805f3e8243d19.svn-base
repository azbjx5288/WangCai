package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 追号撤单
 * Created by Alashi on 2016/2/8.
 */
@RequestConfig(api = "service?packet=Game&action=cancelTraceReserve", method = Request.Method.POST)
public class CancelTraceCommand extends CommonAttribute{
    @SerializedName("trace_id")
    private String traceId;
    @SerializedName("ids")
    private String ids;

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
