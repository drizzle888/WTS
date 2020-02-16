package com.farm.parameter.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.parameter.domain.Aloneparameterlocal;
import com.farm.parameter.dao.AloneparameterlocalDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/* *
 *功能：用户个性化参数持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141204174206
 *说明：
 */
@Repository
public class AloneparameterlocalDaoImpl implements AloneparameterlocalDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;
	private HibernateSQLTools<Aloneparameterlocal> sqlTools;

	@Override
	public void deleteEntity(Aloneparameterlocal aloneparameterlocal) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(aloneparameterlocal);
	}

	@Override
	public int getAllListNum() {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from farm_code_field");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public Aloneparameterlocal getEntity(String aloneparameterlocalid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (Aloneparameterlocal) session.get(Aloneparameterlocal.class,
				aloneparameterlocalid);
	}

	@Override
	public Aloneparameterlocal insertEntity(
			Aloneparameterlocal aloneparameterlocal) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(aloneparameterlocal);
		return aloneparameterlocal;
	}

	@Override
	public void editEntity(Aloneparameterlocal aloneparameterlocal) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(aloneparameterlocal);
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
		sqlTools
				.deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<Aloneparameterlocal> selectEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return sqlTools.selectSqlFromFunction(
				sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		sqlTools.updataSqlFromFunction(sessionFatory.getCurrentSession(),
				values, rules);
	}

	@Override
	public int countEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return sqlTools.countSqlFromFunction(sessionFatory.getCurrentSession(),
				rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public HibernateSQLTools<Aloneparameterlocal> getSqlTools() {
		return sqlTools;
	}

	public void setSqlTools(HibernateSQLTools<Aloneparameterlocal> sqlTools) {
		this.sqlTools = sqlTools;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Aloneparameterlocal getEntityByUser(String userId, String parameterId) {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session
				.createQuery(" from Aloneparameterlocal where euser=? and parameterid=?");
		sqlquery.setString(0, userId);
		sqlquery.setString(1, parameterId);
		List<Aloneparameterlocal> list = sqlquery.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
