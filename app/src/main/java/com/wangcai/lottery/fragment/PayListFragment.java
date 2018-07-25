package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.component.FlowRadioGroup2;
import com.wangcai.lottery.data.RechargeChannelConfig;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ACE-PC on 2017/2/22.
 */
public class PayListFragment extends BaseFragment {

    private static final String FRAGMENT_INDEX = "fragment_index";

    @BindView(R.id.page_tips)
    TextView pageTips;
    @BindView(R.id.channel_config)
    FlowRadioGroup2 channelConfig;


    private int mCurIndex = -1;
    private List<RechargeChannelConfig> channelList;
    private OnInItItem onInItItem;
    private OnModeItemClickListener onModeItemClickListener;

    /**
     * 创建新实例
     *
     * @param index
     * @return
     */
    public static PayListFragment newInstance(int index, String msg) {
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        bundle.putString("msg", msg);
        PayListFragment fragment = new PayListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCurIndex = bundle.getInt(FRAGMENT_INDEX);
        }
        String msg = bundle.getString("msg");
        channelList = GsonHelper.toJsonArray(msg, RechargeChannelConfig.class);
        if(channelList.size()==0){
            pageTips.setVisibility(View.VISIBLE);
            String channel="";
            switch (mCurIndex){
                case 0:   // 微信
                    channel="微信";
                    break;
                case 1:  // 支付宝
                    channel="支付宝";
                    break;
                case 2:  // QQ
                    channel="QQ";
                    break;
                case 3: // 银联
                    channel="银联";
                    break;
                default:
                    channel="微信";
            }
            String tips= getActivity().getResources().getString(R.string.is_page_tips);
            tips = StringUtils.replaceEach(tips, new String[]{"MODE"}, new String[]{channel});
            pageTips.setText(Html.fromHtml(tips).toString());
            channelConfig.setVisibility(View.GONE);
        }else{
            pageTips.setVisibility(View.GONE);
            channelConfig.setVisibility(View.VISIBLE);

            for (int i = 0; i < channelList.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                if (i == 0) {
                    radioButton.setChecked(true);
                }
                radioButton.setId(channelList.get(i).getId());
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                radioButton.setLayoutParams(layoutParams);
                radioButton.setText(channelList.get(i).getName());//style
                radioButton.setTextSize(14);
                channelConfig.addView(radioButton);
            }
            channelConfig.setOnCheckedChangeListener(onCheckedChangeListener);
            /*if(onInItItem!=null){
                onInItItem.onInItItem(findViewById(channelConfig.getCheckedRadioButtonId()).getId());
            }*/
        }
    }

    public void setOnModeItemClickListener(OnModeItemClickListener onModeItemClickListener) {
        this.onModeItemClickListener = onModeItemClickListener;
    }

    public void setOnInItItem(OnInItItem onInItItem) {
        this.onInItItem = onInItItem;
    }

    public int getChannelSelection(){
        if(channelConfig.getChildCount()>0){
            RadioButton radioButton=(RadioButton)findViewById(channelConfig.getCheckedRadioButtonId());
            return radioButton.getId();
        }else{
            return 0;
        }
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (onModeItemClickListener != null) {
                for (int i = 0; i < channelList.size(); i++) {
                    RechargeChannelConfig channel = channelList.get(i);
                    if (channel.getId() == checkedId) {
                        selectPage(i);
                    }
                }
            }
        }
    };

    private void selectPage(int position) {
        channelConfig.check(channelConfig.getChildAt(position).getId());
        onModeItemClickListener.onModeItemClick(channelConfig.getChildAt(position).getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnModeItemClickListener {
        void onModeItemClick(int selection);
    }

    public interface OnInItItem {
        void onInItItem(int selection);
    }
}




