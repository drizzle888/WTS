package com.farm.wcp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.User;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.PopServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.exception.CheckCodeErrorException;
import com.farm.core.auth.exception.UserNoLoginException;
import com.farm.core.inter.domain.LinkMessage;
import com.farm.core.inter.domain.Message;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;
import com.wts.exam.domain.ExamStat;
import com.wts.exam.service.ExamStatServiceInter;
import com.wts.exam.service.PaperUserOwnServiceInter;
import com.wts.exam.service.SubjectUserOwnServiceInter;

/**
 * 统计
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/webuser")
@Controller
public class UserWebController extends WebUtils {
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private UsermessageServiceInter usermessageServiceImpl;
	// private final static Logger log = Logger.getLogger(UserController.class);
	@Resource
	private PopServiceInter popServiceImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	@Resource
	private SubjectUserOwnServiceInter subjectUserOwnServiceImpl;
	@Resource
	private ExamStatServiceInter examStatServiceImpl;
	@Resource
	private PaperUserOwnServiceInter paperUserOwnServiceImpl;
	private static final Logger log = Logger.getLogger(UserWebController.class);

	/**
	 * 修改用户信息
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/edit")
	public ModelAndView editUser(HttpSession session) {
		try {
			User user = userServiceImpl.getUserEntity(getCurrentUser(session).getId());
			String name = user.getName();
			String photoid = user.getImgid();
			String photourl = null;
			if (photoid != null && photoid.trim().length() > 0) {
				photourl = farmFileManagerImpl.getPhotoURL(photoid);
			}
			Organization org = userServiceImpl.getOrg(user.getId());
			return ViewMode.getInstance().putAttr("user", user).putAttr("name", name).putAttr("photoid", photoid)
					.putAttr("org", org).putAttr("photourl", photourl)
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/userInfoEdit");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/userInfoEdit");
		}
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/PubRegist")
	public ModelAndView regist(HttpSession session) {
		String showOrgStr = FarmParameterService.getInstance().getParameter("config.regist.showOrg");
		boolean showOrg = false;
		if ("true".equals(showOrgStr)) {
			showOrg = true;
		}
		return ViewMode.getInstance().putAttr("imgUrl", farmFileManagerImpl.getPhotoURL("NONE"))
				.putAttr("showOrg", showOrg).returnModelAndView(ThemesUtil.getThemePath() + "/user/regist");
	}

	/**
	 * 用户注册提交
	 * 
	 * @return
	 * @throws UserNoExistException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/PubRegistCommit")
	public ModelAndView registSubmit(String photoid, String loginname, String name, String password, String orgid,
			String email, HttpSession session) {
		try {
			if (FarmParameterService.getInstance().getParameter("config.show.local.regist.able ").equals("false")) {
				throw new RuntimeException("该操作已经被管理员禁用!");
			}
			{// 校验用户登录名、邮箱
				if (userServiceImpl.validateIsRepeatLoginName(loginname, null)) {
					throw new RuntimeException("登录名已经存在" + loginname);
				}
			}
			User user = new User();
			{// 注册用户级别资料
				user.setImgid(photoid);
				user.setLoginname(loginname);
				user.setName(name);
				user.setState("1");
				user.setType("1");
				user = userServiceImpl.registUser(user, orgid);
				farmFileManagerImpl.submitFile(user.getImgid(),
						FarmFileManagerInter.FILE_APPLICATION_TYPE.USER_IMG.getValue());
			}
			// 设置用户密码
			userServiceImpl.editUserPasswordByLoginName(loginname,
					FarmParameterService.getInstance().getParameter("config.default.password"), password);
			// 处理用户为待审核状态
			if (FarmParameterService.getInstance().getParameter("config.registed.audit").equals("true")) {
				user = userServiceImpl.editUserState(user.getId(), "3", user);
				// 发送审核通知给所有超级管理员
				Message message = new Message(TYPE_KEY.user_registe);
				message.initTitle();
				message.initText();
				message.addLink(new LinkMessage("frame/index.do", "点击进入后台"));
				usermessageServiceImpl.sendMessage(message, userServiceImpl.getSuperUserids());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			String showOrgStr = FarmParameterService.getInstance().getParameter("config.regist.showOrg");
			boolean showOrg = false;
			if ("true".equals(showOrgStr)) {
				showOrg = true;
			}
			return ViewMode.getInstance().putAttr("photoid", photoid).putAttr("loginname", loginname)
					.putAttr("showOrg", showOrg).putAttr("email", email).putAttr("name", name).putAttr("orgid", orgid)
					.putAttr("imgUrl", farmFileManagerImpl.getPhotoURL(photoid)).putAttr("errorMessage", e.getMessage())
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/regist");
		}
		return ViewMode.getInstance().putAttr("name", loginname).putAttr("password", password)
				.returnRedirectUrl("/login/websubmit.do");
	}

	/**
	 * 用户首页
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unused", "deprecation" })
	@RequestMapping("/Home")
	public ModelAndView showUserHome(String userid, String type, Integer num, HttpSession session,
			HttpServletRequest request) {
		ViewMode mode = ViewMode.getInstance();
		boolean isSelf = false;
		DataResult result = null;
		User user = null;
		try {
			if (userid == null) {
				// 访问自己的空间
				if (getCurrentUser(session) == null) {
					return mode.setError("请登录系统执行此操作！", null).returnModelAndView(ThemesUtil.getThemePath() + "/error");
				}
				user = userServiceImpl.getUserEntity(getCurrentUser(session).getId());
				mode.putAttr("self", true);
				isSelf = true;
			} else {
				// 访问别人的空间
				user = userServiceImpl.getUserEntity(userid);
				if (user.getId().equals(getCurrentUser(session) != null ? getCurrentUser(session).getId() : "none")) {
					mode.putAttr("self", true);
					isSelf = true;
				} else {
					mode.putAttr("self", false);
					isSelf = false;
					// 只有在非自己访问时才算被访问
					userServiceImpl.visitUserHomePage(userid, getCurrentUser(session), getCurrentIp(request));
				}
			}
			mode.putAttr("user", user);
			ExamStat stat = examStatServiceImpl.getExamstatEntity(user);
			mode.putAttr("examStat", stat == null ? new ExamStat(0, 0, 0, 0) : stat);
			mode.putAttr("org", userServiceImpl.getOrg(user.getId()));
			mode.putAttr("posts", userServiceImpl.getPost(user.getId()));
			mode.putAttr("photourl", farmFileManagerImpl.getPhotoURL(user.getImgid()));
			// --------------------------查询只是列表和小组------------------------------
			if (num == null) {
				num = 1;
			}
			if (type == null) {
				type = "AllSubject";
			}
			if (type.equals("usermessage")) {
				// 用户消息
				result = usermessageServiceImpl.getMyMessageByUser(getCurrentUserOrThrowException(session).getId(), 10,
						num);
				result.runformatTime("USERMESSAGECTIME", "yyyy-MM-dd HH:mm");
				result.runDictionary("0:未读,1:已读", "READSTATE");
			}
		} catch (Exception e) {
			return mode.setError(e.getMessage(), e).returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		return mode.putAttr("userid", user.getId()).putAttr("username", user.getName()).putAttr("type", type)
				.putAttr("num", num).putAttr("docs", result)
				.returnModelAndView(ThemesUtil.getThemePage("user-homePage", request));
	}

	/**
	 * 设置所有消息为已读状态
	 * 
	 * @param userid
	 * @param type
	 * @param num
	 * @param session
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/readallmsg")
	public ModelAndView readAllmsg(String userid, String type, Integer num, HttpSession session) {
		try {
			usermessageServiceImpl.setReadAllMessageByUser(getCurrentUser(session));
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		return ViewMode.getInstance().putAttr("userid", userid).putAttr("type", type).putAttr("num", num)
				.returnRedirectUrl("/webuser/Home.do");
	}

	/**
	 * 更新当前登录用户信息
	 * 
	 * @param id
	 *            用户ID
	 * @param name
	 *            用户名称
	 * @param photoid
	 *            头像ID
	 * @return ModelAndView
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/editCurrentUser")
	public ModelAndView editCurrentUser(String name, String photoid, String orgid, String email, HttpSession session) {
		try {
			// 修改用户信息
			LoginUser currentUser = getCurrentUser(session);
			String oldimgid = userServiceImpl.getUserEntity(currentUser.getId()).getImgid();
			userServiceImpl.editCurrentUser(currentUser.getId(), name, photoid, orgid);
			String newimgid = userServiceImpl.getUserEntity(currentUser.getId()).getImgid();
			farmFileManagerImpl.updateFileState(oldimgid, newimgid, currentUser);
			// 更新email
			if (StringUtils.isBlank(name)) {
				name = currentUser.getName();
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		return ViewMode.getInstance().putAttr("MESSAGE", "用户信息修改成功！")
				.returnModelAndView(ThemesUtil.getThemePath() + "/message");
	}

	/**
	 * 跳转到修改密码页面 v1.0 zhanghc 2015年9月29日下午6:49:40
	 * 
	 * @param id
	 * @param name
	 * @param photoid
	 * @return ModelAndView
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/editCurrentUserPwd")
	public ModelAndView editCurrentUserPwd() {
		return ViewMode.getInstance().returnModelAndView(ThemesUtil.getThemePath() + "/user/passwordEdit");
	}

	/**
	 * 更新当前登录用户的密码 v1.0 zhanghc 2015年9月29日下午6:54:53
	 * 
	 * @return ModelAndView
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/editCurrentUserPwdCommit")
	public ModelAndView editCurrentUserPwdCommit(String password, String newPassword, String checkcode,
			HttpSession session) {
		try {
			// 是否启用登录验证码
			if (FarmParameterService.getInstance().getParameter("config.sys.verifycode.able").equals("true")) {
				boolean ischeck = checkcode.toUpperCase().equals(session.getAttribute("verCode"));
				session.removeAttribute("verCode");
				if (!ischeck) {
					throw new CheckCodeErrorException("验证码错误");
				}
			}
			userServiceImpl.editUserPassword(getCurrentUser(session).getId(), password, newPassword);
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/passwordEdit");
		}
		return ViewMode.getInstance().putAttr("MESSAGE", "用户密码修改成功！")
				.returnModelAndView(ThemesUtil.getThemePath() + "/message");
	}

	/**
	 * 
	 */
	@RequestMapping("/validCurrUserPwd")
	@ResponseBody
	public Map<String, Object> validCurrUserPwd(String password, HttpSession session) {
		try {
			boolean b = userServiceImpl.validCurrentUserPwd(getCurrentUser(session).getId(), password);
			return ViewMode.getInstance().putAttr("validCurrentUserPwd", b).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载组织机构树
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadOrgTreeData")
	@ResponseBody
	public Map<String, Object> loadOrgTreeData(HttpSession session) {
		try {
			List<Organization> orgs = organizationServiceImpl.getList();
			Collections.sort(orgs, new Comparator<Organization>() {
				@Override
				public int compare(Organization o1, Organization o2) {
					int n = o1.getTreecode().length() - o2.getTreecode().length();
					if (n == 0) {
						return o1.getSort() - o2.getSort();
					} else {
						return n;
					}
				}
			});
			List<Map<String, Object>> treeData = new ArrayList<>();
			Map<String, Object> treeDataMap = new HashMap<>();
			for (Organization org : orgs) {
				Map<String, Object> node = null;
				// 构造当前节点
				if (treeDataMap.get(org.getId()) == null) {
					node = new HashMap<>();
					node.put("text", org.getName());
					node.put("id", org.getId());
					treeDataMap.put(org.getId(), node);
				} else {
					node = (Map<String, Object>) treeDataMap.get(org.getId());
				}
				// ---------------------------
				// 挂载当前节点到数据结构中
				if (org.getParentid().equals("NONE")) {
					// 根节点
					treeData.add(node);
				} else {
					// 非根节点
					String parentid = org.getParentid();
					Map<String, Object> parentNode = (Map<String, Object>) treeDataMap.get(parentid);
					List<Map<String, Object>> nodes = (List<Map<String, Object>>) parentNode.get("nodes");
					if (nodes == null) {
						// 没有子节点则构造子节点序列
						nodes = new ArrayList<>();
						parentNode.put("nodes", nodes);
					}
					nodes.add(node);
				}
			}
			return ViewMode.getInstance().putAttr("data", treeData).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 前台页面选择推送人时人员查询方法
	 */
	@RequestMapping("/loadSendUserData")
	@ResponseBody
	public Map<String, Object> loadSendUserData(String orgid, String name, int page, HttpSession session) {
		try {
			if (page == 0) {
				page = 1;
			}
			DataQuery query = DataQuery.getInstance(page, "a. NAME AS NAME, a.ID AS ID, a.IMGID AS IMGID",
					"alone_auth_user a LEFT JOIN alone_auth_userorg b ON a.ID = b.USERID left join alone_auth_organization c on b.ORGANIZATIONID=c.ID");
			query.addRule(new DBRule("a.STATE", 1, "="));
			DataQuery queryOrg = DataQuery.getInstance(1, "NAME,ID", "alone_auth_organization");

			if (!StringUtils.isBlank(name)) {
				DataQuerys.wipeVirus(name);
				query.addSqlRule("and ( a.name like '%" + name + "%' or c.name like '%" + name + "%' )");
				queryOrg.addRule(new DBRule("name", name, "like"));
			}
			DataResult resultOrg = DataResult.getInstance();
			if (!StringUtils.isBlank(orgid)) {
				Organization org = organizationServiceImpl.getOrganizationEntity(orgid);
				query.addRule(new DBRule("C.treecode", org.getTreecode(), "like-"));
				queryOrg.addRule(new DBRule("id", orgid, "="));
				resultOrg = queryOrg.search();
			}
			query.setPagesize(11);
			DataResult result = query.search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					String fileid = (String) row.get("IMGID");
					String url = farmFileManagerImpl.getPhotoURL(fileid);
					row.put("URL", url);
				}
			});
			return ViewMode.getInstance().putAttr("data", result).putAttr("orgs", resultOrg).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/loadOwnAllSubjects")
	public ModelAndView loadOwnAllSubjects(String pagenum, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			query.setCurrentPage(pagenum);
			query.addRule(new DBRule("a.CUSER", getCurrentUser(session).getId(), "="));
			query.addRule(new DBRule("a.MODELTYPE", "1", "="));
			query = subjectUserOwnServiceImpl.createSubjectuserownSimpleQuery(query);
			DataResult result = query.search();
			// result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩",
			// "CARDSTATE");
			return ViewMode.getInstance().putAttr("result", result)
					.putAttr("ids", getIdsFromResult(result, "SUBJECTID"))
					.putAttr("ownids", getIdsFromResult(result, "ID"))
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/commons/includeLoadOwnAllSubject");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/loadOwnErrorSubjects")
	public ModelAndView loadOwnErrorSubjects(String pagenum, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			query.setCurrentPage(pagenum);
			query.addRule(new DBRule("a.CUSER", getCurrentUser(session).getId(), "="));
			query.addRule(new DBRule("a.MODELTYPE", "3", "="));
			query = subjectUserOwnServiceImpl.createSubjectuserownSimpleQuery(query);
			DataResult result = query.search();
			return ViewMode.getInstance().putAttr("result", result)
					.putAttr("ids", getIdsFromResult(result, "SUBJECTID"))
					.putAttr("ownids", getIdsFromResult(result, "ID"))
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/commons/includeLoadOwnErrorSubject");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/loadBookSubjects")
	public ModelAndView loadBookSubjects(String pagenum, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			query.setCurrentPage(pagenum);
			query.addRule(new DBRule("a.CUSER", getCurrentUser(session).getId(), "="));
			query.addRule(new DBRule("a.MODELTYPE", "2", "="));
			query = subjectUserOwnServiceImpl.createSubjectuserownSimpleQuery(query);
			DataResult result = query.search();
			return ViewMode.getInstance().putAttr("result", result)
					.putAttr("ids", getIdsFromResult(result, "SUBJECTID"))
					.putAttr("ownids", getIdsFromResult(result, "ID"))
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/commons/includeLoadBookSubject");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/loadOwnAllPapers")
	public ModelAndView loadOwnAllPapers(String pagenum, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			query.setCurrentPage(pagenum);
			query.addRule(new DBRule("a.CUSER", getCurrentUser(session).getId(), "="));
			query.addRule(new DBRule("a.MODELTYPE", "1", "="));
			query = paperUserOwnServiceImpl.createPaperuserownSimpleQuery(query);
			DataResult result = query.search();
			result.runDictionary("1:答卷模式,3:练习模式", "PAPERMODELTITLE");
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩", "CARDSTATE");
			return ViewMode.getInstance().putAttr("result", result).putAttr("ownids", getIdsFromResult(result, "ID"))
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/commons/includeLoadOwnAllPaper");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/loadOwnBookPapers")
	public ModelAndView loadOwnBookPapers(String pagenum, HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance();
			query.setCurrentPage(pagenum);
			query.addRule(new DBRule("a.CUSER", getCurrentUser(session).getId(), "="));
			query.addRule(new DBRule("a.MODELTYPE", "2", "="));
			query = paperUserOwnServiceImpl.createPaperuserownSimpleQuery(query);
			DataResult result = query.search();
			result.runDictionary("1:答卷模式,3:练习模式", "PAPERMODELTITLE");
			return ViewMode.getInstance().putAttr("result", result).putAttr("ownids", getIdsFromResult(result, "ID"))
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/commons/includeLoadOwnBookPaper");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
	}

	/**
	 * 拼接DataResult中的字段为逗号分隔的字符串
	 * 
	 * @param result
	 * @param key
	 * @return
	 */
	private String getIdsFromResult(DataResult result, final String key) {
		final StringBuffer ids = new StringBuffer();
		result.runHandle(new ResultsHandle() {
			@Override
			public void handle(Map<String, Object> row) {
				if (ids.length() <= 0) {
					ids.append(row.get(key));
				} else {
					ids.append("," + row.get(key));
				}
			}
		});
		return ids.toString();
	}
}
