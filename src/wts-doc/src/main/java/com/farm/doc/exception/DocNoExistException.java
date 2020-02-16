package com.farm.doc.exception;


/**
 * 知识没有存在异常
 * 
 * @author Administrator
 * 
 */
public class DocNoExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocNoExistException(String message) {
		super(message);
	}

	public DocNoExistException() {
		super("文档不存在");
	}
}
