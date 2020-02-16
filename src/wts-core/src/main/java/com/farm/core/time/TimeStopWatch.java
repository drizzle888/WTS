package com.farm.core.time;

import org.apache.log4j.Logger;


/**
 * 计时器
 * 
 * @author lenovo
 *
 */
public class TimeStopWatch {
	private long startTime;
	private long endTime;
	static final Logger log = Logger.getLogger(TimeStopWatch.class);
	/**
	 * 开始计时
	 * 
	 * @return
	 */
	public static TimeStopWatch start() {
		TimeStopWatch tr = new TimeStopWatch();
		tr.startTime = System.currentTimeMillis();
		return tr;
	}

	/**
	 * 打印间隔时间
	 * @param showflag 打印时间时的提示字符串
	 * @return
	 */
	public TimeStopWatch endPrintInfo(String showflag) {
		endTime = System.currentTimeMillis(); // 获取结束时间
		log.info(showflag+"-程序运行时间：" + (endTime - startTime) + "ms");
		return this;
	}

	/**
	 * 获得间隔时间（单位毫秒）
	 * 
	 * @return
	 */
	public long getEndTimes() {
		endTime = System.currentTimeMillis(); // 获取结束时间
		return endTime - startTime;
	}
}
