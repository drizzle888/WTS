package com.farm.core.sql.query;

import java.sql.SQLException;

import org.hibernate.Session;

import com.farm.core.sql.result.DataResult;
import com.farm.util.spring.HibernateSessionFactory;

public class SQLQuery {
	private String sql;
	private int pagesize;
	private int currentPage;
	private String countSql;

	private SQLQuery() {
	}

	/**
	 * @param sql
	 * @param countSql 数量查询可以为空NULL
	 * @param pagesize
	 * @param currentPage
	 * @return
	 */
	public static SQLQuery getInstance(String sql, String countSql,
			int pagesize, int currentPage) {
		SQLQuery query = new SQLQuery();
		query.setCurrentPage(currentPage);
		query.setPagesize(pagesize);
		query.setSql(sql);
		query.setCountSql(countSql);
		return query;
	}

	/**
	 * 执行查询
	 * 
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	public DataResult search(Session session) throws SQLException {
		return HibernateQueryHandle.runSqlQuery(session, sql, countSql,
				pagesize, currentPage);
	}
	

	/**
	 * 执行查询
	 * 
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	public DataResult search() throws SQLException {
		Session session = HibernateSessionFactory.getSession();
		DataResult result = null;
		try {
			result = this.search(session);
		} catch (Exception e) {
			throw new SQLException(e);
		} finally {
			session.close();
		}
		return result;
	}

	// ---------------------------------------------------------------------
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

}
