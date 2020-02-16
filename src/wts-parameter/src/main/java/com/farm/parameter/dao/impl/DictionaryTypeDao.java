package com.farm.parameter.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.parameter.dao.DictionaryTypeDaoInter;
import com.farm.parameter.domain.AloneDictionaryType;
@Repository
public class DictionaryTypeDao implements DictionaryTypeDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	public void deleteEntity(AloneDictionaryType entity) {
		Session session = sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	@Override
	public void deleteEntityByTreecode(String entityId) {
		//String hql = "update AloneDictionaryType a set a.state = '2' "
		//		+ "where a.treecode like ?";
		String hql = "delete from AloneDictionaryType a where a.treecode like ?";
		Query query = sessionFatory.getCurrentSession().createQuery(hql);
		query.setString(0, "%" + entityId + "%");
		query.executeUpdate();
	}

	public int getAllListNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("select count(*) from alone_dictionary_type");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	public AloneDictionaryType getEntity(String id) {
		Session session = sessionFatory.getCurrentSession();
		return (AloneDictionaryType) session.get(AloneDictionaryType.class, id);
	}

	public void insertEntity(AloneDictionaryType entity) {
		Session session = sessionFatory.getCurrentSession();
		session.save(entity);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	public void editEntity(AloneDictionaryType entity) {
		Session session = sessionFatory.getCurrentSession();
		session.update(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AloneDictionaryType> getListByEntityId(String entityId) {
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery("from AloneDictionaryType where entity=?")
				.setString(0, entityId);
		List<AloneDictionaryType> list = query.list();
		return list;
	}
}
