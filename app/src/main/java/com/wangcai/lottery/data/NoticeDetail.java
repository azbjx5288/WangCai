package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * 公告或banner的详情
 * Created by User on 2016/2/19.
 */
public class NoticeDetail {


    /**
     * id : 61
     * category_id : 2
     * title : 金苹果全面开售！就是当你的小呀小苹果！
     * content : <p>尊敬的客户&nbsp; 您好：</p><p>&nbsp;</p><p>金苹果彩票全面开售啦！</p><p>更流畅的购彩！ 更快度的提现与充值！</p><p>&nbsp;</p><p>金苹果彩票隶属于亚洲最大的综合性公司金亚洲集团旗下，拥有包括美国在内的两国权威官方认证，</p><p>已根据不同产品线先后在6个国家成立了多家分公司。</p><p>&nbsp;</p><p>平台信誉一流。前期运营资金超过5000万，确保提款无忧。</p><p>&nbsp;</p><p>最完整的营运团队，最雄厚资金背景！</p><p>&nbsp;</p><p>金苹果彩票欢迎各位的加入！</p><p>&nbsp;<span style="line-height: 1em;">&nbsp;</span></p><p>若有任何需要谘询的地方，欢迎随时谘询QQ客服。(谘询前需先启动QQ软件)</p><p>&nbsp;</p><p>&nbsp;</p><p>永远新奇&nbsp; 值得期待 – 金苹果</p><p>2015年 09月 08日</p><p><br/></p>
     * created_at : 2015-09-08 15:14:22
     */

    private int id;
    @SerializedName("category_id")
    private String categoryId;
    private String title;
    private String content;
    @SerializedName("created_at")
    private String createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
