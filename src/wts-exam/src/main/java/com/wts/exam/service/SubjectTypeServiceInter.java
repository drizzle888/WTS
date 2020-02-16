package com.wts.exam.service;

import com.wts.exam.domain.SubjectType;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.easyui.EasyUiTreeNode;

import java.util.List;
import java.util.Set;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考题分类服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectTypeServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectType insertSubjecttypeEntity(SubjectType entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectType editSubjecttypeEntity(SubjectType entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjecttypeEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectType getSubjecttypeEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjecttypeSimpleQuery(DataQuery query);

	/**
	 * 移动分类
	 * 
	 * @param orgId
	 * @param targetOrgId
	 */
	public void moveTreeNode(String orgId, String targetOrgId, LoginUser currentUser);

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
	 *            1使用权、2维护权
	 * @return
	 */
	public Set<String> getUserPopTypeids(String userId, String funtype);

	/**
	 * 用戶是否有分類的權限
	 * 
	 * @param typeid
	 * @param funtype
	 *            1使用权、2维护权
	 * @param userid
	 * @return
	 */
	public boolean isHavePop(String typeid, String funtype, String userid);
}