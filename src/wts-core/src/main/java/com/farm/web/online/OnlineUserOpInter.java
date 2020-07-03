package com.farm.web.online;

import java.util.HashMap;
import java.util.Map;

import com.farm.core.sql.result.DataResult;

/**
 * 在线用户管理 非集群实现 实现该功能需要将方法： userLoginHandle()
 * userVisitHandle()加入到用户登录，和用户访问系统资源的代码中
 * 
 * @author wangdong
 * @date 2012-03-01
 */
public interface OnlineUserOpInter {
	/**
	 * 在线用户注册表，勿调用、勿操作
	 */
	static final Map<String, Map<String, Object>> onlineUserTable = new HashMap<String, Map<String, Object>>();

	/**
	 * 用户登陆ip记录表（最新登陆的ip为用户的当前ip）<loginname,ip>
	 */
	static final Map<String, String> onlineIpLiveTable = new HashMap<String, String>();
	
	/**
	 * 用户登陆session记录表（最新登陆的sessionid为用户的当前sessionid）<loginname,sessionId>
	 */
	static final Map<String, String> onlineSessionLiveTable = new HashMap<String, String>();
	
	
	/**
	 * 最近访问时间
	 */
	static final String key_TIME = "TIME";
	/**
	 * 用户IP
	 */
	static final String key_IP = "IP";
	/**
	 * 用户loginName
	 */
	static final String key_LNAME = "LNAME";
	/**
	 * 用户对象
	 */
	static final String key_USEROBJ = "USEROBJ";
	/**
	 * 用户登录时间
	 */
	static final String key_LOGINTIME = "LOGINTIME";

	/**
	 * 用户初次访问时间
	 */
	static final String key_STARTTIME = "STARTTIME";

	/**
	 * 登录时长
	 */
	static final String key_VISITTIME = "VISITTIME";
	/**
	 * 在线用户判超时时间 （分）
	 */
	static final long onlineVilaMinute = 10;
	/**
	 * 最大缓存数，超越就清空缓存
	 */
	static final long usersMaxSize = 5000;

	/**
	 * 处理在线IP：用户访问handle 判断当前用户是否在线，不在线就(从session中)注销掉用户
	 * 
	 * @param strutsSession
	 *            STRUTS的session
	 * @param ip
	 *            用户ip
	 */
	public void userVisitHandle();

	/**
	 * 查看当前在线用户
	 * 
	 * @param repetAble
	 *            是否显示重复登陆
	 * @return
	 */
	public DataResult findOnlineUser(boolean repetAble);

	/**
	 * 执行用户登陆
	 */
	public void userlogin();

	/**
	 * 执行用户注销
	 */
	public void userlogout();

	/**
	 * 判断用户是否重复登陆（指用户ip是否最新用户名的登陆ip，如果不是就是重复登陆）
	 * 
	 * @return
	 */
	public boolean isRepetLogin();

}
