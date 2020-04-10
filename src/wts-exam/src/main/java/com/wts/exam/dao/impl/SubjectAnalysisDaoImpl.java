package com.wts.exam.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.dao.SubjectAnalysisDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：试题解析持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class SubjectAnalysisDaoImpl extends HibernateSQLTools<SubjectAnalysis>implements SubjectAnalysisDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(SubjectAnalysis subjectanalysis) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(subjectanalysis);
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
	public SubjectAnalysis getEntity(String subjectanalysisid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (SubjectAnalysis) session.get(SubjectAnalysis.class, subjectanalysisid);
	}

	@Override
	public SubjectAnalysis insertEntity(SubjectAnalysis subjectanalysis) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(subjectanalysis);
		return subjectanalysis;
	}

	@Override
	public void editEntity(SubjectAnalysis subjectanalysis) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(subjectanalysis);
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
	public List<SubjectAnalysis> selectEntitys(List<DBRule> rules) {
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
		return SubjectAnalysis.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public List<SubjectAnalysis> getAnalysisByPaperId(String paperId) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"select distinct b.ID as ID,b.CTIME as CTIME,b.CUSER as CUSER,b.CUSERNAME as CUSERNAME,b.PSTATE as PSTATE,b.PCONTENT as PCONTENT,b.TEXT as TEXT,b.SUBJECTID as SUBJECTID from WTS_PAPER_SUBJECT a left join WTS_SUBJECT_ANALYSIS b on a.SUBJECTID=b.SUBJECTID where a.paperid=? and b.id is not null");
		sqlquery.setString(0, paperId);
		@SuppressWarnings("unchecked")
		List<SubjectAnalysis> list = (List<SubjectAnalysis>) sqlquery.addEntity(SubjectAnalysis.class).list();
		return list;
	}
}
