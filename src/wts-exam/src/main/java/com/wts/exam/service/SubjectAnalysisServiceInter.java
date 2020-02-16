package com.wts.exam.service;

import com.wts.exam.domain.SubjectAnalysis;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试题解析服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectAnalysisServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectAnalysis insertSubjectAnalysisEntity(SubjectAnalysis entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectAnalysis editSubjectAnalysisEntity(SubjectAnalysis entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjectAnalysisEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectAnalysis getSubjectAnalysisEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjectAnalysisSimpleQuery(DataQuery query);

	/**
	 * 获得题目的解析
	 * 
	 * @param subjectid
	 * @return
	 */
	public List<SubjectAnalysis> getSubjectAnalysies(String subjectid);
}