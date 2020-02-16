package com.farm.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.farm.core.auth.domain.WebMenu;

/**
 * 权限资源
 * 
 * @author MAC_alone
 * 
 */
public class FarmAction {
	private String name;
	private String password;
	private String autoLogin;
	private HttpSession httpSession;
	private HttpServletRequest httprequest;
	private HttpServletResponse httprespons;
	private List<Map<String, Object>> result;
	private List<WebMenu> menus;
	private String menuId;
	//private static final Logger log = Logger.getLogger(FarmAction.class);

//	/**
//	 * 用户登录
//	 * 
//	 * @return
//	 */
//	public void loginCommit(HttpSession session,
//			HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
//		AuthorityService authAdapter = farmService.getAuthorityService();
//		page = ViewMode.getInstance();
//		try {
//			if (!authAdapter.isLegality(name, password)) {
//				page.setError("登录失败：密码错误");
//				log.error("登录失败：密码错误");
//				return;
//			} else {
//			}
//		} catch (Exception e) {
//			page.setError(e.getMessage() + "用户验证失败");
//			log.error("登录失败：用户验证失败");
//			return;
//		}
//		try {
//			{// 登录成功
//				// 开始写入session用户信息
//				if (httpSession == null) {
//					WebUtils.setCurrentUser(authAdapter.getUserByLoginName(name));
//					WebUtils.setLoginTime(session);
//				} else {
//					WebUtils.setCurrentUser(authAdapter.getUserByLoginName(name),
//							httpSession);
//					WebUtils.setLoginTime(httpSession);
//				}
//
//				// 开始写入session用户权限
//				if (httpSession == null) {
//					WebUtils.setCurrentUserAction(authAdapter
//							.getUserAuthKeys(WebUtils.getCurrentUser(session).getId()));
//				} else {
//					WebUtils.setCurrentUserAction(
//							authAdapter.getUserAuthKeys(WebUtils.getCurrentUser(
//									httpSession).getId()), httpSession);
//				}
//				// 开始写入session用户菜单
//				if (httpSession == null) {
//					WebUtils.setCurrentUserMenu(authAdapter.getUserMenu(WebUtils.getCurrentUser(
//							session).getId()));
//				} else {
//					WebUtils.setCurrentUserMenu(authAdapter.getUserMenu(WebUtils.getCurrentUser(
//							httpSession).getId()), httpSession);
//				}
//				// 写入用户上线信息
//				OnlineUserOpInter ouop = null;
//				if (httpSession == null) {
//					ouop = OnlineUserOpImpl.getInstance(
//							WebUtils.getCurrentIp(httpRequest), name, session);
//				} else {
//					ouop = OnlineUserOpImpl.getInstance(
//							httprequest.getRemoteAddr(), name,
//							WebUtils.getSession(httpSession));
//				}
//				ouop.userLoginHandle(authAdapter.getUserByLoginName(name));
//				// 记录用户登录时间
//				authAdapter.loginHandle(WebUtils.getCurrentUser(httpSession).getId());
//			}
//			MDC.put("USERID", WebUtils.getCurrentUser(httpSession).getId());
//			log.info("登录成功");
//		} catch (Exception e) {
//			page.setError(e + e.getMessage());
//			log.error("登录失败：" + e.getMessage());
//			return;
//		}
//	}

//	/**
//	 * 获得menu
//	 * 
//	 * @return
//	 */
//	public List<WebMenu> findMenu(HttpSession session) {
//		return WebUtils.getCurrentUserMenus(session);
//	}

	// ----------------------------------------------------------------------------------

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public String getAutoLogin() {
		return autoLogin;
	}

	public HttpServletRequest getHttprequest() {
		return httprequest;
	}

	public void setHttprequest(HttpServletRequest httprequest) {
		this.httprequest = httprequest;
	}

	public HttpServletResponse getHttprespons() {
		return httprespons;
	}

	public void setHttprespons(HttpServletResponse httprespons) {
		this.httprespons = httprespons;
	}

	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}

	public List<WebMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<WebMenu> menus) {
		this.menus = menus;
	}
}
