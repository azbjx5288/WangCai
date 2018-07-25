package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/3/2.
 */
@RequestConfig(api = "service?packet=User&action=changeBankCard", response = String.class, method = Request.Method.POST)
public class ChangeCardCommand extends CommonAttribute{
    @SerializedName("card_id")
    private int cardId;
    @SerializedName("bank_id")
    private int bankId;
    @SerializedName("province_id")
    private String provinceId;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account")
    private String account;
    @SerializedName("branch")
    private String branch;
    @SerializedName("checked_token")
    private String checkedToken;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCheckedToken() {
        return checkedToken;
    }

    public void setCheckedToken(String checkedToken) {
        this.checkedToken = checkedToken;
    }

}
