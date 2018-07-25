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
import java.util.HashMap;

/**
 * 任选玩法
 * Created by ACE-PC on 2016/6/2.
 */
public class ArbitraryGame extends Game {
    private static final String TAG = ArbitraryGame.class.getSimpleName();
    private static String[] disText = new String[]{"万位", "千位", "百位", "十位", "个位"};
    private static String[] dismergeText = new String[]{"小(0-4)", "大(5-9)"};
    private static String[] intervalText = new String[]{"一区(0,1)", "二区(2,3)", "三区(4,5)", "四区(6,7)", "五区(8,9)"};
    private static HashMap<String, String> dismergeTextMap = new HashMap<String, String>() {{
        put("小(0-4)", "0");
        put("大(5-9)", "1");
    }};

    private static HashMap<String, String> intervalTextMap = new HashMap<String, String>() {{
        put("一区(0,1)", "0");
        put("二区(2,3)", "1");
        put("三区(4,5)", "2");
        put("四区(6,7)", "3");
        put("五区(8,9)", "4");
    }};

    private static boolean assignMode = false; //包括 文字 true, 不包括 false
    private static int assign = 0;

    public ArbitraryGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("ArbitraryGame", "onInflate: " + "//"
                    + method.getNameCn() + " " + method.getNameEn() + method.getId()
                    + " public static void " + method.getNameEn() + method.getId()
                    + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < pickNumbers.size(); i++) {
            PickNumber pickNumber = pickNumbers.get(i);
            if (assignMode) {
                jsonArray.add(transform(particular(pickNumber.getCheckedNumber(), i), pickNumber.getNumberCount(), true));
            } else {
                jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), true));
            }
        }
        return jsonArray.toString();
    }

    //处理特殊选号
    private ArrayList<Integer> particular(ArrayList<Integer> checked, int few) {
        if (checked.size() == 0 || few >= assign) {
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

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        int index=0;
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            if (assignMode) {
                switch (method.getId()) {
                    case 38:
                    case 39:
                    case 40:
                    case 55:
                        if (i < assign) {
                            builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), dismergeText, dismergeTextMap, true, false));
                        } else {
                            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, true));
                        }
                        index=1;
                        break;
                    case 41:
                    case 42:
                    case 43:
                    case 56:
                        if (i < assign) {
                            builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), intervalText, intervalTextMap, true, false));
                        } else {
                            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, true));
                        }
                        index=1;
                        break;
                }
            } else {
                builder.append(transformText(pickNumbers.get(i).getCheckedNumber(), disText, true, false));
            }

            if (i != size - 1) {
                builder.append(Delimiter.fromCode(index).getSymbol());
            }
        }
        return builder.toString();
    }

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//"
                    + method.getNameCn() + " " + method.getNameEn() + method.getId() + "Random"
                    + " public static void " + method.getNameEn() + method.getId() + "Random"
                    + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    public static View createAssignPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_text, null, false);
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

    private static void addPickNumberGame(Game game, View topView, String title, boolean effectflag) {
        PickNumber pickNumber = new PickNumber(topView, title);
        pickNumber.setControlBarHide(effectflag);
        game.addPickNumber(pickNumber);
    }

    /**
     * 标题 title, 最大 min, 最小 max,标题显示　titleflag　选号功能区 effectflag
     **/
    private static void addPickSpecialNumberGame(Game game, View topView, String title, int min, int max, boolean flag) {
        PickNumber pickNumberSpecial = new PickNumber(topView, title);
        pickNumberSpecial.setControlBarHide(flag);
        pickNumberSpecial.getNumberGroupView().setNumber(min, max);
        game.addPickNumber(pickNumberSpecial);
    }

    /**
     * row 行数　titleflag　标题显示 　effectflag 选号功能区
     **/
    private static void createPickTextlayout(Game game, String[] name, String[] text, int row, boolean titleflag, boolean effectflag) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view;
            if (i < row) {
                view = createAssignPickLayout(game.getTopLayout());
                addPickTextGame(game, view, name[i], text, titleflag, !effectflag);
            } else {
                view = createDefaultPickLayout(game.getTopLayout());
                addPickNumberGame(game, view, name[i], effectflag);
            }
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    private static void createPicklayout(Game game, String[] name, String[] text, int min, int max, boolean titleFlag, boolean effectflag) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view;
            if (i == 0) {
                view = createAssignPickLayout(game.getTopLayout());
                addPickTextGame(game, view, name[i], text, titleFlag, effectflag);
            } else {
                view = createDefaultPickLayout(game.getTopLayout());
                addPickSpecialNumberGame(game, view, name[i], min, max, effectflag);
            }
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    private static void renxuanertip(Game game) {
        final PickNumber pickNumber = game.pickNumbers.get(0);
        ArrayList<Integer> result = new ArrayList<>();
        result.add(new Integer(4));
        result.add(new Integer(5));
        pickNumber.onRandom(result);
        pickNumber.setChooseItemClickListener((int position) -> {
            ArrayList<Integer> numList = pickNumber.getCheckedNumber();
            if (numList.size() < 2) {
                game.onCustomDialog("请从万位、千位、百位、十位、个位中任意选择两个位置");
            } else {
                result.clear();
                result.addAll(numList);
            }
            pickNumber.onRandom(result);
            game.notifyListener();
        });
    }

    //任选二
    //直选和值 zhixuanhezhi196
    public static void zhixuanhezhi196(Game game) {
        createPicklayout(game, new String[]{"位值", "任二直选和值"}, disText, 0, 2 * 9, false, true);
        renxuanertip(game);
    }

    //直选跨度 zhixuankuadu198
    public static void zhixuankuadu198(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任二直选跨度"}, disText, 1, false, true);
        renxuanertip(game);
    }

    //组选复式 zuxuanfushi195
    public static void zuxuanfushi195(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任二组选复式"}, disText, 1, false, true);
        renxuanertip(game);
    }

    //组选和值 zuxuanhezhi197
    public static void zuxuanhezhi197(Game game) {
        createPicklayout(game, new String[]{"位值", "任二组选和值"}, disText, 1, (2 * 9) - 1, false, true);
        renxuanertip(game);
    }

    private static void renxuansantip(Game game) {
        final PickNumber pickNumber = game.pickNumbers.get(0);
        ArrayList<Integer> result = new ArrayList<>();
        result.add(new Integer(3));
        result.add(new Integer(4));
        result.add(new Integer(5));
        pickNumber.onRandom(result);
        pickNumber.setChooseItemClickListener((int position) -> {
            ArrayList<Integer> numList = pickNumber.getCheckedNumber();
            if (numList.size() < 3) {
                game.onCustomDialog("请从万位、千位、百位、十位、个位中任意选择三个位置");
            } else {
                result.clear();
                result.addAll(numList);
            }
            pickNumber.onRandom(result);
            game.notifyListener();
        });
    }

    //任选三
    //直选和值 zhixuanhezhi183
    public static void zhixuanhezhi183(Game game) {
        createPicklayout(game, new String[]{"位值", "任三直选和值"}, disText, 0, 3 * 9, false, true);
        renxuansantip(game);
    }

    //直选跨度 zhixuankuadu185
    public static void zhixuankuadu185(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任三直选跨度"}, disText, 1, false, false);
        renxuansantip(game);
    }

    //组三复式 zu3fushi181
    public static void zu3fushi181(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任三组三复式"}, disText, 1, false, false);
        renxuansantip(game);
    }

    //组六复式 zu6fushi182
    public static void zu6fushi182(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任三组六复式"}, disText, 1, false, false);
        renxuansantip(game);
    }

    //组选和值 zuxuanhezhi184
    public static void zuxuanhezhi184(Game game) {
        createPicklayout(game, new String[]{"位值", "任三组选和值"}, disText, 1, (3 * 9) - 1, false, true);
        renxuansantip(game);
    }

    private static void renxuanshitip(Game game) {
        final PickNumber pickNumber = game.pickNumbers.get(0);
        ArrayList<Integer> result = new ArrayList<>();
        result.add(new Integer(2));
        result.add(new Integer(3));
        result.add(new Integer(4));
        result.add(new Integer(5));
        pickNumber.onRandom(result);
        pickNumber.setChooseItemClickListener((int position) -> {
            ArrayList<Integer> numList = pickNumber.getCheckedNumber();
            if (numList.size() < 4) {
                game.onCustomDialog("请从万位、千位、百位、十位、个位中任意选择四个位置");
            } else {
                result.clear();
                result.addAll(numList);
            }
            pickNumber.onRandom(result);
            game.notifyListener();
        });
    }

    //任选四
    //组选24 zuxuan24194
    public static void zuxuan24194(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任四组选24"}, disText, 1, false, false);
        renxuanshitip(game);
    }

    //组选12 zuxuan12193
    public static void zuxuan12193(Game game) {
        createPickTextlayout(game, new String[]{"位值", "二重号", "单号"}, disText, 1, false, false);
        renxuanshitip(game);
    }

    //组选6 zuxuan6192
    public static void zuxuan6192(Game game) {
        createPickTextlayout(game, new String[]{"位值", "任四二重号"}, disText, 1, false, false);
        renxuanshitip(game);
    }

    //组选4 zuxuan4191
    public static void zuxuan4191(Game game) {
        createPickTextlayout(game, new String[]{"位值", "三重号", "单号"}, disText, 1, false, false);
        renxuanshitip(game);
    }

    //五码趣味三星 wumaquweisanxing38
    public static void wumaquweisanxing38(Game game) {
        assign = 2;
        assignMode = true;
        createPickTextlayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"}, dismergeText, assign, true, false);
    }

    //四码趣味三星 simaquweisanxing39
    public static void simaquweisanxing39(Game game) {
        assign = 1;
        assignMode = true;
        createPickTextlayout(game, new String[]{"千位", "百位", "十位", "个位"}, dismergeText, assign, true, false);
    }

    //前三趣味二星 qiansanquweierxing
    public static void qiansanquweierxing40(Game game) {
        assign = 1;
        assignMode = true;
        createPickTextlayout(game, new String[]{"万位", "千位", "百位"}, dismergeText, assign, true, false);
    }

    //五码区间三星 wumaqujiansanxing41
    public static void wumaqujiansanxing41(Game game) {
        assign = 2;
        assignMode = true;
        createPickTextlayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"}, intervalText, assign, true, false);
    }

    //四码区间三星 simaqujiansanxing42
    public static void simaqujiansanxing42(Game game) {
        assign = 1;
        assignMode = true;
        createPickTextlayout(game, new String[]{"千位", "百位", "十位", "个位"}, intervalText, assign, true, false);
    }

    //前三区间二星 qiansanqujianerxing43
    public static void qiansanqujianerxing43(Game game) {
        assign = 1;
        assignMode = true;
        createPickTextlayout(game, new String[]{"万位", "千位", "百位"}, intervalText, assign, true, false);
    }

    //后三趣味二星 housanquweierxing55
    public static void housanquweierxing55(Game game) {
        assign = 1;
        assignMode = true;
        createPickTextlayout(game, new String[]{"百位", "十位", "个位"}, dismergeText, assign, true, false);
    }

    //后三区间二星 housanqujianerxing56
    public static void housanqujianerxing56(Game game) {
        assign = 1;
        assignMode = true;
        createPickTextlayout(game, new String[]{"百位", "十位", "个位"}, intervalText, assign, true, false);
    }

    /*==========================================随机===================================================*/

    public static void YardsRandom(Game game, int digit, int textLength, int yards) {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i < digit) {
                randomList = random(1, textLength, yards);
                pickNumber.onRandom(randomList);
            } else {
                pickNumber.onRandom(random(0, 9, yards));
            }
        }
        game.notifyListener();
    }

    /**
     * 趣味
     **/
    //五码趣味三星 wumaquweisanxing38Random
    public static void wumaquweisanxing38Random(Game game) {
        YardsRandom(game, assign, dismergeText.length, 1);
    }

    //四码趣味三星 simaquweisanxing39Random
    public static void simaquweisanxing39Random(Game game) {
        YardsRandom(game, assign, dismergeText.length, 1);
    }

    //后三趣味二星 housanquweierxing55Random
    public static void housanquweierxing55Random(Game game) {
        YardsRandom(game, assign, dismergeText.length, 1);
    }

    //前三趣味二星 qiansanquweierxing40Random
    public static void qiansanquweierxing40Random(Game game) {
        YardsRandom(game, assign, dismergeText.length, 1);
    }

    //五码区间三星 wumaqujiansanxing41Random
    public static void wumaqujiansanxing41Random(Game game) {
        YardsRandom(game, assign, intervalText.length, 1);
    }

    //四码区间三星 simaqujiansanxing42Random
    public static void simaqujiansanxing42Random(Game game) {
        YardsRandom(game, assign, intervalText.length, 1);
    }

    //后三区间二星 housanqujianerxing56Random
    public static void housanqujianerxing56Random(Game game) {
        YardsRandom(game, assign, intervalText.length, 1);
    }

    //前三区间二星 qiansanqujianerxing43Random
    public static void qiansanqujianerxing43Random(Game game) {
        YardsRandom(game, assign, intervalText.length, 1);
    }

    /** 任选 **/
    //直选复式 zhixuanfushi199Random public static void zhixuanfushi199Random(Game game) {}
    //直选和值 zhixuanhezhi196Random public static void zhixuanhezhi196Random(Game game) {}
    //直选跨度 zhixuankuadu198Random public static void zhixuankuadu198Random(Game game) {}
    //组选复式 zuxuanfushi195Random public static void zuxuanfushi195Random(Game game) {}
    //组选和值 zuxuanhezhi197Random public static void zuxuanhezhi197Random(Game game) {}
    //直选复式 zhixuanfushi179Random public static void zhixuanfushi179Random(Game game) {}
    //直选和值 zhixuanhezhi183Random public static void zhixuanhezhi183Random(Game game) {}
    //直选跨度 zhixuankuadu185Random public static void zhixuankuadu185Random(Game game) {}
    //组三复式 zu3fushi181Random public static void zu3fushi181Random(Game game) {}
    //组六复式 zu6fushi182Random public static void zu6fushi182Random(Game game) {}
    //组选和值 zuxuanhezhi184Random public static void zuxuanhezhi184Random(Game game) {}
    //直选复式 fushi180Random public static void fushi180Random(Game game) {}
    //组选24 zuxuan24194Random public static void zuxuan24194Random(Game game) {}
    //组选12 zuxuan12193Random public static void zuxuan12193Random(Game game) {}
    //组选6 zuxuan6192Random public static void zuxuan6192Random(Game game) {}
    //组选4 zuxuan4191Random public static void zuxuan4191Random(Game game) {}
}
