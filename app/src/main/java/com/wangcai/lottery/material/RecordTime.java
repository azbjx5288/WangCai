package com.wangcai.lottery.material;

/**
 * Created on 2016/01/13.
 * @author ACE
 * @功能描述: 存放定时器时间
 */
public class RecordTime {
	private int day=0;
	private int hour=0;
	private int minute=0;
	private int second=0;
	
	public RecordTime() {
	}

	public RecordTime(int day, int hour, int minute, int second) {
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
}
