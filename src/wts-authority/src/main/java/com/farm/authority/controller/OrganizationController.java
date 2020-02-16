package com.farm.authority.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.AuthUtils;
import com.farm.authority.domain.Organization;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

/* *
 *功能：组织机构控制层
 *详细：
 *
 * 版本：v0.1
 * @author zhaonaixia
 * @time 2015-6-26 上午10:19:25
 * 说明：
 */
@RequestMapping("/organization")
@Controller
public class OrganizationController extends WebUtils {
	private final static Logger log = Logger.getLogger(OrganizationController.class);

	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;

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
	public Map<String, Object> queryall(@ModelAttribute("query") DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (query.getQueryRule().size() == 0) {
				query.addRule(new DBRule("PARENTID", "NONE", "="));
			}
			DataResult result = organizationServiceImpl.createOrganizationSimpleQuery(query).search();
			result.runDictionary("1:可用,0:禁用", "STATE");
			result.runDictionary("1:标准", "TYPE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 进入组织机构管理界面
	 * 
	 * @param session
	 * @return
	 */

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/OrganizationResult");
	}

	/**
	 * 进入组织机构管理界面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/chooseOrg")
	public ModelAndView chooseOrg(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/ChooseOrgResult");
	}

	/**
	 * 组织机构tabs
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/organizationTabs")
	public ModelAndView orgTabs(RequestMode pageset, String ids, String parentID) {

		if (pageset.getOperateType() == 1) {
			return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("parentId", parentID)
					.returnModelAndView("authority/OrganizationTabs");
		} else {
			Organization entity = organizationServiceImpl.getOrganizationEntity(ids);
			return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
					.putAttr("parentId", entity.getParentid()).putAttr("ids", ids)
					.returnModelAndView("authority/OrganizationTabs");
		}
	}

	/**
	 * 岗位管理tabs
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/postConsoleTabs")
	public ModelAndView postConsoleTabs(RequestMode pageset, String ids) {
		return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("authority/PostResult");
	}

	/**
	 * 设置岗位权限tabs
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/postActionsTabs")
	public ModelAndView postActionsTabs(RequestMode pageset, String ids) {
		return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("ids", ids)
				.returnModelAndView("authority/OrgUserResult");
	}

	/**
	 * 移动节点
	 * 
	 * @return
	 */
	@RequestMapping("/OrgTreeNodeSubmit")
	@ResponseBody
	public Object moveTreeNodeSubmit(String ids, String id, HttpSession session) {
		try {
			organizationServiceImpl.moveOrgTreeNode(ids, id, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Organization org, HttpSession session) {
		try {
			Organization entity = organizationServiceImpl.editOrganizationEntity(org, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Organization org, HttpSession session) {
		try {
			Organization entity = organizationServiceImpl.insertOrganizationEntity(org, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
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
			for (String id : parseIds(ids)) {
				organizationServiceImpl.deleteOrganizationEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnModelAndView("authority/OrganizationResult");

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("authority/OrganizationResult");
		}
	}

	/**
	 * 移除岗位的用户
	 * 
	 * @return
	 */
	@RequestMapping("/removeOrgUser")
	@ResponseBody
	public Object removeOrgUser(String id, String ids, HttpSession session) {
		try {
			organizationServiceImpl.removeOrgUsers(id, ids, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 跳转
	 * 
	 * @return
	 */
	@RequestMapping("/orgTreeNodeTreeView")
	public ModelAndView forSend(String ids) {
		return ViewMode.getInstance().putAttr("ids", ids)
				.returnModelAndView("authority/OrganizationTreenodeChooseTreeWin");
	}

	/**
	 * 跳转到组织机构tree页面
	 * 
	 * @return
	 */
	@RequestMapping("/userORGTreeView")
	public ModelAndView userOrgTree(String ids) {
		return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("authority/UserorgChooseTreeWin");
	}

	/**
	 * 增加用户岗位
	 * 
	 * @return
	 */
	@RequestMapping("/userOrg")
	@ResponseBody
	public Object userORGSubmit(RequestMode pageset, String ids, String id, HttpSession session) {

		try {
			for (String userId : parseIds(ids)) {
				organizationServiceImpl.addUserPost(userId, id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();

		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 增加机构用户
	 * 
	 * @return
	 */
	@RequestMapping("/addOrgUser")
	@ResponseBody
	public Object addOrgUser(String userids, String id, HttpSession session) {
		try {
			for (String userId : parseIds(userids)) {
				userServiceImpl.setUserOrganization(userId, id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 设置用户岗位
	 * 
	 * @return
	 */
	@RequestMapping("/setOrgUserPost")
	@ResponseBody
	public Object setOrgUserPost(String userids, String postids, HttpSession session) {
		try {
			for (String userId : parseIds(userids)) {
				userServiceImpl.setUserPost(userId, postids, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载选择岗位树
	 * 
	 * @return
	 */
	@RequestMapping("/postTree")
	@ResponseBody
	public Object userORGLoadTree(String id) {
		return organizationServiceImpl.loadPostTree(id);
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids, String parentId) {
		try {
			Organization parent = null;

			if (!parentId.equals("") && parentId != null) {
				parent = organizationServiceImpl.getOrganizationEntity(parentId);
			}

			switch (pageset.getOperateType()) {
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("parent", parent)
						.returnModelAndView("authority/OrganizationForm");
			}
			case (0): {// 展示
				Organization entity = organizationServiceImpl.getOrganizationEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.putAttr("parent", parent).returnModelAndView("authority/OrganizationForm");
			}
			case (2): {// 修改

				Organization entity = organizationServiceImpl.getOrganizationEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.putAttr("parent", parent).returnModelAndView("authority/OrganizationForm");

			}
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/OrganizationForm");
		}
		return ViewMode.getInstance().returnModelAndView("authority/OrganizationForm");
	}

	/**
	 * 组织机构节点
	 */
	@RequestMapping("/organizationTree")
	@ResponseBody
	public Object loadTreeNode(String id) {
		// -------------原来没有固定节点的组织机构树，稳定后可删除--------------------
		// if (id == null) {
		// id = "NONE";
		// }
		// try {
		// return EasyUiTreeNode
		// .formatAsyncAjaxTree(
		// EasyUiTreeNode.queryTreeNodeOne(id, "SORT",
		// "alone_auth_organization", "ID", "PARENTID",
		// "NAME", "CTIME").getResultList(),
		// EasyUiTreeNode
		// .queryTreeNodeTow(id, "SORT", "alone_auth_organization", "ID",
		// "PARENTID", "NAME", "CTIME")
		// .getResultList(), "PARENTID", "ID", "NAME", "CTIME");
		// } catch (Exception e) {
		// log.error(e.getMessage());
		// return
		// ViewMode.getInstance().setError(e.getMessage(),e).returnObjMode();
		// }
		// -------------原来没有固定节点的组织机构树，稳定后可删除--------------------
		try {
			if (id == null) {
				// 如果是未传入id，就是根节点，就构造一个虚拟的上级节点
				id = "NONE";
				List<EasyUiTreeNode> list = new ArrayList<>();
				EasyUiTreeNode nodes = new EasyUiTreeNode("NONE", "组织机构", "open", "icon-customers");
				nodes.setChildren(EasyUiTreeNode.formatAsyncAjaxTree(
						EasyUiTreeNode.queryTreeNodeOne(id, "SORT", "alone_auth_organization", "ID", "PARENTID", "NAME",
								"CTIME").getResultList(),
						EasyUiTreeNode.queryTreeNodeTow(id, "SORT", "alone_auth_organization", "ID", "PARENTID", "NAME",
								"CTIME").getResultList(),
						"PARENTID", "ID", "NAME", "CTIME"));
				list.add(nodes);
				return list;
			}
			return EasyUiTreeNode.formatAsyncAjaxTree(
					EasyUiTreeNode
							.queryTreeNodeOne(id, "SORT", "alone_auth_organization", "ID", "PARENTID", "NAME", "CTIME")
							.getResultList(),
					EasyUiTreeNode
							.queryTreeNodeTow(id, "SORT", "alone_auth_organization", "ID", "PARENTID", "NAME", "CTIME")
							.getResultList(),
					"PARENTID", "ID", "NAME", "CTIME");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 组织机构节点Auth
	 */
	@RequestMapping("/organizationTreeAuth")
	@ResponseBody
	public Object loadTreeNode(String id, HttpSession session) {
		boolean authFlag = false;
		Organization org = AuthUtils.getCurrentOrganization(session);
		if (id == null) {
			id = "NONE";
			if (org != null) {
				authFlag = true;
				id = org.getParentid();
			}
		}

		DataQuery query1 = EasyUiTreeNode.queryTreeNodeOneAuth(id, "SORT", "alone_auth_organization", "ID", "PARENTID",
				"NAME", "CTIME", null, null, authFlag);
		DataQuery query2 = EasyUiTreeNode.queryTreeNodeTowAuth(id, "SORT", "alone_auth_organization", "ID", "PARENTID",
				"NAME", "CTIME", null, authFlag);

		if (org != null) {
			query1.addRule(new DBRule("treecode", AuthUtils.getCurrentOrganization(session).getTreecode(), "like-"));
			query2.addRule(new DBRule("b.treecode", AuthUtils.getCurrentOrganization(session).getTreecode(), "like-"));
		}
		try {
			return EasyUiTreeNode.formatAsyncAjaxTree(query1.search().getResultList(), query2.search().getResultList(),
					"PARENTID", "ID", "NAME", "CTIME");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 返回树数据 v1.0 zhanghc 2015年9月6日下午1:58:25
	 * 
	 * @return List<Organization>
	 */
	@RequestMapping("/loadTree")
	@ResponseBody
	public List<Organization> loadTree() {
		return organizationServiceImpl.getTree();
	}
	// ----------------------------------------------------------------------------------

	/**
	 * 获取岗位 v1.0 zhanghc 2015年9月6日下午2:43:50
	 * 
	 * @param orgId
	 *            机构ID
	 * @return List<Organization>
	 */
	@RequestMapping("/loadPost")
	@ResponseBody
	public List<Map<String, Object>> loadPost(String orgId) {
		return organizationServiceImpl.getPostList(orgId);
	}

	/**
	 * 获取岗位，带父机构可用的岗位 v1.0 zhanghc 2015年9月7日下午1:48:14
	 * 
	 * @param orgId
	 * @return List<Map<String,Object>>
	 */
	@RequestMapping("/loadPostWithPOrgPost")
	@ResponseBody
	public List<Map<String, Object>> loadPostWithPOrgPost(String orgId) {
		return organizationServiceImpl.getPostListWithPOrgPost(orgId);
	}

}
