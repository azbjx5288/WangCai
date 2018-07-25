package com.wangcai.lottery.data;

/**
 * 省市县抽象，本类及其子类不可混淆
 * Created by ACE-PC on 2016/10/24.
 */
public abstract class Area {
    private String areaId;
    private String areaName;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "areaId=" + areaId + ",areaName=" + areaName;
    }

}