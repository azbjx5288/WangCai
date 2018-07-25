package com.wangcai.lottery.pattern;

import com.wangcai.lottery.component.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;
    public String subTitle;
    public boolean isSort;
    public int sortIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon, boolean isSort, int sortIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.isSort = isSort;
        this.sortIcon = sortIcon;
    }

    public TabEntity(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    @Override
    public boolean isSort() {
        return isSort;
    }

    @Override
    public int getSortIcon() {
        return sortIcon;
    }
}