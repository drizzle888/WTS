package com.farm.core.auth.domain;

public interface AuthKey {
	/**
	 * 是否需要用户登录使用
	 * 
	 * @return
	 */
	public boolean isLogin();

	/**
	 * 是否需要检查用户权限
	 * 
	 * @return
	 */
	public boolean isCheck();

	/**
	 * 是否可用
	 * 
	 * @return
	 */
	public boolean isUseAble();

	/**
	 * 获得名称
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 权限关键字
	 * 
	 * @return
	 */
	public String getKey();

	/**
	 * 是否权限组
	 * 
	 * @return
	 */
	public boolean isGroupKey();
}
