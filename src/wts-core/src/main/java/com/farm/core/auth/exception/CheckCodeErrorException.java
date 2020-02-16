package com.farm.core.auth.exception;

/**验证码错误异常
 * @author wangdong
 *
 */
public class CheckCodeErrorException extends Exception {
	public CheckCodeErrorException(String message) {
		super(message);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6482296763929242398L;

}
