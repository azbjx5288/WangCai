package com.wangcai.lottery.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.component.TabPageAdapter;
import com.wangcai.lottery.component.WrapContentHeightViewPager;
import com.wangcai.lottery.data.ChannelInfoCommand;
import com.wangcai.lottery.data.ChannelInfoRecharge;
import com.wangcai.lottery.data.RechargeCallback;
import com.wangcai.lottery.data.RechargeCallbackCommand;
import com.wangcai.lottery.data.RechargeChannelConfig;
import com.wangcai.lottery.data.RechargeConfigCommand;
import com.wangcai.lottery.game.PromptManager;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/6/10.
 */
public class RechargeApply extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = RechargeApply.class.getSimpleName();

    private static final int TRACE_CONFIG_COMMAND = 1;
    private static final int TRACE_CHANNELINFO_COMMAND = 2;
    private static final int TRACE_CALLBACK_COMMAND = 3;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewpagerRecharge)
    WrapContentHeightViewPager viewpagerRecharge;
    @BindView(R.id.amounts_mark)
    TextView amountsMark;
    @BindView(R.id.edit_orderamount)
    EditText editOrderamount;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.currency)
    TextView currency;

    private UserCentre userCentre;
    private List<RechargeChannelConfig> configList = new ArrayList<>();
    private RechargeChannelConfig cardRecharge;
    private ChannelInfoRecharge channelInfo;
    private Drawable[] unselectedIcon;
    private Drawable[] selectedIcon;
    private List<BaseFragment> fragments = new ArrayList<>();
    private TabPageAdapter tabPageAdapter;
    private int annal = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflateView(inflater, container, "快速充值", R.layout.recharge_apply, true, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        getChannelConfigLoad();
    }

    private void applyArguments() {
        userCentre = WangCaiApp.getUserCentre();
    }

    private void init() {
        int[] ids = {R.drawable.icon_wechat, R.drawable.icon_alipay, R.drawable.icon_qqpay, R.drawable.bank_icon};//, R.drawable.icon_online_bank
        unselectedIcon = new Drawable[ids.length];
        selectedIcon = new Drawable[ids.length];
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            unselectedIcon[i] = getResources().getDrawable(ids[i]);
            selectedIcon[i] = DrawableCompat.wrap(getResources().getDrawable(ids[i]).mutate());
            unselectedIcon[i].setBounds(0, 0, unselectedIcon[i].getMinimumWidth(), unselectedIcon[i].getMinimumHeight());
            selectedIcon[i].setBounds(0, 0, selectedIcon[i].getMinimumWidth(), selectedIcon[i].getMinimumHeight());
        }
        viewpagerRecharge.setOffscreenPageLimit(2);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            List<RechargeChannelConfig> filterList = new ArrayList<>();
            for (RechargeChannelConfig config : configList) {
                switch (radioGroup.getChildAt(i).getId()) {
                    /*case R.id.unionpay:
                        if (config.getPaymentTypeId() == 1) {
                            filterList.add(config);
                        }
                        break;*/
                    case R.id.wechatpay:
                        if (config.getPaymentTypeId() == 2) {
                            filterList.add(config);
                        }
                        break;
                    case R.id.alipaypay:
                        if (config.getPaymentTypeId() == 3) {
                            filterList.add(config);
                        }
                        break;
                    case R.id.qqpay:
                        if (config.getPaymentTypeId() == 5) {
                            filterList.add(config);
                        }
                        break;
                }
            }
            PayListFragment payListPage = PayListFragment.newInstance(i, GsonHelper.toJson(filterList));
            payListPage.setOnModeItemClickListener((int selection) -> findChannel(selection));
            payListPage.setOnInItItem((int selection) -> findChannel(selection));
            fragments.add(payListPage);
        }

        radioGroup.setOnCheckedChangeListener(this);
        tabPageAdapter = new TabPageAdapter(getFragmentManager(), fragments);
        viewpagerRecharge.setAdapter(tabPageAdapter);
        viewpagerRecharge.setOnPageChangeListener(onPageChangeListener);
        radioGroup.check(radioGroup.getChildAt(0).getId());

        editOrderamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int len = s.toString().length();
                if (len == 1 && text.equals("0")) {
                    s.clear();
                }
            }
        });

        //        selectPage(0);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            //根据用户选中的按钮修改按钮样式
            selectPage(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewpagerRecharge.setCurrentItem(position, true);
        RadioButton select = (RadioButton) radioGroup.getChildAt(position);
        select.setCompoundDrawables(selectedIcon[position], null, null, null);
        findChannel(((PayListFragment) fragments.get(position)).getChannelSelection());
    }

    private void findChannel(int selection) {
        if (annal != selection) {
            annal = selection;
            cardRecharge = null;
            for (RechargeChannelConfig config : configList) {
                if (selection == config.getId()) {
                    cardRecharge = config;
                    String currencyAmount = getActivity().getResources().getString(R.string.is_currency_amount);
                    currencyAmount = StringUtils.replaceEach(currencyAmount, new String[]{"MIN", "MAX"}, new String[]{String.valueOf(cardRecharge.getMinAmount()), String.valueOf(cardRecharge.getMaxAmount())});
                    currency.setText(Html.fromHtml(currencyAmount).toString());
                }
            }
            /*if (cardRecharge != null && cardRecharge.isQr()) {
                tipDialog(0, true);
            }*/
        }
    }

    public void tipDialog(double opt, boolean dupo) {
        double amount = 0.00;
        if (opt > cardRecharge.getMinAmount()) {
            Random rand = new Random();
            if (dupo) {
                amount = cardRecharge.getMinAmount() + rand.nextFloat() * cardRecharge.getMaxAmount();
            } else {
                if (cardRecharge.getMinAmount() == opt) {
                    opt = opt + (0 + rand.nextDouble() * 0.99);
                    amount = opt;
                } else {
                    opt = opt - (0 + rand.nextDouble() * 0.99);
                    amount = opt;
                }
            }
            String finalAmount = String.format("%.2f", amount);

            CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
            builder.setMessage("为提高您的充值到帐速度，当前可受理的充值金额为 " + finalAmount + " 元");
            builder.setTitle("温馨提示");
            builder.setLayoutSet(DialogLayout.SINGLE);
            builder.setPositiveButton("知道了", (dialog, which) ->
            {
                editOrderamount.setText(finalAmount);
                dialog.dismiss();
            });
            builder.create().show();
        } else {
            editOrderamount.setText(String.format("%.2f", opt));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.wechatpay: // 微信支付
                selectPage(0);
                break;
            case R.id.alipaypay: // 支付宝
                selectPage(1);
                break;
            case R.id.qqpay: // QQ
                selectPage(2);
                break;
            /*case R.id.unionpay: // 银联
                selectPage(3);
                break;*/
        }
    }

    @OnClick({R.id.quota_50, R.id.quota_100, R.id.quota_500, R.id.quota_1000})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quota_50:
                if (cardRecharge != null && cardRecharge.isQr()) {
                    double opt = Double.parseDouble(((TextView) findViewById(R.id.quota_50)).getText().toString());
                    tipDialog(opt, false);
                } else {
                    editOrderamount.setText(((TextView) findViewById(R.id.quota_50)).getText());
                }
                break;
            case R.id.quota_100:
                if (cardRecharge != null && cardRecharge.isQr()) {
                    double opt = Double.parseDouble(((TextView) findViewById(R.id.quota_100)).getText().toString());
                    tipDialog(opt, false);
                } else {
                    editOrderamount.setText(((TextView) findViewById(R.id.quota_100)).getText());
                }
                break;
            case R.id.quota_500:
                if (cardRecharge != null && cardRecharge.isQr()) {
                    double opt = Double.parseDouble(((TextView) findViewById(R.id.quota_500)).getText().toString());
                    tipDialog(opt, false);
                } else {
                    editOrderamount.setText(((TextView) findViewById(R.id.quota_500)).getText());
                }
                break;
            case R.id.quota_1000:
                if (cardRecharge != null && cardRecharge.isQr()) {
                    double opt = Double.parseDouble(((TextView) findViewById(R.id.quota_1000)).getText().toString());
                    tipDialog(opt, false);
                } else {
                    editOrderamount.setText(((TextView) findViewById(R.id.quota_1000)).getText());
                }
                break;
        }
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitDone() {
        if (cardRecharge == null) {
            findChannel(((PayListFragment) tabPageAdapter.getCurrentFragment()).getChannelSelection());
        }

        if (cardRecharge == null) {
            showToast("此充值渠道正在维护", Toast.LENGTH_SHORT);
            return;
        }
        String moneyStr = editOrderamount.getText().toString();
        if (moneyStr.isEmpty()) {
            showToast("请输入充值金额", Toast.LENGTH_SHORT);
            return;
        }

        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(moneyStr);
        if (match.matches() == false) {
            showToast("请输入正确金额", Toast.LENGTH_SHORT);
            return;
        }

        double money = Double.valueOf(moneyStr);
        if (money < cardRecharge.getMinAmount() || money >= cardRecharge.getMaxAmount()) {
            String tip = money < cardRecharge.getMinAmount() ? "最小" + cardRecharge.getMinAmount() : "最大" + cardRecharge.getMaxAmount();
            showToast("充值金额" + tip + "元", Toast.LENGTH_SHORT);
            return;
        }

        if (money != cardRecharge.getMinAmount() && money % 10 == 0) {
            tipDialog(Double.parseDouble(editOrderamount.getText().toString()), false);
            return;
        }

        getChannelInfoLoad(money);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (channelInfo != null) {
            if (!channelInfo.getOrderNo().isEmpty()) {
                getRechargeCallback();
            }
        }
    }

    /**
     * 获取充值渠道
     */
    private void getChannelConfigLoad() {
        RechargeConfigCommand configCommand = new RechargeConfigCommand();
        configCommand.setToken(userCentre.getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<RechargeChannelConfig>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), configCommand, typeToken, restCallback, TRACE_CONFIG_COMMAND, this);
        restRequest.execute();
    }

    /**
     * 获取充值数据
     *
     * @param money
     */
    private void getChannelInfoLoad(double money) {
        ChannelInfoCommand channelInfoCommand = new ChannelInfoCommand();
        channelInfoCommand.setPlatformId(cardRecharge.getId());
        channelInfoCommand.setAmount(money);
        channelInfoCommand.setToken(userCentre.getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<ChannelInfoRecharge>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), channelInfoCommand, typeToken, restCallback, TRACE_CHANNELINFO_COMMAND, this);
        restRequest.execute();
    }

    /**
     * 充值回调
     */
    private void getRechargeCallback() {
        RechargeCallbackCommand callbackCommand = new RechargeCallbackCommand();
        callbackCommand.setOrderNo(channelInfo.getOrderNo());
        callbackCommand.setToken(userCentre.getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<RechargeCallback>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), callbackCommand, typeToken, restCallback, TRACE_CALLBACK_COMMAND, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case TRACE_CONFIG_COMMAND: {
                    if (response != null && response.getData() instanceof ArrayList) {
                        configList = (ArrayList) response.getData();
                        init();
                    }
                    break;
                }
                case TRACE_CHANNELINFO_COMMAND: {
                    if (response != null && response.getData() instanceof ChannelInfoRecharge) {
                        channelInfo = (ChannelInfoRecharge) response.getData();
                        if (channelInfo != null) {
                            if (!TextUtils.isEmpty(channelInfo.getRechargeUrl())) {
                                Bundle bundle = new Bundle();
                                bundle.putString("url", channelInfo.getRechargeUrl());
                                Intent intent = new Intent(getActivity(), HtmlFragment.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                showToast("此充值渠道正在维护", Toast.LENGTH_SHORT);
                            }
                        }
                    }
                    break;
                }
                case TRACE_CALLBACK_COMMAND:
                    if (response != null && response.getData() instanceof RechargeCallback) {
                        RechargeCallback callbackInfo = (RechargeCallback) response.getData();
                        CustomDialog dialog = PromptManager.showCustomTipDialog(getActivity(), "温馨提示", callbackInfo.getMessage());
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 5018) {
                showToast(errDesc);
            } else if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                dialogShow("进行中...");
            } else {
                hideWaitProgress();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
