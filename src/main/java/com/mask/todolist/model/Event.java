package com.mask.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
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
	 * @EventStatus
	 */
	@Column
	private EventStatus status;

	public Event(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
		this.status = EventStatus.ACTION;
	}

	public Event() {

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

	public EventStatus getEventStatus() {
		return this.status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatusComplete() {
		this.status = EventStatus.COMPLETE;
	}

}
