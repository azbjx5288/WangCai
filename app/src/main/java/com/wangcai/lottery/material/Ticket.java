package com.wangcai.lottery.material;

import com.wangcai.lottery.data.Method;
import com.wangcai.lottery.data.MethodType;

/**
 * Created by ACE-PC on 2016/1/26.
 */
public class Ticket {
    private String codes="";
    private MethodType methodType;
    private Method chooseMethod;
    private double noteMoney;
    private int multiple;
    private long chooseNotes=0;

    public Ticket() {
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    public Method getChooseMethod() {
        return chooseMethod;
    }

    public void setChooseMethod(Method chooseMethod) {
        this.chooseMethod = chooseMethod;
    }

    public double getNoteMoney() {
        return noteMoney;
    }

    public void setNoteMoney(double noteMoney) {
        this.noteMoney = noteMoney;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public long getChooseNotes() {
        return chooseNotes;
    }

    public void setChooseNotes(long chooseNotes) {
        this.chooseNotes = chooseNotes;
    }
}
