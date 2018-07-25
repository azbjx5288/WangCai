package com.wangcai.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * pk8 和值(和值 单双大小 810五行) 奇偶 和 上中下
 */

public class Kl8Special2Game extends Game {

    private final String TAG = Kl8Special2Game.class.getSimpleName();
    private static boolean assignMode = false;
    private static String[] dansuangText = new String[]{"单", "双"};
    private HashMap<String, String> dansuangMap = new HashMap<String, String>() {{
        put("单", "1");
        put("双", "0");
    }};

    private static String[] daxiao810Text = new String[]{"大", "810", "小"};
    private HashMap<String, String> daxiao810Map = new HashMap<String, String>() {{
        put("大", "2");
        put("810", "1");
        put("小", "0");
    }};

    private static String[] wuxingText = new String[]{"金", "木", "水", "火", "土"};
    private HashMap<String, String> wuxingMap = new HashMap<String, String>() {{
        put("金", "0");
        put("木", "1");
        put("水", "2");
        put("火", "3");
        put("土", "4");
    }};

    private static String[] jiouheText = new String[]{"奇", "偶", "和"};
    private HashMap<String, String> jiouheMap = new HashMap<String, String>() {{
        put("奇", "1");
        put("偶", "0");
        put("和", "2");
    }};

    private static String[] shangzxText = new String[]{"上", "中", "下"};
    private HashMap<String, String> shangzxMap = new HashMap<String, String>() {{
        put("上", "2");
        put("中", "1");
        put("下", "0");
    }};

    public Kl8Special2Game(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method.getId() + " public static void " + method.getNameEn() + method.getId() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < pickNumbers.size(); i++) {
            PickNumber pickNumber = pickNumbers.get(i);
            if (assignMode) {
                jsonArray.add(transform(particular(pickNumber.getCheckedNumber()), pickNumber.getNumberCount(), true));
            } else {
                jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), true));
            }
        }

        return jsonArray.toString();
    }

    //处理特殊选号
    private ArrayList<Integer> particular(ArrayList<Integer> checked) {
        if (checked.size() == 0) {
            return checked;
        }
        ArrayList<Integer> checkedNew = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            if (checked.get(i) != 0) {
                checkedNew.add(checked.get(i) - 1);
            } else {
                checkedNew.add(checked.get(i));
            }
        }
        return checkedNew;
    }

    @Override
    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();

        switch (method.getNameEn()) {
            case "danshuang"://单双
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), dansuangText, dansuangMap, true, false));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            case "daxiao810"://大小810
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), daxiao810Text, daxiao810Map, true, false));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            case "wuxing"://五行
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), wuxingText, wuxingMap, true, false));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            case "fs":
                if(method.getId()==429){//2.玩法：奇偶和
                    for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                        builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), jiouheText, jiouheMap, true, false));
                        if (i != size - 1) {
                            builder.append("|");
                        }
                    }
                    break;
                }else  if(method.getId()==430) {// 2.玩法：上中下
                    for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                        builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), shangzxText, shangzxMap, true, false));
                        if (i != size - 1) {
                            builder.append("|");
                        }
                    }
                    break;
                }
        }

        return builder.toString();
    }

    public static View createAssignPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_kl8_special, null, false);
    }

    /**
     * row 行数　titleflag　标题显示 　effectflag 选号功能区
     **/
    private static void createPickTextlayout(Game game, String[] name, String[] text, boolean titleflag, boolean effectflag) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createAssignPickLayout(game.getTopLayout());
            addPickTextGame(game, view, name[i], text, titleflag, !effectflag);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }


    private static void addPickTextGame(Game game, View topView, String title, String[] text, boolean titleflag, boolean effectflag) {
        PickNumber pickNumberText = new PickNumber(topView, title);
        pickNumberText.getNumberGroupView()
                .setDisplayText(text)
                .setNumber(1, text.length)
                .setChooseMode(false)
                .setUncheckedDrawable(game.getActivity().getResources().getDrawable(R.drawable.bg_special_complete_angle_defalut_ball))
                .setCheckedDrawable(game.getActivity().getResources().getDrawable(R.drawable.bg_special_complete_angle_choose_ball));
        pickNumberText.setTitleHideOrShow(titleflag);
        pickNumberText.setControlBarHide(effectflag);
        game.addPickNumber(pickNumberText);
    }

    //单双 danshuang427
     public static void danshuang427(Game game) {
         assignMode=true;
         createPickTextlayout(game, new String[]{"单双"}, dansuangText, true, false);
     }

    //大小810 daxiao810428
     public static void daxiao810428(Game game) {
         assignMode=true;
         createPickTextlayout(game, new String[]{"大小810"}, daxiao810Text, true, false);
     }

    //五行 wuxing431
     public static void wuxing431(Game game) {
         assignMode=true;
         createPickTextlayout(game, new String[]{"五行"}, wuxingText, true, false);
     }

    //奇偶和 fs429
     public static void fs429(Game game) {
         assignMode=true;
         createPickTextlayout(game, new String[]{"奇偶和"}, jiouheText, true, false);
     }

    //上中下 fs430
     public static void fs430(Game game) {
         assignMode=true;
         createPickTextlayout(game, new String[]{"上中下"}, shangzxText, true, false);
     }
}