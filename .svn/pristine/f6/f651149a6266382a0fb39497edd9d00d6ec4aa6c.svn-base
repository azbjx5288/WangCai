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

/**
 * Created by ACE-PC on 2016/2/19.
 */
public class SpecialTwoGame extends Game {
    private static final String TAG = SpecialTwoGame.class.getSimpleName();

    public SpecialTwoGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//"
                    + method.getNameCn() + " " + method.getNameEn() + method.getId()
                    + " public static void " + method.getNameEn() + method.getId()
                    + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(),false));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transformSpecial(pickNumbers.get(i).getCheckedNumber(), false, false));
            if (i != size - 1) {
                builder.append("|");
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

    private static void addPickSpecialNumberGame(Game game, View topView, String title, int min, int max,boolean numberStyle) {
        PickNumber pickNumberSpecial = new PickNumber(topView, title);
        pickNumberSpecial.setNumberStyle(numberStyle);
		pickNumberSpecial.setControlBarHide(false);
        pickNumberSpecial.getNumberGroupView().setNumber(min, max);
        game.addPickNumber(pickNumberSpecial);
    }

    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    private static void createPicklayout(Game game, String[] name, int min, int max,boolean style) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickSpecialNumberGame(game, view, name[i], min, max,style);
            views[i] = view;
        }
        addPicklayout(game,views);
    }

    private static void addPicklayout(Game game,View[] views){
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    //组选和值 hezhi75
    public static void hezhi75(Game game) {
        createPicklayout(game, new String[]{"组选和值"}, 1, (3 * 9) - 1,true);
    }

    //组选和值 hezhi154
    public static void hezhi154(Game game) {
        createPicklayout(game, new String[]{"组选和值"}, 1, (3 * 9) - 1,true);
    }

    //组选和值 hezhi80
    public static void hezhi80(Game game) {
        createPicklayout(game, new String[]{"组选和值"}, 1, (3 * 9) - 1,true);
    }

    //后二和值组选 houerhezhi77
    public static void houerhezhi77(Game game) {
        createPicklayout(game, new String[]{"组选和值"}, 1, (2 * 9) - 1,true);
    }

    //前二和值组选 qianerhezhi76
    public static void qianerhezhi76(Game game) {
        createPicklayout(game, new String[]{"组选和值"}, 1, (2 * 9) - 1,true);
    }
    
    //组选和值 hezhi140
    public static void hezhi140(Game game)
    {
        createPicklayout(game, new String[]{"组选和值"}, 1, 3 * 9 - 1,true);
    }

    /****************************
     * 随机
     ****************************/
    public static void YardsRandom(Game game, int start, int amount, int yards) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(start, amount, yards));
        game.notifyListener();
    }

    //组选和值 hezhi75Random
    public static void hezhi75Random(Game game) {
        YardsRandom(game, 1, (3 * 9) - 1, 1);
    }

    //组选和值 hezhi154Random
    public static void hezhi154Random(Game game) {
        YardsRandom(game, 1, (3 * 9) - 1, 1);
    }

    //组选和值 hezhi80Random
    public static void hezhi80Random(Game game) {
        YardsRandom(game, 1, (3 * 9) - 1, 1);
    }

    //后二组选和值 houerhezhi77Random
    public static void houerhezhi77Random(Game game) {
        YardsRandom(game, 1, (2 * 9) - 1, 1);
    }

    //前二组选和值 qianerhezhi76Random
    public static void qianerhezhi76Random(Game game) {
        YardsRandom(game, 1, (2 * 9) - 1, 1);
    }
    
    //组选和值 hezhi140Random
    public static void hezhi140Random(Game game) {
        YardsRandom(game, 1, 3 * 9 - 1, 1);
    }
}
