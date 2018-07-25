package com.wangcai.lottery.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.fragment.GameStickNavFragment;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2017/07/11.
 * @author ACE
 * 乐透彩种大厅 Adapter
 */

public class LottoRecyclerViewAdapter extends RecyclerView.Adapter<LottoRecyclerViewAdapter.ViewHolder>{

    private List<Lottery> items;
    private final BaseFragment fragment;
    private RecordType chooserLottery;

    public LottoRecyclerViewAdapter(BaseFragment fragment,RecordType recordType, List<Lottery> items) {
        this.fragment = fragment;
        this.items = items;
        this.chooserLottery=recordType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lotto_item, parent, false);
        return new ViewHolder(view);
    }

    public void setUpdataView(List<Lottery> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private ViewHolder holders;

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        this.holders = holder;
        if (items != null && items.size() > 0) {
            Lottery lottery = items.get(position);
            holders.logo.setImageResource(ConstantInformation.getLotteryLogo(lottery.getIdentifier(), lottery.getStatus() == 3));
            holders.name.setText(lottery.getName());
        } else {
            holders.logo.setImageResource(R.drawable.jia);
            holders.name.setText("");
        }
        holders.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConstantInformation.isFastClick()) {
                    if (items.size() > 0) {
                        Lottery lottery = items.get(position);
                        if (lottery.getStatus() == 3) {
                            addPreference(lottery);
                            GameStickNavFragment.launch(fragment, lottery);
                        } else {
                            fragment.tipDialog("正在测试……");
                        }
                    } else {
                        fragment.tipDialog("数据正在加载请稍等");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void addPreference(Lottery lottery) {
        chooserLottery.addChoosedLottery(lottery);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        @BindView(R.id.home_lottery_ico)
        ImageView logo;
        @BindView(R.id.recentlyplayed_name)
        TextView name;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            view.setTag(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
