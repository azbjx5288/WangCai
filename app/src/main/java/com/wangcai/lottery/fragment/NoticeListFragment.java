package com.wangcai.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.NoticeList;
import com.wangcai.lottery.data.NoticeListCommand;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by Alashi on 2016/1/19.
 */
public class NoticeListFragment extends BaseFragment {
    private static final String TAG = NoticeListFragment.class.getSimpleName();

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    private ArrayList<NoticeList> notices;
    private UserCentre userCentre;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "公告", R.layout.refreshable_list_fragment,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userCentre= WangCaiApp.getUserCentre();
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadData(false));
        listView.setAdapter(adapter);
        loadData(true);
    }

    private void loadData(boolean withCache) {
        NoticeListCommand command = new NoticeListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<NoticeList>>>() {
        };
        if (withCache) {
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, 0, this);
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof ArrayList) {
                notices = (ArrayList<NoticeList>) restResponse.getData();
            }
            restRequest.execute();
        } else {
            RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, 0, this);
        }
    }

    private RestCallback restCallback = new RestCallback<ArrayList<NoticeList>>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ArrayList<NoticeList>> response) {
            notices = response.getData();
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
            return notices == null ? 0 : notices.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            NoticeList notice = notices.get(position);
            holder.title.setText(notice.getTitle());
            holder.time.setText(notice.getCreatedAt());

            return convertView;
        }
    };

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        NoticeDetailsFragment.launch(this, notices.get(position));
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }
}
