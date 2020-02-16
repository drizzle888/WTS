package com.farm.quartz.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.quartz.dao.FarmQzSchedulerDaoInter;
import com.farm.quartz.domain.FarmQzScheduler;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 计划任务管理
 * 
 * @author MAC_wd
 * 
 */
@Repository
public class FarmQzSchedulerDao extends HibernateSQLTools<FarmQzScheduler>
		implements FarmQzSchedulerDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	public void deleteEntity(FarmQzScheduler entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from farm_qz_scheduler");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public FarmQzScheduler getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (FarmQzScheduler) session.get(FarmQzScheduler.class, id);
	}

	public FarmQzScheduler insertEntity(FarmQzScheduler entity) {
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	public void editEntity(FarmQzScheduler entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@Override
	public Session getSession() {
		return sessionFatory.getCurrentSession();
	}

	public DataResult runSqlQuery(DataQuery query) {
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<FarmQzScheduler> selectEntitys(List<DBRule> rules) {
		return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FarmQzScheduler> getAutoSchedulers() {
		Session session = sessionFatory.getCurrentSession();
		Query query = session
				.createQuery("from FarmQzScheduler where AUTOIS=?");
		query.setString(0, "1");
		return query.list();
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	protected Class<?> getTypeClass() {
		return FarmQzScheduler.class;
	}
}
