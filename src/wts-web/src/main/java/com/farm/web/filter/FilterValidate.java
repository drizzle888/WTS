package com.farm.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.AuthorityService;
import com.farm.core.ParameterService;
import com.farm.core.auth.domain.AuthKey;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.util.Urls;
import com.farm.parameter.FarmParameterService;
import com.farm.util.limit.FarmDoLimits;
import com.farm.web.WebUtils;
import com.farm.web.constant.FarmConstant;
import com.farm.web.online.OnlineUserOpImpl;
import com.farm.web.online.OnlineUserOpInter;

/**
 * 
 * @author WangDong
 * @date Mar 14, 2010
 * 
 */
public class FilterValidate implements Filter {
	private static final Logger log = Logger.getLogger(FilterValidate.class);

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
	 * 判断ip是否在访问黑名单中
	 * 
	 * @param ip
	 * @return
	 */
	private boolean isBlankList(String ip, HttpSession httpSession) {
		Map<String, String> blankList = FarmParameterService.getInstance().getDictionary("ONLINE_BLANKLIST");
		LoginUser user = (LoginUser) httpSession.getAttribute(FarmConstant.SESSION_USEROBJ);
		if (user != null && user.getLoginname() != null && blankList.keySet().contains(user.getLoginname())) {
			return true;
		}
		return blankList.keySet().contains(ip);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		ParameterService parameterService = FarmParameterService.getInstance();
		String path = ((HttpServletRequest) arg0).getContextPath();
		String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":" + arg0.getServerPort() + path + "/";
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		String requestUrl = request.getRequestURL().toString();
		// 如果端口为80端口则，将该端口去掉，认为是不许要端口的
		String formatUrl = Urls.formatUrl(requestUrl, basePath);
		String key = null;
		LoginUser currentUser = null;
		AuthKey authkey = null;
		{
			// 全部资源流量控制（静态和动态）
			if (!FarmDoLimits.isVisiteAble(WebUtils.getCurrentIp(request), session, formatUrl)
					|| isBlankList(WebUtils.getCurrentIp(request), session)) {
				response.sendError(405, "当前用户访问受限(FarmDoLimits)!");
				return;
			}
		}
		{// 不是后台请求直接运行访问()
			if (!isURL(formatUrl)) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		{// API表示restful接口直接放过
			if (formatUrl.indexOf("api/") == 0) {
				arg2.doFilter(arg0, arg1);
				return;
			}
			// webshare表示对外无权限分享
			if (formatUrl.indexOf("webshare/") == 0) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		// log.info(requestUrl + (request.getQueryString() == null ? "" : "?" +
		// request.getQueryString()));
		request.setAttribute("URL",
				requestUrl + (request.getQueryString() == null ? "" : "?" + request.getQueryString()));
		log.debug(formatUrl + request.getRemoteAddr());
		{// 组织URL参数
			key = Urls.getActionKey(formatUrl);
			currentUser = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
			AuthorityService authority = FarmAuthorityService.getInstance();
			authkey = authority.getAuthKey(key);
		}
		{// 如果是不检查则任何连接都可执行
			if (parameterService.getParameter("config.sys.mode.debug").equals("true")) {
				// 放开就是对权限不做验证
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		{// 权限是否被定义为禁用
			if (authkey != null && !authkey.isUseAble()) {
				response.sendError(405, "当前用户没有权限请求该资源!");
				return;
			}
		}
		{// 处理--online--用户在线
			OnlineUserOpInter ouop = OnlineUserOpImpl.getInstance(request.getRemoteAddr(), session);
			ouop.userVisitHandle();
			if (!FarmParameterService.getInstance().getParameterBoolean("config.login.repet.able")
					&& ouop.isRepetLogin()) {
				WebUtils.logoutUser(WebUtils.getCurrentIp(request), session);
				throw new RuntimeException("当前用户被迫离线，因为该用户已在另一台设备上登陆!");
			}
		}
		String subKey = Urls.getActionSubKey(key);
		{// 是否可以直接访问
			// 未被定义和不需要登录的均可以直接访问
			if (authkey != null && !authkey.isLogin()) {
				arg2.doFilter(arg0, arg1);
				return;
			}
			// 判断是否需要用户权限认证
			String prefix = parameterService.getParameter("config.url.free.path.prefix");
			if (prefix != null && subKey.indexOf("/" + prefix) == 0) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		{// 判断是否是登录即可访问
			String prefix = parameterService.getParameter("config.url.login.path.prefix");
			if (prefix != null && subKey.indexOf("/" + prefix) == 0) {
				if (isURL(formatUrl)) {
					if (currentUser == null) {
						response.sendRedirect(parameterService.getParameter("config.index.defaultpage"));
						return;
					} else {
						arg2.doFilter(arg0, arg1);
						return;
					}
				}
			}
			if (authkey != null && authkey.isLogin() && !authkey.isCheck()) {
				arg2.doFilter(arg0, arg1);
				return;
			}
		}
		{// 处理登录操作
			String[] urls = parameterService.getParameter("config.url.login.indexs").split(",");
			if (isURL(formatUrl)) {
				// 如果是用户执行登录操作登录就放过
				if (Arrays.asList(urls).contains(key)) {
					arg2.doFilter(arg0, arg1);
					return;
				}
			}
		}
		{// 处理未登录用户---让其登录
			if (isURL(formatUrl)) {
				if (currentUser == null) {
					if (request.getMethod().equals("GET")) {
						session.setAttribute(parameterService.getParameter("farm.constant.session.key.go.url"),
								requestUrl + "?" + Urls.getUrlParameters(request));
					}
					if (FilterSso.isSsoAble()) {
						// 是否進行單點登錄
						response.sendRedirect(FilterSso.getRemoteLoginUrl(request));
					} else {
						response.sendRedirect(basePath + parameterService.getParameter("config.url.login"));
					}
					return;
				}
			}
		}
		// 验证登录用户权限(用户是否有该权限)
		{
			Set<String> usraction = ((Set<String>) request.getSession().getAttribute(FarmConstant.SESSION_USERACTION));
			if (authkey != null && authkey.isGroupKey()) {
				key = authkey.getKey();
			}
			if (authkey != null && !usraction.contains(key)) {
				log.info("用户未经授权访问'" + authkey.getTitle() + "(" + key + ")'被拦截");
				response.sendError(405, "当前用户没有权限请求该资源!");
				return;
			}
		}
		// 非受管的权限可以直接登录
		arg2.doFilter(arg0, arg1);
		return;
	}

	// -----------------------------------------------------------
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}