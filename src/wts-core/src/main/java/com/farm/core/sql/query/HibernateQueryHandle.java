package com.farm.core.sql.query;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.core.time.TimeTool;
import com.farm.util.web.WaterOfForest;

/**
 * hibernate的帮助类
 * 
 * @author 王东
 * @date 2012-12-30
 */
public class HibernateQueryHandle {
	private static final String SqlSelectSize = " select count(*) num "; // 默认查询数量头

	/**
	 * sql查询
	 * 
	 * @param session
	 *            hibernateSession
	 * @param sql
	 * @param pagesize
	 *            每页多少条
	 * @param currentPage
	 *            当前页
	 * @return
	 * @throws SQLException
	 */
	protected static DataResult runSqlQuery(Session session, String querysql, String countsql, int pagesize,
			int currentPage) throws SQLException {
		long startTime = new Date().getTime();
		if (querysql.indexOf("*") > 0) {
			throw new SQLException("*不能存在于查询语句中，请明确查询字段!");
		}
		querysql = querysql.toUpperCase();
		DataResult result = null;
		int firstResourt;// 开始条数
		int sizeResourt;// 单页显示
		sizeResourt = pagesize;
		firstResourt = (currentPage - 1) * sizeResourt;
		String titles = querysql.substring(0, querysql.indexOf("FROM"));
		List<Map<String, Object>> limap = DataResults.getMaps(titles,
				runLimitQuery(session, querysql, firstResourt, sizeResourt));
		result = DataResult.getInstance(limap, countsql == null ? limap.size() : runLimitQueryNum(session, countsql),
				currentPage, pagesize);
		long endTime = new Date().getTime();
		result.setRuntime(endTime - startTime);
		return result;
	}

	/**
	 * DataQuery查询
	 * 
	 * @param session
	 *            hibernate会话
	 * @param dataquery
	 * @return
	 * @throws SQLException
	 */
	protected static DataResult runDataQuery(Session session, DataQuery dataquery) throws SQLException {
		long startTime = new Date().getTime();
		DataResult result = null;
		try {
			int firstResourt;// 开始条数
			int sizeResourt;// 单页显示
			String upsql = praseSQL(dataquery);
			String partSql = upsql.substring(upsql.indexOf(" FROM "));
			String headsql = upsql.substring(0, upsql.indexOf(" FROM "));
			if (headsql.indexOf("*") >= 0) {
				throw new SQLException("select can't contain *");
			}
			sizeResourt = dataquery.getPagesize();
			firstResourt = (Integer.valueOf(dataquery.getCurrentPage().trim()) - 1) * sizeResourt;
			// 将一个pageDomain中的list<object>解析为map<String,String>
			dataquery.setRealSql(upsql);
			List<Map<String, Object>> limap = DataResults.getMaps(dataquery.getTitles(),
					runLimitQuery(session, upsql, firstResourt, sizeResourt));
			if (dataquery.isDistinct()) {
				if (upsql.indexOf("ORDER BY") > 0) {
					upsql = upsql.substring(0, upsql.indexOf("ORDER"));
				}
				partSql = " FROM (" + upsql + ") counum";
			}
			// 查询结果总数量
			int count = limap.size();
			if (dataquery.isCount()) {
				if (partSql.toUpperCase().indexOf("ORDER BY") > 0) {
					partSql = partSql.substring(0, partSql.toUpperCase().indexOf("ORDER BY"));
				}
				partSql = SqlSelectSize + partSql;
				String countSql = dataquery.getCountSql() == null ? partSql : dataquery.getCountSql();
				dataquery.setRealCountSql(countSql);
				count = runLimitQueryNum(session, countSql);
			}
			result = DataResult.getInstance(limap, count, Integer.valueOf(dataquery.getCurrentPage()),
					dataquery.getPagesize());
		} catch (Exception e) {
			throw new SQLException(e);
		}
		long endTime = new Date().getTime();
		result.setRuntime(endTime - startTime);
		return result;
	}


	/**
	 * 查询条件转换成sql语句
	 * 
	 * @param dataquery
	 *            查询条件封装
	 * @return
	 * @throws SQLException
	 */
	public static String praseSQL(DataQuery dataquery) throws SQLException {
		String distinct = "";
		if (dataquery.isDistinct()) {
			distinct = " distinct ";
		}
		StringBuffer SQL_run = new StringBuffer().append("select ").append(distinct)
				.append(dataquery.getTitles().toUpperCase()).append(getSql_part(dataquery));
		return upCaseSQLKEY(SQL_run.toString());
	}

	/**
	 * 将SQL关键字转换为大写
	 * 
	 * @param SQL
	 * @return
	 */
	private static String upCaseSQLKEY(String SQL) {
		SQL = SQL.replace(" select ", " SELECT ");
		SQL = SQL.replace(" from ", " FROM ");
		SQL = SQL.replace(" as ", " AS ");
		SQL = SQL.replace(" where ", " WHERE ");
		SQL = SQL.replace(" order by ", " ORDER BY ");
		return SQL;
	}

	@SuppressWarnings("unchecked")
	private static List<Object[]> runLimitQuery(Session session_, String Sql, int firstResourt, int sizeResourt) {
		List<Object[]> list = null;
		try {
			SQLQuery sqlQuery = session_.createSQLQuery(Sql);
			sqlQuery.setFirstResult(firstResourt);
			sqlQuery.setMaxResults(sizeResourt);
			list = sqlQuery.list();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	private static int runLimitQueryNum(Session session_, String countSql) {
		SQLQuery sqlQuery = session_.createSQLQuery(countSql);
		Object num = sqlQuery.list().get(0);
		Integer renum = (Integer) Integer.valueOf(num.toString());
		int n = renum.intValue();
		return n;
	}

	private static String getSql_part(DataQuery query) throws SQLException {
		String sql_part = getSql_from(query) + getSql_where(query) + getSortWhere(query);
		return sql_part;
	}

	private static String getSortWhere(DataQuery query) {
		List<DBSort> sortList = query.sort;
		StringBuffer where = new StringBuffer(" order by ");
		boolean isHaveSort = false;
		for (Iterator<DBSort> iterator = sortList.iterator(); iterator.hasNext();) {
			DBSort name = (DBSort) iterator.next();
			if (name != null && name.getSortTitleText() != null && name.getSortTypeText() != null
					&& !name.getSortTitleText().equals("") && !name.getSortTypeText().equals("")) {
				where.append(name.getSortTitleText());
				where.append(" ");
				where.append(name.getSortTypeText());
				isHaveSort = true;
				if (iterator.hasNext()) {
					where.append(" , ");
				}
			}
		}
		if (!isHaveSort) {
			return "";
		}
		return where.toString();
	}

	private static String getSql_from(DataQuery query) {
		String sql_from = " from " + query.getTables() + " ";
		return sql_from;
	}

	private static String getSql_where(DataQuery query) throws SQLException {
		if (query.queryRule == null) {
			throw new SQLException("whereList is null!");
		}
		Iterator<DBRule> it_ = query.queryRule.iterator();
		StringBuffer where_ = new StringBuffer("");
		where_.append(" where 1=1 ");
		while (it_.hasNext()) {
			DBRule _queryld = it_.next();
			if (_queryld != null && _queryld.getValue() != null)
				where_.append(_queryld.getThisLimit());
		}
		String sql_where = where_.toString();
		if (query.getUserWhere() != null && query.getUserWhere().trim().length() > 1) {
			sql_where = sql_where + " " + query.getUserWhere() + " ";
		}
		return sql_where;
	}

}
