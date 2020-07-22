package com.farm.authority;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.farm.authority.domain.Action;
import com.farm.authority.domain.User;
import com.farm.authority.service.ActionServiceInter;
import com.farm.authority.service.OutuserServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.AuthorityService;
import com.farm.core.ParameterService;
import com.farm.core.auth.domain.AuthKey;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;
import com.farm.core.auth.util.AuthenticateInter;
import com.farm.core.auth.util.AuthenticateProvider;
import com.farm.core.auth.util.Urls;
import com.farm.parameter.FarmParameterService;
import com.farm.util.spring.BeanFactory;
import com.farm.web.WebUtils;
import com.farm.web.log.WcpLog;
import com.farm.web.online.OnlineUserOpImpl;

public class FarmAuthorityService implements AuthorityService {
	// 人数限制
	private static int PLOGS_USERNUM = 5000;
	private UserServiceInter userServiceImpl;
	private ActionServiceInter actionServiceImpl;
	private OutuserServiceInter outuserServiceImpl;
	private AuthenticateInter authUtil = AuthenticateProvider.getInstance();
	private static FarmAuthorityService service;
	private final static Logger log = Logger.getLogger(FarmAuthorityService.class);

	public static int getPLOGS_USERNUM() {
		return PLOGS_USERNUM;
	}

	public static AuthorityService getInstance() {
		if (service == null) {
			service = new FarmAuthorityService();
			service.userServiceImpl = (UserServiceInter) BeanFactory.getBean("userServiceImpl");
			service.outuserServiceImpl = (OutuserServiceInter) BeanFactory.getBean("outuserServiceImpl");
			service.actionServiceImpl = (ActionServiceInter) BeanFactory.getBean("actionServiceImpl");
		}
		return service;
	}

	/**
	 * 将登录信息写进session
	 * 
	 * @param session
	 * @param ip
	 * @param loginName
	 * @param note
	 *            备注（描述登录方式，将写在日志中）
	 * @return 上次登录时间/用户ID
	 */
	public static Map<String, String> loginIntoSession(HttpSession session, String ip, String loginName, String note) {
		WebUtils webutils = new WebUtils();
		// 清空session中的參數
		initSession(session);
		// 开始写入session用户信息
		LoginUser user = getInstance().getUserByLoginName(loginName);
		try {
			BeanUtils.setProperty(user, "ip", ip);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.warn("loginIntoSession:set User Ip:" + e.getMessage());
		}
		WcpLog.info("用户登陆:" + note, user.getName(), user.getId());
		webutils.setCurrentUser(user, session);
		webutils.setLoginTime(session);
		// 开始写入session用户权限
		webutils.setCurrentUserAction(getInstance().getUserAuthKeys(WebUtils.getCurrentUser(session).getId()), session);
		// 开始写入session用户菜单
		webutils.setCurrentUserMenu(getInstance().getUserMenu(WebUtils.getCurrentUser(session).getId()), session);
		// 写入用户上线信息
		// OnlineUserOpInter ouop = null;
		// ouop = OnlineUserOpImpl.getInstance(ip, loginName, session);
		// ouop.userLoginHandle(FarmAuthorityService.getInstance().getUserByLoginName(loginName));
		// 记录用户登录时间
		String lastLoginTime = getInstance().loginHandle(WebUtils.getCurrentUser(session).getId());
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("lastLoginTime", lastLoginTime);
		returnMap.put("UserId", user.getId());
		OnlineUserOpImpl.getInstance(ip, session).userlogin();
		return returnMap;
	}

	/**
	 * 清空session的attribute
	 * 
	 * @param session
	 */
	private static void initSession(HttpSession session) {
		@SuppressWarnings("rawtypes")
		Enumeration em = session.getAttributeNames();
		ParameterService parameterService = FarmParameterService.getInstance();
		while (em.hasMoreElements()) {
			String key = em.nextElement().toString();
			if (!key.equals(parameterService.getParameter("farm.constant.session.key.go.url"))
					&& !key.equals(parameterService.getParameter("farm.constant.session.key.from.url"))) {
				session.removeAttribute(key);
			}
		}
	}

	@Override
	public String loginHandle(String userId) {
		String lastLoginTime = userServiceImpl.setLoginTime(userId);
		return lastLoginTime;
	}

	@Override
	public Set<String> getUserAuthKeys(String userId) {
		User user = userServiceImpl.getUserEntity(userId);
		List<Action> actions = null;
		if (user.getType().equals("3")) {
			actions = actionServiceImpl.getAllActions();
		} else {
			actions = userServiceImpl.getUserActions(userId);
		}
		Set<String> set = new HashSet<String>();
		for (Action action : actions) {
			// 添加自定义权限
			set.add(action.getAuthkey());
			String groupkey = Urls.getGroupKey(action.getAuthkey());
			// 添加组权限
			if (groupkey != null) {
				set.add(groupkey);
			}
		}
		return set;
	}

	@Override
	public LoginUser getUserById(String userId) {
		return userServiceImpl.getUserEntity(userId);
	}

	@Override
	public LoginUser getUserByLoginName(String loginName) {
		return userServiceImpl.getUserByLoginName(loginName);
	}

	@Override
	public List<WebMenu> getUserMenu(String userId) {
		User user = userServiceImpl.getUserEntity(userId);
		List<WebMenu> list = null;
		if (user.getType().equals("3")) {
			list = actionServiceImpl.getAllMenus();
		} else {
			list = userServiceImpl.getUserMenus(userId);
		}
		return list;
	}

	@Override
	public boolean isLegality(String loginName, String password)
			throws LoginUserNoExistException, LoginUserNoAuditException {
		User user = userServiceImpl.getUserByLoginName(loginName);
		if (user == null) {
			throw new LoginUserNoExistException("该登录名不存在！");
		}
		if (user.getType().equals("2")) {
			throw new LoginUserNoExistException("该用户无登录权限！");
		}
		if (user.getState().equals("3")) {
			throw new LoginUserNoAuditException("当前用户正在等待管理员审核！");
		}
		if (!user.getState().equals("1")) {
			throw new LoginUserNoExistException("该用户已停用！");
		}
		if (authUtil.isMd5code(password)) {
			if (password.toUpperCase().equals(user.getPassword())) {
				return true;
			}
		} else {
			if (authUtil.encodeLoginPasswordOnMd5(password, loginName).equals(user.getPassword())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public AuthKey getAuthKey(String key) {
		return actionServiceImpl.getCacheAction(key);
	}

	@Override
	public List<String> getUserPostKeys(String userId) {
		return userServiceImpl.getUserPostIds(userId);
	}

	@Override
	public String getUserOrgKey(String userId) {
		return userServiceImpl.getUserOrganization(userId).getId();
	}

	public UserServiceInter getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserServiceInter userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public ActionServiceInter getActionServiceImpl() {
		return actionServiceImpl;
	}

	public void setActionServiceImpl(ActionServiceInter actionServiceImpl) {
		this.actionServiceImpl = actionServiceImpl;
	}

	@Override
	public LoginUser getUserByOutUserId(String outuserid, String name, String content) {
		return outuserServiceImpl.getUserByAccountId(outuserid, name, content);
	}

}
