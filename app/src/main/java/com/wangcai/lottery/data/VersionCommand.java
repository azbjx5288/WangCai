package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 获取最新版本号
 * Created by Alashi on 2016/3/1.
 */
@RequestConfig(api = "service?packet=Release&action=getLatestRelease", response = Version.class)
public class VersionCommand {
    @SerializedName("terminal_id")
    private int terminalId = 2;
}
