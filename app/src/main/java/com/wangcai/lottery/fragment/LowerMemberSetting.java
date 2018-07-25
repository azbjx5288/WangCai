package com.wangcai.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.LowerMemberCommand;
import com.wangcai.lottery.data.LowerMemberList;
import com.wangcai.lottery.user.UserCentre;
import com.wangcai.lottery.view.adapter.LowerMemberAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/2.
 */
public class LowerMemberSetting extends BaseFragment {
    private static final int LOWER_TRACE_ID = 1;
    @BindView(R.id.lower_search)
    EditText lowerSearch;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private UserCentre userCentre;
    private int totalCount;
    private boolean isLoading;
    private List items = new ArrayList();
    private LowerMemberAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lower_member_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
        adapter=new LowerMemberAdapter(items);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> lowerLoad());

        final int endTrigger = 2; // load more content 2 items before the end
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        list.setAdapter(adapter);

        lowerLoad();
    }

    @OnClick({R.id.home})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                getActivity().finish();
                break;
        }
    }

    private void lowerLoad() {
        LowerMemberCommand command = new LowerMemberCommand();
        TypeToken typeToken = new TypeToken<RestResponse<LowerMemberList>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, LOWER_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof LowerMemberList) {
            items.addAll(((LowerMemberList) restResponse.getData()).getUsers());
            adapter.setData(items);
        }else{
            adapter.setData(null);
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == LOWER_TRACE_ID) {
                if (response.getData() == null || !(response.getData() instanceof LowerMemberList)) {
                    items.clear();
                } else {
                    totalCount = ((LowerMemberList) response.getData()).getUsersCount();
                    if (page == FIRST_PAGE) {
                        items.clear();
                    }
                    items.addAll(((LowerMemberList) response.getData()).getUsers());
                }
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
            if (request.getId() == LOWER_TRACE_ID){
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
