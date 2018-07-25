package com.wangcai.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * 快三彩系：苹果快三分分彩、江苏快三、甘肃快三、福建快三、广西快三、安徽快三、河北快三
 * Created by Alashi on 2017/3/10.
 */
public class KsCommonGame extends Game {
    private static final String TAG = "KsCommonGame";
    private java.lang.reflect.Method randomFunction;
    private Boolean containRandomFunction;

    public KsCommonGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    public static View createDefaultPickLayout(boolean useCircle, ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(
                useCircle ? R.layout.pick_column_text1 : R.layout.pick_column3, null, false);
    }

    private static void addPickTextGame(Game game, View topView, String title, String[] disText) {
        PickNumber pickNumberText = new PickNumber(topView, title);
        pickNumberText.getNumberGroupView().setDisplayText(disText).setNumber(1, disText.length);
        pickNumberText.setControlBarHide(true);
        game.addPickNumber(pickNumberText);
    }

    private static void createPickLayout(Game game, boolean useCircle, String name, String[] disText) {
        View view = createDefaultPickLayout(useCircle, game.getTopLayout());
        addPickTextGame(game, view, name, disText);

        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
    }

    private static void createPickLayout(Game game, String name, String[] disText) {
        View view = createDefaultPickLayout(false, game.getTopLayout());
        addPickTextGame(game, view, name, disText);

        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
    }

    private static void createPickLayout(Game game, String[] name, String[][] disText) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(false, game.getTopLayout());
            addPickTextGame(game, view, name[i], disText[i]);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    private static void random(Game game, int count) {
        PickNumber pickNumber = game.pickNumbers.get(0);
        pickNumber.onRandom(random(1, pickNumber.getNumberCount(), count));
        game.notifyListener();
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getDeclaredMethod(
                    method.getNameEn() + method.getId(), (Class[]) null);
            function.setAccessible(true);
            function.invoke(this, (Object[]) null);
        } catch (Exception e) {
            Log.e(TAG, "onInflate: can't find " + method.getNameEn() + method.getId(), e);
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRandomCodes() {
        if (containRandomFunction == null) {
            try {
                randomFunction = getClass().getDeclaredMethod(
                        method.getNameEn() + method.getId() + "Random", (Class[]) null);
                randomFunction.setAccessible(true);
                containRandomFunction = true;
            } catch (NoSuchMethodException e) {
                containRandomFunction = false;
            }
        }

        if (containRandomFunction) {
            try {
                randomFunction.invoke(this, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            random(this, 1);
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        switch (method.getNameEn() + method.getId()) {
            case "fs157": {
                ArrayList<Integer> balls = pickNumbers.get(0).getCheckedNumber();
                for (int i = 0, size = balls.size(); i < size; i++) {
                    builder.append(balls.get(i) + 2);
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            }

            case "dx160": {
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, true));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            }

            case "fs165":
            case "fs166": {
                ArrayList<Integer> balls = pickNumbers.get(0).getCheckedNumber();
                for (int i = 0, size = balls.size(); i < size; i++) {
                    builder.append(balls.get(i) == 1 ? 1 : 0);
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            }

            case "cbc1375":
            case "cbc2376":
            case "cbc3377": {
                ArrayList<Integer> balls = pickNumbers.get(0).getCheckedNumber();
                for (int i = 0, size = balls.size(); i < size; i++) {
                    builder.append(balls.get(i));
                }
                break;
            }

            default: {
                ArrayList<Integer> balls = pickNumbers.get(0).getCheckedNumber();
                for (int i = 0, size = balls.size(); i < size; i++) {
                    builder.append(balls.get(i));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            }
        }
        return builder.toString();
    }

    //和值
    private void fs157() {
        String[] texts = {"3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18"};
        createPickLayout(this, "和值", texts);
    }

    //三同号单选
    private void dx158() {
        createPickLayout(this, true, "三同号单选", new String[]{"111", "222", "333", "444", "555", "666"});
    }

    //三同号通选
    private void tx159() {
        createPickLayout(this, true, "三同号通选", new String[]{"通选"});
    }

    //二同号单选
    private void dx160() {
        String[][] texts = {{"11", "22", "33", "44", "55", "66"},
                {"1", "2", "3", "4", "5", "6"}};
        createPickLayout(this, new String[]{"同号", "不同号"}, texts);
    }

    //二同号单选
    private void dx160Random() {
        ArrayList<Integer> index = random(1, 6, 2);
        ArrayList<Integer> list0 = new ArrayList<>();
        list0.add(index.get(0));
        pickNumbers.get(0).onRandom(list0);
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(index.get(1));
        pickNumbers.get(1).onRandom(list1);
        notifyListener();
    }

    //二同号复选
    private void fx161() {
        createPickLayout(this, true, "二同号复选", new String[]{"11*", "22*", "33*", "44*", "55*", "66*"});
    }

    //三不同号
    private void fs162() {
        createPickLayout(this, "三不同号", new String[]{"1", "2", "3", "4", "5", "6"});
    }

    //三不同号
    private void fs162Random() {
        random(this, 3);
    }

    //二不同号
    private void fs163() {
        createPickLayout(this, "二不同号", new String[]{"1", "2", "3", "4", "5", "6"});
    }

    //二不同号
    private void fs163Random() {
        random(this, 2);
    }

    //三连号通选
    private void fs164() {
        createPickLayout(this, true, "三连号通选", new String[]{"通选"});
    }

    //大小
    private void fs165() {
        createPickLayout(this, "大小", new String[]{"大", "小"});
    }

    //单双
    private void fs166() {
        createPickLayout(this, "单双", new String[]{"单", "双"});
    }

    //猜必出
    private void fs167() {
        createPickLayout(this, "猜必出", new String[]{"1", "2", "3", "4", "5", "6"});
    }

    //猜1不出
    private void cbc1375() {
        createPickLayout(this, "一个号", new String[]{"1", "2", "3", "4", "5", "6"});
    }

    //猜2不出
    private void cbc2376() {
        createPickLayout(this, "二个号", new String[]{"1", "2", "3", "4", "5", "6"});
    }

    //猜2不出
    private void cbc2376Random() {
        random(this, 2);
    }

    //猜3不出
    private void cbc3377() {
        createPickLayout(this, "三个号", new String[]{"1", "2", "3", "4", "5", "6"});
    }

    //猜3不出
    private void cbc3377Random() {
        random(this, 3);
    }

    //全红
    private void red378() {
        createPickLayout(this, true, "全红", new String[]{"全红"});
    }

    //全黑
    private void black379() {
        createPickLayout(this, true, "全黑", new String[]{"全黑"});
    }
}
