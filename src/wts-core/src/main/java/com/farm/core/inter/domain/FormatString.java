package com.farm.core.inter.domain;

import org.apache.commons.lang.StringUtils;

/**
 * 封装格式化字符串数据
 * 
 * @author wd
 *
 */
public class FormatString {
	private String[] stringArray;
	private String[] valArray;

	FormatString(String val) {
		try {
			val = val.replaceAll("？", "?");
			stringArray = val.split("\\?");
			if (stringArray.length <= 0) {
				valArray = new String[1];
			} else {
				valArray = new String[stringArray.length];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		String str = null;
		try {
			for (int n = 0; n < stringArray.length; n++) {
				str = (str == null ? "" : str) + stringArray[n]
						+ (StringUtils.isBlank(valArray[n]) ? "" : "<i>" + valArray[n] + "</i>");
			}
			if (str == null && valArray.length > 0) {
				for (int n = 0; n < valArray.length; n++) {
					str = (str == null ? "" : str) + (StringUtils.isBlank(valArray[n]) ? "" : valArray[n]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return str;
	}

	public FormatString setString(String val) {
		try {
			valArray[0] = val;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public FormatString setString(int i, String val) {
		try {
			valArray[i] = val;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}
}
