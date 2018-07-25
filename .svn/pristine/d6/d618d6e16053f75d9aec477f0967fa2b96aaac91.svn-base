package com.wangcai.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.SwitchButton;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryListCommand;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alashi on 2016/1/19.
 */
public class RebatesSetting extends BaseFragment {
    private static final String TAG = RebatesSetting.class.getSimpleName();

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private ArrayList<Lottery> lotteryList;
    private UserCentre userCentre;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "奖金设置", R.layout.refreshable_list_fragment,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadData(false));

        userCentre = WangCaiApp.getUserCentre();
        listView.setAdapter(adapter);
        loadData(true);
    }

    private void loadData(boolean withCache) {
        LotteryListCommand lotteryListCommand = new LotteryListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Lottery>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), lotteryListCommand, typeToken, restCallback, 0, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            lotteryList = (ArrayList<Lottery>) restResponse.getData();
            adapter.notifyDataSetChanged();
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            lotteryList = (ArrayList<Lottery>) response.getData();
            adapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
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
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
        }
    };

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return lotteryList == null ? 0 : lotteryList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rebates_setting_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Lottery lottery = lotteryList.get(position);

            holder.mtogbtnlab.setText(lottery.getName());
            holder.mtogbtn.setChecked(userCentre.getBonusMode(lottery.getId()) != 0 ? true : false);
            holder.mtogbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        //选中
                        userCentre.setBonusMode(lottery.getId(), 1);
                    } else {
                        //未选中
                        userCentre.setBonusMode(lottery.getId(), 0);
                    }
                }
            });
            return convertView;
        }
    };

    static class ViewHolder {
        @BindView(R.id.mtogbtnlab)
        TextView mtogbtnlab;
        @BindView(R.id.mtogbtn)
        SwitchButton mtogbtn;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }
}
