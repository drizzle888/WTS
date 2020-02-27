package com.wts.exam.service;

import com.wts.exam.domain.PaperChapter;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试卷章节服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface PaperChapterServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public PaperChapter insertPaperchapterEntity(PaperChapter entity, LoginUser user);

	public PaperChapter insertPaperchapter(String paperid, String name, int sort, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public PaperChapter editPaperchapterEntity(PaperChapter entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deletePaperchapterEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public PaperChapter getPaperchapterEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPaperchapterSimpleQuery(DataQuery query);

	/**
	 * 添加章节下的试题
	 * 
	 * @param subjectid
	 * @param chapterId
	 * @param currentUser
	 */
	public void addSubject(String subjectId, String chapterId, LoginUser currentUser);

	/**
	 * 题在章节内的排序上调
	 * 
	 * @param id
	 * @param currentUser
	 */
	public void subjectSortUp(String id, LoginUser currentUser);

	/**
	 * 通过章节名称模糊取出第一个章节
	 * 
	 * @param paperid
	 * @param name
	 * @return
	 */
	public PaperChapter getEntity(String paperid, String name);

	/**
	 * 答卷中是否已经存在某题
	 * 
	 * @param subjectId
	 * @param paperid
	 * @return
	 */
	boolean isHasSubject(String subjectId, String paperid);

}