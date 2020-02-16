package com.farm.lucene.common;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.core.sql.result.DataResult;

/**
 * 分装查询结果集合
 * 
 * @author wangdong
 * 
 */
public class IRResult {
	private Logger log = Logger.getLogger(this.getClass());
	private long runtime;

	/**
	 * 每页记录数
	 */
	private int PageSize = 10;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 条件字符串
	 */
	private String queryWhereStr;
	/**
	 * 总记录数
	 */
	private int totleSize;

	private int totalPage;

	/**
	 * 排序字段
	 */
	private String sortField;
	/**
	 * 排序类型
	 */
	private String sortType;
	/**
	 * 结果集合
	 */
	private List<Map<String, Object>> resultList;

	/**
	 * 排序子句
	 */
	private String orderBy;

	/**
	 * 条件子句
	 */
	private String whereStr;

	@Deprecated
	public IRResult() {
	}

	/**
	 * @return 开始记录数
	 */
	public int getFirstSize() {
		return (currentPage - 1) * PageSize;
	}

	public static IRResult getInstance(int currentPage) {
		IRResult domain = new IRResult();
		if (currentPage == 0) {
			currentPage = 1;
		}
		domain.currentPage = currentPage;

		return domain;
	}

	public static IRResult getInstance() {
		IRResult domain = new IRResult();
		domain.currentPage = 1;
		return domain;
	}

	public static IRResult getInstance(int currentPage, String sortField) {
		IRResult domain = new IRResult();
		domain.currentPage = currentPage;
		domain.sortField = sortField;
		return domain;
	}

	public static IRResult getInstance(int currentPage, String sortField,
			String sortType) {
		IRResult domain = new IRResult();
		domain.currentPage = currentPage;
		domain.sortField = sortField;
		domain.sortType = sortType;
		return domain;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		try {
			setCurrentPage(Integer.valueOf(currentPage));
		} catch (Exception e) {
			log.warn(e.getMessage());
			setCurrentPage(1);
		}
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotleSize() {
		return totleSize;
	}

	public void setTotleSize(int totleSize) {
		this.totleSize = totleSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	public int getPageSize() {
		return PageSize;
	}

	public String getQueryWhereStr() {
		return queryWhereStr;
	}

	public void setQueryWhereStr(String queryWhereStr) {
		this.queryWhereStr = queryWhereStr;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getWhereStr() {
		return whereStr;
	}

	public void setWhereStr(String whereStr) {
		this.whereStr = whereStr;
	}

	public long getRuntime() {
		return runtime;
	}

	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public DataResult getDataResult() {
		DataResult result= DataResult.getInstance(getResultList(), getTotleSize(),
				getCurrentPage(), getPageSize());
		result.setRuntime(this.runtime);
		return result;
	}
}
