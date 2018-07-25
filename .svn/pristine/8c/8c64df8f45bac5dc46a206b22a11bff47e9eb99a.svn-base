package com.wangcai.lottery.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.wangcai.lottery.app.WangCaiApp;

import java.util.List;

/**
 * Created by Ace.Dong on 2017/9/27.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setFragmentData(List<Fragment> fragments){
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
//        super.destroyItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    //设置tablayout标题
    @Override
    public CharSequence getPageTitle(int position) {
        return WangCaiApp.getUserCentre().getLotteryList().get(position).getName();
    }
}
