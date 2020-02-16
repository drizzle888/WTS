package com.farm.doc.exception;


/**
 * 没有修改权限异常
 * 
 * @author Administrator
 * 
 */
public class CanNoWriteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanNoWriteException(String message) {
		super(message);
	}

	public CanNoWriteException() {
		super("当前用户没有修改权限");
	}
}
