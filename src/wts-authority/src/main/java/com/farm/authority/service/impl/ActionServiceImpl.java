package com.farm.authority.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.authority.domain.Action;
import com.farm.authority.domain.Actiontree;
import com.farm.authority.domain.AuthKeyImpl;
import com.farm.authority.domain.AuthMenuImpl;
import com.farm.core.time.TimeTool;
import com.farm.authority.dao.ActionDaoInter;
import com.farm.authority.dao.ActiontreeDaoInter;
import com.farm.authority.service.ActionServiceInter;
import com.farm.core.auth.domain.AuthKey;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.auth.util.Urls;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.web.easyui.EasyUiTreeNode;

/* *
 *功能：权限资源服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141119144919
 *说明：
 */
@Service
public class ActionServiceImpl implements ActionServiceInter {
	@Resource
	private ActionDaoInter actionDaoImpl;
	@Resource
	private ActiontreeDaoInter actiontreeDaoImpl;
	/**
	 * 系统所用权限缓存
	 */
	private Map<String, AuthKey> keyMap = new HashMap<String, AuthKey>();

	@Override
	@Transactional
	public Action insertActionEntity(Action entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setMuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate14());
		if (actionDaoImpl.getEntityByKey(entity.getAuthkey()) != null) {
			throw new RuntimeException("key已经存在!");
		}
		keyMap.clear();
		return actionDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Action editActionEntity(Action entity, LoginUser user) {
		Action entity2 = actionDaoImpl.getEntity(entity.getId());
		entity2.setMuser(user.getId());
		entity2.setUtime(TimeTool.getTimeDate14());
		entity2.setLoginis(entity.getLoginis());
		entity2.setCheckis(entity.getCheckis());
		entity2.setState(entity.getState());
		entity2.setComments(entity.getComments());
		entity2.setName(entity.getName());
		entity2.setAuthkey(entity.getAuthkey());
		actionDaoImpl.editEntity(entity2);
		Action keyAction = actionDaoImpl.getEntityByKey(entity.getAuthkey());
		if (keyAction != null && !keyAction.getId().equals(entity2.getId())) {
			throw new RuntimeException("key已经存在!");
		}
		keyMap.clear();
		return entity2;
	}

	@Override
	@Transactional
	public void deleteActionEntity(String id, LoginUser user) {
		actionDaoImpl.deleteEntity(actionDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Action getActionEntity(String id) {
		if (id == null) {
			return null;
		}
		return actionDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createActionSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "ALONE_AUTH_ACTION",
				"ID,LOGINIS,CHECKIS,STATE,MUSER,CUSER,UTIME,CTIME,COMMENTS,NAME,AUTHKEY");
		return dbQuery;
	}

	@Override
	@Transactional
	public Actiontree insertActiontreeEntity(Actiontree entity, LoginUser user, String authkey) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setUuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate14());
		entity.setState("1");
		if (entity.getParentid() == null) {
			entity.setParentid("NONE");
		}
		// 类型为结构菜单则不设置权限
		if (entity.getType().equals("1")) {
			entity.setActionid(null);
		} else {
			// 如果传入了id标准用户选择了一个已有的权限
			if (entity.getActionid() == null || entity.getActionid().trim().length() <= 0) {
				// 如果没有id则为手填的
				// 新建立action
				Action action = new Action();
				action.setAuthkey(authkey);
				String actionName = "";
				if (!entity.getParentid().equals("NONE")) {
					actionName = actiontreeDaoImpl.getEntity(entity.getParentid()).getName() + '_' + entity.getName();
				} else {
					actionName = entity.getName();
				}
				action.setName(actionName);
				action.setCheckis("1");
				action.setLoginis("1");
				action.setState("1");
				entity.setActionid(insertActionEntity(action, user).getId());
			}
		}
		entity.setTreecode("NONE");
		entity = actiontreeDaoImpl.insertEntity(entity);
		initTreeCode(entity.getId());
		return entity;
	}

	@Override
	@Transactional
	public Actiontree editActiontreeEntity(Actiontree entity, LoginUser user, String authkey) {
		Actiontree entity2 = actiontreeDaoImpl.getEntity(entity.getId());
		entity2.setUuser(user.getId());
		entity2.setUtime(TimeTool.getTimeDate14());
		entity2.setParams(entity.getParams());
		entity2.setImgid(entity.getImgid());
		entity2.setIcon(entity.getIcon());
		entity2.setActionid(entity.getActionid());
		entity2.setState(entity.getState());
		entity2.setType(entity.getType());
		entity2.setComments(entity.getComments());
		entity2.setName(entity.getName());
		entity2.setSort(entity.getSort());
		// 类型为结构菜单则不设置权限
		if (entity.getType().equals("1")) {
			entity.setActionid(null);
		} else {
			// 如果传入了id标准用户选择了一个已有的权限
			if (entity.getActionid() == null || entity.getActionid().trim().length() <= 0) {
				// 如果没有id则为手填的
				// 新建立action
				Action action = new Action();
				action.setAuthkey(authkey);
				String actionName = "";
				if (!entity.getParentid().equals("NONE")) {
					actionName = actiontreeDaoImpl.getEntity(entity.getParentid()).getName() + '_' + entity.getName();
				} else {
					actionName = entity.getName();
				}
				action.setName(actionName);
				action.setCheckis("1");
				action.setLoginis("1");
				entity.setActionid(insertActionEntity(action, user).getId());
			}
		}
		actiontreeDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteActiontreeEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		if (actiontreeDaoImpl.selectEntitys(DBRule.addRule(new ArrayList<DBRule>(), "parentid", id, "=")).size() > 0) {
			throw new RuntimeException("不能删除该节点，请先删除其子节点");
		}
		actiontreeDaoImpl.deleteEntity(actiontreeDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Actiontree getActiontreeEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return actiontreeDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createActiontreeSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "ALONE_AUTH_ACTIONTREE",
				"ID,PARAMS,IMGID,ICON,DOMAIN,ACTIONID,STATE,UUSER,CUSER,UTIME,CTIME,TYPE,COMMENTS,TREECODE,NAME,PARENTID,SORT");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<EasyUiTreeNode> getSyncTree(String parentId, String domain) {
		if (parentId == null) {
			parentId = "NONE";
		}
		if (domain == null) {
			domain = "alone";
		}
		DataQuerys.wipeVirus(parentId);
		DataQuerys.wipeVirus(domain);
		return EasyUiTreeNode.formatAsyncAjaxTree(
				EasyUiTreeNode
						.queryTreeNodeOne(parentId, "SORT",
								"(SELECT c.NAME AS NAME,SORT,c.ID AS ID,PARENTID,ICON,b.AUTHKEY as URL,c.PARAMS as PARAM,domain FROM alone_auth_actiontree c LEFT JOIN alone_auth_action b ON c.ACTIONID=b.ID)",
								"ID", "PARENTID", "NAME", "ICON", "and a.DOMAIN='" + domain + "'", "URL,PARAM")
						.getResultList(),
				EasyUiTreeNode.queryTreeNodeTow(parentId, "SORT", "alone_auth_actiontree", "ID", "PARENTID", "NAME",
						"ICON", "and a.DOMAIN='" + domain + "'").getResultList(),
				"PARENTID", "ID", "NAME", "ICON");
	}

	@Override
	@Transactional
	public void moveActionTreeNode(String treeNodeId, String targetTreeNodeId) {
		// 移动节点
		Actiontree node = getActiontreeEntity(treeNodeId);
		if (node.getParentid().equals("NONE")) {
			throw new RuntimeException("不能够移动根节点!");
		}
		Actiontree target = getActiontreeEntity(targetTreeNodeId);
		if (target.getTreecode().indexOf(node.getTreecode()) >= 0) {
			throw new RuntimeException("不能够移动到其子节点下!");
		}
		node.setParentid(targetTreeNodeId);
		actiontreeDaoImpl.editEntity(node);
		// 构造所有树TREECODE
		List<Actiontree> list = actiontreeDaoImpl.getAllSubNodes(treeNodeId);
		for (Actiontree action : list) {
			action.setDomain(target.getDomain());
			actiontreeDaoImpl.editEntity(action);
			initTreeCode(action.getId());
		}
	}

	private void initTreeCode(String treeNodeId) {
		Actiontree node = getActiontreeEntity(treeNodeId);
		if (node.getParentid().equals("NONE")) {
			node.setTreecode(node.getId());
		} else {
			node.setTreecode(actiontreeDaoImpl.getEntity(node.getParentid()).getTreecode() + node.getId());
		}
		actiontreeDaoImpl.editEntity(node);
	}

	@Override
	@Transactional
	public AuthKey getCacheAction(String key) {
		if (keyMap.size() <= 0) {
			List<Action> list = actionDaoImpl.getAllEntity();
			for (Action node : list) {
				{
					// 注册声明的类型（即数据库中注册过的受控权限）
					AuthKeyImpl authkey = new AuthKeyImpl();
					authkey.setIscheck(node.getCheckis().equals("1"));
					authkey.setIslogin(node.getLoginis().equals("1"));
					authkey.setUseAble(node.getState().equals("1"));
					authkey.setGroupKey(false);
					authkey.setKey(node.getAuthkey());
					authkey.setTitle(node.getName());
					keyMap.put(node.getAuthkey(), authkey);
				}
				{
					// 注册默认类型，即被注册过的一级url，如actiontree/list.do中的actiontree
					String groupKey = Urls.getGroupKey(node.getAuthkey());
					if (groupKey != null) {
						AuthKeyImpl supperkey = new AuthKeyImpl();
						supperkey.setIscheck(true);
						supperkey.setIslogin(true);
						supperkey.setUseAble(true);
						supperkey.setTitle("权限组");
						supperkey.setGroupKey(true);
						supperkey.setKey(groupKey);
						keyMap.put(groupKey, supperkey);
					}
				}
			}
		}
		AuthKey authkey = null;
		authkey = keyMap.get(key);
		if (authkey == null) {
			authkey = keyMap.get(key.substring(0, key.replaceAll("\\\\", "/").indexOf("/")).toUpperCase());
		}
		return authkey;
	}

	@Override
	@Transactional
	public List<WebMenu> getAllMenus() {
		DataQuery query = DataQuery.getInstance(1, "SORT,ID,PARENTID,NAME,TYPE,STATE,ICON,IMGID,PARAMS,AUTHKEY",
				"(SELECT c.SORT,c.ID,c.PARENTID,c.NAME,c.TYPE,c.STATE,c.ICON,c.IMGID,c.PARAMS,d.AUTHKEY FROM  alone_auth_actiontree c LEFT JOIN alone_auth_action d ON d.ID=c.ACTIONID WHERE (d.STATE = '1' or d.STATE IS NULL) and c.type!='3' and c.STATE='1' order by LENGTH(c.TREECODE),c.SORT asc) e");
		List<WebMenu> menus = new ArrayList<WebMenu>();
		query.setPagesize(1000);
		query.setNoCount();
		query.addSort(new DBSort("SORT", "asc"));
		query.setDistinct(true);
		try {
			for (Map<String, Object> map : query.search().getResultList()) {
				AuthMenuImpl node = new AuthMenuImpl();
				node.setIcon(map.get("ICON") != null ? map.get("ICON").toString() : null);
				node.setId(map.get("ID") != null ? map.get("ID").toString() : null);
				node.setName(map.get("NAME") != null ? map.get("NAME").toString() : null);
				node.setParams(map.get("PARAMS") != null ? map.get("PARAMS").toString() : null);
				node.setParentid(map.get("PARENTID") != null ? map.get("PARENTID").toString() : null);
				node.setUrl(map.get("AUTHKEY") != null ? map.get("AUTHKEY").toString() : null);
				menus.add(node);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return menus;
	}

	@Override
	@Transactional
	public List<Action> getAllActions() {
		DataQuery dbQuery = DataQuery.getInstance(1, "d.id,d.AUTHKEY,d.NAME,d.COMMENTS,d.STATE,d.CHECKIS,d.LOGINIS",
				" alone_auth_action d ");
		dbQuery.addRule(new DBRule("d.STATE", "1", "="));
		dbQuery.setDistinct(true);
		dbQuery.setNoCount();
		dbQuery.setPagesize(5000);
		List<Action> list = new ArrayList<Action>();
		try {
			for (Map<String, Object> node : dbQuery.search().getResultList()) {
				Action action = new Action();
				action.setAuthkey(node.get("D_AUTHKEY") != null ? node.get("D_AUTHKEY").toString() : null);
				action.setId(node.get("D_ID") != null ? node.get("D_ID").toString() : null);
				action.setName(node.get("D_NAME") != null ? node.get("D_NAME").toString() : null);
				action.setComments(node.get("D_COMMENTS") != null ? node.get("D_COMMENTS").toString() : null);
				action.setState(node.get("D_STATE") != null ? node.get("D_STATE").toString() : null);
				action.setCheckis(node.get("D_CHECKIS") != null ? node.get("D_CHECKIS").toString() : null);
				action.setLoginis(node.get("D_LOGINIS") != null ? node.get("D_LOGINIS").toString() : null);
				list.add(action);
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return list;
	}

	public ActionDaoInter getActionDaoImpl() {
		return actionDaoImpl;
	}

	public void setActionDaoImpl(ActionDaoInter actionDaoImpl) {
		this.actionDaoImpl = actionDaoImpl;
	}

	public ActiontreeDaoInter getActiontreeDaoImpl() {
		return actiontreeDaoImpl;
	}

	public void setActiontreeDaoImpl(ActiontreeDaoInter actiontreeDaoImpl) {
		this.actiontreeDaoImpl = actiontreeDaoImpl;
	}

	@Override
	public DataResult searchAllMenu() throws SQLException {
		DataQuery query = DataQuery.getInstance(1,
				"ACT_TREE.NAME AS NAME, ACT_KEY.AUTHKEY AS SRCKEY, ACT_TREE.ID AS ID,ACT_TREE.PARENTID AS PARENTID",
				"alone_auth_actiontree AS act_tree left join  alone_auth_action AS act_key on act_tree.ACTIONID=act_key.ID");
		query.setPagesize(1000);
		return query.search();
	}

	@Override
	public List<EasyUiTreeNode> searchAsynEasyUiTree(String id, String domain, List<WebMenu> usermenu) {
		List<Map<String, Object>> list1 = EasyUiTreeNode.queryTreeNodeOne(id, "SORT",
				"(SELECT c.NAME    AS NAME, SORT, c.ID      AS ID,  PARENTID, ICON, b.AUTHKEY     AS URL, c.PARAMS  AS PARAM, domain FROM alone_auth_actiontree c LEFT JOIN alone_auth_action b ON c.ACTIONID = b.ID)",
				"ID", "PARENTID", "NAME", "ICON", "and a.DOMAIN='" + domain + "'", "URL,PARAM").getResultList();

		List<Map<String, Object>> list2 = EasyUiTreeNode.queryTreeNodeTow(id, "SORT", "alone_auth_actiontree", "ID",
				"PARENTID", "NAME", "ICON", "and a.DOMAIN='" + domain + "'").getResultList();

		return EasyUiTreeNode.formatAsyncAjaxTreeForMenuTree(list1, list2, "PARENTID", "ID", "NAME", "ICON", "URL",
				"PARAM", usermenu);
	}

}
