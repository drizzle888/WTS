package com.farm.doc.exception;

import com.farm.parameter.FarmParameterService;

/**
 * 没有删除权限异常
 * 
 * @author Administrator
 * 
 */
public class CanNoDeleteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanNoDeleteException(String message) {
		super(message);
	}

	public CanNoDeleteException() {
		super("没有删除权限");
	}
}
