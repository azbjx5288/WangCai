package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/3.
 */
public class LowerMember {


    /**
     * user_id : 56931
     * username : proxy1
     * level : 1
     * parent_id : 56930
     * top_id : 56930
     * parent_tree : 56930
     * real_name :
     * nick_name : 无昵称
     * balance : 0.0000
     * is_test : 0
     * province :
     * city :
     * email :
     * birth : 0000-00-00
     * mobile :
     * qq :
     * bbin_username :
     * pt_username : JYZPROXY1
     * quota :
     * group_id : 2
     * reg_src : ssc.la
     * reg_ip : 203.82.46.57
     * reg_proxy_ip : 203.82.46.57
     * reg_time : 2016-02-11 16:05:53
     * last_ip : 203.82.46.57
     * last_time : 2016-05-04 10:59:05
     * ref_group_id : 0
     * user_rank : 0
     * remark :
     * status : 8
     * admin_id : 0
     * deposit_num : 0
     * ts : 2016-05-04 10:59:05
     */

    @SerializedName("user_id")
    private int userId;
    @SerializedName("username")
    private String username;
    @SerializedName("level")
    private int level;
    @SerializedName("parent_id")
    private int parentId;
    @SerializedName("top_id")
    private int topId;
    @SerializedName("parent_tree")
    private String parentTree;
    @SerializedName("real_name")
    private String realName;
    @SerializedName("nick_name")
    private String nickName;
    @SerializedName("balance")
    private double balance;
    @SerializedName("is_test")
    private boolean isTest;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("email")
    private String email;
    @SerializedName("birth")
    private String birth;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("qq")
    private String qq;
    @SerializedName("bbin_username")
    private String bbinUsername;
    @SerializedName("pt_username")
    private String ptUsername;
    @SerializedName("quota")
    private String quota;
    @SerializedName("group_id")
    private int groupId;
    @SerializedName("reg_src")
    private String regSrc;
    @SerializedName("reg_ip")
    private String regIp;
    @SerializedName("reg_proxy_ip")
    private String regProxyIp;
    @SerializedName("reg_time")
    private String regTime;
    @SerializedName("last_ip")
    private String lastIp;
    @SerializedName("last_time")
    private String lastTime;
    @SerializedName("ref_group_id")
    private int refGroupId;
    @SerializedName("user_rank")
    private String userRank;
    @SerializedName("remark")
    private String remark;
    @SerializedName("status")
    private int status;
    @SerializedName("admin_id")
    private int adminId;
    @SerializedName("deposit_num")
    private int depositNum;
    @SerializedName("ts")
    private String ts;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getTopId() {
        return topId;
    }

    public void setTopId(int topId) {
        this.topId = topId;
    }

    public String getParentTree() {
        return parentTree;
    }

    public void setParentTree(String parentTree) {
        this.parentTree = parentTree;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getBbinUsername() {
        return bbinUsername;
    }

    public void setBbinUsername(String bbinUsername) {
        this.bbinUsername = bbinUsername;
    }

    public String getPtUsername() {
        return ptUsername;
    }

    public void setPtUsername(String ptUsername) {
        this.ptUsername = ptUsername;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getRegSrc() {
        return regSrc;
    }

    public void setRegSrc(String regSrc) {
        this.regSrc = regSrc;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getRegProxyIp() {
        return regProxyIp;
    }

    public void setRegProxyIp(String regProxyIp) {
        this.regProxyIp = regProxyIp;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public int getRefGroupId() {
        return refGroupId;
    }

    public void setRefGroupId(int refGroupId) {
        this.refGroupId = refGroupId;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(int depositNum) {
        this.depositNum = depositNum;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
