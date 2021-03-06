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
import com.farm.wcp.userfunc.JlscUtils;
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
			// ???????????????????????????
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("??????????????????");
			}
			name = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// ????????????
				// ??????session
				FarmAuthorityService.loginIntoSession(session, getCurrentIp(request), name, "?????????");
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl("/frame/index.do");
			} else {
				// ????????????
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("message", "??????????????????").returnModelAndView("frame/login");
			}
		} catch (LoginUserNoExistException e) {
			log.info("?????????????????????");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("message", "?????????????????????").returnModelAndView("frame/login");
		} catch (CheckCodeErrorException e) {
			log.info(e.getMessage());
			return ViewMode.getInstance().putAttr("message", e.getMessage()).returnModelAndView("frame/login");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "????????????????????????????????????????????????!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/webPage")
	public ModelAndView login(HttpSession session, HttpServletRequest request) {
		if (FilterSso.isSsoAble()) {
			// ????????????????????????
			return ViewMode.getInstance().returnRedirectUrl(FilterSso.getRemoteLoginUrl(request));
		}
		// ?????????????????????????????????session???????????????????????????????????????
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
	 * ???????????????
	 * 
	 * @param checkcode
	 *            ?????????
	 * @param name
	 *            ??????????????????/?????????/??????
	 * @param password
	 *            ???????????????
	 * @param outuserid
	 *            ????????????
	 * @param outusername
	 *            ??????????????????
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubbindOldUser")
	public ModelAndView bindOldUser(String checkcode, String name, String password, String outuserid,
			String outusername, HttpServletRequest request, HttpSession session) {
		try {
			// ???????????????????????????
			CheckcodeAbleUtil.setEnforceCheckCode(session);
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("??????????????????");
			}
			String loginName = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// ????????????
				// ???????????????
				LoginUser user = FarmAuthorityService.getInstance().getUserByLoginName(loginName);
				Outuser outuser = outUserServiceImpl.getOutuserByAccountId(outuserid);
				outuser.setUserid(user.getId());
				outUserServiceImpl.editOutuserEntity(outuser);
				// ??????session
				FarmAuthorityService.loginIntoSession(session, getCurrentIp(request), name, "???????????????????????????");
				String goUrl = getGoUrl(session, FarmParameterService.getInstance());
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl(goUrl);
			} else {
				// ????????????
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("loginname", name).putAttr("outuserid", outuserid)
						.putAttr("outusername", outusername).setError("??????????????????", null)
						.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
			}
		} catch (LoginUserNoExistException e) {
			log.info("?????????????????????");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("loginname", name).putAttr("outuserid", outuserid)
					.putAttr("outusername", outusername).setError("?????????????????????", e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().putAttr("loginname", name).putAttr("outuserid", outuserid)
					.putAttr("outusername", outusername).setError("???????????????", e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "????????????????????????????????????????????????!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * ???????????????
	 * 
	 * @param checkcode
	 *            ?????????
	 * @param outuserid
	 *            ????????????
	 * @param outusername
	 *            ??????????????????
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubbindNewUser")
	public ModelAndView bindNewUser(String checkcode, String outuserid, String loginname, String outusername,
			String orgid, String photourl, HttpServletRequest request, HttpSession session) {
		if (!FarmParameterService.getInstance().getParameter("config.show.out.regist.able").toLowerCase()
				.equals("true")) {
			return ViewMode.getInstance().setError("??????????????????????????????????????????????????????????????????????????????", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		User user = null;
		try {
			// ???????????????????????????
			CheckcodeAbleUtil.setEnforceCheckCode(session);
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("??????????????????");
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
			// ???????????????????????????
			userServiceImpl.setUserOrganization(user.getId(), orgid, getCurrentUser(session));
			// ????????????
			outuser.setUserid(user.getId());
			outUserServiceImpl.editOutuserEntity(outuser);
			// ??????session
			Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session, getCurrentIp(request),
					user.getLoginname(), "?????????????????????????????????");
			sendFirstMessageToUser(loginParas,
					FarmParameterService.getInstance().getParameter("config.sys.firstBind.message"));
			String goUrl = getGoUrl(session, FarmParameterService.getInstance());
			return ViewMode.getInstance().returnRedirectUrl(goUrl);
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().setError("???????????????", null).putAttr("outuserid", outuserid)
					.putAttr("photourl", photourl).putAttr("outusername", outusername)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).putAttr("outuserid", outuserid)
					.putAttr("photourl", photourl).putAttr("outusername", outusername)
					.returnModelAndView(ThemesUtil.getThemePath() + "/userRelation");
		}
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param checkcode
	 *            ?????????
	 * @param name
	 *            ?????????/?????????/??????
	 * @param session
	 * @return
	 * @throws CheckCodeErrorException
	 */
	private String findUserLoginName(String name, HttpSession session) {
		if (name == null) {
			// ????????????
			return null;
		}
		LoginUser user = FarmAuthorityService.getInstance().getUserByLoginName(name);
		if (user != null) {
			name = user.getLoginname();
		}
		{
			// ?????????????????????????????????????????????+???????????????????????????????????????????????????????????????
			String orgAndNameGetLoginName = JlscUtils.getUserLoginName(name);
			if (orgAndNameGetLoginName != null) {
				name = orgAndNameGetLoginName;
			}
		}
		return name;
	}

	/**
	 * ????????????????????????
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
	 * ??????????????????
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/websubmit")
	public ModelAndView webLoginCommit(String checkcode, String name, String password, HttpServletRequest request,
			HttpSession session) {
		try {
			// ???????????????????????????
			if (!CheckcodeAbleUtil.isCheckCodeAble(checkcode, session)) {
				throw new CheckCodeErrorException("??????????????????");
			}
			name = findUserLoginName(name, session);
			if (FarmAuthorityService.getInstance().isLegality(name, password)) {
				// ????????????
				// ??????session
				Map<String, String> loginParas = FarmAuthorityService.loginIntoSession(session, getCurrentIp(request),
						name, "?????????");
				sendFirstMessageToUser(loginParas,
						FarmParameterService.getInstance().getParameter("config.sys.firstlogin.message"));
				String goUrl = getGoUrl(session, FarmParameterService.getInstance());
				loginStateHandle(true, session);
				return ViewMode.getInstance().returnRedirectUrl(goUrl);
			} else {
				// ????????????
				loginStateHandle(false, session);
				return ViewMode.getInstance().putAttr("loginname", name).setError("??????????????????", null)
						.returnModelAndView(ThemesUtil.getThemePath() + "/login");
			}
		} catch (LoginUserNoExistException e) {
			log.info("?????????????????????");
			loginStateHandle(false, session);
			return ViewMode.getInstance().putAttr("loginname", name).setError("?????????????????????", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/login");
		} catch (CheckCodeErrorException e) {
			return ViewMode.getInstance().putAttr("loginname", name).setError("???????????????", null)
					.returnModelAndView(ThemesUtil.getThemePath() + "/login");
		} catch (LoginUserNoAuditException e) {
			return ViewMode.getInstance().putAttr("MESSAGE", "?????????????????????????????????????????????????????????!")
					.returnModelAndView(ThemesUtil.getThemePath() + "/message");
		}
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @param loginParas
	 *            loginIntoSession??????????????????
	 * @param message
	 *            ?????????????????????
	 */
	private void sendFirstMessageToUser(Map<String, String> loginParas, String message) {
		if (loginParas.get("lastLoginTime") == null || loginParas.get("lastLoginTime").isEmpty()) {
			String firstLoginTipmessage = null;
			try {
				firstLoginTipmessage = message;
			} catch (Exception e) {
				firstLoginTipmessage = "?????????????????????";
			}
			Message messageMaster = new Message(TYPE_KEY.user_login);
			messageMaster.initTitle();
			messageMaster.initText().setString(firstLoginTipmessage);
			usermessageServiceImpl.sendMessage(messageMaster, loginParas.get("UserId"));
		}
	}

	@RequestMapping("/webout")
	public ModelAndView weblogOut(String name, HttpServletRequest request, HttpSession session)
			throws UnsupportedEncodingException {
		logoutUser(getCurrentIp(request), session);
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
	public ModelAndView logOut(String name, HttpServletRequest request, HttpSession session)
			throws UnsupportedEncodingException {
		logoutUser(getCurrentIp(request), session);
		// return ViewMode.getInstance().returnRedirectUrl("/login/page.do");
		if (FilterSso.isSsoAble()) {
			return ViewMode.getInstance().returnRedirectUrl(FilterSso.getSsoLogoutURL(request));
		} else {
			return ViewMode.getInstance().returnRedirectUrl("/login/webPage.html");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubCurrent")
	@ResponseBody
	public Map<String, Object> current(HttpSession session) {
		log.debug("????????????????????????");
		LoginUser user = getCurrentUser(session);
		boolean islogined = (user != null);
		return ViewMode.getInstance().putAttr("islogin", islogined).putAttr("curentUser", user).returnObjMode();
	}
}
