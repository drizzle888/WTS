package com.farm.authority.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.farm.authority.domain.Pop;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：权限控制集合，可以配置任意权限与（人、岗位、组织机构间的关系）
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface PopServiceInter {

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Pop getPopEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPopSimpleQuery(DataQuery query);

	/**
	 * 添加一个用户到 分类权限
	 * 
	 * @param typeid
	 * @param userid
	 * @param audit
	 */
	public void addUserPop(String targetId, String targetName, String oid, String targetType, LoginUser user);

	/**
	 * 添加一个组织机构到 分类权限
	 * 
	 * @param typeid
	 * @param orgid
	 * @param read
	 */
	public void addOrgPop(String targetId, String targetName, String oid, String targetType, LoginUser user);

	/**
	 * 添加一个岗位到 分类权限
	 * 
	 * @param typeid
	 * @param orgid
	 * @param read
	 */
	public void addPostPop(String targetId, String targetName, String oid, String targetType, LoginUser user);

	/**
	 * 删除一个分类权限
	 * 
	 * @param typepopid
	 */
	public void delPop(String popId, LoginUser user);

	/**
	 * 判断用户是否拥有一个权限(会查找用户的组织机构和岗位，如果该授权对象没有被授予过权限则所有用户均可以访问)【带session缓存】
	 * 
	 * @param session
	 *            用户的session
	 * @param targetId
	 *            应用授权对象ID
	 * @param defaultAble
	 *            默认情况下（该授权对象未设置过权限时）是否所有人拥有权限，true为所有人拥有权限(建议为true)，false所有人没有权限
	 * @return
	 */
	public boolean isUserHaveTargetid(HttpSession session, String targetId, boolean defaultAble);

	/**
	 * 获得用户拥有的Targetid(会查找用户的组织机构和岗位)查找用户是否有有该id的使用权限
	 * 
	 * @param userid
	 *            用户的Id
	 * @return
	 */
	public List<String> getUserTargetid(String userid);
}