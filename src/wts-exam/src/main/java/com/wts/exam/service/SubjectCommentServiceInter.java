package com.wts.exam.service;

import com.wts.exam.domain.SubjectComment;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试题评论服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectCommentServiceInter {

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjectCommentEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectComment getSubjectCommentEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjectCommentSimpleQuery(DataQuery query);

	/**
	 * 获得题评论
	 * 
	 * @param subjectid
	 * @return
	 */
	public List<SubjectComment> getSubjectComments(String subjectid);

	/**
	 * 发表评论
	 * 
	 * @param text
	 * @param subjectid
	 * @param currentUser
	 */
	public SubjectComment insertSubjectComment(String text, String subjectid, LoginUser currentUser);
}