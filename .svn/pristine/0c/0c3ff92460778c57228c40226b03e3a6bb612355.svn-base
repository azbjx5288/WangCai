package com.wangcai.lottery.view.adapter;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.component.CustomDialogReverse;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.fragment.GameGaFragment;
import com.wangcai.lottery.material.ConstantInformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2017/07/13.
 *
 * @author ACE Ga彩种大厅 Adapter
 */

public class GaRecyclerViewAdapter extends RecyclerView.Adapter<GaRecyclerViewAdapter.ViewHolder> {

    private List<Lottery> items;
    private final BaseFragment fragment;

    public GaRecyclerViewAdapter(BaseFragment fragment, List<Lottery> items) {
        this.fragment = fragment;
        this.items = items;
    }

    public void setUpdataView(List<Lottery> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lotto_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (items != null && items.size() > 0) {
            Lottery lottery = items.get(position);
            holder.logo.setImageResource(ConstantInformation.getLotteryLogo(lottery.getIdentifier(), lottery.getStatus() == 3));
            holder.name.setText(lottery.getName());
        } else {
            holder.logo.setImageResource(R.drawable.jia);
            holder.name.setText("");
        }

        holder.mView.setOnClickListener((View v) -> {
            if (ConstantInformation.isFastClick()) {
                if (items.size() > 0) {
                    Lottery lottery = items.get(holder.getAdapterPosition());
                    if (lottery.getStatus() == 3) {
                        CustomDialogReverse.Builder builder = new CustomDialogReverse.Builder(fragment.getActivity());
                        builder.setTitle("温馨提示!");
                        builder.setMessage("您可以选择：");
                        builder.setPositiveButton("免费试玩", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GameGaFragment.launch(fragment, lottery, true);
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton("开始游戏", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GameGaFragment.launch(fragment, lottery, false);
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT_LEVEL);
                        builder.create().show();
                        //GameGaFragment.launch(fragment, lottery);
                    } else {
                        fragment.tipDialog("正在测试……");
                    }
                } else {
                    fragment.tipDialog("数据正在加载请稍等");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
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
