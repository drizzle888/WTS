package com.wts.exam.service;

import com.wts.exam.domain.SubjectUserOwn;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户题库服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectUserOwnServiceInter {
	/**
	 * 添加一道用戶完成的題
	 * 
	 * @param subjectid
	 * @param isRight
	 * @param user
	 */
	public void addFinishSubject(String subjectid, Boolean isRight, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjectuserownEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectUserOwn getSubjectuserownEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjectuserownSimpleQuery(DataQuery query);

	/**
	 * 获得收藏数量
	 * 
	 * @param subjectId
	 * @param userid
	 * @return
	 */
	public int getBookNum(String subjectId, String userid);

	/**執行收藏或取消收藏
	 * @param subjectId
	 * @param userid
	 * @return
	 */
	public boolean doBook(String subjectId, String userid);

	/**是否收藏
	 * @param subjectId
	 * @param userid
	 * @return
	 */
	public boolean isBook(String subjectId, String userid);

}