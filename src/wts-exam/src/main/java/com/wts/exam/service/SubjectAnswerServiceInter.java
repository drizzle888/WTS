package com.wts.exam.service;

import com.wts.exam.domain.SubjectAnswer;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考题答案服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectAnswerServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectAnswer insertSubjectanswerEntity(SubjectAnswer entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectAnswer editSubjectanswerEntity(SubjectAnswer entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjectanswerEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectAnswer getSubjectanswerEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjectanswerSimpleQuery(DataQuery query);

	/**
	 * 设置答案为唯一正确答案
	 * 
	 * @param id
	 * @param currentUser
	 */
	public void setOnlyRight(String answerId, LoginUser currentUser);

	/**
	 * 設置是否為正確答案
	 * 
	 * @param answerId
	 * @param isTrue
	 * @param currentUser
	 */
	public void setRightAnswer(String answerId, boolean isTrue, LoginUser currentUser);
}