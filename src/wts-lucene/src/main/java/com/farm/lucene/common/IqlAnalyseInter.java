package com.farm.lucene.common;

import java.util.List;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;

/**
 * 全文检索语法分析器（WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC)）
 * 
 * @author wangdong
 * 
 */
public interface IqlAnalyseInter {
	// "查询字段", "查询词",记录数
	/**
	 * 获得要搜索的字段
	 * 
	 * @return
	 */
	public String[] getLimitTitle();

	/**
	 * 获得要搜索的字段值
	 * 
	 * @return
	 */
	public String getLimitValue();

	/**
	 * 获得排序规则
	 * 
	 * @return
	 */
	public Sort getSortTitle();

	/**
	 * 最大要查到的记录数
	 * 
	 * @return
	 */
	public int getMaxTopNum();

	/**
	 * 获得所要的分页结果
	 * 
	 * @param allDoc
	 *            查到的全部结果
	 * @return
	 */
	public ScoreDoc[] subDoc(ScoreDoc[] allDoc);

	/**
	 * 获得所要的分页结果
	 * 
	 * @param allDoc
	 *            查到的全部结果
	 * @return
	 */
	public List<ScoreDoc> subDoc(List<ScoreDoc> allDoc);

	/**
	 * 获得IQL
	 * 
	 * @return
	 */
	public String getIQL();
}
