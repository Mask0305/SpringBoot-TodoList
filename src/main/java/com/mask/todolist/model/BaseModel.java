package com.mask.todolist.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 共通欄位
 */
@MappedSuperclass
public class BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public Long getId() {
		return this.id;
	}

	/**
	 * 每次建立時產生 createdAt
	 */
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = this.createdAt;
	}

	/**
	 * 每次更新時，覆蓋更新時間
	 */
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

}
