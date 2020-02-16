package com.farm.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.web.WebUtils;
import com.farm.web.constant.FarmConstant;

public class FilterLogInfo implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpSession session = req.getSession();
		// 获得EKCP的登录token,并登录系统
		{
			@SuppressWarnings("unchecked")
			Map<String, String[]> params = arg0.getParameterMap();
			String[] paras = params.get("ekcplogintoken");
			if (paras != null && params.get("ekcplogintoken").length > 0) {
				String ekcplogintoken = params.get("ekcplogintoken")[0];
				String userloginname = WebUtils.getTokenUsername(ekcplogintoken);
				if (userloginname != null) {
					FarmAuthorityService.loginIntoSession(session, WebUtils.getCurrentIp(req), userloginname,"获得token登录");
				}
			}
		}
		// 写入log4j的用户信息
		MDC.put("IP", arg0.getRemoteAddr());
		if (session == null) {
			MDC.put("USERID", "NONE");
		} else {
			LoginUser user = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
			if (user == null) {
				MDC.put("USERID", "NONE");
			} else {
				MDC.put("USERID", user.getId());
			}
		}
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
