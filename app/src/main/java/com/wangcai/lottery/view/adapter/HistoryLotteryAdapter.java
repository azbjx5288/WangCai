package com.wangcai.lottery.view.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.component.WarpLinearLayout;
import com.wangcai.lottery.data.LotteriesHistory;
import com.wangcai.lottery.data.LotteryCode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2016/01/19.
 *
 * @author ACE
 * @功能描述: 历史开奖适配器
 */
public class HistoryLotteryAdapter extends BaseAdapter {
    private static final String TAG = HistoryLotteryAdapter.class.getSimpleName();
    private List<LotteriesHistory> dataSourceArray;
    private OnItemClickListener clickListener;

    public HistoryLotteryAdapter() {
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return dataSourceArray != null ? dataSourceArray.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refresh(List<LotteriesHistory> dataSourceArray) {
        this.dataSourceArray = dataSourceArray;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lottery_trend_item, parent, false);
            holder = new ViewHolder(convertView);
            holder.historyBet.setOnClickListener(onClickListener);
            holder.otherMore.setOnClickListener(onClickListener);
            holder.otherList.setOnClickListener(onClickListener);
            holder.trendIcon.setOnClickListener(onClickListener);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LotteriesHistory historyList = dataSourceArray.get(position);
        holder.title.setText(historyList.getLottery());
        List<LotteryCode> data = historyList.getIssues();
        holder.historyBet.setTag(position);
        holder.otherMore.setTag(position);
        holder.otherList.setTag(position);
        holder.trendIcon.setTag(position);

        if (data.size() > 0) {
            LotteryCode history = data.get(0);
            holder.issue.setText(history.getIssue());

            for (int i = 0, size = (data.size() > 3 ? 3 : data.size()); i < holder.otherLayout.length; i++) {
                if (i + 1 < size) {
                    LotteryCode historyCode = data.get(i + 1);
                    holder.otherCode[i].setText(historyCode.getWnNumber());
                    holder.otherIssue[i].setText(historyCode.getIssue());
                    holder.otherLayout[i].setVisibility(View.VISIBLE);
                } else {
                    holder.otherLayout[i].setVisibility(View.GONE);
                }
            }
            if (holder.otherLayout.length <= 1) {
                holder.divideline.setVisibility(View.GONE);
                holder.otherList.setVisibility(View.GONE);
            }

            if (history.getWnNumber().length() == 3 && historyList.getLottery().contains("快三")) {
                //快三的
                setKuaisanUi(holder, historyList);
                return convertView;
            } else {
                String digit = history.getWnNumber();
                holder.ballList.setVisibility(View.VISIBLE);
                holder.kuaisanLayout.setVisibility(View.GONE);
                if (digit.contains(" ")) {
                    String[] item = digit.split(" ");
                    if (historyList.getLottery().contains("台湾宾果") || historyList.getLottery().contains("快乐8")) {
                        setKuaiLe8(item, holder);
                    } else {
                        setBallText(item, holder);
                    }
                } else {
                    CharSequence[] item = new CharSequence[digit.length()];
                    for (int i = 0; i < item.length; i++) {
                        item[i] = String.valueOf(digit.charAt(i));
                    }
                    setBallText(item, holder);
                }

                for (View view : holder.kuaisanItemLayout) {
                    view.setVisibility(View.GONE);
                }
                return convertView;
            }
        } else {
            setNullData(historyList.getLottery(), holder);
            return convertView;
        }
    }

    private void setKuaiLe8(CharSequence[] item, ViewHolder holder) {
        holder.ballList.setVisibility(View.GONE);
        holder.kuaisanLayout.setVisibility(View.GONE);
        holder.kuaile8LayoutList.setVisibility(View.VISIBLE);

        for (int i = 0; i < holder.kuaile8Texts.length; i++) {
            holder.kuaile8Texts[i].setVisibility(View.VISIBLE);
            holder.kuaile8Texts[i].setText(item[i]);
        }
    }

    private void setKuaisanUi(ViewHolder holder, LotteriesHistory historyList) {
        String digit = historyList.getIssues().get(0).getWnNumber();
        holder.ballList.setVisibility(View.GONE);
        holder.kuaisanLayout.setVisibility(View.VISIBLE);
        holder.kuaile8LayoutList.setVisibility(View.GONE);
        if (digit == null || "".equals(digit)) {
            return;
        }
        int code0 = digit.charAt(0) - 48;
        int code1 = digit.charAt(1) - 48;
        int code2 = digit.charAt(2) - 48;
        int sum = code0 + code1 + code2;
        setDice(holder.kuaisanDice1, code0);
        setDice(holder.kuaisanDice2, code1);
        setDice(holder.kuaisanDice3, code2);
        holder.kuaisanSum.setText(String.valueOf(sum));
        holder.kuaisanSize.setText(sum > 10 ? "大" : "小");
        holder.kuaisanOdd.setText(sum % 2 == 0 ? "双" : "单");

        List<LotteryCode> data = historyList.getIssues();
        for (int i = 0, size = (data.size() > 3 ? 3 : data.size()); i < holder.otherLayout.length; i++) {
            if (i + 1 < size) {
                LotteryCode history = data.get(i + 1);
                String win = history.getWnNumber();
                sum = win.charAt(0) - 48 + win.charAt(1) - 48 + win.charAt(2) - 48;
                holder.kuaisanSums[i].setText(String.valueOf(sum));
                holder.kuaisanSizes[i].setText(sum > 10 ? "大" : "小");
                holder.kuaisanOdds[i].setText(sum % 2 == 0 ? "双" : "单");
                holder.kuaisanItemLayout[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private int[] kuaisanDice = {R.drawable.kuaisan_dice_1, R.drawable.kuaisan_dice_2, R.drawable.kuaisan_dice_3,
            R.drawable.kuaisan_dice_4, R.drawable.kuaisan_dice_5, R.drawable.kuaisan_dice_6};

    private void setDice(ImageView imageView, int index) {
        imageView.setImageResource(kuaisanDice[index - 1]);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                int position = (int) v.getTag();
                if (clickListener != null) {
                    clickListener.onItemClick(position, dataSourceArray.get(position).getLottery(), v.getId() == R.id.lottery_history_bet);
                }
            }
        }
    };

    private void setBallText(CharSequence[] item, ViewHolder holder) {
        holder.ballList.setVisibility(View.VISIBLE);
        holder.kuaile8LayoutList.setVisibility(View.GONE);
        holder.kuaisanLayout.setVisibility(View.GONE);
        for (int i = 0; i < holder.ballTexts.length; i++) {
            if (i < item.length) {
                holder.ballTexts[i].setVisibility(View.VISIBLE);
                holder.ballTexts[i].setText(item[i]);
            } else {
                holder.ballTexts[i].setText("x");
                holder.ballTexts[i].setVisibility(View.GONE);
            }
        }
    }

    private void setNullData(String name, ViewHolder holder) {
        holder.issue.setText("第 xxxxxxx 期");
        if (name.contains("快三")) {
            holder.ballList.setVisibility(View.GONE);
            holder.kuaile8LayoutList.setVisibility(View.GONE);
            holder.kuaisanLayout.setVisibility(View.VISIBLE);
            holder.kuaisanSum.setText("x");
            holder.kuaisanSize.setText("x");
            holder.kuaisanOdd.setText("x");
        } else {
            CharSequence[] item = setDefault(name);
            setBallText(item, holder);
            holder.ballList.setVisibility(View.VISIBLE);
            holder.kuaile8LayoutList.setVisibility(View.GONE);
            holder.kuaisanLayout.setVisibility(View.GONE);
            for (int i = 0; i < holder.otherLayout.length; i++) {
                holder.otherCode[i].setText("xxxxxxx");
                holder.otherIssue[i].setText("xxxxx-xxxx");
            }
            for (View view : holder.kuaisanItemLayout) {
                view.setVisibility(View.GONE);
            }
        }
    }

    private CharSequence[] setDefault(String name) {
        int digit = 0;
        if (name.contains("快三") || name.contains("3D")) {
            digit = 3;
        } else if (name.contains("时时彩") || name.contains("11选5") || name.contains("排列三/五") || name.contains("分分彩") || name.contains("三分彩") || name.contains("五分彩") || name.contains("百秒彩")) {
            digit = 5;
        } else if (name.contains("PK10")) {
            digit = 10;
        } else if (name.contains("快乐8")) {
            digit = 20;
        }
        CharSequence[] item = new CharSequence[digit];
        for (int i = 0; i < item.length; i++) {
            item[i] = String.valueOf("x");
        }
        return item;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String cname, boolean flag);
    }

    // 依据item的layout
    static class ViewHolder {
        @BindView(R.id.lottery_history_title)
        TextView title;
        @BindView(R.id.lottery_history_issue)
        TextView issue;
        //时时彩 十一选五 3D
        @BindView(R.id.lottery_history_code)
        LinearLayout ballList;
        @BindView(R.id.divideline)
        View divideline;
        //其它开奖号码
        @BindView(R.id.lottery_trend_other_list)
        LinearLayout otherList;
        @BindView(R.id.lottery_other_more)
        View otherMore;
        @BindView(R.id.lottery_history_bet)
        TextView historyBet;
        @BindView(R.id.trend_icon)
        ImageView trendIcon;

        //快8
        @BindView(R.id.kuaile8)
        WarpLinearLayout kuaile8LayoutList;

        //快三的
        @BindView(R.id.kuaisanLayout)
        LinearLayout kuaisanLayout;
        @BindView(R.id.kuaisanDice1)
        ImageView kuaisanDice1;
        @BindView(R.id.kuaisanDice2)
        ImageView kuaisanDice2;
        @BindView(R.id.kuaisanDice3)
        ImageView kuaisanDice3;
        @BindView(R.id.kuaisanSum)
        TextView kuaisanSum;
        @BindView(R.id.kuaisanSize)
        TextView kuaisanSize;
        @BindView(R.id.kuaisanOdd)
        TextView kuaisanOdd;
        View[] kuaisanItemLayout;
        TextView[] kuaisanSums;
        TextView[] kuaisanSizes;
        TextView[] kuaisanOdds;
        //快三的 end

        TextView[] ballTexts;
        LinearLayout[] otherLayout;
        TextView[] otherIssue;
        TextView[] otherCode;
        TextView[] kuaile8Texts;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
            ballTexts = new TextView[ballList.getChildCount()];
            for (int i = 0; i < ballTexts.length; i++) {
                ballTexts[i] = (TextView) ballList.getChildAt(i);
                ballTexts[i].setVisibility(View.VISIBLE);
            }

            int childCount = 4;
            otherIssue = new TextView[childCount];
            otherCode = new TextView[childCount];
            otherLayout = new LinearLayout[childCount];
            kuaisanItemLayout = new View[childCount];
            kuaisanSums = new TextView[childCount];
            kuaisanSizes = new TextView[childCount];
            kuaisanOdds = new TextView[childCount];
            for (int i = 0, j = 0; j < otherIssue.length && i < otherList.getChildCount(); i++) {
                View tmp = otherList.getChildAt(i);
                if (tmp instanceof LinearLayout) {
                    otherIssue[j] = (TextView) tmp.findViewById(R.id.lottery_historyother_issue);
                    otherCode[j] = (TextView) tmp.findViewById(R.id.lottery_historyother_code);
                    kuaisanItemLayout[j] = tmp.findViewById(R.id.kuaisanItemLayout);
                    kuaisanSums[j] = (TextView) tmp.findViewById(R.id.kuaisanSum);
                    kuaisanSizes[j] = (TextView) tmp.findViewById(R.id.kuaisanSize);
                    kuaisanOdds[j] = (TextView) tmp.findViewById(R.id.kuaisanOdd);
                    otherLayout[j] = (LinearLayout) tmp;
                    j++;
                }
            }

            int childKuaiL8Count = 20;
            kuaile8Texts = new TextView[childKuaiL8Count];

            for (int i = 0; i < kuaile8Texts.length; i++) {
                View kuaile8Text = LayoutInflater.from(view.getContext()).inflate(R.layout.lottery_trend_item_kuaile8, null);
                kuaile8Texts[i] = kuaile8Text.findViewById(R.id.kuaile8_ball);
                kuaile8Texts[i].setVisibility(View.VISIBLE);
                kuaile8LayoutList.addView(kuaile8Text);
            }
        }
    }
}
