package com.farm.authority.service.impl;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Pop;
import com.farm.authority.domain.Post;
import com.farm.core.time.TimeTool;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;

import org.apache.log4j.Logger;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.dao.PopDaoInter;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.PopServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuery.CACHE_UNIT;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：业务权限服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class PopServiceImpl implements PopServiceInter {
	@Resource
	private PopDaoInter popDaoImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;
	private static final Logger log = Logger.getLogger(PopServiceImpl.class);
	@Resource
	private UserServiceInter userServiceImpl;

	@Override
	@Transactional
	public Pop getPopEntity(String id) {
		if (id == null) {
			return null;
		}
		return popDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createPopSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "ALONE_AUTH_POP",
				"PCONTENT,CUSERNAME,PSTATE,CUSER,CTIME,POPTYPE,OID,ONAME,TARGETTYPE,TARGETID,TARGETNAME,ID");
		return dbQuery;
	}

	@Override
	@Transactional
	public void addUserPop(String targetId, String targetName, String oid, String targetType, LoginUser user) {
		Pop pop = new Pop();
		pop.setCtime(TimeTool.getTimeDate14());
		pop.setCuser(user.getId());
		pop.setCusername(user.getName());
		pop.setOid(oid);
		pop.setOname(FarmAuthorityService.getInstance().getUserById(oid).getName());
		// pop.setPcontent(pcontent);
		pop.setPoptype("1");
		pop.setPstate("1");
		pop.setTargetid(targetId);
		pop.setTargetname(targetName);
		pop.setTargettype(targetType);
		popDaoImpl.deleteEntitys(new DBRule("TARGETID", targetId, "=").addRule("OID", oid, "=")
				.addRule("TARGETTYPE", targetType, "=").getDBRules());
		popDaoImpl.insertEntity(pop);
	}

	@Override
	@Transactional
	public void addOrgPop(String targetId, String targetName, String oid, String targetType, LoginUser user) {
		Pop pop = new Pop();
		pop.setCtime(TimeTool.getTimeDate14());
		pop.setCuser(user.getId());
		pop.setCusername(user.getName());
		pop.setOid(oid);
		pop.setOname(organizationServiceImpl.getOrganizationEntity(oid).getName());
		// pop.setPcontent(pcontent);
		pop.setPoptype("2");
		pop.setPstate("1");
		pop.setTargetid(targetId);
		pop.setTargetname(targetName);
		pop.setTargettype(targetType);
		popDaoImpl.deleteEntitys(new DBRule("TARGETID", targetId, "=").addRule("OID", oid, "=")
				.addRule("TARGETTYPE", targetType, "=").getDBRules());
		popDaoImpl.insertEntity(pop);

	}

	@Override
	@Transactional
	public void addPostPop(String targetId, String targetName, String oid, String targetType, LoginUser user) {
		Pop pop = new Pop();
		pop.setCtime(TimeTool.getTimeDate14());
		pop.setCuser(user.getId());
		pop.setCusername(user.getName());
		pop.setOid(oid);
		pop.setOname(organizationServiceImpl.getPostEntity(oid).getName());
		// pop.setPcontent(pcontent);
		pop.setPoptype("3");
		pop.setPstate("1");
		pop.setTargetid(targetId);
		pop.setTargetname(targetName);
		pop.setTargettype(targetType);
		popDaoImpl.deleteEntitys(new DBRule("TARGETID", targetId, "=").addRule("OID", oid, "=")
				.addRule("TARGETTYPE", targetType, "=").getDBRules());
		popDaoImpl.insertEntity(pop);

	}

	@Override
	@Transactional
	public void delPop(String popId, LoginUser user) {
		Pop pop = popDaoImpl.getEntity(popId);
		popDaoImpl.deleteEntity(pop);
	}

	@Override
	@Transactional
	public boolean isUserHaveTargetid(HttpSession session, String targetId, boolean defaultAble) {
		String USER_TARGETPOP = "USER_TARGETPOP";
		// 是否要初始化用户权限
		boolean isInitPopSesion;
		LoginUser user = WebUtils.getCurrentUser(session);
		String userKey = (user == null) ? "NONE" : ("USER" + user.getId());
		// 当前登录session的权限集合
		@SuppressWarnings("unchecked")
		Map<String, Boolean> popmap = (Map<String, Boolean>) session.getAttribute(USER_TARGETPOP);
		if (popmap == null || popmap.get(userKey) == null || !popmap.get(userKey)) {
			// 如果当前session未初始化过权限或当前session中的权限和当前登录用户不符合,重新初始化权限
			isInitPopSesion = true;
		} else {
			isInitPopSesion = false;
		}
		if (popmap == null) {
			// 给session绑定一个权限集合
			popmap = new HashMap<>();
			popmap.put(userKey, true);
			session.setAttribute(USER_TARGETPOP, popmap);
		}
		// 初始化登录用户的权限
		if (isInitPopSesion) {
			popmap.clear();
			popmap.put(userKey, true);
			if (user != null) {
				// -获得用户拥有的权限
				List<String> targetIds = getUserTargetid(user.getId());
				for (String id : targetIds) {
					// -存入session中
					popmap.put(id, true);
				}
			}
		}
		// =----------------------------初始化完成--------------------------------
		Boolean isHave = popmap.get(targetId);
		if (isHave == null) {
			// -判断该权限是否受控(更新到session缓存中)
			Boolean isVisit = null;
			if (defaultAble) {
				isVisit = !isCtrl(targetId);
			} else {
				isVisit = false;
			}
			popmap.put(targetId, isVisit);
			return isVisit;
		} else {
			return isHave;
		}
	}

	/**
	 * 是否受控权限，如果是受控权限则有权限才能访问
	 * 
	 * @param targetId
	 * @return
	 */
	private boolean isCtrl(String targetId) {
		// 如果有配置，权限则是受控权限，即使是没有可用权限，只有禁用权限也是受控权限
		List<DBRule> rules = new DBRule("TARGETID", targetId, "=").getDBRules();
		List<Pop> pops = popDaoImpl.selectEntitys(rules);
		if (pops.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public List<String> getUserTargetid(String userid) {
		// 获得当前用户的所有权限
		List<String> ids = new ArrayList<>();
		// 所有用户分类
		Set<String> idset = new HashSet<String>();
		// 获得所有用户分配到的分类权限（不包括公开的分类）
		{
			Organization org = null;
			List<Post> posts = null;

			org = userServiceImpl.getUserOrganization(userid);
			posts = userServiceImpl.getPost(userid);
			String userSelect = null;
			String postSelect = null;
			String orgSelect = null;
			// 用户
			{
				userSelect = "SELECT TARGETID,ID FROM ALONE_AUTH_POP WHERE PSTATE='1' AND POPTYPE = '1'" + " AND OID ='"
						+ userid + "'";
			}
			// 组织机构
			{
				if (org != null) {
					orgSelect = "SELECT TARGETID,ID FROM ALONE_AUTH_POP WHERE PSTATE='1' AND POPTYPE = '2'" + " AND '"
							+ org.getTreecode() + "' LIKE CONCAT('%', OID, '%')";
				}
			}
			// 岗位
			{
				if (posts != null) {
					String postSql = "(";
					for (Post post : posts) {
						if (postSql.equals("(")) {
							postSql = postSql + "'" + post.getId() + "'";
						} else {
							postSql = postSql + ",'" + post.getId() + "'";
						}
					}
					postSql = postSql + ")";
					if (posts.isEmpty()) {
						postSql = "('NONE')";
					}
					postSelect = "SELECT TARGETID,ID FROM ALONE_AUTH_POP WHERE PSTATE='1' AND POPTYPE = '3'"
							+ " AND OID IN " + postSql + "";
				}
			}
			DataQuery query = DataQuery.getInstance("1", "TARGETID,ID",
					"((" + userSelect + ") " + (postSelect == null ? "" : ("UNION(" + postSelect + ")"))
							+ (orgSelect == null ? "" : ("UNION(" + orgSelect + ")")) + " ) a");
			query.setPagesize(10000);
			query.setNoCount();
			query.setDistinct(true);
			try {
				for (Map<String, Object> node : query.search().getResultList()) {
					idset.add((String) node.get("TARGETID"));
				}
			} catch (SQLException e) {
				log.error("search user pop by getUserTargetid", e);
				return new ArrayList<>();
			}
		}
		for (String typeid : idset) {
			ids.add(typeid);
		}
		return ids;
	}

}
