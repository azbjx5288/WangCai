package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.wangcai.lottery.component.CountdownView;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.data.BackToken;
import com.wangcai.lottery.data.BindCardDetail;
import com.wangcai.lottery.data.CardDetailCommand;
import com.wangcai.lottery.data.ReviseCommand;
import com.wangcai.lottery.data.UserInfo;
import com.wangcai.lottery.data.UserInfoCommand;
import com.wangcai.lottery.data.WithdrawCommand;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordTime;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现页面
 * Created by Alashi on 2016/3/17.
 */
public class DrawFragment extends BaseFragment {

    private static final int ID_USER_INFO = 1;
    private static final int ID_CARD_INFO = 2;
    private static final int ID_APPLY_WITHDRAW = 3;
    private static final int REVISE_TRACE_ID = 4;
    private static final int INVALID_INDEX = -1;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.card_area)
    TextView cardArea;
    @BindView(R.id.card_branch)
    TextView cardBranch;
    @BindView(R.id.fund_password)
    EditText fundPassword;
    @BindView(R.id.draw_money)
    EditText drawMoneyEditText;
    @BindView(R.id.card_info)
    ViewGroup cardInfo;
    @BindView(R.id.card_info_no_card)
    View cardInfoNoCard;
    @BindView(R.id.withdrawals_time)
    CountdownView withdrawalsTime;
    @BindView(R.id.submit)
    Button submit;


    private UserCentre userCentre;
    private ArrayList<BindCardDetail> cardDetails;
    private int lastCheckIndex = INVALID_INDEX;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "快速提现", R.layout.withdraw_deposit, true, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
        money.setText(String.format("%.4f", WangCaiApp.getUserCentre().getUserInfo().getWithdrawable()));
        loadInfo();
        withdrawalsTime.start(0);
    }

    private void loadInfo() {
        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<BindCardDetail>>>() {
        };
        RestRequestManager.executeCommand(getActivity(), new CardDetailCommand(), typeToken, restCallback, ID_CARD_INFO, this);
    }

    private void loadRevise(String pass, int type) {
        if (cardDetails != null) {
            return;
        }
        if (cardDetails.size() > 0) {
            ReviseCommand command = new ReviseCommand();
            command.setCardId(cardDetails.get(0).getId());
            command.setFundPwd(pass);
            command.setAccountName(cardDetails.get(0).getAccountName());
            command.setAccount(cardDetails.get(0).getAccount());
            command.setType(type);
            TypeToken typeToken = new TypeToken<RestResponse<BackToken>>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, REVISE_TRACE_ID, this);
            restRequest.execute();
        }
    }

    @OnClick(R.id.submit)
    public void onClickSubmit() {
        if (TextUtils.isEmpty(fundPassword.getText())) {
            Toast.makeText(getActivity(), "请输入资金密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(drawMoneyEditText.getText())) {
            Toast.makeText(getActivity(), "请输入提款金额", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastCheckIndex == INVALID_INDEX) {
            Toast.makeText(getActivity(), "请选择银行卡", Toast.LENGTH_SHORT).show();
            return;
        }

        double drawMoney = Double.parseDouble(drawMoneyEditText.getText().toString());
        if (drawMoney < 100) {
            Toast.makeText(getActivity(), "提款金额不能小于100元", Toast.LENGTH_SHORT).show();
            return;
        }
        if (drawMoney > 150000) {
            Toast.makeText(getActivity(), "提款金额不能大于150000元", Toast.LENGTH_SHORT).show();
            return;
        }

        submit();
    }

    private void submit() {
        BindCardDetail checkCard = cardDetails.get(lastCheckIndex);
        try {
            WithdrawCommand command = new WithdrawCommand();
            command.setBankcardId(String.valueOf(checkCard.getId()));
            command.setFundPassword(fundPassword.getText().toString());//DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(fundPassword.getText().toString())))
            command.setAmount(drawMoneyEditText.getText().toString());
            String requestCommand = GsonHelper.toJson(command);
            requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
            command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=Fund&action=withdraw", "UTF-8") + userCentre.getKeyApiKey()));
            executeCommand(command, restCallback, ID_APPLY_WITHDRAW);
            dialogShow("正在申请提现...");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private RestCallback restCallback = new RestCallback() {

        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case ID_USER_INFO:
                    UserInfo userInfo = ((UserInfo) response.getData());
                    WangCaiApp.getUserCentre().setUserInfo(userInfo);
                    if (userInfo != null) {
                        money.setText(String.format("%.4f", userInfo.getWithdrawable()));
                    }
                    break;
                case ID_CARD_INFO:
                    cardDetails = (ArrayList<BindCardDetail>) response.getData();
                    refreshCardLayout();
                    break;
                case ID_APPLY_WITHDRAW:
                    showToast("申请提现成功", Toast.LENGTH_LONG);
                    resetUI();
                    loadInfo();
                    break;
                case REVISE_TRACE_ID:
                    BackToken verify = (BackToken) response.getData();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("revise", false);
                    bundle.putString("verify", verify.getCheckedToken());
                    bundle.putString("bindCard", GsonHelper.toJson(cardDetails.get(0)));
                    launchFragment(AddBankCard.class, bundle);
                    break;
                default:
                    break;
            }
            return false;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (request.getId() == ID_CARD_INFO) {
                showToast("无法加载银行卡信息", Toast.LENGTH_LONG);
                return true;
            }
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }else{
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state != RestRequest.RUNNING) {
                dialogHide();
            }
        }
    };

    private void resetUI() {
        fundPassword.setText("");
        drawMoneyEditText.setText("");
        lastCheckIndex = INVALID_INDEX;
        cardNumber.setText("");
        cardArea.setText("");
        cardBranch.setText("");
        for (int i = 0; i < cardInfo.getChildCount(); i++) {
            ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(i);
            tmp.setVisibility(View.GONE);
            CheckBox checkBox = (CheckBox) tmp.findViewById(R.id.card_item_checkbox);
            checkBox.setChecked(false);
        }
    }

    private void refreshCardLayout() {
        if (cardDetails == null || cardDetails.size() == 0) {
            cardInfoNoCard.setVisibility(View.VISIBLE);
            cardInfo.setVisibility(View.GONE);
            replaceFragment(BankCardSetting.class, null);
            return;
        } else {
            cardInfoNoCard.setVisibility(View.GONE);
            cardInfo.setVisibility(View.VISIBLE);
            removeAllMenu();
            if (cardDetails.size() < 4) {
                addMenuItemAtRight(R.drawable.plus, (v) -> {
                    addBankCard();
                });
            }
        }

        for (int i = 0; i < cardInfo.getChildCount(); i++) {
            ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(i);
            if (i >= 0 && i < cardDetails.size()) {
                tmp.setVisibility(View.VISIBLE);
                tmp.setOnClickListener(onClickCardListener);
                BindCardDetail cardDetail = cardDetails.get(i);
                if (cardDetail.isLocked()) {
                    removeAllMenu();
                }
                tmp.setTag(i);
                ((ImageView) tmp.findViewById(R.id.card_item_logo)).setImageResource(getLogo(cardDetail.getBankId()));
            } else {
                tmp.setVisibility(View.GONE);
            }
        }

        scrollView.scrollTo(0, 0);
    }

    private void addBankCard() {
        View fundsView = LayoutInflater.from(getContext()).inflate(R.layout.view_revise_pswd, null);
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setContentView(fundsView);
        builder.setTitle("密码验证");
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("确认", (dialog, which) -> {
            loadRevise(((TextView) fundsView.findViewById(R.id.funds_password)).getText().toString(), 1);
            dialog.dismiss();
        });
        builder.create().show();
    }

    private static int getLogo(int backId) {
        switch (backId) {
            case 1://工商银行
                return R.drawable.bank_logo_gs;
            case 2://建设银行
                return R.drawable.bank_logo_js;
            case 3://农业银行
                return R.drawable.bank_logo_ny;
            case 4://中国银行',
                return R.drawable.bank_logo_zg;
            case 5://招商银行
                return R.drawable.bank_logo_zs;
            case 6://交通银行
                return R.drawable.bank_logo_jt;
            case 7://民生银行
                return R.drawable.bank_logo_ms;
            case 8://中信银行
                return R.drawable.bank_logo_zx;
            case 9://上海浦东发展银行
                return R.drawable.bank_logo_pdfz;
            case 10://广州银行',
                return R.drawable.bank_logo_gf;
            case 11://平安银行
                return R.drawable.bank_logo_payh;
            case 13://兴业银行
                return R.drawable.bank_logo_xy;
            case 14://华夏银行
                return R.drawable.bank_logo_hx;
            case 15://中国光大银行
                return R.drawable.bank_logo_gd;
            case 16://邮政储汇
                return R.drawable.bank_logo_yz;
            case 17://"城市商业银行"
                return R.drawable.bank_logo_cssy;
            case 18://"农村商业银行"
                return R.drawable.bank_logo_ncsy;
            case 19://"恒丰银行"
                return R.drawable.bank_logo_hf;
            case 20://"浙商银行"
                return R.drawable.bank_logo_zsyh;
            case 21://"农村合作银行"
                return R.drawable.bank_logo_nchz;
            case 22://"渤海银行"
                return R.drawable.bank_logo_bh;
            case 23://"徽商银行股份有限公司"
                return R.drawable.bank_logo_hs;
            case 24://"村镇银行"
                return R.drawable.bank_logo_cz;
            case 25://"上海农村商业银行"
                return R.drawable.bank_logo_shncsy;
            case 26://"农村信用合作社"
                return R.drawable.bank_logo_ncxh;
            case 27://"韩国外换银行"
                return R.drawable.bank_logo_keb;
            case 28://"友利银行"
                return R.drawable.bank_logo_yl;
            case 29://"新韩银行"
                return R.drawable.bank_logo_xh;
            case 31://"韩亚银行"
                return R.drawable.bank_logo_hy;
            default:
                return R.drawable.cycleviewpager_default_pic;
        }
    }

    private View.OnClickListener onClickCardListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            if (lastCheckIndex != INVALID_INDEX) {
                ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(lastCheckIndex);
                CheckBox checkBox = (CheckBox) tmp.findViewById(R.id.card_item_checkbox);
                checkBox.setChecked(false);
                cardNumber.setText("");
                cardArea.setText("");
                cardBranch.setText("");
                submit.setVisibility(View.VISIBLE);
                withdrawalsTime.setVisibility(View.GONE);
                if (lastCheckIndex == index) {
                    lastCheckIndex = INVALID_INDEX;
                    return;
                }
            }

            lastCheckIndex = index;
            withdrawalsTime.setVisibility(View.GONE);
            ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(lastCheckIndex);
            CheckBox checkBox = (CheckBox) tmp.findViewById(R.id.card_item_checkbox);
            checkBox.setChecked(true);

            BindCardDetail checkCard = cardDetails.get(lastCheckIndex);
            if(TextUtils.isEmpty(checkCard.getModifiedAt())){
                submit.setVisibility(View.GONE);
                withdrawalsTime.setVisibility(View.VISIBLE);
            } else {
                RecordTime intendedLockAt = ConstantInformation.getLasttime(ConstantInformation.df.format(new Date()), checkCard.getIntendedWithdrawalAt());
                if (!checkCard.isLocked() && intendedLockAt.getDay() <= 0 && intendedLockAt.getHour() <= 2 && intendedLockAt.getHour() >= 0) {
                    submit.setVisibility(View.GONE);
                    withdrawalsTime.setVisibility(View.VISIBLE);
                    withdrawalsTime.start(ConstantInformation.ONE_HOUR * intendedLockAt.getHour() + ConstantInformation.ONE_MINUTE * intendedLockAt.getMinute() + 1000L * intendedLockAt.getSecond());
                    withdrawalsTime.setOnCountdownEndListener((cv) -> {
                        submit.setVisibility(View.VISIBLE);
                        withdrawalsTime.setVisibility(View.GONE);
                    });
                } else {
                    submit.setVisibility(View.VISIBLE);
                    withdrawalsTime.setVisibility(View.GONE);
                    withdrawalsTime.stop();
                }
                String numb = checkCard.getAccount().substring(checkCard.getAccount().length() - 4, checkCard.getAccount().length());
                cardNumber.setText("************ " + numb);
                cardArea.setText(checkCard.getProvince() + "-" + checkCard.getCity());
                cardBranch.setText(checkCard.getBranch());
            }
        }
    };
}
