package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/5.
 */
@RequestConfig(api = "service?packet=User&action=getBankCardList")
public class BindCardDetailCommand extends CommonAttribute {
    @SerializedName("begin_date")
    private String beginDate;
    @SerializedName("end_date")
    private String endDate;

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
