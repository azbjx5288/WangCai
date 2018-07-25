package com.wangcai.lottery.data;

import com.wangcai.lottery.data.Balls;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ACE-PC on 2016/7/12.
 */
public class BetData {
    private int gameId;		            //11	彩票ID
    private int isTrace;		        //是否追号,1或0
    private boolean traceWinStop;		//是否追中即停，在isTrace为0时，无效
    private ArrayList<Balls> balls;     //投注详细信息
    private Map<String,Integer> orders; //奖期及倍数列表，键为奖期，值为倍数
    private double amount;              //总金额


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getIsTrace() {
        return isTrace;
    }

    public void setIsTrace(int isTrace) {
        this.isTrace = isTrace;
    }

    public boolean isTraceWinStop() {
        return traceWinStop;
    }

    public void setTraceWinStop(boolean traceWinStop) {
        this.traceWinStop = traceWinStop;
    }

    public ArrayList<Balls> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<Balls> balls) {
        this.balls = balls;
    }

    public Map<String, Integer> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Integer> orders) {
        this.orders = orders;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
