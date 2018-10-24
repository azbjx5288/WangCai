package com.wangcai.lottery.data;


import com.wangcai.lottery.base.net.RequestConfig;

/**
 * 13.5.获得发件时，当前用户基本信息接口
 */
@RequestConfig(api = "service?packet=Message&action=GetUserLetterInfo")
public class GetUserLetterInfoCommand extends CommonAttribute {
}
