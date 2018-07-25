package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/8/3.
 */

public class ActivityData {


    /**
     * id : 11
     * identifier : jpg07
     * title : 开奖啦
     * description : 登录手机App投注 享疯狂嘉奖！
     * calculate_cycle : 2
     * view_type : 3
     * is_team_event : 0
     * is_show_team_leader : 0
     * is_show_team_member : 1
     * is_receive : 0
     * after_receive_day_limit : 0
     * status : 1
     * is_get_mulite_prize : 1
     * start_time : 2017-08-01 23:59:59
     * end_time : 2017-08-13 00:00:00
     * icon : http://www.jpgtest1.com/events/event-11/images/D3.png
     * url : http://www.jpgtest1.com/event/11/view
     * terminal_id : null
     * created_at : 2017-07-25 16:03:43
     * updated_at : 2017-08-04 15:24:15
     * expired_at : null
     * is_single_condition : 0
     * terminal : 2
     * color : #FFE4C4
     * isPartIn : true,
     * large_icon:"http://www.jpgtest1.com/events/event-11/images/D1.png"
     */

    @SerializedName("id")
    private int id;
    @SerializedName("identifier")
    private String identifier;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("calculate_cycle")
    private int calculateCycle;
    @SerializedName("view_type")
    private int viewType;
    @SerializedName("is_team_event")
    private int isTeamEvent;
    @SerializedName("is_show_team_leader")
    private int isShowTeamLeader;
    @SerializedName("is_show_team_member")
    private int isShowTeamMember;
    @SerializedName("is_receive")
    private int isReceive;
    @SerializedName("after_receive_day_limit")
    private int afterReceiveDayLimit;
    @SerializedName("status")
    private boolean status;
    @SerializedName("isPartIn")
    private boolean isPartIn;
    @SerializedName("is_get_mulite_prize")
    private int isGetMulitePrize;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("icon")
    private String icon;
    @SerializedName("url")
    private String url;
    @SerializedName("terminal_id")
    private Object terminalId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("expired_at")
    private Object expiredAt;
    @SerializedName("is_single_condition")
    private int isSingleCondition;
    @SerializedName("terminal")
    private String terminal;
    @SerializedName("color")
    private String color;
    @SerializedName("large_icon")
    private String largeIcon;

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCalculateCycle() {
        return calculateCycle;
    }

    public int getViewType() {
        return viewType;
    }

    public int getIsTeamEvent() {
        return isTeamEvent;
    }

    public int getIsShowTeamLeader() {
        return isShowTeamLeader;
    }

    public int getIsShowTeamMember() {
        return isShowTeamMember;
    }

    public int getIsReceive() {
        return isReceive;
    }

    public int getAfterReceiveDayLimit() {
        return afterReceiveDayLimit;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isPartIn() {
        return isPartIn;
    }

    public int getIsGetMulitePrize() {
        return isGetMulitePrize;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }

    public Object getTerminalId() {
        return terminalId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Object getExpiredAt() {
        return expiredAt;
    }

    public int getIsSingleCondition() {
        return isSingleCondition;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getColor() {
        return color;
    }

    public String getLargeIcon() {
        return largeIcon;
    }
}
