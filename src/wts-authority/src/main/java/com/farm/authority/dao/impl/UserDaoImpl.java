package com.farm.authority.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.authority.domain.User;
import com.farm.authority.dao.UserDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/* *
 *功能：用户持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141119144919
 *说明：
 */
@Repository
public class UserDaoImpl extends HibernateSQLTools<User>implements UserDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(User user) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(user);
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
	public User getEntity(String userid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (User) session.get(User.class, userid);
	}

	@Override
	public User insertEntity(User user) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(user);
		return user;
	}

	@Override
	public void editEntity(User user) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(user);
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
	public List<User> selectEntitys(List<DBRule> rules) {
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> findUserByLoginName(String loginname) {
		String hql = "from User a where a.loginname = ?";
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, loginname);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByLoginName(String loginname, String userId) {
		String hql = "from User a where a.loginname = ? and a.id!=?";
		Session session = sessionFatory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, loginname);
		query.setString(1, userId);
		return query.list();
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@Override
	protected Class<?> getTypeClass() {
		return User.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public Integer getUsersNum() {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from ALONE_AUTH_USER where STATE=1");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public void insertSqlEntity(User remoteUser) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session
				.createSQLQuery("INSERT INTO ALONE_AUTH_USER VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?)");
		sqlquery.setString(0, remoteUser.getId());
		sqlquery.setString(1, remoteUser.getName());
		sqlquery.setString(2, remoteUser.getPassword());
		sqlquery.setString(3, remoteUser.getComments());
		sqlquery.setString(4, remoteUser.getType());
		sqlquery.setString(5, remoteUser.getCtime());
		sqlquery.setString(6, remoteUser.getUtime());
		sqlquery.setString(7, remoteUser.getCuser());
		sqlquery.setString(8, remoteUser.getMuser());
		sqlquery.setString(9, remoteUser.getState());
		sqlquery.setString(10, remoteUser.getLoginname());
		sqlquery.setString(11, remoteUser.getLogintime());
		sqlquery.setString(12, remoteUser.getImgid());
		sqlquery.executeUpdate();
	}

}
