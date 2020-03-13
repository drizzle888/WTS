package com.wts.exam.service;
import java.util.List;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;
import com.wts.exam.domain.ExamPop;

/* *
 *功能：考试中各种权限验证得服务接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ExamPopsServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public ExamPop insertExampopEntity(ExamPop entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public ExamPop editExampopEntity(ExamPop entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteExampopEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ExamPop getExampopEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createExampopSimpleQuery(DataQuery query);

	/**
	 * 插入用户分类权限
	 * 
	 * @param userIds
	 * @param typeIds
	 * @param functype
	 * @param currentUser
	 */
	public void insertPop(List<String> userIds, List<String> typeIds, String functype, LoginUser currentUser);
	/**
	 * 判断不是判卷人
	 * 
	 * @param roomId
	 *            房间id
	 * @param currentUser
	 *            当前人
	 * @return
	 */
	boolean isNotJudger(String roomId, LoginUser currentUser);

	/**
	 * 判断是判卷人
	 * 
	 * @param roomId
	 * @param currentUser
	 * @return
	 */
	boolean isJudger(String roomId, LoginUser currentUser);
	/**
	 * 判断是管理人
	 * 
	 * @param roomId
	 * @param currentUser
	 * @return
	 */
	boolean isManager(String roomId, LoginUser currentUser);
}