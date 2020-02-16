package com.farm.wcp.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Outuser;
import com.farm.authority.domain.User;
import com.farm.authority.service.OutuserServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.exception.CheckCodeErrorException;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;
import com.farm.core.inter.domain.Message;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;
import com.farm.core.page.ViewMode;
import com.farm.core.time.TimeTool;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.doc.util.HtmlUtils;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.CheckcodeAbleUtil;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;
import com.farm.web.filter.FilterSso;

@RequestMapping("/login")
@Controller
public class LoginWebController extends WebUtils {
	private final static Logger log = Logger.getLogger(LoginWebController.class);
	@Resource
	private OutuserServiceInter outUserServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private UsermessageServiceInter usermessageServiceImpl;

	@RequestMapping("/submit")
	public ModelAndView loginCommit(String name, String password, String checkcode, HttpServletRequest request,
			HttpSession session) {
		try {
			// 是否启用登录验证码
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过");
			}
			name = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// 登录成功
				// 注册session
				FarmAuthorityService.loginIntoSession(session, getCurrentIp(request), name, "登录页");
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl("/frame/index.do");
			} else {
				// 登录失败
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("message", "用户密码错误").returnModelAndView("frame/login");
			}
		} catch (LoginUserNoExistException e) {
			log.info("当前用户不存在");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("message", "当前用户不存在").returnModelAndView("frame/login");
		} catch (CheckCodeErrorException e) {
			log.info(e.getMessage());
			return ViewMode.getInstance().putAttr("message", e.getMessage()).returnModelAndView("frame/login");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "当前用户已注册，正等待管理员审核!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * 进入登录页面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/webPage")
	public ModelAndView login(HttpSession session, HttpServletRequest request) {
		// 将登录前的页面地址存入session中，为了登录后回到该页面中
		String url = request.getHeader("Referer");
		session.setAttribute(FarmParameterService.getInstance().getParameter("farm.constant.session.key.from.url"),
				url);
		CheckcodeAbleUtil.loadCheckAbleNum(session);
		ViewMode mode = ViewMode.getInstance();
		LoginUser user = getCurrentUser(session);
		if (user != null) {
			User wcpuser = userServiceImpl.getUserEntity(getCurrentUser(session).getId());
			mode.putAttr("photourl", farmFileManagerImpl.getPhotoURL(wcpuser.getImgid()));
			mode.putAttr("user", user);
		}
		return mode.returnModelAndView(ThemesUtil.getThemePath() + "/login");
	}

	/**
	 * 绑定老用户
	 * 
	 * @param checkcode
	 *            验证码
	 * @param name
	 *            老用户登录名/手机号/邮箱
	 * @param password
	 *            老用户密码
	 * @param outuserid
	 *            外部账户
	 * @param outusername
	 *            外部人员姓名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubbindOldUser")
	public ModelAndView bindOldUser(String checkcode, String name, String password, String outuserid,
			String outusername, HttpServletRequest request, HttpSession session) {
		try {
			// 强制启用登录验证码
			CheckcodeAbleUtil.setEnforceCheckCode(session);
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过");
			}
			String loginName = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// 登录成功
				// 绑定老用户
				LoginUser user = FarmAuthorityService.getInstance().getUserByLoginName(loginName);
				Outuser outuser = outUserServiceImpl.getOutuserByAccountId(outuserid);
				outuser.setUserid(user.getId());
				outUserServiceImpl.editOutuserEntity(outuser);
				// 注册session
				FarmAuthorityService.loginIntoSession(session, getCurrentIp(request), name, "外部账户绑定时登录");
				String goUrl = getGoUrl(session, FarmParameterService.getInstance());
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl(goUrl);
			} else {
				// 登录失败
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("loginname", name).putAttr("outuserid", outuserid)
						.putAttr("outusername", outusername).setError("用户密码错误", null)
						.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
			}
		} catch (LoginUserNoExistException e) {
			log.info("当前用户不存在");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("loginname", name).putAttr("outuserid", outuserid)
					.putAttr("outusername", outusername).setError("当前用户不存在", e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().putAttr("loginname", name).putAttr("outuserid", outuserid)
					.putAttr("outusername", outusername).setError("验证码错误", e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "当前用户已注册，正等待管理员审核!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * 绑定新用户
	 * 
	 * @param checkcode
	 *            验证码
	 * @param outuserid
	 *            外部账户
	 * @param outusername
	 *            外部人员姓名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubbindNewUser")
	public ModelAndView bindNewUser(String checkcode, String outuserid, String loginname, String outusername,
			String orgid, String photourl, HttpServletRequest request, HttpSession session) {
		if (!FarmParameterService.getInstance().getParameter("config.show.out.regist.able").toLowerCase()
				.equals("true")) {
			return ViewMode.getInstance().setError("用户注册功能已经被禁用，请通过管理员进行创建新用户！", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		User user = null;
		try {
			// 强制启用登录验证码
			CheckcodeAbleUtil.setEnforceCheckCode(session);
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过");
			}
			Outuser outuser = outUserServiceImpl.getOutuserByAccountId(outuserid);
			if (photourl != null && !photourl.isEmpty()) {
				try {
					String fileid = HtmlUtils.downloadWebImg(photourl, farmFileManagerImpl);
					user = userServiceImpl.insertUserEntity(outusername, loginname, TimeTool.getTimeDate12(), fileid);
				} catch (Exception e) {
					log.error(e.getMessage());
					user = userServiceImpl.insertUserEntity(outusername, loginname, TimeTool.getTimeDate12());
				}
			} else {
				user = userServiceImpl.insertUserEntity(outusername, loginname, TimeTool.getTimeDate12());
			}
			// 为用户设置组织机构
			userServiceImpl.setUserOrganization(user.getId(), orgid, getCurrentUser(session));
			// 登录成功
			outuser.setUserid(user.getId());
			outUserServiceImpl.editOutuserEntity(outuser);
			// 注册session
			Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session, getCurrentIp(request),
					user.getLoginname(), "外部账户创建用户时登录");
			sendFirstMessageToUser(loginParas,
					FarmParameterService.getInstance().getParameter("config.sys.firstBind.message"));
			String goUrl = getGoUrl(session, FarmParameterService.getInstance());
			return ViewMode.getInstance().returnRedirectUrl(goUrl);
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().setError("验证码错误", null).putAttr("outuserid", outuserid)
					.putAttr("photourl", photourl).putAttr("outusername", outusername)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).putAttr("outuserid", outuserid)
					.putAttr("photourl", photourl).putAttr("outusername", outusername)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		}
	}

	/**
	 * 验证验证码并且获取用户登录名
	 * 
	 * @param checkcode
	 *            验证码
	 * @param name
	 *            登录名/手机号/邮箱
	 * @param session
	 * @return
	 * @throws CheckCodeErrorException
	 */
	private String findUserLoginName(String name, HttpSession session) {
		if (name == null) {
			// 制作验证
			return null;
		}
		LoginUser user = FarmAuthorityService.getInstance().getUserByLoginName(name);
		if (user != null) {
			name = user.getLoginname();
		}
		return name;
	}

	/**
	 * 处理登录状态结果
	 * 
	 * @param state
	 */
	private void loginStateHandle(boolean state, HttpSession session) {
		if (state) {
			CheckcodeAbleUtil.addLoginSuccess(session);
		} else {
			CheckcodeAbleUtil.addLoginError(session);
		}
	}

	/**
	 * 提交登录请求
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/websubmit")
	public ModelAndView webLoginCommit(String checkcode, String name, String password, HttpServletRequest request,
			HttpSession session) {
		try {
			// 是否启用登录验证码
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("验证码未通过");
			}
			name = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// 登录成功
				// 注册session
				Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session, getCurrentIp(request),
						name, "登录页");
				sendFirstMessageToUser(loginParas,
						FarmParameterService.getInstance().getParameter("config.sys.firstlogin.message"));
				String goUrl = getGoUrl(session, FarmParameterService.getInstance());
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl(goUrl);
			} else {
				// 登录失败
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("loginname", name).setError("用户密码错误", null)
						.returnModelAndView(ThemesUtil.getThemePath() + "/login");
			}
		} catch (LoginUserNoExistException e) {
			log.info("当前用户不存在");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("loginname", name).setError("当前用户不存在", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/login");
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().putAttr("loginname", name).setError("验证码错误", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/login");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "当前用户已经注册成功，正等待管理员审核!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * 发送第一个系统消息给用户
	 * 
	 * @param loginParas
	 *            loginIntoSession方法的返回值
	 * @param message
	 *            发送的消息内容
	 */
	private void sendFirstMessageToUser(Map<String, String> loginParas, String message) {
		if (loginParas.get("lastLoginTime") == null || loginParas.get("lastLoginTime").isEmpty()) {
			String firstLoginTipmessage = null;
			try {
				firstLoginTipmessage = message;
			} catch (Exception e) {
				firstLoginTipmessage = "欢迎登录系统！";
			}
			Message messageMaster = new Message(TYPE_KEY.user_login);
			messageMaster.initTitle();
			messageMaster.initText().setString(firstLoginTipmessage);
			usermessageServiceImpl.sendMessage(messageMaster, loginParas.get("UserId"));
		}
	}

	@RequestMapping("/webout")
	public ModelAndView weblogOut(String name, HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		clearCurrentUser(session);
		if (FilterSso.isSsoAble()) {
			return ViewMode.getInstance().returnRedirectUrl(FilterSso.getSsoLogoutURL(request));
		} else {
			return ViewMode.getInstance()
					.returnRedirectUrl("/" + WebUtils.getDefaultIndexPage(FarmParameterService.getInstance()));
		}
	}

	@RequestMapping("/page")
	public ModelAndView login(String name) {
		return ViewMode.getInstance().returnModelAndView("/frame/login");
	}

	@RequestMapping("/out")
	public ModelAndView logOut(String name,HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		clearCurrentUser(session);
		// return ViewMode.getInstance().returnRedirectUrl("/login/page.do");
		if (FilterSso.isSsoAble()) {
			return ViewMode.getInstance().returnRedirectUrl(FilterSso.getSsoLogoutURL(request));
		} else {
			return ViewMode.getInstance().returnRedirectUrl("/login/webPage.html");
		}
	}

	/**
	 * 获得当前用户信息
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubCurrent")
	@ResponseBody
	public Map<String, Object> current(HttpSession session) {
		log.debug("检查当前用户信息");
		LoginUser user = getCurrentUser(session);
		boolean islogined = (user != null);
		return ViewMode.getInstance().putAttr("islogin", islogined).putAttr("curentUser", user).returnObjMode();
	}
}
