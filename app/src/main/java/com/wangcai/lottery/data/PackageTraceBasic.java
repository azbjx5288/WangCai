package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

public class PackageTraceBasic {
    /**
     * id : 1091
     * user_id : 28823
     * terminal_id : 2
     * serial_number : 288235B28C74ED31D15.88632549
     * prize_group : 1946
     * lottery_id : 51
     * lottery_name : 旺旺快乐8分分彩
     * total_issues : 6
     * finished_issues : 1
     * canceled_issues : 5
     * stop_on_won : 1
     * start_issue : 1806191026
     * way_id : 429
     * way : 奇偶和
     * bet_number : 奇 偶
     * coefficient : 1.000
     * single_amount : 4.0000
     * amount : 24.0000
     * prize : 4.610000
     * status : 4
     * bought_at : 2018-06-19 17:05:18
     * updated_at : 2018-06-19 17:06:16
     */

    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("terminal_id")
    private String terminalId;
    @SerializedName("serial_number")
    private String serialNumber;
    @SerializedName("prize_group")
    private String prizeGroup;
    @SerializedName("lottery_id")
    private String lotteryId;
    @SerializedName("lottery_name")
    private String lotteryName;
    @SerializedName("total_issues")
    private String totalIssues;
    @SerializedName("finished_issues")
    private String finishedIssues;
    @SerializedName("canceled_issues")
    private String canceledIssues;
    @SerializedName("stop_on_won")
    private String stopOnWon;
    @SerializedName("start_issue")
    private String startIssue;
    @SerializedName("way_id")
    private String wayId;
    @SerializedName("way")
    private String way;
    @SerializedName("bet_number")
    private String betNumber;
    @SerializedName("coefficient")
    private String coefficient;
    @SerializedName("single_amount")
    private String singleAmount;
    @SerializedName("amount")
    private String amount;
    @SerializedName("prize")
    private String prize;
    @SerializedName("status")
    private int status;
    @SerializedName("bought_at")
    private String boughtAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPrizeGroup() {
        return prizeGroup;
    }

    public void setPrizeGroup(String prizeGroup) {
        this.prizeGroup = prizeGroup;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(String totalIssues) {
        this.totalIssues = totalIssues;
    }

    public String getFinishedIssues() {
        return finishedIssues;
    }

    public void setFinishedIssues(String finishedIssues) {
        this.finishedIssues = finishedIssues;
    }

    public String getCanceledIssues() {
        return canceledIssues;
    }

    public void setCanceledIssues(String canceledIssues) {
        this.canceledIssues = canceledIssues;
    }

    public String getStopOnWon() {
        return stopOnWon;
    }

    public void setStopOnWon(String stopOnWon) {
        this.stopOnWon = stopOnWon;
    }

    public String getStartIssue() {
        return startIssue;
    }

    public void setStartIssue(String startIssue) {
        this.startIssue = startIssue;
    }

    public String getWayId() {
        return wayId;
    }

    public void setWayId(String wayId) {
        this.wayId = wayId;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(String betNumber) {
        this.betNumber = betNumber;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    public String getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(String singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(String boughtAt) {
        this.boughtAt = boughtAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
