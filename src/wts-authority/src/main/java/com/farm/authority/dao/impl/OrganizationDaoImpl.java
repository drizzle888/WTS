package com.farm.authority.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.authority.domain.Organization;
import com.farm.authority.dao.OrganizationDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/* *
 *功能：组织机构持久层实现类
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
@Repository
public class OrganizationDaoImpl extends HibernateSQLTools<Organization>implements OrganizationDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(Organization organization) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(organization);
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
	public Organization getEntity(String organizationid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (Organization) session.get(Organization.class, organizationid);
	}

	@Override
	public Organization insertEntity(Organization organization) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(organization);
		return organization;
	}

	@Override
	public void editEntity(Organization organization) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(organization);
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
		deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<Organization> selectEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释

		return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getAllSubNodes(String orgId) {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session.createQuery(" from Organization where TREECODE like ? order by ctime ");
		sqlquery.setString(0, getEntity(orgId).getTreecode() + "%");
		return sqlquery.list();
	}

	@Override
	protected Class<Organization> getTypeClass() {
		return Organization.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getList() {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session.createQuery(" from Organization where state='1' order by ctime ");
		return sqlquery.list();
	}

	@Override
	public void insertSqlEntity(Organization remoteorg) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("INSERT INTO ALONE_AUTH_ORGANIZATION VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		sqlquery.setString(0, remoteorg.getId());
		sqlquery.setString(1, remoteorg.getTreecode());
		sqlquery.setString(2, remoteorg.getComments());
		sqlquery.setString(3, remoteorg.getName());
		sqlquery.setString(4, remoteorg.getCtime());
		sqlquery.setString(5, remoteorg.getUtime());
		sqlquery.setString(6, remoteorg.getState());
		sqlquery.setString(7, remoteorg.getCuser());
		sqlquery.setString(8, remoteorg.getMuser());
		sqlquery.setString(9, remoteorg.getParentid());
		sqlquery.setInteger(10, remoteorg.getSort());
		sqlquery.setString(11, remoteorg.getType());
		sqlquery.setString(12, remoteorg.getAppid());
		sqlquery.executeUpdate();

	}
}
