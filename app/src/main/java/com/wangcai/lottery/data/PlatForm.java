package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

public class PlatForm {

    /**
     * id : 1
     * name : 竞彩
     * identity : JC
     */

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("identity")
    private String identity;

    public PlatForm(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
