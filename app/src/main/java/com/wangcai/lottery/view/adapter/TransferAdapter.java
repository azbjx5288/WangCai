package com.wangcai.lottery.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wangcai.lottery.R;
import com.wangcai.lottery.data.PlatForm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2017/6/5.
 */

public class TransferAdapter extends BaseAdapter {
    private static final String TAG = TransferAdapter.class.getSimpleName();

    private int currentItem = -1;
    private boolean dupo = false;
    private Context context;
    private List<PlatForm> datalist;
    private OnTransferClickListener onTransferClickListener;

    public TransferAdapter(Context context, List<PlatForm> datalist,int lowerMemberUserId) {
        this.context = context;
        this.datalist = datalist;
        this.currentItem=lowerMemberUserId;
    }
    public TransferAdapter(Context context) {
        this.context = context;
    }

    public void setOnIssueNoClickListener(OnTransferClickListener onTransferClickListener) {
        this.onTransferClickListener = onTransferClickListener;
    }

    public void setData(List datalist,int lowerMemberUserId) {
        this.datalist = datalist;
        this.currentItem=lowerMemberUserId;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datalist != null || datalist.size() >= 0 ? datalist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spiner_item_layout, null);
            holderView = new ViewHolder(convertView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        // 记录的点击位置来设置"对应Item"的可见性（在list依次加载列表数据时，每加载一个时都看一下是不是需改变可见性的那一条）
        PlatForm platform = datalist.get(position);
        if (currentItem == platform.getId()) {
            holderView.layout.setBackgroundResource(R.color.app_main);//Color.parseColor("#16A085")
            holderView.textView.setTextColor(Color.parseColor("#ffffffff"));
        } else {
            holderView.layout.setBackgroundResource(R.color.white);//Color.parseColor("#e2e2e2")
            holderView.textView.setTextColor(Color.parseColor("#808080"));
        }
        holderView.textView.setTag(position);
        holderView.textView.setText(platform.getName() + "");
        holderView.textView.setPadding(0, 15, 0, 15);

        holderView.textView.setOnClickListener((View view) -> {
            // 用 currentItem 记录点击位置
            if (onTransferClickListener != null) {
                onTransferClickListener.onTransferListener(datalist.get((Integer) view.getTag()),position);
            }
            notifyDataSetChanged(); // 必须有的一步
        });

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.textView)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnTransferClickListener {
        void onTransferListener(PlatForm platform,int position);
    }
}