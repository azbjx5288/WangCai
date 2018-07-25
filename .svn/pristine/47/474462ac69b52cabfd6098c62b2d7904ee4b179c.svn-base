package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/3/3.
 */
@RequestConfig(api = "service?packet=User&action=deleteBankCard",method = Request.Method.POST)
public class DeleteCommand extends CommonAttribute{
    @SerializedName("card_id")
    private int cardId;
    @SerializedName("checked_token")
    private String checkedToken;

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setCheckedToken(String checkedToken) {
        this.checkedToken = checkedToken;
    }

}
