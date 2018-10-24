package com.wangcai.lottery.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.component.LastInputEditText;
import com.wangcai.lottery.data.ChaseRowData;
import com.wangcai.lottery.data.RedoubleRowData;
import com.wangcai.lottery.data.TraceIssue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 翻倍追号UI
 * Created on 2018/07/30.
 *
 * @author ACE
 */
public class RedoubleAdapter extends BaseAdapter implements View.OnClickListener, View.OnTouchListener, View.OnFocusChangeListener,TextView.OnEditorActionListener, OnLongClickListener{

    private int selectedEditTextPosition = -1;
    private Map<Integer,ChaseRowData<RedoubleRowData>> dataList = new HashMap();
    private List<TraceIssue> issueList = new ArrayList<>();
    private SparseArray<Integer> multipleArray=new SparseArray();

    private OnItemListener onItemListener;
    private OnItemMultipleListener onItemMultipleListener;

    private Handler handler = new Handler();
    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            if (onItemMultipleListener != null) {
                onItemMultipleListener.OnItemMultipleListener(multipleArray);
            }
        }
    };
    @Override
    public int getCount() {
        return dataList.size() > 0 ? dataList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void UpdateUIData(Map<Integer,ChaseRowData<RedoubleRowData>> dataList, List<TraceIssue> issueList, SparseArray<Integer> multipleArray) {
        if (dataList.size() < issueList.size() || dataList.size() != multipleArray.size()) {
            return;
        }
        this.dataList = dataList;
        this.issueList = issueList;
        this.multipleArray = multipleArray;
        notifyDataSetChanged();
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void setOnItemMultipleListener(OnItemMultipleListener onItemMultipleListener) {
        this.onItemMultipleListener = onItemMultipleListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chase_redouble_item, parent, false);
            holder = new ViewHolder(convertView);
            holder.issue.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ChaseRowData rowData = dataList.get(position);
        RedoubleRowData redoubleRow= (RedoubleRowData)rowData.get();

        TraceIssue issue = issueList.get(position);
        holder.issue.setText(redoubleRow.getIssue());
        holder.issue.setTag(R.id.chase_issue, position);
        holder.issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemListener != null) {
                    if (holder.issue.isChecked()) {
                        rowData.setCheckBoxStatus(true);
                        onItemListener.onItemListener(rowData, position, true);
                    } else {
                        rowData.setCheckBoxStatus(false);
                        onItemListener.onItemListener(rowData, position, false);
                    }
                }
            }
        });
        //判断CheckBox的状态
        if (rowData.isCheckBoxStatus()) {
            holder.issue.setChecked(true);//选中
        } else {
            holder.issue.setChecked(false);//未选中
        }

        holder.multiple.setText(String.valueOf(redoubleRow.getMultiple()));
        holder.multiple.setOnTouchListener(this);
        holder.multiple.setOnFocusChangeListener(this);
        holder.multiple.setLongClickable(false);
        holder.multiple.setOnEditorActionListener(this);
        holder.multiple.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        holder.multiple.setTag(position);

        if (selectedEditTextPosition != -1 && position == selectedEditTextPosition) { // 保证每个时刻只有一个EditText能获取到焦点
            holder.multiple.requestFocus();
        } else {
            holder.multiple.clearFocus();
        }

        holder.current.setText(String.format("%.3f", redoubleRow.getCost()));
        holder.drawtime.setText(issue.getTime());

        convertView.setTag(R.id.item_root, position); // 应该在这里让convertView绑定position
        convertView.setOnClickListener(this);
        convertView.setOnLongClickListener(this);
        return convertView;
    }
    private TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            handler.removeCallbacks(delayRun);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (selectedEditTextPosition != -1 && !TextUtils.isEmpty(s.toString())) {
                multipleArray.put(selectedEditTextPosition, Integer.valueOf(s.toString()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (delayRun != null) {
                //每次editText有变化的时候，则移除上次发出的延迟线程
                handler.removeCallbacks(delayRun);
            }
            if (!"".equals(s.toString().trim())) {
                //延迟1600ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 1600);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_root:
                int position = (int) view.getTag(R.id.item_root);
                ChaseRowData rowData = dataList.get(position);
                if (onItemListener != null) {
                    AppCompatCheckBox chaseIssue = view.findViewById(R.id.chase_issue);
                    if (chaseIssue.isChecked()) {
                        rowData.setCheckBoxStatus(false);
                        chaseIssue.setChecked(false);
                        onItemListener.onItemListener(rowData, position, false);
                    } else {
                        rowData.setCheckBoxStatus(true);
                        chaseIssue.setChecked(true);
                        onItemListener.onItemListener(rowData, position, true);
                    }
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (hasFocus) {
            editText.addTextChangedListener(mTextWatcher);
        } else {
            editText.removeTextChangedListener(mTextWatcher);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.item_root) {
            int position = (int) view.getTag(R.id.item_root);
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            EditText editText = (EditText) v;
            selectedEditTextPosition = (int) editText.getTag();
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideSoftKeyBoard(v);
        }
        return false;
    }

    private class ActionModeCallbackInterceptor implements ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }
    }

    static class ViewHolder {
        @BindView(R.id.chase_issue)
        AppCompatCheckBox issue;
        @BindView(R.id.chase_multiple)
        LastInputEditText multiple;
        @BindView(R.id.chase_current)
        TextView current;
        @BindView(R.id.chase_drawtime)
        TextView drawtime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 监听器选择
     */
    public interface OnItemListener {
        void onItemListener(ChaseRowData rowData, int position, boolean status);
    }

    /**
     * 监听器倍数修改
     */
    public interface OnItemMultipleListener {
        void OnItemMultipleListener(SparseArray<Integer> multipleArray);
    }

    public void hideSoftKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
