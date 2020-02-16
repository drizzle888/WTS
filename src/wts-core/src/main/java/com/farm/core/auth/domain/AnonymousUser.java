package com.farm.core.auth.domain;

import java.util.UUID;

/**
 * 匿名用户
 * 
 * @author macpl
 *
 */
public class AnonymousUser implements LoginUser {
	private String name;
	private String id;
	private String loginname;
	private String type;
	private String ip;

	public AnonymousUser() {
		// Anonymous
		name = "匿名用户";
		id = "ANONYMOUS" + (UUID.randomUUID().toString().replaceAll("-", "").replaceAll(" ", "")).substring(9);
		loginname = "anonymous";
		type = "0";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
