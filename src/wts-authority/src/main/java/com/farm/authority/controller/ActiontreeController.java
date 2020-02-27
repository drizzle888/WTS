package com.farm.authority.controller;

import com.farm.authority.domain.Action;
import com.farm.authority.domain.Actiontree;
import com.farm.authority.service.ActionServiceInter;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;

/* *
 *功能：构造权限控制层
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
@RequestMapping("/actiontree")
@Controller
public class ActiontreeController extends WebUtils {
	private final static Logger log = Logger.getLogger(ActiontreeController.class);
	@Resource
	ActionServiceInter actionServiceImpl;

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/ActiontreeResult");
	}

	@RequestMapping("/icons")
	public ModelAndView icons(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/ActionCssIcon");
	}

	@RequestMapping("/actionsPage")
	public ModelAndView actionsPage(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/ActionChoose");
	}

	@RequestMapping("/treePage")
	public ModelAndView treePage(HttpSession session, String ids) {
		return ViewMode.getInstance().putAttr("ids", ids).returnModelAndView("authority/ActiontreenodeChooseTreeWin");
	}

	/**
	 * 查询所有主菜单结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (query.getQueryRule().size() == 0) {
				query.addRule(new DBRule("PARENTID", "NONE", "="));
			}
			DataResult result = actionServiceImpl.createActiontreeSimpleQuery(query).search();
			result.runDictionary("1:分类,2:菜单,3:权限", "TYPE");
			result.runDictionary("1:可用,0:禁用", "STATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}

	}

	/**
	 * 加载页面所有菜单
	 * 
	 * @return
	 */
	@RequestMapping("/queryMenu")
	@ResponseBody
	public Map<String, Object> loadAllMenu() {
		try {
			DataResult result = actionServiceImpl.searchAllMenu();
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
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
	public Map<String, Object> editSubmit(Actiontree entity, Action action, HttpSession session) {
		try {
			entity = actionServiceImpl.editActiontreeEntity(entity, getCurrentUser(session), action.getAuthkey());

			return ViewMode.getInstance().putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @param entity
	 *            表单
	 * @param authkey
	 *            权限uri
	 * @param session
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Actiontree entity, String authkey, HttpSession session) {
		try {
			entity = actionServiceImpl.insertActiontreeEntity(entity, getCurrentUser(session), authkey);
			return ViewMode.getInstance().putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				actionServiceImpl.deleteActiontreeEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String domain, String ids, String parentid) {
		ViewMode mode = ViewMode.getInstance();
		try {
			Actiontree parent = new Actiontree();
			Actiontree entity = null;
			switch (pageset.getOperateType()) {
			case (1): {// 新增
				break;
			}
			case (0): {// 展示
				entity = actionServiceImpl.getActiontreeEntity(ids);
				mode.putAttr("entity", entity);
				break;
			}
			case (2): {// 修改
				entity = actionServiceImpl.getActiontreeEntity(ids);
				mode.putAttr("entity", entity);
				break;
			}
			default:
				break;
			}
			if (parentid != null && parentid.trim().length() > 0) {
				parent = actionServiceImpl.getActiontreeEntity(parentid);
				mode.putAttr("parent", parent);
			}
			if (entity != null && entity.getActionid() != null) {
				Action treeAction = actionServiceImpl.getActionEntity(entity.getActionid());
				mode.putAttr("treeAction", treeAction);
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/ActiontreeForm");
		}
		return mode.putAttr("pageset", pageset).putAttr("domain", domain)
				.returnModelAndView("authority/ActiontreeForm");
	}

	/**
	 * 移动节点
	 * 
	 * @return
	 */
	@RequestMapping("/move")
	@ResponseBody
	public Map<String, Object> moveTreeNodeSubmit(String oid, String deid) {
		try {
			for (String id : parseIds(oid)) {
				actionServiceImpl.moveActionTreeNode(id, deid);
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 加载子节点
	 * 
	 * @param id
	 * @param domain
	 * @return
	 */
	@RequestMapping("/loadtree")
	@ResponseBody
	public List<EasyUiTreeNode> loadTreeNode(String id, String domain) {
		try {
			return actionServiceImpl.getSyncTree(id, domain);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ArrayList<EasyUiTreeNode>();
		}
	}

	/**
	 * 加载树节点根据用户权限
	 * 
	 * @return
	 */
	@RequestMapping("/cuserMenus")
	@ResponseBody
	public Object loadTreeNodeForCurrentUser(String id, String domain, HttpSession session) {
		try {
			DataQuerys.wipeVirus(id);
			DataQuerys.wipeVirus(domain);
			List<EasyUiTreeNode> result = actionServiceImpl.searchAsynEasyUiTree(id, domain,
					getCurrentUserMenus(session));
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

}
