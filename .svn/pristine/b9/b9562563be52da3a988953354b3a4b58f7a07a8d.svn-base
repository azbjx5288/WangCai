package com.wangcai.lottery.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.base.net.NetStateHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.fragment.GoldenLogin;
import com.wangcai.lottery.fragment.LoadingDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Alashi on 2015/12/18.
 */
public class BaseFragment extends Fragment implements NetStateHelper.NetStateListener {
    private static final String TAG = "BaseFragment";

    private TextView titleBarTitle;
    private TextView globalNetState;
    private ImageButton homeBtn;
    protected LinearLayout actionBarMenuLayout;
    private boolean fitSystem = true;
    protected Unbinder unbinder;
    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;

    protected Activity mActivity;
    /**
     * 登录退出提示弹窗
     **/
    private AlertDialog alert = null;
    /**
     * 加载loding
     **/
    private LoadingDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //即使没有菜单，也要调用setHasOptionsMenu(true)，否则会触发actionbar的bug：
        //ActionBar.NAVIGATION_MODE_LIST时，即使跑了onCreateOptionsMenu，列表和菜单可能没法显示
        setHasOptionsMenu(true);

        /*//将5.0系统以上的状态栏设置为沉浸式
        if (fitSystem)
            WindowUtils.makeWindowTranslucent(getActivity());*/
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, String title, @LayoutRes int resource, boolean windowTranslucentStatus, boolean titleStatus) {
        return inflateView(inflater, container, true, title, resource, windowTranslucentStatus, titleStatus);
    }

    protected View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, boolean homeButton, String title, @LayoutRes int resource, boolean windowTranslucentStatus, boolean titleStatus) {
        isBarBarEnabled = windowTranslucentStatus;
        View top = inflater.inflate(R.layout.title_bar_fragment, container, false);

        homeBtn = top.findViewById(android.R.id.home);
        globalNetState = top.findViewById(R.id.global_net_state);
        titleBarTitle = top.findViewById(android.R.id.title);
        actionBarMenuLayout = top.findViewById(R.id.action_bar_menu_layout);
        titleBarTitle.setText(title);

        NetStateHelper netStateHelper = WangCaiApp.getNetStateHelper();
        globalNetState.setVisibility(netStateHelper.isConnected() ? View.GONE : View.VISIBLE);
        netStateHelper.addListener(this);

        inflater.inflate(resource, top.findViewById(R.id.title_bar_fragment_content), true);

        if (titleStatus) {
            (top.findViewById(R.id.customize_title)).setVisibility(View.VISIBLE);
            (top.findViewById(R.id.dividers)).setVisibility(View.VISIBLE);
        } else {
            (top.findViewById(R.id.customize_title)).setVisibility(View.GONE);
            (top.findViewById(R.id.dividers)).setVisibility(View.GONE);
        }

        if (homeButton) {
            homeBtn.setOnClickListener(v -> {
                hideSoftKeyBoard();
                getActivity().finish();
            });
        } else {
            homeBtn.setVisibility(View.GONE);
        }
        return top;
    }

    protected void enableHomeButton(boolean homeButton) {
        if (homeButton) {
            homeBtn.setVisibility(View.VISIBLE);
        } else {
            homeBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 在ActionBar添加图标菜单
     */
    protected void addMenuItemAtRight(@DrawableRes int resID, View.OnClickListener listener) {
        if (actionBarMenuLayout == null) {
            actionBarMenuLayout = (LinearLayout) getActivity().findViewById(R.id.action_bar_menu_layout);
            if (actionBarMenuLayout == null) {
                Log.e(TAG, "addMenuItem: can not add menu, actionBarMenuLayout is null", new Throwable());
                //return null;
            }
        }

        ImageView view = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.actionbar_menu_image, actionBarMenuLayout, false);
        view.setOnClickListener(listener);
        view.setImageResource(resID);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        view.setPadding(0, 0, 40, 0);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        actionBarMenuLayout.addView(view, lp);
        //return view;
    }

    protected void addMenuItemAtLeft(@DrawableRes int resID, View.OnClickListener listener) {
        homeBtn.setVisibility(View.VISIBLE);
        homeBtn.setImageResource(resID);
        homeBtn.setOnClickListener(listener);
    }

    /**
     * 在ActionBar添加文字菜单
     */
    protected View addMenuItem(String text, View.OnClickListener listener) {
        if (actionBarMenuLayout == null) {
            actionBarMenuLayout = (LinearLayout) getActivity().findViewById(R.id.action_bar_menu_layout);
            if (actionBarMenuLayout == null) {
                Log.e(TAG, "addMenuItem: can not add menu item, actionBarMenuLayout is null", new Throwable());
                return null;
            }
        }

        TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.actionbar_menu_text, actionBarMenuLayout, false);
        view.setText(text);
        view.setOnClickListener(listener);
        actionBarMenuLayout.addView(view);
        return view;
    }

    protected void removeAllMenu() {
        if (actionBarMenuLayout != null) {
            actionBarMenuLayout.removeAllViews();
        }
    }

    protected void replaceFragment(Class<? extends Fragment> fClass, Bundle bundle) {
        Fragment fragment = Fragment.instantiate(getContext(), fClass.getName());
        fragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideSoftKeyBoard() {
        if (imm == null) {
            imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void lodingImmersionBar() {
        mImmersionBar = ImmersionBar.with(mActivity);
        if (isImmersionBarEnabled()) {
            initImmersionBarEnabled();
        } else {
            initImmersionBarDisabled();
        }
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBarEnabled() {
        mImmersionBar.statusBarColor(R.color.app_main)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .navigationBarEnable(true)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    protected void initImmersionBarDisabled() {
        mImmersionBar.keyboardEnable(true)
                .navigationBarEnable(true)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    private boolean isBarBarEnabled = true;

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return isBarBarEnabled;
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WangCaiApp.getNetStateHelper().removeListener(this);
        RestRequestManager.cancelAll(this);
        hideSoftKeyBoard();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        this.imm = null;
        if (alert != null) {
            alert.dismiss();
            alert = null;
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    public void setSupportBackButton(boolean showHomeAsUp) {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        lodingImmersionBar();
        MobclickAgent.onPageStart(this.getClass().getName());
    }

    public Toolbar getToolbar() {
        return (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public final void setTitle(int resId) {
        if (titleBarTitle != null) {
            titleBarTitle.setText(resId);
        }
        getActivity().setTitle(resId);
    }

    public final void setTitle(String string) {
        if (titleBarTitle != null) {
            titleBarTitle.setText(string);
        }
        getActivity().setTitle(string);
    }

    public boolean isFinishing() {
        return getActivity() == null || getActivity().isFinishing();
    }

    protected SharedPreferences getSharedPreferences(String name, int mode) {
        if (getActivity() != null) {
            return getActivity().getSharedPreferences(name, mode);
        }
        return null;
    }

    protected final View findViewById(int id) {
        if (id < 0 || null == getView()) {
            return null;
        }

        return getView().findViewById(id);
    }

    private void spandTimeMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void dialogShow(String msg) {
        if (null == msg || msg.trim().length() == 0) {
            msg = "加载中...";
        }
        dialog = LoadingDialog.show(getActivity(), msg);
        dialog.show();
    }

    public void dialogHide() {
        if (dialog != null)
            dialog.dismiss();
    }

    public void hideWaitProgress() {
        new Thread(() -> {
            spandTimeMethod();
            dialogHide();
        }).start();
    }

    protected RestRequest executeCommand(Object command, RestCallback callback, int id) {
        return RestRequestManager.executeCommand(getActivity(), command, callback, id, this);
    }

    protected RestRequest executeCommand(Object command, RestCallback callback) {
        return RestRequestManager.executeCommand(getActivity(), command, callback, 0, this);
    }

    protected void launchFragment(Class<? extends Fragment> fragment) {
        FragmentLauncher.launch(getActivity(), fragment, null);
    }

    protected void launchFragment(Class<? extends Fragment> fragment, Bundle bundle) {
        FragmentLauncher.launch(getActivity(), fragment, bundle);
    }

    protected void launchFragmentForResult(Class<? extends Fragment> fragment, Bundle bundle, int requestCode) {
        FragmentLauncher.launchForResult(this, fragment, bundle, requestCode);
    }

    public void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(CharSequence text, int duration) {
        if (isAdded()) {
            Toast.makeText(getActivity(), text, duration).show();
        }
    }

    public void showToast(@StringRes int resId, int duration) {
        if (isAdded()) {
            Toast.makeText(getActivity(), resId, duration).show();
        }
    }

    /**
     * 错误参数与成功参数提示窗
     *
     * @param msg
     */
    public void tipDialog(String msg) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle("温馨提示");
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("知道了", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    public void signOutDialog(Activity activity, int errCode) {
        if (alert == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(errCode == 2016 ? "登录超时，请重新登录..." : "您的账号已在其他设备上登录，将在此设备上自动注销！");
            builder.setTitle("提示");
            builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    WangCaiApp.getUserCentre().logout();
                    RestRequestManager.cancelAll();
                    activity.finish();
                    FragmentLauncher.launch(activity, GoldenLogin.class);

                }
            });
            alert = builder.create();
        }
        alert.show();
    }

    @Override
    public void onStateChange(boolean isConnected) {
        if (globalNetState != null) {
            globalNetState.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        }
    }

    public boolean isFitSystem() {
        return fitSystem;
    }

    public void setFitSystem(boolean fitSystem) {
        this.fitSystem = fitSystem;
    }

}
