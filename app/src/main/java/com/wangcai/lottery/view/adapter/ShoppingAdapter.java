package com.wangcai.lottery.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.game.Delimiter;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.ShoppingCart;
import com.wangcai.lottery.material.Ticket;
import com.wangcai.lottery.util.NumbericUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingAdapter extends BaseAdapter {

    private List<Ticket> ticketsList;
    private OnDeleteItemClickListener onDeleteClickListener;

    public ShoppingAdapter() {
        ticketsList = ShoppingCart.getInstance().getCodesMap();
    }

    public void setOnDeleteClickListener(OnDeleteItemClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getCount() {
        return ticketsList != null ? ticketsList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_fragment_reveal, parent,
                    false);
            holder = new ViewHolder(convertView);
            holder.delButton.setOnClickListener(onClickListener);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = ticketsList.get(position);
        String methodName = "";
        if (ticket.getMethodType() != null) {
            methodName = ticket.getMethodType().getNameCn() + "" + ticket.getChooseMethod().getNameCn();
        } else {
            methodName = ticket.getChooseMethod().getNameCn();
        }
        holder.playName.setText(methodName); //玩法名
        holder.noteNumber.setText(String.format("%d 注", ticket.getChooseNotes())); //注数
        switch (methodName) {
            case "趣味型定单双":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DING_DAN_SHUANG_MAP, true, true));
                break;
            case "冠亚和值大小单双":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DXDS_MAP, false, true));
                break;
            case "大小单双后三大小单双":
            case "大小单双前二大小单双":
            case "大小单双前三大小单双":
            case "大小单双五星和值大小单双":
            case "大小单双后二大小单双":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DXDS_MAP, false, true));
                break;
            case "龙虎龙虎":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_TIGER_MAP, false, true));
                break;
            case "龙虎和万千":
            case "龙虎和万百":
            case "龙虎和万十":
            case "龙虎和万个":
            case "龙虎和千百":
            case "龙虎和千十":
            case "龙虎和千个":
            case "龙虎和百十":
            case "龙虎和百个":
            case "龙虎和十个":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_TIGER_SUN_MAP, false, true));
                break;
            case "大小大小"://排列5
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_DXDX_MAP, false, true));
                break;
            case "单双单双"://排列5
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_DSDS_MAP, false, true));
                break;
            case "颜色全红"://排列5
                holder.code.setText("全红");
                break;
            case "颜色全黑"://排列5
                holder.code.setText("全黑");
                break;
            case "三连号三连号通选"://排列5
            case "三同号三同号通选"://排列5
                holder.code.setText("通选");
                break;
            case "二同号二同号复选"://排列5
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_ETHTX_MAP, false, true));
                break;
            case "三同号三同号单选"://排列5
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_STHSTHDX_MAP, false, true));
                break;
            case "趣味五码趣味三星":
                holder.code.setText(quweiGame(2, ticket.getCodes()));
                break;
            case "趣味四码趣味三星":
            case "趣味后三趣味二星":
            case "趣味前三趣味二星":
                holder.code.setText(quweiGame(1, ticket.getCodes()));
                break;
            case "区间五码区间三星":
                holder.code.setText(qujianGame(2, ticket.getCodes()));
                break;
            case "区间四码区间三星":
            case "区间后三区间二星":
            case "区间前三区间二星":
                holder.code.setText(qujianGame(1, ticket.getCodes()));
                break;
            case "其它特殊号码":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.SPECIAL_NUB_MAP, false, false));
                break;
            case "上中下上中下":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.KL8_SZX_MAP, false, true));
                break;
            case "奇偶和奇偶和":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.HZ_JIOUHE_MAP, false, true));
                break;
            case "和值五行":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.HZ_WX_MAP, false, true));
                break;
            case "和值大小810":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DAXIAO810_DX_MAP, false, true));
                break;
            case "和值单双":
                holder.code.setText(NumbericUtils.replaceByMap(ticket.getCodes(), ConstantInformation.DRAGON_DSDS_MAP, false, true));
                break;
            default:
                holder.code.setText(ticket.getCodes()); //Code
        }

        holder.delButton.setTag(position);

        return convertView;
    }

    private String quweiGame(int digit, String codes) {
        String[] str = codes.split(Delimiter.fromCode(Delimiter.DEELIMITER_LINE_INDEX).getSymbol());
        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = str.length; i < length; i++) {
            if (i < digit) {
                builder.append(NumbericUtils.replaceByMap(str[i], ConstantInformation.DRAGON_DXDX_MAP, false, true));
            } else {
                builder.append(str[i]);
            }
            if (i != 0 && i < length - 1) {
                builder.append(Delimiter.fromCode(Delimiter.VERTICAL_LINE_INDEX).getSymbol());
            }
        }
        return builder.toString();
    }

    private String qujianGame(int digit, String codes) {
        String[] str = codes.split(Delimiter.fromCode(Delimiter.DEELIMITER_LINE_INDEX).getSymbol());
        StringBuilder builder = new StringBuilder();
        for (int i = 0, length = str.length; i < length; i++) {
            if (i < digit) {
                builder.append(NumbericUtils.replaceByMap(str[i], ConstantInformation.INTERVAL_TEXT_MAP, false, true));
            } else {
                builder.append(str[i]);
            }
            if (i < length - 1) {
                builder.append(Delimiter.fromCode(Delimiter.VERTICAL_LINE_INDEX).getSymbol());
            }
        }
        return builder.toString();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShoppingCart.getInstance().deleteCode((Integer) v.getTag());
            notifyDataSetChanged();
            onDeleteClickListener.onDeleteItemClick();
        }
    };

    static class ViewHolder {
        @BindView(R.id.shopping_item_method)
        TextView playName;
        @BindView(R.id.shopping_item_code)
        TextView code;
        @BindView(R.id.shopping_item_notenum)
        TextView noteNumber;
        @BindView(R.id.shopping_item_delete)
        ImageButton delButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnDeleteItemClickListener {
        void onDeleteItemClick();
    }
}