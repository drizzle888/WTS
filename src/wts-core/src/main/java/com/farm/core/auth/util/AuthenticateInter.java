package com.farm.core.auth.util;

/**
 * 密码编码解码类
 * 
 * @author 王东
 * 
 */
public interface AuthenticateInter {
	/**
	 * 将明文加密
	 * 
	 * @param password
	 *            传人明文
	 * @return
	 */
	public String encodeMacpro(String password);

	/**
	 * 将密文解密
	 * 
	 * @param password
	 *            传人密文
	 * @return
	 * @throws Exception
	 *             解码异常
	 */
	public String decodeMacpro(String password) throws Exception;

	/**
	 * md5不可逆的编码
	 * 
	 * @param password
	 *            传人明文
	 * @return
	 */
	public String encodeMd5(String password);

	/**
	 * 判断是否是MD5密码
	 * 
	 * @param password
	 * @return
	 */
	public boolean isMd5code(String password);

	/**
	 * 加密用户密码MD5(password+loginname)
	 * 
	 * @param password
	 * @return
	 */
	public String encodeLoginPasswordOnMd5(String password, String loginName);
}
