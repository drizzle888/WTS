package com.farm.authority.domain;

import java.io.Serializable;

import com.farm.core.auth.domain.WebMenu;

public class AuthMenuImpl implements WebMenu,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1595137580049523129L;
	private String params;
    private String icon;
    private String url;
    private String name;
    private String parentid;
    private String id;
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
}
