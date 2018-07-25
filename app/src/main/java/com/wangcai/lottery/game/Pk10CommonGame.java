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
 * 北京PK10数字玩法
 * Created by Sakura on 2016/10/20.
 */
public class Pk10CommonGame extends Game {
    private static final String TAG = "Pk10CommonGame";

    private static boolean isGYHZ;

    public Pk10CommonGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
        if ("hezhi".equals(method.getNameEn()))
            isGYHZ = true;
        else
            isGYHZ = false;
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("SyxwCommonGame", "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method
                    .getId() + " public static void " + method.getNameEn() + method.getId() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        if (!isGYHZ)
            for (PickNumber pickNumber : pickNumbers) {
                jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false));
            }
        else
            for (PickNumber pickNumber : pickNumbers) {
                jsonArray.add(transformOffset(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false, -3));
            }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        if (!isGYHZ)
            for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
                if (i != size - 1) {
                    builder.append("|");
                }
            }
        else
            for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                builder.append(transformSpecial(pickNumbers.get(i).getCheckedNumber(), false, true));
            }
        return builder.toString();
    }

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId() + "Random",
                    Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method.getId() +
                    "Random" + " public static void " + method.getNameEn() + method.getId() + "Random" + "(Game game)" +
                    " {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        game.addPickNumber(pickNumber2);
    }

    public static View createDefaultPickLayout(ViewGroup container) {
        if (!isGYHZ)
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_pk10, null, false);
        else
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_pk10_gyhz, null, false);
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

    //冠亚和 hezhi175
    public static void hezhi175(Game game) {
        createPicklayout(game, new String[]{"冠亚和"});
    }

    //猜名次 cmc177
    public static void cmc177(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"});
    }

    //前二名直选 fs172
    public static void fs172(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军"});
    }

    //后二名直选 fs394
    public static void fs394(Game game) {
        createPicklayout(game, new String[]{"第九名", "第十名"});
    }

    //前三名直选 fs173
    public static void fs173(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军"});
    }

    //后三名直选 fs395
    public static void fs395(Game game) {
        createPicklayout(game, new String[]{"第八名", "第九名", "第十名"});
    }

    //前四名直选 fs486
    public static void fs486(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名"});
    }

    //后四名直选 fs487
    public static void fs487(Game game) {
        createPicklayout(game, new String[]{"第七名", "第八名", "第九名", "第十名"});
    }

    //前五名直选 fs490
    public static void fs490(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名"});
    }

    //后四名直选 fs491
    public static void fs491(Game game) {
        createPicklayout(game, new String[]{"第六名", "第七名", "第八名", "第九名", "第十名"});
    }

    /*==========================================随机===================================================*/
    public static void YardsRandom(Game game, int yards) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(3, 19, yards));
        game.notifyListener();
    }

    public static void YardsRandom(Game game, int yards, int single) {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 10, yards);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(1, 10, single, randomList));
        }
        game.notifyListener();
    }

    public static void DistinctRandom(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 10, 1);
                pickNumber.onRandom(randomList);
            } else {
                ArrayList<Integer> A = randomCommon(1, 10, 1, randomList);
                randomList.addAll(A);
                pickNumber.onRandom(A);
            }
        }
        game.notifyListener();
    }

    //冠亚和 hezhi175
    public static void hezhi175Random(Game game) {
        YardsRandom(game, 1);
    }

    //猜名次 cmc177
    public static void cmc177Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(new ArrayList<>());
        game.notifyListener();
        PickNumber pickNumber = game.pickNumbers.get(new Random().nextInt(game.pickNumbers.size()));
        pickNumber.onRandom(random(1, 10, 1));
        game.notifyListener();
    }

    //前二名直选 fs172
    public static void fs172Random(Game game) {
        DistinctRandom(game);
    }

    //后二名直选 fs394
    public static void fs394Random(Game game) {
        DistinctRandom(game);
    }

    //前三名直选 fs173
    public static void fs173Random(Game game) {
        DistinctRandom(game);
    }

    //后三名直选 fs395
    public static void fs395Random(Game game) {
        DistinctRandom(game);
    }

    //前四名直选 fs486
    public static void fs486Random(Game game) {
        DistinctRandom(game);
    }

    //后四名直选 fs487
    public static void fs487Random(Game game) {
        DistinctRandom(game);
    }

    //前五名直选 fs490
    public static void fs490Random(Game game) {
        DistinctRandom(game);
    }

    //前五名直选 fs491
    public static void fs491Random(Game game) {
        DistinctRandom(game);
    }
}
