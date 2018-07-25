package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.ChangeFundCommand;
import com.wangcai.lottery.data.SetFundPwdCommand;
import com.wangcai.lottery.data.UserInfo;
import com.wangcai.lottery.data.UserInfoCommand;
import com.wangcai.lottery.user.UserCentre;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 资金密码设置
 * Created by Alashi on 2016/5/2.
 */
public class CashPasswordSetting extends BaseFragment {

    private static final int ID_USER_INFO = 1;
    private static final int ID_USER_MODIFY = 2;
    private static final int ID_USER_SET = 3;

    @BindView(R.id.now_layout)
    LinearLayout nowLayout;
    @BindView(R.id.now_password)
    EditText nowPassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.new_password_verify)
    EditText newPasswordVerify;

    private UserCentre userCentre;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cash_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
        if (userCentre.getUserInfo().isFundPwdSeted()) {
            nowLayout.setVisibility(View.VISIBLE);
        } else {
            nowLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        if (!(check())) {
            return;
        }
        if (userCentre.getUserInfo().isFundPwdSeted()) {
            try {
                ChangeFundCommand command = new ChangeFundCommand();
                command.setCurrentPassword(nowPassword.getText().toString());
                command.setNewPassword(newPassword.getText().toString());
                String requestCommand = GsonHelper.toJson(command);
                requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
                command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=changeFundPwd", "UTF-8") + userCentre.getKeyApiKey()));
                executeCommand(command, callback, ID_USER_MODIFY);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                SetFundPwdCommand command = new SetFundPwdCommand();
                command.setFundPwd(newPassword.getText().toString());
                command.setConfirmFundPwd(newPasswordVerify.getText().toString());
                String requestCommand = GsonHelper.toJson(command);
                requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
                command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=setFundPwd", "UTF-8") + userCentre.getKeyApiKey()));
                executeCommand(command, callback, ID_USER_SET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private RestCallback callback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case ID_USER_INFO:
                    showToast("资金密码修改成功", Toast.LENGTH_SHORT);
                    WangCaiApp.getUserCentre().setUserInfo((UserInfo) response.getData());
                    break;
                case ID_USER_SET:
                case ID_USER_MODIFY:
                    nowPassword.setText("");
                    newPassword.setText("");
                    newPasswordVerify.setText("");
                    executeCommand(new UserInfoCommand(), callback, ID_USER_INFO);
                    break;
            }

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            switch (request.getId()) {
                case ID_USER_SET:
                case ID_USER_MODIFY:
                    showToast("资金密码修改失败：" + errDesc, Toast.LENGTH_SHORT);
                    break;
            }
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }else {
                showToast(errDesc);
            }
            return true;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            switch (request.getId()) {
                case ID_USER_SET:
                case ID_USER_MODIFY:
                    if (state == RestRequest.RUNNING) {
                        dialogShow("正在修改资金密码...");
                    } else {
                        dialogHide();
                    }
                    break;
            }
        }
    };

    private boolean check() {
        String now = nowPassword.getText().toString();
        String newP = newPassword.getText().toString();
        String newPv = newPasswordVerify.getText().toString();

        if (now.isEmpty() && userCentre.getUserInfo().isFundPwdSeted()) {
            showToast("请输入当前密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (newP.isEmpty()) {
            showToast("请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (newPv.isEmpty()) {
            showToast("请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (newPv.length() < 6 || newP.length() < 6) {
            showToast("新密码太短，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (newPv.length() > 20 || newP.length() > 20) {
            showToast("新密码太长，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        if (!newP.equals(newPv)) {
            showToast("输入的新密码不一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (now.equals(newP) && userCentre.getUserInfo().isFundPwdSeted()) {
            showToast("当前密码和新密码一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (!newP.matches(regex)) {
            showToast("新密码必须包含数字和字母，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
