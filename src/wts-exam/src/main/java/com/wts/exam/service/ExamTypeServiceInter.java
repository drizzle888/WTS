package com.wts.exam.service;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.ex.ExamTypeUnit;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.easyui.EasyUiTreeNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考试分类服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface ExamTypeServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public ExamType insertExamtypeEntity(ExamType entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public ExamType editExamtypeEntity(ExamType entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteExamtypeEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public ExamType getExamtypeEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createExamtypeSimpleQuery(DataQuery query);

	/**
	 * 移动分类
	 * 
	 * @param orgId
	 * @param targetOrgId
	 */
	public void moveTreeNode(String orgId, String targetOrgId, LoginUser currentUser);

	/**
	 * 获得一级分类和房间(迭代所有一级分类，取出每个一级分类含其子分类的房间（最多取20个房间）)
	 * 
	 * @param loginUser
	 * @return
	 */
	public List<ExamTypeUnit> getRootTypeUnits(LoginUser loginUser);

	/**
	 * 获得分类的所有子分类id
	 * 
	 * @param typeIds
	 * @return
	 */
	public List<String> getAllSubType(List<String> typeIds);

	/**
	 * 运行权限过滤器，过滤当前用户拥有的分类权限
	 * 
	 * @param list
	 *            用户展示树
	 * @param funtype
	 *            0分类管理展示全部分类1使用权、2维护权
	 * @param currentUser
	 * @return
	 */
	public List<EasyUiTreeNode> RunPopFilter(List<EasyUiTreeNode> list, String funtype, LoginUser currentUser);

	/**
	 * 获得用户有权限的题库分类id集合
	 * 
	 * @param userId
	 * @param funtype
	 *            1:管理权限.2:判卷权限.3:查询权限.4:超级权限 1:管理权限.2:判卷权限.3:查询权限.4:超级权限
	 * @return
	 */
	public Set<String> getUserPopTypeids(String userId, String... funtype);

	/**
	 * 获得分类权限的用户
	 * 
	 * @param examtypeid
	 * @param funtype
	 *            1:管理权限.2:判卷权限.3:查询权限.4:超级权限
	 * @return map<USERID,USERNAME,ORGID,ORGNAME,TYPEID,TYPENAME>
	 */
	public List<Map<String, Object>> getTypePopUsers(String examtypeid, String funtype);
}