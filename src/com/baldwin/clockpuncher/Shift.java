package com.baldwin.clockpuncher;

public class Shift {
	int _id;
	long timeIn;
	long timeOut;
	long totalTime;

	public Shift(int id, long inTime, long outTime, long timeTotal){
		this._id = id;
		this.timeIn = inTime;
		this.timeOut = outTime;
		this.totalTime = timeTotal;		
	}
	
	public Shift(int id, long inTime, long outTime){
		this._id = id;
		this.timeIn = inTime;
		this.timeOut = outTime;
	}
	public Shift(int id, long inTime){
		this._id = id;
		this.timeIn = inTime;
	}
	
	public Shift(){
		
	}
	
	
	
	public int getId() {
		return this._id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public void setTimeIn(long in) {
		this.timeIn = in;
	}

	public void setTimeOut(long out) {
		this.timeOut = out;
		this.totalTime = timeOut - timeIn;
	}

	public long getTimeIn() {
		return this.timeIn;
	}

	public long getTimeout() {
		return this.timeOut;
	}

	public long getTotalTime() {
		return this.totalTime;

	}

}
