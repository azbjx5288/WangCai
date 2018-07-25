package com.wangcai.lottery.data;

import java.util.ArrayList;

/**
 * 省份
 * Created by ACE-PC on 2016/10/24.
 */
public class Province extends Area {
    private ArrayList<City> cities = new ArrayList<City>();

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

}