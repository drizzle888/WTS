package com.farm.util.web;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.xml.bind.ValidationException;

import com.farm.core.auth.util.MD5;

/**
 * 将中文编码或解码为字符串
 * 
 * @author macpl
 *
 */
public class WaterCodeUtils {
	//private static String PRIVATE_KEY = "WCP";
	//private static DesUtil des = DesUtil.getInstance(PRIVATE_KEY);

	public static WaterCodeUtils getInctance() {
		return new WaterCodeUtils();
	}

	/**
	 * 编码字符串
	 * 
	 * @param str
	 * @return
	 */
	public String encode(String str) {
		try {
			String val = URLEncoder.encode(str, "utf-8").replaceAll("%A", "-B").replaceAll("%B", "-C")
					.replaceAll("%C", "-D").replaceAll("%D", "-E").replaceAll("%E", "-F").replaceAll("%", "-");
			String md5 = (new MD5().getMD5ofStr(val) + "00000").substring(0, 5);
			return val + "-" + md5;
		} catch (Exception e) {
			return "NONE";
		}
	}

	/**
	 * 解码字符串
	 * 
	 * @param str
	 * @return
	 * @throws ValidationException
	 *             编码格式不正确，或者编码被篡改过
	 */
	public String decode(String str) throws ValidationException {
		try {
			if (str.lastIndexOf("-") < 0) {
				throw new ValidationException("the key format is error!");
			}

			String val = str.substring(0, str.lastIndexOf("-"));
			String md5 = str.substring(str.lastIndexOf("-") + 1);
			String checkMd5 = (new MD5().getMD5ofStr(val) + "00000").substring(0, 5);
			if (!md5.equals(checkMd5)) {
				throw new ValidationException("the key format is error!");
			}
			return URLDecoder.decode(val.replaceAll("-B", "%A").replaceAll("-C", "%B").replaceAll("-D", "%C")
					.replaceAll("-E", "%D").replaceAll("-F", "%E").replaceAll("-", "%"), "utf-8");
		} catch (Exception e) {
			if (e instanceof ValidationException) {
				throw (ValidationException) e;
			}
			return "NONE";
		}
	}

}
