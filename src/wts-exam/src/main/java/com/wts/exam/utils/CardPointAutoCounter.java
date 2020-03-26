package com.wts.exam.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.farm.util.spring.BeanFactory;
import com.wts.exam.service.CardServiceInter;

/**
 * 答题卡得分自动计算(单线程，循环取出队列中得答题卡并计算得分)
 * 
 * @author macpl
 *
 */
public class CardPointAutoCounter {
	private static ExecutorService fixedThreadPool = null;
	private static final Logger log = Logger.getLogger(CardPointAutoCounter.class);
	private static Long num = new Long(0);

	public static int submitTask(final String cardId) {
		if (fixedThreadPool == null) {
			log.info("首次启动答题卡得分自动运算器!");
			fixedThreadPool = Executors.newSingleThreadExecutor();
		}
		if (fixedThreadPool.isShutdown()) {
			num = new Long(1);
		} else {
			num = num + 1;
		}
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					log.info("开始计算答题卡cardId:" + cardId);
					CardServiceInter cardServiceImpl = (CardServiceInter) BeanFactory.getBean("cardServiceImpl");
					cardServiceImpl.autoCountCardPoint(cardId);
					num--;
					log.info("完成答题卡计算cardId:" + cardId + "(剩餘等待數量" + num + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		log.info("答題卡計算等待數量:" + num);
		return num.intValue();
	}

}
