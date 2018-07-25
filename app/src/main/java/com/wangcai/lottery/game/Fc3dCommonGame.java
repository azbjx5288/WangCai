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
import java.util.Random;

/**
 * Created by ACE-PC on 2016/2/19.
 */
public class Fc3dCommonGame extends Game {

    private static final String TAG = Fc3dCommonGame.class.getSimpleName();

    public Fc3dCommonGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method
                    .getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() +
                    method.getId() + " public static void " + method.getNameEn() + method.getId()
                    + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(),true));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, true));
            if (i != size - 1) {
                builder.append("|");
            }
        }
        return builder.toString();
    }

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method
                    .getId() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() +
                    method.getId() + "Random" + " public static void " + method.getNameEn() +
                    method.getId() + "Random" + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        game.addPickNumber(pickNumber2);
    }


    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null,
                false);
    }

    private static void createPicklayout(Game game, String[] name) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    //复式 fushi136
    public static void fushi136(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"});
    }

    //组三 zusan131
    public static void zusan131(Game game) {
        createPicklayout(game, new String[]{"组三"});
    }

    //组六 zuliu132
    public static void zuliu132(Game game) {
        createPicklayout(game, new String[]{"组六"});
    }

    //后二直选 houerfushi138
    public static void houerfushi138(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"});
    }

    //前二直选 qianerfushi137
    public static void qianerfushi137(Game game) {
        createPicklayout(game, new String[]{"百位", "十位"});
    }

    //后二组选 houerfushi135
    public static void houerfushi135(Game game) {
        createPicklayout(game, new String[]{"后二组选"});
    }

    //前二组选 qianerfushi134
    public static void qianerfushi134(Game game) {
        createPicklayout(game, new String[]{"前二组选"});
    }

    //定位胆 sanxingdingweidan141
    public static void sanxingdingweidan141(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"});
    }

    //一码不定位 qiansanyimabudingwei133
    public static void qiansanyimabudingwei133(Game game) {
        createPicklayout(game, new String[]{"一码不定位"});
    }

    //二码不定位 erma485
    public static void erma485(Game game) {
        createPicklayout(game, new String[]{"二码不定位"});
    }

    //猜1不出 cbc1367
    public static void cbc1367(Game game) {
        createPicklayout(game, new String[]{"一个号"});
    }

    //猜2不出 cbc2368
    public static void cbc2368(Game game) {
        createPicklayout(game, new String[]{"二个号"});
    }

    //猜3不出 cbc3369
    public static void cbc3369(Game game) {
        createPicklayout(game, new String[]{"三个号"});
    }

    //猜4不出 cbc4370
    public static void cbc4370(Game game) {
        createPicklayout(game, new String[]{"四个号"});
    }

    //猜5不出 cbc5371
    public static void cbc5371(Game game) {
        createPicklayout(game, new String[]{"五个号"});
    }

    //随机方法

    public static void YardsRandom(Game game, int yards) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, yards));
        game.notifyListener();
    }

    //复式 fushi136Random
    public static void fushi136Random(Game game) {
        YardsRandom(game, 1);
    }

    //组三 zusan131Random
    public static void zusan131Random(Game game) {
        YardsRandom(game, 2);
    }

    //组六 zuliu132Random
    public static void zuliu132Random(Game game) {
        YardsRandom(game, 3);
    }

    //后二直选 houerfushi138Random
    public static void houerfushi138Random(Game game) {
        YardsRandom(game, 1);
    }

    //前二直选 qianerfushi137Random
    public static void qianerfushi137Random(Game game) {
        YardsRandom(game, 1);
    }

    //后二组选 houerfushi135Random
    public static void houerfushi135Random(Game game) {
        YardsRandom(game, 2);
    }

    //前二组选 qianerfushi134Random
    public static void qianerfushi134Random(Game game) {
        YardsRandom(game, 2);
    }

    //定位胆 sanxingdingweidan141Random
    public static void sanxingdingweidan141Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(new ArrayList<>());
        game.notifyListener();
        PickNumber pickNumber = game.pickNumbers.get(new Random().nextInt(game.pickNumbers.size()));
        pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //一码不定位 qiansanyimabudingwei133Random
    public static void qiansanyimabudingwei133Random(Game game) {
        YardsRandom(game, 1);
    }

    //二码不定位 erma485Random
    public static void erma485Random(Game game) {
        YardsRandom(game, 2);
    }

    //猜1不出 cbc1367Random
    public static void cbc1367Random(Game game) {
        YardsRandom(game, 1);
    }

    //猜2不出 cbc2368Random
    public static void cbc2368Random(Game game) {
        YardsRandom(game, 2);
    }

    //猜3不出 cbc3369Random
    public static void cbc3369Random(Game game) {
        YardsRandom(game, 3);
    }

    //猜4不出 cbc4370Random
    public static void cbc4370Random(Game game) {
        YardsRandom(game, 4);
    }

    //猜5不出 cbc5371Random
    public static void cbc5371Random(Game game) {
        YardsRandom(game, 5);
    }
}
