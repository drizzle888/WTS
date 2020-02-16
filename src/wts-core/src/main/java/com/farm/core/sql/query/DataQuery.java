package com.farm.core.sql.query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.farm.core.auth.util.AuthenticateInter;
import com.farm.core.auth.util.AuthenticateProvider;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.DataResults;
import com.farm.util.spring.HibernateSessionFactory;

/**
 * 数据库查询封装类
 * 
 * @author 王东
 * @version 2012
 * @version 2014-10-29 重构：1.缓存带分页2.重构代码3.缓存更新时使用线程
 * 
 */
public class DataQuery {
	static final Logger log = Logger.getLogger(DataQuery.class);
	private final static int CACHE_MAX_SIZE = 5000;

	/**
	 * 缓存器单位枚举值
	 * 
	 * @author Administrator
	 * 
	 */
	public enum CACHE_UNIT {
		/**
		 * 毫秒
		 */
		Millisecond(1), /**
						 * 秒
						 */
		second(1000), /**
						 * 分
						 */
		minute(1000 * 60);
		public int num;

		CACHE_UNIT(int val) {
			num = val;
		}
	}

	private int pagesize = 10;
	private boolean isCount = true;
	private String currentPage;// 当前页
	private String sortTitleText;// 排序字段ok
	private String sortTypeText;// 排序类型ok
	private String ruleText;// 查询条件
	private String titles;// 结果集中展示的字段
	private String tables;// 表名或者内建视图
	private String userWhere;// 自定义查询条件
	private DBSort defaultSort;// 默认排序条件
	private String countSql;
	protected static final Map<String, DataResult> resultCache = new HashMap<String, DataResult>();// 结果集合的缓存
	private boolean DISTINCT = false;
	private boolean ruleValTrim = true;// 是否启用自动去除查询条件值得两边空格
	private long cacheMillisecond;// 启用缓存(毫秒数)：只要该数字大于0则表示启用，从缓存区读取数据如果缓存区没有数据则数据库查询并更新到缓冲区
	protected List<DBSort> sort = new ArrayList<DBSort>();
	protected List<DBRule> queryRule = new ArrayList<DBRule>();

	/**
	 * 最终执行的sql
	 */
	private String realSql;
	/**
	 * 最终执行的sql
	 */
	private String realCountSql;

	/**
	 * 返回缓存信息
	 * 
	 * @return
	 */
	public static Map<String, Object> getCacheInfo() {
		Map<String, Object> map = new HashMap<>();
		map.put("DATAQUERY.CACHE.CURRENT.SIZE", resultCache.size());
		map.put("DATAQUERY.CACHE.MAX.SIZE", CACHE_MAX_SIZE);
		return map;
	}

	/**
	 * 清理缓存
	 */
	public static void clearCache() {
		resultCache.clear();
	}

	/**
	 * 获得查询对象实例
	 * 
	 * @param currentPage
	 *            当前页
	 * @param titles
	 *            展现字段
	 * @param tables
	 *            表描述
	 * @return
	 */
	public static DataQuery getInstance(String currentPage, String titles, String tables) {
		DataQuery query = new DataQuery();
		query.setCurrentPage(currentPage);
		query.setTitles(titles);
		query.setTables(tables);
		return query;
	}

	public static DataQuery getInstance(int currentPage, String titles, String tables) {
		return getInstance(String.valueOf(currentPage), titles, tables);
	}

	public static DataQuery getInstance() {
		return new DataQuery();
	}
	public static DataQuery getInstance(int i, String sql) {
		// 把titles拆分出来
		String titles = sql.substring(sql.toUpperCase().indexOf("SELECT") + "SELECT".length(),
				sql.toUpperCase().indexOf("FROM"));
		// 构造tables
		String tables = "(" + sql + ") DATAQUERY_SUBSTITUTE";
		titles = StringUtils.join(DataResults.getTitles(titles.toUpperCase()).toArray(), ",");
		return getInstance(i, titles, tables);
	}

	/**
	 * 执行查询,该方法适用于sever中，没有对用于页面状态的序列化，没有缓存功能
	 * 
	 * @param session
	 * @return
	 * @throws SQLException
	 */
	public DataResult search(Session session) throws SQLException {
		DataResult result = null;
		try {
			String key = null;
			long startTime = System.currentTimeMillis();
			// -------------------缓存查询----------------------------
			if (cacheMillisecond > 0) {
				key = getQueryMD5Key(this);
				if (key != null) {
					// 启用缓存功能
					{
						DataResult dr = resultCache.get(key);
						// 是否已经有缓存
						if (dr != null) {
							long time = new Date().getTime() - dr.getCtime().getTime();
							// 判断缓存是否超时
							if (time < cacheMillisecond) {
								// 缓存可用
								return dr;
							} else {
								// 启动线程的时候就预先更新缓存时间避免在线程运行中启动多次线程
								dr.setCtime(new Date());
								// 超时也返回过时的，并启动线程查询
								Searcher search = new Searcher(this);
								Thread searchThread = new Thread(search);
								searchThread.start();
								return dr;
							}
						}
					}
				}
				// 缓存中超出缓存最大值的时候就将缓存清空
				if (resultCache.size() > CACHE_MAX_SIZE) {
					resultCache.clear();
				}
			}
			// -------------------缓存查询----------------------------
			if (sort.size() <= 0) {
				sort.add(defaultSort);
			}

			try {
				Searcher search = new Searcher(this);
				result = search.doSearch(session);
				result.setTitles(DataResults.getTitles(titles));
				result.setSortTitleText(sortTitleText);
				result.setSortTypeText(sortTypeText);
			} catch (Exception e) {
				throw new SQLException(e);
			}
			if (cacheMillisecond > 0) {
				// 启用缓存功能,将当前结果存入缓存
				if (key != null) {
					result.setCtime(new Date());
					resultCache.put(key, result);
				}
			}
			{
				// 写日志
				long endTime = System.currentTimeMillis();
				long executeTime = endTime - startTime;
				if (executeTime > 100) {
					log.warn("SQL------runTime:<" + executeTime + "ms>----------------------------------");
					log.warn("SQL-RealSql-----:" + getRealSql());
					if (StringUtils.isNotBlank(getRealCountSql())) {
						log.warn("SQL-RealCountSql:" + getRealCountSql());
					}
				} else {
					log.debug("SQL------runTime:--<" + executeTime + "ms>--");
				}
			}
		} catch (Exception e) {
			log.warn("SQL-ERROR-----:" + e.getMessage());
			log.warn("SQL-ERROR-Sql-----:" + getRealSql());
			if (StringUtils.isNotBlank(getRealCountSql())) {
				log.warn("SQL-ERROR-CountSql:" + getRealCountSql());
			}
			throw e;
		}
		return result;
	}

	/**
	 * 执行查询,序列化页面状态,可配置为缓存
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DataResult search() throws SQLException {
		Session session = HibernateSessionFactory.getSession();
		DataResult result = null;
		try {
			result = search(session);
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 将查询对象转换为MD5验证码
	 * 
	 * @param query
	 * @return
	 */
	protected static String getQueryMD5Key(DataQuery query) {
		String sql = "";
		try {
			sql = HibernateQueryHandle.praseSQL(query) + ",PAGE:" + query.getCurrentPage();
			AuthenticateInter ai = AuthenticateProvider.getInstance();
			sql = ai.encodeMd5(sql);
		} catch (SQLException e) {
			log.error(e + e.getMessage());
			return null;
		}
		return sql;
	}

	/**
	 * 添加一个排序
	 * 
	 * @param dbsort
	 * @return
	 */
	public DataQuery addSort(DBSort dbsort) {
		sort.add(dbsort);
		return this;
	}

	/**
	 * 添加一个默认排序
	 * 
	 * @param dbsort
	 * @return
	 */
	public DataQuery addDefaultSort(DBSort dbsort) {
		defaultSort = dbsort;
		return this;
	}

	/**
	 * 清除排序
	 * 
	 * @param sort
	 */
	public DataQuery clearSort() {
		this.sort.clear();
		return this;
	}

	/**
	 * 添加一个过滤条件
	 * 
	 * @param sort
	 */
	public DataQuery addRule(DBRule rule) {
		DataQuerys.wipeVirus(rule.getValue());
		this.queryRule.add(rule);
		return this;
	}

	/**
	 * 获得一个查询条件并从query中移除该条件
	 * 
	 * @param index
	 *            查询条件索引号
	 * @return
	 */
	public DBRule getAndRemoveRule(int index) {
		DBRule dbrule = this.queryRule.get(index);
		queryRule.remove(index);
		ruleText = parseRules();
		return dbrule;
	}

	/**
	 * 获得一个查询条件并从query中移除该条件,每次获得匹配到得第一个titleName 查询条件字段名
	 * 
	 * @param titleName
	 *            查询条件字段名
	 * @return
	 */
	public DBRule getAndRemoveRule(String titleName) {
		int n = -1;
		for (int i = 0; i < queryRule.size(); i++) {
			if (queryRule.get(i).getKey().equals(titleName.toUpperCase())) {
				n = i;
			}
		}
		DBRule dbrule = null;
		if (n >= 0) {
			dbrule = getAndRemoveRule(n);
		}
		return dbrule;
	}

	/**
	 * 将条件对象集合转换为条件字符串
	 * 
	 * @return
	 */
	private String parseRules() {
		StringBuffer sb = null;
		for (DBRule node : queryRule) {
			// parentid:=:NONE_,_
			if (sb == null) {
				sb = new StringBuffer();
			} else {
				sb.append("_,_");
			}
			sb.append(node.getKey());
			sb.append(":");
			sb.append(node.getComparaSign());
			sb.append(":");
			sb.append(node.getValue());
		}
		if (sb == null) {
			return "";
		} else {
			return sb.toString();
		}
	}

	/**
	 * 清除排序
	 * 
	 * @param sort
	 */
	public DataQuery clearRule() {
		this.queryRule.clear();
		return this;
	}

	/**
	 * 是否在SQL中加入distinct
	 * 
	 * @param var
	 *            true||false
	 */
	public DataQuery setDistinct(boolean var) {
		DISTINCT = var;
		return this;
	}

	/**
	 * 是否加入了distinct关键字
	 * 
	 * @return
	 */
	public boolean isDistinct() {
		return DISTINCT;
	}

	// pojo-------------------------------
	/**
	 * 获得每页记录条数
	 * 
	 * @return
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * 设置每页记录条数
	 * 
	 * @param pagesize
	 */
	public DataQuery setPagesize(int pagesize) {
		this.pagesize = pagesize;
		return this;
	}

	/**
	 * 获得当前页
	 * 
	 * @return
	 */
	public String getCurrentPage() {
		if (currentPage == null || currentPage.trim().length() <= 0) {
			return "1";
		}
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 */
	public DataQuery setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 */
	public DataQuery setCurrentPage(int currentPage) {
		this.currentPage = String.valueOf(currentPage);
		return this;
	}

	/**
	 * 获得排序字段
	 * 
	 * @return
	 */
	public String getSortTitleText() {
		return sortTitleText;
	}

	/**
	 * 获得排序类型
	 * 
	 * @return
	 */
	public String getSortTypeText() {
		return sortTypeText;
	}

	/**
	 * 设置排序字段,但是会清理掉已有排序
	 * 
	 * @param sortTitleText
	 */
	public void setSortTitleText(String sortTitleText) {
		this.sortTitleText = sortTitleText;
		if (this.sortTitleText != null && this.sortTypeText != null) {
			sort.clear();
			sort.add(new DBSort(this.sortTitleText, this.sortTypeText));
		}
	}

	/**
	 * 设置排序类型
	 * 
	 * @param sortTypeText
	 *            ASC||DESC
	 */
	public void setSortTypeText(String sortTypeText) {
		if (sortTypeText != null && sortTypeText.toUpperCase().trim().equals("NULL")) {
			sortTypeText = null;
		}
		this.sortTypeText = sortTypeText;
		if (this.sortTitleText != null && this.sortTypeText != null) {
			sort.clear();
			sort.add(new DBSort(this.sortTitleText, this.sortTypeText));
		}
	}

	/**
	 * 获得条件描述字符串
	 * 
	 * @return
	 */
	public String getRuleText() {
		return ruleText;
	}

	/**
	 * 设置查询条件，但是会清理掉已有条件
	 * 
	 * @param ruleText
	 *            查询条件
	 */
	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
		List<DBRule> list = null;
		if (!checkStringForLimitDomain(ruleText)) {
			list = new ArrayList<DBRule>();
		} else {
			ruleText = ruleText.replace("_D_", ":");
			String[] strarray = ruleText.split("_,_");
			list = new ArrayList<DBRule>();
			for (String onestr : strarray) {
				if (onestr != null && !onestr.trim().equals("")) {
					String[] valueT = onestr.split(":");
					if (valueT.length >= 3 && valueT[0] != null && valueT[1] != null && valueT[2] != null) {
						if (!valueT[0].equals("") && !valueT[0].equals("") && !valueT[0].equals("")) {
							DBRule dbrule = new DBRule(valueT[0], (ruleValTrim ? valueT[2].trim() : valueT[2]),
									valueT[1]);
							list.add(dbrule);
						}
					}
				}
			}
		}
		queryRule.clear();
		queryRule.addAll(list);
	}

	/**
	 * 获得结果集字段
	 * 
	 * @return
	 */
	public String getTitles() {
		return titles;
	}

	/**
	 * 设置结果集字段
	 * 
	 * @return
	 */
	public void setTitles(String titles) {
		this.titles = titles;
	}

	/**
	 * 获得表描述
	 * 
	 * @return
	 */
	public String getTables() {
		return tables;
	}

	/**
	 * 设置表描述
	 * 
	 * @param tables
	 */
	public void setTables(String tables) {
		this.tables = tables;
	}

	/**
	 * 对一个查询条件类型的数据进行验证
	 * 
	 * @param str
	 * @return
	 */
	private boolean checkStringForLimitDomain(String str) {
		if (str == null)
			return false;
		else
			return true;
	}

	/**
	 * 获得用户自定义查询条件
	 * 
	 * @return
	 */
	public String getUserWhere() {
		return userWhere;
	}

	/**
	 * 设置自定义条件
	 * 
	 * @param userWhere
	 *            需要添加 AND关键字
	 */
	public void setSqlRule(String sql) {
		this.userWhere = sql;
	}

	/**
	 * 增加自定义条件
	 * 
	 * @param userWhere
	 *            需要添加 AND关键字
	 */
	public DataQuery addSqlRule(String SQLString) {
		if (this.userWhere == null) {
			this.userWhere = "";
		}
		this.userWhere = this.userWhere + SQLString;
		return this;
	}

	/**
	 * 初始化查询类
	 * 
	 * @param query
	 *            对象引用
	 * @param tables
	 *            表
	 * @param titles
	 *            字段
	 */
	public static DataQuery init(DataQuery query, String tables, String titles) {
		if (query == null) {
			query = new DataQuery();
		}
		query.setTables(tables);
		query.setTitles(titles);
		if (query.sortTypeText == null) {
			query.sortTypeText = "asc";
		}
		if (query.getCurrentPage() == null) {
			query.setCurrentPage("1");
		}
		// 删除所有排序字段不合乎要求的
		{
			int n = 0;
			List<Integer> indexArray = new ArrayList<Integer>();
			for (; n < query.sort.size(); n++) {
				if (query.sort.get(n).getSortTitleText() == null
						|| query.sort.get(n).getSortTitleText().trim().toUpperCase().equals("NULL")) {
					indexArray.add(n);
				}
			}
			for (Integer index : indexArray) {
				query.sort.remove(index.intValue());
			}
		}

		return query;
	}

	/**
	 * 获得默认排序
	 * 
	 * @return
	 */
	public DBSort getDefaultSort() {
		return defaultSort;
	}

	/**
	 * 设置数据缓存
	 * 
	 * @param cachetime
	 *            缓存时间长
	 * @param cache_unit
	 *            缓存时间单位
	 */
	public void setCache(int cachetime, CACHE_UNIT cache_unit) {
		this.cacheMillisecond = cachetime * cache_unit.num;
	}

	/**
	 * 设置默认排序
	 * 
	 * @param defaultSort
	 */
	public void setDefaultSort(DBSort defaultSort) {
		this.defaultSort = defaultSort;
	}

	/**
	 * 获得查询条件格式序列
	 * 
	 * @return
	 */
	public List<DBRule> getQueryRule() {
		return queryRule;
	}

	/**
	 * 只查询结果不计算总页数
	 */
	public void setNoCount() {
		this.isCount = false;
	}

	/**
	 * 是执行记录条数统计COUNT（*）的SQL语句
	 * 
	 * @return
	 */
	public boolean isCount() {
		return isCount;
	}

	public String getCountSql() {
		return countSql;
	}

	/**
	 * 如果传入该参数则按照该SQL进行结果集数量查询
	 * 
	 * @param countSql
	 */
	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	/**
	 * 获取一个查询条件
	 * 
	 * @param string
	 * @return
	 */
	public String getRule(String key) {
		for (DBRule rule : this.queryRule) {
			if (rule.getKey().equals(key)) {
				return rule.getValue();
			}
		}
		return null;
	}

	public String getRealSql() {
		return realSql;
	}

	public void setRealSql(String realSql) {
		this.realSql = realSql;
	}

	public String getRealCountSql() {
		return realCountSql;
	}

	public void setRealCountSql(String realCountSql) {
		this.realCountSql = realCountSql;
	}

}
