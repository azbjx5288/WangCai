package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Alashi on 2016/1/27.
 */
public class Trace {


    /**
     * id : 287
     * user_id : 284
     * terminal_id : null
     * serial_number : 284574C0E8478D1A4.92453597
     * prize_group : 1950
     * lottery_id : 1
     * lottery_name : 重庆时时彩
     * total_issues : 5
     * finished_issues : 1
     * canceled_issues : 0
     * stop_on_won : 1
     * start_issue : 160530072
     * way_id : 26
     * way : 后四组选24
     * bet_number : 4567
     * coefficient : 0.010
     * single_amount : 0.0200
     * amount : 0.5000
     * prize : 0.0000
     * status : 0
     * bought_at : 2016-05-30 17:57:23
     * updated_at : 2016-05-30 17:57:24
     */

    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("terminal_id")
    private String terminalId;
    @SerializedName("serial_number")
    private String serialNumber;
    @SerializedName("prize_group")
    private int prizeGroup;
    @SerializedName("lottery_id")
    private int lotteryId;
    @SerializedName("lottery_name")
    private String lotteryName;
    @SerializedName("total_issues")
    private int totalIssues;
    @SerializedName("finished_issues")
    private int finishedIssues;
    @SerializedName("canceled_issues")
    private int canceledIssues;
    @SerializedName("stop_on_won")
    private int stopOnWon;
    @SerializedName("start_issue")
    private String startIssue;
    @SerializedName("way_id")
    private int wayId;
    @SerializedName("way")
    private String way;
    @SerializedName("bet_number")
    private String betNumber;
    @SerializedName("coefficient")
    private float coefficient;
    @SerializedName("single_amount")
    private double singleAmount;
    @SerializedName("amount")
    private double amount;
    @SerializedName("prize")
    private double prize;
    @SerializedName("status")
    private int status;
    @SerializedName("bought_at")
    private Date boughtAt;
    @SerializedName("updated_at")
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getPrizeGroup() {
        return prizeGroup;
    }

    public void setPrizeGroup(int prizeGroup) {
        this.prizeGroup = prizeGroup;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public int getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(int totalIssues) {
        this.totalIssues = totalIssues;
    }

    public int getFinishedIssues() {
        return finishedIssues;
    }

    public void setFinishedIssues(int finishedIssues) {
        this.finishedIssues = finishedIssues;
    }

    public int getCanceledIssues() {
        return canceledIssues;
    }

    public void setCanceledIssues(int canceledIssues) {
        this.canceledIssues = canceledIssues;
    }

    public int getStopOnWon() {
        return stopOnWon;
    }

    public void setStopOnWon(int stopOnWon) {
        this.stopOnWon = stopOnWon;
    }

    public String getStartIssue() {
        return startIssue;
    }

    public void setStartIssue(String startIssue) {
        this.startIssue = startIssue;
    }

    public int getWayId() {
        return wayId;
    }

    public void setWayId(int wayId) {
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

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(Date boughtAt) {
        this.boughtAt = boughtAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
