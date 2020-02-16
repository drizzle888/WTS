package com.farm.lucene.server;

import java.io.File;
import java.util.List;

import com.farm.lucene.adapter.DocMap;
import com.farm.lucene.common.IndexTaskDomain;

/**
 * 基于lucene的全文检索,索引引擎
 * 
 * @author wangdong
 * 
 */
public interface DocIndexInter {
	/**
	 * 做索引到内存中
	 * 
	 * @param doc
	 *            要写入的文档
	 * @param newIndexDir
	 *            新建立索引的文件夹，将来会合并到原有索引文件中
	 */
	public void indexDoc(DocMap doc) throws Exception;

	/**
	 * 关闭索引生成器 ，同時合并内存索引到文件索引中
	 */
	public void close() throws Exception;

	/**
	 * 从物理索引文件中删除索引
	 * 
	 * @param docId
	 *            索引ID字段
	 * @throws Exception
	 */
	public void deleteFhysicsIndex(String docId) throws Exception;

	/**
	 * 从物理索引文件中删除索引
	 * 
	 * @param docids
	 * @param domain
	 * @throws Exception
	 */
	public void deleteFhysicsIndex(List<String> docids, IndexTaskDomain domain) throws Exception;

	/**
	 * 合并索引
	 * 
	 * @param dir
	 *            file
	 */
	public void mergeIndex();
}
