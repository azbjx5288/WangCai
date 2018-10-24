package com.wangcai.lottery.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.ReceiveBoxResponse;
import com.wangcai.lottery.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/11/20.
 * 收件箱   Adapter
 */

public class InBoxAdapter extends BaseAdapter {

    private List<ReceiveBoxResponse> list;
    private boolean mStateIsEdit=false;
    private ArrayList<String> mUnreadPositionList;
    private  String MAIL="mail";

    public InBoxAdapter(boolean stateIsEdit) {
        mStateIsEdit=stateIsEdit;
    }

    public void setList(List list,ArrayList<String> unreadPositionList) {
        this.list = list;
        this.mUnreadPositionList=unreadPositionList;
        notifyDataSetChanged();
    }

    public void setmStateIsEdit(boolean mStateIsEdit) {
        this.mStateIsEdit = mStateIsEdit;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReceiveBoxResponse bean = list.get(position);

        //是否已读
        /*Object  tag=holder.time.getTag();
        if( SharedPreferencesUtils.getBoolean(parent.getContext(), ConstantInformation.APP_INFO,MAIL)) { //解除屏蔽 就是 可以 显示  小红点 提示
            if (tag == null) {
                QBadgeView qBadgeView=new QBadgeView(parent.getContext());//
                qBadgeView.bindTarget(holder.overlay_badge);
                qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
                if("0".equals(bean.getHas_read())&&!mUnreadPositionList.contains(bean.getMt_id())) {
                    qBadgeView.setBadgeNumber(1);////1:已读,0:未读
                    refreshTextColor(holder,parent.getContext(),false);
                }else {
                    qBadgeView.setBadgeNumber(0);
                    refreshTextColor(holder,parent.getContext(),true);
                }

                holder.time.setTag(qBadgeView);
            }else{
                QBadgeView qQBadgeView=(QBadgeView)tag;
                if("0".equals(bean.getHas_read())&&!mUnreadPositionList.contains(bean.getMt_id())) {
                    qQBadgeView.setBadgeNumber(1);////1:已读,0:未读
                    refreshTextColor(holder,parent.getContext(),false);
                }else {
                    qQBadgeView.setBadgeNumber(0);
                    refreshTextColor(holder,parent.getContext(),true);
                }
            }

        }else{//屏蔽
            if(tag!=null){
                QBadgeView qQBadgeView=(QBadgeView)tag;
                qQBadgeView.setBadgeNumber(0);
            }
        }*/
        if(0==bean.getIs_readed()&&!mUnreadPositionList.contains(String.valueOf(bean.getId()))) {
            refreshTextColor(holder,parent.getContext(),false);
        }else {
            refreshTextColor(holder,parent.getContext(),true);
        }

        holder.from_username.setText(bean.getSender());
        holder.content.setText(bean.getMsg_title());
        holder.time.setText(bean.getCreated_at());

        if(mStateIsEdit){
            holder.check_box.setVisibility(View.VISIBLE);
            if(bean.isState()){
                holder.check_box.setChecked(true);
            }else{
                holder.check_box.setChecked(false);
            }
        }else{
            holder.check_box.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void refreshTextColor(ViewHolder holder, Context context , boolean isRead) {
        if(isRead){
            holder.from_username.setTextColor(UiUtils.getColor(context,R.color.gray));
            holder.content.setTextColor(UiUtils.getColor(context,R.color.gray));
            holder.time.setTextColor(UiUtils.getColor(context,R.color.gray));
        }else{
            holder.from_username.setTextColor(UiUtils.getColor(context,R.color.contents_text));
            holder.content.setTextColor(UiUtils.getColor(context,R.color.contents_text));
            holder.time.setTextColor(UiUtils.getColor(context,R.color.contents_text));
        }
    }


    static class ViewHolder {
        @BindView(R.id.from_username)
        TextView from_username;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.check_box)
        CheckBox check_box;
    /*    @BindView(R.id.overlay_badge)
        TextView overlay_badge;*/

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }
}
