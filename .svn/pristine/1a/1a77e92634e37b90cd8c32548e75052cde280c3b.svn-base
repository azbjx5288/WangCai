package com.wangcai.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.pattern.PickNumber;
import com.wangcai.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 某一种彩种下的一种玩法：提供选号界面中间的选号区布局配置，计算注数，格式化输出选号Code的String
 * Created by Alashi on 2016/2/16.
 */
public abstract class Game implements NumberGroupView.OnChooseItemClickListener {
    private static final String TAG = "Game";

    protected ViewGroup topLayout;
    protected OnSelectedListener onSelectedListener;
    protected Activity activity;
    protected Method method;
    protected Lottery lottery;
    protected ArrayList<PickNumber> pickNumbers = new ArrayList<>();
    private long singleNum;
    private boolean inputStatus = false;
    private boolean isCalculating = false;

    public Game(Activity activity, Method method, Lottery lottery) {
        this.activity = activity;
        this.method = method;
        this.lottery = lottery;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public final void inflate(ViewGroup container) {
        topLayout = container;
        onInflate();
    }

    public final void setOnSelectedListener(OnSelectedListener listener) {
        this.onSelectedListener = listener;
    }

    protected final void notifyListener() {
        if (onSelectedListener != null) {
            onSelectedListener.onChanged(this);
        }
    }

    public final void destroy() {
        if (topLayout != null)
            topLayout.removeAllViews();
        onSelectedListener = null;
        onDestroy();
    }

    public void onDestroy() {
        pickNumbers.clear();
    }

    public final void addPickNumber(PickNumber pickNumber) {
        pickNumbers.add(pickNumber);
        pickNumber.setChooseItemClickListener(this);
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), true));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, false));
            /*if (i != size - 1)
            {
                builder.append(",");
            }*/
        }
        return builder.toString();
    }

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            Log.e(TAG, "onRandomCodes: error in call " + method.getNameEn() + method.getId() + "Random", e);
        }
    }

    public void calculate(){

    }

    public ViewGroup getTopLayout() {
        return topLayout;
    }

    public boolean isInputStatus() {
        return inputStatus;
    }

    public void setInputStatus(boolean inputStatus) {
        this.inputStatus = inputStatus;
    }

    public boolean isCalculating() {
        return isCalculating;
    }

    public void setCalculating(boolean calculating) {
        isCalculating = calculating;
    }

    public long getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(long singleNum) {
        this.singleNum = singleNum;
    }

    public abstract void onInflate();

    /**
     * 提示调用
     */
    public void onCustomDialog(String msg) {
        PromptManager.showCustomDialog(topLayout.getContext(), msg);
    }

    @Override
    public void onChooseItemClick(int position) {
        notifyListener();
    }

    /**
     * 　将Int的list转换成字符串，如list[06, 07] 转成string[06_07]
     *
     * @param list  　选择个数
     * @param scale 号球个数
     * @return [-1,-1,-1,-1,-1, 1, 1, 1,-1,-1]
     */

    protected static JsonArray transform(ArrayList<Integer> list, int scale, boolean emptyStyle) {
        JsonArray jsonArray = new JsonArray();
        ArrayList<Integer> array = new ArrayList<>();
        if (list.size() > 0) {
            for (int j = 0, size = list.size(); j < size; j++) {
                for (int i = 0; i < scale; i++) {
                    if (array.size() >= scale) {
                        if (i == (emptyStyle ? list.get(j) : list.get(j) - 1)) {
                            array.set(i, 1);
                        }
                    } else {
                        array.add(i == (emptyStyle ? list.get(j) : list.get(j) - 1) ? 1 : -1);
                    }
                }
            }
        } else {
            for (int j = 0; j < scale; j++) {
                array.add(-1);
            }
        }
        for (Integer a : array) {
            jsonArray.add(a);
        }
        return jsonArray;
    }

    /**
     * 　将Int的list转换成字符串，如list[06, 07] 转成string[06_07]
     *
     * @param list  　选择个数
     * @param scale 号球个数
     * @return [-1,-1,-1,-1,-1, 1, 1, 1,-1,-1]
     */

    protected static JsonArray transformOffset(ArrayList<Integer> list, int scale, boolean emptyStyle, int offset) {
        JsonArray jsonArray = new JsonArray();
        ArrayList<Integer> array = new ArrayList<>();
        if (list.size() > 0) {
            for (int j = 0, size = list.size(); j < size; j++) {
                for (int i = 0; i < scale; i++) {
                    if (array.size() >= scale) {
                        if (i == (emptyStyle ? list.get(j) : list.get(j) + offset)) {
                            array.set(i, 1);
                        }
                    } else {
                        array.add(i == (emptyStyle ? list.get(j) : list.get(j) + offset) ? 1 : -1);
                    }
                }
            }
        } else {
            for (int j = 0; j < scale; j++) {
                array.add(-1);
            }
        }
        for (Integer a : array) {
            jsonArray.add(a);
        }
        return jsonArray;
    }

    /**
     * 将Int的list转换成字符串，如list[06, 07] 转成string[06_07]
     *
     * @param list        int型数组
     * @param numberStyle 数字显示风格，true: 6, false: 06
     * @param emptyStyle  数组空时的显示风格， true: ""，false: "-"
     */
    protected static String transform(ArrayList<Integer> list, boolean numberStyle, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(String.format(numberStyle ? "%d" : "%02d", list.get(i)));
                if (!numberStyle && i != size - 1) {
                    builder.append(" ");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    protected static String transformSpecial(ArrayList<Integer> list, boolean numberStyle, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(list.get(i));
                if (!numberStyle && i != size - 1) {
                    builder.append("|");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    /**
     * 将Int的list转换成字串文字信息 如[1，3]
     *
     * @param list
     * @param disText
     * @return
     */
    protected static String transformText(ArrayList<Integer> list, String[] disText, boolean numberStyle, boolean
            emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(disText[list.get(i) - 1]);
                if (!numberStyle && i != size - 1) {
                    builder.append(" ");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "");
        }
        return builder.toString();
    }

    /**
     * 将Int的list转换成字串文字信息 如[1，3]
     *
     * @param list
     * @param disText
     * @return
     */
    protected static String transformTextMap(ArrayList<Integer> list, String[] disText, HashMap<String, String> map,
                                             boolean numberStyle, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(map.get(disText[list.get(i) - 1]));
                if (!numberStyle && i != size - 1) {
                    builder.append(" ");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "");
        }
        return builder.toString();
    }

    protected static ArrayList<Integer> random(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        int count = 0;
        while (count < n) {
            Random random = new Random();
            int num = random.nextInt(max - min + 1) + min;
            //int num = (int) (Math.random() * (max - min)) + min;
            if (result.contains(num)) {
                continue;
            }
            result.add(num);
            count++;
        }
        Log.e(TAG, result.toString());
        return result;
    }

    protected static ArrayList<Integer> randomCommon(int min, int max, int n, ArrayList<Integer> array) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        ArrayList<Integer> result = new ArrayList<>();
        int count = 0;
        while (count < n) {
            Random random = new Random();
            int num = random.nextInt(max - min + 1) + min;
            //int num = (int) (Math.random() * (max - min)) + min;
            boolean arrayflag = true;
            for (Integer a : array) {
                if (num == a) {
                    arrayflag = false;
                    break;
                }
            }
            if (arrayflag) {
                if (!result.contains(num)) {
                    result.add(num);
                    count++;
                }
            }
        }
        return result;
    }

    public void reset() {
        for (PickNumber pickNumber : pickNumbers) {
            pickNumber.getNumberGroupView().setCheckNumber(new ArrayList<Integer>());
        }
        notifyListener();
    }
    
    public void getResultKl8RX2()
    {
    }
    public void getResultKl8RX3()
    {
    }
    public void getResultKl8RX4()
    {
    }
    public void getResultKl8RX5()
    {
    }
    public void getResultKl8RX6()
    {
    }
    public void getResultKl8RX7()
    {
    }
}
