package com.wangcai.lottery.data;

public class ChaseRowData<T> {

    private boolean checkBoxStatus = true;
    private T object;

    public boolean isCheckBoxStatus() {
        return checkBoxStatus;
    }

    public void setCheckBoxStatus(boolean checkBoxStatus) {
        this.checkBoxStatus = checkBoxStatus;
    }

    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }
}
