package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 玩法类型
 * Created by ACE-PC on 2016/1/22.
 */
public class MethodType {
    @SerializedName("id")
    private int id;
    @SerializedName("pid")
    private int pid;
    @SerializedName("name_cn")
    private String nameCn;
    @SerializedName("name_en")
    private String nameEn;
    /**
     * id : 68
     * pid : 4
     * series_way_id : 68
     * name_cn : 直选复式
     * name_en : fushi
     * price : 2
     * bet_note : 从个、十、百、千、万位各选一个号码组成一注
     * bonus_note : 从万位、千位、百位、十位、个位中各选择一个号码组成一注，所选号码与开奖号码全部相同，且顺序一致，即为中奖。
     * basic_methods : 16
     */

    @SerializedName("children")
    private List<Method> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public List<Method> getChildren() {
        return children;
    }

    public void setChildren(List<Method> children) {
        this.children = children;
    }
}
