package com.goldenapple.lottery.pattern;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.component.LimitTextWatcher;

/**
 * Created by ACE-PC on 2017/4/4.
 */

public class PlanSetting implements View.OnClickListener, View.OnFocusChangeListener {


    private View planSetView;
    private RadioGroup radioGroup;

    private LinearLayout ratiolayout0;
    private LinearLayout ratiolayout1;
    private LinearLayout ratiolayout2;
    private LinearLayout ratiolayout3;

    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;

    private int mode = 0;
    private OnChaseModeListener onChaseModeListener;

    public PlanSetting(Activity activity) {
        this.planSetView = LayoutInflater.from(activity).inflate(R.layout.plansetting, null);

        radioGroup = planSetView.findViewById(R.id.plan_radiogroup);

        ratiolayout0 = planSetView.findViewById(R.id.ratiolayout_0);
        ratiolayout1 = planSetView.findViewById(R.id.ratiolayout_1);
        ratiolayout2 = planSetView.findViewById(R.id.ratiolayout_2);
        ratiolayout3 = planSetView.findViewById(R.id.ratiolayout_3);

        radioButton0 = planSetView.findViewById(R.id.plan_ratio_0);
        radioButton1 = planSetView.findViewById(R.id.plan_ratio_1);
        radioButton2 = planSetView.findViewById(R.id.plan_ratio_2);
        radioButton3 = planSetView.findViewById(R.id.plan_ratio_3);

        EditText plan_way_0 = planSetView.findViewById(R.id.plan_way_0);
        plan_way_0.setOnFocusChangeListener(this);
        plan_way_0.addTextChangedListener(new LimitTextWatcher(plan_way_0, 5));

        EditText plan_way_1_0 = planSetView.findViewById(R.id.plan_way_1_0);
        plan_way_1_0.setOnFocusChangeListener(this);
        plan_way_1_0.addTextChangedListener(new LimitTextWatcher(plan_way_1_0, 3));

        EditText plan_way_1_1 = planSetView.findViewById(R.id.plan_way_1_1);
        plan_way_1_1.setOnFocusChangeListener(this);
        plan_way_1_1.addTextChangedListener(new LimitTextWatcher(plan_way_1_1, 5));

        EditText plan_way_1_2 = planSetView.findViewById(R.id.plan_way_1_2);
        plan_way_1_2.setOnFocusChangeListener(this);
        plan_way_1_2.addTextChangedListener(new LimitTextWatcher(plan_way_1_2, 5));

        EditText plan_way_2 = planSetView.findViewById(R.id.plan_way_2);
        plan_way_2.setOnFocusChangeListener(this);
        plan_way_2.addTextChangedListener(new LimitTextWatcher(plan_way_2, 5));

        EditText plan_way_3_0 = planSetView.findViewById(R.id.plan_way_3_0);
        plan_way_3_0.setOnFocusChangeListener(this);
        plan_way_3_0.addTextChangedListener(new LimitTextWatcher(plan_way_3_0, 3));

        EditText plan_way_3_1 = planSetView.findViewById(R.id.plan_way_3_1);
        plan_way_3_1.setOnFocusChangeListener(this);
        plan_way_3_1.addTextChangedListener(new LimitTextWatcher(plan_way_3_1, 5));

        EditText plan_way_3_2 = planSetView.findViewById(R.id.plan_way_3_2);
        plan_way_3_2.setOnFocusChangeListener(this);
        plan_way_3_2.addTextChangedListener(new LimitTextWatcher(plan_way_3_2, 5));


        ratiolayout0.setOnClickListener(this);
        ratiolayout1.setOnClickListener(this);
        ratiolayout2.setOnClickListener(this);
        ratiolayout3.setOnClickListener(this);

        radioButton0.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton0.setChecked(true);
    }

    public View getPlanSetView() {
        return planSetView;
    }

    @Override
    public void onClick(View v) {
        radioButton0.setChecked(false);
        radioButton1.setChecked(false);
        radioButton2.setChecked(false);
        radioButton3.setChecked(false);
        switch (v.getId()) {
            case R.id.ratiolayout_0:
                radioButton0.setChecked(true);
                mode = 0;
                break;
            case R.id.ratiolayout_1:
                radioButton1.setChecked(true);
                mode = 1;
                break;
            case R.id.ratiolayout_2:
                radioButton2.setChecked(true);
                mode = 2;
                break;
            case R.id.ratiolayout_3:
                radioButton3.setChecked(true);
                mode = 3;
                break;
            case R.id.plan_ratio_0:
                radioButton0.setChecked(true);
                mode = 0;
                break;
            case R.id.plan_ratio_1:
                radioButton1.setChecked(true);
                mode = 1;
                break;
            case R.id.plan_ratio_2:
                radioButton2.setChecked(true);
                mode = 2;
                break;
            case R.id.plan_ratio_3:
                radioButton3.setChecked(true);
                mode = 3;
                break;
        }
        if (onChaseModeListener != null) {
            onChaseModeListener.onChaseModeItemClick(mode);
        }
    }

    public void setOnChaseModeListener(OnChaseModeListener onChaseModeListener) {
        this.onChaseModeListener = onChaseModeListener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            radioButton0.setChecked(false);
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
            switch (v.getId()) {
                case R.id.plan_way_0:
                    radioButton0.setChecked(true);
                    mode = 0;
                    break;
                case R.id.plan_way_1_0:
                case R.id.plan_way_1_1:
                case R.id.plan_way_1_2:
                    radioButton1.setChecked(true);
                    mode = 1;
                    break;
                case R.id.plan_way_2:
                    radioButton2.setChecked(true);
                    mode = 2;
                    break;
                case R.id.plan_way_3_0:
                case R.id.plan_way_3_1:
                case R.id.plan_way_3_2:
                    radioButton3.setChecked(true);
                    mode = 3;
                    break;
            }
            if (onChaseModeListener != null) {
                onChaseModeListener.onChaseModeItemClick(mode);
            }
        }
    }

    /**
     * 选中监听器
     */
    public interface OnChaseModeListener {
        void onChaseModeItemClick(int mode);
    }

}
