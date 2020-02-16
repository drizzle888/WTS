package com.farm.core;

/**
 * 参数服务接口
 * 
 * @author wangdong
 * @version 2014-12
 * 
 */
public interface LogService {

	/**
	 * 记录日志
	 * 
	 * @param message
	 *            信息
	 * @param loginUserId
	 *            用户id
	 * @param level
	 *            日志级别
	 * @param methodName
	 *            程序方法名称
	 * @param className
	 *            程序类名
	 * @param ip
	 *            用户IP
	 */
	public void log(String info, String loginUserId, String level,
			String methodName, String className, String ip);

}
