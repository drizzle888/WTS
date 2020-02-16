package com.farm.exread.service;

import java.util.ArrayList;
import java.util.List;

import com.farm.exread.domain.ColumnConfig;

public class ReaderConfig {
	protected int sheetNum;
	protected int startRow;
	protected int startCol;
	protected List<ColumnConfig> columns;

	public enum ColumnType {
		STRING, INT;
	}

	private ReaderConfig() {
	};

	/**
	 * @param sheetNum
	 *            读取sheet从0开始计数
	 * @param startRow
	 *            读取行从0开始计数
	 * @param startCol
	 *            读取列从0开始计数
	 * @return
	 */
	public static ReaderConfig newInstance(int sheetNum, int startRow,
			int startCol) {
		ReaderConfig config = new ReaderConfig();
		config.sheetNum = sheetNum;
		config.startRow = startRow;
		config.startCol = startCol;
		config.columns = new ArrayList<ColumnConfig>();
		return config;
	}

	/**
	 * 添加一个列定义
	 * 
	 * @param num
	 *            第几列
	 * @param key
	 *            关键字
	 * @param columnType
	 *            类型
	 * @return
	 */
	public ReaderConfig addColumn(int num, String key, ColumnType columnType) {
		columns.add(new ColumnConfig(num, key, columnType));
		return this;
	}
	/**
	 * 添加一个列定义
	 * 
	 * @param num
	 *            第几列
	 * @param key
	 *            关键字
	 * @param columnType
	 *            类型
	 * @return
	 */
	public ReaderConfig addColumn(int num, ColumnType columnType) {
		columns.add(new ColumnConfig(num, columnType));
		return this;
	}

}
