package com.wangcai.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.BuildConfig;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.ContainerActivity;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.LoginCommand;
import com.wangcai.lottery.data.UserInfo;
import com.wangcai.lottery.pattern.VersionChecker;
import com.gyf.barlibrary.ImmersionBar;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页面
 * Created on 2015/12/31.
 *
 * @author ACE
 */

public class GoldenLogin extends BaseFragment {
    private static final String TAG = GoldenLogin.class.getSimpleName();

    @BindView(R.id.login_edit_account)
    EditText userName;
    @BindView(R.id.login_edit_password)
    EditText password;
    @BindView(R.id.login_account_edit_clear)
    View userNameClear;
    @BindView(R.id.login_password_edit_clear)
    View passwordClear;
    @BindView(R.id.version)
    TextView version;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "登录", R.layout.golden_login, true, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(getActivity()).keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING).init();  //单独指定软键盘模式
        new VersionChecker(this).startCheck();
        userNameClear.setVisibility(View.INVISIBLE);
        passwordClear.setVisibility(View.INVISIBLE);
        String ver = getActivity().getResources().getString(R.string.is_version_no);
        ver = StringUtils.replaceEach(ver, new String[]{"VERSION"}, new String[]{"1." + getAppVersionName(getContext())});
        version.setText(ver);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userName.getText().toString().length() > 0) {
                    userNameClear.setVisibility(View.VISIBLE);
                } else {
                    userNameClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        userName.setOnEditorActionListener((v, actionId, event) -> false);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getText().toString().length() > 0) {
                    passwordClear.setVisibility(View.VISIBLE);
                } else {
                    passwordClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                        //发送请求
                        String keyWord = password.getText().toString().trim();
                        if (null == keyWord)
                            keyWord = "";
                        if (checkUserInfo()) {
                            login();
                        }
                        hideSoftKeyBoard();
                        return true;
                    default:
                        return true;
                }
            }
            return false;
        });

        String name = WangCaiApp.getUserCentre().getUserName();
        if (!TextUtils.isEmpty(name)) {
            userName.setText(name);
            password.requestFocus();
        }
    }

    @OnClick({R.id.login_login_btn, R.id.login_account_edit_clear, R.id.login_password_edit_clear})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_account_edit_clear: //帐号清空 点帐号清空时默认清空密码
                userName.setText(null);
                password.setText(null);
                userName.requestFocus();
                break;
            case R.id.login_password_edit_clear://密码清空
                password.setText(null);
                password.requestFocus();
                break;
            case R.id.login_login_btn: //帐号登录BUT
                if (checkUserInfo()) {
                    login();
                }
                break;
        }
    }

    private void login() {
        LoginCommand command = new LoginCommand();
        command.setUsername(userName.getText().toString());
        command.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(userName.getText().toString().toLowerCase() + password.getText().toString()))));
        executeCommand(command, restCallback);
    }

    private RestCallback restCallback = new RestCallback<UserInfo>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<UserInfo> response) {
            startActivity(new Intent(getActivity(), ContainerActivity.class));
            getActivity().finish();
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            showToast(errDesc);
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                dialogShow("正在登录...");
            } else{
                dialogHide();
            }
        }
    };

    /**
     * 用户信息验证
     */
    private boolean checkUserInfo() {
        if (TextUtils.isEmpty(userName.getText().toString())) {
            Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            if (BuildConfig.DEBUG) {
                //测试版本时，自动填写密码为"a123456"
                password.setText("a123456");
                return true;
            }
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
