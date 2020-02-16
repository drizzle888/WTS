package com.farm.exread.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farm.exread.ExcelReader;
import com.farm.exread.ReaderHandle;
import com.farm.exread.domain.ColumnConfig;
import com.farm.exread.service.ReaderConfig.ColumnType;

public class ExcelReaderImpl implements ExcelReader {
	private ReaderConfig config;
	private InputStream excelStream;

	public static ExcelReader getInstance(ReaderConfig config,
			InputStream excelStream) {
		ExcelReaderImpl obj = new ExcelReaderImpl();
		obj.config = config;
		obj.excelStream = excelStream;
		return obj;
	}

	@Override
	public void read(ReaderHandle handle) {
		ExcelReaders readers = new ExcelReaders();
		List<Map<Integer, String>> list = readers.readExcelContent(excelStream,
				config);
		for (Map<Integer, String> node : list) {
			Map<String, Object> row = new HashMap<String, Object>();
			for (ColumnConfig column : config.columns) {
				String val = node.get(column.getNum());
				if (column.getColumnType().equals(ColumnType.INT)) {
					row.put(column.getKey(), Integer.valueOf(val));
				}
				if (column.getColumnType().equals(ColumnType.STRING)) {
					row.put(column.getKey(), val);
				}
			}
			handle.handle(row);
		}
	}
}
