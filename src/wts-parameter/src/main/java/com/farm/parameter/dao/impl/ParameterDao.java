package com.farm.parameter.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.core.sql.result.DataResults;
import com.farm.parameter.dao.ParameterDaoInter;
import com.farm.parameter.domain.AloneParameter;

@Repository
public class ParameterDao implements ParameterDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneParameter entity) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from Alone_Parameter");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneParameter getEntity(String id) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		return (AloneParameter) session.get(AloneParameter.class, id);
	}

	public void insertEntity(AloneParameter entity) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneParameter entity) {
		// TODO Auto-generated method stub
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@Override
	public List<AloneParameter> getAllEntity() {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session.createQuery("from AloneParameter");
		@SuppressWarnings("unchecked")
		List<AloneParameter> list = sqlquery.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getListByDomainType(String domainType) {
		String sql = "select a.id, a.name, a.vtype, a.rules, a.pvalue, a.comments " + "from ALONE_PARAMETER a "//
				+ "where a.domain = ? " //
				+ "order by a.utime desc";
		SQLQuery sqlQuery = sessionFatory.getCurrentSession().createSQLQuery(sql);
		sqlQuery.setString(0, domainType);

		return DataResults.getMaps("id, name, vtype, rules, pvalue, comments", sqlQuery.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneParameter> findListByKey(String paramKey) {
		String hql = "from AloneParameter a where a.pkey = ?";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, paramKey.trim());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneParameter> findListByKeyAndExcludeParamId(String paramKey, String excludeParamId) {
		String hql = "from AloneParameter a where a.pkey = ? and a.id != ?";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, paramKey.trim()).setString(1, excludeParamId);
		return query.list();
	}


	@Override
	public AloneParameter getEntityByKey(String key) {
		Query sqlquery = sessionFatory.getCurrentSession().createQuery("from AloneParameter where PKEY=?");
		sqlquery.setString(0, key.trim());
		@SuppressWarnings("unchecked")
		List<AloneParameter> list = sqlquery.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}
}
