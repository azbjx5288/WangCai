package com.wangcai.lottery.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.LazyBaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.LotteriesHistory;
import com.wangcai.lottery.data.LotteriesHistoryCommand;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryCode;
import com.wangcai.lottery.data.LotteryListCommand;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordType;
import com.wangcai.lottery.view.adapter.HistoryLotteryAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created on 2016/1/19.
 *
 * @author ACE
 * @功能描述: 历史开奖
 */
public class FragmentLotteryTrend extends LazyBaseFragment {
    private static final String TAG = FragmentLotteryTrend.class.getSimpleName();

    private static final int LOTTERY_HISTORY_TRACE_ID = 1;
    private static final int LOTTERY_TRACE_ID = 2;

    @BindView(R.id.history_listview)
    ListView listView;
    @BindView(R.id.refresh_history)
    PtrClassicFrameLayout refreshLayout;

    private RecordType recordType;
    private List<LotteriesHistory> historyList;
    private HistoryLotteryAdapter historyLotteryAdapter;
    private ArrayList<Lottery> lotteryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "开奖走势", R.layout.fragment_lotterytrend, true, true);
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
                recordType = ConstantInformation.getLotteryModel(getContext());
                try {
                    Thread.sleep(2000);
                    //在这里添加调用接口获取数据的代码
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

    /**
     * 初始化显示彩种
     */
    private void initData() {
        lotteryList.clear();
        if (recordType.getLotteryInfos() != null && recordType.getLotteryInfos().size() > 0) {
            for (int i = 0; i < recordType.getLotteryInfos().size(); i++) {
                Lottery l = recordType.getLotteryInfo(i).lottery;
                if (l.getGameType() == 1) {
                    lotteryList.add(l);
                }
            }
        } else {
            lotteryListLoad();
        }
    }

    private void initView() {
        historyLotteryAdapter = new HistoryLotteryAdapter();
        historyLotteryAdapter.setClickListener((int position, String cname, boolean flag) -> {
            if (lotteryList != null && lotteryList.size() > 0) {
                Lottery lottery = null;
                for (Lottery l : lotteryList) {
                    if (l.getName().equals(cname))
                        lottery = l;
                }
                if (lottery != null) {
                    if (flag) {
                        GameStickNavFragment.launch(FragmentLotteryTrend.this, lottery);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("lottery", lottery);
                        launchFragment(HistoryCodeFragment.class, bundle);
                    }
                }
            }
        });
        listView.setAdapter(historyLotteryAdapter);
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
                        LotteriesHistoryLoad();
                    }
                }, 300);
            }

        });
        // the following are default settings
        refreshLayout.autoRefresh(true);
        refreshLayout.setResistance(1.7f);
        refreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        refreshLayout.setDurationToClose(200); //返回到刷新的位置
        refreshLayout.setDurationToCloseHeader(1000); //关闭头部的时间
        refreshLayout.setLoadingMinTime(1000);
        refreshLayout.setPullToRefresh(false);//当下拉到一定距离时，自动刷新（true），显示释放以刷新（false）
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setKeepHeaderWhenRefresh(true);
    }

    private void LotteriesHistoryLoad() {
        LotteriesHistoryCommand lotteryListCommand = new LotteriesHistoryCommand();
        TypeToken typeToken = new TypeToken<RestResponse<List<LotteriesHistory>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), lotteryListCommand, typeToken, restCallback, LOTTERY_HISTORY_TRACE_ID, this);
        restRequest.execute();
    }

    private void lotteryListLoad() {
        LotteryListCommand lotteryListCommand = new LotteryListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Lottery>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), lotteryListCommand, typeToken, restCallback, LOTTERY_TRACE_ID, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {

        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case LOTTERY_HISTORY_TRACE_ID:
                    List<LotteriesHistory> hlist = (List<LotteriesHistory>) response.getData();
                    historyList=new ArrayList<>();
                    for (LotteriesHistory history : hlist) {
                        List<LotteryCode> lotteryCode = new ArrayList<>();
                        for (int i = 0; i < history.getIssues().size(); i++) {
                            LotteryCode code = history.getIssues().get(i);
                            if (!TextUtils.isEmpty(code.getWnNumber()) || code.getWnNumber().length() != 0) {
                                lotteryCode.add(code);
                            }
                        }
                        history.setIssues(lotteryCode);
                        historyList.add(history);
                    }
                    historyLotteryAdapter.refresh(historyList);
                    break;
                case LOTTERY_TRACE_ID:
                    List<Lottery> list = (ArrayList<Lottery>) response.getData();
                    recordType.setLotteryList(list);
                    for (int i = 0; i < list.size(); i++) {
                        Lottery l = list.get(i);
                        if (l.getGameType() == 1) {
                            lotteryList.add(l);
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
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            switch (request.getId()) {
                case LOTTERY_HISTORY_TRACE_ID:
                    refreshLayout.refreshComplete();
                    break;
            }
        }
    };
}