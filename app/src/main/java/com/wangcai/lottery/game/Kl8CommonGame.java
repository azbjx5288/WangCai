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
import com.wangcai.lottery.util.NumbericUtils;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Random;

/**
 * 快乐8，一般性的玩法，均由这个类处理
 */
public class Kl8CommonGame extends Game
{
    private static final String TAG = "Kl8CommonGame";
    
    public Kl8CommonGame(Activity activity, Method method, Lottery lottery)
    {
        super(activity, method, lottery);
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
            Log.i("Kl8CommonGame", "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method
                    .getId() + " public static void " + method.getNameEn() + method.getId() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    public String getWebViewCode()
    {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0, size = pickNumbers.size(); i < size; i++)
        {
            PickNumber pickNumber = pickNumbers.get(i);
            if (i == 0)
                jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false));
            else
                jsonArray.add(transformOffset(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false, -41));
        }
        return jsonArray.toString();
    }
    
    public String getSubmitCodes()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++)
        {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
            if (i != size - 1 && pickNumbers.get(i).getCheckedNumber().size() != 0 && pickNumbers.get(i + 1)
                    .getCheckedNumber().size() != 0)
            {
                builder.append(" ");
            }
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
                    "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + " {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    private static View createTopPickLayout(ViewGroup container)
    {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column6, null, false);
    }
    
    private static View createBottomPickLayout(ViewGroup container)
    {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column7, null, false);
    }
    
    private static void createPicklayout(Game game, String[] name)
    {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++)
        {
            View view;
            if (i == 0)
            {
                view = createTopPickLayout(game.getTopLayout());
            } else
                view = createBottomPickLayout(game.getTopLayout());
            game.addPickNumber(new PickNumber(view, name[i]));
            views[i] = view;
        }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
    }
    
    //任选一
    public static void fs400(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
    //任选二
    public static void fs401(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
    //任选三
    public static void fs402(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
    //任选四
    public static void fs403(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
    //任选五
    public static void fs404(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
    //任选六
    public static void fs405(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
    //任选七
    public static void fs406(Game game)
    {
        createPicklayout(game, new String[]{"上", "下"});
    }
    
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
    
    public static void distinctRandom(Game game)
    {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++)
        {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0)
            {
                randomList = random(1, 40, 1);
                pickNumber.onRandom(randomList);
            } else
            {
                ArrayList<Integer> A = randomCommon(1, 40, 1, randomList);
                randomList.addAll(A);
                pickNumber.onRandom(A);
            }
        }
        game.notifyListener();
    }
    
    public static void singleRandom(Game game, int n)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(new ArrayList<>());
        game.notifyListener();
        int random = new Random().nextInt(game.pickNumbers.size());
        PickNumber pickNumber = game.pickNumbers.get(random);
        if (random == 0)
            pickNumber.onRandom(random(1, 40, n));
        else
            pickNumber.onRandom(random(41, 80, n));
        game.notifyListener();
    }
    
    //任选一
    public static void fs400Random(Game game)
    {
        singleRandom(game, 1);
    }
    
    //任选二
    public static void fs401Random(Game game)
    {
        singleRandom(game, 2);
    }
    
    //任选三
    public static void fs402Random(Game game)
    {
        singleRandom(game, 3);
    }
    
    //任选四
    public static void fs403Random(Game game)
    {
        singleRandom(game, 4);
    }
    
    //任选五
    public static void fs404Random(Game game)
    {
        singleRandom(game, 5);
    }
    
    //任选六
    public static void fs405Random(Game game)
    {
        singleRandom(game, 6);
    }
    
    //任选七
    public static void fs406Random(Game game)
    {
        singleRandom(game, 7);
    }
    
    private long getResultKl8RX(int n)
    {
        return NumbericUtils.getCombine(pickNumbers.get(0).getCheckedNumber().size() + pickNumbers.get(1).getCheckedNumber().size(), n);
    }
    
    @Override
    public void getResultKl8RX2()
    {
        setSingleNum(getResultKl8RX(2));
    }
    
    @Override
    public void getResultKl8RX3()
    {
        setSingleNum(getResultKl8RX(3));
    }
    
    @Override
    public void getResultKl8RX4()
    {
        setSingleNum(getResultKl8RX(4));
    }
    
    @Override
    public void getResultKl8RX5()
    {
        setSingleNum(getResultKl8RX(5));
    }
    
    @Override
    public void getResultKl8RX6()
    {
        setSingleNum(getResultKl8RX(6));
    }
    
    @Override
    public void getResultKl8RX7()
    {
        setSingleNum(getResultKl8RX(7));
    }
}
