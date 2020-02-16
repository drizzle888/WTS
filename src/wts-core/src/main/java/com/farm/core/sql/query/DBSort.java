package com.farm.core.sql.query;

/**
 * 排序方式的封装类，配合DataQuery使用
 * 
 * @author 王东
 * @date 2012-12-30
 */
public class DBSort {
	private String sortTitleText;// 排序字段
	private String sortTypeText;// 排序类型
	private String isValidate;

	@SuppressWarnings("unused")
	private DBSort() {
	}

	/**
	 * 构造一个排序方式
	 * 
	 * @param title
	 *            排序字段
	 * @param type
	 *            排序类型ASC/DESC
	 */
	public DBSort(String title, String type) {
		DataQuerys.wipeVirus(type);
		DataQuerys.wipeVirus(title);
		sortTitleText = title;
		sortTypeText = type;
	}

	public DBSort(String title, String type, boolean isValidate) {
		if (isValidate) {
			DataQuerys.wipeVirus(type);
			DataQuerys.wipeVirus(title);
		}
		sortTitleText = title;
		sortTypeText = type;
	}

	public void setSortTitleText(String sortTitleText) {
		this.sortTitleText = sortTitleText;
	}

	/**
	 * 数据库中的字段不能够带下划线（_）否则会被转义为点(.)
	 * 
	 * @return
	 */
	public String getSortTitleText() {
		if (sortTitleText == null
				|| sortTitleText.trim().toUpperCase().equals("NULL")) {
			return null;
		}
		return sortTitleText.replace("_", ".");
	}

	public String getSortTypeText() {
		return sortTypeText;
	}

	public void setSortTypeText(String sortTypeText) {
		this.sortTypeText = sortTypeText;
	}

	public String getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}

}
