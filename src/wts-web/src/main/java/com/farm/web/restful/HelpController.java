package com.farm.web.restful;

import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.User;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.util.AuthenticateProvider;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.farm.web.filter.FilterUserInfo;

/**
 * 组织机构、 [创建、查询、更新、删除] 用户、 [创建、查询、更新、删除] ---------------------------- 知识接口[查询]、
 * 分类接口[查询]、 问答接口[查询]、
 * 
 * @author wangdong
 *
 */
@RequestMapping("/helper")
@Controller
public class HelpController extends WebUtils {
	private final static Logger log = Logger.getLogger(HelpController.class);
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;

	/**
	 * API说明文档
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/readme")
	public ModelAndView index(HttpSession session, HttpServletRequest request) {
		String url = request.getRequestURL().toString().substring(0,
				request.getRequestURL().toString().lastIndexOf("/"));
		url = url.substring(0, url.lastIndexOf("/"));
		url=url+"/api";
		return ViewMode.getInstance().putAttr("CURL", url).returnModelAndView("help/restfulApi");
	}
}
