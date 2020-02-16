package com.farm.core.auth.util;

import org.apache.commons.codec.binary.Base64;

public class AuthenticateProvider implements AuthenticateInter {
	private AuthenticateProvider() {
	}

	public static AuthenticateInter getInstance() {
		return new AuthenticateProvider();
	}

	public String decodeMacpro(String password) throws Exception {
		byte[] fpass = Base64.decodeBase64(password.getBytes());
		String str = new String(fpass);
		return str;
	}

	public String encodeMacpro(String password) {
		byte[] cpass = Base64.encodeBase64(password.getBytes());
		String temp = new String(cpass);
		return temp;
	}

	@Override
	public String encodeMd5(String password) {
		if (this.isMd5code(password)) {
			return password;
		} else {
			return new MD5().getMD5ofStr(password);
		}
	}

	@Override
	public boolean isMd5code(String password) {
		if (password.trim().length() == 32)
			return true;
		else
			return false;
	}

	@Override
	public String encodeLoginPasswordOnMd5(String password, String loginName) {
		return encodeMd5(password + loginName);
	}
}
