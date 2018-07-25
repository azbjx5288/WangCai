package com.wangcai.lottery.data;

import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 彩票种类信息查询
 * Created by Alashi on 2016/1/5.
 */
@RequestConfig(api = "service?packet=Game&action=getAllGames")
public class LotteryListCommand extends CommonAttribute{
    @SerializedName("game_type")
    private String gameType;

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

}
