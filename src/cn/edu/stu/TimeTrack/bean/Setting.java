package cn.edu.stu.TimeTrack.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Setting implements Serializable {
	
	private boolean messagePush = true;
	private boolean isUnlocked = true;
	
	public boolean isMessagePush() {
		return messagePush;
	}
	public void setMessagePush(boolean messagePush) {
		this.messagePush = messagePush;
	}
	
	public boolean isUnlocked(){
		return isUnlocked;
	}
	public void setUnlocked(boolean isUnlocked) {
		this.isUnlocked = isUnlocked;
	}
	
}
