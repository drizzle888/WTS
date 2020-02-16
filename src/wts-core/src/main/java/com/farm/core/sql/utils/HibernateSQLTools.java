package com.farm.core.sql.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;

import com.farm.core.sql.query.DBRule;

public abstract class HibernateSQLTools<E> {

	private AbstractEntityPersister classMetadata;

	protected abstract Class<?> getTypeClass();

	protected abstract SessionFactory getSessionFactory();

	/**
	 * 在更新时创建对象键值对
	 * 
	 * @param keyValses
	 * @return Address = 'Zhongshan 23', City = 'Nanjing'
	 */
	public static String getValusSqlStr(Map<String, Object> keyValses) {
		boolean isFirst = true;
		StringBuffer returnStr = new StringBuffer();
		for (Entry<String, Object> node : keyValses.entrySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				returnStr.append(",");
			}
			returnStr.append(node.getKey());
			returnStr.append(" = ");
			returnStr.append("'");
			returnStr.append(node.getValue());
			returnStr.append("'");
		}
		return returnStr.toString();
	}

	/**
	 * 创建批量删除sql
	 * 
	 * @param session
	 * @param rules
	 *            条件序列
	 */
	public void deleteSqlFromFunction(Session session, List<DBRule> rules) {
		classMetadata = (AbstractEntityPersister) getSessionFactory()
				.getClassMetadata(getTypeClass());
		String whereStr;
		if (rules == null || rules.size() <= 0) {
			whereStr = "";
		} else {
			whereStr = " WHERE 1=1" + DBRule.makeWhereStr(rules);
		}
		SQLQuery sqlquery = session.createSQLQuery("DELETE FROM "
				+ classMetadata.getTableName() + whereStr);
		sqlquery.executeUpdate();
	}

	/**
	 * 创建批量查询SQL
	 * 
	 * @param session
	 * @param rules
	 *            条件序列
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> selectSqlFromFunction(Session session, List<DBRule> rules) {
		classMetadata = (AbstractEntityPersister) getSessionFactory()
				.getClassMetadata(getTypeClass());
		String whereStr;
		if (rules == null || rules.size() <= 0) {
			whereStr = "";
		} else {
			whereStr = " WHERE 1=1" + DBRule.makeWhereStr(rules);
		}
		Query sqlquery = session.createQuery("FROM " + getTypeClass().getName()
				+ whereStr);
		List<E> list = sqlquery.list();
		return list;
	}

	/**
	 * 创建批量更新SQL
	 * 
	 * @param sessionFatory
	 * @param values
	 * @param rules
	 */
	public void updataSqlFromFunction(Session session,
			Map<String, Object> values, List<DBRule> rules) {
		classMetadata = (AbstractEntityPersister) getSessionFactory()
				.getClassMetadata(getTypeClass());
		if (values == null || values.size() <= 0) {
			return;
		}
		String whereStr;
		if (rules == null || rules.size() <= 0) {
			whereStr = "";
		} else {
			whereStr = " WHERE 1=1" + DBRule.makeWhereStr(rules);
		}
		SQLQuery sqlquery = session.createSQLQuery("UPDATE "
				+ classMetadata.getTableName() + " SET "
				+ HibernateSQLTools.getValusSqlStr(values) + whereStr);
		sqlquery.executeUpdate();
	}

	/**
	 * 创建自定义统计SQL
	 * 
	 * @param sessionFatory
	 * @param values
	 * @param rules
	 */
	@SuppressWarnings("unchecked")
	public int countSqlFromFunction(Session session, List<DBRule> rules) {
		classMetadata = (AbstractEntityPersister) getSessionFactory()
				.getClassMetadata(getTypeClass());
		String whereStr;
		if (rules == null || rules.size() <= 0) {
			whereStr = "";
		} else {
			whereStr = " WHERE 1=1" + DBRule.makeWhereStr(rules);
		}
		SQLQuery sqlquery = session.createSQLQuery("select count(*) num FROM "
				+ classMetadata.getTableName() + whereStr);
		List<E> list = sqlquery.list();
		return Integer.valueOf(list.get(0).toString());
	}
}
