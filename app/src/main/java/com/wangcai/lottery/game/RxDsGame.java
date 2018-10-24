package com.wangcai.lottery.game;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.material.Calculation;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.util.NumbericUtils;
import com.wangcai.lottery.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.wangcai.lottery.game.GameConfig.DS_TYPE_KL10;
import static com.wangcai.lottery.game.GameConfig.DS_TYPE_KL12;
import static com.wangcai.lottery.game.GameConfig.DS_TYPE_PK10;
import static com.wangcai.lottery.game.GameConfig.DS_TYPE_SSC;
import static com.wangcai.lottery.game.GameConfig.DS_TYPE_SYXW;

/**
 * Created by Ace on 2018/7/03.
 * 任选 单式玩法
 */
public class RxDsGame extends Game {
    private static final String TAG = RxDsGame.class.getSimpleName();
    private int basic, digit = 0;
    private boolean hasIllegal;
    private boolean showOrHide = false;
    private ArrayList<String[]> codeList;

    private Button clear;
    private ImageButton submit;
    private EditText codesInput;
    private LinearLayout mainLayout;
    private LinearLayout loadingLayout;
    private LinearLayout digitMode;
    private TextView pickNoticeView;
    private ViewGroup parentLayout;
    private AppCompatCheckBox wanCheck, qianCheck, baiCheck, shiCheck, geCheck;
    private Map<Integer, Boolean> checkArray;
    private Map<Integer, Boolean> checkBoxArray;
    private Game game;
    private ExecutorService executorService;
    private Runnable delayRun;
    private Handler handler;

    public RxDsGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
        checkArray = new HashMap<>();
        checkArray.put(0, false);
        checkArray.put(1, false);
        checkArray.put(2, false);
        checkArray.put(3, false);
        checkArray.put(4, false);
        switch (method.getId()) {
            case 200:   //时时彩 任二直选单式 By Ace
            case 201:   //时时彩 任二组选单式 By Ace
                checkArray.put(3, true);
                checkArray.put(4, true);
                showOrHide = true;
                basic = 2;
                break;
            case 186:   //任选三直选单式 By Ace
            case 189:   //任选三组六单式 By Ace
            case 188:   //任选三组三单式 By Ace
            case 190:   //任选混合组选
                checkArray.put(2, true);
                checkArray.put(3, true);
                checkArray.put(4, true);
                showOrHide = true;
                basic = 3;
                break;
            case 187: //任选四直选单式
                checkArray.put(1, true);
                checkArray.put(2, true);
                checkArray.put(3, true);
                checkArray.put(4, true);
                showOrHide = true;
                basic = 4;
                break;
            default:
                Log.w(TAG, "DsGame: 不支持的method类型：" + method.getId());
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
        }
        checkBoxArray = checkArray;
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
            case DS_TYPE_SYXW:
            case DS_TYPE_PK10:
            case DS_TYPE_KL10:
            case DS_TYPE_KL12:
                break;
            default:
                Log.w(TAG, "DsGame: 不支持的类型：method:" + method.getId() + ", lottery:" + lottery.getId());
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
                break;
        }
        codeList = new ArrayList<>();
        setSingleNum(0);
        initThread();
    }

    @Override
    public void onInflate() {
        try {
            createPicklayout(this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    private void initThread() {
        executorService = Executors.newSingleThreadExecutor();
        delayRun = new Runnable() {
            @Override
            public void run() {
                //在这里调用服务器的接口，获取数据
                doCalculation();
            }
        };
        handler = new Handler();
    }

    //计算
    private void noteCount() {
        codesInput.setText(getSubmitCodes());
        digit = 0;
        List<String> array = new ArrayList<>();
        if (codeList.size() > 0) {
            for (int i = 0, size = checkArray.size(); i < size; i++) {
                Boolean check = checkArray.get(i);
                if (check) {
                    array.add("" + i);
                    digit += 1;
                }
            }
        }
        int chance = 0;
        switch (method.getId()) {
            case 200:   //时时彩 任二直选单式 By Ace
            case 186:   //时时彩 任选三直选单式 By Ace
            case 187:   //时时彩 任选四直选单式 By Ace
            case 201:   //时时彩 任二组选单式 By Ace
            case 189:   //任选三组六单式 By Ace
            case 188:   //任选三组三单式 By Ace
            case 190:   //任选三混合组选 By Ace
                String[] combineData = array.toArray(new String[array.size()]);
                chance = Calculation.getInstance().C(combineData.length, basic);
                break;
            default:
                Log.w(TAG, "DsRxGame: 不支持的method类型：" + method.getId());
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
        }
        setSingleNum(codeList.size() > 0 ? codeList.size() * chance : 0);
    }

    public void calculate() {
        if (!isCalculating()) {
            loadingLayout.setVisibility(View.VISIBLE);
            submit.setEnabled(false);
            executorService.execute(delayRun);
        }
    }

    private void doCalculation() {
        setCalculating(true);
        verify();
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
                codeList = NumbericUtils.delDupWithOrder(codeList);
                break;
            case DS_TYPE_SYXW:
            case DS_TYPE_PK10:
            case DS_TYPE_KL12:
            case DS_TYPE_KL10:
                codeList = NumbericUtils.delDup(codeList);
            default:
                break;
        }
        if (!activity.isFinishing()) {
            setCalculating(false);
            loadingLayout.setVisibility(View.GONE);
            submit.setEnabled(true);
            noteCount();
            pickNoticeView.callOnClick();
            if (hasIllegal && !executorService.isShutdown())
                ToastUtils.showShortToastLocation(activity, "您的注单存在错误/重复项，已为您优化注单。", Gravity.CENTER, 0, -300);
        }
    }

    private void verify() {
        if (codesInput == null) {
            return;
        }
        hasIllegal = false;
        codeList.clear();
        if (TextUtils.isEmpty(codesInput.getText()))
            return;
        String[] codes;
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
                codes = codesInput.getText().toString().split("[,，;；：:|｜.\n ]");
                break;
            default:
                codes = codesInput.getText().toString().split("[,，;；：:|｜.\n]");
                break;
        }

        for (String code : codes) {
            String[] strs;
            switch (type) {
                case DS_TYPE_SSC:
                    int length = code.length();
                    strs = new String[length];
                    for (int i = 0; i < length; i++)
                        strs[i] = String.valueOf(code.charAt(i));
                    break;
                default:
                    strs = code.split(" ");
                    if (NumbericUtils.hasDupString(strs)) {
                        hasIllegal = true;
                        continue;
                    }
                    break;
            }
            if (strs.length != basic) {
                hasIllegal = true;
                continue;
            }

            switch (type) {
                case DS_TYPE_SSC:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SSC);
                    break;
                case DS_TYPE_SYXW:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SYXW);
                    break;
                case DS_TYPE_PK10:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_PK10);
                    break;
                case DS_TYPE_KL12:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_KL12);
                    break;
                case DS_TYPE_KL10:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_KL10);
                    break;
                default:
                    Log.w(TAG, "verify: 不支持的类型：" + type);
                    ToastUtils.showShortToast(getActivity(), "不支持的类型");
                    break;
            }
        }
        if (NumbericUtils.hasDupArray(codeList))
            hasIllegal = true;
    }

    private void verifyNumber(String[] strs, ArrayList<String> legals) {
        for (String str : strs) {
            if (!legals.contains(str)) {
                hasIllegal = true;
                return;
            }
        }
        switch (method.getId()) {
            //组三
            case 188: //任选三组三单式
                if (!NumbericUtils.isDupStrCountUnique(strs, 2)) {
                    hasIllegal = true;
                    return;
                }
                break;
            //组六
            case 189:
                if (NumbericUtils.hasDupString(strs)) {
                    hasIllegal = true;
                    return;
                }
                break;
            case 190:
                if (!NumbericUtils.isDupStrMaxCount(strs, 2)) {
                    hasIllegal = true;
                    return;
                }
                break;
            case 201: //任二 组选
                if (!NumbericUtils.isDupStrCountRepeat(strs)) {
                    hasIllegal = true;
                    return;
                }
                break;
        }
        codeList.add(strs);
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
                for (int i = 0, size = codeList.size(); i < size; i++) {
                    for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                        builder.append(codeList.get(i)[j]);
                    }
                    if (i < size - 1)
                        builder.append("|");
                }
                break;
            case DS_TYPE_SYXW:
            case DS_TYPE_PK10:
            case DS_TYPE_KL10:
            case DS_TYPE_KL12:
                for (int i = 0, size = codeList.size(); i < size; i++) {
                    for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                        builder.append(codeList.get(i)[j]);
                        if (j < length - 1)
                            builder.append(" ");
                    }
                    if (i < size - 1)
                        builder.append("|");
                }
                break;
            default:
                Log.w(TAG, "getSubmitCodes: 不支持的类型：" + type);
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
                break;
        }

        return builder.toString();
    }

    private View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.single_rx_layout, null, false);
    }

    private void createPicklayout(Game game) {
        this.game = game;
        /*对GameFragment的View进行操作*/
        parentLayout = activity.getWindow().getDecorView().findViewById(R.id.parent_layout);
        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codesInput.clearFocus();
            }
        });
        pickNoticeView = activity.getWindow().getDecorView().findViewById(R.id.pick_notice);
        submit = activity.getWindow().getDecorView().findViewById(R.id.choose_done_button);
        View view = createDefaultPickLayout(game.getTopLayout());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
        game.setInputStatus(true);
        game.setSeat(String.valueOf(basic));
        game.setPostsiton(toDigitsString(checkArray));
        loadingLayout = view.findViewById(R.id.loading_layout);
        loadingLayout.setVisibility(View.GONE);
        mainLayout = view.findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codesInput != null) {
                    codesInput.clearFocus();
                }
            }
        });

        digitMode = view.findViewById(R.id.digit_mode);
        digitMode.setVisibility(showOrHide ? View.VISIBLE : View.GONE);

        wanCheck = view.findViewById(R.id.wan_check);
        wanCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectionSet(buttonView, isChecked);
            }
        });
        qianCheck = view.findViewById(R.id.qian_check);
        qianCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectionSet(buttonView, isChecked);
            }
        });
        baiCheck = view.findViewById(R.id.bai_check);
        baiCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectionSet(buttonView, isChecked);
            }
        });
        shiCheck = view.findViewById(R.id.shi_check);
        shiCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectionSet(buttonView, isChecked);
            }
        });
        geCheck = view.findViewById(R.id.ge_check);
        geCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectionSet(buttonView, isChecked);
            }
        });

        for (int i = 0; i < digitMode.getChildCount(); i++) {
            CheckBox digitCheckBox = (CheckBox) digitMode.getChildAt(i);
            digitCheckBox.setChecked(checkArray.get(i));
        }

        codesInput = view.findViewById(R.id.input_multiline_text);
        codesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                runThread();
                codesInput.setSelection(s.length());
            }
        });
        codesInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(codesInput.getWindowToken(), 0);
                    if (!isCalculating()) {
                        runThread();
                    }
                }
            }
        });
        codesInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codesInput.setFocusable(true);
            }
        });

        clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codesInput.setText("");
                if (!isCalculating()) {
                    runThread();
                }
            }
        });
    }

    private void runThread() {
        if (delayRun != null) {
            handler.removeCallbacks(delayRun);
        }
        //延迟800ms，如果不再输入字符，则执行该线程的run方法
        if (codesInput != null) {
            if (!TextUtils.isEmpty(codesInput.getText())) {
                handler.postDelayed(delayRun, 800);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static String toDigitsString(Map<Integer, Boolean> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Map<Integer, Boolean> resultMap = sortMapByKey(map);
        for (Iterator iterator = resultMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Integer, Boolean> entry = (java.util.Map.Entry) iterator.next();
            if(entry.getValue()) {
                sb.append(entry.getKey().toString());
            }
        }
        return sb.toString();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown())
            executorService.shutdownNow();
    }

    private void selectionSet(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.wan_check:
                checkBoxArray.put(0, isChecked);
                break;
            case R.id.qian_check:
                checkBoxArray.put(1, isChecked);
                break;
            case R.id.bai_check:
                checkBoxArray.put(2, isChecked);
                break;
            case R.id.shi_check:
                checkBoxArray.put(3, isChecked);
                break;
            case R.id.ge_check:
                checkBoxArray.put(4, isChecked);
                break;
        }

        int ck = 0;
        for (int i = 0; i < checkBoxArray.size(); i++) {
            boolean checkBool = checkBoxArray.get(i);
            if (checkBool) {
                ck += 1;
            }
        }
        if (ck >= basic) {
            checkArray = checkBoxArray;
            game.setPostsiton(toDigitsString(checkArray));
        } else {
            view.setChecked(true);
        }
        runThread();
    }

    /**
     * 使用 Map按value进行排序
     * @param oriMap
     * @return
     */
    private static Map<Integer, Boolean> sortMapByKey(Map<Integer, Boolean> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<Integer, Boolean> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<Integer, Boolean>> entryList = new ArrayList<>(oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<Integer, Boolean>> iter = entryList.iterator();
        Map.Entry<Integer, Boolean> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    static class MapValueComparator implements Comparator<Map.Entry<Integer, Boolean>> {
        @Override
        public int compare(Map.Entry<Integer, Boolean> me1, Map.Entry<Integer, Boolean> me2) {
            return me1.getKey().compareTo(me2.getKey());
        }
    }

}
