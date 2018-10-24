package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

public class Customers{
    /**
     * id : 1
     * url : http://s3.myapple888.com/index/app?companyId=70722524&style=default&mode=4
     */

    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
