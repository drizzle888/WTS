package com.farm.authority.controller;

import java.util.List;
import java.util.Map;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.service.OrganizationServiceInter;

import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/* *
 *功能：岗位控制层
 *详细：
 *
 * 版本：v0.1
 * @author zhaonaixia
 * @time 2015-6-26 上午10:19:25
 * 说明：
 */
@RequestMapping("/post")
@Controller
public class PostController extends WebUtils {
	private final static Logger log = Logger.getLogger(PostController.class);

	@Resource
	OrganizationServiceInter organizationServiceImpl;

	public OrganizationServiceInter getOrganizationServiceImpl() {
		return organizationServiceImpl;
	}

	public void setOrganizationServiceImpl(OrganizationServiceInter organizationServiceImpl) {
		this.organizationServiceImpl = organizationServiceImpl;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(@ModelAttribute("query") DataQuery query, HttpServletRequest request,
			String ids) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (ids != null) {
				query.addRule(new DBRule("ORGANIZATIONID", ids, "="));
			}
			DataResult result = organizationServiceImpl.createPostSimpleQuery(query).search();
			result.runDictionary("0:否,1:是", "EXTENDIS");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e).returnObjMode();
		}
	}

	/**
	 * 查询结果集合(查询组织机构下可用岗位)
	 * 
	 * @return
	 */
	@RequestMapping("/queryOrgPost")
	@ResponseBody
	public Map<String, Object> queryOrgPost(@ModelAttribute("query") DataQuery query, String orgid,
			HttpServletRequest request) {
		try {
			List<Organization> orgs = organizationServiceImpl.getParentOrgs(orgid);
			String orgids = null;
			for (Organization org : orgs) {
				if (orgids == null) {
					orgids = "'" + org.getId() + "'";
				} else {
					orgids = orgids + ",'" + org.getId() + "'";
				}
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			if (orgid != null) {
				query.addSqlRule(" and (ORGANIZATIONID='" + orgid + "' or (ORGANIZATIONID in (" + orgids
						+ ") and EXTENDIS ='1'))");
			}
			DataResult result = organizationServiceImpl.createPostSimpleQuery(query).search();
			result.runDictionary("0:否,1:是", "EXTENDIS");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e).returnObjMode();
		}
	}

	/**
	 * 选择岗位界面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/choosePost")
	public ModelAndView choosePost(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/ChoosePostResult");
	}

	/**
	 * 选择组织机构下的岗位界面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/choosePostByOrg")
	public ModelAndView choosePostByOrg(String orgid, HttpSession session) {
		return ViewMode.getInstance().putAttr("orgid", orgid).returnModelAndView("authority/ChoosePostResultByOrg");
	}

	/**
	 * 进入机构岗位页面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/postActions")
	public ModelAndView postActions(HttpSession session, String ids) {
		return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("authority/PostActionsSeting");
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Post org, HttpSession session) {
		try {
			Post entity = organizationServiceImpl.editPost(org.getId(), org.getName(), org.getExtendis(),
					getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(),e).returnObjMode();
		}

	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Post org, HttpSession session, String ids) {
		try {
			Post entity = organizationServiceImpl.insertPost(ids, org.getName(), org.getExtendis(),
					getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(),e).returnObjMode();
		}
	}

	/**
	 * 设置角色权限树
	 * 
	 * @return
	 */

	@RequestMapping("/ALONEROLEGROUP_SYSBASE_SETTREE")
	@ResponseBody
	public ModelAndView roleGroupSetTree(String ids, String id) {
		try {
			List<String> actions = parseIds(ids);
			organizationServiceImpl.setPostActionTree(actions, id);
			return ViewMode.getInstance().returnModelAndView("authority/PostActionsSeting");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().returnModelAndView("authority/PostActionsSeting");
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public ModelAndView delSubmit(String ids, HttpSession session) {
		try {
			organizationServiceImpl.deletePostEntity(ids, getCurrentUser(session));
			return ViewMode.getInstance().returnModelAndView("authority/PostResult");

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e).returnModelAndView("authority/PostResult");
		}

	}

	/**
	 * 构造岗位权限树
	 * 
	 * @return
	 */
	@RequestMapping("/ALONEMENU_RULEGROUP_TREENODE")
	@ResponseBody
	public Object postActionsTreeInit(DataQuery query, String ids) {
		return EasyUiTreeNode.formatAjaxTree(initRoleGruopTreeNode(query, ids), "A_PARENTID", "A_ID", "A_NAME",
				"C_POSTID", "A_ICON");
	}

	/**
	 * 查询菜单权限构造树形菜单
	 * 
	 * @return
	 */
	private List<Map<String, Object>> initRoleGruopTreeNode(DataQuery query, String ids) {
		DataResult result = null;
		try {
			query = DataQuery.init(query,
					"alone_auth_actiontree A LEFT JOIN alone_auth_action B ON A.ACTIONID = B.ID LEFT JOIN (SELECT POSTID, MENUID FROM alone_auth_postaction WHERE POSTID = '"
							+ ids + "') C ON A.id = C.menuid",
					"A.NAME,A.DOMAIN,a.ICON,A.ID,A.SORT,A.PARENTID,A.TREECODE,B.AUTHKEY,A.name as B_LABLE,A.TYPE,c.postid");
			query.setDistinct(true);
			query.setPagesize(100);
			query.addSort(new DBSort(" a.DOMAIN,LENGTH(A.TREECODE),a.SORT", "ASC", false));
			query.setNoCount();
			result = query.search();
			for (Map<String, Object> node : result.getResultList()) {
				node.put("A_NAME",
						node.get("A_NAME").toString()
								+ "&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:gray;font-size:10px;'>"
								+ (node.get("A_DOMAIN").toString()).toUpperCase() + "</span>");
			}
		} catch (Exception e) {
			result = DataResult.setMessager(result, e + e.getMessage());
			log.error(e.getMessage() + e);
		}
		return result.getResultList();
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			switch (pageset.getOperateType()) {
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("ids", ids)
						.returnModelAndView("authority/PostForm");
			}
			case (0): {// 展示
				Post entity = organizationServiceImpl.getPostEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.returnModelAndView("authority/PostForm");
			}
			case (2): {// 修改
				Post entity = organizationServiceImpl.getPostEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.returnModelAndView("authority/PostForm");
			}
			default:
				break;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e).returnModelAndView("authority/OrganizationForm");
		}
		return ViewMode.getInstance().returnModelAndView("authority/OrganizationForm");
	}

}
