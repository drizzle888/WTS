package com.farm.util.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.farm.core.time.TimeTool;

/**
 * 计算访问热度
 * 
 * @author Administrator
 * 
 */
public class FarmproHotnum {
	static final Logger log = Logger.getLogger(FarmproHotnum.class);

	/**
	 * 计算文章热度
	 * 
	 * @param date12最后一次访问时间yyyyMMddHHmm
	 * @param hotNum
	 *            上次热度
	 * @param visitNum
	 *            新增访问量（一般为1）
	 * @param hotWeight
	 *            冷却系数（一般为19，24小时从100衰减到1,越大衰减的越快）
	 * @return
	 */
	public static int getHotnum(String date12, Integer hotNum,
			Integer visitNum, int hotWeight) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		int minuteMinus = 0;
		try {
			minuteMinus = TimeTool.countMinuteMinus(sdf.parse(date12.substring(
					0, 12)), new Date());
		} catch (ParseException e) {
			log.error(e + e.getMessage());
		}
		// 本期得分 = 上一期得分 × exp(-(冷却系数) × 间隔的小时数) + 本期本期票数
		return Integer.valueOf(String.valueOf(Math.round(hotNum
				* Math.exp(-((float) hotWeight / 100) * minuteMinus / 60))))
				+ visitNum * 10;
	}

}
