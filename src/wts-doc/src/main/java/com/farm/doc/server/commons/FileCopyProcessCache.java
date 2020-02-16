package com.farm.doc.server.commons;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.farm.core.time.TimeTool;

/**
 * 用来缓存文件的拷贝进度
 * 
 * @author macpl
 *
 */
public class FileCopyProcessCache {
	private static Map<String, FileCopyProcessDomain> cache = new HashMap<>();
	/**
	 * 强制清理时，超过这个时间的都清理（分）
	 */
	private static int forceClearMinuteMin = -120;
	/**
	 * 超过此数量，自动清理
	 */
	private static int autoClearSize = 100;

	/**
	 * 超过此数量，强制清理
	 */
	private static int forceClearSize = 1000;

	/**
	 * 超過此數量，緩存清空
	 */
	private static int finalClearSize = 5000;

	/**
	 * @param key
	 * @param percentage
	 */
	public static void setProcess(String key, Integer percentage) {
		cache.put(key, new FileCopyProcessDomain(new Date(), percentage));
		if (cache.size() > autoClearSize) {
			// 执行清理缓存
			claer(null);
		}
		if (cache.size() > forceClearSize) {
			// 强制清理缓存
			claer(TimeTool.getTimeDate12ForMinute(forceClearMinuteMin, new Date()));
		}
		if (cache.size() > finalClearSize) {
			// 强制清空緩存
			cache.clear();
		}
	}

	/**
	 * 清理緩存
	 * 
	 * @param maxDate
	 *            为空时只清理100%的记录
	 */
	public static void claer(Date minDate) {
		Set<String> unKeys = new HashSet<>();
		for (Entry<String, FileCopyProcessDomain> node : cache.entrySet()) {
			if (node.getValue().getPercent() >= 100) {
				// 清理完成的
				unKeys.add(node.getKey());
				continue;
			}
			if (minDate != null && (node.getValue().getCtime().compareTo(minDate) <= 0)) {
				// 清理超時的
				unKeys.add(node.getKey());
				continue;
			}
			node.getValue().getCtime();
		}
		for (String key : unKeys) {
			cache.remove(key);
		}
	}

	public static Integer getProcess(String key) {
		if (StringUtils.isBlank(key) || cache.size() <= 0) {
			return 100;
		}
		FileCopyProcessDomain node = cache.get(key);
		if (node == null) {
			return 1;
		}
		Integer process = node.getPercent();
		return process;
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 50000; i++) {
			for (int n = 0; n <= 10; n++) {
				String key = "key" + i;
				System.out.println("key=" + key);
				FileCopyProcessCache.setProcess(key, n);
				System.out.println("cache size:" + cache.size());
				System.out.println(FileCopyProcessCache.getProcess(key));
			}
		}
	}
}
