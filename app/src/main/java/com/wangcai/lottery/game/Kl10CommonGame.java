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
import java.util.Arrays;

/**
 * Created by Ace.Dong on 2018/1/10.
 */

public class Kl10CommonGame extends Game {

    private static final String TAG = Kl10CommonGame.class.getSimpleName();

    public Kl10CommonGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
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
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
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

    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_kl10, null, false);
    }

    private static void createPicklayout(Game game, String[] name, boolean control, int few) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            PickNumber pickNumber = new PickNumber(view, name[i]);
            if (i == few) {
                pickNumber.setControlBarHide(control);
            }
            game.addPickNumber(pickNumber);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    //单式
    //任选二 rx2477 public static void rx2477(Game game) {}
    //任选三 rx3478 public static void rx3478(Game game) {}
    //任选四 rx4479 public static void rx4479(Game game) {}
    //任选五 rx5480 public static void rx5480(Game game) {}
    //任选六 rx6483 public static void rx6483(Game game) {}
    //任选七 rx7484 public static void rx7484(Game game) {}

    //前二直选复式 fs456
    public static void fs456(Game game) {
        createPicklayout(game, new String[]{"一位", "二位"}, false, -1);
    }

    //前二组选复式 fs453
    public static void fs453(Game game) {
        createPicklayout(game, new String[]{"前二"}, false, -1);
    }

    //前二组选胆拖 dt482
    public static void dt482(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        datuo1(game);
    }

    //前三直选复式 fs455
    public static void fs455(Game game) {
        createPicklayout(game, new String[]{"一位", "二位", "三位"}, false, -1);
    }

    //前三组选复式 fs452
    public static void fs452(Game game) {
        createPicklayout(game, new String[]{"前三"}, false, -1);
    }

    static int extra = -1;

    //前三组选胆拖 dt481
    public static void dt481(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        extra = -1;
        datuo2(game, 2);
    }

    //快乐10分
    //任选复式 任选二 rx2448
    public static void rx2448(Game game) {
        createPicklayout(game, new String[]{"任选二"}, true, -1);
    }

    //任选复式 任选三 rx3449
    public static void rx3449(Game game) {
        createPicklayout(game, new String[]{"任选三"}, true, -1);
    }

    //任选复式 任选四 rx4450
    public static void rx4450(Game game) {
        createPicklayout(game, new String[]{"任选四"}, true, -1);
    }

    //任选复式 任选五 rx5451
    public static void rx5451(Game game) {
        createPicklayout(game, new String[]{"任选五"}, true, -1);
    }

    //任选复式 任选六 rx6457
    public static void rx6457(Game game) {
        createPicklayout(game, new String[]{"任选六"}, true, -1);
    }

    //任选复式 任选七 rx7458
    public static void rx7458(Game game) {
        createPicklayout(game, new String[]{"任选七"}, true, -1);
    }

    //任选胆拖 任选二 rx2477
    public static void rx2477(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        datuo1(game);
    }

    //任选胆拖 任选三 rx3478
    public static void rx3478(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        extra = -1;
        datuo2(game, 2);
    }

    //任选胆拖 任选四 rx4479
    public static void rx4479(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        extra = -1;
        datuo2(game, 3);
    }

    //任选胆拖 任选五 rx5480
    public static void rx5480(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        extra = -1;
        datuo2(game, 4);
    }

    //任选胆拖 任选六 rx6483
    public static void rx6483(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        extra = -1;
        datuo2(game, 5);
    }

    //任选胆拖 任选七 rx7484
    public static void rx7484(Game game) {
        createPicklayout(game, new String[]{"胆码", "拖码"}, true, 0);
        extra = -1;
        datuo2(game, 6);
    }

    private static void datuo1(Game game) {
        final PickNumber pickNumberDM = game.pickNumbers.get(0);
        final PickNumber pickNumberTM = game.pickNumbers.get(1);
        pickNumberDM.setChooseItemClickListener((int position) -> {
            ArrayList arrayOldDM = pickNumberDM.getCheckedNumber();
            if (pickNumberDM.getCheckedNumber().size() > 1) {
                pickNumberDM.onRandom(new ArrayList<>());
                pickNumberDM.onRandom(new ArrayList<>(Arrays.asList(position)));
            }
            ArrayList arrayTM = pickNumberTM.getCheckedNumber();
            arrayTM.removeAll(arrayOldDM);
            pickNumberTM.onRandom(arrayTM);
            game.notifyListener();
        });

        pickNumberTM.setChooseItemClickListener((int position) -> {
            ArrayList arrayDM = pickNumberDM.getCheckedNumber();
            arrayDM.removeAll(pickNumberTM.getCheckedNumber());
            pickNumberDM.onRandom(arrayDM);

            ArrayList arrayList = pickNumberTM.getCheckedNumber();
            arrayList.removeAll(pickNumberDM.getCheckedNumber());
            pickNumberTM.onRandom(arrayList);
            game.notifyListener();
        });
    }

    private static void datuo2(Game game, int digit) {
        final PickNumber pickNumberDM = game.pickNumbers.get(0);
        final PickNumber pickNumberTM = game.pickNumbers.get(1);

        pickNumberDM.setChooseItemClickListener((int position) -> {
            ArrayList arrayDM = pickNumberDM.getCheckedNumber();
            if (arrayDM.size() > digit) {
                arrayDM.remove(arrayDM.indexOf(extra));
            }
            extra = position;
            ArrayList arrayTM = pickNumberTM.getCheckedNumber();
            arrayTM.removeAll(arrayDM);
            pickNumberTM.onRandom(arrayTM);
            pickNumberDM.onRandom(arrayDM);

            game.notifyListener();
        });

        pickNumberTM.setChooseItemClickListener((int position) -> {
            ArrayList arrayDM = pickNumberDM.getCheckedNumber();
            arrayDM.removeAll(pickNumberTM.getCheckedNumber());
            pickNumberDM.onRandom(arrayDM);

            ArrayList arrayTM = pickNumberTM.getCheckedNumber();
            arrayTM.removeAll(pickNumberDM.getCheckedNumber());
            pickNumberTM.onRandom(arrayTM);

            game.notifyListener();
        });
    }

    public static void YardsRandom(Game game, int yards) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 20, yards));
        game.notifyListener();
    }

    public static void YardsRandom(Game game, int yards, int single) {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 20, yards);
                pickNumber.onRandom(randomList);
            } else {
                pickNumber.onRandom(randomCommon(1, 20, single, randomList));
            }
        }
        game.notifyListener();
    }


    //任选二 rx2448Random
    public static void rx2448Random(Game game) {
        YardsRandom(game, 2);
    }

    //任选三 rx3449Random
    public static void rx3449Random(Game game) {
        YardsRandom(game, 3);
    }

    //任选四 rx4450Random
    public static void rx4450Random(Game game) {
        YardsRandom(game, 4);
    }

    //任选五 rx5451Random
    public static void rx5451Random(Game game) {
        YardsRandom(game, 5);
    }

    //任选六 rx6457Random
    public static void rx6457Random(Game game) {
        YardsRandom(game, 6);
    }

    //任选七 rx7458Random
    public static void rx7458Random(Game game) {
        YardsRandom(game, 7);
    }

    //任选二 rx2477Random
    public static void rx2477Random(Game game) {
        YardsRandom(game, 1, 1);
    }

    //任选三 rx3478Random
    public static void rx3478Random(Game game) {
        YardsRandom(game, 2, 1);
    }

    //任选四 rx4479Random
    public static void rx4479Random(Game game) {
        YardsRandom(game, 3, 1);
    }

    //任选五 rx5480Random
    public static void rx5480Random(Game game) {
        YardsRandom(game, 4, 1);
    }

    //任选六 rx6483Random
    public static void rx6483Random(Game game) {
        YardsRandom(game, 5, 1);
    }

    //任选七 rx7484Random
    public static void rx7484Random(Game game) {
        YardsRandom(game, 6, 1);
    }

    //前二直选复式 fs456Random
    public static void fs456Random(Game game) {
        YardsRandom(game, 1);
    }

    //前二组选复式 fs453Random
    public static void fs453Random(Game game) {
        YardsRandom(game, 2);
    }

    //前二组选胆拖 dt482Random
    public static void dt482Random(Game game) {
        YardsRandom(game, 1, 1);
    }

    //前三直选复式 fs455Random
    public static void fs455Random(Game game) {
        YardsRandom(game, 1);
    }

    //前三组选复式 fs452Random
    public static void fs452Random(Game game) {
        YardsRandom(game, 3);
    }

    //前三组选胆拖 dt481Random
    public static void dt481Random(Game game) {
        YardsRandom(game, 2, 1);
    }
}
