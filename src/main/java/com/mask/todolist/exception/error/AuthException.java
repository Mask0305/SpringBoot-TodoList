package com.mask.todolist.exception.error;

public class AuthException extends BaseException {

	public AuthException() {
		super();
	}

	public AuthException(String message, Throwable e) {
		super(message, e);
	}

	public AuthException AuthFail(Throwable e) {
		return new AuthException("驗證失敗", e);
	}

}
