package com.wangcai.lottery.view.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.QuotaBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuotaAdapter2 extends BaseAdapter
{
    private ArrayList<QuotaBean> data;

    public void setData(ArrayList<QuotaBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (data == null)
            return null;
        if (position >= 0 && position < data.size())
            return data.get(position);
        return null;
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quota2, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.quota.setText(String.valueOf(data.get(position).getQuota()));
        viewHolder.result.setText(data.get(position).getResult()==null?"0":data.get(position).getResult());

        return convertView;
    }
    
    class ViewHolder
    {
        @BindView(R.id.quota)
        TextView quota;
        @BindView(R.id.result)
        EditText result;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
            //result.setTag(position);
        }
    }
    
}
