package com.wangcai.lottery.data;

import com.android.volley.Request;
import com.wangcai.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/6.
 */
@RequestConfig(api = "service?packet=User&action=bindBankCard", response = String.class, method = Request.Method.POST)
public class BindCardCommand extends CommonAttribute {
    @SerializedName("bank_id")
    private int bankId;
    @SerializedName("province_id")
    private String provinceId;
    @SerializedName("province")
    private String province;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("city")
    private String city;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account")
    private String account;
    @SerializedName("branch")
    private String branch;
    @SerializedName("checked_token")
    private String checkedToken;
    @SerializedName("token")
    private String token;
    @SerializedName("sign")
    private String sign;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
