package com.farm.core.auth.util;


import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * DES算法帮组类 提供DES算法帮助 包括生成密钥，加密和解密
 * 
 */
public class DesUtil {

	/** 运算法则标示 */
	private static final String ALGORITHM = "DES";

	/** 是否支持.net加密方式 */
	private static final boolean IS_NET_KEY = false;

	/** 私有秘钥 */
	private static String PRIVATE_KEY;

	private static final String ENCODING = "UTF-8";

	/** 算法提供对象 */
	private Provider provider = null;
	private static DesUtil obj = null;

	public static DesUtil getInstance(String private_key) {
		if (obj == null) {
			obj = new DesUtil();
			PRIVATE_KEY = private_key;
		}
		return obj;
	}

	/**
	 * 构造方法，初始化算法提供对象
	 * 
	 */
	private DesUtil() {
		this.provider = new BouncyCastleProvider();
	}

	/**
	 * 指定算法提供器
	 * 
	 * @param provider
	 *            算法提供类对象
	 */
	public DesUtil(Provider provider) {
		this.provider = provider;
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] src, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行加密");
		}
		if (src == null) {
			throw new IllegalArgumentException("不能对空数据进行加密");
		}
		try {
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM, this.provider);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 生成密钥
	 * 
	 * @return 生成后的密钥对象
	 */
	public SecretKey generateKey() {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(ALGORITHM, this.provider);

			// 修复SecureRandom在linux下使用相同的种子生成的随机数不同的问题
			// keyGenerator.init(new SecureRandom(privateKey.getBytes()));
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(PRIVATE_KEY.getBytes());
			keyGenerator.init(sr);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据，但src或key为null时，抛异常
	 */
	public byte[] encrypt(String src, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行加密");
		}
		if (src == null) {
			throw new IllegalArgumentException("不能对空数据进行加密");
		}
		return encrypt(src.getBytes(), key);
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的字符串
	 */
	public String encryptString(String src, Key key) {
		return encryptString(src, key, null);
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @return 返回加密后的字符串，如果IS_NET_KEY配置为true，返回.NET方式加密后数据。
	 */
	public String encryptString(String src) {
		return encryptString(src, IS_NET_KEY);
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            源数据
	 * @param isDotNet
	 *            不是Net加密方式，boolean类型
	 * @return 返回加密后数据
	 */
	public String encryptString(String src, boolean isDotNet) {
		if (!isDotNet) {
			return encryptString(src, this.generateKey(), null);
		} else {
			return DesNetHelper.encryptString(src);
		}
	}

	/**
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @param encoding
	 *            字符集（如：UTF-8）
	 * @return 返回加密后数据
	 */
	public String encryptString(String src, Key key, String encoding) {
		// 调用DES进行加密
		byte[] passbyties = encrypt(src, key);
		// 进行Base64编码
		String passText = Base64.encodeBase64String(passbyties);
		// 转码操作
		if (null != encoding && !"".equals(encoding)) {
			try {
				passText = new String(passText.getBytes(), encoding);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return passText;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] src, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行解密");
		}
		if (src == null) {
			throw new IllegalArgumentException("不能对空数据进行解密");
		}
		try {
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM, this.provider);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 现在，获取数据并解密
			// 正式执行解密操作
			return cipher.doFinal(src);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 *            被解密数据
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后数据
	 * @throws Exception
	 */
	public byte[] decrypt(String data, Key key) {
		if (key == null) {
			throw new IllegalArgumentException("不能使用空密钥对数据进行解密");
		}
		if (data == null) {
			throw new IllegalArgumentException("不能对空数据进行解密");
		}
		return decrypt(data.getBytes(), key);
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 *            密码数据
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @param encoding
	 *            字符集（如：UTF-8）
	 * @return 返回解密后数据
	 * @throws Exception
	 */
	public String decryptString(String data, Key key, String encoding) {
		// 进行转码
		if (null != encoding && !"".equals(encoding)) {
			try {
				data = new String(data.getBytes(), encoding);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		// 进行Base64编码解密
		byte[] btdate = Base64.decodeBase64(data);
		// 进行DES解密
		byte[] textPass = decrypt(btdate, key);
		return new String(textPass);
	}

	/**
	 * 密码解密
	 * 
	 * @param data
	 *            密码数据
	 * @return key 密钥，长度必须是8的倍数
	 * @return 返回解密后数据
	 * @throws Exception
	 */
	public String decryptString(String data, Key key) {
		return decryptString(data, key, null);
	}

	public String decryptString(String data) {
		if (!IS_NET_KEY) {
			return decryptString(data, this.generateKey());
		} else {
			return DesNetHelper.decryptString(data);
		}
	}

	/***
	 * 用于支持.Net的des算法
	 * 
	 */
	public static class DesNetHelper {

		/**
		 * 密码转Hex进制输出
		 * 
		 * @param b
		 *            密码
		 * @return 返回转换后的编码字符
		 */
		public static String toHexString(byte b[]) {
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				String plainText = Integer.toHexString(0xff & b[i]);
				if (plainText.length() < 2)
					plainText = "0" + plainText;
				hexString.append(plainText);
			}
			return hexString.toString();
		}

		/**
		 * 加密操作
		 * 
		 * @param message
		 *            数据源
		 * @param key
		 *            密钥
		 * @return 返回加密后的数据
		 * @throws Exception
		 */
		public static byte[] encrypt(String message, String key) throws Exception {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			return cipher.doFinal(message.getBytes(ENCODING));
		}

		/**
		 * 解密算法
		 * 
		 * @param message
		 *            密码
		 * @param key
		 *            密钥
		 * @return 返回解密后数据
		 * @throws Exception
		 */
		public static String decrypt(String message, String key) throws Exception {

			byte[] bytesrc = convertHexString(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(ENCODING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes(ENCODING));

			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		}

		/**
		 * 将Hex进制转换成字节
		 * 
		 * @param ss
		 *            Hex进制字符
		 * @return 返回转换后字节数字
		 */
		public static byte[] convertHexString(String ss) {
			byte digest[] = new byte[ss.length() / 2];
			for (int i = 0; i < digest.length; i++) {
				String byteString = ss.substring(2 * i, 2 * i + 2);
				int byteValue = Integer.parseInt(byteString, 16);
				digest[i] = (byte) byteValue;
			}

			return digest;
		}

		/**
		 * 加密
		 * 
		 * @param data
		 *            数据源
		 * @return 返回加密后的字符
		 */
		public static String encryptString(String data) {
			try {
				String jiami = java.net.URLEncoder.encode(data, ENCODING).toLowerCase();
				return toHexString(encrypt(jiami, PRIVATE_KEY)).toUpperCase();
			} catch (Exception ex) {
				throw new RuntimeException("加密[" + data + "]失败！");
			}
		}

		/**
		 * 解密
		 * 
		 * @param data
		 *            数据源
		 * @return 返回解密后数据
		 */
		public static String decryptString(String data) {
			try {
				return java.net.URLDecoder.decode(decrypt(data, PRIVATE_KEY), ENCODING);
			} catch (Exception ex) {
				throw new RuntimeException("解密[" + data + "]失败！");
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		DesUtil helper = DesUtil.getInstance("WCPDOC");
		System.out.println("加密后：" + helper.encryptString("sys222admin"));
		System.out.println("加密后：" + helper.encryptString("OBLIGATEKEY"));
		System.out.println("解密后：" +  helper.decryptString("0IY9ndJZ5Pbsv6dGK5hVQQ=="));

		/*
		 * String netPass = DesNetHelper.encryptString(src); System.out.println(
		 * "密码         ："+pass); System.out.println("NET密码："+netPass); String
		 * netTest = DesNetHelper.decryptString(netPass);
		 * 
		 * System.out.println("原密码："+test); System.out.println("NET原密码：" +
		 * netTest);
		 */
	}
}
