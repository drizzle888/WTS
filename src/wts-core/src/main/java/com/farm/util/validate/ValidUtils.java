package com.farm.util.validate;


public class ValidUtils {
	public static boolean isEmptyString(String str) {
		return str == null || str.trim().length() <= 0;
	}
}
