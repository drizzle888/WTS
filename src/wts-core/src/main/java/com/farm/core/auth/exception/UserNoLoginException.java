package com.farm.core.auth.exception;

public class UserNoLoginException extends Exception {
	public UserNoLoginException() {
		super("当前用户未登录!");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6482296763929242398L;

}
