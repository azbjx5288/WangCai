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
import com.wangcai.lottery.app.FragmentDelayer;
import com.wangcai.lottery.component.TabPageAdapter;
import com.wangcai.lottery.data.Lottery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 历史开奖
 * Created on 2016/1/19.
 *
 * @author ACE
 */
public class HistoryCodeFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private static final String TAG = HistoryCodeFragment.class.getSimpleName();

    @BindView(R.id.history_code_viewpager)
    ViewPager viewPager;
    @BindView(R.id.history_radioGroup)
    RadioGroup radioGroup;
    private Lottery lottery;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "开奖", R.layout.fragment_history_code, true, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        init();
        initView();
        selectPage(0); // 默认选中首页
    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    protected void init() {
        setTitle(lottery.getName());
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        BaseFragment resultsFragment = FragmentDelayer.newInstance(R.drawable.ic_tab_classify, ResultsFragment.class.getName(), bundle);
        BaseFragment chartTrendFragment = FragmentDelayer.newInstance(R.drawable.ic_tab_discover, ChartTrendFragment.class.getName(), bundle);
        fragments.add(resultsFragment);
        fragments.add(chartTrendFragment);
    }

    private void initView() {
        radioGroup.setOnCheckedChangeListener(this);
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.setOnPageChangeListener(this);
        radioGroup.check(radioGroup.getChildAt(0).getId());
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

    /**
     * 选择某页
     *
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.history_CodeRadioButton: // 开奖结果
                selectPage(0);
                break;
            case R.id.history_TrendRadioButton: // 走势图
                selectPage(1);
                break;
        }
    }
}
