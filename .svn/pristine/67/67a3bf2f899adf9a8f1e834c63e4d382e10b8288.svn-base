package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.CountdownView;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.data.BindCardDetail;
import com.wangcai.lottery.data.BindCardDetailCommand;
import com.wangcai.lottery.data.LockBankCardCommand;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordTime;
import com.wangcai.lottery.user.UserCentre;
import com.wangcai.lottery.view.adapter.BankCardAdapter;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class BankCardSetting extends BaseFragment {
    private static final int BANKCARD_TRACE_ID = 1;
    private static final int LOCKBANK_TRACE_ID = 2;

    @BindView(R.id.add_menu)
    ImageView add_menu;
    @BindView(R.id.bindcatrd_list)
    ListView bindcardList;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.add_area)
    LinearLayout addArea;
    @BindView(R.id.locking_but)
    Button locking_but;
    @BindView(R.id.locking_time)
    CountdownView lockingTimeBut;

    private List<BindCardDetail> items = new ArrayList();
    private BankCardAdapter adapter;
    private UserCentre userCentre;
    private Boolean editType = new Boolean(null), type = false;
    private BindCardDetail bindCard = new BindCardDetail();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bankcard_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BankCardAdapter(items);
        bindcardList.setAdapter(adapter);
        TextView bottomTips = new TextView(getActivity());
        bottomTips.setPadding(10, 10, 10, 10);
        bottomTips.setText("同一帐号最多绑定４张银行卡\n为了您的帐户资金安全，银行卡“新增”“修改”将在操作完成２小时后，才能向平台发起“提现”。\n\n银行卡信息发生变更，１小时后将自动锁定，锁定银行卡后，将不能对银行卡进行增加/修改/删除操作。如需解除银行卡请联系在线客服。");
        bindcardList.addFooterView(bottomTips);
        adapter.setOnEditItemClickListener(new BankCardAdapter.OnEditItemClickListener() {
            @Override
            public void onEditItemClick(int position, Boolean status) {
                bindCard = items.get(position);
                editType = status;
                type = true;
                if (editType != null) {
                    turnToVerify();
                } else {
                    fundsView();
                }
            }

            @Override
            public void onStatus(BindCardDetail bind, int position) {
                bindCard = bind;
                if (bindCard.isLocked()) {
                    locking();
                } else {
                    RecordTime intendedLockAt = ConstantInformation.getLasttime(ConstantInformation.df.format(new Date()), bindCard.getIntendedWithdrawalAt());
                    if (intendedLockAt.getDay() <= 0 && intendedLockAt.getHour() <= 2 && intendedLockAt.getHour() >= 0) {
                        locking_but.setVisibility(View.GONE);
                        lockingTimeBut.setVisibility(View.VISIBLE);
                        lockingTimeBut.start(ConstantInformation.ONE_HOUR * intendedLockAt.getHour() + ConstantInformation.ONE_MINUTE * intendedLockAt.getMinute() + 1000L * intendedLockAt.getSecond());
                        lockingTimeBut.setOnCountdownEndListener((cv) -> {
                            locking_but.setText("已锁定");
                            adapter.notifyDataSetChanged();
                        });
                    } else {
                        locking();
                    }
                }
            }
        });
        lockingTimeBut.start(0);
        lockingTimeBut.setEnabled(false);
    }

    @OnClick(R.id.locking_time)
    public void clickBankcard(View v) {
        addBankCard();
    }

    @OnClick({android.R.id.home, R.id.add_menu})
    public void onClick(View v) {
        switch (v.getId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.add_menu:
                addBankCard();
                break;
        }
    }

    private void locking() {
        locking_but.setText("已锁定");
        locking_but.setVisibility(View.VISIBLE);
        lockingTimeBut.setVisibility(View.GONE);
        add_menu.setVisibility(View.GONE);
    }

    private void addBankCard() {
        if (items.size() == 0) {
            type = false;
            Bundle bundle = new Bundle();
            bundle.putBoolean("revise", type);
            launchFragment(AddBankCard.class, bundle);
        } else {
            type = false;
            editType = true;
            turnToVerify();
        }
    }

    private void turnToVerify() {
        Bundle bundle = new Bundle();
        if(editType!=null) {
            if (editType) {
                bundle.putBoolean("under", true);
                bundle.putBoolean("revise", type ? true : false);
                bundle.putString("bindCard", GsonHelper.toJson(bindCard));
            } else {
                bundle.putBoolean("under", false);
                bundle.putString("bindCard", GsonHelper.toJson(bindCard));
            }
            launchFragment(CheckBank.class, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        removeAllMenu();
        userCentre = WangCaiApp.getUserCentre();
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 处理锁定
     */
    private void fundsView() {
        View fundsView = LayoutInflater.from(getContext()).inflate(R.layout.view_revise_pswd, null);
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setContentView(fundsView);
        builder.setTitle("资金密码验证");
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("锁定银行卡", (dialog, which) -> {
            unlocked(((TextView) fundsView.findViewById(R.id.funds_password)).getText().toString());
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void loadData() {
        BindCardDetailCommand command = new BindCardDetailCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<BindCardDetail>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BANKCARD_TRACE_ID, this);
        restRequest.execute();
    }

    private void unlocked(String pass) {
        try {
            LockBankCardCommand command = new LockBankCardCommand();
            command.setFundPwd(pass);
            String requestCommand = GsonHelper.toJson(command);
            requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
            command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=lockBankCard", "UTF-8") + userCentre.getKeyApiKey()));
            TypeToken typeToken = new TypeToken<RestResponse>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, LOCKBANK_TRACE_ID, this);
            restRequest.execute();
        } catch (UnsupportedEncodingException e) {
            MobclickAgent.reportError(getActivity(), e.getMessage());
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == BANKCARD_TRACE_ID) { //加载绑定银行卡信息
                items = (ArrayList) response.getData();
                if (items.size() == 0 || items.size() <= 4) {
                    add_menu.setVisibility(View.VISIBLE);
                    if (items.size() == 0) {
                        addArea.setVisibility(View.GONE);
                        tip.setVisibility(View.VISIBLE);
                    } else {
                        addArea.setVisibility(View.VISIBLE);
                        tip.setVisibility(View.GONE);
                    }
                } else if (items.size() > 4) {
                    add_menu.setVisibility(View.GONE);
                }
                adapter.setData(items);
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
                return true;
            }else {
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };
}
