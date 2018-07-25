package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 玩法返回信息
 * Created by ACE-PC on 2016/1/21.
 */
public class MethodList implements Serializable {

    /**
     * id : 2
     * pid : 0
     * name_cn : 五星
     * name_en : wuxing
     * children : [{"id":"4","pid":"2","name_cn":"直选","name_en":"zhixuan","children":[{"id":"68","pid":"4","series_way_id":"68","name_cn":"直选复式","name_en":"fushi","price":"2","bet_note":"从个、十、百、千、万位各选一个号码组成一注","bonus_note":"从万位、千位、百位、十位、个位中各选择一个号码组成一注，所选号码与开奖号码全部相同，且顺序一致，即为中奖。","basic_methods":"16"}]},{"id":"5","pid":"2","name_cn":"组选","name_en":"zuxuan","children":[{"id":"32","pid":"5","series_way_id":"32","name_cn":"组选120","name_en":"zuxuan120","price":"2","bet_note":"从0-9中选择5个号码组成一注","bonus_note":"从0-9中任意选择5个号码组成一注，所选号码与开奖号码的万位、千位、百位、十位、个位相同，顺序不限，即为中奖。","basic_methods":"22"},{"id":"31","pid":"5","series_way_id":"31","name_cn":"组选60","name_en":"zuxuan60","price":"2","bet_note":"从\u201c二重号\u201d选择一个号码，\u201c单号\u201d中选择三个号码组成一注","bonus_note":"选择1个\u201c二重号\u201d和3个单号号码组成一注，所选的单号号码与开奖号码相同，且所选二重号码在开奖号码中出现了2次，即为中奖。","basic_methods":"21"},{"id":"30","pid":"5","series_way_id":"30","name_cn":"组选30","name_en":"zuxuan30","price":"2","bet_note":"从\u201c二重号\u201d选择两个号码，\u201c单号\u201d中选择一个号码组成一注","bonus_note":"选择2个二重号和1个单号号码组成一注，所选的单号号码与开奖号码相同，且所选的2个二重号码分别在开奖号码中出现了2次，即为中奖","basic_methods":"20"},{"id":"29","pid":"5","series_way_id":"29","name_cn":"组选20","name_en":"zuxuan20","price":"2","bet_note":"从\u201c三重号\u201d选择一个号码，\u201c单号\u201d中选择两个号码组成一注","bonus_note":"选择1个三重号码和2个单号号码组成一注，所选的单号号码与开奖号码相同，且所选三重号码在开奖号码中出现了3次，即为中奖。","basic_methods":"19"},{"id":"28","pid":"5","series_way_id":"28","name_cn":"组选10","name_en":"zuxuan10","price":"2","bet_note":"从\u201c三重号\u201d选择一个号码，\u201c二重号\u201d中选择一个号码组成一注","bonus_note":"选择1个三重号码和1个二重号码，所选三重号码在开奖号码中出现3次，并且所选二重号码在开奖号码中出现了2次，即为中奖。","basic_methods":"18"},{"id":"27","pid":"5","series_way_id":"27","name_cn":"组选5","name_en":"zuxuan5","price":"2","bet_note":"从\u201c四重号\u201d选择一个号码，\u201c单号\u201d中选择一个号码组成一注","bonus_note":"选择1个四重号码和1个单号号码组成一注，所选的单号号码与开奖号码相同，且所选四重号码在开奖号码中出现了4次，即为中奖。","basic_methods":"17"}]}]
     */

    @SerializedName("id")
    private int id;
    @SerializedName("pid")
    private int pid;
    @SerializedName("name_cn")
    private String nameCn;
    @SerializedName("name_en")
    private String nameEn;
    /**
     * id : 4
     * pid : 2
     * name_cn : 直选
     * name_en : zhixuan
     * children : [{"id":"68","pid":"4","series_way_id":"68","name_cn":"直选复式","name_en":"fushi","price":"2","bet_note":"从个、十、百、千、万位各选一个号码组成一注","bonus_note":"从万位、千位、百位、十位、个位中各选择一个号码组成一注，所选号码与开奖号码全部相同，且顺序一致，即为中奖。","basic_methods":"16"}]
     */

    @SerializedName("children")
    private List<MethodType> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public List<MethodType> getChildren() {
        return children;
    }

    public void setChildren(List<MethodType> children) {
        this.children = children;
    }

}
