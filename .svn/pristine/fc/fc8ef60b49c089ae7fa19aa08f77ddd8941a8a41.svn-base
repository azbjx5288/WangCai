package com.wangcai.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.Order;
import com.wangcai.lottery.data.OrderListCommand;
import com.wangcai.lottery.data.Type;
import com.wangcai.lottery.data.TypeCommand;
import com.wangcai.lottery.material.ConstantInformation;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 资金明细的某一种类别页面，“全部、投注、派奖、充值、提现”
 * Created by Alashi on 2016/1/19.
 */
public class BalanceDetailsFragment extends BaseFragment {
    private static final String TAG = BalanceDetailsFragment.class.getSimpleName();

    private static final int TYPE_LIST_ID = 1;
    private static final int BALANCE_DETAIL_ID = 2;

    /** 服务器分页从1开始 */
    private static final int FIRST_PAGE = 1;

    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list) ListView listView;

    private int totalCount;
    private List<Order> orders = new ArrayList<>();
    private SparseArray<String> orderTypes = new SparseArray<>();
    private int page = FIRST_PAGE;
    private boolean isLoading;

    /** 其他的类别：全部、投注、派奖、充值、提现 */
    private int orderType;
    private SparseArray<Integer> typesArray = new SparseArray<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.refreshable_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typesArray.put(1,7);
        typesArray.put(2,11);
        typesArray.put(3,1);
        typesArray.put(4,2);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"),Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadData(FIRST_PAGE));

        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,int totalItemCount) {
            }
        });

        listView.setAdapter(adapter);
        loadData(FIRST_PAGE);
        loadType();
    }

    /**0：全部
     1：投注，含撤单返款
     2：派奖，含撤销派奖
     3：充值
     4：提现， 含取消取现
     */
    public void setOrderType(int type) {
        this.orderType = type;
    }

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        Order order = orders.get(position);
        //401:投注; 301:投注返点; 303:撤单返款; 308:中奖; 413:撤消中奖;
        // 411:撤消返点
        /*int type = order.getTypeId();

        if(type == 7){
            Bet bet = new Bet();
            bet.setId(order.getId());
            bet.setSerialNumber(order.getSerialNumber());
            BetOrTraceDetailFragment.launch(this, bet);
        }*/
    }

    private void loadType() {
        TypeCommand command = new TypeCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Type>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, TYPE_LIST_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            ArrayList<Type> typeList=(ArrayList<Type>) restResponse.getData();
            for (Type type : typeList) {
                orderTypes.put(type.getId(), type.getDescription());
            }
        }
        restRequest.execute();
    }

    private void loadData(int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        OrderListCommand command = new OrderListCommand();
        command.setPage(page);
        if(orderType!=0){
            command.setTypeId(typesArray.get(orderType));
        }
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Order>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BALANCE_DETAIL_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            orders=(ArrayList<Order>) restResponse.getData();
            adapter.notifyDataSetChanged();
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if(request.getId()==TYPE_LIST_ID){
                ArrayList<Type> typeList=(ArrayList<Type>) response.getData();
                for (Type type : typeList) {
                    orderTypes.put(type.getId(), type.getDescription());
                }
            }else if(request.getId()==BALANCE_DETAIL_ID) {
                ArrayList<Order> orderList = (ArrayList<Order>) response.getData();
                if (page == FIRST_PAGE) {
                    orders.clear();
                }
                orders = orderList;
                adapter.notifyDataSetChanged();
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }else {
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
            isLoading = state == RestRequest.RUNNING;
        }
    };

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return orders == null? 0 : orders.size();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_details_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Order order = orders.get(position);
            holder.name.setText(order.getDescription());
            holder.rebates.setText(String.format("%.2f", order.getAmount()));
            holder.time.setText(ConstantInformation.getDateFormat(order.getCreatedAt()));
            holder.money.setText(String.format("%.2f", order.getAblance()));
            return convertView;
        }
    };

    static class ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.rebates) TextView rebates;
        @BindView(R.id.money) TextView time;
        @BindView(R.id.time) TextView money;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }
}
