package com.wangcai.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.LowerMember;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/5/3.
 */
public class LowerMemberAdapter extends BaseAdapter {
    private List lowerMemberList;
    public LowerMemberAdapter(List lowerMemberList) {
        this.lowerMemberList = lowerMemberList;
    }

    public void setData(List lowerMemberList) {
        this.lowerMemberList = lowerMemberList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lowerMemberList != null ? lowerMemberList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (lowerMemberList == null) {
            return null;
        }
        if (position >= 0 && position < lowerMemberList.size()) {
            return lowerMemberList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lower_member_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LowerMember lowerMembers=(LowerMember)lowerMemberList.get(position);
        viewHolder.lowerUsername.setText("用户名："+lowerMembers.getUsername());
        viewHolder.lowerTypes.setText("类型："+getLevel(lowerMembers.getLevel()));
        viewHolder.enroltime.setText("注册时间："+lowerMembers.getRegTime());
        viewHolder.lowerStatus.setText("状态："+getStatus(lowerMembers.getStatus()));
        viewHolder.lowerMoney.setText(String.format("余额:\t%.2f\t 元",lowerMembers.getBalance()));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.lower_username)
        TextView lowerUsername;
        @BindView(R.id.lower_types)
        TextView lowerTypes;
        @BindView(R.id.enroltime)
        TextView enroltime;
        @BindView(R.id.lower_status)
        TextView lowerStatus;
        @BindView(R.id.lower_money)
        TextView lowerMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
    //    level  0=总代；1=一代；2=二代；3=三代；4=四代；5=五代；6=六代；10=普通用户
    private String getLevel(int index){
        String name="";
        switch (index){
            case 0:
                name="总代";
                break;
            case 1:
                name="一代";
                break;
            case 2:
                name="二代";
                break;
            case 3:
                name="三代";
                break;
            case 4:
                name="四代";
                break;
            case 5:
                name="五代";
                break;
            case 6:
                name="六代";
                break;
            case 10:
                name="普通用户";
        }
        return name;
    }


    //0=已删除；1=冻结；5=已回收；8=正常

    private String getStatus(int index){
        String name="";
        switch (index){
            case 0:
                name="已删除";
                break;
            case 1:
                name="冻结";
                break;
            case 5:
                name="已回收";
                break;
            case 8:
                name="正常";
                break;
        }
        return name;
    }
}
