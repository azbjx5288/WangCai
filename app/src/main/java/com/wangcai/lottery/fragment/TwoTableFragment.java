package com.wangcai.lottery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.component.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 两页Table的页面
 * Created by Alashi on 2016/3/17.
 */
public class TwoTableFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    private List<BaseFragment> fragments = new ArrayList<>();

    public static void launch(Context context, String title, String radio1title, Class<? extends Fragment> fragment1,String radio2title, Class<? extends Fragment> fragment2) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("radio1title", radio1title);
        bundle.putString("fragment1", fragment1.getName());
        bundle.putString("radio2title", radio2title);
        bundle.putString("fragment2", fragment2.getName());

        FragmentLauncher.launch(context, TwoTableFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "", R.layout.fragment_two_table,true,true);
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
        Bundle bundle = getArguments();
        setTitle(bundle.getString("title"));
        radioButton1.setText(bundle.getString("radio1title"));
        radioButton2.setText(bundle.getString("radio2title"));

        fragments.add((BaseFragment) Fragment.instantiate(getActivity(), bundle.getString("fragment1")));
        fragments.add((BaseFragment) Fragment.instantiate(getActivity(), bundle.getString("fragment2")));
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
