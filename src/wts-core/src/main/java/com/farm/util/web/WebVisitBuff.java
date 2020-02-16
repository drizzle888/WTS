package com.farm.util.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 控制用户访问的计数器工具，用来计算一个KEY在一定时间内是否已经被标记（如控制相同用户不重复计算文章的访问量）
 * 
 * @author Administrator
 * 
 */
public class WebVisitBuff {
	private static Map<String, WebVisitBuff> BUFF_S = new HashMap<String, WebVisitBuff>();
	private Set<String> pool = new HashSet<String>();
	private int max = 0;

	/**
	 * 获得控制计数器工具对象实例
	 * 
	 * @param domain
	 *            域关键字
	 * @param maxNum
	 *            最大存储的Key数量，超过该数量时池被重置
	 * @return
	 */
	public static WebVisitBuff getInstance(String domain, int maxNum) {
		if (BUFF_S.get(domain) == null) {
			WebVisitBuff ob = new WebVisitBuff();
			BUFF_S.put(domain, ob);
		}
		BUFF_S.get(domain).max = maxNum;
		return BUFF_S.get(domain);
	}

	/**
	 * 是否能够被访问，如果在规定时间内出现相同字符串则不能被访问
	 * 
	 * @return
	 */
	public boolean canVisite(String key) {
		if (pool.size() > max) {
			pool.clear();
		}
		if (pool.contains(key)) {
			return false;
		} else {
			pool.add(key);
			return true;
		}
	}
}
