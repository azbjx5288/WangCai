package com.wangcai.lottery.pattern;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CountdownView;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.WarpLinearLayout;
import com.wangcai.lottery.data.IssueInfo;
import com.wangcai.lottery.data.IssueInfoCommand;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryCode;
import com.wangcai.lottery.data.LotteryCodeCommand;
import com.wangcai.lottery.game.PromptManager;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordTime;
import com.wangcai.lottery.user.UserCentre;
import com.wangcai.lottery.util.NumbericUtils;
import com.google.gson.reflect.TypeToken;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * 开奖与销售 倒计时
 * Created by ACE-PC on 2016/1/18.
 */
public class TitleTimingView {
    private static final String TAG = TitleTimingView.class.getSimpleName();
    private final long TRACK_TIME_INTERVAL_FAST = 8;
    private final long TRACK_TIME_INTERVAL_SLOW = 30;
    private static final int ISSUE_TRACE_ID = 2;
    private static final int OPENISSUE_TRACE_ID = 3;
    private static final long ONE_DAY = 60L * 60 * 1000 * 24;
    private static final long ONE_HOUR = 60L * 60 * 1000;
    private static final long ONE_MINUTE = 60L * 1000;
    private UserCentre userCentre;
    private Activity activity;
    private View view;
    private LinearLayout lastIssueLayout;
    private WarpLinearLayout lastWarpLayout;
    private LinearLayout rankLayout;
    private TextView lotterynameView;
    private TextView salesIssueView;
    private CountdownView salesTimeView;
    private OnClickTrendListener onClickrendListener;
    private OnUpdatedListener onUpdatedListener;
    private boolean statusflag = true;
    private Lottery lottery;
    private int methodID;
    private String issue = "";
    private LotteryCode lastIssueInfo;
    private IssueInfo issueInfo;
    private DateFormat df = new SimpleDateFormat("yyyyMMdd");
    private static final int TAIL_DIGIT = 4;

    /**
     * 带Context的构造器
     *
     * @param view
     */

    public TitleTimingView(Activity activity, View view, Lottery lottery, int methodID) {
        this.view = view;
        this.activity = activity;
        this.lottery = lottery;
        this.methodID = methodID;
        this.userCentre = WangCaiApp.getUserCentre();
        create();
    }

    public TitleTimingView(LayoutInflater inflater, int id) {
        this.view = inflater.inflate(id, null);
        create();
    }

    private Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public LotteryCode getLotteryCode() {
        return lastIssueInfo;
    }

    public void setLotteryCode(LotteryCode lastIssueInfo) {
        this.lastIssueInfo = lastIssueInfo;
    }

    public IssueInfo getIssueInfo() {
        return issueInfo;
    }

    public void setIssueInfo(IssueInfo issueInfo) {
        this.issueInfo = issueInfo;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    private void create() {
        lastIssueLayout = view.findViewById(R.id.last_issue);
        lastWarpLayout = view.findViewById(R.id.last_warp_layout);
        rankLayout = view.findViewById(R.id.rank_layout);
        lotterynameView = view.findViewById(R.id.timing_title_lotteryname);
        salesIssueView = view.findViewById(R.id.timing_title_salesissue);
        salesTimeView = view.findViewById(R.id.timing_title_salestime);
        ImageView trendButton = view.findViewById(R.id.trend_button);
        trendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickrendListener != null) {
                    onClickrendListener.onClickTrendListener();
                }
            }
        });

        setListener();
    }

    public void setOnClickTrendListener(OnClickTrendListener onClickrendListener) {
        this.onClickrendListener = onClickrendListener;
    }

    public void setOnUpdatedListener(OnUpdatedListener onUpdatedListener) {
        this.onUpdatedListener = onUpdatedListener;
    }

    public interface OnClickTrendListener {
        void onClickTrendListener();
    }

    public interface OnUpdatedListener {
        void onUpdatedListener(String issue);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        salesTimeView.setOnCountdownEndListener((cv) -> {
            //当销售结束后 开启开奖倒计时
            GetSales();
            GetCode();
        });
        salesTimeView.start((long) 0);
        GetAwardPeriod();
        GetCode();
    }

    public void stop() {
        if (salesTimeView != null)
            salesTimeView.stop();
        closeRequest();
    }

    /**
     * 更新销售信息
     */
    private void setUpdateSalesLottery(String salesIssue, long time) {
        Date curDate = new Date(System.currentTimeMillis());
        salesTimeView.start(time);
        salesIssueView.setText(salesIssue != null && salesIssue.length() > 0
                ? salesIssue.substring(salesIssue.length() - TAIL_DIGIT, salesIssue.length()) + "期截止时间："
                : df.format(curDate) + "-" + "****");
    }

    /**
     * 更新开奖号码
     */
    private void setUpdateOpenLottery(String openIssue, String codeOpen, long interval) {
        Date curDate = new Date(System.currentTimeMillis());

        String sequence = openIssue != null && openIssue.length() > 0 ? openIssue : df.format(curDate);
        setIssue(sequence);

        if (onUpdatedListener != null) {
            onUpdatedListener.onUpdatedListener(getIssue());
        }

        String issueSequence = activity.getResources().getString(R.string.is_issue_trend);
        issueSequence = StringUtils.replaceEach(issueSequence, new String[]{"ISSUETREND"}, new String[]{splitIssue(sequence)});

        lotterynameView.setText(issueSequence);
        lastIssueLayout.removeAllViews();
        lastWarpLayout.removeAllViews();
        if (!"".equals(codeOpen)) {
            String[] list;
            if (codeOpen.contains(" ")) {
                list = codeOpen.split(" ");
            } else {
                list = new String[codeOpen.length()];
                for (int i = 0; i < list.length; i++) {
                    list[i] = String.valueOf(codeOpen.charAt(i));
                }
            }

            if (lottery.getSeriesId() == 5) {
                if (methodID == 178) {
                    addDragonTiger(list);
                } else {
                    addCars(list);
                }
            } else if (lottery.getSeriesId() == 7) {
                addCodeOpenKuaile8(list);
            } else {
                addCodeOpen(list);
            }
        }
        GetIntervalTimer(interval);
    }

    private void addDragonTiger(String[] list) {
        rankLayout.setVisibility(View.GONE);
        lastWarpLayout.setVisibility(View.GONE);
        String[] labs = new String[]{"1V10", "2V9", "3V8", "4V7", "5V6"};
        for (int i = 0; i < 5; i++) {
            TextView labView = new TextView(activity);
            labView.setText(labs[i]);
            labView.setTextSize(10);

            CircleTextImageView circleTextImageView = new CircleTextImageView(activity);
            circleTextImageView.setTextColor(Color.WHITE);
            circleTextImageView.setTextSize(50);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80, 80);
            layoutParams.setMargins(10, 0, 10, 0);
            circleTextImageView.setLayoutParams(layoutParams);
            if (NumbericUtils.isNumeric(list[i]) && NumbericUtils.isNumeric(list[(list.length - 1) - i])) {
                if (Integer.valueOf(list[i]) > Integer.valueOf(list[(list.length - 1) - i])) {
                    circleTextImageView.setText("龙");
                    circleTextImageView.setFillColor(activity.getResources().getColor(R.color.dragon));
                } else {
                    circleTextImageView.setText("虎");
                    circleTextImageView.setFillColor(activity.getResources().getColor(R.color.tiger));
                }
            }

            lastIssueLayout.addView(labView);
            lastIssueLayout.addView(circleTextImageView);
        }
    }

    private void addCars(String[] list) {
        for (String code : list) {
            ImageView carView = new ImageView(activity);
            switch (code) {
                case "01":
                    carView.setImageResource(R.drawable.car_01);
                    break;
                case "02":
                    carView.setImageResource(R.drawable.car_02);
                    break;
                case "03":
                    carView.setImageResource(R.drawable.car_03);
                    break;
                case "04":
                    carView.setImageResource(R.drawable.car_04);
                    break;
                case "05":
                    carView.setImageResource(R.drawable.car_05);
                    break;
                case "06":
                    carView.setImageResource(R.drawable.car_06);
                    break;
                case "07":
                    carView.setImageResource(R.drawable.car_07);
                    break;
                case "08":
                    carView.setImageResource(R.drawable.car_08);
                    break;
                case "09":
                    carView.setImageResource(R.drawable.car_09);
                    break;
                case "10":
                    carView.setImageResource(R.drawable.car_10);
                    break;
                default:
                    break;
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            carView.setLayoutParams(layoutParams);
            lastIssueLayout.addView(carView);
            rankLayout.setVisibility(View.VISIBLE);
        }
    }

    private void addCodeOpenKuaile8(String[] list) {
        rankLayout.setVisibility(View.GONE);
        lastIssueLayout.setVisibility(View.GONE);
        lastWarpLayout.setVisibility(View.VISIBLE);
        for (String code : list) {
            View kuaile8Text = LayoutInflater.from(view.getContext()).inflate(R.layout.lottery_trend_item_kuaile8, null);
            TextView textView = kuaile8Text.findViewById(R.id.kuaile8_ball);
            textView.setText(code);
            textView.setVisibility(View.VISIBLE);
            lastWarpLayout.addView(kuaile8Text);
        }
    }

    private void addCodeOpen(String[] list) {
        if (list.length == 3 && lottery.getName().contains("快三")) {
            int[] kuaisanDice = {R.drawable.kuaisan_dice_1, R.drawable.kuaisan_dice_2, R.drawable.kuaisan_dice_3,
                    R.drawable.kuaisan_dice_4, R.drawable.kuaisan_dice_5, R.drawable.kuaisan_dice_6};
            if (NumbericUtils.isNumeric(list[0]) && NumbericUtils.isNumeric(list[1]) && NumbericUtils.isNumeric(list[2])) {
                View layout = LayoutInflater.from(activity).inflate(R.layout.lottery_trend_item_kuaisan, lastIssueLayout, false);
                ImageView dice1 = layout.findViewById(R.id.kuaisanDice1);
                ImageView dice2 = layout.findViewById(R.id.kuaisanDice2);
                ImageView dice3 = layout.findViewById(R.id.kuaisanDice3);
                TextView kuaisanSum = layout.findViewById(R.id.kuaisanSum);
                TextView kuaisanSize = layout.findViewById(R.id.kuaisanSize);
                TextView kuaisanOdd = layout.findViewById(R.id.kuaisanOdd);
                int code1 = Integer.valueOf(NumbericUtils.isNumeric(list[0]) ? list[0] : "1");
                int code2 = Integer.valueOf(NumbericUtils.isNumeric(list[1]) ? list[1] : "1");
                int code3 = Integer.valueOf(NumbericUtils.isNumeric(list[2]) ? list[2] : "1");
                dice1.setImageResource(kuaisanDice[code1 - 1]);
                dice2.setImageResource(kuaisanDice[code2 - 1]);
                dice3.setImageResource(kuaisanDice[code3 - 1]);
                int sum = code1 + code2 + code3;
                kuaisanSum.setText(String.valueOf(sum));
                kuaisanSize.setText(sum > 10 ? "大" : "小");
                kuaisanOdd.setText(sum % 2 == 0 ? "双" : "单");
                lastIssueLayout.addView(layout);
            }
            return;
        }
        lastWarpLayout.setVisibility(View.GONE);
        rankLayout.setVisibility(View.GONE);
        for (String code : list) {
            View viewText = LayoutInflater.from(activity).inflate(R.layout.lottery_ball_item_text, null);
            TextView textView = viewText.findViewById(R.id.ball_text);
            textView.setText(code);
            textView.setVisibility(View.VISIBLE);
            lastIssueLayout.addView(viewText);
        }
    }

    /**
     * 销售完成后 等待开奖倒计时触发
     */
    private void setOpenLottery(long second) {
        statusflag = false;
        salesTimeView.start(ONE_HOUR * 0 + ONE_MINUTE * 0 + 1000L * second);
    }

    /**
     * 获取销售
     */
    private void GetSales() {
        GetAwardPeriod();
    }

    private void GetSales(IssueInfo issueInfo) {
        if (issueInfo != null) {
            int daySales = 0, hourSales = 0, minuteSales = 0, secondSales = 0;
            RecordTime salesTime = ConstantInformation.getLasttime(issueInfo.getServer_time(), issueInfo.getEnd_time());
            if (salesTime != null) {
                statusflag = true;
                daySales = salesTime.getDay() > 0 ? salesTime.getDay() : 0;
                hourSales = salesTime.getHour() > 0 ? salesTime.getHour() : 0;
                minuteSales = salesTime.getMinute() > 0 ? salesTime.getMinute() : 0;
                secondSales = salesTime.getSecond() > 0 ? salesTime.getSecond() : 0;
            }
            setUpdateSalesLottery(issueInfo.getIssue(), ONE_DAY * daySales + ONE_HOUR * hourSales + ONE_MINUTE * minuteSales + 1000L * secondSales);
        } else
            salesTimeView.stop();
    }


    /**
     * 获取号码
     */
    private void GetCode() {
        UpdateOpenPeriod();
    }

    /**
     * 验证开奖数据
     *
     * @param lotteryCode
     */
    private void GetCode(LotteryCode lotteryCode) {
        if (lotteryCode != null) {
            if (TextUtils.isEmpty(getLotteryCode().getWnNumber())) {
                setUpdateOpenLottery(getLotteryCode().getIssue(), setDefault(), Interval());
            } else {
                setUpdateOpenLottery(getLotteryCode().getIssue(), getLotteryCode().getWnNumber(), 0);
            }
        } else {
            setUpdateOpenLottery("xxxxxx", setDefault(), Interval());
        }
    }

    /**
     * 间隔获取
     */
    private void GetIntervalTimer(long interval) {
        salesTimeView.setOnCountdownIntervalListener(interval * 1000L, (cv, t) -> GetCode());
    }

    private String splitIssue(String issue) {
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

        String issueSplit = "";
        if (issue.length() > splitNo) {
            issueSplit = issue.substring(issue.length() - splitNo, issue.length());
        } else {
            issueSplit = issue;
        }
        return issueSplit;
    }

    private String setDefault() {
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
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < item; i++) {
            sb.append("-");
            if (i != item - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private long Interval() {
        long interval = 0;
        switch (getLottery().getId()) {
            case 13://苹果分分彩
            case 16://苹果三分彩
            case 28://苹果五分彩
            case 26://苹果秒秒彩
            case 14://苹果11选5
            case 17://苹果快三分分彩
            case 19: //苹果极速PK10
            case 20: //苹果极速3D
                interval = TRACK_TIME_INTERVAL_FAST;
                break;
            default:
                interval = TRACK_TIME_INTERVAL_SLOW;
                break;
        }
        return interval;
    }

    /**
     * 获取销售期间信息  @param lottery
     */
    private void GetAwardPeriod() {
        IssueInfoCommand command = new IssueInfoCommand();
        command.setLotteryId(lottery.getId());
        TypeToken typeToken = new TypeToken<RestResponse<IssueInfo>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(activity, command, typeToken, restCallback, ISSUE_TRACE_ID, this);
        restRequest.execute();
    }

    private void UpdateOpenPeriod() {
        LotteryCodeCommand command = new LotteryCodeCommand();
        command.setLotteryId(lottery.getId());
        command.setCount(1);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<LotteryCode>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(activity, command, typeToken, restCallback, OPENISSUE_TRACE_ID, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ISSUE_TRACE_ID) {
                setIssueInfo((IssueInfo) response.getData());
                GetSales(getIssueInfo());
            } else if (request.getId() == OPENISSUE_TRACE_ID) {
                if (((ArrayList<LotteryCode>) response.getData()).size() > 0) {
                    setLotteryCode(((ArrayList<LotteryCode>) response.getData()).get(0));
                    GetCode(getLotteryCode());
                } else {
                    Toast.makeText(activity, "没有开奖结果", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004) {
                CustomDialog dialog = PromptManager.showCustomDialog(activity, "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            } else {
                Toast.makeText(activity, errDesc, Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

    private void closeRequest() {
        RestRequestManager.cancelAll(this);
        if(salesTimeView!=null){
            salesTimeView.stop();
        }
    }
}