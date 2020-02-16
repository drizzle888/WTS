package com.farm.core.page;

/**
 * 提交状态，用来标志操作是否成功，多用于页面对后台操作的判断
 * 
 * @author wangdong
 * 
 */
public enum StateType {
	/**
	 * 成功
	 */
	SUCCESS(0), /**
	 * 失败
	 */
	ERROR(1);
	public int value;

	private StateType(int var) {
		value = var;
	}
}
