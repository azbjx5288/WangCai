package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageTrace {

    /**
     * basic : {"id":1091,"user_id":28823,"terminal_id":2,"serial_number":"288235B28C74ED31D15.88632549","prize_group":"1946","lottery_id":51,"lottery_name":"旺旺快乐8分分彩","total_issues":6,"finished_issues":1,"canceled_issues":5,"stop_on_won":1,"start_issue":"1806191026","way_id":429,"way":"奇偶和","bet_number":"奇 偶","coefficient":"1.000","single_amount":"4.0000","amount":"24.0000","prize":"4.610000","status":4,"bought_at":"2018-06-19 17:05:18","updated_at":"2018-06-19 17:06:16"}
     * issues : [{"id":752043,"issue":"1806191026","multiple":1,"amount":"4.0000","project_id":15826,"status":1,"bought_at":"2018-06-19 17:05:18","canceled_at":null},{"id":752044,"issue":"1806191027","multiple":1,"amount":"4.0000","project_id":null,"status":4,"bought_at":null,"canceled_at":"2018-06-19 17:06:16"},{"id":752045,"issue":"1806191028","multiple":1,"amount":"4.0000","project_id":null,"status":4,"bought_at":null,"canceled_at":"2018-06-19 17:06:16"},{"id":752046,"issue":"1806191029","multiple":1,"amount":"4.0000","project_id":null,"status":4,"bought_at":null,"canceled_at":"2018-06-19 17:06:16"},{"id":752047,"issue":"1806191030","multiple":1,"amount":"4.0000","project_id":null,"status":4,"bought_at":null,"canceled_at":"2018-06-19 17:06:16"},{"id":752048,"issue":"1806191031","multiple":1,"amount":"4.0000","project_id":null,"status":4,"bought_at":null,"canceled_at":"2018-06-19 17:06:16"}]
     */

    @SerializedName("basic")
    private PackageTraceBasic basic;
    @SerializedName("issues")
    private List<PackageTraceIssues> issues;

    public PackageTraceBasic getBasic() {
        return basic;
    }

    public void setBasic(PackageTraceBasic basic) {
        this.basic = basic;
    }

    public List<PackageTraceIssues> getIssues() {
        return issues;
    }

    public void setIssues(List<PackageTraceIssues> issues) {
        this.issues = issues;
    }

}
