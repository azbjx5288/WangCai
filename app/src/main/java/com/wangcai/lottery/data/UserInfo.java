package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alashi on 2016/1/7.
 */
public class UserInfo {


    /**
     * id : 306
     * is_agent : 1
     * username : amostest
     * parent_id : null
     * forefather_ids :
     * parent :
     * forefathers :
     * account_id : 306
     * prize_group : 1960
     * blocked : 0
     * portrait_code : 1
     * name :
     * nickname : amos
     * email :
     * mobile :
     * is_tester : 1
     * qq :
     * skype :
     * bet_coefficient : 1.000
     * login_ip : 203.82.46.57
     * signin_at : 2016-07-22 16:57:02
     * register_at : 2016-06-24 16:01:56
     * abalance : 10072362.9000
     * fund_pwd_seted : 1
     * token : 02c89afc28cd301560a8dae6e88b9ffa310d4977
     */

    private int id;
    @SerializedName("is_agent")
    private boolean isAgent;
    private String username;
    @SerializedName("parent_id")
    private String parentId;
    @SerializedName("forefather_ids")
    private String forefatherIds;
    private String parent;
    private String forefathers;
    @SerializedName("account_id")
    private int accountId;
    @SerializedName("prize_group")
    private int prizeGroup;
    private int blocked;
    @SerializedName("portrait_code")
    private int portraitCode;
    private String name;
    private String nickname;
    private String email;
    private String mobile;
    @SerializedName("is_tester")
    private boolean isTester;
    private String qq;
    private String skype;
    @SerializedName("bet_coefficient")
    private double betCoefficient;
    @SerializedName("login_ip")
    private String loginIp;
    @SerializedName("signin_at")
    private String signinAt;
    @SerializedName("register_at")
    private String registerAt;
    private double abalance;
    @SerializedName("fund_pwd_seted")
    private boolean fundPwdSeted;
    private String token;
    private double withdrawable;//可提现
    private String frozen;//冻结
    private String prohibit;// 不可用

    public double getWithdrawable() {
        return withdrawable;
    }

    public void setWithdrawable(double withdrawable) {
        this.withdrawable = withdrawable;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public String getProhibit() {
        return prohibit;
    }

    public void setProhibit(String prohibit) {
        this.prohibit = prohibit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAgent() { return isAgent; }

    public void setAgent(boolean agent) { isAgent = agent; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getForefatherIds() {
        return forefatherIds;
    }

    public void setForefatherIds(String forefatherIds) {
        this.forefatherIds = forefatherIds;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getForefathers() {
        return forefathers;
    }

    public void setForefathers(String forefathers) {
        this.forefathers = forefathers;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPrizeGroup() {
        return prizeGroup;
    }

    public void setPrizeGroup(int prizeGroup) {
        this.prizeGroup = prizeGroup;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public int getPortraitCode() {
        return portraitCode;
    }

    public void setPortraitCode(int portraitCode) {
        this.portraitCode = portraitCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isTester() {
        return isTester;
    }

    public void setTester(boolean tester) {
        isTester = tester;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public double getBetCoefficient() {
        return betCoefficient;
    }

    public void setBetCoefficient(double betCoefficient) {
        this.betCoefficient = betCoefficient;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getSigninAt() {
        return signinAt;
    }

    public void setSigninAt(String signinAt) {
        this.signinAt = signinAt;
    }

    public String getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(String registerAt) {
        this.registerAt = registerAt;
    }

    public double getAbalance() {
        return abalance;
    }

    public void setAbalance(double abalance) {
        this.abalance = abalance;
    }

    public boolean isFundPwdSeted() {
        return fundPwdSeted;
    }

    public void setFundPwdSeted(boolean fundPwdSeted) {
        this.fundPwdSeted = fundPwdSeted;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
