package com.wangcai.lottery.game;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.Method;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 11选5“定单双, SDDDS”
 * Created by User on 2016/2/23.
 */
public class SdddsGame extends Game {
    /*private static ArrayList<Integer> viewList = new ArrayList<Integer>()
    {
        {
            add(R.id.sddds_0);
            add(R.id.sddds_1);
            add(R.id.sddds_2);
            add(R.id.sddds_3);
            add(R.id.sddds_4);
            add(R.id.sddds_5);
        }
    };*/
    /*private static int[] viewList = new int[]{R.id.sddds_0, R.id.sddds_1, R.id.sddds_2, R.id.sddds_3, R.id.sddds_4, R
            .id.sddds_5};*/
    private static HashMap<String, String> viewMap = new HashMap<String, String>() {{
        put(String.valueOf(R.id.sddds_0), "0");
        put(String.valueOf(R.id.sddds_1), "1");
        put(String.valueOf(R.id.sddds_2), "2");
        put(String.valueOf(R.id.sddds_3), "3");
        put(String.valueOf(R.id.sddds_4), "4");
        put(String.valueOf(R.id.sddds_5), "5");
    }};
    private ArrayList<String> pickList;

    public SdddsGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
        pickList = new ArrayList<>();
    }

    @Override
    public void onInflate() {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.sddds, topLayout, true);
        ButterKnife.bind(this, topLayout);
    }

    @Override
    public void reset() {
        for (String i : viewMap.keySet())
            topLayout.findViewById(Integer.valueOf(i)).setSelected(false);
        pickList.clear();
        notifyListener();
    }

    @OnClick({R.id.sddds_0, R.id.sddds_1, R.id.sddds_2, R.id.sddds_3, R.id.sddds_4, R.id.sddds_5})
    public void onTextClick(TextView view) {
        if (view.isSelected()) {
            view.setSelected(false);
            pickList.remove(viewMap.get(String.valueOf(view.getId())));
        } else {
            view.setSelected(true);
            pickList.add(viewMap.get(String.valueOf(view.getId())));
        }

        notifyListener();
    }

    public void onRandomCodes() {
        ArrayList<Integer> textid = random(0, 5, 1);
        reset();
        switch (textid.get(0)) {
            case 0:
                selectView(R.id.sddds_0);
                break;
            case 1:
                selectView(R.id.sddds_1);
                break;
            case 2:
                selectView(R.id.sddds_2);
                break;
            case 3:
                selectView(R.id.sddds_3);
                break;
            case 4:
                selectView(R.id.sddds_4);
                break;
            case 5:
                selectView(R.id.sddds_5);
                break;
        }
        notifyListener();
    }

    private void selectView(int id) {
        TextView lastClick = (TextView) topLayout.findViewById(id);
        lastClick.setSelected(true);
        pickList.add(viewMap.get(String.valueOf(id)));
        notifyListener();
    }

    private void addToArray(ArrayList<Integer> arrayList, String string) {
        if (pickList.contains(string))
            arrayList.add(1);
        else
            arrayList.add(-1);
    }

    @Override
    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        JsonArray temp = new JsonArray();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String string : viewMap.values())
            addToArray(arrayList, string);
        for (Integer integer : arrayList)
            temp.add(integer);
        jsonArray.add(temp);
        return jsonArray.toString();
    }

    @Override
    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickList.size(); i < size; i++) {
            builder.append(pickList.get(i));
            if (i < size - 1)
                builder.append(" ");
        }
        return builder.toString();
    }
}
