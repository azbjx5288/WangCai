package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.ChangePasswordCommand;
import com.wangcai.lottery.user.UserCentre;
import com.umeng.analytics.MobclickAgent;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录密码设置
 * Created by Alashi on 2016/5/2.
 */
public class LoginPasswordSetting extends BaseFragment {

    @BindView(R.id.now_password) EditText nowPassword;
    @BindView(R.id.new_password) EditText newPassword;
    @BindView(R.id.new_password_verify) EditText newPasswordVerify;

    private UserCentre userCentre;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        if (!(check())) {
            return;
        }
        try {
            ChangePasswordCommand command = new ChangePasswordCommand();
            command.setCurrentPassword(nowPassword.getText().toString());
            command.setNewPassword(newPassword.getText().toString());
            String requestCommand= GsonHelper.toJson(command);
            requestCommand=requestCommand.replace(":","=").replace(",","&").replace("\"","");
            Log.i("密码",requestCommand.substring(1, requestCommand.length() - 1) + "&packet=User&action=changeLoginPwd");
            command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1,requestCommand.length()-1)+"&packet=User&action=changeLoginPwd","UTF-8")+userCentre.getKeyApiKey()));
            executeCommand(command, callback);
        } catch (UnsupportedEncodingException e) {
            MobclickAgent.reportError(getActivity(), e.getMessage());
        }

    }

    private RestCallback callback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            showToast("登录密码修改成功", Toast.LENGTH_SHORT);
            nowPassword.setText("");
            newPassword.setText("");
            newPasswordVerify.setText("");
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
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
            if (state == RestRequest.RUNNING) {
                dialogShow("正在修改登录密码...");
            } else {
                dialogHide();
            }
        }
    };

    private boolean check() {
        String now = nowPassword.getText().toString();
        String newP = newPassword.getText().toString();
        String newPv = newPasswordVerify.getText().toString();

        if (now.isEmpty()) {
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
        if (newPv.length() > 16 || newP.length() > 16) {
            showToast("新密码太长，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        if (!newP.equals(newPv)) {
            showToast("输入的新密码不一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (now.equals(newP)) {
            showToast("当前密码和新密码一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (!newP.matches(regex)) {
            showToast("新密码必须包含数字和字母，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
