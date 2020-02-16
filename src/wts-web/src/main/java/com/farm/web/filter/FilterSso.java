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
import com.farm.authority.service.UserServiceInter;
import com.farm.core.ParameterService;
import com.farm.core.auth.util.Urls;
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
	private static final Logger log = Logger.getLogger(FilterSso.class);

	private static UserServiceInter userServiceImpl;
	private static OrganizationServiceInter OrganizationServiceImpl;

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
		// 獲得用戶，判斷本地是否有用戶,如果本地有用戶就更新本地用戶,如果沒有就創建用戶並更新全部組織機構
		User user = getRemoteUser(ssoUrlBase + "/sso/finduser.do", loginName);
		if (userServiceImpl.syncRemoteUser(user)) {
			// TODO 再同步全部組織機構到本地
			OrganizationServiceImpl.syncRemotOrgs(getRemoteOrgs(ssoUrlBase + "/sso/findorgs.do"));
			// TODO 綁定用戶和組織機構
			userServiceImpl.setUserOrganization(user.getId(),
					getRemoteUserOrgid(ssoUrlBase + "/sso/finduserorgid.do", loginName), user);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		{// 是否可以直接访问,處理非必须登陆的请求// 判断是否需要用户权限认证
			String path = httpRequest.getContextPath();
			String requestUrl = ((HttpServletRequest) request).getRequestURL().toString();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			ParameterService parameterService = FarmParameterService.getInstance();
			String key = null;
			String formatUrl = Urls.formatUrl(requestUrl, basePath);
			key = Urls.getActionKey(formatUrl);
			String subKey = Urls.getActionSubKey(key);
			String prefix = parameterService.getParameter("config.url.free.path.prefix");
			if (prefix != null && subKey.indexOf("/" + prefix) == 0) {
				chain.doFilter(request, response);
				return;
			}
		}
		boolean isSsoAble = FarmParameterService.getInstance().getParameterBoolean("config.sso.state");
		String ssoUrlBase = FarmParameterService.getInstance().getParameter("config.sso.url");
		if (!isSsoAble) {
			// 如果没有开启单点登陆则直接跳过过滤器
			chain.doFilter(request, response);
			return;
		} else {
			log.info("the sso able!");
		}
		String loginName = getRemoteLoginName(ssoUrlBase + "/sso/user.do", httpRequest.getSession().getId());
		if (loginName != null) {
			// 如果登陆过，就获取远端登陆信息
			if (WebUtils.getCurrentUser(httpRequest.getSession()) == null
					|| !WebUtils.getCurrentUser(httpRequest.getSession()).getLoginname().equals(loginName)) {
				// 远程信息和本地不符的更新本地信息
				syncUserAndOrgHandle(loginName, ssoUrlBase);
				FarmAuthorityService.loginIntoSession(httpRequest.getSession(), httpRequest.getRemoteAddr(), loginName,
						"单点登陆");
			}
			chain.doFilter(request, response);
			return;
		} else {
			String requestUrl = httpRequest.getRequestURL().toString();
			if (StringUtils.isNotBlank(httpRequest.getQueryString())) {
				String params = httpRequest.getQueryString();
				params = params.replaceAll("&amp;", "&1a1m1p;");
				requestUrl = requestUrl + "?" + params;
			}
			requestUrl = URLEncoder.encode(requestUrl, "utf-8");
			// 如果启用过滤器则，直接跳转到单点登陆的登陆页面
			String loginUrl = ssoUrlBase + "/sso/login.do?backurl=" + requestUrl + "&clientid="
					+ httpRequest.getSession().getId();
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(loginUrl);
			return;
		}
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
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 为啥注释掉:因为从页面取回得来源URL中没有带参数，会包id不存在得异常
		// String requestUrl = request.getHeader("referer");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		String requestUrl = WebUtils.getDefaultIndexPage(FarmParameterService.getInstance());
		requestUrl = URLEncoder.encode(basePath + requestUrl, "utf-8");
		String ssoUrlBase = FarmParameterService.getInstance().getParameter("config.sso.url");
		String loginUrl = ssoUrlBase + "/sso/logout.do?backurl=" + requestUrl + "&clientid="
				+ httpRequest.getSession().getId();
		return loginUrl;
	}

	/**
	 * 是否啓用單點登陸
	 * 
	 * @return
	 */
	public static boolean isSsoAble() {
		return FarmParameterService.getInstance().getParameterBoolean("config.sso.state");
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
	 * 獲得遠程組織機構
	 * 
	 * @param url
	 * @return
	 */
	private List<Organization> getRemoteOrgs(String url) {
		Map<String, String> map = new HashMap<>();
		JSONObject json = HttpUtils.httpPost(url, map);
		if (json != null && !json.isNull("orgs")) {
			List<Organization> orgs = new ArrayList<>();
			JSONArray array = json.getJSONArray("orgs");
			for (int i = 0; i < array.length(); i++) {
				JSONObject orgjson = array.getJSONObject(i);
				Organization org = new Organization();
				if (!orgjson.isNull("appid")) {
					org.setAppid(orgjson.getString("appid"));
				}
				if (!orgjson.isNull("comments")) {
					org.setComments(orgjson.getString("comments"));
				}
				if (!orgjson.isNull("ctime")) {
					org.setCtime(orgjson.getString("ctime"));
				}
				if (!orgjson.isNull("cuser")) {
					org.setCuser(orgjson.getString("cuser"));
				}
				if (!orgjson.isNull("id")) {
					org.setId(orgjson.getString("id"));
				}
				if (!orgjson.isNull("muser")) {
					org.setMuser(orgjson.getString("muser"));
				}
				if (!orgjson.isNull("name")) {
					org.setName(orgjson.getString("name"));
				}
				if (!orgjson.isNull("parentid")) {
					org.setParentid(orgjson.getString("parentid"));
				}
				if (!orgjson.isNull("sort")) {
					org.setSort(orgjson.getInt("sort"));
				}
				if (!orgjson.isNull("state")) {
					org.setState(orgjson.getString("state"));
				}
				if (!orgjson.isNull("treecode")) {
					org.setTreecode(orgjson.getString("treecode"));
				}
				if (!orgjson.isNull("type")) {
					org.setType(orgjson.getString("type"));
				}
				if (!orgjson.isNull("utime")) {
					org.setUtime(orgjson.getString("utime"));
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
	 * @return
	 */
	private User getRemoteUser(String url, String loginname) {
		Map<String, String> map = new HashMap<>();
		map.put("loginname", loginname);
		JSONObject json = HttpUtils.httpPost(url, map);
		if (json != null && !json.isNull("user")) {
			JSONObject userJson = json.getJSONObject("user");
			User remoteuser = new User();
			if (!userJson.isNull("comments")) {
				remoteuser.setComments(userJson.getString("comments"));
			}
			if (!userJson.isNull("ctime")) {
				remoteuser.setCtime(userJson.getString("ctime"));
			}
			if (!userJson.isNull("cuser")) {
				remoteuser.setCuser(userJson.getString("cuser"));
			}
			if (!userJson.isNull("id")) {
				remoteuser.setId(userJson.getString("id"));
			}
			if (!userJson.isNull("imgid")) {
				remoteuser.setImgid(userJson.getString("imgid"));
			}
			// if(!userJson.isNull("comments")){remoteuser.setIp(userJson.getString("ip"));}
			if (!userJson.isNull("loginname")) {
				remoteuser.setLoginname(userJson.getString("loginname"));
			}
			if (!userJson.isNull("logintime")) {
				remoteuser.setLogintime(userJson.getString("logintime"));
			}
			if (!userJson.isNull("muser")) {
				remoteuser.setMuser(userJson.getString("muser"));
			}
			if (!userJson.isNull("name")) {
				remoteuser.setName(userJson.getString("name"));
			}
			if (!userJson.isNull("password")) {
				remoteuser.setPassword(userJson.getString("password"));
			}
			if (!userJson.isNull("state")) {
				remoteuser.setState(userJson.getString("state"));
			}
			if (!userJson.isNull("type")) {
				remoteuser.setType(userJson.getString("type"));
			}
			if (!userJson.isNull("utime")) {
				remoteuser.setUtime(userJson.getString("utime"));
			}
			return remoteuser;
		} else {
			return null;
		}
	}

	/**
	 * 獲得遠程用戶的組織機構id
	 * 
	 * @param url
	 * @param loginname
	 * @return
	 */
	private String getRemoteUserOrgid(String url, String loginname) {
		Map<String, String> map = new HashMap<>();
		map.put("loginname", loginname);
		JSONObject json = HttpUtils.httpPost(url, map);
		if (json == null || json.isNull("orgid")) {
			return null;
		} else {
			String userObj = json.getString("orgid");
			return userObj;
		}
	}

	/**
	 * 获得单点登陆系统的登陆名
	 * 
	 * @return
	 */
	private String getRemoteLoginName(String url, String clientid) {
		Map<String, String> map = new HashMap<>();
		map.put("clientid", clientid);
		JSONObject json = HttpUtils.httpPost(url, map);
		if (json == null || json.isNull("user")) {
			return null;
		} else {
			JSONObject userObj = json.getJSONObject("user");
			String loginname = userObj.getString("loginname");
			return loginname;
		}
	}
}