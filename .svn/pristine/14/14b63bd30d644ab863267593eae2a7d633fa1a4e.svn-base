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
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * pk8 和值(和值 单双大小 810五行) 奇偶 和 上中下
 */

public class Kl8SpecialGame extends Game {

    private  final String TAG = Kl8SpecialGame.class.getSimpleName();

    private Map<String,Boolean> mPickMap=new HashMap<>();//当前选中文名字集合

    private  TextView tv_pick_column_title;
    private  TextView tv_sddds_0;
    private  TextView tv_sddds_1;
    private  TextView tv_sddds_2;
    private  TextView tv_sddds_3;
    private  TextView tv_sddds_4;

    public Kl8SpecialGame(Activity activity, Method method, Lottery lottery)
    {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate()
    {
        if("danshuang".equals(method.getNameEn())){      //单双
            danshuang(this);
        }else if("daxiao810".equals(method.getNameEn())){ //daxiao810"://大小810
            daxiao810(this);
        }else if("wuxing".equals(method.getNameEn())){ //"wuxing"://五行
            wuxing(this);
        }else if("fs".equals(method.getNameEn())){//奇偶和  上中下

            if(method.getId()==429){//1.彩系： 北京快乐8（彩系：KENO） 台湾宾果（彩系：KENO） 苹果快乐8分分彩（彩系：KENO） 2.玩法：奇偶和
                jiouhe(this);
            }else  if(method.getId()==430) {//1.彩系： 北京快乐8（彩系：KENO） 台湾宾果（彩系：KENO） 苹果快乐8分分彩（彩系：KENO） 2.玩法：上中下
                shangzhongxia(this);
            }
        }else{
            Log.e("ShanDongKuaiLePuKeGame", "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + " public static " +
                    "" + "void " + method.getNameCn() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }


    //根据不同玩法返回不同的String数组
    private String[] getGameMethodStringArray() {
        switch (method.getNameEn()) {
            case "danshuang"://单双
                return new String[]{"单","双"};//包选 PKBX;
            case "daxiao810"://daxiao810"://大小810
                return new String[]{"大","810","小"};
            case "wuxing"://"wuxing"://五行
                return new String[]{"金","木","水","火","土"};
            case "fs"://奇偶和  上中下
                if(method.getId()==429){ //"jiouhe"://奇偶和
                    return new String[]{"奇","偶","和"};
                }else if(method.getId()==430){ //上中下
                    return new String[]{"上","中","下"};
                }
            default:
                return new String[]{"单","双"};//包选 PKBX;
        }
    }

    @Override
    public String getWebViewCode()
    {
        StringBuilder stringBuilder = new StringBuilder();

        String[] methodStringArray = getGameMethodStringArray();

        for (int i = 0; i < methodStringArray.length; i++)
        {
            if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                stringBuilder.append("1");
            }
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());
        return jsonArray.toString();
    }

    @Override
    public String getSubmitCodes()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String[] methodStringArray = getGameMethodStringArray();
        for (int i = 0; i < methodStringArray.length; i++)
        {
            if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                stringBuilder.append(methodStringArray[i]);
                stringBuilder.append("_");
            }
        }
        if(stringBuilder.length()>=2) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }


     class MyOnClickListener implements OnClickListener{

        @Override
        public void onClick(View view) {
            if (view.isSelected())
            {
                view.setSelected(false);
                mPickMap.put(view.getTag().toString(),false);
            } else
            {
                view.setSelected(true);
                mPickMap.put(view.getTag().toString(),true);
            }

            notifyListener();
        }
    }

    /*====================================具体玩法添加开始===========================================================================*/
    //"danshuang"://单双
    public  void danshuang(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("单双");
        tv_sddds_0.setText("单");
        tv_sddds_0.setTag("单");
        tv_sddds_1.setText("双");
        tv_sddds_1.setTag("双");
        tv_sddds_2.setVisibility(View.GONE);
        tv_sddds_3.setVisibility(View.GONE);
        tv_sddds_4.setVisibility(View.GONE);
        addTopLayout(game, view);
    }

    //daxiao810"://大小810
    public  void daxiao810(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("大小810");
        tv_sddds_0.setText("大");
        tv_sddds_0.setTag("大");
        tv_sddds_1.setText("810");
        tv_sddds_1.setTag("810");
        tv_sddds_2.setText("小");
        tv_sddds_2.setTag("小");
        tv_sddds_3.setVisibility(View.GONE);
        tv_sddds_4.setVisibility(View.GONE);
        addTopLayout(game, view);
    }

    //"wuxing"://五行
    public  void wuxing(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("五行");
        tv_sddds_0.setText("金");
        tv_sddds_0.setTag("金");
        tv_sddds_1.setText("木");
        tv_sddds_1.setTag("木");
        tv_sddds_2.setText("水");
        tv_sddds_2.setTag("水");
        tv_sddds_3.setText("火");
        tv_sddds_3.setTag("火");
        tv_sddds_4.setText("土");
        tv_sddds_4.setTag("土");
        addTopLayout(game, view);
    }

    //"jiouhe"://奇偶和
    public  void jiouhe(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("奇偶和");
        tv_sddds_0.setText("奇");
        tv_sddds_0.setTag("奇");
        tv_sddds_1.setText("偶");
        tv_sddds_1.setTag("偶");
        tv_sddds_2.setText("和");
        tv_sddds_2.setTag("和");
        tv_sddds_3.setVisibility(View.GONE);
        tv_sddds_4.setVisibility(View.GONE);
        addTopLayout(game, view);
    }

    //"shangzhongxia"://上中下
    public  void shangzhongxia(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("上中下");
        tv_sddds_0.setText("上");
        tv_sddds_0.setTag("上");
        tv_sddds_1.setText("中");
        tv_sddds_1.setTag("中");
        tv_sddds_2.setText("下");
        tv_sddds_2.setTag("下");
        tv_sddds_3.setVisibility(View.GONE);
        tv_sddds_4.setVisibility(View.GONE);
        addTopLayout(game, view);
    }

    private void initView(View view) {
        tv_pick_column_title=view.findViewById(R.id.pick_column_title);
        tv_sddds_0=view.findViewById(R.id.sddds_0);
        tv_sddds_1=view.findViewById(R.id.sddds_1);
        tv_sddds_2=view.findViewById(R.id.sddds_2);
        tv_sddds_3=view.findViewById(R.id.sddds_3);
        tv_sddds_4=view.findViewById(R.id.sddds_4);

        tv_sddds_0.setOnClickListener(new MyOnClickListener());
        tv_sddds_1.setOnClickListener(new MyOnClickListener());
        tv_sddds_2.setOnClickListener(new MyOnClickListener());
        tv_sddds_3.setOnClickListener(new MyOnClickListener());
        tv_sddds_4.setOnClickListener(new MyOnClickListener());
    }

    private  void addTopLayout(Game game, View view) {
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
    }


}