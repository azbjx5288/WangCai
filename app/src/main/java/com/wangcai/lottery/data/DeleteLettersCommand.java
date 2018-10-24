package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;

/**
 * Created by Gan on 2017/11/17.
 *13.7.删除信息接口
 13.7.1.功能
 删除收件箱数据。
 */
@RequestConfig(api = "service?packet=Message&action=DeleteLetters", method = Request.Method.POST)
public class DeleteLettersCommand extends CommonAttribute{

    private  int type;//收件箱：1,2:发件箱
    private  String ids;//收件箱：1,2:发件箱

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
