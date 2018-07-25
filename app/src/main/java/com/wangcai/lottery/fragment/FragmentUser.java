package com.wangcai.lottery.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.LazyBaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.data.LogoutCommand;
import com.wangcai.lottery.data.RechargeUrl;
import com.wangcai.lottery.data.RechargeUrlCommand;
import com.wangcai.lottery.data.UserInfo;
import com.wangcai.lottery.data.UserInfoCommand;
import com.wangcai.lottery.pattern.VersionChecker;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 用户信息界面
 */

public class FragmentUser extends LazyBaseFragment {
    private static final String TAG = FragmentUser.class.getSimpleName();

    private static final int ID_USER_INFO = 1;
    private static final int ID_LOGOUT = 2;
    private static final int ID_RECHARGE = 3;

    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_balance)
    TextView userBalance;
    private UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "账号中心", R.layout.fragment_user, true, true);
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
                    if (userInfo != null) {
                        userName.setText(userInfo.getNickname());
                        userBalance.setText(String.format("账号余额：%.4f", userInfo.getAbalance()));
                    }
                    executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
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
        userInfo = WangCaiApp.getUserCentre().getUserInfo();
    }

    @OnClick({R.id.balance_details, R.id.withdraw_deposit, R.id.rebates_setting, R.id.notice, R.id.customer_service,
            R.id.password_setting, R.id.security_setting, R.id.version, R.id.lower_setting, R.id.card_setting})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balance_details:
                launchFragment(BalanceTableFragment.class);
                break;
            case R.id.rebates_setting:
                launchFragment(RebatesSetting.class);
                break;
            case R.id.notice:
                launchFragment(NoticeListFragment.class);
                break;
            case R.id.withdraw_deposit:
//                TwoTableFragment.launch(getActivity(), "快速提现", "提取余额", DrawFragment.class, "提款记录", WithdrawListFragment.class);
                if (userInfo == null) {
                    return;
                }
                if (userInfo.isFundPwdSeted()) {
                    launchFragment(DrawFragment.class);
                } else {
                    tipDialog("请设置资金密码");
                }
                break;
            case R.id.password_setting:
                TwoTableFragment.launch(getActivity(), "密码设置", "登录密码", LoginPasswordSetting.class, "资金密码", CashPasswordSetting.class);
                break;
            case R.id.security_setting:
                launchFragment(SecuritySetting.class);
                break;
            case R.id.customer_service:
                launchFragment(ServiceCenterFragment.class);
                break;
            case R.id.card_setting:
                if (userInfo == null) {
                    return;
                }
                if (userInfo.isFundPwdSeted()) {
                    launchFragment(BankCardSetting.class);
                } else {
                    tipDialog("请设置资金密码");
                }
                break;
            case R.id.lower_setting:
                launchFragment(LowerMemberSetting.class);
                break;
            case R.id.version:
                new VersionChecker(this).startCheck(true);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.logout)
    public void logout() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("退出当前账号");
        builder.setTitle("温馨提示");
        builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT_LEVEL);
        builder.setPositiveButton("退出", (dialog, which) -> {
            LogoutCommand command = new LogoutCommand();
            command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
            executeCommand(command, restCallback, ID_LOGOUT);
            dialog.dismiss();
        });

        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        CustomDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.recharge)
    public void onRecharge() {
        RechargeUrlCommand command = new RechargeUrlCommand();
        command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<RechargeUrl>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, ID_RECHARGE, this);
        restRequest.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void handleExit() {
        getActivity().finish();
        WangCaiApp.getUserCentre().logout();
        RestRequestManager.cancelAll();
        launchFragment(GoldenLogin.class);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_LOGOUT) {
                handleExit();
            } else if (request.getId() == ID_USER_INFO) {
                userInfo = ((UserInfo) response.getData());
                WangCaiApp.getUserCentre().setUserInfo(userInfo);
                if (userInfo != null) {
                    userName.setText(userInfo.getNickname());
                    userBalance.setText(String.format("账号余额：%.4f", userInfo.getAbalance()));
                }
            } else if (request.getId() == ID_RECHARGE) {
                RechargeUrl rechargeUrl = (RechargeUrl) response.getData();
                if (TextUtils.isEmpty(rechargeUrl.getUrl())) {
                    showToast("充值中心正在维护!请耐心等待通知...");
                } else {
                    HtmlFragment.launch(FragmentUser.this, rechargeUrl.getUrl(), "充值中心");
                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (request.getId() != ID_LOGOUT && (errCode == 3004 || errCode == 2016)) {
                signOutDialog(getActivity(), errCode);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (request.getId() == ID_LOGOUT) {
                if (state == RestRequest.RUNNING) {
                    dialogShow("退出中...");
                } else {
                    dialogHide();
                }
            }
        }
    };
}