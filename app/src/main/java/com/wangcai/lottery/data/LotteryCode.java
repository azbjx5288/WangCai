package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/3/1.
 */
public class LotteryCode {

    @SerializedName("issue")
    private String issue;
    @SerializedName("wn_number")
    private String wnNumber;
    @SerializedName("offical_time")
    private String officalTime;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getWnNumber() {
        return wnNumber;
    }

    public void setWnNumber(String wnNumber) {
        this.wnNumber = wnNumber;
    }

    public String getOfficalTime() {
        return officalTime;
    }

    public void setOfficalTime(String officalTime) {
        this.officalTime = officalTime;
    }
}
