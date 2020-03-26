package com.farm.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.User;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.OutuserServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.util.Urls;
import com.farm.core.time.TimeTool;
import com.farm.parameter.FarmParameterService;
import com.farm.util.spring.BeanFactory;
import com.farm.wcp.api.util.HttpUtils;
import com.farm.web.WebUtils;

/**
 * 单点登陆系统, 0.注销"config.sso.state"和"config.sso.url"两个参数。
 * 1.在web.xml中配置filter在权限过滤前。 2.getSsoLogoutURL获得单点登陆的注销URL，在本系统注销时使用
 * 3.isSsoAble来判断是否启用sso单点登陆
 * 
 * @author WangDong
 * @date Mar 14, 2010
 * 
 */
public class FilterSso implements Filter {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(FilterSso.class);
	private static UserServiceInter userServiceImpl;
	private static OutuserServiceInter outuserServiceImpl;
	private static OrganizationServiceInter OrganizationServiceImpl;
	private static String REMOTE_USER_KEY = "REMOTE_USER_KEY";

	/**
	 * 同步用户和组织机构信息
	 * 
	 * @param loginName
	 * @param ssoUrlBase
	 */
	private void syncUserAndOrgHandle(String loginName, String ssoUrlBase) {
		if (userServiceImpl == null) {
			userServiceImpl = (UserServiceInter) BeanFactory.getBean("userServiceImpl");
		}
		if (OrganizationServiceImpl == null) {
			OrganizationServiceImpl = (OrganizationServiceInter) BeanFactory.getBean("organizationServiceImpl");
		}
		if (outuserServiceImpl == null) {
			outuserServiceImpl = (OutuserServiceInter) BeanFactory.getBean("outuserServiceImpl");
		}
		// 獲得用戶，判斷本地是否有用戶,如果本地有用戶就更新本地用戶,如果沒有就創建用戶並更新全部組織機構
		RemoteUser remoteUser = getRemoteUser(ssoUrlBase + "/api/get/user.do", loginName);
		User localuser = userServiceImpl.syncRemoteUser(remoteUser.getUser());
		if (StringUtils.isNotBlank(remoteUser.getOrgid())
				&& OrganizationServiceImpl.getOrganizationByAppid(remoteUser.getOrgid()) == null) {
			// 再同步全部組織機構到本地
			OrganizationServiceImpl.syncRemotOrgs(getRemoteOrgs(ssoUrlBase + "/api/get/organization.do"));
		}
		if (StringUtils.isNotBlank(remoteUser.getOrgid())) {
			// 更新用户组织机构
			Organization localOrg = OrganizationServiceImpl.getOrganizationByAppid(remoteUser.getOrgid());
			if (localOrg != null) {
				userServiceImpl.setUserOrganization(localuser.getId(), localOrg.getId(), localuser);
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 判断是否url中有登陆授权码，如果有就从服务器端获取用户信息
		// 授权码 REMOTE_USER_KEY
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		boolean isSsoAble = FarmParameterService.getInstance().getParameterBoolean("config.sso.state");
		if (!isSsoAble) {
			// 未启用单点登陆
			chain.doFilter(request, response);
			return;
		}
		String ssoUrlBase = FarmParameterService.getInstance().getParameter("config.sso.url");
		String remoteUserKey = request.getParameter(REMOTE_USER_KEY);
		if (StringUtils.isBlank(remoteUserKey)) {
			// 未授权远程用户
			chain.doFilter(request, response);
			return;
		}
		String remoteLogiNname = null;
		// 如果有带来用户注册信息而且启用了远程单点登陆
		{
			// 抓取远程用户
			remoteLogiNname = getRemoteUserByUserKey(remoteUserKey, ssoUrlBase + "/api/get/login.do");
		}
		if (StringUtils.isNotBlank(remoteLogiNname)) {
			// 远程信息和本地不符的更新本地信息
			syncUserAndOrgHandle(remoteLogiNname, ssoUrlBase);
			LoginUser user = outuserServiceImpl.getUserByAccountId(remoteLogiNname, null, null);
			FarmAuthorityService.loginIntoSession(httpRequest.getSession(), httpRequest.getRemoteAddr(),
					user.getLoginname(), "单点登陆");
		} else {
			// 如果注冊码失效就访问默认页面
			log.warn("the remoteUserKey is error:" + remoteUserKey);
			((HttpServletResponse) response).sendRedirect(Urls.getBaseUrl(request)
					+ FarmParameterService.getInstance().getParameter("config.index.defaultpage"));
			return;
		}
		chain.doFilter(request, response);
		return;
	}

	/**
	 * 获得单点登陆的注销url
	 * 
	 * @param session
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSsoLogoutURL(HttpServletRequest request) throws UnsupportedEncodingException {
		String ssoUrlBase = FarmParameterService.getInstance().getParameter("config.sso.url");
		String loginUrl = ssoUrlBase + "/sso/logout.do?backurl=" + getCurrentBackUrl(request);
		return loginUrl;
	}

	/**
	 * 活得單點登陆后的返回URL
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteLoginUrl(HttpServletRequest request) {
		String loginUrl = FarmParameterService.getInstance().getParameter("config.sso.url") + "/sso/login.do?backurl="
				+ getCurrentBackUrl(request);
		return loginUrl;
	}

	// ------------------------------内部方法，不需要更改-----------------------------------------------------------------
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// NONE
	}

	@Override
	public void destroy() {
		// NONE
	}

	/**
	 * 是否啓用單點登陸
	 * 
	 * @return
	 */
	public static boolean isSsoAble() {
		return FarmParameterService.getInstance().getParameterBoolean("config.sso.state");
	}

	/**
	 * 获得登陆或登出后的返回地址
	 * 
	 * @param request
	 * @return
	 */
	private static String getCurrentBackUrl(HttpServletRequest request) {
		String backurl = null;
		if (request.getMethod().equals("GET") && !isLoginPage(request)) {
			backurl = request.getRequestURL().toString() + "?" + Urls.getUrlParameters(request);
		} else {
			String path = ((HttpServletRequest) request).getContextPath();
			backurl = request.getScheme() + "://" + request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + path + "/"
					+ WebUtils.getDefaultIndexPage(FarmParameterService.getInstance());
		}
		try {
			backurl = URLEncoder.encode(backurl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return backurl;
	}

	/**
	 * 當前頁面是否登錄頁面
	 * 
	 * @param request
	 * @return
	 */
	private static boolean isLoginPage(HttpServletRequest request) {
		List<String> loginPagekeys = new ArrayList<>();
		loginPagekeys.add("login/webPage");
		loginPagekeys.add("login/webout");
		loginPagekeys.add("login/out");
		for (String index : loginPagekeys) {
			if (request.getRequestURL().toString().indexOf(index) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查接口结果
	 * 
	 * @param json
	 */
	private static void checkRomoteBackState(JSONObject json) {
		if (json.getInt("STATE") == 1) {
			throw new RuntimeException(json.getString("MESSAGE"));
		}
	}

	/**
	 * 獲得全部遠程組織機構
	 * 
	 * @param url
	 * @return
	 */
	public static List<Organization> getRemoteOrgs(String url) {
		Map<String, String> map = new HashMap<>();
		map.put("secret", FarmParameterService.getInstance().getParameter("config.sso.secret"));
		map.put("operatorLoginname", FarmParameterService.getInstance().getParameter("config.sso.ologinname"));
		map.put("operatorPassword", FarmParameterService.getInstance().getParameter("config.sso.opassword"));
		JSONObject json = HttpUtils.httpPost(url, map);
		checkRomoteBackState(json);
		int listsize = ((int) ((JSONObject) json.get("DATA")).get("totalsize"));
		if (listsize > 0) {
			List<Organization> orgs = new ArrayList<>();
			JSONArray array = (JSONArray) ((JSONObject) json.get("DATA")).get("list");
			for (int i = 0; i < array.length(); i++) {
				JSONObject orgjson = array.getJSONObject(i);
				Organization org = new Organization();
				org.setCuser("NONE");
				org.setMuser("NONE");
				org.setTreecode("NONE");
				org.setCtime(TimeTool.getTimeDate14());
				org.setUtime(TimeTool.getTimeDate14());
				if (!orgjson.isNull("APPID")) {
					org.setAppid(orgjson.getString("APPID"));
				}
				if (!orgjson.isNull("ID")) {
					org.setId(orgjson.getString("ID"));
				}
				if (!orgjson.isNull("NAME")) {
					org.setName(orgjson.getString("NAME"));
				}
				if (!orgjson.isNull("PARENTID")) {
					org.setParentid(orgjson.getString("PARENTID"));
				}
				if (!orgjson.isNull("SORT")) {
					org.setSort(orgjson.getInt("SORT"));
				}
				if (!orgjson.isNull("STATE")) {
					org.setState(orgjson.getString("STATE"));
				}
				orgs.add(org);
			}
			return orgs;
		} else {
			return null;
		}
	}

	/**
	 * 獲得遠程用戶
	 * 
	 * @param url
	 * @param loginname
	 * @return [User,orgid]
	 */
	private RemoteUser getRemoteUser(String url, String loginname) {
		Map<String, String> map = new HashMap<>();
		map.put("loginname", loginname);
		map.put("secret", FarmParameterService.getInstance().getParameter("config.sso.secret"));
		map.put("operatorLoginname", FarmParameterService.getInstance().getParameter("config.sso.ologinname"));
		map.put("operatorPassword", FarmParameterService.getInstance().getParameter("config.sso.opassword"));
		JSONObject json = HttpUtils.httpPost(url, map);
		checkRomoteBackState(json);
		int listsize = ((int) ((JSONObject) json.get("DATA")).get("totalsize"));
		if (listsize > 0) {
			JSONArray array = (JSONArray) ((JSONObject) json.get("DATA")).get("list");
			JSONObject userJson = (JSONObject) array.get(0);
			User user = new User();
			if (!userJson.isNull("COMMENTS")) {
				user.setComments(userJson.getString("COMMENTS"));
			}
			if (!userJson.isNull("ID")) {
				user.setId(userJson.getString("ID"));
			}
			if (!userJson.isNull("LOGINNAME")) {
				user.setLoginname(userJson.getString("LOGINNAME"));
			}
			if (!userJson.isNull("NAME")) {
				user.setName(userJson.getString("NAME"));
			}
			if (!userJson.isNull("STATE")) {
				user.setState(userJson.getString("STATE"));
			}
			if (!userJson.isNull("TYPE")) {
				user.setType(userJson.getString("TYPE"));
			}
			user.setPassword("NONE");
			user.setMuser("NONE");
			user.setCtime(TimeTool.getTimeDate14());
			user.setCuser("NONE");
			user.setUtime(TimeTool.getTimeDate14());
			RemoteUser remoteUser = new RemoteUser();
			remoteUser.setUser(user);
			if (!userJson.isNull("ORGANIZATIONID")) {
				remoteUser.setOrgid(userJson.getString("ORGANIZATIONID"));
			}
			return remoteUser;
		} else {
			return null;
		}
	}

	// 通过注册码抓取远程用户的登陆名
	private static String getRemoteUserByUserKey(String remoteUserKey, String apiUrl) {
		Map<String, String> map = new HashMap<>();
		map.put("secret", FarmParameterService.getInstance().getParameter("config.sso.secret"));
		map.put("certificate", remoteUserKey);
		JSONObject json = HttpUtils.httpPost(apiUrl, map);
		checkRomoteBackState(json);
		if (json.isNull("LOGINNAME")) {
			return null;
		} else {
			return json.getString("LOGINNAME");
		}
	}

	/**
	 * 远程用户
	 * 
	 * @author macpl
	 *
	 */
	class RemoteUser {
		private User user;
		private String orgid;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getOrgid() {
			return orgid;
		}

		public void setOrgid(String orgid) {
			this.orgid = orgid;
		}
	}

}