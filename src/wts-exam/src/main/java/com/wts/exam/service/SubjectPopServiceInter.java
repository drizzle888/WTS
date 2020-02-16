package com.wts.exam.service;

import com.wts.exam.domain.SubjectPop;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：题库权限服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectPopServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectPop insertSubjectpopEntity(SubjectPop entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectPop editSubjectpopEntity(SubjectPop entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjectpopEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectPop getSubjectpopEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjectpopSimpleQuery(DataQuery query);

	/**
	 * 插入用户分类权限
	 * 
	 * @param userIds
	 * @param typeIds
	 * @param functype
	 * @param currentUser
	 */
	public void insertPop(List<String> userIds, List<String> typeIds, String functype, LoginUser currentUser);
}