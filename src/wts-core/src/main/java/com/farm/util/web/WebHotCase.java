package com.farm.util.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

class hotCase {
	public String key;
	public int num;

	public hotCase(String key, int num) {
		this.key = key;
		this.num = num;
	}
}

/**
 * 计算热词（如搜索关键字的统计）
 * 
 * @author Administrator
 * 
 */
public class WebHotCase {
	private final static Logger log = Logger.getLogger(WebHotCase.class);
	private static List<hotCase> HOT_CASE = new ArrayList<hotCase>();
	private static Map<String, Integer> HOT_CASE_MAP = new HashMap<String, Integer>();
	private static int maxNum = 10;
	private static int minNum = 5;
	private static int index = 0;

	/**
	 * 录入一个查询用例
	 * 
	 * @param caseStr
	 */
	public static void putCase(String caseStr) {
		if (caseStr == null || caseStr.trim().equals("") || caseStr.trim().length() > 7
				|| caseStr.trim().length() < 2) {
			// 在这个范围内认为无意义的词
			return;
		}
		caseStr = caseStr.trim();
		for (String cas : caseStr.replaceAll("，", ",").split(",")) {
			Integer num = HOT_CASE_MAP.get(cas);
			if (num == null) {
				num = 0;
			}
			index++;
			HOT_CASE_MAP.put(cas, num + 1);
			if (HOT_CASE_MAP.size() >= maxNum || index == 1 || index == 5 || index == 10 || index % 20 == 0) {
				clearHotCase();
			}
		}

	}

	/**
	 * 获得查询case
	 * 
	 * @param caseStr
	 */
	public static List<String> getCases(int num) {
		List<String> list = new ArrayList<String>();
		try {
			for (int n = 0; n < HOT_CASE.size() && n < num; n++) {
				list.add(HOT_CASE.get(n).key);
			}
		} catch (Exception e) {
			log.error("获取查询热词getCases时发生" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获得热词
	 * 
	 * @param num
	 *            热词数量
	 * @param list
	 *            置顶热词
	 * @return
	 */
	public static List<String> getCases(int num, List<String> list) {
		try {
			for (int n = 0; n < HOT_CASE.size() && n < num; n++) {
				if (!list.contains(HOT_CASE.get(n).key)) {
					list.add(HOT_CASE.get(n).key);
				}
			}
		} catch (Exception e) {
			log.error("获取查询热词getCases时发生" + e.getMessage());
		}
		return list;
	}

	/**
	 * 清理map
	 */
	private static void clearHotCase() {
		// 清空list
		HOT_CASE.clear();
		// map放入list
		for (String key : HOT_CASE_MAP.keySet()) {
			int num = HOT_CASE_MAP.get(key);
			HOT_CASE.add(new hotCase(key, num));
		}
		// list排序
		Collections.sort(HOT_CASE, new Comparator<hotCase>() {
			public int compare(hotCase o1, hotCase o2) {
				return o2.num - o1.num;
			};
		});
		// list截串
		if (HOT_CASE.size() >= minNum) {
			HOT_CASE = HOT_CASE.subList(0, minNum);
		}
		if (index >= minNum) {
			index = minNum;
		}
		// 装回map
		HOT_CASE_MAP.clear();
		for (hotCase hc : HOT_CASE) {
			int cnum = hc.num;
			// 每次清理 热度都会衰减一半
			if (cnum > 0) {
				cnum = cnum / 2;
			}
			HOT_CASE_MAP.put(hc.key, cnum);
		}
	}

	public static List<hotCase> getHOT_CASE() {
		return HOT_CASE;
	}

	/**
	 * 移除某个热词
	 * 
	 * @param key
	 *            void
	 */
	public static void remove(String key) {
		ListIterator<hotCase> iterator = HOT_CASE.listIterator();
		while (iterator.hasNext()) {
			hotCase hotCase = iterator.next();
			if (hotCase.key.equals(key)) {
				iterator.remove();
			}
		}
	}

	/**
	 * 移除某个热词
	 * 
	 * @param key
	 *            void
	 */
	public static void removeAll() {
		HOT_CASE.clear();
		HOT_CASE_MAP.clear();
		index = 0;
	}
}
