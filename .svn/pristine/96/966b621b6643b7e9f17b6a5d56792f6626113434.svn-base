package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.component.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 快速提现界面
 * Created by Alashi on 2016/3/17.
 */
public class WithdrawDepositFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "快速提现", R.layout.fragment_withdraw_deposit,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

        radioGroup.setOnCheckedChangeListener(this);
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.setOnPageChangeListener(this);
        radioGroup.check(radioGroup.getChildAt(0).getId());

        selectPage(0);
    }

    protected void initData() {
        fragments.add((BaseFragment) Fragment.instantiate(getActivity(), DrawFragment.class.getName()));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton1:
                selectPage(0);
                break;
            case R.id.radioButton2:
                selectPage(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
    }
}
