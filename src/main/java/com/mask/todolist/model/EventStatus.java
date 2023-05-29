package com.mask.todolist.model;

/**
 * 事件狀態
 * @ 0: NONE
 * @ 1: Action 進行中
 * @ 2: Complete 完成
 */
public enum EventStatus {
	NONE("NONE", 0),
	ACTION("ACTION", 1),
	COMPLETE("COMPLETE", 2);

	private int status;
	private String desc;

	EventStatus(String desc, int status) {
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return this.status;
	}

	public String getDesc() {
		return this.desc;

	}
}
