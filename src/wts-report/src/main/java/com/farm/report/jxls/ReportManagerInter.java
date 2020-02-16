package com.farm.report.jxls;

import java.util.Map;


/**
 * excel报表生成工具
 * 
 * @author 王东
 * @date 2013-7-2
 */
public interface ReportManagerInter {
	/**
	 * 输出报表文件
	 * 
	 * @param fileName
	 *            模板文件名
	 * @param parameter
	 *            数据集合
	 * @param outputStream
	 *            输出流
	 */
	public void generate(String fileName, Map<String, Object> parameter)
			throws ReportException;

	/**
	 * 获得报表文件绝对
	 * 
	 * @return
	 */
	public String getReportPath(String fileName);

}
