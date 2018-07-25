package com.wangcai.lottery.view.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.Withdraws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/3/18.
 */
public class WithdrawalAdapter extends BaseAdapter {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private List withdrawList;

    public WithdrawalAdapter(List withdrawList) {
        this.withdrawList = withdrawList;
    }

    public void setData(List withdrawList) {
        this.withdrawList = withdrawList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return withdrawList != null ? withdrawList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (withdrawList == null) {
            return null;
        }
        if (position >= 0 && position < withdrawList.size()) {
            return withdrawList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Withdraws withdrawsNotes = (Withdraws) withdrawList.get(position);
        viewHolder.serialNumber.setText(withdrawsNotes.getWrapId());
        viewHolder.takeAmount.setText(String.format("%.2f", withdrawsNotes.getAmount()));
        String[] notes=status(withdrawsNotes);
        viewHolder.takeTime.setText(notes[0]);
        viewHolder.takeStatus.setText(Html.fromHtml(notes[1]));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.serial_number)
        TextView serialNumber;
        @BindView(R.id.take_amount)
        TextView takeAmount;
        @BindView(R.id.take_time)
        TextView takeTime;
        @BindView(R.id.take_status)
        TextView takeStatus;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /*0未处理 1已受理 2已审核 3交给机器受理 4机器正在受理 8已成功 9因故取消',*/
    private String[] status(Withdraws notes) {
        String[] statustext=new String[2];
        int status=notes.getStatus();
        if(notes.getStatus()==0) {
            statustext[0] = notes.getCreateTime().substring(0, notes.getCreateTime().indexOf(" "));
            statustext[1] = "<font color=\"#AE0000\">未处理</font>";
        }else if(status==1) {
            statustext[0] = notes.getVerifyTime().substring(0, notes.getVerifyTime().indexOf(" "));
            statustext[1] = "<font color=\"#AE0000\">已受理</font>";
        }else if(status==2) {
            statustext[0] = notes.getVerifyTime().substring(0, notes.getVerifyTime().indexOf(" "));
            statustext[1] = "<font color=\"#AE0000\">已审核</font>";
        }else if(status==3) {
            statustext[0] = notes.getVerifyTime().substring(0, notes.getVerifyTime().indexOf(" "));
            statustext[1] = "<font color=\"#AE0000\">已受理</font>";
        }else if(status==4) {
            statustext[0] = notes.getVerifyTime().substring(0, notes.getVerifyTime().indexOf(" "));
            statustext[1] = "<font color=\"#AE0000\">受理中</font>";
        }else if(status==8) {
            statustext[0] = notes.getFinishTime().substring(0, notes.getFinishTime().indexOf(" "));
            statustext[1] = "<font color=\"#adadad\">已成功</font>";
        }else if(status==9) {
            statustext[1] = "<font color=\"#AE0000\">因故取消</font>";
        }
        return statustext;
    }
}
