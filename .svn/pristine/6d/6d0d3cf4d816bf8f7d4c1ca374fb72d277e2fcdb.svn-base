package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryCode;
import com.wangcai.lottery.data.LotteryCodeCommand;
import com.wangcai.lottery.material.TrendData;
import com.wangcai.lottery.pattern.CodeView;
import com.wangcai.lottery.pattern.TrendView;
import com.wangcai.lottery.util.NumbericUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ACE-PC on 2016/3/7.
 */
public class ChartTrendFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = ChartTrendFragment.class.getSimpleName();
    private static final int LIST_HISTORY_CODE_ID = 1;
    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;

    @BindView(R.id.linear_codelayout)
    LinearLayout codelayout;
    @BindView(R.id.linear_chartlayout)
    LinearLayout chartlayout;
    @BindView(R.id.isNeedLink)
    CheckBox isNeedLink;
    @BindView(R.id.spinner)
    Spinner spinner;

    private Lottery lottery;
    private TrendData trend;
    private ArrayList<CodeView> codeListView = new ArrayList<>();
    private ArrayList<TrendView> trendListView = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> datalist = Arrays.asList("20", "40", "50");
    private boolean flagStyle = true;   //走势号码 显示方式。true 默认 单列单号显示，false 单列 多号显示

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_charttrend, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();

        spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, datalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        isNeedLink.setChecked(true);
        isNeedLink.setOnCheckedChangeListener(this);
        loadCodeList(FIRST_PAGE, 20);
    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    private void displayStyle() {
        if (trend == null) {
            showToast("暂无开奖数据");
            return;
        }
        codeListView.clear();
        trendListView.clear();
        if (lottery.getSeriesId() == 7) {
            createCodeLayout(new String[]{"期号"});
        } else {
            createCodeLayout(new String[]{"期号", "开奖号码"});
        }

        switch (lottery.getSeriesId()) {
            case 2://山东11选5
                createSyxwlayout(new String[]{"万位", "千位", "百位", "十位", "个位"});
                break;
            case 1://重庆时时彩
            case 3: //F3D
                if (trend.getTrendData().length > 3)
                    createSsclayout(new String[]{"万位", "千位", "百位", "十位", "个位"});
                else
                    createSsclayout(new String[]{"百位", "十位", "个位"});
                break;
            case 4: //快三
                createKslayout(new String[]{"百位", "十位", "个位"});
                break;
            case 5://PK拾
                createSyxwlayout(new String[]{"冠", "亚", "季", "四", "五", "六", "七", "八", "九", "十"});
                break;
            case 7:
                createKl8Layout(new String[]{"开奖号码"});
                break;
        }
    }

    private void addCodeView(View v, String title) {
        CodeView codeView = new CodeView(v, title);
        codeListView.add(codeView);
    }

    private void addTrendView(View v, String title) {
        TrendView trendView = new TrendView(v, title);
        trendListView.add(trendView);
    }

    private void createCodeLayout(String[] name) {
        codelayout.removeAllViews();
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(codelayout.getContext()).inflate(R.layout.view_code_column, null, false);
            addCodeView(view, name[i]);
            views[i] = view;
        }

        for (View view : views) {
            codelayout.addView(view);
        }
    }

    //时时彩
    private void createSsclayout(String[] name) {
        chartlayout.removeAllViews();
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(chartlayout.getContext()).inflate(R.layout.view_trend_column, null, false);
            addTrendView(view, name[i]);
            views[i] = view;
        }

        for (View view : views) {
            chartlayout.addView(view);
        }
    }

    //快三
    private void createKslayout(String[] name) {
        chartlayout.removeAllViews();
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(chartlayout.getContext()).inflate(R.layout.view_trend_column3, null, false);
            addTrendView(view, name[i]);
            views[i] = view;
        }

        for (View view : views) {
            chartlayout.addView(view);
        }
    }

    //快乐8
    private void createKl8Layout(String[] name) {
        chartlayout.removeAllViews();
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(chartlayout.getContext()).inflate(R.layout.view_trend_column_kl8, null, false);
            addTrendView(view, name[i]);
            views[i] = view;
        }

        for (View view : views) {
            chartlayout.addView(view);
        }
    }

    //十一选五
    private void createSyxwlayout(String[] name) {
        chartlayout.removeAllViews();
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(chartlayout.getContext()).inflate(R.layout.view_trend_column2, null, false);
            addTrendView(view, name[i]);
            views[i] = view;
        }

        for (View view : views) {
            chartlayout.addView(view);
        }
    }

    private void setData() {
        if (trend != null) {
            for (int i = 0, size = codeListView.size(); i < size; i++) {
                CodeView codeView = codeListView.get(i);
                if (i == 0) {
                    codeView.setCodeData(trend.getIssue());
                } else {
                    codeView.setCodeData(trend.getCodeData());
                }
                codeView.requestLayout();
            }
            for (int i = 0, size = trendListView.size(); i < size; i++) {
                TrendView trendView = trendListView.get(i);
                if (flagStyle) {
                    trendView.setTrendData(trend.getTrendData()[i]);
                } else {
                    trendView.setTrendData(trend.getTrendData());
                }
                trendView.requestLayout();
            }
        }
    }

    private void loadCodeList(int page, int number) {
        this.page = page;
        LotteryCodeCommand command = new LotteryCodeCommand();
        command.setLotteryId(lottery.getId());
        command.setCount(number);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<LotteryCode>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, LIST_HISTORY_CODE_ID, this);
        restRequest.execute();
    }

    /**
     * @param data
     */
    private void resolveCode(List<LotteryCode> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        int item = 0;

        switch (lottery.getSeriesId()) {
            case 2://山东11选5
                item = 5;
                break;
            case 5://PK拾
                item = 10;
                break;
            case 8://快乐十二
                item = 5;
                break;
            case 9://快乐十分
                item = 8;
                break;
            case 1://时时彩
                item = 5;
                break;
            case 3: //F3D
            case 4: //快三
                item = 3;
                break;
            case 7:
                flagStyle = false;
                item = 20;
                break;
        }

        int splitNo = 0;
        switch (lottery.getId()) {
            case 13:
            case 14:
            case 17:
            case 19:
            case 20:
                splitNo = 4;
                break;
            case 2:
            case 6:
            case 8:
            case 9:
            case 22:
            case 27:
            case 32:
                splitNo = 2;
                break;
            default: //  1 7 10 11 12 15 16 18 21 28 30 33 39 41 42 44 48 49 57 59
                splitNo = 3;
        }

        String[][] issues = new String[data.size()][1];
        String[][] codeData = new String[data.size()][item];
        String[][] trendSingleData = new String[item][data.size()]; //列内显示单球
        String[][] trendMoreData = new String[data.size()][item]; //列内显示多球
        for (int i = 0, size = data.size(); i < size; i++) {
            LotteryCode code = data.get(i);
            int index = code.getIssue().indexOf("-");
            String issuestr = "";
            if (index != -1) {
                issuestr = code.getIssue().substring(index + 1, code.getIssue().length());
            } else {
                if (code.getIssue().length() > splitNo) {
                    issuestr = code.getIssue().substring(code.getIssue().length() - splitNo, code.getIssue().length());
                } else {
                    issuestr = code.getIssue();
                }
            }
            issues[i][0] = issuestr;
            switch (lottery.getSeriesId()) {
                case 2://11选5
                case 5://PK拾
                case 8://快乐十二
                case 9://快乐十分
                case 7://快乐8
                    String[] syxwitem = code.getWnNumber().split(" ");
                    if (syxwitem.length > 1) {
                        for (int j = 0; j < syxwitem.length; j++) {
                            codeData[i][j] = NumbericUtils.isNumeric(syxwitem[j]) ? syxwitem[j] : "0";
                            if (flagStyle) {
                                trendSingleData[j][i] = NumbericUtils.isNumeric(syxwitem[j]) ? syxwitem[j] : "0";
                            } else {
                                trendMoreData[i][j] = NumbericUtils.isNumeric(syxwitem[j]) ? syxwitem[j] : "0";
                            }
                        }
                    } else {
                        for (int j = 0; j < item; j++) {
                            codeData[i][j] = "0";
                            if (flagStyle) {
                                trendSingleData[j][i] = "0";
                            } else {
                                trendMoreData[i][j] = "0";
                            }
                        }
                    }
                    break;
                case 1://时时彩
                case 3: //F3D
                case 4: //快三
                    String[] sscitem = code.getWnNumber().split("");
                    StringBuffer sb = new StringBuffer();
                    for (int s = 0, length = sscitem.length; s < length; s++) {
                        if ("".equals(sscitem[s])) {
                            continue;
                        }
                        sb.append(sscitem[s]);
                        if (s != sscitem.length - 1) {
                            sb.append(";");
                        }
                    }
                    //用String的split方法分割，得到数组
                    sscitem = sb.toString().split(";");
                    if (sscitem.length > 1) {
                        for (int j = 0, length = sscitem.length; j < length; j++) {
                            codeData[i][j] = NumbericUtils.isNumeric(sscitem[j]) ? sscitem[j] : "0";
                            trendSingleData[j][i] = NumbericUtils.isNumeric(sscitem[j]) ? sscitem[j] : "0";
                        }
                    } else {
                        for (int j = 0; j < item; j++) {
                            codeData[i][j] = "0";
                            trendSingleData[j][i] = "0";
                        }
                    }
                    break;
            }
        }

        trend = new TrendData();
        trend.setIssue(issues);
        trend.setCodeData(codeData);
        trend.setTrendData(flagStyle ? trendSingleData : trendMoreData);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == LIST_HISTORY_CODE_ID) {
                resolveCode((ArrayList<LotteryCode>) response.getData());
                displayStyle();
                setData();
                showHideLines(isNeedLink.isChecked());
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
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        showHideLines(isChecked);
    }

    private void showHideLines(boolean isChecked) {
        if (trend != null) {
            for (int i = 0, size = trendListView.size(); i < size; i++) {
                TrendView view = trendListView.get(i);
                view.showHideLines(isChecked);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int number = Integer.parseInt(datalist.get(position));
        loadCodeList(FIRST_PAGE, number);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 错误参数与成功参数提示窗
     *
     * @param title
     * @param msg
     */
    private void tipDialog(String title, String msg) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("知道了", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }
}
