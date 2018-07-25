package com.wangcai.lottery.data;

import java.util.ArrayList;

/**
 * 地市
 * Created by ACE-PC on 2016/10/24.
 */
public class City extends Area {
    private ArrayList<County> counties = new ArrayList<County>();

    public ArrayList<County> getCounties() {
        return counties;
    }

    public void setCounties(ArrayList<County> counties) {
        this.counties = counties;
    }

}