package com.wts.exam.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.SubjectType;
import com.wts.exam.dao.SubjectTypeDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：考题分类持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class SubjectTypeDaoImpl extends HibernateSQLTools<SubjectType>implements SubjectTypeDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(SubjectType subjecttype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(subjecttype);
	}

	@Override
	public int getAllListNum() {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from farm_code_field");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public SubjectType getEntity(String subjecttypeid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (SubjectType) session.get(SubjectType.class, subjecttypeid);
	}

	@Override
	public SubjectType insertEntity(SubjectType subjecttype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(subjecttype);
		return subjecttype;
	}

	@Override
	public void editEntity(SubjectType subjecttype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(subjecttype);
	}

	@Override
	public Session getSession() {
		// TODO 自动生成代码,修改后请去除本注释
		return sessionFatory.getCurrentSession();
	}

	@Override
	public DataResult runSqlQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<SubjectType> selectEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
	}

	@Override
	public int countEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return countSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@Override
	protected Class<?> getTypeClass() {
		return SubjectType.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubjectType> getAllSubNodes(String orgId) {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session.createQuery("from SubjectType where TREECODE like ? order by ctime ");
		sqlquery.setString(0, getEntity(orgId).getTreecode() + "%");
		return sqlquery.list();
	}

	@Override
	public List<String> getAllSubType(List<String> typeIds) {
		String whereSql = null;
		for (String typeid : typeIds) {
			if (whereSql == null) {
				whereSql = "TREECODE like '%" + typeid + "%'";
			} else {
				whereSql = whereSql + " or TREECODE like '%" + typeid + "%'";
			}
		}
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select ID from WTS_SUBJECT_TYPE where " + whereSql);
		return sqlquery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getUserPopTypeids(String userId, String funtype) {
		String sql = null;
		// 1使用权
		if (funtype.equals("1")) {
			sql = "SELECT DISTINCT a.id FROM WTS_SUBJECT_TYPE a LEFT JOIN WTS_SUBJECT_POP b ON b.TYPEID = a.ID WHERE "
					+ " a.READPOP = '1' OR ( b.FUNTYPE = '1' AND b.USERID = '" + userId + "' )";
		}
		// 2维护权
		if (funtype.equals("2")) {
			sql = "SELECT DISTINCT a.id FROM WTS_SUBJECT_TYPE a LEFT JOIN WTS_SUBJECT_POP b ON b.TYPEID = a.ID WHERE "
					+ " a.WRITEPOP = '1' OR ( b.FUNTYPE = '2' AND b.USERID = '" + userId + "' )";
		}
		Set<String> idset = new HashSet<>();
		if (sql == null) {
			return idset;
		}
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(sql);
		//sqlquery.setString(0, userId);
		for (String id : (List<String>) sqlquery.list()) {
			idset.add(id);
		}
		return idset;
	}
}
