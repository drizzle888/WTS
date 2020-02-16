package com.farm.lucene.server;

import com.farm.lucene.common.IRResult;
import com.farm.lucene.common.ScoreDocFilterInter;

public interface DocQueryInter {
	/**
	 * 配置标红处理的白名单，这里配置的字段不会被截取和标红处理
	 */
	public static String[] NO_SPLIT_TITLE={"TAGKEY"}; 

	/**
	 * 查询结果集合从索引中WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC)
	 * @param Iql 查詢語句
	 * @param currentPage 
	 * @param pagesize
	 * @param filter 過濾器可以用來處理權限
	 * @return
	 * @throws Exception
	 */
	IRResult queryByMultiIndex(String Iql, int currentPage, int pagesize, ScoreDocFilterInter filter) throws Exception;
	
	/**
	 * 查询结果集合从索引中WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC)
	 * @param Iql 查詢語句
	 * @param currentPage 
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	IRResult queryByMultiIndex(String Iql, int currentPage, int pagesize) throws Exception;
}
