package com.farm.wcp.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.farm.authority.service.OrganizationServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.parameter.FarmParameterService;
import com.farm.util.spring.BeanFactory;
import com.farm.web.WebUtils;
import com.farm.web.filter.FilterSso;

/**
 * 同步远程组织机构
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/ssosycn")
@Controller
public class SsoOrgSycnController extends WebUtils implements Job {

	/**
	 * 同步组织机构
	 */
	@RequestMapping("/sycnorgs")
	@ResponseBody
	public Map<String, Object> validCurrUserPwd(HttpSession session) {
		try {
			if(!getCurrentUser(session).getType().equals("3")){
				throw new RuntimeException("只有超级管理员有同步权限 ");
			}
			execute(null);
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String ssoUrlBase = FarmParameterService.getInstance().getParameter("config.sso.url");
		((OrganizationServiceInter) BeanFactory.getBean("organizationServiceImpl"))
				.syncRemotOrgs(FilterSso.getRemoteOrgs(ssoUrlBase + "/api/get/organization.do"));
	}

}
