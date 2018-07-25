package com.wangcai.lottery.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.Bet;
import com.wangcai.lottery.data.Trace;
import com.wangcai.lottery.fragment.BetOrTraceDetailFragment;
import com.wangcai.lottery.user.UserCentre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/1/15.
 */
public class GameHistoryAdapter extends BaseAdapter {
    private static final int STATE_WIN = 0;
    private static final int STATE_FAIL = 1;
    private static final int STATE_DOING = 2;
    private static final int STATE_DONE = 3;
    
    private List dataList=new ArrayList();
    private BaseFragment fragment;
    private int[] colors = new int[4];
    private UserCentre userCentre;
    private SparseArray<String> states;

    public GameHistoryAdapter(Context context,BaseFragment fragment) {
        Resources rs = context.getResources();
        userCentre = WangCaiApp.getUserCentre();
        colors[STATE_WIN] = rs.getColor(R.color.gameListItemWin);
        colors[STATE_FAIL] = rs.getColor(R.color.gameListItemFail);
        colors[STATE_DOING] = rs.getColor(R.color.gameListItemDoing);
        colors[STATE_DONE] = rs.getColor(R.color.gameListItemDone);
        this.fragment=fragment;
        states = new SparseArray<>();
        states.put(0, "待开奖");
        states.put(1, "已撤销");
        states.put(2, "未中奖");
        states.put(3, "已中奖");
        states.put(4, "已派奖");
        states.put(5, "系统撤销");
    }

    public void setData(List data) {
        this.dataList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null? 0: dataList.size();
    }

    @Override
    public Object getItem(int position) {
        if (dataList == null) {
            return null;
        }
        if (position >= 0 && position < dataList.size()) {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_history_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Object data = this.dataList.get(position);
        if (data instanceof Bet) {
            setBetData(viewHolder, (Bet) data);
        } else {
            setTraceData(viewHolder, (Trace) data);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data instanceof Bet) {
                    BetOrTraceDetailFragment.launch(fragment, (Bet) data);
                } else {
                    BetOrTraceDetailFragment.launch(fragment, (Trace) data);
                }
            }
        });
        return convertView;
    }

    private void setTraceData(ViewHolder viewHolder, Trace data) {
        viewHolder.money.setText(data.getAmount() + "元");
        viewHolder.time.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(data.getBoughtAt()));
//        Lottery lottery = userCentre.getLottery(data.getLotteryId());
//        if (lottery != null) {
            viewHolder.name.setText(data.getLotteryName());
//        } else {
//            viewHolder.name.setText("未知彩种");
//        }
        viewHolder.gamename.setVisibility(View.GONE);
        switch (data.getStatus()) {
            case 0:
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText("进行中");
                break;

            case 1:
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText("已完成");
                break;

            case 2:
                viewHolder.state.setTextColor(colors[STATE_DONE]);
                viewHolder.state.setText("用户终止");
                break;
            case 3:
                viewHolder.state.setTextColor(colors[STATE_DONE]);
                viewHolder.state.setText("管理员终止");
                break;
            case 4:
                viewHolder.state.setTextColor(colors[STATE_DONE]);
                viewHolder.state.setText("系统终止");
                break;
        }
    }

    private void setBetData(ViewHolder viewHolder, Bet bet) {
        viewHolder.money.setText(bet.getAmount() + "元");
        viewHolder.time.setText((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(bet.getBoughtAt()));
//        Lottery lottery = userCentre.getLottery(bet.getLotteryId());
//        if (lottery != null) {
            viewHolder.name.setText(bet.getLotteryName());
//        } else {
//            viewHolder.name.setText("未知彩种");
//        }
        viewHolder.gamename.setVisibility(View.VISIBLE);
        viewHolder.gamename.setText(bet.getWay());
        switch (Integer.parseInt(bet.getStatus())) {
            case 0:
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText(states.get(Integer.parseInt(bet.getStatus())));
                break;
            case 1:
                viewHolder.state.setTextColor(colors[STATE_FAIL]);
                viewHolder.state.setText("已撤销");
                break;
            case 2:
                viewHolder.state.setTextColor(colors[STATE_FAIL]);
                viewHolder.state.setText("未中奖");
                break;
            case 3:
                viewHolder.state.setTextColor(colors[STATE_WIN]);
                viewHolder.state.setText("已中奖");
                break;
            case 4:
                viewHolder.state.setTextColor(colors[STATE_FAIL]);
                viewHolder.state.setText("已派奖");
                break;
        }
    }

    static class ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.game_name) TextView gamename;
        @BindView(R.id.money) TextView money;
        @BindView(R.id.time) TextView time;
        @BindView(R.id.state) TextView state;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
