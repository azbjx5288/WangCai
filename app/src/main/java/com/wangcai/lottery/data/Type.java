package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/31.
 */
public class Type {
    /**
     * id : 1
     * description : 在线充值
     */

    @SerializedName("id")
    private int id;
    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
