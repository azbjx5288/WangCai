package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2016/5/4.
 */

@RequestConfig(api = "service?packet=Game&action=GetSinglePick")
public class GetSinglePickCommand extends CommonAttribute{
}
