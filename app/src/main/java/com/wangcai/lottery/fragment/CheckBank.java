package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.component.MyTextWatcher;
import com.wangcai.lottery.data.BackToken;
import com.wangcai.lottery.data.BindCardDetail;
import com.wangcai.lottery.data.DeleteCommand;
import com.wangcai.lottery.data.ReviseCommand;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2017/5/31.
 */

public class CheckBank extends BaseFragment {
    private static final int REVISE_TRACE_ID = 1;
    private static final int DELETE_TRACE_ID = 2;

    @BindView(R.id.pick_verify)
    TextView pickVerify;
    @BindView(R.id.real_name)
    EditText realName;
    @BindView(R.id.card_no)
    EditText cardNo;
    @BindView(R.id.funds_pswd)
    EditText fundsPswd;
    @BindView(R.id.submit_censor)
    Button submitCensor;


    private boolean under = false;
    private boolean revise = false;
    private UserCentre userCentre;
    private BindCardDetail bindCard = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "银行卡管理", R.layout.checkbank_setting,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
        under = getArguments().getBoolean("under");
        if (under) {
            //编辑 revise　true修改　false 添加
            revise = getArguments().getBoolean("revise");
            submitCensor.setText(revise ? "修改验证" : "确认验证");
            loadCheckCard();
        } else {
            //删除
            submitCensor.setText("确认删除");
            loadCheckCard();
        }

        cardNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        MyTextWatcher cardnumberWatcher = new MyTextWatcher(cardNo);
        cardnumberWatcher.setTextWatcherChanging((boolean textbool) -> {

        });
        cardNo.addTextChangedListener(cardnumberWatcher);
    }

    private void loadCheckCard() {
        bindCard = GsonHelper.fromJson(getArguments().getString("bindCard"), BindCardDetail.class);
        if (!TextUtils.isEmpty(bindCard.getBank()) || !TextUtils.isEmpty(bindCard.getAccount())) {
            String numb = bindCard.getAccount().substring(bindCard.getAccount().length() - 4, bindCard.getAccount().length());
            String bankinfo = getActivity().getResources().getString(R.string.is_bank_info);
            bankinfo = StringUtils.replaceEach(bankinfo, new String[]{"BANKNAME", "CARDNO"},
                    new String[]{bindCard.getBank(), "****************" + numb});
            pickVerify.setText(Html.fromHtml(bankinfo));
        } else {
            pickVerify.setText("无选择绑定银行卡");
        }
    }

    @OnClick(R.id.submit_censor)
    public void submitCensor() {
        if (bindCard == null) {
            showToast("请选择需要验证的银行卡信息");
            return;
        }

        if (TextUtils.isEmpty(realName.getText().toString())) {
            showToast("开户人名不能为空");
            return;
        }

        if (TextUtils.isEmpty(cardNo.getText().toString())) {
            showToast("银行卡号不能为空");
            return;
        }

        if (TextUtils.isEmpty(fundsPswd.getText().toString())) {
            showToast("资金密码不能为空");
            return;
        }
        String card = cardNo.getText().toString().replaceAll("\\s*", "");
        if(card.length() < 16 || card.length()  > 19){
            tipDialog("银行卡格式不正确");
            return ;
        }
        loadRevise(card);
    }

    /**
     * 验证银行卡信息
     */
    private void loadRevise(String card) {

        int type = under ? (revise ? 2 : 1) : 3;
        try {
            ReviseCommand command = new ReviseCommand();
            command.setCardId(bindCard.getId());
            command.setFundPwd(fundsPswd.getText().toString());
            command.setAccountName(realName.getText().toString());
            command.setAccount(card);
            command.setType(type);
            String requestCommand = GsonHelper.toJson(command);
            requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
            command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=checkBankCard", "UTF-8") + userCentre.getKeyApiKey()));
            TypeToken typeToken = new TypeToken<RestResponse<BackToken>>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, REVISE_TRACE_ID, this);
            restRequest.execute();
        } catch (UnsupportedEncodingException e) {
            MobclickAgent.reportError(getActivity(), e.getMessage());
        }
    }

    /**
     * 删除银行卡
     *
     * @param chekToken
     */
    private void deleteData(String chekToken) {
        try {
            DeleteCommand command = new DeleteCommand();
            command.setCardId(bindCard.getId());
            command.setCheckedToken(chekToken);
            String requestCommand = GsonHelper.toJson(command);
            requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
            command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=deleteBankCard", "UTF-8") + userCentre.getKeyApiKey()));
            TypeToken typeToken = new TypeToken<RestResponse>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, DELETE_TRACE_ID, this);
            restRequest.execute();
        } catch (UnsupportedEncodingException e) {
            MobclickAgent.reportError(getActivity(), e.getMessage());
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case REVISE_TRACE_ID:
                    BackToken verify = (BackToken) response.getData();
                    if (under) {
                        Bundle bundle = new Bundle();
                        if (revise) {
                            bundle.putBoolean("revise", true);
                            bundle.putString("verify", verify.getCheckedToken());
                            bundle.putString("bindCard", GsonHelper.toJson(bindCard));
                        } else {
                            bundle.putBoolean("revise", false);
                            bundle.putString("verify", verify.getCheckedToken());
                        }
                        launchFragment(AddBankCard.class, bundle);
                    } else {
                        deleteData(verify.getCheckedToken());
                    }
                    getActivity().finish();
                    break;
                case DELETE_TRACE_ID:
                    getActivity().finish();
                    break;
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
        }
    };
}
