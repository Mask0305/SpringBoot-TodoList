package com.mask.todolist.exception.error;

public class UserException extends BaseException {

	public UserException() {
		super();
	}

	public UserException(String message, Throwable e) {
		super(message, e);
	}

	public UserException CreateFail(Throwable e) {
		return new UserException("建立失敗", e);
	}

	public UserException NotFoundUser(Throwable e) {
		return new UserException("使用者不存在", e);
	}
}
