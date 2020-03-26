package com.farm.parameter.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.parameter.dao.DictionaryEntityDaoInter;
import com.farm.parameter.domain.AloneDictionaryEntity;

@Repository
public class DictionaryEntityDao implements DictionaryEntityDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneDictionaryEntity entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from alone_dictionary_entity ");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneDictionaryEntity getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneDictionaryEntity) session.get(AloneDictionaryEntity.class, id);
	}

	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity entity) {
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneDictionaryEntity entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneDictionaryEntity> findEntityByKey(String key) {
		String hql = "from AloneDictionaryEntity a where a.entityindex = ?  and a.state='1'";
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, key);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneDictionaryEntity> findEntityByKey(String key, String exId) {
		String hql = "from AloneDictionaryEntity a where a.entityindex = ? and a.id != ?  and a.state='1'";
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, key.trim()).setString(1, exId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneDictionaryEntity> getAllEntity() {
		String hql = "from AloneDictionaryEntity a where a.state='1'";
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Override
	public AloneDictionaryEntity getEntityByKey(String key) {
		String hql = "from AloneDictionaryEntity a where a.entityindex = ? ";
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, key);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		if (list == null || list.size() <= 0) {
			return null;
		}
		return (AloneDictionaryEntity)list.get(0);
	}
}
