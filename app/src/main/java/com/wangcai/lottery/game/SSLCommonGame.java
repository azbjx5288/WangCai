package com.wangcai.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.pattern.PickNumber;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ace.Dong on 2018/3/12.
 */

public class SSLCommonGame extends Game {

    private static final String TAG = SSLCommonGame.class.getSimpleName();

    public SSLCommonGame(Activity activity, Method method, Lottery lottery) {
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
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), true));
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

    private static void addPickNumber2Game(Game game, View topView, String title, boolean controlStyle) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        if (controlStyle) {
            pickNumber2.getNumberGroupView().setChooseMode(controlStyle);
            pickNumber2.setControlBarHide(controlStyle);
        }
        game.addPickNumber(pickNumber2);
    }


    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    private static void createPicklayoutStyle(Game game, String[] name) {
        createPicklayout(game, name, false);
    }

    private static void createPicklayoutStyle(Game game, String[] name, boolean style) {
        createPicklayout(game, name, style);
    }

    private static void createPicklayout(Game game, String[] name, boolean style) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i], style);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    //直选复式 fushi68
     public static void fushi68(Game game) {
         createPicklayoutStyle(game, new String[]{"万位","千位", "百位", "十位", "个位"});
     }
    //直选复式 fushi295
     public static void fushi295(Game game) {
         createPicklayoutStyle(game, new String[]{"万位","千位", "百位", "十位"});
     }
    //直选复式 fushi67
     public static void fushi67(Game game) {
         createPicklayoutStyle(game, new String[]{"千位", "百位", "十位", "个位"});
     }
    //直选复式 fushi65
     public static void fushi65(Game game) {
         createPicklayoutStyle(game, new String[]{"万位","千位", "百位"});
     }
    //直选跨度 kuadu60
     public static void kuadu60(Game game) {
         createPicklayoutStyle(game, new String[]{"跨度"});
     }
    //组三 zusan16
     public static void zusan16(Game game) {
         createPicklayoutStyle(game, new String[]{"组三"});
     }
    //组六 zuliu17
     public static void zuliu17(Game game) {
         createPicklayoutStyle(game, new String[]{"组六"});
     }
    //直选复式 fushi150
     public static void fushi150(Game game) {
         createPicklayoutStyle(game, new String[]{"组六"});
     }
    //直选跨度 kuadu149
     public static void kuadu149(Game game) {
         createPicklayoutStyle(game, new String[]{"跨度"});
     }
    //组三 zusan145
     public static void zusan145(Game game) {
         createPicklayoutStyle(game, new String[]{"组三"});
     }
    //组六 zuliu146
     public static void zuliu146(Game game) {
         createPicklayoutStyle(game, new String[]{"组六"});
     }
    //直选复式 fushi69
     public static void fushi69(Game game) {
         createPicklayoutStyle(game, new String[]{"百位", "十位", "个位"});
     }
    //直选跨度 kuadu62
     public static void kuadu62(Game game) {
         createPicklayoutStyle(game, new String[]{"跨度"});
     }
    //组三 zusan49
     public static void zusan49(Game game) {
         createPicklayoutStyle(game, new String[]{"组三"});
     }
    //组六 zuliu50
     public static void zuliu50(Game game) {
         createPicklayoutStyle(game, new String[]{"组六"});
     }
    //包胆 baodan83
     public static void baodan83(Game game) {
         createPicklayoutStyle(game, new String[]{"包胆"});
     }
    //后二复式 houerfushi70
    public static void houerfushi70(Game game) {
        createPicklayoutStyle(game, new String[]{"十位", "个位"});
    }
    //后二跨度 houerkuadu63
     public static void houerkuadu63(Game game) {
         createPicklayoutStyle(game, new String[]{"跨度"});
     }
    //前二复式 qianerfushi66
     public static void qianerfushi66(Game game) {
         createPicklayoutStyle(game, new String[]{"万位","千位"});
     }
    //前二跨度 qianerkuadu61
     public static void qianerkuadu61(Game game) {
         createPicklayoutStyle(game, new String[]{"跨度"});
     }
    //定位胆 fushi78
     public static void fushi78(Game game) {
         createPicklayoutStyle(game, new String[]{"万位","千位", "百位", "十位", "个位"});
     }
    //后三一码不定位 housanyimabudingwei51
     public static void housanyimabudingwei51(Game game) {
         createPicklayoutStyle(game, new String[]{"不定位"});
     }
    //后三二码不定位 housanermabudingwei52
     public static void housanermabudingwei52(Game game) {
         createPicklayoutStyle(game, new String[]{"不定位"});
     }
    //前三一码不定位 qiansanyimabudingwei18
     public static void qiansanyimabudingwei18(Game game) {
         createPicklayoutStyle(game, new String[]{"不定位"});
     }
    //前三二码不定位 qiansanermabudingwei21
     public static void qiansanermabudingwei21(Game game) {
         createPicklayoutStyle(game, new String[]{"不定位"});
     }
    //四星一码不定位 sixingyimabudingwei34
     public static void sixingyimabudingwei34(Game game) {
         createPicklayoutStyle(game, new String[]{"不定位"});
     }
    //四星二码不定位 sixingermabudingwei35
     public static void sixingermabudingwei35(Game game) {
         createPicklayoutStyle(game, new String[]{"不定位"});
     }

    /*==========================================随机===================================================*/
    public static void YardsRandom(Game game, int yards) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, yards));
        game.notifyListener();
    }

    public static void YardsRandom(Game game, int yards, int single) {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, yards);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, single, randomList));
        }
        game.notifyListener();
    }


    //直选复式 fushi68Random
     public static void fushi68Random(Game game) {
         YardsRandom(game, 1);
     }
    //直选复式 fushi295Random
     public static void fushi295Random(Game game) {
         YardsRandom(game, 1);
     }
    //直选复式 fushi67Random
     public static void fushi67Random(Game game) {
         YardsRandom(game, 1);
     }
    //直选复式 fushi65Random
     public static void fushi65Random(Game game) {
         YardsRandom(game, 1);
     }
    //直选跨度 kuadu60Random
     public static void kuadu60Random(Game game) {
         YardsRandom(game, 1);
     }
    //组三 zusan16Random
     public static void zusan16Random(Game game) {
         YardsRandom(game, 2);
     }
    //组六 zuliu17Random
     public static void zuliu17Random(Game game) {
         YardsRandom(game, 3);
     }
    //直选复式 fushi150Random
     public static void fushi150Random(Game game) {
         YardsRandom(game, 1);
     }
    //直选跨度 kuadu149Random
     public static void kuadu149Random(Game game) {
         YardsRandom(game, 1);
     }
    //组三 zusan145Random
     public static void zusan145Random(Game game) {
         YardsRandom(game, 2);
     }
    //组六 zuliu146Random
     public static void zuliu146Random(Game game) {
         YardsRandom(game, 3);
     }
    //直选复式 fushi69Random
     public static void fushi69Random(Game game) {
         YardsRandom(game, 1);
     }
    //直选跨度 kuadu62Random
     public static void kuadu62Random(Game game) {
         YardsRandom(game, 1);
     }
    //组三 zusan49Random
     public static void zusan49Random(Game game) {
         YardsRandom(game, 2);
     }
    //组六 zuliu50Random
     public static void zuliu50Random(Game game) {
         YardsRandom(game, 3);
     }
    //包胆 baodan83Random
     public static void baodan83Random(Game game) {
         YardsRandom(game, 1);
     }
    //后二复式 houerfushi70Random
     public static void houerfushi70Random(Game game) {
         YardsRandom(game, 1);
     }
    //后二跨度 houerkuadu63Random
     public static void houerkuadu63Random(Game game) {
         YardsRandom(game, 1);
     }
    //前二复式 qianerfushi66Random
     public static void qianerfushi66Random(Game game) {
         YardsRandom(game, 1);
     }
    //前二跨度 qianerkuadu61Random
     public static void qianerkuadu61Random(Game game) {
         YardsRandom(game, 1);
     }
    //定位胆 fushi78Random
     public static void fushi78Random(Game game) {
         for (PickNumber pickNumber : game.pickNumbers)
             pickNumber.onRandom(new ArrayList<>());
         game.notifyListener();
         PickNumber pickNumber = game.pickNumbers.get(new Random().nextInt(game.pickNumbers.size()));
         pickNumber.onRandom(random(0, 9, 1));
         game.notifyListener();
     }
    //后三一码不定位 housanyimabudingwei51Random
     public static void housanyimabudingwei51Random(Game game) {
         YardsRandom(game, 1);
     }
    //后三二码不定位 housanermabudingwei52Random
     public static void housanermabudingwei52Random(Game game) {
         YardsRandom(game, 2);
     }
    //前三一码不定位 qiansanyimabudingwei18Random
     public static void qiansanyimabudingwei18Random(Game game) {
         YardsRandom(game, 1);
     }
    //前三二码不定位 qiansanermabudingwei21Random
     public static void qiansanermabudingwei21Random(Game game) {
         YardsRandom(game, 2);
     }
    //四星一码不定位 sixingyimabudingwei34Random
     public static void sixingyimabudingwei34Random(Game game) {
         YardsRandom(game, 1);
     }
    //四星二码不定位 sixingermabudingwei35Random
     public static void sixingermabudingwei35Random(Game game) {
         YardsRandom(game, 2);
     }

    //猜1不出 cbc1367
    public static void cbc1367Random(Game game) {
        YardsRandom(game, 1);
    }
    //猜2不出 cbc2368
    public static void cbc2368Random(Game game) {
        YardsRandom(game, 2);
    }
    //猜3不出 cbc3369
    public static void cbc3369Random(Game game) {
        YardsRandom(game, 3);
    }
    //猜4不出 cbc4370
    public static void cbc4370Random(Game game) {
        YardsRandom(game, 4);
    }
    //猜5不出 cbc5371
    public static void cbc5371Random(Game game) {
        YardsRandom(game, 5);
    }
}
