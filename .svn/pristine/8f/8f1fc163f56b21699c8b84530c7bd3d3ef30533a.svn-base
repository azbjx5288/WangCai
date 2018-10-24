package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;

import org.json.JSONObject;

/**
 * 5.18.获用户列表接口（代理）
 */
@RequestConfig(api = "service?packet=User&action=AccurateUser")
public class OpenAccountAccurateCommand extends CommonAttribute {

    private float fb_single;//单关返点
    private float fb_all;//混合过关
    private float ag_percent;//AG返点
    private float ga_percent;//Ga返点
    private int is_agent;//是否是代理  0:不是 1：是代理
    private float username;//用户名
    private float password;//密码
    private float nickname;//昵称
    private float series_prize_group_json;//奖金组  {“1”:”1952”}
    private JSONObject agent_prize_set_quota;//大于1950 可以选择可以开启的奖金组配额   {“1950”:2,”1951”:1,”1952”:0}

}
