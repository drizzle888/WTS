package com.farm.exread.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


/**
 * 操作Excel表格的功能类
 */
public class ExcelReaders {
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;
	private static final Logger log = Logger.getLogger(ExcelReaders.class);
	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<Map<Integer, String>> readExcelContent(InputStream is,
			ReaderConfig config) {
		List<Map<Integer, String>> content = new ArrayList<Map<Integer, String>>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		sheet = wb.getSheetAt(config.sheetNum);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(config.startRow);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = config.startRow; i <= rowNum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			int j = config.startCol;
			Map<Integer, String> rowData = new HashMap<Integer, String>();
			boolean isData = false;
			while (j < colNum) {
				// System.out.println(j + ":" + i + ":" + rowNum);
				HSSFCell cell = row.getCell(j);
				if (cell == null) {
					rowData.put(j, null);
				} else {
					String val = getCellFormatValue(cell).trim();
					rowData.put(j, val);
					if (val != null && val.trim().length() > 0) {
						isData = true;
					}
				}
				j++;
			}
			if (isData) {
				content.add(rowData);
			}
		}
		return content;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					Double DB = new Double(cell.getNumericCellValue());
					cellvalue = String.valueOf(DB.longValue());
				}
				break;
			}
				// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

}