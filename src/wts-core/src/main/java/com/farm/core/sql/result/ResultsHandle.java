package com.farm.core.sql.result;

import java.util.Map;

/**
 * 结果集合的迭代接口
 * 
 * @author Administrator
 * 
 */
public interface ResultsHandle {
	/**
	 * 结果集合中的行
	 * 
	 * @param row
	 */
	public void handle(Map<String, Object> row);
}
