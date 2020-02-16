package com.farm.lucene.common;

import org.apache.lucene.document.Document;

public interface ScoreDocFilterInter {
	/**过滤结果集合
	 * @param document
	 * @return
	 */
	public boolean doScan(Document document);
}
