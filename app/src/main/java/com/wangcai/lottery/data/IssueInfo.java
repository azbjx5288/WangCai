package com.wangcai.lottery.data;

/**
 * 获取开奖与销售信息
 * Created by ACE-PC on 2016/1/22.
 */
public class IssueInfo {

    /**
     * issue : 160712034
     * end_time : 2016-07-12 11:39:40
     * server_time : 2016-07-12 11:37:04
     */

    private String issue;
    private String end_time;
    private String server_time;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
