package com.farm.exread;

/**
 * excel读取工具
 * 
 * @author Administrator
 * 
 */
public interface ExcelReader {
	/**
	 * 读取excel
	 * 
	 * @param handle
	 *            处理数据的回调方法
	 */
	public void read(ReaderHandle handle);
}