package com.wangcai.lottery.view.adapter;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.data.BindCardDetail;
import com.wangcai.lottery.material.ConstantInformation;
import com.wangcai.lottery.material.RecordTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class BankCardAdapter extends BaseAdapter {

    private int currentItem = -1;
    private List<BindCardDetail> bankCardList;
    private OnEditItemClickListener onEditItemClickListener;

    public BankCardAdapter(List<BindCardDetail> bankCardList) {
        this.bankCardList = bankCardList;
    }

    public void setData(List<BindCardDetail> cardDetail) {
        this.bankCardList = cardDetail;
        notifyDataSetChanged();
    }

    public void setOnEditItemClickListener(OnEditItemClickListener onEditItemClickListener) {
        this.onEditItemClickListener = onEditItemClickListener;
    }

    @Override
    public int getCount() {
        return bankCardList != null ? bankCardList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (bankCardList == null) {
            return null;
        }
        if (position >= 0 && position < bankCardList.size()) {
            return bankCardList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bankcard_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BindCardDetail bindCard = bankCardList.get(position);

        viewHolder.showArea.setTag(position);
        viewHolder.bankName.setText(bindCard.getBank());
        Drawable img_ico = parent.getContext().getResources().getDrawable(getBankLogo(bindCard.getBankId()));
        img_ico.setBounds(0, 0, img_ico.getMinimumWidth(), img_ico.getMinimumHeight());
        viewHolder.bankName.setCompoundDrawables(img_ico, null, null, null);
        viewHolder.bankName.setCompoundDrawablePadding(4);
        String numb = bindCard.getAccount().substring(bindCard.getAccount().length() - 4, bindCard.getAccount().length());
        viewHolder.bankCard.setText("******** " + numb);

        // 记录的点击位置来设置"对应Item"的可见性（在list依次加载列表数据时，每加载一个时都看一下是不是需改变可见性的那一条）
        if (currentItem == position) {
            viewHolder.hideArea.setVisibility(View.VISIBLE);
        } else {
            viewHolder.hideArea.setVisibility(View.GONE);
        }

        if (currentItem == -1 && !bindCard.isLocked()) {
            currentItem = position;
            if (!TextUtils.isEmpty(bindCard.getIntendedLockAt())) {
                viewHolder.hideArea.setVisibility(View.VISIBLE);
                RecordTime intendedLockAt = ConstantInformation.getLasttime(bindCard.getModifiedAt(), ConstantInformation.df.format(new Date()));
                if (intendedLockAt.getDay() == 0 && intendedLockAt.getHour() < 2) {
                    viewHolder.lockingLayout.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lockingLayout.setVisibility(View.GONE);
                }
            }
            viewHolder.lockBut.setImageResource(R.drawable.bank_open);
            viewHolder.lockBut.setEnabled(true);

        } else if (bindCard.isLocked()) {
            viewHolder.lockBut.setImageResource(R.drawable.bank_close);
            viewHolder.lockBut.setEnabled(false);
        }

        if (onEditItemClickListener != null) {
            onEditItemClickListener.onStatus(bindCard, position);
        }

        viewHolder.showArea.setOnClickListener((View view) -> {
            switchView(viewHolder, view, bindCard);
            notifyDataSetChanged(); // 必须有的一步
        });

        viewHolder.lockBut.setOnClickListener((View view) -> {
            if (onEditItemClickListener != null) {
                onEditItemClickListener.onEditItemClick(position, null); //editType true 清除 ,editStatus 修改 true ,editStatus 锁定 null
            }
        });

        viewHolder.alterBtn.setOnClickListener((View view) -> {
            if (onEditItemClickListener != null) {
                onEditItemClickListener.onEditItemClick(position, true); //editType true 清除 ,editStatus 修改 true
            }
        });

        viewHolder.deleteBtn.setOnClickListener((View view) -> {
            if (onEditItemClickListener != null) {
                onEditItemClickListener.onEditItemClick(position, false); //editType true 清除 ,editStatus  删除 false
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.layout_showArea)
        LinearLayout showArea;
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.bank_card)
        TextView bankCard;
        @BindView(R.id.layout_hideArea)
        RelativeLayout hideArea;
        @BindView(R.id.lock_but)
        ImageButton lockBut;
        @BindView(R.id.locking_layout)
        LinearLayout lockingLayout;
        @BindView(R.id.btn_alter)
        Button alterBtn;
        @BindView(R.id.btn_delete)
        Button deleteBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    // 记录的点击位置来设置"对应Item"的可见性（在list依次加载列表数据时，每加载一个时都看一下是不是需改变可见性的那一条）
    public void switchView(ViewHolder viewHolder, View view, BindCardDetail bindCard) {
        // 用 currentItem 记录点击位置
        int tag = (Integer) view.getTag();
        if (tag == currentItem) { // 再次点击
            currentItem = -1; // 给 currentItem 一个无效值
            viewHolder.hideArea.setVisibility(View.GONE);
        } else if (!bindCard.isLocked()) {
            currentItem = tag;
            if (!TextUtils.isEmpty(bindCard.getIntendedLockAt())) {
                viewHolder.hideArea.setVisibility(View.VISIBLE);
                RecordTime intendedLockAt = ConstantInformation.getLasttime(bindCard.getModifiedAt(), ConstantInformation.df.format(new Date()));
                if (intendedLockAt.getDay() <= 0 && intendedLockAt.getHour() < 2) {
                    viewHolder.lockingLayout.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.lockingLayout.setVisibility(View.GONE);
                }
            }
            if (onEditItemClickListener != null) {
                onEditItemClickListener.onStatus(bindCard, tag);
            }
        }
    }

    /**
     * 选中监听器
     */
    public interface OnEditItemClickListener {
        void onEditItemClick(int position, Boolean editType);

        void onStatus(BindCardDetail bindCard, int position);
    }

    private static int getBankLogo(int backId) {
        switch (backId) {
            case 1://工商银行
                return R.drawable.icon_icbc;
            case 2://建设银行
                return R.drawable.icon_ccb;
            case 3://农业银行
                return R.drawable.icon_abc;
            case 4://中国银行',
                return R.drawable.icon_bc;
            case 5://招商银行
                return R.drawable.icon_cmb;
            case 6://交通银行
                return R.drawable.icon_jtyh;
            case 7://民生银行
                return R.drawable.icon_cmbms;
            case 8://中信银行
                return R.drawable.icon_ccbzx;
            case 9://上海浦东发展银行
                return R.drawable.icon_spdb;
            case 10://广州银行',
                return R.drawable.icon_gzyh;
            case 11://平安银行
                return R.drawable.icon_pab;
            case 13://兴业银行
                return R.drawable.icon_cib;
            case 14://华夏银行
                return R.drawable.icon_hb;
            case 15://中国光大银行
                return R.drawable.icon_ceb;
            case 16://邮政储汇
                return R.drawable.icon_psbc;
            case 17://"城市商业银行"
                return R.drawable.icon_no;
            case 18://"农村商业银行"
                return R.drawable.icon_no;
            case 19://"恒丰银行"
                return R.drawable.icon_hf;
            case 20://"浙商银行"
                return R.drawable.icon_zs;
            /*case 21://"农村合作银行"
                return R.drawable.icon_GZNCXYHZS;*/
            case 22://"渤海银行"
                return R.drawable.icon_bhyh;
            case 23://"徽商银行股份有限公司"
                return R.drawable.icon_wsyh;
            case 24://"村镇银行"
                return R.drawable.icon_no;
            case 25://"上海农村商业银行"
                return R.drawable.icon_srcb;
            case 26://"农村信用合作社"
                return R.drawable.icon_gzncxyhzs;
            case 27://"韩国外换银行"
                return R.drawable.icon_keb;
            case 28://"友利银行"
                return R.drawable.icon_yl;
            case 29://"新韩银行"
                return R.drawable.icon_xh;
            case 31://"韩亚银行"
                return R.drawable.icon_hyyh;
            default:
                return R.drawable.icon_no;
        }
    }
}
