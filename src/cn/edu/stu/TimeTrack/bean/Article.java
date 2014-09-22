package cn.edu.stu.TimeTrack.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Article implements Serializable {
	private String name="";
	private String title = "";
	private String content = "";
	private String picPath = "";
	private boolean isRead = false;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
