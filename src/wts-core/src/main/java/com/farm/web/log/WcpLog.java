package com.farm.web.log;

import org.apache.log4j.Logger;

/**
 * 记录数据库日志
 * 
 * @author wangdong
 *
 */
public class WcpLog {
	private final static Logger log = Logger.getLogger(WcpLog.class);

	public static void info(String message, String username, String userid) {
		log.info(message + (username == null ? "" : (" / 操作用户:" + username))
				+ (userid == null ? "" : ("[" + userid + "]")));
	}

	public static void debug(String message, String username, String userid) {
		log.debug(message + (username == null ? "" : (" / 操作用户:" + username))
				+ (userid == null ? "" : ("[" + userid + "]")));
	}

	public static void warn(String message, String username, String userid) {
		log.warn(message + (username == null ? "" : (" / 操作用户:" + username))
				+ (userid == null ? "" : ("[" + userid + "]")));
	}

	public static void error(String message, String username, String userid) {
		log.error(message + (username == null ? "" : (" / 操作用户:" + username))
				+ (userid == null ? "" : ("[" + userid + "]")));
	}
}
