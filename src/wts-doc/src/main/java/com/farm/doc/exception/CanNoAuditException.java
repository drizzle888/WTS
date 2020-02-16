package com.farm.doc.exception;

import com.farm.parameter.FarmParameterService;

/**
 * 没有删除权限异常
 * 
 * @author Administrator
 * 
 */
public class CanNoAuditException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanNoAuditException(String message) {
		super(message);
	}

	public CanNoAuditException() {
		super("没有审核权限");
	}
}
