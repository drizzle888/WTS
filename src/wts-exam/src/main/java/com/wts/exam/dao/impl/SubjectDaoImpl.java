package com.wts.exam.dao.impl;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.Subject;
import com.wts.exam.dao.SubjectDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：考题持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class SubjectDaoImpl extends HibernateSQLTools<Subject>implements SubjectDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(Subject subject) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(subject);
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
	public Subject getEntity(String subjectid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (Subject) session.get(Subject.class, subjectid);
	}

	@Override
	public Subject insertEntity(Subject subject) {
		if (StringUtils.isBlank(subject.getUuid())) {
			subject.setUuid("");
		}
		Session session = sessionFatory.getCurrentSession();
		session.save(subject);
		if (StringUtils.isBlank(subject.getUuid())) {
			subject.setUuid(subject.getId());
			session.save(subject);
		}
		return subject;
	}

	@Override
	public void editEntity(Subject subject) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(subject);
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
	public List<Subject> selectEntitys(List<DBRule> rules) {
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
		return Subject.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public List<Subject> getSubjectsByPaperId(String paperId) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"select distinct b.ID as ID,b.UUID as UUID,b.TYPEID as TYPEID,b.VERSIONID as VERSIONID,b.PSTATE as PSTATE,b.MATERIALID as MATERIALID,b.PRAISENUM as PRAISENUM,b.COMMENTNUM as COMMENTNUM,b.ANALYSISNUM as ANALYSISNUM,b.DONUM as DONUM,b.RIGHTNUM as RIGHTNUM from WTS_PAPER_SUBJECT a left join WTS_SUBJECT b on a.SUBJECTID=b.ID where a.paperid=? and b.id is not null");
		sqlquery.setString(0, paperId);
		@SuppressWarnings("unchecked")
		List<Subject> list = (List<Subject>) sqlquery.addEntity(Subject.class).list();
		return list;
	}

	@Override
	public String getIdByUuid(String uuid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select ID from WTS_SUBJECT where uuid=?");
		sqlquery.setString(0, uuid);
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) sqlquery.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Subject getEntityByUuid(String uuid) {
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery("from Subject where uuid=?");
		query.setString(0, uuid);
		List<Subject> list = (List<Subject>) query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}