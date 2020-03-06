package com.wts.exam.service.impl;

import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectType;
import com.farm.core.time.TimeTool;
import com.farm.web.easyui.EasyUiTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wts.exam.dao.SubjectDaoInter;
import com.wts.exam.dao.SubjectPopDaoInter;
import com.wts.exam.dao.SubjectTypeDaoInter;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.SubjectTypeServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考题分类服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectTypeServiceImpl implements SubjectTypeServiceInter {
	@Resource
	private SubjectTypeDaoInter subjecttypeDaoImpl;
	@Resource
	private SubjectServiceInter subjectServiceImpl;
	@Resource
	private SubjectDaoInter subjectDaoImpl;
	@Resource
	private SubjectPopDaoInter subjectpopDaoImpl;
	private static final Logger log = Logger.getLogger(SubjectTypeServiceImpl.class);

	@Override
	@Transactional
	public SubjectType insertSubjecttypeEntity(SubjectType entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setMuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate14());
		entity.setState("1");
		if (entity.getParentid() == null) {
			entity.setParentid("NONE");
		}
		if (entity.getReadpop() == null) {
			entity.setReadpop("1");
		}
		if (entity.getWritepop() == null) {
			entity.setWritepop("1");
		}
		entity.setTreecode("NONE");
		entity = subjecttypeDaoImpl.insertEntity(entity);
		initTreeCode(entity.getId());
		return entity;
	}

	private void initTreeCode(String treeNodeId) {
		SubjectType node = getSubjecttypeEntity(treeNodeId);
		if (node.getParentid().equals("NONE")) {
			node.setTreecode(node.getId());
		} else {
			node.setTreecode(subjecttypeDaoImpl.getEntity(node.getParentid()).getTreecode() + node.getId());
		}
		subjecttypeDaoImpl.editEntity(node);
	}

	@Override
	@Transactional
	public SubjectType editSubjecttypeEntity(SubjectType entity, LoginUser user) {
		SubjectType entity2 = subjecttypeDaoImpl.getEntity(entity.getId());
		entity2.setMuser(user.getId());
		entity2.setSort(entity.getSort());
		entity2.setState(entity.getState());
		entity2.setUtime(TimeTool.getTimeDate14());
		entity2.setComments(entity.getComments());
		entity2.setName(entity.getName());
		entity2.setId(entity.getId());
		entity2.setReadpop(entity.getReadpop());
		entity2.setWritepop(entity.getWritepop());
		if (entity2.getReadpop() == null) {
			entity2.setReadpop("1");
		}
		if (entity2.getWritepop() == null) {
			entity2.setWritepop("1");
		}
		subjecttypeDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteSubjecttypeEntity(String id, LoginUser user) {
		{
			List<SubjectType> subtypes = subjecttypeDaoImpl
					.selectEntitys(DBRuleList.getInstance().add(new DBRule("PARENTID", id, "=")).toList());
			if (subtypes.size() > 0) {
				throw new RuntimeException("分类下还有分类，请先删除子分类！");
			}
		}
		{
			List<Subject> ableSubjects = subjectDaoImpl.selectEntitys(DBRuleList.getInstance()
					.add(new DBRule("PSTATE", "1", "=")).add(new DBRule("TYPEID", id, "=")).toList());
			if (ableSubjects.size() > 0) {
				throw new RuntimeException("分类下还有题，请先删除或移动题！");
			}
		}
		{
			List<Subject> subjects = subjectDaoImpl.selectEntitys(DBRuleList.getInstance()
					.add(new DBRule("PSTATE", "0", "=")).add(new DBRule("TYPEID", id, "=")).toList());
			// 删除所有临时试题
			for (Subject subject : subjects) {
				subjectServiceImpl.deleteSubjectEntity(subject.getId(), user);
			}
		}
		subjectpopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", id, "=")).toList());
		subjecttypeDaoImpl.deleteEntity(subjecttypeDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public SubjectType getSubjecttypeEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return subjecttypeDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjecttypeSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WTS_SUBJECT_TYPE",
				"ID,MUSER,PARENTID,SORT,CUSER,STATE,UTIME,CTIME,NAME,COMMENTS,TREECODE,READPOP,WRITEPOP");
		return dbQuery;
	}

	@Override
	@Transactional
	public void moveTreeNode(String orgId, String targetOrgId, LoginUser currentUser) {
		String[] orgIds = orgId.split(",");
		for (int i = 0; i < orgIds.length; i++) {
			// 移动节点
			SubjectType node = getSubjecttypeEntity(orgIds[i]);
			// if (node.getParentid().equals("NONE")) {
			// throw new RuntimeException("不能够移动根节点!");
			// }
			if (targetOrgId.equals("NONE")) {
				node.setParentid("NONE");
			} else {
				SubjectType target = getSubjecttypeEntity(targetOrgId);
				if (target.getTreecode().indexOf(node.getTreecode()) >= 0) {
					throw new RuntimeException("不能够移动到其子节点下!");
				}
				node.setParentid(targetOrgId);
			}
			subjecttypeDaoImpl.editEntity(node);
			// 构造所有树TREECODE
			List<SubjectType> list = subjecttypeDaoImpl.getAllSubNodes(orgIds[i]);
			for (SubjectType type : list) {
				initTreeCode(type.getId());
			}
		}
	}

	@Override
	@Transactional
	public List<String> getAllSubType(List<String> typeIds) {
		return subjecttypeDaoImpl.getAllSubType(typeIds);
	}

	@Override
	@Transactional
	public List<EasyUiTreeNode> RunPopFilter(List<EasyUiTreeNode> list, String funtype, LoginUser currentUser) {
		if (StringUtils.isBlank(funtype)) {
			throw new RuntimeException(" the var funtype is not exist!");
		}
		if (funtype.trim().equals("0")) {
			return list;
		}
		if (funtype.trim().equals("1")) {
			// 1使用权
			Set<String> typeids = getUserPopTypeids(currentUser.getId(), funtype);
			list = deleteEasyUiTreeNodeByPop(list, typeids);
			return list;
		}
		if (funtype.trim().equals("2")) {
			// 2维护权
			Set<String> typeids = getUserPopTypeids(currentUser.getId(), funtype);
			list = deleteEasyUiTreeNodeByPop(list, typeids);
			return list;
		}
		return list;
	}

	/**
	 * 根据分类id过掉没有权限的分类
	 * 
	 * @param list
	 * @param typeids
	 * @return
	 */
	private List<EasyUiTreeNode> deleteEasyUiTreeNodeByPop(List<EasyUiTreeNode> list, Set<String> typeids) {
		for (Iterator<EasyUiTreeNode> ite = list.iterator(); ite.hasNext();) {
			EasyUiTreeNode node = ite.next();
			if (!node.getId().equals("NONE") && !typeids.contains(node.getId())) {
				ite.remove();
			} else {
				deleteEasyUiTreeNodeByPop(node.getChildren(), typeids);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public Set<String> getUserPopTypeids(String userId, String funtype) {
		return subjecttypeDaoImpl.getUserPopTypeids(userId, funtype);
	}

	@Override
	@Transactional
	public boolean isHavePop(String typeid, String funtype, String userid) {
		return getUserPopTypeids(userid, funtype).contains(typeid);
	}
}
