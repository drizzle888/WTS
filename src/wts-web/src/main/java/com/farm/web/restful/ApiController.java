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
import com.farm.wcp.domain.Results;
import com.farm.web.WebUtils;
import com.farm.web.filter.FilterUserInfo;

/**
 * 组织机构、 [创建、查询、更新、删除] 用户、 [创建、查询、更新、删除] ---------------------------- 知识接口[查询]、
 * 分类接口[查询]、 问答接口[查询]、
 * 
 * @author wangdong
 *
 */
@RequestMapping("/api")
@Controller
public class ApiController extends WebUtils {
	private final static Logger log = Logger.getLogger(ApiController.class);
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;

	/**
	 * 編碼安全校验码
	 * 
	 * @return
	 */
	private String encodeSecret() {
		// MD5（UTC时间的毫秒数除以100000+安全码）
		Calendar cal = Calendar.getInstance();
		String sysKey = FarmParameterService.getInstance().getParameter("config.restful.secret.key");
		String MD5_SECRET_KEY = AuthenticateProvider.getInstance()
				.encodeMd5(String.valueOf(cal.getTimeInMillis() / 10000) + sysKey);
		return MD5_SECRET_KEY;
	}

	/**
	 * 验证秘钥
	 * 
	 * @param secret
	 */
	private void checkSecret(String secret) {
		// 从配置文件中读取秘钥
		String state = FarmParameterService.getInstance().getParameter("config.restful.state");
		if (!state.toLowerCase().equals("true")) {
			throw new RuntimeException("the function disabled by config file!");
		}
		if (secret == null || !secret.toUpperCase().equals(encodeSecret().toUpperCase())) {
			if (!FarmParameterService.getInstance().getParameter("config.restful.debug").equals("true")) {
				throw new RuntimeException("secret error!");
			}
		}
	}

	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	private LoginUser getUser() {
		return new LoginUser() {
			@Override
			public String getName() {
				return "restful";
			}

			@Override
			public String getLoginname() {
				return "restful";
			}

			@Override
			public String getId() {
				return "restful";
			}

			@Override
			public String getType() {
				return "NONE";
			}

			@Override
			public String getIp() {
				return "NONE";
			}
		};
	}

	/**
	 * 查询所有组织机构
	 * 
	 * @param ID
	 * @param NAME
	 * @param PARENTID
	 * @param APPID
	 * @param session
	 * @return
	 */
	@RequestMapping("/get/organization")
	@ResponseBody
	public Map<String, Object> getOrganization(String id, String name, String parentid, String appid, String secret,
			HttpSession session) {
		try {
			log.info("restful API:查询组织机构");
			checkSecret(secret);
			DataQuery query = DataQuery.getInstance();
			// --------------------------------------------
			// ------------------------------------------
			if (StringUtils.isNotBlank(id)) {
				query.addRule(new DBRule("id", id, "="));
			}
			if (StringUtils.isNotBlank(name)) {
				query.addRule(new DBRule("name", name, "="));
			}
			if (StringUtils.isNotBlank(parentid)) {
				query.addRule(new DBRule("parentid", parentid, "="));
			}
			if (StringUtils.isNotBlank(appid)) {
				query.addRule(new DBRule("appid", appid, "="));
			}
			query.setPagesize(10000);
			query.addRule(new DBRule("state", "1", "="));
			DataQuery dbQuery = DataQuery.init(query, "ALONE_AUTH_ORGANIZATION",
					"ID,TYPE,SORT,PARENTID,MUSER,CUSER,STATE,UTIME,CTIME,COMMENTS,NAME,TREECODE,APPID");
			DataResult result = dbQuery.search();
			// ------------------------------------------
			// ------------------------------------------
			Results resultObj = Results.getResults(result.getResultList(), result.getTotalSize(),
					result.getCurrentPage(), result.getPageSize());
			return ViewMode.getInstance().putAttr("DATA", resultObj).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 获得UTC时间
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/secret/utc")
	@ResponseBody
	public Map<String, Object> utc(HttpSession session) {
		try {
			log.info("restful API:获得UTC时间，用于制作权限码");
			Calendar cal = Calendar.getInstance();
			return ViewMode.getInstance().putAttr("UTC", cal.getTimeInMillis()).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 创建组织机构
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/post/organization")
	@ResponseBody
	public Map<String, Object> postOrganization(String parentid, String sort, String name, String comments,
			String secret, HttpSession session) {
		try {
			log.info("restful API:创建组织机构");
			checkSecret(secret);
			Organization entity = new Organization();
			if (StringUtils.isNotBlank(parentid)) {
				entity.setParentid(parentid);
			}
			entity.setSort(Integer.valueOf(sort));
			entity.setName(name);
			entity.setComments(comments);
			entity.setType("1");
			entity = organizationServiceImpl.insertOrganizationEntity(entity, getUser());
			return ViewMode.getInstance().putAttr("ID", entity.getId()).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 更新组织机构
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/put/organization")
	@ResponseBody
	public Map<String, Object> putOrganization(String id, String sort, String name, String comments, String secret,
			HttpSession session) {
		try {
			log.info("restful API:修改组织机构");
			checkSecret(secret);
			Organization entity = organizationServiceImpl.getOrganizationEntity(id);
			if (StringUtils.isNotBlank(sort)) {
				entity.setSort(Integer.valueOf(sort));
			}
			if (StringUtils.isNotBlank(name)) {
				entity.setName(name);
			}
			if (StringUtils.isNotBlank(comments)) {
				entity.setComments(comments);
			}
			entity = organizationServiceImpl.editOrganizationEntity(entity, getUser());
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除组织机构
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete/organization")
	@ResponseBody
	public Map<String, Object> delOrganization(String id, String secret, HttpSession session) {
		try {
			log.info("restful API:删除组织机构");
			checkSecret(secret);
			organizationServiceImpl.deleteOrganizationEntity(id, getUser());
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询所有用户
	 * 
	 * @param ID
	 * @param NAME
	 * @param PARENTID
	 * @param APPID
	 * @param session
	 * @return
	 */
	@RequestMapping("/get/user")
	@ResponseBody
	public Map<String, Object> getUser(String id, String loginname, String type, String state, String orgid,
			String secret, HttpSession session) {
		try {
			log.info("restful API:查询用户");
			checkSecret(secret);
			DataQuery query = DataQuery.getInstance();
			// --------------------------------------------
			// ------------------------------------------
			if (StringUtils.isNotBlank(id)) {
				query.addRule(new DBRule("USER.ID", id, "="));
			}
			if (StringUtils.isNotBlank(loginname)) {
				query.addRule(new DBRule("USER.LOGINNAME", loginname, "="));
			}
			if (StringUtils.isNotBlank(type)) {
				query.addRule(new DBRule("USER.TYPE", type, "="));
			}
			if (StringUtils.isNotBlank(state)) {
				query.addRule(new DBRule("USER.STATE", state, "="));
			} else {
				query.addRule(new DBRule("USER.STATE", "1", "="));
			}
			if (StringUtils.isNotBlank(orgid)) {
				query.addRule(new DBRule("RFORG.ORGANIZATIONID", orgid, "="));
			}
			query.setPagesize(10000);
			DataQuery dbQuery = DataQuery.init(query,
					"ALONE_AUTH_USER USER left join ALONE_AUTH_USERORG RFORG on USER.ID=RFORG.USERID",
					"USER.ID as ID,USER.NAME as NAME,RFORG.ORGANIZATIONID as ORGANIZATIONID,USER.COMMENTS as COMMENTS,USER.TYPE as TYPE,USER.LOGINNAME as LOGINNAME,USER.IMGID as IMGID,USER.STATE as STATE");
			DataResult result = dbQuery.search();
			// ------------------------------------------
			// ------------------------------------------
			Results resultObj = Results.getResults(result.getResultList(), result.getTotalSize(),
					result.getCurrentPage(), result.getPageSize());
			return ViewMode.getInstance().putAttr("DATA", resultObj).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 创建用户
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/post/user")
	@ResponseBody
	public Map<String, Object> postUser(String name, String loginname, String comments, String orgid, String secret,
			HttpSession session) {
		try {
			log.info("restful API:创建用户");
			checkSecret(secret);
			User entity = new User();
			entity.setLoginname(loginname);
			entity.setName(name);
			if (StringUtils.isNotBlank(comments)) {
				entity.setComments(comments);
			}
			entity.setType("1");
			entity.setState("1");
			entity = userServiceImpl.insertUserEntity(entity, getUser());
			if (StringUtils.isNotBlank(orgid)) {
				userServiceImpl.setUserOrganization(entity.getId(), orgid, getUser());
			}
			return ViewMode.getInstance().putAttr("ID", entity.getId()).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/put/user")
	@ResponseBody
	public Map<String, Object> putUser(String id, String name, String loginname, String comments, String orgid,
			String secret, HttpSession session) {
		try {
			log.info("restful API:修改用户");
			checkSecret(secret);
			User entity = userServiceImpl.getUserEntity(id);
			if (StringUtils.isNotBlank(name)) {
				entity.setName(name);
			}
			if (StringUtils.isNotBlank(loginname)) {
				entity.setLoginname(loginname);
			}
			if (StringUtils.isNotBlank(comments)) {
				entity.setComments(comments);
			}
			entity = userServiceImpl.editUserEntity(entity, getUser());
			if (StringUtils.isNotBlank(orgid)) {
				userServiceImpl.setUserOrganization(id, orgid, getUser());
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete/user")
	@ResponseBody
	public Map<String, Object> delUser(String id, String secret, HttpSession session) {
		try {
			log.info("restful API:删除用户");
			checkSecret(secret);
			userServiceImpl.deleteUserEntity(id, getUser());
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 用户登录注册,返回后的验证码可以用来在前台登录
	 * 
	 * @param loginname
	 * @param secret
	 * @param session
	 * @return
	 */
	@RequestMapping("/regist/login")
	@ResponseBody
	public Map<String, Object> registLogin(String loginname, String secret, HttpSession session) {
		try {
			log.info("restful API:查询用户");
			checkSecret(secret);
			// ------------------------------------------
			// 判断登录名是否存在
			if (userServiceImpl.getUserByLoginName(loginname) == null) {
				throw new RuntimeException("loginname is not exist!");
			}
			// 通过用登录名注册到
			String uuid = FilterUserInfo.registLoginCertificate(loginname);
			// ------------------------------------------
			return ViewMode.getInstance().putAttr("CERTIFICATE", uuid).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 通过用户登录注册的验证码,查找用户的登录名
	 * 
	 * @param loginname
	 * @param secret
	 * @param session
	 * @return
	 */
	@RequestMapping("/get/login")
	@ResponseBody
	public Map<String, Object> getLogin(String certificate, String secret, HttpSession session) {
		try {
			log.info("restful API:查询用户");
			checkSecret(secret);
			// ------------------------------------------
			String loginname = FilterUserInfo.getLoginNameByCertificate(certificate);
			// ------------------------------------------
			return ViewMode.getInstance().putAttr("LOGINNAME", loginname).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

}
