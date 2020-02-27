package com.farm.authority.domain;

import com.farm.core.auth.domain.AuthKey;

public class AuthKeyImpl implements AuthKey {
	/**
	 * 是否校驗
	 */
	private boolean ischeck;
	/**
	 * 是否登陆后即可访问
	 */
	private boolean islogin;
	/**
	 * 是否可用
	 */
	private boolean isUseAble;
	/**
	 * 权限关键字
	 */
	private String key;
	/**
	 * 是否是一个权限组key（当一个具体权限找不到时，则引用其上级权限=权限组）
	 */
	private boolean groupKey;
	
	/**
	 * 权限名称
	 */
	private String title;
	
	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public boolean isGroupKey() {
		return groupKey;
	}

	public void setGroupKey(boolean groupKey) {
		this.groupKey = groupKey;
	}

	@Override
	public boolean isCheck() {
		return ischeck;
	}

	@Override
	public boolean isLogin() {
		return islogin;
	}

	@Override
	public boolean isUseAble() {
		return isUseAble;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	public void setUseAble(boolean isUseAble) {
		this.isUseAble = isUseAble;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
