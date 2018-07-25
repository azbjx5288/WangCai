package com.wangcai.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.pattern.PickNumber;
import com.wangcai.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Random;

/**
 * 11选5时，一般性的玩法，均由这个类处理，除“定单双, SDDDS”和胆拖
 */
public class SyxwCommonGame extends Game {
    private static final String TAG = "SyxwCommonGame";
    private static boolean isCZW;

    public SyxwCommonGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
        if ("caizhongwei".equals(method.getNameEn()))
            isCZW = true;
        else
            isCZW = false;
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
        if (!isCZW)
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
        if (!isCZW)
            for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
                if (i != size - 1) {
                    builder.append("|");
                }
            }
        else
            for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
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

    private static View createDefaultPickLayout(ViewGroup container) {
        if (!isCZW)
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column2, null, false);
        else
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column4, null, false);
    }

    private static void createPicklayout(Game game, String[] name) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            game.addPickNumber(new PickNumber(view, name[i]));
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    private static void createDantuolayout(Game game, int danSize, int tuoSize) {
        View[] views = new View[2];
        PickNumber danNum;
        NumberGroupView danNumView;
        PickNumber tuoNum;
        NumberGroupView tuoNumView;

        View view0 = createDefaultPickLayout(game.getTopLayout());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 20, 0, 0);
        view0.setLayoutParams(layoutParams);
        danNum = new PickNumber(view0, "胆码");
        danNum.setControlBarHide(true);
        game.addPickNumber(danNum);
        danNumView = danNum.getNumberGroupView();
        views[0] = view0;

        View view1 = createDefaultPickLayout(game.getTopLayout());
        tuoNum = new PickNumber(view1, "拖码");
        tuoNum.setControlBarHide(true);
        game.addPickNumber(tuoNum);
        tuoNumView = tuoNum.getNumberGroupView();
        views[1] = view1;

        danNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick(int position) {
                int lastPick = danNumView.getLastPick();
                int size = danNumView.getPickList().size();
                if (tuoNumView.getPickList().contains(lastPick) && danNumView.getCheckedArray().get(lastPick)) {
                    tuoNumView.getCheckedArray().put(lastPick, false);
                    tuoNumView.getPickList().remove(Integer.valueOf(lastPick));
                    tuoNumView.invalidate();
                }
                if (size > danSize) {
                    danNumView.getCheckedArray().put(danNumView.getPickList().get(size - 2), false);
                    danNumView.getPickList().remove(size - 2);
                }
                game.notifyListener();
            }
        });

        tuoNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick(int position) {
                int lastPick = tuoNumView.getLastPick();
                if (danNumView.getPickList().contains(lastPick) && tuoNumView.getCheckedArray().get(lastPick)) {
                    danNumView.getCheckedArray().put(lastPick, false);
                    danNumView.getPickList().remove(Integer.valueOf(lastPick));
                    danNumView.invalidate();
                }
                game.notifyListener();
            }
        });

        ViewGroup topLayout = game.getTopLayout();
        for (View v : views) {
            topLayout.addView(v);
        }
    }

    //前三直选复式 fushi112
    public static void fushi112(Game game) {
        createPicklayout(game, new String[]{"第一位", "第二位", "第三位"});
    }

    //前三组选复式 fushi108
    public static void fushi108(Game game) {
        createPicklayout(game, new String[]{"前三"});
    }

    //前二直选复式 fushi111
    public static void fushi111(Game game) {
        createPicklayout(game, new String[]{"第一位", "第二位"});
    }

    //前二组选复式 fushi107
    public static void fushi107(Game game) {
        createPicklayout(game, new String[]{"前二"});
    }

    //前三不定位 budingwei122
    public static void budingwei122(Game game) {
        createPicklayout(game, new String[]{"前三"});
    }

    //猜中位 caizhongwei110
    public static void caizhongwei110(Game game) {
        createPicklayout(game, new String[]{"猜中位"});
    }

    //定位胆 dingweidan106
    public static void dingweidan106(Game game) {
        createPicklayout(game, new String[]{"一位", "二位", "三位", "四位", "五位"});
    }

    //任选一中一复式 renxuanyi98
    public static void renxuanyi98(Game game) {
        createPicklayout(game, new String[]{"选1中1"});
    }

    //任选二中二复式 renxuaner99
    public static void renxuaner99(Game game) {
        createPicklayout(game, new String[]{"选2中2"});
    }

    //任选三中三复式 renxuansan100
    public static void renxuansan100(Game game) {
        createPicklayout(game, new String[]{"选3中3"});
    }

    //任选四中四复式 renxuansi101
    public static void renxuansi101(Game game) {
        createPicklayout(game, new String[]{"选4中4"});
    }

    //任选五中五复式 renxuanwu102
    public static void renxuanwu102(Game game) {
        createPicklayout(game, new String[]{"选5中5"});
    }

    //任选六中五复式 renxuanliu103
    public static void renxuanliu103(Game game) {
        createPicklayout(game, new String[]{"选6中5"});
    }

    //任选七中五复式 renxuanqi104
    public static void renxuanqi104(Game game) {
        createPicklayout(game, new String[]{"选7中5"});
    }

    //任选八中五复式 renxuanba105
    public static void renxuanba105(Game game) {
        createPicklayout(game, new String[]{"选8中5"});
    }

    //猜1不出 cbc1380
    public static void cbc1380(Game game) {
        createPicklayout(game, new String[]{"一个号"});
    }

    //猜2不出 cbc2381
    public static void cbc2381(Game game) {
        createPicklayout(game, new String[]{"二个号"});
    }

    //猜3不出 cbc3382
    public static void cbc3382(Game game) {
        createPicklayout(game, new String[]{"三个号"});
    }

    //猜4不出 cbc4383
    public static void cbc4383(Game game) {
        createPicklayout(game, new String[]{"四个号"});
    }

    //猜5不出 cbc5384
    public static void cbc5384(Game game) {
        createPicklayout(game, new String[]{"五个号"});
    }

    //前三组选胆拖 dantuo121
    public static void dantuo121(Game game) {
        createDantuolayout(game, 2, 11);
    }

    //前二组选胆拖 dantuo120
    public static void dantuo120(Game game) {
        createDantuolayout(game, 1, 11);
    }

    //任选二中二胆拖 renxuaner113
    public static void renxuaner113(Game game) {
        createDantuolayout(game, 1, 11);
    }

    //任选三中三胆拖 renxuansan114
    public static void renxuansan114(Game game) {
        createDantuolayout(game, 2, 11);
    }

    //任选四中四胆拖 renxuansi115
    public static void renxuansi115(Game game) {
        createDantuolayout(game, 3, 11);
    }

    //任选五中五胆拖 renxuanwu116
    public static void renxuanwu116(Game game) {
        createDantuolayout(game, 4, 11);
    }

    //任选六中五胆拖 renxuanliu117
    public static void renxuanliu117(Game game) {
        createDantuolayout(game, 5, 11);
    }

    //任选七中五胆拖 renxuanqi118
    public static void renxuanqi118(Game game) {
        createDantuolayout(game, 6, 11);
    }

    //任选八中五胆拖 renxuanba119
    public static void renxuanba119(Game game) {
        createDantuolayout(game, 7, 11);
    }

    /*==========================================随机===================================================*/
    public static void YardsRandom(Game game, int yards) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, yards));
        game.notifyListener();
    }

    public static void YardsRandom(Game game, int yards, int single) {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 11, yards);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(1, 11, single, randomList));
        }
        game.notifyListener();
    }

    public static void DistinctRandom(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 11, 1);
                pickNumber.onRandom(randomList);
            } else {
                ArrayList<Integer> A = randomCommon(1, 11, 1, randomList);
                randomList.addAll(A);
                pickNumber.onRandom(A);
            }
        }
        game.notifyListener();
    }

    //前三直选复式 fushi112
    public static void fushi112Random(Game game) {
        DistinctRandom(game);
    }

    //前三组选复式 fushi108
    public static void fushi108Random(Game game) {
        YardsRandom(game, 3);
    }

    //前三组选胆拖 dantuo121
    public static void dantuo121Random(Game game) {
        YardsRandom(game, 1, 2);
    }

    //前二直选复式 fushi111
    public static void fushi111Random(Game game) {
        DistinctRandom(game);
    }

    //前二组选复式 fushi107
    public static void fushi107Random(Game game) {
        YardsRandom(game, 2);
    }

    //前二组选胆拖 dantuo120
    public static void dantuo120Random(Game game) {
        YardsRandom(game, 1, 1);
    }

    //前三不定位 budingwei122
    public static void budingwei122Random(Game game) {
        YardsRandom(game, 1);
    }

    //猜中位 caizhongwei110
    public static void caizhongwei110Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(3, 9, 1));
        game.notifyListener();
    }

    //定位胆 dingweidan106
    public static void dingweidan106Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(new ArrayList<>());
        game.notifyListener();
        PickNumber pickNumber = game.pickNumbers.get(new Random().nextInt(game.pickNumbers.size()));
        pickNumber.onRandom(random(1, 11, 1));
        game.notifyListener();
    }

    //任选一中一复式 renxuanyi98
    public static void renxuanyi98Random(Game game) {
        YardsRandom(game, 1);
    }

    //任选二中二复式 renxuaner99
    public static void renxuaner99Random(Game game) {
        YardsRandom(game, 2);
    }

    //任选三中三复式 renxuansan100
    public static void renxuansan100Random(Game game) {
        YardsRandom(game, 3);
    }

    //任选四中四复式 renxuansi101
    public static void renxuansi101Random(Game game) {
        YardsRandom(game, 4);
    }

    //任选五中五复式 renxuanwu102
    public static void renxuanwu102Random(Game game) {
        YardsRandom(game, 5);
    }

    //任选六中五复式 renxuanliu103
    public static void renxuanliu103Random(Game game) {
        YardsRandom(game, 6);
    }

    //任选七中五复式 renxuanqi104
    public static void renxuanqi104Random(Game game) {
        YardsRandom(game, 7);
    }

    //任选八中五复式 renxuanba105
    public static void renxuanba105Random(Game game) {
        YardsRandom(game, 8);
    }

    //任选二中二胆拖 renxuaner113
    public static void renxuaner113Random(Game game) {
        YardsRandom(game, 1, 1);
    }

    //任选三中三胆拖 renxuansan114
    public static void renxuansan114Random(Game game) {
        YardsRandom(game, 1, 2);
    }

    //任选四中四胆拖 renxuansi115
    public static void renxuansi115Random(Game game) {
        YardsRandom(game, 1, 3);
    }

    //任选五中五胆拖 renxuanwu116
    public static void renxuanwu116Random(Game game) {
        YardsRandom(game, 1, 4);
    }

    //任选六中五胆拖 renxuanliu117
    public static void renxuanliu117Random(Game game) {
        YardsRandom(game, 1, 5);
    }

    //任选七中五胆拖 renxuanqi118
    public static void renxuanqi118Random(Game game) {
        YardsRandom(game, 1, 6);
    }

    //任选八中五胆拖 renxuanba119
    public static void renxuanba119Random(Game game) {
        YardsRandom(game, 1, 7);
    }

    //猜1不出 cbc1380
    public static void cbc1380Random(Game game) {
        YardsRandom(game, 1);
    }

    //猜2不出 cbc2381
    public static void cbc2381Random(Game game) {
        YardsRandom(game, 2);
    }

    //猜3不出 cbc3382
    public static void cbc3382Random(Game game) {
        YardsRandom(game, 3);
    }

    //猜4不出 cbc4383
    public static void cbc4383Random(Game game) {
        YardsRandom(game, 4);
    }

    //猜5不出 cbc5384
    public static void cbc5384Random(Game game) {
        YardsRandom(game, 5);
    }
}
