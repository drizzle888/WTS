package com.farm.authority.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.domain.User;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.report.FarmReport;
import com.farm.report.jxls.ReportManagerImpl;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.online.OnlineUserOpImpl;
import com.farm.web.online.OnlineUserOpInter;

/* *
 *功能：用户控制层
 *详细：
 *
 * 版本：v0.1
 * @author zhaonaixia
 * @time 2015-6-26 上午10:19:25
 * 说明：
 */
@RequestMapping("/user")
@Controller
public class WebUserController extends WebUtils {
	private final static Logger log = Logger.getLogger(WebUserController.class);
	@Resource
	UserServiceInter userServiceImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;

	public UserServiceInter getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserServiceInter userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	/**
	 * 进入机构用户页面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/chooseUser")
	public ModelAndView chooseUser(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/ChooseUserResult");
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	@RequestMapping("/loadUsers")
	public void download(String ruleText, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			DataQuery query = new DataQuery();
			query.setRuleText(ruleText);
			query.setPagesize(10000);
			if (query.getQueryRule().size() <= 0) {
				query.addRule(new DBRule("a.STATE", "1", "="));
			}
			DataResult result = userServiceImpl.createUserSimpleQuery(query, getCurrentUser(session)).search();
			result.runDictionary("0:禁用,1:可用,2:删除,3:待审核", "STATE");
			result.runDictionary("1:系统用户,2:其他,3:超级用户,4:只读用户,5:知识用户,6:问答用户", "TYPE");
			result.runformatTime("LOGINTIME", "yyyy-MM-dd HH:mm:ss");
			FarmReport.newInstance("userList.xls").addParameter("result", result.getResultList())
					.generateForHttp(response, "userlist");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(@ModelAttribute("query") DataQuery query, HttpServletRequest request,
			HttpSession session) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);

			DBRule parentidRule = query.getAndRemoveRule("PARENTID");
			if (parentidRule != null && !StringUtils.isBlank(parentidRule.getValue())) {
				Organization org = organizationServiceImpl.getOrganizationEntity(parentidRule.getValue());
				if (org != null) {
					query.addRule(new DBRule("TREECODE", org.getTreecode(), "like-"));
				}
			}

			if (query.getQueryRule().size() <= 0) {
				query.addRule(new DBRule("STATE", "1", "="));
			}
			query.addDefaultSort(new DBSort("CTIME", "desc"));
			DataResult result = userServiceImpl.createUserSimpleQuery(query, getCurrentUser(session)).search();
			result.runDictionary("0:禁用,1:可用,2:删除,3:待审核", "STATE");
			result.runDictionary("1:系统用户,2:其他,3:超级用户,4:只读用户,5:知识用户,6:问答用户", "TYPE");
			result.runformatTime("LOGINTIME", "yyyy-MM-dd HH:mm:ss");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 进入用户管理界面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/UserResult");
	}

	/**
	 * 跳转到修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public ModelAndView forSend(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("frame/password");
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping("LoginUser_PassWordUpdata")
	@ResponseBody
	public Object editPassword(HttpSession session, String passwordl, String passwordn1) {
		return ViewMode.getInstance().setError("禁用该功能", null).returnObjMode();
		// try {
		// if
		// (!userServiceImpl.editLoginPassword(getCurrentUser(session).getLoginname(),
		// passwordl, passwordn1)) {
		// throw new RuntimeException("密码修改失败！");
		// }
		// return ViewMode.getInstance().returnObjMode();
		// } catch (Exception e) {
		// log.error(e.getMessage());
		// return
		// ViewMode.getInstance().setError(e.getMessage(),e).returnObjMode();
		// }
	}

	@RequestMapping("/organization")
	public ModelAndView userOrgTree(HttpSession session, String ids) {
		return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("authority/UserorgChooseTreeWin");
	}

	/**
	 * 查看在线用户
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/online")
	public ModelAndView online(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/OnlineResult");
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/onlineQuery")
	@ResponseBody
	public Map<String, Object> onlinequery(HttpServletRequest request, HttpSession session) {
		try {
			OnlineUserOpInter ouop = OnlineUserOpImpl.getInstance(request.getRemoteAddr(), session);
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(ouop.findOnlineUser())).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询组织机构用户结果集合
	 *
	 * @return
	 */
	@RequestMapping("/orgUserQuery")
	@ResponseBody
	public Map<String, Object> queryOrgUser(@ModelAttribute("query") DataQuery query, HttpServletRequest request,
			String ids) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("ORG.ID", ids, "="));
			DataResult result = userServiceImpl.createUserPostQuery(query).search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					String userid = (String) row.get("USERID");
					List<Post> posts = userServiceImpl.getUserPosts(userid);
					String postStrs = null;
					for (Post post : posts) {
						if (post != null) {
							postStrs = (postStrs == null ? post.getName() : postStrs + "," + post.getName());
						}
					}
					row.put("POSTS", postStrs);
				}
			});
			result.runDictionary("0:禁用,1:可用,2:删除", "USERSTATE");
			result.runDictionary("1:标准岗位,2:临时岗位", "TYPE");
			result.runDictionary("0:禁用,1:可用,2:删除", "a.STATE");
			result.runformatTime("LOGINTIME", "yyyy-MM-dd HH:mm:ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 *
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(User user, HttpSession session, String orgId, String postIds) {
		try {
			User entity = userServiceImpl.insertUserEntity(user, getCurrentUser(session), orgId, postIds);
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}

	}

	/**
	 * 提交编辑数据
	 *
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(User user, HttpSession session, String orgId, String postIds) {
		try {
			User entity = userServiceImpl.editUserEntity(user, getCurrentUser(session), orgId, postIds);
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}

	}

	/**
	 * 删除选中单条数据
	 *
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public ModelAndView delSubmit(String ids, HttpSession session) {
		try {
			userServiceImpl.deleteUserEntity(ids, getCurrentUser(session));
			return ViewMode.getInstance().returnModelAndView("authority/UserResult");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserResult");
		}
	}

	/**
	 * 密码初始化
	 *
	 * @return
	 */
	@RequestMapping("/init")
	@ResponseBody
	public Map<String, Object> initPassWord(String ids, HttpSession session) {
		try {
			List<String> idList = parseIds(ids);
			for (String id : idList) {
				userServiceImpl.initDefaultPassWord(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("authority/UserForm");
			}
			case (0): {// 展示
				User entity = userServiceImpl.getUserEntity(ids);
				List<Post> posts = userServiceImpl.getUserPosts(ids);
				Organization organization = userServiceImpl.getUserOrganization(ids);
				return ViewMode.getInstance().putAttr("entity", entity).putAttr("posts", posts)
						.putAttr("pageset", pageset).putAttr("organization", organization)
						.returnModelAndView("authority/UserForm");
			}
			case (2): {// 修改
				User entity = userServiceImpl.getUserEntity(ids);
				Organization org = userServiceImpl.getOrg(entity.getId());
				List<Post> postList = userServiceImpl.getPost(entity.getId());
				String postIds = "";
				for (int i = 0; i < postList.size(); i++) {
					postIds += postList.get(i).getId();
					if (i < postList.size() - 1) {
						postIds += ",";
					}
				}
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.putAttr("orgId", org == null ? null : org.getId()).putAttr("postIds", postIds)
						.returnModelAndView("authority/UserForm");
			}
			default:
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserForm");
		}
		return ViewMode.getInstance().returnModelAndView("authority/UserForm");
	}

	/**
	 * 初始化为指定密码
	 *
	 * @return
	 */
	@RequestMapping("/setingPassWordForm")
	public ModelAndView setingPassWordForm(RequestMode pageset, String userid) {
		try {
			User user = userServiceImpl.getUserEntity(userid);
			return ViewMode.getInstance().putAttr("user", user).putAttr("pageset", pageset)
					.returnModelAndView("authority/UserPasswordForm");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserPasswordForm");
		}
	}

	/**
	 * 初始化为指定密码
	 * 
	 */
	@RequestMapping("/setingPassWordSubmit")
	@ResponseBody
	public Map<String, Object> setingPassWordSubmit(String password, String passwordnew, String mngpassword,
			String userid, HttpSession session) {
		try {
			if (!password.equals(passwordnew)) {
				throw new RuntimeException("两次密码输入不一致!");
			}
			// 验证当前用户密码
			boolean isAble = userServiceImpl.validCurrentUserPwd(getCurrentUser(session).getId(), mngpassword);
			// 修改目标用户密码
			if (isAble) {
				userServiceImpl.editUserPassword(userid, password);
			} else {
				throw new RuntimeException("管理员密码校验失败!");
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 到达人员导入页面
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/toUserImport")
	public ModelAndView toUserImport() {
		try {
			return ViewMode.getInstance().returnModelAndView("authority/userImport");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/userImport");
		}
	}

	/**
	 * 完成人员导入 v1.0 zhanghc 2016年5月3日下午5:09:25
	 * 
	 * @return Map<String,Object>
	 */
	@RequestMapping("/doUserImport")
	@ResponseBody
	public Map<String, Object> doUserImport(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		try {
			userServiceImpl.doUserImport(file, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 下载人员导入模板
	 * 
	 * @param id
	 * @param request
	 * @param response
	 *            void
	 */
	@RequestMapping("/userTempletDown")
	public void userTempletDown(HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			ReportManagerImpl reportManager = new ReportManagerImpl();
			reportManager.setOpath("report");
			File file = new File(reportManager.getReportPath("userImpTemplet.xls"));

			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + new String(file.getName().getBytes("gbk"), "iso-8859-1"));

			is = new FileInputStream(file);
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (Exception e) {
			log.error("下载人员导入模板", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				log.error("下载人员导入模板", e);
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				log.error("下载人员导入模板", e);
				;
			}
		}
	}

	/**
	 * 完成人员导出
	 * 
	 * @param query
	 * @param request
	 * @param response
	 * @param session
	 * @throws Exception
	 *             void
	 */
	@RequestMapping("/doUserExport")
	@SuppressWarnings("unchecked")
	public void doUserExport(DataQuery query, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		query.setPagesize(10000);
		List<Map<String, Object>> list = (List<Map<String, Object>>) queryall(query, request, session).get("rows");
		FarmReport report = FarmReport.newInstance("userList.xls");
		report.addParameter("result", list);
		report.generateForHttp(response, "人员列表");
	}

	/**
	 * 批量设置用户为系统用户
	 *
	 * @return
	 */
	@RequestMapping("/sysUser")
	@ResponseBody
	public ModelAndView sysUser(String ids, HttpSession session) {
		try {
			for (String userid : parseIds(ids)) {
				userServiceImpl.editUserType(userid, "1", getCurrentUser(session));
			}
			return ViewMode.getInstance().returnModelAndView("authority/UserResult");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserResult");
		}
	}

	/**
	 * 修改用户状态
	 *
	 * @return
	 */
	@RequestMapping("/stateUser")
	@ResponseBody
	public ModelAndView stateUser(String ids,String statecode, HttpSession session) {
		try {
			for (String userid : parseIds(ids)) {
				userServiceImpl.editUserState(userid,statecode, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnModelAndView("authority/UserResult");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserResult");
		}
	}
	
	
	
	
	
	/**
	 * 批量设置用户为只读用户
	 *
	 * @return
	 */
	@RequestMapping("/readUser")
	@ResponseBody
	public ModelAndView readUser(String ids, HttpSession session) {
		try {
			for (String userid : parseIds(ids)) {
				userServiceImpl.editUserType(userid, "4", getCurrentUser(session));
			}
			return ViewMode.getInstance().returnModelAndView("authority/UserResult");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserResult");
		}
	}

	/**
	 * 批量设置用户为问答用户
	 *
	 * @return
	 */
	@RequestMapping("/questionUser")
	@ResponseBody
	public ModelAndView questionUser(String ids, HttpSession session) {
		try {
			for (String userid : parseIds(ids)) {
				userServiceImpl.editUserType(userid, "6", getCurrentUser(session));
			}
			return ViewMode.getInstance().returnModelAndView("authority/UserResult");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserResult");
		}
	}

	/**
	 * 批量设置用户为知识用户
	 *
	 * @return
	 */
	@RequestMapping("/knowUser")
	@ResponseBody
	public ModelAndView knowUser(String ids, HttpSession session) {
		try {
			for (String userid : parseIds(ids)) {
				userServiceImpl.editUserType(userid, "5", getCurrentUser(session));
			}
			return ViewMode.getInstance().returnModelAndView("authority/UserResult");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/UserResult");
		}
	}
}
