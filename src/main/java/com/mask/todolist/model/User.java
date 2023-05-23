package com.mask.todolist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class User extends BaseModel {

	@Column
	@OneToMany(mappedBy = "userId")
	private long id;

	/**
	 * 使用者名稱
	 */
	@Column
	private String name;

	/**
	 * 帳號
	 */
	@Column
	private String account;

	/**
	 * 帳號+密碼 Hash
	 */
	@Column
	private String pwdHash;

	public void setName(String name) {
		this.name = name;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPwdHash(String pwdHash) {
		this.pwdHash = pwdHash;
	}

	public String getName() {
		return this.name;
	}

	public String getAccount() {
		return this.account;
	}

	/**
	 * 比較密碼Hash是否相同
	 */
	public boolean comparePwdHash(String pwdHash) {
		// 引用類型之間的值比較要使用 equals
		return this.pwdHash.equals(pwdHash);
	}
}
