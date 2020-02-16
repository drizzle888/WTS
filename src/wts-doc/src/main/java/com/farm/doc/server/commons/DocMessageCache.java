package com.farm.doc.server.commons;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 缓存工具，比如记录用户对一个评论点赞后，再次点赞会返回false表示用户之前已经点过赞了
 * 
 */
public class DocMessageCache {

	private Queue<String> queue = new LinkedList<>();
	private static final int maxNum = 10000;
	private static final DocMessageCache cache = new DocMessageCache();

	public static DocMessageCache getInstance() {
		return cache;
	}

	public synchronized boolean add(String userId, String docId, String docMessageId) {
		String key = userId + docId + docMessageId;

		if (queue.contains(key)) {
			return false;
		}

		if (queue.size() >= maxNum) {
			queue.poll();
		}

		queue.add(key);
		return true;
	}

	public synchronized void remove(String userId, String docId, String docMessageId) {
		String key = userId + docId + docMessageId;
		queue.remove(key);
	}
}
