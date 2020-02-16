package com.farm.core.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtils {
	private String PROPERTY_FILE;
	private static final Logger log = Logger.getLogger(PropertiesUtils.class);
	/**
	 * @param fileName  如jdbc.properties
	 */
	public PropertiesUtils(String fileName) {
		PROPERTY_FILE = PropertiesUtils.class.getResource("/").getPath()
				.toString()
				+ fileName;
	}

	/**
	 * 根据Key 读取Value
	 */
	public String getData(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					PROPERTY_FILE));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 修改或添加键值对 如果key存在，修改 反之，添加。
	 */
	public void setData(String key, String value) {
		Properties prop = new Properties();
		OutputStream fos = null;
		InputStream fis = null;
		try {
			File file = new File(PROPERTY_FILE);
			if (!file.exists())
				file.createNewFile();
			fis = new FileInputStream(file);
			prop.load(fis);
		} catch (IOException e) {
			System.err.println("Visit " + PROPERTY_FILE + " for updating "
					+ value + " value error");
		} finally {
			try {
				fis.close();// 一定要在修改值之前关闭fis
			} catch (IOException e) {
			}
		}
		try {
			fos = new FileOutputStream(PROPERTY_FILE);
			prop.setProperty(key, value);
			prop.store(fos, "Update '" + key + "' value");
			prop.store(fos, "just for test");
		} catch (IOException e) {
			System.err.println("Visit " + PROPERTY_FILE + " for updating "
					+ value + " value error");
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
			}
		}
	}
}
