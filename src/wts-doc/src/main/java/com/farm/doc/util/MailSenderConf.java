package com.farm.doc.util;


public class MailSenderConf {
	private String stmp;
	private String username;
	private String password;
	private boolean isEnable = true;

	public MailSenderConf(String stmp, String username, String password) {
		this.stmp = stmp;
		this.username = username;
		this.password = password;
	}

	public static MailSenderConf getBaseConf() {
		MailSenderConf conf = new MailSenderConf("smtp.ym.163.com","wangd@sdkeji.com","11");
		conf.isEnable = true;
		return conf;
	}

	public String getStmp() {
		return stmp;
	}

	public void setStmp(String stmp) {
		this.stmp = stmp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

}
