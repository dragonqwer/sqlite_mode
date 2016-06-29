package com.example.sqlite_mode;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Task {

	private int id;	
	private String title;
	private String content;
	private String flagCompleted;
	private Date createDate;
	private Time createTime;
	private Timestamp completedTimestamp;
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
	public String getFlagCompleted() {
		return flagCompleted;
	}
	public void setFlagCompleted(String flagCompleted) {
		this.flagCompleted = flagCompleted;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Time getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Time createTime) {
		this.createTime = createTime;
	}
	public Timestamp getCompletedTimestamp() {
		return completedTimestamp;
	}
	public void setCompletedTimestamp(Timestamp completedTimestamp) {
		this.completedTimestamp = completedTimestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
