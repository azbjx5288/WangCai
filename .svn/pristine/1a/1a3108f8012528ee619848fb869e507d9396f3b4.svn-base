package com.wangcai.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.RechargeChannelConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/11/15.
 */

public class RechargeAdapter extends BaseAdapter {
    private List<RechargeChannelConfig> cardList;
    private OnRechargeMethodClickListener onRechargeMethodClickListener;

    public RechargeAdapter(List<RechargeChannelConfig> cardList) {
        this.cardList = cardList;
    }

    public void setData(List<RechargeChannelConfig> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    public void setOnRechargeMethodClickListener(OnRechargeMethodClickListener onRechargeMethodClickListener) {
        this.onRechargeMethodClickListener = onRechargeMethodClickListener;
    }

    @Override
    public int getCount() {
        return cardList == null ? 0 : cardList.size();
    }

    @Override
    public RechargeChannelConfig getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_pick, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RechargeChannelConfig item = getItem(position);
        viewHolder.rechargeName.setText(item.getName());

       /* Drawable img_off = parent.getContext().getResources().getDrawable(ConstantInformation.getRechargeLogo(item.getId()));
        img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
        viewHolder.rechargeName.setCompoundDrawables(img_off, null, null, null); //设置左图标*/

        //根据Item位置分配不同背景
    /*    if (cardList.size() > 0) {
            if (cardList.size() == 1) {
                viewHolder.background.setBackgroundResource(R.drawable.settings_item);
            } else {
                if (position == 0) {
                    viewHolder.background.setBackgroundResource(R.drawable.settings_itemtop_press);
                } else if (position == cardList.size() - 1) {
                    viewHolder.background.setBackgroundResource(R.drawable.settings_itembottom_press);
                } else {
                    viewHolder.background.setBackgroundResource(R.drawable.settings_itemmiddle_press);
                }
            }
        }*/
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        viewHolder.rechargeSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WangCaiApp.getUserCentre().setRechargeMode(item.getId());
                notifyDataSetChanged();
            }
        });

        viewHolder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重置，确保最多只有一项被选中
                WangCaiApp.getUserCentre().setRechargeMode(item.getId());
                notifyDataSetChanged();
            }
        });

        int bankid = WangCaiApp.getUserCentre().getRechargeMode();
        if (bankid > 0 && bankid == item.getId()) {
            viewHolder.rechargeSelect.setChecked(true);
            if (onRechargeMethodClickListener != null) {
                onRechargeMethodClickListener.onRechargeMethodListener(item, position);
            }
        } else {
            if (bankid == 0 && position == 0) {
                viewHolder.rechargeSelect.setChecked(true);
                if (onRechargeMethodClickListener != null) {
                    onRechargeMethodClickListener.onRechargeMethodListener(item, position);
                }
            } else {
                viewHolder.rechargeSelect.setChecked(false);
            }
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.choose_recharge)
        RelativeLayout background;
        @BindView(R.id.recharge_select)
        RadioButton rechargeSelect;
        @BindView(R.id.recharge_name)
        TextView rechargeName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnRechargeMethodClickListener {
        void onRechargeMethodListener(RechargeChannelConfig card, int position);
    }
}
