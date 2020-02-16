package com.farm.core.inter.domain;

/**
 * 超链接消息(用于用户消息)
 * 
 * @author lenovo
 *
 */
public class LinkMessage {
	private String url;
	private String text;

	public LinkMessage(String url, String text) {
		this.url = url;
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
