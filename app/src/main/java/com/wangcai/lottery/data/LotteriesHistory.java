package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/1/26.
 */
public class LotteriesHistory {


    /**
     * lottery : 苹果分分彩
     * issues : [{"issue":"1605271102","wn_number":"26979","offical_time":"1464344520"},{"issue":"1605271101","wn_number":"46162","offical_time":"1464344460"},{"issue":"1605271100","wn_number":"60815","offical_time":"1464344400"},{"issue":"1605271099","wn_number":"95522","offical_time":"1464344340"},{"issue":"1605271098","wn_number":"40666","offical_time":"1464344280"}]
     */

    @SerializedName("lottery")
    private String lottery;
    /**
     * issue : 1605271102
     * wn_number : 26979
     * offical_time : 1464344520
     */

    @SerializedName("issues")
    private List<LotteryCode> issues;

    public String getLottery() {
        return lottery;
    }

    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    public List<LotteryCode> getIssues() {
        return issues;
    }

    public void setIssues(List<LotteryCode> issues) {
        this.issues = issues;
    }

}
