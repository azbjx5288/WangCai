package com.goldenapple.lottery.component;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ACE-PC on 2017/5/12.
 */

public class LimitTextWatcher implements TextWatcher {

    private int limit;// 字符个数限制
    private EditText text;// 编辑框控件

    int cursor = 0;// 用来记录输入字符的时候光标的位置
    int before_length;// 用来标注输入某一内容之前的编辑框中的内容的长度

    public LimitTextWatcher(EditText text, int limit) {
        this.limit = limit;
        this.text = text;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,int after) {
        before_length = s.length();
    }

    /**
     * s 编辑框中全部的内容 、start 编辑框中光标所在的位置（从0开始计算）、count 从手机的输入法中输入的字符个数
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        cursor = start;
    }

    @Override
    public void afterTextChanged(Editable s) {
        int after_length = s.length();// 输入内容后编辑框所有内容的总长度
        // 如果字符添加后超过了限制的长度，那么就移除后面添加的那一部分，这个很关键
        if (after_length > limit) {
            // 比限制的最大数超出了多少字
            int d_value = after_length - limit;
            // 这时候从手机输入的字的个数
            int d_num = after_length - before_length;

            int st = cursor + (d_num - d_value);// 需要删除的超出部分的开始位置
            int en = cursor + d_num;// 需要删除的超出部分的末尾位置
            // 调用delete()方法将编辑框中超出部分的内容去掉
            Editable s_new = s.delete(st, en);
            // 给编辑框重新设置文本
            text.setText(s_new.toString());
            // 设置光标最后显示的位置为超出部分的开始位置，优化体验
            text.setSelection(st);
        }
    }

    /**
     * 验证格式
     */
    private boolean verifyOrFormat(String orgStr) {
        Pattern p = Pattern.compile("^(0+)");
        Matcher m = p.matcher(orgStr);
        return m.find();
    }
}
