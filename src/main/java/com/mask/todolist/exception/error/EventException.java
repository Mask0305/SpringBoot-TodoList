package com.mask.todolist.exception.error;

public class EventException extends BaseException {

	public EventException() {
		super();
	}

	public EventException(String message, Throwable e) {
		super(message, e);
	}

	public EventException CreateFail(Throwable e) {
		return new EventException("建立待辦事項失敗", e);
	}

}
