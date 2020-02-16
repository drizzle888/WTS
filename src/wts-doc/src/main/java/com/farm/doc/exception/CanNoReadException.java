package com.farm.doc.exception;


/**
 * 没有读取权限异常
 * 
 * @author Administrator
 * 
 */
public class CanNoReadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanNoReadException(String message) {
		super(message);
	}

	public CanNoReadException() {
		super("当前用户没有阅读权限");
	}
}
