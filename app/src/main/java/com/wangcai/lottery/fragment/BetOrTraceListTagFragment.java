package com.wangcai.lottery.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.LazyBaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.Bet;
import com.wangcai.lottery.data.BetListCommand;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Trace;
import com.wangcai.lottery.data.TraceListCommand;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordType;
import com.wangcai.lottery.view.adapter.GameHistoryAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DoublePicker;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by ACE-PC on 2016/7/26.
 */
public class BetOrTraceListTagFragment extends LazyBaseFragment {

    private static final String TAG = BetOrTraceListTagFragment.class.getSimpleName();

    @BindView(R.id.lotteryButton)
    TextView lotteryButton;
    @BindView(R.id.stateButton)
    TextView stateButton;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.loadMoreContainer)
    LoadMoreListViewContainer loadMoreContainer;

    private static final int BET_LIST_COMMAND = 1;//请求状态ID 投注记录
    private static final int TRACE_LIST_COMMAND = 2;//请求状态ID 追号记录
    private static final int RECORD_TYPE_SELECT = 0; //记录类型
    private static final int FIRST_PAGE = 1;  //默认页数
    private int currentLotterySelectedIndex = 0; //记录选择器选择
    private int currentLotteryQueryConditionType = -1; //当前彩种ID
    private int currentRecordQueryConditionType = RECORD_TYPE_SELECT;    //0 投注记录 1 追号记录
    private int page = FIRST_PAGE;         //记录页数
    private boolean isLoading = false;     //加载状态

    private Lottery initLottery = null;
    private DoublePicker picker;    //滚轮选择器
    private GameHistoryAdapter adapter;    //记录布局器
    private RecordType recordType;
    private List items = new ArrayList();    //记录查询数据
    private static ArrayList<String> firstData = new ArrayList<>();
    private static ArrayList<String> secondData = new ArrayList<>(Arrays.asList("投注记录", "追号记录"));

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "游戏记录", R.layout.fragment_bet_or_trace_list, true, true);
    }

    @Override
    public void onFirstUserVisible() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //显示加载进度对话框
                dialogShow("正在加载...");
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                    //在这里添加调用接口获取数据的代码
                    recordType = ConstantInformation.getLotteryModel(getContext());
                    initData();
                    //doSomething()
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    // 加载成功
                    initView();
                    // 渲染页面
                } else {
                    // 加载失败
                }
                //关闭对话框
                dialogHide();
            }
        }.execute();
    }

    public void onUserVisible() {
        defaultChoice();
        initViewData();
    }

    private void initView() {
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setVisibility(View.VISIBLE);
                refreshLayout.autoRefresh();
            }
        });

        // 为listview的创建一个headerview,注意，如果不加会影响到加载的footview的显示！
        View headerMarginView = new View(getContext());
        headerMarginView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LocalDisplay.dp2px(20)));
        listView.addHeaderView(headerMarginView);

        adapter = new GameHistoryAdapter(getContext(), this);
        listView.setAdapter(adapter);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = FIRST_PAGE;
                        autoRefresh(page);
                    }
                }, 300);
            }
        });
        // the following are default settings
        refreshLayout.setResistance(1.7f);
        refreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        refreshLayout.setDurationToClose(200);
        refreshLayout.setDurationToCloseHeader(1000);
        refreshLayout.setLoadingMinTime(1000);
        refreshLayout.setPullToRefresh(true);
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setKeepHeaderWhenRefresh(true);

        loadMoreContainer.setAutoLoadMore(true);//设置是否自动加载更多
        loadMoreContainer.useDefaultHeader();
        //5.添加加载更多的事件监听
        loadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer moreContainer) {
                //模拟加载更多的业务处理
                loadMoreContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = page + 1;
                        autoRefresh(page);
                    }
                }, 300);
            }
        });

        picker = new DoublePicker(getActivity(), firstData, secondData);
        picker.setDividerVisible(true);
        picker.setCycleDisable(true);
        picker.setTextColor(getResources().getColor(R.color.app_main));
        picker.setDividerColor(getResources().getColor(R.color.app_main));
        picker.setContentPadding(15, 10);
        picker.setOnPickListener(new DoublePicker.OnPickListener() {
            @Override
            public void onPicked(int selectedFirstIndex, int selectedSecondIndex) {
                if (firstData.size() > 0 && secondData.size() > 0) {
                    showToast(firstData.get(selectedFirstIndex) + " " + secondData.get(selectedSecondIndex));
                    if (selectedFirstIndex == 0) {
                        currentLotteryQueryConditionType = -1;
                        ConstantInformation.setLotteryEmblem(null);
                    } else {
                        Lottery currentLottery = recordType.getLotteryInfo(selectedFirstIndex - 1).lottery;
                        ConstantInformation.setLotteryEmblem(currentLottery);
                        currentLotteryQueryConditionType = currentLottery.getId();
                    }
                    currentLotterySelectedIndex = selectedFirstIndex; //当前选择彩种
                    lotteryButton.setText(firstData.get(currentLotterySelectedIndex));

                    currentRecordQueryConditionType = selectedSecondIndex; //投注类型
                    stateButton.setText(secondData.get(currentRecordQueryConditionType));

                    if (refreshLayout != null) {
                        refreshLayout.autoRefresh();
                    }
                }
            }
        });
        initViewData();
    }

    private void initData() {
        firstData.clear();
        if (recordType.getLotteryInfos() != null && recordType.getLotteryInfos().size() > 0) {
            InitLotteryData();
            defaultChoice();
        }
    }

    private void initViewData() {
        if (initLottery != null) {
            currentLotteryQueryConditionType = initLottery.getId();
            lotteryButton.setText(initLottery.getName());
        } else {
            currentLotteryQueryConditionType = -1;
            if (firstData.size() > 0) {
                lotteryButton.setText(firstData.get(currentLotterySelectedIndex));
            } else {
                lotteryButton.setText("全部记录");
            }
        }
        stateButton.setText(secondData.get(currentRecordQueryConditionType));
        refreshLayout.autoRefresh(true);
        picker.setSelectedIndex(currentLotterySelectedIndex, currentRecordQueryConditionType);
    }

    /**
     * 初使化数据
     */

    private void InitLotteryData() {
        firstData.add("全部记录");
        for (int i = 0; i < recordType.getLotteryInfos().size(); i++) {
            Lottery l = recordType.getLotteryInfo(i).lottery;
            if (l.getGameType() == 1) {
                firstData.add(l.getName());
            }
        }
    }

    /**
     * 初始化显示彩种
     */
    private void defaultChoice() {
//        if (ConstantInformation.getLotteryEmblem() != null) {
            initLottery = ConstantInformation.getLotteryEmblem();
//        }

        if (initLottery != null) {
            for (int i = 0; i < firstData.size(); i++) {
                String name = firstData.get(i);
                if (name.equals(initLottery.getName())) {
                    currentLotterySelectedIndex = i;
                }
            }
        }
    }

    private void autoRefresh(int page) {
        switch (currentRecordQueryConditionType) {
            case 0:
                loadBetList(page);
                break;
            case 1:
                loadTraceList(page);
                break;
        }
    }

    @OnClick({R.id.lotteryButton, R.id.stateButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lotteryButton:
            case R.id.stateButton:
                picker.show();
                break;
        }
    }


    private void loadBetList(int page) {
        if (isLoading) {
            return;
        }
        BetListCommand command = new BetListCommand();
        if (currentLotteryQueryConditionType != -1) {
            command.setLotteryId(currentLotteryQueryConditionType + "");
        }
        command.setPage(page);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Bet>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BET_LIST_COMMAND, this);
        restRequest.execute();
    }

    private void loadTraceList(int page) {
        if (isLoading) {
            return;
        }
        TraceListCommand command = new TraceListCommand();
        if (currentLotteryQueryConditionType != -1) {
            command.setLotteryId(currentLotteryQueryConditionType + "");
        }
        command.setPage(page);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Trace>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, TRACE_LIST_COMMAND, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case BET_LIST_COMMAND:
                case TRACE_LIST_COMMAND:
                    if (response.getData() == null) {
                        items.clear();
                    } else {
                        if (page == FIRST_PAGE) {
                            items.clear();
                        }
                        List data = (ArrayList) response.getData();
                        items.addAll(data);
                        adapter.setData(items);
                        if (data.size() == 0 || items.size() >= 100) {
                            loadMoreContainer.loadMoreFinish(false, false);
                        } else {
                            loadMoreContainer.loadMoreFinish(false, true);
                        }
                        if (adapter.getCount() > 0) {
                            empty.setVisibility(View.GONE);
                        } else {
                            empty.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
                return true;
            } else {
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (request.getId() == BET_LIST_COMMAND || request.getId() == TRACE_LIST_COMMAND) {
                isLoading = state == RestRequest.RUNNING;
                refreshLayout.refreshComplete();
                if (state == RestRequest.RUNNING) {
                    dialogShow("正在加载...");
                } else {
                    dialogHide();
                }
            }
        }
    };
}
