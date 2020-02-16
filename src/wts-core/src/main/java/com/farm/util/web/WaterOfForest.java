package com.farm.util.web;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import org.hibernate.Query;
import org.hibernate.Session;

import com.farm.core.auth.util.DesUtil;
import com.farm.core.auth.util.MD5;
import com.farm.core.time.TimeTool;
import com.farm.util.spring.HibernateSessionFactory;

public class WaterOfForest {
	private static String errorkey = "ERROR";
	private static String CODE_C = "WOTAIBUXIAOXINGLE";
	private static String PRO_TYPE = "PRO";
	private static String STA_TYPE = "STA";
	private static String ULT_TYPE = "ULT";
	private static String key = null;

	/**
	 * 生成认证密钥(专业版)
	 * 
	 * @param mkey
	 * @return
	 */
	public static String getFkey_PRO(String mkey) {
		return new MD5().getMD5ofStr(mkey + CODE_C+PRO_TYPE).substring(0, 12);
	}
	
	/**生成认证密钥(标准版)
	 * @param mkey
	 * @return
	 */
	public static String getFkey_STAND(String mkey) {
		return new MD5().getMD5ofStr(mkey + CODE_C+STA_TYPE).substring(0, 12);
	}
	
	/**生成认证密钥(旗舰版)
	 * @param mkey
	 * @return
	 */
	public static String getFkey_ULTRA(String mkey) {
		return new MD5().getMD5ofStr(mkey + CODE_C+ULT_TYPE).substring(0, 12);
	}
	/**
	 * 生成认证密钥(专业版)
	 * 
	 * @param mkey
	 *            原始密钥
	 * @param yyyymm
	 *            授权月份
	 * @return
	 */
	public static String getFkey_PRO(String mkey, String yyyy, String mms) {
		if (mms != null && !mms.isEmpty()) {
			mms = mms.replaceAll("，", ",").replaceAll(" ", ",").replaceAll("、", ",");
		}
		String key = null;
		for (String mm : mms.split(",")) {
			if (key == null) {
				key = new MD5().getMD5ofStr(mkey + CODE_C+PRO_TYPE + yyyy + mm).substring(0, 12);
			} else {
				key = key + "-" + new MD5().getMD5ofStr(mkey + CODE_C+PRO_TYPE + yyyy + mm).substring(0, 12);
			}
		}

		return key;
	}
	/**
	 * 生成认证密钥(标准版)
	 * 
	 * @param mkey
	 *            原始密钥
	 * @param yyyymm
	 *            授权月份
	 * @return
	 */
	public static String getFkey_STAND(String mkey, String yyyy, String mms) {
		if (mms != null && !mms.isEmpty()) {
			mms = mms.replaceAll("，", ",").replaceAll(" ", ",").replaceAll("、", ",");
		}
		String key = null;
		for (String mm : mms.split(",")) {
			if (key == null) {
				key = new MD5().getMD5ofStr(mkey + CODE_C+STA_TYPE + yyyy + mm).substring(0, 12);
			} else {
				key = key + "-" + new MD5().getMD5ofStr(mkey + CODE_C+STA_TYPE + yyyy + mm).substring(0, 12);
			}
		}

		return key;
	}
	/**
	 * 获得原始密钥
	 * 
	 * @return
	 */
	public static String getMKey() {
		try {
			if (key == null) {
				Enumeration<NetworkInterface> netInterfaces = null;
				// 获得所有网络接口
				netInterfaces = NetworkInterface.getNetworkInterfaces();
				StringBuffer sb = new StringBuffer();
				long keynum = 0;// 所有mac数值
				while (netInterfaces.hasMoreElements()) {
					try {
						NetworkInterface ni = netInterfaces.nextElement();
						byte[] macs = ni.getHardwareAddress();
						// 该interface不存在HardwareAddress，继续下一次循环
						if (macs == null || macs.length == 0) {
							continue;
						}
						String mac_all = null;// 单个mac数值
						for (int i = 0; i < macs.length; i++) {
							String mac = "";
							mac = Integer.toHexString(macs[i] & 0xFF);
							if (mac.length() == 1) {
								mac = '0' + mac;
							}
							if (mac_all == null) {
								mac_all = mac;
							} else {
								mac_all = mac_all + "-" + mac;
							}
							sb.append(mac + "-");
						}
						long num = 0;// 单个mac对应的数字
						for (byte nuu : mac_all.getBytes()) {
							num = num + nuu;
						}
						keynum = keynum + num;
					} catch (Exception e) {
					}
				}

				// String midlekey = new MD5().getMD5ofStr(sb.toString() +
				// "FARM");
				String midlekey = new MD5().getMD5ofStr(keynum + "FARM");
				String finalkey = midlekey.substring(0, 3) + "-" + midlekey.substring(3, 11);
				key = initMKey(finalkey);
			}
			return key;
		} catch (SocketException e) {
		}
		return errorkey;
	}
	
	/**
	 * 数据库key的处理
	 * 
	 * @param key
	 * @return
	 */
	public static String initMKey(String key) {
		// key=92D-64AB1161;
		//safekey for encode and raplace key
		String privateKey = "8240866";
		Session session = HibernateSessionFactory.getSession();
		try {
			String yyyy = TimeTool.getFormatTimeDate12(TimeTool.getTimeDate14(), "yyyyMM");
			yyyy = DesUtil.getInstance(privateKey).encryptString(yyyy);
			Query query = session.createSQLQuery("SELECT NAME FROM ALONE_DICTIONARY_ENTITY WHERE ENTITYINDEX=?")
					.setString(0, yyyy);
			@SuppressWarnings("unchecked")
			List<Object> dbkey2 = query.list();
			String dbkey = key;
			if (dbkey2.size() > 0) {
				//数据库中有机器key，读取
				dbkey = (String) dbkey2.get(0);
				dbkey=DesUtil.getInstance(privateKey).decryptString(dbkey).replace(privateKey, "-");
			} else {
				//数据库中无机器key，插入
				String id = UUID.randomUUID().toString().replaceAll("-", "").replaceAll(" ", "");
				String innerkey = key;
				innerkey = innerkey.replace("-", privateKey);
				innerkey = DesUtil.getInstance(privateKey).encryptString(innerkey);
				query = session.createSQLQuery(
						"INSERT INTO ALONE_DICTIONARY_ENTITY ( ID, CTIME,UTIME,CUSER,MUSER,STATE,NAME,ENTITYINDEX,COMMENTS,TYPE )"
								+ "  VALUES ( '" + id + "','" + TimeTool.getTimeDate12() + "','"
								+ TimeTool.getTimeDate12()
								+ "','40288b854a329988014a329a12f30002','40288b854a329988014a329a12f30002','0','"
								+ innerkey + "','" + yyyy + "','{none}','1');");
				query.executeUpdate();
			}
			return dbkey;
		} catch (Exception e) {
			return key;
		} finally {
			session.close();
		}
	}
}
