package com.farm.core.sql.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 结果集合工具类
 * 
 * @author 王东
 * 
 */
public class DataResults {
	private static final Logger log = Logger.getLogger(DataResults.class);

	/**
	 * 由setMessage方法获得一个带消息的DataResult对象
	 * 
	 * @param message消息
	 * @return DataResult对象
	 */
	public static DataResult setException(DataResult result, Exception e) {
		if (result == null) {
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
		}
		if (e != null) {
			result.setMessage(e + e.getMessage());
			log.error(result.getMessage());
		}
		return result;
	}

	/**
	 * 由一个hibernater结果集合获得一个List<Map<String, Object>>
	 * 
	 * @param names
	 *            a.id,a.subject,b.rname,a.visit,COUNT(a.id) AS num,
	 * @param resulresult
	 *            List<Object[]>
	 * @return
	 */
	public static List<Map<String, Object>> getMaps(String names,
			List<Object[]> t) {

		if (names == null || t == null) {
			throw new IllegalArgumentException("参数异常！");
		}
		names = names.replace("，", ",").toUpperCase();
		String[] nameArray = names.split(",");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Iterator<Object[]> iterator = t.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			Map<String, Object> mapresult = new HashMap<String, Object>();
			// valueLength:结果记录字段值数量
			int valueLength = objects.length;
			if (nameArray.length < valueLength) {
				valueLength = nameArray.length;
			}
			try {
				for (int i = 0; i < valueLength; i++) {
					Object value = objects[i];
					String key = null;
					if (names.trim().equals("*")) {
						key = String.valueOf(i);
					} else {
						try {
							key = nameArray[i].trim();
							if (key.indexOf(" AS ") >= 0) {
								key = key
										.substring(key.trim().indexOf(" AS ") + 3);
							}
							key = key.replace(".", "_").trim();
						} catch (Exception e) {
							log.error("参数填充错误！（key和value数量可能不匹配）");
						}
					}
					mapresult.put(key, value);
				}
			} catch (Exception e) {
				log.error("参数填充错误！（key和value数量可能不匹配）");
			}
			list.add(mapresult);
		}
		return list;
	}

	/**
	 * 由字段名序列获得结构数据
	 * 
	 * @param names
	 *            如name,type,ctime
	 * @return
	 */
	public static List<String> getTitles(String names) {
		List<String> list = new ArrayList<String>();
		if (names == null) {
			throw new IllegalArgumentException("参数异常！");
		}
		names = names.replace("，", ",").toUpperCase();
		String[] nameArray = names.split(",");
		for (String name : nameArray) {
			String key = "";
			key = name.trim();
			if (key.indexOf(" AS ") >= 0) {
				key = key.substring(key.trim().indexOf(" AS ") + 3);
			}
			key = key.replace(".", "_").trim();
			list.add(key);
		}
		return list;
	}

	/**
	 * 通过字符串生成一个和List<Map<String, Object>>相对应的key
	 * 
	 * @param key
	 *            如a.asdf 或 ass as name
	 * @return 如A_ASDF或NAME
	 */
	public static String getMapKey(String key) {
		key = key.trim();
		if (key.indexOf(" AS ") >= 0) {
			key = key.substring(key.trim().indexOf(" AS ") + 3);
		}
		key = key.replace(".", "_").trim();
		return key.toUpperCase();
	}

	/**
	 * 遍历打印Map集合（list）
	 * 
	 * @param listmap
	 */
	public static void printMaps(List<Map<String, Object>> listmap) {
		for (Iterator<Map<String, Object>> iterator = listmap.iterator(); iterator
				.hasNext();) {
			Map<String, Object> name = (Map<String, Object>) iterator.next();
			Set<String> keyset = name.keySet();
			for (Iterator<String> iterator2 = keyset.iterator(); iterator2
					.hasNext();) {
				String name2 = (String) iterator2.next();
				System.out.println(name2 + ":" + name.get(name2));
			}
			System.out.println("------------------------------------------");
		}
	}
}
