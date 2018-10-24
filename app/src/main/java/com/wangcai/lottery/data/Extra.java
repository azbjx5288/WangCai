package com.wangcai.lottery.data;

public class Extra {
    private String position;
    private String seat;

    public Extra(String position, String seat) {
        this.position = position;
        this.seat = seat;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
