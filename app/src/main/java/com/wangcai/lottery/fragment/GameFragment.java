package com.wangcai.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangcai.lottery.BuildConfig;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.Preferences;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.data.MethodList;
import com.wangcai.lottery.data.MethodListCommand;
import com.wangcai.lottery.data.MethodType;
import com.wangcai.lottery.game.Game;
import com.wangcai.lottery.game.GameConfig;
import com.wangcai.lottery.game.MenuController;
import com.wangcai.lottery.game.OnSelectedListener;
import com.wangcai.lottery.material.ShoppingCart;
import com.wangcai.lottery.material.Ticket;
import com.wangcai.lottery.pattern.TitleTimingView;
import com.wangcai.lottery.user.UserCentre;
import com.wangcai.lottery.view.TableMenu;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选号界面
 * Created by Alashi on 2016/2/17.
 */
public class GameFragment extends BaseFragment implements OnSelectedListener, TableMenu.OnClickMethodListener {
    private static final String TAG = GameFragment.class.getSimpleName();

    private static final int ID_METHOD_LIST = 1;

    WebView webView;
    @BindView(android.R.id.title)
    TextView titleView;
    @BindView(R.id.pick_notice)
    TextView pickNoticeView;
    @BindView(R.id.pick_game_layout)
    LinearLayout pickGameLayout;
    @BindView(R.id.choose_done_button)
    Button chooseDoneButton;
    @BindView(R.id.lottery_choose_bottom)
    RelativeLayout chooseBottomLayout;
    @BindView(R.id.pick_random)
    Button pickRandom;

    private TitleTimingView timingView;
    private Lottery lottery;
    private Game game;
    private MenuController menuController;
    private ShoppingCart shoppingCart;
    private UserCentre userCentre;
    private ArrayList<MethodList> methodListRecord;
    private MethodType methodTypeRecord;
    private Method methodRecord;

    public static void launch(BaseFragment fragment, Lottery lottery) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        FragmentLauncher.launch(fragment.getActivity(), GameFragment.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        initMenu();
        loadMethodFromXml();
        loadMenu();
    }

    private void applyArguments() {
        shoppingCart = ShoppingCart.getInstance();
        userCentre = WangCaiApp.getUserCentre();
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    private void loadWebViewIfNeed() {
        if (webView != null) {
            return;
        }
        if (chooseBottomLayout != null) {
            chooseBottomLayout.postDelayed(() -> {
                synchronized (chooseBottomLayout) {
                    if (isFinishing()) {
                        return;
                    }
                    if (webView != null) {
                        update2WebView();
                        return;
                    }
                    webView = new WebView(getActivity());
                    chooseBottomLayout.addView(webView, 1, 1);
                    initWebView();
                    webView.loadUrl("file:///android_asset/web/game.html");
                }
            }, 400);
        }
    }

    private void loadMethodFromXml() {
        String methodTypeKey = "game_config_methodtype_" + WangCaiApp.getUserCentre().getUserID() + "_" + lottery.getId();
        String methodTypeJson = Preferences.getString(getContext(), methodTypeKey, null);

        String methodKey = "game_config_method_" + WangCaiApp.getUserCentre().getUserID() + "_" + lottery.getId();
        String methodJson = Preferences.getString(getContext(), methodKey, null);

        if (!TextUtils.isEmpty(methodJson) && !TextUtils.isEmpty(methodTypeJson)) {
            methodTypeRecord = GsonHelper.fromJson(methodTypeJson, MethodType.class);
            methodRecord = GsonHelper.fromJson(methodJson, Method.class);
            if (methodTypeRecord != null && methodRecord != null) {
                changeGameMethod(methodTypeRecord, methodRecord);
            }
        }
    }

    private void saveMethod2Xml(MethodType methodType, Method method) {
        String methodTypeKey = "game_config_methodtype_" + WangCaiApp.getUserCentre().getUserID() + "_" + lottery.getId();
        Preferences.saveString(getContext(), methodTypeKey, GsonHelper.toJson(methodType));

        String methodKey = "game_config_method_" + WangCaiApp.getUserCentre().getUserID() + "_" + lottery.getId();
        Preferences.saveString(getContext(), methodKey, GsonHelper.toJson(method));
    }

    private void initMenu() {
        menuController = new MenuController(getActivity(), lottery);
        menuController.setOnClickMethodListener(this);
    }

    private void loadMenu() {
        MethodListCommand methodListCommand = new MethodListCommand();
        methodListCommand.setLotteryId(lottery.getId());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MethodList>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), methodListCommand, typeToken, restCallback, ID_METHOD_LIST, this);
        restRequest.execute();
    }

    private void updateMenu(ArrayList<MethodList> methodList) {
        menuController.setMethodList(methodList);
    }

    private void loadTimingView(int methodID) {
        timingView = new TitleTimingView(getActivity(), findViewById(R.id.pick_game_timing_view), lottery, methodID);
    }

    @Override
    public void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timingView != null) {
            timingView.stop();
        }
        if (game != null) {
            game.destroy();
        }
        if (webView != null) {
            webView.destroy();
        }
    }

    @OnClick(android.R.id.home)
    public void finishFragment() {
        getActivity().finish();
    }

    @OnClick({R.id.title_text_layout, android.R.id.title, R.id.title_image})
    public void showOrHideMenu(View v) {
        inputkey(v);
        Log.d(TAG, "showOrHideMenu: ");
        if (menuController.isShowing()) {
            menuController.hide();
        } else {
            menuController.show(titleView);
        }
    }

    @OnClick(R.id.help)
    public void showHelp() {
        //TODO:点击“帮助”按钮，显示帮助信息
    }

    @OnClick(R.id.pick_random)
    public void onRandom() {
        if (game != null) {
            game.onRandomCodes();
        }
    }

    @OnClick(R.id.pick_notice)
    public void calculate() {
        if (game == null) {
            return;
        }
        pickNoticeView.setText(String.format("选择%d注", game.getSingleNum()));
        if (game.getSingleNum() > 0) {
            chooseDoneButton.setEnabled(true);
        } else {
            chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
        }
    }

    @OnClick(R.id.choose_done_button)
    public void onChooseDone() {
        if (game == null) {
            return;
        }
        if (game.getSingleNum() > 0) {
            String codes = game.getSubmitCodes();
            Ticket ticket = new Ticket();
            ticket.setMethodType(methodTypeRecord);
            ticket.setChooseMethod(game.getMethod());
            ticket.setChooseNotes(game.getSingleNum());
            ticket.setCodes(codes);

            shoppingCart.init(lottery);
            shoppingCart.addTicket(ticket);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        ShoppingFragment.launch(GameFragment.this, lottery);
//        launchFragmentForResult(ShoppingFragment.class, bundle, 1);
    }

    private void initWebView() {
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterface(), "androidJs");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (game != null) {
            game.reset();
        }
    }

    @Override
    public void onClickMethod(Method method) {
        menuController.hide();
        MethodType methodType = selectMethodType(method);
        changeGameMethod(methodType, method);
    }

    private class JsInterface {
        @JavascriptInterface
        public String getData() {
            if (game == null) {
                return "";
            }
            return game.getWebViewCode();
        }

        @JavascriptInterface
        public String getSubmitCodes() {
            if (game == null) {
                return "";
            }
            return game.getSubmitCodes();
        }

        @JavascriptInterface
        public String getMethodName() {
            if (game == null) {
                return "";
            }
            return game.getMethod().getNameEn();
        }

        @JavascriptInterface
        public int getLotteryId() {
            if (lottery == null) {
                return -1;
            }
            return lottery.getId();
        }

        @JavascriptInterface
        public int getSeriesId() {
            if (lottery == null) {
                return -1;
            }
            return lottery.getSeriesId();
        }

        @JavascriptInterface
        public int getMethodId() {
            if (game == null) {
                return -1;
            }
            return game.getMethod().getId();
        }

        @JavascriptInterface
        public void result(int singleNum) {
            Log.d(TAG, "result() called with: " + "singleNum = [" + singleNum + "]");
            if (game == null) {
                return;
            }
            game.setSingleNum(singleNum);
            webView.post(updatePickNoticeRunnable);
        }
    }

    private void update2WebView() {
        if (webView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("calculate();", null);
        } else {
            webView.loadUrl("javascript:calculate();");
        }
    }

    private Runnable updatePickNoticeRunnable = new Runnable() {
        @Override
        public void run() {
            calculate();
        }
    };

    private void changeGameMethod(MethodType methodType, Method method) {
        if (method == null) {
            return;
        }
        if (game == null) {
            pickGameLayout.removeAllViews();
        } else {
            if (methodType.getId() == game.getMethod().getPid() && method.getId() == game.getMethod().getId()) {
                //同一个玩法，不用切换
                return;
            }
            game.destroy();
            menuController.addPreference(method);
            saveMethod2Xml(methodType, method);
            methodTypeRecord = methodType;
            methodRecord = method;
        }
        if (method.getId() == 178)
            loadTimingView(178);
        else
            loadTimingView(0);
        menuController.setCurrentMethod(method);
        titleView.setText(methodType.getNameCn() + "-" + method.getNameCn());
        pickNoticeView.setText("选择0注");
        chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
        game = GameConfig.createGame(getActivity(), method, lottery);
        game.inflate(pickGameLayout);
        game.setOnSelectedListener(this);
        if (game.isInputStatus())
            pickRandom.setVisibility(View.GONE);
        else
            pickRandom.setVisibility(View.VISIBLE);

        loadWebViewIfNeed();
    }

    private Method defaultGameMethod(ArrayList<MethodList> methodList) {
        int id = 0;
        switch (lottery.getSeriesId()) {
            case 2://11选5
                id = 112;
                break;
            case 1://重庆时时彩
                if (lottery.getId() == 10) {
                    id = 70;
                } else {
                    id = 65;
                }
                break;
            case 4://苹果快三分分彩
                id = 160;
                break;
            case 5://苹果极速PK10
                id = 175;
                break;
            case 3://苹果极速3D
                id = 136;
                break;
            case 9://快乐十分
                id = 456;
            default:
                break;
        }

        if (id == 0) {
            return methodList.get(0).getChildren().get(0).getChildren().get(0);
        }

        Method defaultMethod = null;
        for (MethodList methods : methodList) {
            for (MethodType methodGroup : methods.getChildren()) {
                for (Method method : methodGroup.getChildren()) {
                    if (id == method.getId()) {
                        defaultMethod = method;
                    }
                }
            }
        }
        return defaultMethod;
    }

    private MethodType selectMethodType(Method methodChoose) {
        MethodType methodType = null;
        if (methodChoose != null) {
            for (MethodList methods : methodListRecord) {
                for (MethodType methodGroup : methods.getChildren()) {
                    for (Method method : methodGroup.getChildren()) {
                        if (methodChoose.getId() == method.getId() && methodChoose.getPid() == methodGroup.getId()) {
                            methodType = methodGroup;
                        }
                    }
                }
            }
        } else {
            methodType = methodListRecord.get(0).getChildren().get(0);
        }
        return methodType;
    }

    //game.setOnSelectedListener(this)的回调
    @Override
    public void onChanged(Game game) {
        loadWebViewIfNeed();
        update2WebView();
    }

    private void inputkey(View v) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private RestCallback restCallback = new RestCallback<ArrayList<?>>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_METHOD_LIST) {
                methodListRecord = (ArrayList<MethodList>) response.getData();
                if (methodListRecord != null && methodListRecord.size() > 0 && game == null) {
                    Method method = defaultGameMethod(methodListRecord);
                    MethodType methodType = selectMethodType(method);
                    saveMethod2Xml(methodType, method);
                    menuController.addPreference(method);
                    changeGameMethod(methodType, method);
                }
                updateMenu(methodListRecord);
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

