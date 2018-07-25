package com.wangcai.lottery.pattern;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcai.lottery.R;
import com.wangcai.lottery.component.CustomDialog;
import com.wangcai.lottery.component.DialogLayout;
import com.wangcai.lottery.component.WheelView;
import com.wangcai.lottery.material.ChaseRuleData;
import com.wangcai.lottery.material.ConstantInformation;

import java.util.Arrays;

/**
 * Created by ACE-PC on 2016/3/28.
 */
public class TaskPlanView implements View.OnClickListener, DraftPlansMenu.OnItemClickListener {
    private static final String TAG = TaskPlanView.class.getSimpleName();
    private Activity activity;
    private LinearLayout planarrange;
    private TextView multiple;
    private TextView issueno;
    private DraftPlansMenu draftplans_menu;
    private View view;
    private OnArrangeChangedListener onArrangeChangedListener;
    private TextView textBut;
    private TextView textPromptBut;
    private int index = 0;
    private ChaseRuleData chaserule;

    public TaskPlanView(Activity activity, View view) {
        this.activity = activity;
        this.chaserule = new ChaseRuleData();
        multiple = (TextView) view.findViewById(R.id.plan_multiple);
        multiple.setOnClickListener(this);
        issueno = (TextView) view.findViewById(R.id.plan_issueno);
        issueno.setOnClickListener(this);

        textBut = (TextView) view.findViewById(R.id.textViewBut);
        textPromptBut = (TextView) view.findViewById(R.id.textViewPromptBut);

        planarrange = (LinearLayout) view.findViewById(R.id.plan_arrange);
        planarrange.setOnClickListener(this);

        // 初始化弹出菜单
        draftplans_menu = new DraftPlansMenu(activity);
        draftplans_menu.addItems(new String[]{"同倍追号", "翻倍追号", "成长型", "稳定型", "均衡型"}, new String[]{"设置相同倍数追号", "设置翻倍盈利追号", "设置最低盈利率", "设置最低盈利金额", "设置分段最低盈利率"});
        draftplans_menu.setOnItemClickListener(this);

    }

    public OnArrangeChangedListener getOnArrangeChangedListener() {
        return onArrangeChangedListener;
    }

    public void setOnArrangeChangedListener(OnArrangeChangedListener onArrangeChangedListener) {
        this.onArrangeChangedListener = onArrangeChangedListener;
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.plan_arrange) {
            draftplans_menu.showAsDropDown(v);
        } else if ((v.getId() == R.id.plan_issueno) || (v.getId() == R.id.plan_multiple)) {
            View outerView = LayoutInflater.from(activity).inflate(R.layout.wheel_view, null);
            ((TextView) outerView.findViewById(R.id.editlabel)).setText(v.getId() == R.id.plan_multiple ? "输入起追倍数:" : "输入起追期数:");
            final EditText edittext = (EditText) outerView.findViewById(R.id.wv_editText);
            edittext.setText(v.getId() == R.id.plan_multiple ? "1" : "10");

            WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
            /*wv.setOffset(1);
            wv.setItems(v.getId() == R.id.plan_multiple ? Arrays.asList(ConstantInformation.getMultiple()) : Arrays.asList(ConstantInformation.getChaseissue()));
            wv.setSeletion(v.getId() == R.id.plan_multiple ? 0 : 1);
            wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    edittext.setText(item.substring(0, item.length() - 1));
                }
            });*/

            CustomDialog.Builder builder = new CustomDialog.Builder(activity);
            builder.setContentView(outerView);
            builder.setTitle(v.getId() == R.id.plan_multiple ? "选择起始倍数" : "选择期数");
            builder.setLayoutSet(DialogLayout.UP_AND_DOWN);
            builder.setPositiveButton("确定", (dialog, which) -> {
                if(v.getId() == R.id.plan_issueno){
                    issueno.setText(edittext.getText());
                }else if(v.getId() == R.id.plan_multiple){
                    multiple.setText(edittext.getText());
                }
                changeRules();
                dialog.dismiss();
            });
            builder.setNegativeButton("取消", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.create().show();
        }
    }

    @Override
    public void onItemClick(int index) {
        this.index = index;
        if (index == 1) {
            view = LayoutInflater.from(activity).inflate(R.layout.draftplansmenu_item_rule2, null, false);
            dialog(view, "翻倍追号");
        } else if (index == 2 || index == 3) {
            view = LayoutInflater.from(activity).inflate(R.layout.draftplansmenu_item_rule, null, false);
            ((TextView) view.findViewById(R.id.plan_ratiounit)).setText(index == 2 ? "%" : "元");
            dialog(view, index == 2 ? "成长型" : "稳定型");
        } else if (index == 4) {
            view = LayoutInflater.from(activity).inflate(R.layout.draftplansmenu_item_rule3, null, false);
            dialog(view, "均衡型");
        } else {
            changeRules();
        }
    }

    private void dialog(View v, String title) {
        CustomDialog.Builder builder = new CustomDialog.Builder(activity);
        builder.setContentView(v);
        builder.setTitle(title);
        builder.setLayoutSet(DialogLayout.UP_AND_DOWN);
        builder.setPositiveButton("确定", (dialog, which) -> {
            changeRules();
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void changeRules() {
        if (TextUtils.isEmpty(issueno.getText())) {
            Toast.makeText(activity, "请追号期数", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(issueno.getText().toString()) == 0) {
            Toast.makeText(activity, "追号至少为1期", Toast.LENGTH_SHORT).show();
            return;
        }
        chaserule.setMultiple(Integer.parseInt(multiple.getText().toString()));
        chaserule.setGainMode(index);

        int issue = 0, ratio = 0, agorate = 0, laterrate = 0;
        if (index == 0) {
            textBut.setText("同倍追号");
            textPromptBut.setText("全程以" + multiple.getText().toString() + "倍追号");
        } else if (index == 1) {
            EditText issuegap = (EditText) view.findViewById(R.id.issuegap);
            EditText turnmultiple = (EditText) view.findViewById(R.id.turnmultiple);
            chaserule.setIssueGap(Integer.parseInt(issuegap.getText().toString()));
            chaserule.setMultipleTurn(Integer.parseInt(turnmultiple.getText().toString()));
            textBut.setText("翻倍追号");
            textPromptBut.setText("全程以每隔" + issuegap.getText().toString() + "期×" + turnmultiple.getText().toString() + "倍追号");
        } else if (index == 2 || index == 3) {
            EditText ratioEdit = (EditText) view.findViewById(R.id.plan_ratio);
            if (TextUtils.isEmpty(ratioEdit.getText())) {
                ratio = 30;
            } else {
                ratio = Integer.parseInt(ratioEdit.getText().toString());
            }
            chaserule.setAgoValue(ratio);
            textBut.setText(index == 2 ? "成长型" : "稳定型");
            textPromptBut.setText(((TextView) view.findViewById(R.id.plan_ratiotext)).getText().toString() + ratio + ((TextView) view.findViewById(R.id.plan_ratiounit)).getText());
        } else if (index == 4) {
            EditText agoissue = (EditText) view.findViewById(R.id.agoissue);
            EditText agovalue = (EditText) view.findViewById(R.id.agovalue);
            EditText latervalue = (EditText) view.findViewById(R.id.latervalue);

            if (TextUtils.isEmpty(agoissue.getText())) {
                issue = 0;
            } else {
                issue = Integer.parseInt(agoissue.getText().toString());
            }

            if (TextUtils.isEmpty(agovalue.getText())) {
                agorate = 0;
            } else {
                agorate = Integer.parseInt(agovalue.getText().toString());
            }

            if (TextUtils.isEmpty(agovalue.getText())) {
                laterrate = 0;
            } else {
                laterrate = Integer.parseInt(latervalue.getText().toString());
            }
            chaserule.setAgoIssue(issue);
            chaserule.setAgoValue(agorate);
            chaserule.setLaterValue(laterrate);
            textBut.setText("均衡型");
            textPromptBut.setText(((TextView) view.findViewById(R.id.agoissue_label)).getText().toString()
                    + issue
                    + ((TextView) view.findViewById(R.id.agoissue_lat)).getText().toString()
                    + agorate
                    + ((TextView) view.findViewById(R.id.agounit)).getText().toString()
                    + "之后"
                    + laterrate
                    + ((TextView) view.findViewById(R.id.laterunit)).getText().toString());
        }

        onArrangeChangedListener.newArrange(Integer.parseInt(multiple.getText().toString()), Integer.parseInt(issueno.getText().toString()), chaserule);
    }

    public interface OnArrangeChangedListener {
        void newArrange(int multiple, int issueno, ChaseRuleData chaserule);
    }
}
