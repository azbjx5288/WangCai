package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.material.RecordType;
import com.wangcai.lottery.view.adapter.GaRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACE-PC on 2017/7/7.
 * GA游戏列表
 */

public class GaFragment extends BaseFragment {
    private static final String TAG = GaFragment.class.getSimpleName();
    private GaRecyclerViewAdapter viewAdapter;
    private List<Lottery> item = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ga, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view instanceof RecyclerView) {
            viewAdapter = new GaRecyclerViewAdapter(this, item);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(viewAdapter);
        }
    }

    public void notifyData(List<Lottery> item) {
        List<Lottery> lotteries = new ArrayList<>();
        if (item.size() == 0) {
            if (getLotteryModel().getLotteryInfos().size() > 0) {
                for (int i = 0; i < getLotteryModel().getLotteryInfos().size(); i++) {
                    Lottery l = getLotteryModel().getLotteryInfo(i).lottery;
                    if (l.getGameType() == 5) {
                        lotteries.add(l);
                    }
                }
            }
        }else{
            getLotteryModel().setLotteryList(item);
            WangCaiApp.getUserCentre().setLotteryList(item);
            for (Lottery l : item) {
                if (l.getGameType() == 5) {
                    lotteries.add(l);
                }
            }
        }
        viewAdapter.setUpdataView(lotteries);
    }

    private RecordType getLotteryModel() {
        return RecordType.get(getActivity(), "lottery_model_history");
    }
}
