package com.wangcai.lottery.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.LinkUserBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2018/7/11.
 */

public class LinkListAdapter extends RecyclerView.Adapter<LinkListAdapter.ViewHolder> implements View.OnClickListener
{
    private ArrayList<LinkUserBean> data;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private OnDetailClickListner onDetailClickListner;
    private OnDeleteClickListner onDeleteClickListner;
    
    public void setData(ArrayList<LinkUserBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_list, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if (data != null && data.size() > 0)
        {
            LinkUserBean linkUserBean = data.get(position);
            holder.channel.setText(linkUserBean.getChannel());
            holder.userAmount.setText(String.valueOf(linkUserBean.getCreat_count()));
            holder.date.setText(simpleDateFormat.format(linkUserBean.getCreated_at()));
            
            holder.detail.setTag(data.get(position));
            holder.delete.setTag(data.get(position));
        }
    }
    
    @Override
    public int getItemCount()
    {
        return data != null ? data.size() : 0;
    }
    
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.detail:
                if (onDetailClickListner != null)
                    onDetailClickListner.onDetailClick(view, (LinkUserBean) view.getTag());
                break;
            case R.id.delete:
                if (onDeleteClickListner != null)
                    onDeleteClickListner.onDeleteClick(view, (LinkUserBean) view.getTag());
                break;
        }
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.channel)
        TextView channel;
        @BindView(R.id.user_amount)
        TextView userAmount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.delete)
        ImageView delete;
        
        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
            
            detail.setOnClickListener(LinkListAdapter.this::onClick);
            delete.setOnClickListener(LinkListAdapter.this::onClick);
        }
    }
    
    public interface OnDetailClickListner
    {
        void onDetailClick(View view, LinkUserBean curData);
    }
    
    public interface OnDeleteClickListner
    {
        void onDeleteClick(View view, LinkUserBean curData);
    }
    
    public void setOnDetailClickListner(OnDetailClickListner onDetailClickListner)
    {
        this.onDetailClickListner = onDetailClickListner;
    }
    
    public void setOnDeleteClickListner(OnDeleteClickListner onDeleteClickListner)
    {
        this.onDeleteClickListner = onDeleteClickListner;
    }
}
