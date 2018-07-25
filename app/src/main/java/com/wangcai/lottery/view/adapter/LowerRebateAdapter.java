package com.wangcai.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.NormalRebateOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/5/4.
 */
public class LowerRebateAdapter extends BaseAdapter {
    private List rebateList;
    private OnItemClickListener onChooseItemListener;
    public LowerRebateAdapter(List rebateList) {
        this.rebateList = rebateList;
    }
    public void setData(List rebateList) {
        this.rebateList = rebateList;
        notifyDataSetChanged();
    }

    public OnItemClickListener getOnChooseItemListener() {
        return onChooseItemListener;
    }

    public void setOnChooseItemListener(OnItemClickListener onChooseItemListener) {
        this.onChooseItemListener = onChooseItemListener;
    }

    @Override
    public int getCount() {
        return rebateList != null ? rebateList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (rebateList == null) {
            return null;
        }
        if (position >= 0 && position < rebateList.size()) {
            return rebateList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lower_rebate_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NormalRebateOptions rebateOptions=(NormalRebateOptions)rebateList.get(position);
        List<String> datalist = new ArrayList<>();
        for (int i=0,size=rebateOptions.getOptions().size();i<size;i++){
            double rebate=rebateOptions.getOptions().get(i).getRebate()*100;
            datalist.add(String.format("%.1f%%",rebate));
        }

        viewHolder.lotteryName.setText(rebateOptions.getPropertyName()+"：");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item, datalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(spinnerAdapter);
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                onChooseItemListener.onChooseItemClick(datalist.get(p),position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.lower_spinner)
        Spinner spinner;
        @BindView(R.id.rebates_lottery_name)
        TextView lotteryName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnItemClickListener{
        void onChooseItemClick(String rebate, int position);
    }
}
