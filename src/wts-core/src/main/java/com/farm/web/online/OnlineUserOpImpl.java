package com.farm.web.online;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.result.DataResult;
import com.farm.web.constant.FarmConstant;

/**
 * 在线用户管理 非集群实现 实现该功能需要将方法： userLoginHandle()
 * userVisitHandle()加入到用户登录，和用户访问系统资源的代码中
 * 
 * @author wangdong
 * 
 */
public class OnlineUserOpImpl implements OnlineUserOpInter {
	/**
	 * 当前用户ip
	 */
	private String ip;
	private HttpSession httpSession;
	private Map<String, Object> strutsSession;

	@Override
	public DataResult findOnlineUser() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String> removekeys = new ArrayList<>();
		for (String key : OnlineUserOpInter.onlineUserTable.keySet()) {
			// 处理时间----开始
			// 上次访问时间
			Date date = (Date) OnlineUserOpInter.onlineUserTable.get(key).get(OnlineUserOpInter.key_TIME);
			// 登录时间
			Date visitdate = (Date) OnlineUserOpInter.onlineUserTable.get(key).get(OnlineUserOpInter.key_STARTTIME);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat curentDayf = new SimpleDateFormat("yyyy-MM-dd");
			// 当前时间
			Date curentTime = new Date();
			// 当前时间
			Calendar curentC = Calendar.getInstance();
			curentC.setTime(curentTime);
			// 上次访问时间
			Calendar curentV = Calendar.getInstance();
			curentV.setTime(date);
			// 登录时间
			Calendar curentl = Calendar.getInstance();
			curentl.setTime(visitdate);
			// 相差分钟数
			long timeMillis = (curentC.getTimeInMillis() - curentV.getTimeInMillis()) / (1000 * 60);
			// 登录时长
			long visitMillis = (curentC.getTimeInMillis() - curentl.getTimeInMillis()) / (1000 * 60);
			if (timeMillis > OnlineUserOpInter.onlineVilaMinute) {
				// 超时用户判为不在线,从集合中删除
				removekeys.add(key);
				continue;
			}
			// 处理时间----结束
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(OnlineUserOpInter.key_LNAME, key);
			map.put(OnlineUserOpInter.key_VISITTIME, visitMillis);
			map.put(OnlineUserOpInter.key_TIME, sdf.format(date).replace(curentDayf.format(new Date()), "今天"));
			LoginUser user = (LoginUser) OnlineUserOpInter.onlineUserTable.get(key).get(OnlineUserOpInter.key_USEROBJ);
			map.put(OnlineUserOpInter.key_USEROBJ, user);
			if (user != null) {
				map.put("USERNAME", user.getName());
				map.put("LOGINNAME", user.getLoginname());
				map.put(OnlineUserOpInter.key_LOGINTIME,
						sdf.format(OnlineUserOpInter.onlineUserTable.get(key).get(OnlineUserOpInter.key_LOGINTIME))
								.replace(curentDayf.format(new Date()), "今天"));
			} else {
				map.put("USERNAME", "NONE");
				map.put("LOGINNAME", "NONE");
			}
			map.put(OnlineUserOpInter.key_IP, OnlineUserOpInter.onlineUserTable.get(key).get(OnlineUserOpInter.key_IP));
			map.put(OnlineUserOpInter.key_STARTTIME,
					sdf.format(OnlineUserOpInter.onlineUserTable.get(key).get(OnlineUserOpInter.key_STARTTIME))
							.replace(curentDayf.format(new Date()), "今天"));
			list.add(map);
		}
		for (String key : removekeys) {
			// 超时用户从集合中删除
			OnlineUserOpInter.onlineUserTable.remove(key);
		}
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return ((String) o1.get(OnlineUserOpInter.key_TIME))
						.compareTo((String) o2.get(OnlineUserOpInter.key_TIME));
			};
		});
		DataResult result = DataResult.getInstance(list, list.size(), 1, list.size());
		return result;
	}

	@Override
	public void userVisitHandle() {
		// 注册用户
		if (!((httpSession == null && strutsSession == null) && ip != null)) {
			Map<String, Object> userMap = null;
			if (OnlineUserOpInter.onlineUserTable.get(ip) != null) {
				// 已经访问过
				userMap = OnlineUserOpInter.onlineUserTable.get(ip);
			} else {
				// 第一次访问
				userMap = new HashMap<String, Object>();
				userMap.put(OnlineUserOpInter.key_IP, ip);
				userMap.put(OnlineUserOpInter.key_STARTTIME, new Date());
			}
			userMap.put(OnlineUserOpInter.key_TIME, new Date());
			Object user = httpSession != null ? httpSession.getAttribute(FarmConstant.SESSION_USEROBJ)
					: strutsSession.get(FarmConstant.SESSION_USEROBJ);
			if (user != null) {
				userMap.put(OnlineUserOpInter.key_LNAME, ((LoginUser) user).getLoginname());
				if (userMap.get(OnlineUserOpInter.key_USEROBJ) == null) {
					userMap.put(OnlineUserOpInter.key_LOGINTIME, new Date());
				}
				userMap.put(OnlineUserOpInter.key_USEROBJ, user);
			} else {
				userMap.put(OnlineUserOpInter.key_USEROBJ, null);
			}
			// 将用户注册在在线表中
			OnlineUserOpInter.onlineUserTable.put(ip, userMap);
		} else {
			throw new RuntimeException("参数错误");
		}
		if (OnlineUserOpInter.onlineUserTable.size() > OnlineUserOpInter.usersMaxSize) {
			OnlineUserOpInter.onlineUserTable.clear();
		}
	}

	// ----------get/set------------------------------------------------
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public Map<String, Object> getStrutsSession() {
		return strutsSession;
	}

	public void setStrutsSession(Map<String, Object> strutsSession) {
		this.strutsSession = strutsSession;
	}

	// --------------------------------------构造方法
	public static OnlineUserOpInter getInstance(String ip, HttpSession httpSession) {
		OnlineUserOpImpl obj = new OnlineUserOpImpl();
		obj.setHttpSession(httpSession);
		obj.setIp(ip);
		return obj;
	}

	public static OnlineUserOpInter getInstance(String ip, Map<String, Object> strutsSession) {
		OnlineUserOpImpl obj = new OnlineUserOpImpl();
		obj.setIp(ip);
		obj.setStrutsSession(strutsSession);
		return obj;
	}

	public static OnlineUserOpInter getInstance() {
		OnlineUserOpImpl obj = new OnlineUserOpImpl();
		return obj;
	}

}
