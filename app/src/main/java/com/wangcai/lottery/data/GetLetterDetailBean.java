package com.wangcai.lottery.data;

/**
 * 13.3.获得收件信息详情接口
 * response
 */

public class GetLetterDetailBean{
    private  int id	;	//记录id 	122
   private String sender;//	发送人	Test111
    private String created_at	;//发送时间	2018-07-09
    private  int is_readed;//		是否已经阅读	0:未读,1::已读取
    private String msg_title	;//标题	水电费
    private String  msg_type	;//消息类型 中文说明	站内私信
    private String  msg_content;//收件信内容	士大夫撒旦法师的水电费

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getIs_readed() {
        return is_readed;
    }

    public void setIs_readed(int is_readed) {
        this.is_readed = is_readed;
    }

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }
}
