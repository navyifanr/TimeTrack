package cn.edu.stu.TimeTrack.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class TimeData implements Serializable{
	
	private String uId = "";
	private String mDate = "";
	private Map<String,Integer> status = new HashMap<String, Integer>();
	private boolean isUploaded = false;
	
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	
	public Map<String,Integer> getStatus() {
		
		return status;
	}
	public void setStatus(Map<String,Integer> status) {
		this.status = status;
	}
	
	public boolean isUploaded() {
		return isUploaded;
	}
	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}
	
}
