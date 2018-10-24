package com.wangcai.lottery.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.DeleteLettersCommand;
import com.wangcai.lottery.data.ReceiveBoxCommand;
import com.wangcai.lottery.data.ReceiveBoxResponse;
import com.wangcai.lottery.view.adapter.InBoxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收件箱
 */
public class MessageInFragment extends BaseFragment {
    private static final String TAG = MessageInFragment.class.getSimpleName();
    private int LIST = 0;
    private int DELETE = 1;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.ll_edit)
    RelativeLayout ll_edit;

    private List<ReceiveBoxResponse> list = new ArrayList<ReceiveBoxResponse>();

    private static final int FIRST_PAGE = 1;//服务器分页从1开始
    private int page = FIRST_PAGE;
    private boolean isLoading;

    private InBoxAdapter adapter;

    private boolean mStateIsEdit = false;
    private boolean mStateIsSelectAll = false;
    @BindView(R.id.select_all)
    Button select_all;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.empty_show)
    RelativeLayout empty_show;
    private ArrayList<String> mUnreadMtIdList = new ArrayList<>();

    private int maxPage= Integer.MAX_VALUE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_in_fragment, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFitSystem(false);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            loadData(false, FIRST_PAGE);
        });
        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getCount() != 0 &&page<maxPage&& listView.getLastVisiblePosition() >=
                            (listView.getCount() - 1) - endTrigger) {
                        loadData(false, page + 1);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ReceiveBoxResponse bean = list.get(position);

                if (mStateIsEdit) {

                    bean.setState(!bean.isState());
                    list.set(position, bean);


                    adapter.setList(list, mUnreadMtIdList);

                    setSelectAllState();
                } else {

                    if (!mUnreadMtIdList.contains(bean.getId())) {
                        mUnreadMtIdList.add(String.valueOf(bean.getId()));
                    }

                    BoxDetailsFragment.launch(MessageInFragment.this, bean.getId(), "receive");
                }

            }
        });
        adapter = new InBoxAdapter(mStateIsEdit);

        listView.setAdapter(adapter);
        if (page == FIRST_PAGE) {
            //默认不加载缓存的数据
            loadData(false, FIRST_PAGE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        page = FIRST_PAGE;
        if (page == FIRST_PAGE) {
            //默认不加载缓存的数据
            loadData(false, FIRST_PAGE);
        }
//        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.home, R.id.edit, R.id.select_all, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                getActivity().finish();
                break;
            case R.id.edit:
                mStateIsEdit = !mStateIsEdit;

                if (mStateIsEdit) {
                    edit.setText("取消");
                    ll_edit.setVisibility(View.VISIBLE);
                } else {
                    edit.setText("编辑");
                    ll_edit.setVisibility(View.GONE);
                }
                adapter.setmStateIsEdit(mStateIsEdit);

                break;
            case R.id.select_all:

                if (mStateIsSelectAll) {
                    mStateIsSelectAll = false;
                    selectAll(mStateIsSelectAll);
                    select_all.setText("全选");
                } else {

                    mStateIsSelectAll = true;
                    selectAll(mStateIsSelectAll);
                    select_all.setText("取消全选");
                }

                break;
            case R.id.delete:

                new AlertDialog.Builder(getActivity()).setMessage("确定要删除邮件吗？").setNegativeButton("是", (dialog, which) -> deleteBox()).setPositiveButton
                        ("否", null).create().show();

                break;

        }
    }

    private void setSelectAllState() {
        int sum = list.size();

        int selectCount = 0;
        for (int i = 0; i < list.size(); i++) {
            ReceiveBoxResponse bean = list.get(i);
            if (bean.isState()) {
                selectCount++;
            }
        }

        if (sum == selectCount) {
            mStateIsSelectAll = true;
            select_all.setText("取消全选");
        } else {
            mStateIsSelectAll = false;
            select_all.setText("全选");
        }
    }

    private void deleteBox() {
        StringBuffer deleteBoxIds = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            ReceiveBoxResponse bean = list.get(i);
            if (bean.isState()) {
                deleteBoxIds.append(String.valueOf(bean.getId())).append(",");
            }
        }

        if ("".equals(deleteBoxIds.toString())) {
            showToast("请选择要删除的选项");
            return;
        } else {
            deleteBoxIds.deleteCharAt(deleteBoxIds.length() - 1);
        }

        DeleteLettersCommand command = new DeleteLettersCommand();
        command.setType(1);
        command.setIds(deleteBoxIds.toString());

        executeCommand(command, restCallback, DELETE);
    }

    private void selectAll(boolean stateIsSelectAll) {
        List<ReceiveBoxResponse> newList = new ArrayList<ReceiveBoxResponse>();
        for (ReceiveBoxResponse bean : list) {
            bean.setState(stateIsSelectAll);
            newList.add(bean);
        }
        list = newList;
        adapter.setList(list, mUnreadMtIdList);
    }

    private void loadData(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        ReceiveBoxCommand command = new ReceiveBoxCommand();
        command.setPage(page);

        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<ReceiveBoxResponse>>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, LIST, this);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {

            if (request.getId() == LIST) {
                List<ReceiveBoxResponse> listP = (ArrayList<ReceiveBoxResponse>) (response.getData());
                if(listP.size()==0){
                    maxPage=page;//这就是最大的页数
//                    return true;
                }

                if (page == FIRST_PAGE) {
                    list.clear();
                }
                list.addAll(listP);

                if (list.size() == 0) {
                    refreshLayout.setVisibility(View.GONE);
                    edit.setVisibility(View.GONE);
                    empty_show.setVisibility(View.VISIBLE);
                } else {
                    empty_show.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
//                    edit.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                }

                adapter.setList(list, mUnreadMtIdList);

                if (mStateIsEdit) {
                    setSelectAllState();
                }

            } else if (request.getId() == DELETE) {
                showToast(response.getError());
                loadData(false, FIRST_PAGE);
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
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
            isLoading = state == RestRequest.RUNNING;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}