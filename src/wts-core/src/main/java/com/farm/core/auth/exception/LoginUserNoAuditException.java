package com.farm.core.auth.exception;

/**用户状态为待审核，此时抛出异常
 * @author lenovo
 *
 */
public class LoginUserNoAuditException extends Exception {
	public LoginUserNoAuditException(String message) {
		super(message);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6482296763929242398L;

}
