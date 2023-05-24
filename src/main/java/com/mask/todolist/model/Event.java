package com.mask.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 待辦事項
 */
@Entity
@Table
public class Event extends BaseModel {

	/**
	 * 使用者
	 * <p>
	 * 建立與User的關聯，關聯外鍵 user.id
	 */
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	/**
	 * 標題
	 */
	@Column
	private String title;

	/**
	 * 內容
	 */
	@Column
	private String content;

	/**
	 * 事件狀態
	 * 
	 * @EventStats
	 */
	@Column
	private EventStats stats;

	public Event(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
		this.stats = EventStats.ACTION;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContent() {
		return this.content;
	}

	public User getUser() {
		return this.user;
	}

	public EventStats getEventStats() {
		return this.stats;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatusComplete() {
		this.stats = EventStats.COMPLETE;
	}
}

/**
 * 事件狀態
 * @ 0: NONE
 * @ 1: Action 進行中
 * @ 2: Complete 完成
 */
enum EventStats {
	NONE(0, "NONE"),
	ACTION(1, "Action"),
	COMPLETE(2, "Complete");

	private int stats;
	private String desc;

	EventStats(int stats, String desc) {
		this.stats = stats;
		this.desc = desc;
	}

	public int getStats() {
		return this.stats;
	}

	public String getDesc() {
		return this.desc;

	}
}
