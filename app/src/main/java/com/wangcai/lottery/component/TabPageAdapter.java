package com.wangcai.lottery.component;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.wangcai.lottery.app.BaseFragment;

import java.util.List;

/**
 * Created by lt on 2016/01/08.
 * @author ACE
 * @功能描述: app导航内容区域适配器
 */
public class TabPageAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private List<? extends BaseFragment> fragments;
    private int mChildCount=0;

    public TabPageAdapter(FragmentManager fm,List<? extends BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount=getCount();
        super.notifyDataSetChanged();
    }

    /**
     * 重写，不让Fragment销毁
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
//        super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        fragment = (Fragment)object;
    }

    @Override
    public int getItemPosition(Object object) {
        //方式1：
        if(mChildCount>0){
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
        //方式2：
        //return POSITION_NONE;
        /*备注：方式1和方式2在只有3个Fragment的情况下，效果一样，多于3个的情况没有验证*/
    }

    public Fragment getCurrentFragment() {
        return fragment;
    }
}
