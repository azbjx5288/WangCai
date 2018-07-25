package com.wangcai.lottery.data;

import java.util.ArrayList;

/**
 * Created by ACE-PC on 2016/7/12.
 * 玩法数据
 */
public class Balls {
    private int jsId; //注单的顺序ID
    private int wayId; //玩法ID
    private String ball; //投注码
    private long num; //单倍注数
    private double moneyunit; //价格系数
    private int multiple; //倍数
    private int prizeGroup; //奖金组
    private ArrayList extra; //附加信息，用于任选玩法，非任选时请设为空数组

    public Balls(int jsId, int wayId, String ball, long num, double moneyunit, int multiple, int prizeGroup, ArrayList extra) {
        this.jsId = jsId;
        this.wayId = wayId;
        this.ball = ball;
        this.num = num;
        this.moneyunit = moneyunit;
        this.multiple = multiple;
        this.prizeGroup = prizeGroup;
        this.extra = extra;
    }

    public int getJsId() {
        return jsId;
    }

    public void setJsId(int jsId) {
        this.jsId = jsId;
    }

    public int getWayId() {
        return wayId;
    }

    public void setWayId(int wayId) {
        this.wayId = wayId;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public long getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getMoneyunit() { return moneyunit; }

    public void setMoneyunit(double moneyunit) {
        this.moneyunit = moneyunit;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getPrizeGroup() {
        return prizeGroup;
    }

    public void setPrizeGroup(int prizeGroup) {
        this.prizeGroup = prizeGroup;
    }

    public ArrayList getExtra() {
        return extra;
    }

    public void setExtra(ArrayList extra) {
        this.extra = extra;
    }
}
