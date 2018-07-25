package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;

import java.util.Date;

/**
 * Created by ACE-PC on 2016/5/3.
 */

@RequestConfig(api = "service?packet=User&action=getUpdatedUsers")
public class LowerMemberCommand extends CommonAttribute{
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }
}
