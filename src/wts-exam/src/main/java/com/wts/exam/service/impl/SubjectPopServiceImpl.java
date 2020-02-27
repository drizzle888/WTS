package com.wts.exam.service.impl;

import com.wts.exam.domain.SubjectPop;
import com.wts.exam.domain.SubjectType;
import org.apache.log4j.Logger;
import com.wts.exam.dao.SubjectPopDaoInter;
import com.wts.exam.service.SubjectPopServiceInter;
import com.wts.exam.service.SubjectTypeServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：题库权限服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectPopServiceImpl implements SubjectPopServiceInter {
	@Resource
	private SubjectPopDaoInter subjectpopDaoImpl;
	@Resource
	private SubjectTypeServiceInter subjectTypeServiceImpl;
	private static final Logger log = Logger.getLogger(SubjectPopServiceImpl.class);

	@Override
	@Transactional
	public SubjectPop insertSubjectpopEntity(SubjectPop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return subjectpopDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public SubjectPop editSubjectpopEntity(SubjectPop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		SubjectPop entity2 = subjectpopDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setTypeid(entity.getTypeid());
		entity2.setUsername(entity.getUsername());
		entity2.setUserid(entity.getUserid());
		entity2.setFuntype(entity.getFuntype());
		entity2.setId(entity.getId());
		subjectpopDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public SubjectPop getSubjectpopEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return subjectpopDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjectpopSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_SUBJECT_POP a left join WTS_SUBJECT_TYPE b on a.TYPEID=b.ID",
				"a.FUNTYPE as FUNTYPE,a.USERNAME as USERNAME,b.NAME as TYPENAME ,a.ID as ID");
		return dbQuery;
	}

	@Override
	@Transactional
	public void deleteSubjectpopEntity(String id, LoginUser user) {
		SubjectPop pop = subjectpopDaoImpl.getEntity(id);
		{
			List<String> ids = new ArrayList<>();
			ids.add(pop.getTypeid());
			List<String> typeIds = subjectTypeServiceImpl.getAllSubType(ids);
			for (String typeid : typeIds) {
				subjectpopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", typeid, "="))
						.add(new DBRule("USERID", pop.getUserid(), "="))
						.add(new DBRule("FUNTYPE", pop.getFuntype(), "=")).toList());

				List<SubjectPop> pops = subjectpopDaoImpl
						.selectEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", typeid, "="))
								.add(new DBRule("FUNTYPE", pop.getFuntype(), "=")).toList());
				if (pops.size() == 0) {
					SubjectType type = subjectTypeServiceImpl.getSubjecttypeEntity(typeid);
					if (pop.getFuntype().equals("1")) {
						// 使用权，设置为指定人员
						type.setReadpop("1");
					}
					if (pop.getFuntype().equals("2")) {
						// 编辑权，设置为指定人员
						type.setWritepop("1");
					}
					subjectTypeServiceImpl.editSubjecttypeEntity(type, user);
				}
			}
		}
	}

	@Override
	@Transactional
	public void insertPop(List<String> userIds, List<String> typeIds, String functype, LoginUser currentUser) {
		typeIds = subjectTypeServiceImpl.getAllSubType(typeIds);
		for (String typeid : typeIds) {
			for (String userid : userIds) {
				SubjectPop pop = new SubjectPop();
				pop.setFuntype(functype);
				pop.setTypeid(typeid);
				pop.setUserid(userid);
				LoginUser user = FarmAuthorityService.getInstance().getUserById(userid);
				if (user != null) {
					pop.setUsername(user.getName());
					subjectpopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", typeid, "="))
							.add(new DBRule("USERID", userid, "=")).add(new DBRule("FUNTYPE", functype, "=")).toList());
					subjectpopDaoImpl.insertEntity(pop);
					SubjectType type = subjectTypeServiceImpl.getSubjecttypeEntity(typeid);
					if (functype.equals("1")) {
						// 使用权，设置为指定人员
						type.setReadpop("2");
					}
					if (functype.equals("2")) {
						// 编辑权，设置为指定人员
						type.setWritepop("2");
					}
					subjectTypeServiceImpl.editSubjecttypeEntity(type, currentUser);
				}
			}
		}
	}
}
