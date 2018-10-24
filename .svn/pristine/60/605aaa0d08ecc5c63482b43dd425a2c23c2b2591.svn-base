package com.goldenapple.lottery.data;


import com.android.volley.Request;
import com.goldenapple.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2017/2/7.
 */
@RequestConfig(api = "service?packet=Message&action=SendLetters", method = Request.Method.POST)
public class SendMsgCommand extends CommonAttribute{

    private String title;
    private String content;
    private int user_type;
    private int receiver;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }
}
