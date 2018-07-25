package com.wangcai.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.pattern.PickNumber;
import com.wangcai.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Random;

/**
 * 快乐十二时，一般性的玩法，均由这个类处理
 */
public class Kl12CommonGame extends Game
{
    private static final String TAG = "Kl12CommonGame";
    private static boolean isCZW;
    
    public Kl12CommonGame(Activity activity, Method method, Lottery lottery)
    {
        super(activity, method, lottery);
        if ("caizhongwei".equals(method.getNameEn()))
            isCZW = true;
        else
            isCZW = false;
    }
    
    @Override
    public void onInflate()
    {
        try
        {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.i("Kl12CommonGame", "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method
                    .getId() + " public static void " + method.getNameEn() + method.getId() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    public String getWebViewCode()
    {
        JsonArray jsonArray = new JsonArray();
        if (!isCZW)
            for (PickNumber pickNumber : pickNumbers)
            {
                jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false));
            }
        else
            for (PickNumber pickNumber : pickNumbers)
            {
                jsonArray.add(transformOffset(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false, -3));
            }
        return jsonArray.toString();
    }
    
    public String getSubmitCodes()
    {
        StringBuilder builder = new StringBuilder();
        if (!isCZW)
            for (int i = 0, size = pickNumbers.size(); i < size; i++)
            {
                builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
                if (i != size - 1)
                {
                    builder.append("|");
                }
            }
        else
            for (int i = 0, size = pickNumbers.size(); i < size; i++)
            {
                builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
            }
        return builder.toString();
    }
    
    public void onRandomCodes()
    {
        try
        {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId() + "Random",
                    Game.class);
            function.invoke(null, this);
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.i(TAG, "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method.getId() +
                    "Random" + " public static void " + method.getNameEn() + method.getId() + "Random" + "(Game game)" +
                    "" + "" + "" + "" + "" + "" + " {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    private static View createDefaultPickLayout(ViewGroup container)
    {
        if (!isCZW)
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column5, null, false);
        else
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column4, null, false);
    }
    
    private static void createPicklayout(Game game, String[] name)
    {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++)
        {
            View view = createDefaultPickLayout(game.getTopLayout());
            game.addPickNumber(new PickNumber(view, name[i]));
            views[i] = view;
        }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
    }
    
    private static void createDantuolayout(Game game, int danSize, int tuoSize)
    {
        View[] views = new View[2];
        PickNumber danNum;
        NumberGroupView danNumView;
        PickNumber tuoNum;
        NumberGroupView tuoNumView;
        
        View view0 = createDefaultPickLayout(game.getTopLayout());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        
        danNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener()
        {
            @Override
            public void onChooseItemClick(int position)
            {
                int lastPick = danNumView.getLastPick();
                int size = danNumView.getPickList().size();
                if (tuoNumView.getPickList().contains(lastPick) && danNumView.getCheckedArray().get(lastPick))
                {
                    tuoNumView.getCheckedArray().put(lastPick, false);
                    tuoNumView.getPickList().remove(Integer.valueOf(lastPick));
                    tuoNumView.invalidate();
                }
                if (size > danSize)
                {
                    danNumView.getCheckedArray().put(danNumView.getPickList().get(size - 2), false);
                    danNumView.getPickList().remove(size - 2);
                }
                game.notifyListener();
            }
        });
        
        tuoNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener()
        {
            @Override
            public void onChooseItemClick(int position)
            {
                int lastPick = tuoNumView.getLastPick();
                if (danNumView.getPickList().contains(lastPick) && tuoNumView.getCheckedArray().get(lastPick))
                {
                    danNumView.getCheckedArray().put(lastPick, false);
                    danNumView.getPickList().remove(Integer.valueOf(lastPick));
                    danNumView.invalidate();
                }
                game.notifyListener();
            }
        });
        
        ViewGroup topLayout = game.getTopLayout();
        for (View v : views)
        {
            topLayout.addView(v);
        }
    }
    
    //任选一
    public static void rx1409(Game game)
    {
        createPicklayout(game, new String[]{"选1中1"});
    }
    
    //任选二
    public static void rx2410(Game game)
    {
        createPicklayout(game, new String[]{"选2中2"});
    }
    
    //任选三
    public static void rx3411(Game game)
    {
        createPicklayout(game, new String[]{"选3中3"});
    }
    
    //任选四
    public static void rx4412(Game game)
    {
        createPicklayout(game, new String[]{"选4中4"});
    }
    
    //任选五
    public static void rx5413(Game game)
    {
        createPicklayout(game, new String[]{"选5中5"});
    }
    
    //任选六
    public static void rx6461(Game game)
    {
        createPicklayout(game, new String[]{"选6中5"});
    }
    
    //任选七
    public static void rx7462(Game game)
    {
        createPicklayout(game, new String[]{"选7中5"});
    }
    
    //任选八
    public static void rx8463(Game game)
    {
        createPicklayout(game, new String[]{"选8中5"});
    }
    
    //胆拖二
    public static void rx2467(Game game) {createDantuolayout(game, 1, 12);}
    
    //胆拖三
    public static void rx3468(Game game) {createDantuolayout(game, 2, 12);}
    
    //胆拖四
    public static void rx4469(Game game) {createDantuolayout(game, 3, 12);}
    
    //胆拖五
    public static void rx5470(Game game) {createDantuolayout(game, 4, 12);}
    
    //胆拖六
    public static void rx6473(Game game) {createDantuolayout(game, 5, 12);}
    
    //胆拖七
    public static void rx7474(Game game) {createDantuolayout(game, 6, 12);}
    
    //胆拖八
    public static void rx8475(Game game) {createDantuolayout(game, 7, 12);}
    
    //定位胆
    public static void fs426(Game game) {createPicklayout(game, new String[]{"一位", "二位", "三位", "四位", "五位"});}
    
    //二码前二直选复式
    public static void fs414(Game game) {createPicklayout(game, new String[]{"一位", "二位"});}
    
    //二码前二组选复式
    public static void fs417(Game game) {createPicklayout(game, new String[]{"前二"});}
    
    //二码前二组选胆拖
    public static void dt472(Game game) {createDantuolayout(game,1,12);}
    
    //三码前三直选复式
    public static void fs415(Game game) {createPicklayout(game, new String[]{"一位", "二位", "三位"});}
    
    //三码前三组选复式
    public static void fs416(Game game) {createPicklayout(game, new String[]{"前三"});}
    
    //三码前三组选胆拖
    public static void dt471(Game game) {createDantuolayout(game,2,12);}
    
    /*==========================================随机===================================================*/
    public static void YardsRandom(Game game, int yards)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, yards));
        game.notifyListener();
    }
    
    public static void YardsRandom(Game game, int yards, int single)
    {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++)
        {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0)
            {
                randomList = random(1, 11, yards);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(1, 11, single, randomList));
        }
        game.notifyListener();
    }
    
    public static void DistinctRandom(Game game)
    {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++)
        {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0)
            {
                randomList = random(1, 11, 1);
                pickNumber.onRandom(randomList);
            } else
            {
                ArrayList<Integer> A = randomCommon(1, 11, 1, randomList);
                randomList.addAll(A);
                pickNumber.onRandom(A);
            }
        }
        game.notifyListener();
    }
    
    //任选一
    public static void rx1409Random(Game game) {YardsRandom(game, 1);}
    
    //任选二
    public static void rx2410Random(Game game) {YardsRandom(game, 2);}
    
    //任选三
    public static void rx3411Random(Game game) {YardsRandom(game, 3);}
    
    //任选四
    public static void rx4412Random(Game game) {YardsRandom(game, 4);}
    
    //任选五
    public static void rx5413Random(Game game) {YardsRandom(game, 5);}
    
    //任选六
    public static void rx6461Random(Game game) {YardsRandom(game, 6);}
    
    //任选七
    public static void rx7462Random(Game game) {YardsRandom(game, 7);}
    
    //任选八
    public static void rx8463Random(Game game) {YardsRandom(game, 8);}
    
    //胆拖二
    public static void rx2467Random(Game game) {YardsRandom(game, 1, 1);}
    
    //胆拖三
    public static void rx3468Random(Game game) {YardsRandom(game, 1, 2);}
    
    //胆拖四
    public static void rx4469Random(Game game) {YardsRandom(game, 1, 3);}
    
    //胆拖五
    public static void rx5470Random(Game game) {YardsRandom(game, 1, 4);}
    
    //胆拖六
    public static void rx6473Random(Game game) {YardsRandom(game, 1, 5);}
    
    //胆拖七
    public static void rx7474Random(Game game) {YardsRandom(game, 1, 6);}
    
    //胆拖八
    public static void rx8475Random(Game game) {YardsRandom(game, 1, 7);}
    
    //定位胆
    public static void fs426Random(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(new ArrayList<>());
        game.notifyListener();
        PickNumber pickNumber = game.pickNumbers.get(new Random().nextInt(game.pickNumbers.size()));
        pickNumber.onRandom(random(1, 11, 1));
        game.notifyListener();
    }
    
    //二码前二直选复式
    public static void fs414Random(Game game) {YardsRandom(game, 1, 1);}
    
    //二码前二组选复式
    public static void fs417Random(Game game) {YardsRandom(game, 2);}
    
    //二码前二组选胆拖
    public static void dt472Random(Game game) {YardsRandom(game,1,1);}
    
    //三码前三直选复式
    public static void fs415Random(Game game) {DistinctRandom(game);}
    
    //三码前三组选复式
    public static void fs416Random(Game game) {YardsRandom(game,3);}
    
    //三码前三组选胆拖
    public static void dt471Random(Game game) {YardsRandom(game,2,1);}
}
