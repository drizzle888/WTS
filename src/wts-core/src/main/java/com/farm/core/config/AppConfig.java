package com.farm.core.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 应用参数工具类，获得config中定义的参数
 * 
 * @author wangdong
 * 
 */
public class AppConfig {
	private static final String BUNDLE_NAME = "config/config"; //$NON-NLS-1$
	private static final Logger log = Logger.getLogger(AppConfig.class);
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private AppConfig() {
	}

	/**
	 * 从properties文件中获得配置值
	 * 
	 * @param key
	 *            配置文件的key
	 * @return
	 */
	public static String getString(String key) {
		try {
			String messager = RESOURCE_BUNDLE.getString(key);
			return messager;
		} catch (MissingResourceException e) {
			String messager = "不能在配置文件" + BUNDLE_NAME + "中发现参数：" + '!' + key + '!';
			log.error(messager);
			throw new RuntimeException(messager);
		}
	}

	/** 从properties文件中获得配置值
	 * @param string
	 * @return
	 */
	public static boolean getBoolean(String string) {
		try {
			String messager = RESOURCE_BUNDLE.getString(string);
			return messager.trim().toUpperCase().equals("TRUE");
		} catch (MissingResourceException e) {
			log.warn(e.getMessage(), e);
			return false;
		}
	}
}
