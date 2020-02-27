package com.wts.exam.service;

import com.wts.exam.domain.ExamStat;
import com.farm.core.sql.query.DataQuery;
import com.farm.authority.domain.User;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户答题统计服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ExamStatServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public ExamStat insertExamstatEntity(ExamStat entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public ExamStat editExamstatEntity(ExamStat entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteExamstatEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ExamStat getExamstatEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createExamstatSimpleQuery(DataQuery query);

	/**
	 * 用戶完成一道題
	 * 
	 * @param subjectid
	 * @param isRight
	 * @param user
	 */
	public ExamStat addFinishSubjectNum(String subjectid, Boolean isRight, LoginUser user);

	/**
	 * 记录一次答卷考试
	 * 
	 * @param subjectid
	 * @param isRight
	 * @param user
	 * @return
	 */
	public ExamStat addStartCardNum(String cardId, LoginUser user);

	/**
	 * 记录一次答卷练习
	 * 
	 * @param subjectid
	 * @param isRight
	 * @param user
	 * @return
	 */
	public ExamStat addStartTestNum(String paperid, LoginUser user);

	/**
	 * 获得用户的考试统计
	 * 
	 * @param user
	 * @return
	 */
	public ExamStat getExamstatEntity(User user);
}