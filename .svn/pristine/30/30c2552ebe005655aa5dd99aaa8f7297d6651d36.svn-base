package com.wangcai.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryCode;
import com.wangcai.lottery.data.LotteryCodeCommand;
import com.wangcai.lottery.view.adapter.HistoryCodeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by ACE-PC on 2016/3/7.
 */
public class ResultsFragment extends BaseFragment {

    private static final String TAG = HistoryCodeFragment.class.getSimpleName();

    private static final int LIST_HISTORY_CODE_ID = 1;

    @BindView(R.id.refresh_history_listviewcode)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.history_lottery_listviewcode)
    ListView listView;

    private HistoryCodeAdapter adapter;
    private Lottery lottery;

    private ArrayList<LotteryCode> items = new ArrayList();
    private boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        adapter = new HistoryCodeAdapter(lottery,getContext());
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadCodeList());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        listView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
        isLoading = false;
        loadCodeList();
    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    private void loadCodeList() {
        if (isLoading) {
            return;
        }
        LotteryCodeCommand command = new LotteryCodeCommand();
        command.setLotteryId(lottery.getId());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<LotteryCode>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, LIST_HISTORY_CODE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList<?>) {
            items=(ArrayList<LotteryCode>) restResponse.getData();
            adapter.setData(items);
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == LIST_HISTORY_CODE_ID) {
                items=(ArrayList<LotteryCode>) response.getData();
                adapter.setData(items);
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (request.getId() == LIST_HISTORY_CODE_ID){
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };
}
