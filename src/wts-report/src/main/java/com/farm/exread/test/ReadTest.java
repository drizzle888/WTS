package com.farm.exread.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import com.farm.exread.ExcelReader;
import com.farm.exread.ReaderHandle;
import com.farm.exread.service.ExcelReaderImpl;
import com.farm.exread.service.ReaderConfig;
import com.farm.exread.service.ReaderConfig.ColumnType;

public class ReadTest {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		InputStream stream = new FileInputStream(
				new File(
						"E:\\workspace\\EKP_WORK\\hrmis\\easyXls\\com\\farm\\xls\\test\\excel.xls"));
		ExcelReader reader = ExcelReaderImpl.getInstance(ReaderConfig
				.newInstance(0, 2, 0).addColumn(0, "name", ColumnType.STRING),
				stream);
		reader.read(new ReaderHandle() {
			@Override
			public void handle(Map<String, Object> node) {
				System.out.println(node.get("name"));
			}
		});
	}
}
