package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

public class PackageTraceIssues {
    /**
     * id : 752043
     * issue : 1806191026
     * multiple : 1
     * amount : 4.0000
     * project_id : 15826
     * status : 1
     * bought_at : 2018-06-19 17:05:18
     * canceled_at : null
     */

    @SerializedName("id")
    private String id;
    @SerializedName("issue")
    private String issue;
    @SerializedName("multiple")
    private String multiple;
    @SerializedName("amount")
    private String amount;
    @SerializedName("project_id")
    private String projectId;
    @SerializedName("status")
    private String status;
    @SerializedName("bought_at")
    private String boughtAt;
    @SerializedName("canceled_at")
    private String canceledAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(String boughtAt) {
        this.boughtAt = boughtAt;
    }

    public Object getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(String canceledAt) {
        this.canceledAt = canceledAt;
    }
}
