package com.farm.exread.domain;

import com.farm.exread.service.ReaderConfig.ColumnType;

public class ColumnConfig {
	private int num;
	private String key;
	private ColumnType columnType;

	public ColumnConfig(int num, String key, ColumnType columnType) {
		this.num = num;
		this.key = key;
		this.columnType = columnType;
	}

	public ColumnConfig(int num, ColumnType columnType) {
		this.num = num;
		this.key = Integer.toString(num);
		this.columnType = columnType;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
}
