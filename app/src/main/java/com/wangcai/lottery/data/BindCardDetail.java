package com.wangcai.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class BindCardDetail {


    /**
     * id : 81
     * bank_id : 2
     * bank : 中国建设银行
     * province : 河北
     * city : 唐山
     * branch : 河北唐山
     * branch_address : 河北唐山河北唐山
     * account_name : 杨永平
     * account : 4367421217054374228
     * status : 1
     * modified_at : 2017-03-03 14:57:53
     * locked : 1
     * intended_withdrawal_at : 2017-03-03 14:58:53
     * intended_lock_at :
     */

    @SerializedName("id")
    private int id;
    @SerializedName("bank_id")
    private int bankId;
    @SerializedName("bank")
    private String bank;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("branch")
    private String branch;
    @SerializedName("branch_address")
    private String branchAddress;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account")
    private String account;
    @SerializedName("status")
    private int status;
    @SerializedName("modified_at")
    private String modifiedAt;
    @SerializedName("locked")
    private boolean locked;
    @SerializedName("intended_withdrawal_at")
    private String intendedWithdrawalAt;
    @SerializedName("intended_lock_at")
    private String intendedLockAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getIntendedWithdrawalAt() {
        return intendedWithdrawalAt;
    }

    public void setIntendedWithdrawalAt(String intendedWithdrawalAt) {
        this.intendedWithdrawalAt = intendedWithdrawalAt;
    }

    public String getIntendedLockAt() {
        return intendedLockAt;
    }

    public void setIntendedLockAt(String intendedLockAt) {
        this.intendedLockAt = intendedLockAt;
    }
}
