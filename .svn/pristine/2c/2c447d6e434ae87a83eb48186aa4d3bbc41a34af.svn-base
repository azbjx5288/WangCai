package com.wangcai.lottery.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.component.WarpLinearLayout;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.LotteryCode;
import com.wangcai.lottery.util.NumbericUtils;
import com.thinkcool.circletextimageview.CircleTextImageView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/3/1.
 */
public class HistoryCodeAdapter extends BaseAdapter {
    private static final String TAG = GameStickResultAdapter.class.getSimpleName();

    private Context context;
    private Lottery lottery;
    private List<LotteryCode> resultList;
    private LayoutInflater inflater;

    public HistoryCodeAdapter(Lottery lottery, Context context) {
        this.context = context;
        this.lottery = lottery;
        this.resultList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resultList == null ? 0 : resultList.size();
    }

    @Override
    public LotteryCode getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList<LotteryCode> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_history_result_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LotteryCode data = getItem(position);
        String issue = context.getResources().getString(R.string.is_issue_trend);
        issue = StringUtils.replaceEach(issue, new String[]{"ISSUETREND"}, new String[]{splitIssue(data.getIssue())});
        holder.issue.setText(issue);
        if (lottery.getSeriesId() == 4) { 
            setKuaisanUi(holder, data);
        } else if (lottery.getSeriesId() == 5) {
            String[] list;
            if (data.getWnNumber().contains(" ")) {
                list = data.getWnNumber().split(" ");
            } else {
                list = new String[data.getWnNumber().length()];
                for (int i = 0; i < list.length; i++) {
                    list[i] = String.valueOf(data.getWnNumber().charAt(i));
                }
            }
            holder.replacelayout.removeAllViews();
            addCars(holder, list, parent.getContext());
        } else {
            String digit = data.getWnNumber();
            holder.ballList.setVisibility(View.VISIBLE);
            holder.pk10layout.setVisibility(View.GONE);
            holder.kuaile8LayoutList.setVisibility(View.GONE);
            holder.kuaisanLayout.setVisibility(View.GONE);

            if (digit.contains(" ")) {
                String[] item = digit.split(" ");
                if (lottery.getSeriesId() == 7) {
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
        }
        return convertView;
    }

    private String splitIssue(String issue) {
        int splitNo = 0;

        switch (lottery.getId()) {
            case 13:
            case 14:
            case 17:
            case 19:
            case 20:
                splitNo = 4;
                break;
            case 2:
            case 6:
            case 8:
            case 9:
            case 22:
            case 27:
            case 32:
                splitNo = 2;
                break;
            default: //  1 7 10 11 12 15 16 18 21 28 30 33 39 41 42 44 48 49 57 59
                splitNo = 3;
        }

        String issueSplit = "";
        if (issue.length() > splitNo) {
            issueSplit = issue.substring(issue.length() - splitNo, issue.length());
        } else {
            issueSplit = issue;
        }
        return issueSplit;
    }

    private void setKuaiLe8(CharSequence[] item, ViewHolder holder) {
        holder.ballList.setVisibility(View.GONE);
        holder.pk10layout.setVisibility(View.GONE);
        holder.kuaisanLayout.setVisibility(View.GONE);
        holder.kuaile8LayoutList.setVisibility(View.VISIBLE);
        holder.ranklayout.setVisibility(View.GONE);

        for (int i = 0; i < holder.kuaile8Texts.length; i++) {
            holder.kuaile8Texts[i].setVisibility(View.VISIBLE);
            holder.kuaile8Texts[i].setText(item[i]);
        }
    }

    private void setKuaisanUi(ViewHolder holder, LotteryCode code) {
        String digit = code.getWnNumber();
        holder.ballList.setVisibility(View.GONE);
        holder.pk10layout.setVisibility(View.GONE);
        holder.kuaisanLayout.setVisibility(View.VISIBLE);
        holder.kuaile8LayoutList.setVisibility(View.GONE);
        holder.ranklayout.setVisibility(View.GONE);
        if (digit == null || "".equals(digit)) {
            setDice(holder.kuaisanDice1, 0);
            setDice(holder.kuaisanDice2, 0);
            setDice(holder.kuaisanDice3, 0);
            holder.kuaisanSum.setVisibility(View.GONE);
            holder.kuaisanSize.setVisibility(View.GONE);
            holder.kuaisanOdd.setVisibility(View.GONE);
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
		
        
    }

    private int[] kuaisanDice = {R.drawable.kuaisan_dice_1, R.drawable.kuaisan_dice_2, R.drawable.kuaisan_dice_3, R.drawable.kuaisan_dice_4, R.drawable.kuaisan_dice_5, R.drawable.kuaisan_dice_6};

    private void setDice(ImageView imageView, int index) {
        if (index == 0) {
            imageView.setImageResource(R.color.transparent);
        } else {
            imageView.setImageResource(kuaisanDice[index - 1]);
        }
    }

    private void setBallText(CharSequence[] item, ViewHolder holder) {
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

    /**
     * 极速PK10
     *
     * @param list
     */
    private void addCars(ViewHolder holder, String[] list, Context context) {
        for (String code : list) {
            ImageView carView = new ImageView(context);
            switch (code) {
                case "01":
                    carView.setImageResource(R.drawable.car_01);
                    break;
                case "02":
                    carView.setImageResource(R.drawable.car_02);
                    break;
                case "03":
                    carView.setImageResource(R.drawable.car_03);
                    break;
                case "04":
                    carView.setImageResource(R.drawable.car_04);
                    break;
                case "05":
                    carView.setImageResource(R.drawable.car_05);
                    break;
                case "06":
                    carView.setImageResource(R.drawable.car_06);
                    break;
                case "07":
                    carView.setImageResource(R.drawable.car_07);
                    break;
                case "08":
                    carView.setImageResource(R.drawable.car_08);
                    break;
                case "09":
                    carView.setImageResource(R.drawable.car_09);
                    break;
                case "10":
                    carView.setImageResource(R.drawable.car_10);
                    break;
                default:
                    break;
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            carView.setLayoutParams(layoutParams);
            holder.replacelayout.addView(carView);
        }
        holder.ranklayout.setVisibility(View.VISIBLE);
        holder.ballList.setVisibility(View.GONE);
        holder.kuaile8LayoutList.setVisibility(View.GONE);
        holder.pk10layout.setVisibility(View.VISIBLE);
        holder.kuaisanLayout.setVisibility(View.GONE);

    }

    private void addDragonTiger(ViewHolder holder, String[] list, Context context) {
        if (list == null || list.length == 0) {
            Log.e(TAG, "-> " + list.length + "    " + list.toString());
            return;
        }
        holder.ranklayout.setVisibility(View.GONE);
        String[] labs = new String[]{"1V10", "2V9", "3V8", "4V7", "5V6"};
        LinearLayout carLayout = new LinearLayout(context);
        carLayout.setGravity(Gravity.CENTER_VERTICAL);
        for (int i = 0; i < 5; i++) {
            TextView labView = new TextView(context);
            labView.setText(labs[i]);
            labView.setTextSize(10);

            CircleTextImageView circleTextImageView = new CircleTextImageView(context);
            circleTextImageView.setTextColor(Color.WHITE);
            circleTextImageView.setTextSize(25);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(60, 60);
            layoutParams.setMargins(10, 0, 10, 0);
            circleTextImageView.setLayoutParams(layoutParams);
            if (NumbericUtils.isNumeric(list[i]) && NumbericUtils.isNumeric(list[(list.length - 1) - i])) {
                if (Integer.valueOf(list[i]) > Integer.valueOf(list[(list.length - 1) - i])) {
                    circleTextImageView.setText("龙");
                    circleTextImageView.setFillColor(context.getResources().getColor(R.color.dragon));
                } else {
                    circleTextImageView.setText("虎");
                    circleTextImageView.setFillColor(context.getResources().getColor(R.color.tiger));
                }
            }
            carLayout.addView(labView);
            carLayout.addView(circleTextImageView);
        }

        holder.replacelayout.addView(carLayout);
        holder.ballList.setVisibility(View.GONE);
        holder.pk10layout.setVisibility(View.VISIBLE);
        holder.kuaile8LayoutList.setVisibility(View.GONE);
        holder.kuaisanLayout.setVisibility(View.GONE);
        holder.ranklayout.setVisibility(View.GONE);
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

    // 依据item的layout
    static class ViewHolder {
        @BindView(R.id.lottery_history_issue)
        TextView issue;
        @BindView(R.id.lottery_history_code)
        LinearLayout ballList;

        @BindView(R.id.kuaile8)
        WarpLinearLayout kuaile8LayoutList;

        //pk10
        @BindView(R.id.pk10layout)
        LinearLayout pk10layout;
        @BindView(R.id.replaceLayout)
        LinearLayout replacelayout;
        @BindView(R.id.rank_layout)
        LinearLayout ranklayout;

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

        TextView[] ballTexts;
        TextView[] kuaile8Texts;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);

            ballTexts = new TextView[ballList.getChildCount()];
            for (int i = 0; i < ballTexts.length; i++) {
                ballTexts[i] = (TextView) ballList.getChildAt(i);
                ballTexts[i].setVisibility(View.VISIBLE);
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
