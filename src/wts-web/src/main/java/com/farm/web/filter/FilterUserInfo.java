package com.farm.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.AuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;
import com.farm.core.auth.util.Urls;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.farm.web.constant.FarmConstant;

/**
 * 判断用户信息是否完善，如果不完善就跳转到用户信息修改页面
 * 
 * @author WangDong
 * @date November 01, 2017
 * 
 */
/**
 * 判断是否有restful免密登录，登录请求如果有就执行登录
 * 
 * @author WangDong
 * @date November 17, 2017
 * 
 */
public class FilterUserInfo implements Filter {
	private static final Logger log = Logger.getLogger(FilterUserInfo.class);
	/**
	 * 缓存restful登录凭证
	 */
	private static final Map<String, Map<String, Object>> RESTFUL_LOGIN_CERTIFICATE = new HashMap<>();

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) arg0).getSession();
		{// 判断是否有restful免密登录，登录请求如果有就执行登录
			try {
				String certificate = arg0.getParameter("LOGIN_CERTIFICATE");
				log.debug("缓存restful登录凭证certificate:" + certificate);
				if (StringUtils.isNotBlank(certificate)) {
					Map<String, Object> certificateObj = RESTFUL_LOGIN_CERTIFICATE.get(certificate);
					if (certificateObj != null) {
						// 注册session
						Object loginName = certificateObj.get("loginname");
						if (loginName != null) {
							FarmAuthorityService.loginIntoSession(session,
									WebUtils.getCurrentIp((HttpServletRequest) arg0), loginName.toString(),
									"restful免密登录");
							clearCertificate();
						} else {
							log.warn("restful Login: loginName is error:" + certificate);
						}
					} else {
						log.warn("restful Login: certificate is error:" + certificate);
					}
				}
			} catch (Exception e) {
				log.warn("restful Login: certificate is error:" + e.getMessage());
			}
		}

		// ----------------------------------------------------------------------------------------------------------------------
		// ---------------分割线---------------------------------------------------------------------------------------------------
		// ---------判断用户是否需要补充信息，是否需要修改密码,准备参数开开始..---------------------------------------------------------------------------------
		LoginUser currentUser = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
		String path = ((HttpServletRequest) arg0).getContextPath();
		String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":" + arg0.getServerPort() + path + "/";
		HttpServletRequest request = (HttpServletRequest) arg0;
		String requestUrl = request.getRequestURL().toString();
		// 如果端口为80端口则，将该端口去掉，认为是不许要端口的
		String formatUrl = Urls.formatUrl(requestUrl,basePath);
		{// 不是后台请求直接运行访问()
			if (!isURL(formatUrl)) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		HttpServletResponse response = (HttpServletResponse) arg1;
		AuthorityService authServer = FarmAuthorityService.getInstance();
		{
			// 配置文件中是否启用判断用户信息完整性的逻辑
			if (currentUser == null || !FarmParameterService.getInstance()
					.getParameter("config.sys.perfect.userinfo.able").toUpperCase().equals("TRUE")) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		// --------------准备参数开结束----------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------------------------
		{
			// 判断用户密码是否为默认密码，如果为默认密码表示没有修改过密码，需要修改密码
			try {
				if (FarmParameterService.getInstance().getParameter("config.sys.enforce.password.update").equals("true")
						&& authServer.isLegality(currentUser.getLoginname(),
								FarmParameterService.getInstance().getParameter("config.default.password"))) {
					// 用户未修改密码
					log.info("配置文件要求 ：用户必须修改密码");
					response.sendRedirect(basePath + "webuser/editCurrentUserPwd.do");
					return;
				}
			} catch (LoginUserNoExistException | LoginUserNoAuditException e) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		{
			session.setAttribute("USERINFO_COMPLETE" + currentUser.getId(), true);
			arg2.doFilter(arg0, arg1);
			return;
		}
	}

	// -----------------------------------------------------------
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	/**
	 * 判断是否是调用controller的逻辑请求
	 * 
	 * @param urlStr
	 * @return
	 */
	private boolean isURL(String urlStr) {
		return Urls.isActionByUrl(urlStr, "do") || Urls.isActionByUrl(urlStr, "html");
	}

	/**
	 * 清理所有5分钟之前的凭证
	 */
	private static void clearCertificate() {
		log.info("RESTFUL_LOGIN_CERTIFICATE(" + RESTFUL_LOGIN_CERTIFICATE.size() + ")开始清理------");
		List<String> removeKes = new ArrayList<>();
		for (String uuidkey : RESTFUL_LOGIN_CERTIFICATE.keySet()) {
			Map<String, Object> certificate = RESTFUL_LOGIN_CERTIFICATE.get(uuidkey);
			Date certificateTime = (Date) certificate.get("time");
			Date currentTime = Calendar.getInstance().getTime();
			if ((currentTime.getTime() - certificateTime.getTime()) > 60 * 1000) {
				removeKes.add(uuidkey);
			}
		}
		for (String uuidkey : removeKes) {
			RESTFUL_LOGIN_CERTIFICATE.remove(uuidkey);
			log.info("清理------" + uuidkey + "过期，当前缓存区大小" + RESTFUL_LOGIN_CERTIFICATE.size());
		}
	}
	// -------------------------------------------------------------------------------------------------

	/**
	 * 注册并返回登录预登录凭证，后客户端可以通过在浏览器转发该凭证进行登录
	 * 
	 * @param loginname
	 * @return
	 */
	public static String registLoginCertificate(String loginname) {
		clearCertificate();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		Map<String, Object> certificate = new HashMap<>();
		certificate.put("loginname", loginname);
		certificate.put("time", Calendar.getInstance().getTime());
		RESTFUL_LOGIN_CERTIFICATE.put(uuid, certificate);
		return uuid;
	}

	/**
	 * @param loginCertificate
	 * @return
	 */
	public static String getLoginNameByCertificate(String loginCertificate) {
		Map<String, Object> certificateObj = RESTFUL_LOGIN_CERTIFICATE.get(loginCertificate);
		if (certificateObj != null) {
			Object loginName = certificateObj.get("loginname");
			return loginName == null ? null : loginName.toString();
		} else {
			log.warn("restful Login: certificate is error:" + loginCertificate);
		}
		return null;
	}
}