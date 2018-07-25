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
import com.wangcai.lottery.view.adapter.LottoRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACE-PC on 2017/07/11.
 *
 * @author ACE  乐透彩种大厅 页面
 */
public class LottoFragment extends BaseFragment {
    private LottoRecyclerViewAdapter viewAdapter;
    private List<Lottery> item = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lotto, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view instanceof RecyclerView) {
            viewAdapter = new LottoRecyclerViewAdapter(this, getLotteryModel(), item);
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
                    if (l.getGameType() == 1) {
                        lotteries.add(l);
                    }
                }
            }
        }else{
            WangCaiApp.getUserCentre().setLotteryList(item);
            for (Lottery l : item) {
                if (l.getGameType() == 1) {
                    lotteries.add(l);
                }
            }
        }

        if (viewAdapter != null) {
            viewAdapter.setUpdataView(lotteries);
        }
    }

    public void notifyData(int seriesid, boolean dupo) {
        if (viewAdapter == null) {
            return;
        }
        List<Lottery> strainer = new ArrayList<>();
        if (dupo) {
            if (getLotteryModel().getLotteryInfos().size() >= 6) {
                for (int i = 0; i < 6; i++) {
                    Lottery l = getLotteryModel().getLotteryInfo(i).lottery;
                    if (l.getGameType() == 1) {
                        strainer.add(l);
                    }
                }
            } else {
                for (int i = 0; i < getLotteryModel().getLotteryInfos().size(); i++) {
                    Lottery l = getLotteryModel().getLotteryInfo(i).lottery;
                    if (l.getGameType() == 1) {
                        strainer.add(l);
                    }
                }
            }
        } else {
            List<Lottery> list = WangCaiApp.getUserCentre().getLotteryList();
            for (int i = 0; i < list.size(); i++) {
                Lottery l = list.get(i);
                if (l.getSeriesId() == seriesid && l.getGameType() == 1) {
                    strainer.add(l);
                }
            }
        }
        viewAdapter.setUpdataView(strainer);
    }

    private RecordType getLotteryModel() {
        return RecordType.get(getActivity(), "lottery_model_history");
    }
}
