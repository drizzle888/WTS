package com.farm.exread;

import java.util.Map;

public interface ReaderHandle {

	/**
	 * 处理行行数据
	 * 
	 * @param node 
	 */
	public void handle(Map<String, Object> node);

}
