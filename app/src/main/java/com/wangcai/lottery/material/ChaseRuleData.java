package com.wangcai.lottery.material;

/**
 * Created by ACE-PC on 2016/3/30.
 */
public class ChaseRuleData {
    private int multiple=1;
    private int issueGap=1;
    private int multipleTurn=1;

    private int gainMode=0;
    private int agoIssue=0;
    private int agoValue=0;
    private int laterValue=0;

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getIssueGap() {
        return issueGap;
    }

    public void setIssueGap(int issueGap) {
        this.issueGap = issueGap;
    }

    public int getMultipleTurn() {
        return multipleTurn;
    }

    public void setMultipleTurn(int multipleTurn) {
        this.multipleTurn = multipleTurn;
    }

    public int getGainMode() {
        return gainMode;
    }

    public void setGainMode(int gainMode) {
        this.gainMode = gainMode;
    }

    public int getAgoIssue() {
        return agoIssue;
    }

    public void setAgoIssue(int agoIssue) {
        this.agoIssue = agoIssue;
    }

    public int getAgoValue() {
        return agoValue;
    }

    public void setAgoValue(int agoValue) {
        this.agoValue = agoValue;
    }

    public int getLaterValue() {
        return laterValue;
    }

    public void setLaterValue(int laterValue) {
        this.laterValue = laterValue;
    }
}
