package com.wangcai.lottery.data;

public class SameMultipleRowData {
    private int id;
    private String issue;
    private String issueAbridge;
    private Integer multiple = 1;
    private double cost = 0.00;
    private double addUpAmount = 0.00;

    public SameMultipleRowData() {
    }

    public SameMultipleRowData(int id, String issue, String issueAbridge, int multiple, double cost, double addUpAmount) {
        this.id = id;
        this.issue = issue;
        this.issueAbridge = issueAbridge;
        this.multiple = multiple;
        this.cost = cost;
        this.addUpAmount = addUpAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getIssueAbridge() {
        return issueAbridge;
    }

    public void setIssueAbridge(String issueAbridge) {
        this.issueAbridge = issueAbridge;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAddUpAmount() {
        return addUpAmount;
    }

    public void setAddUpAmount(double addUpAmount) {
        this.addUpAmount = addUpAmount;
    }
}
