package com.wts.exam.service;

import com.wts.exam.domain.RandomItem;
import com.wts.exam.domain.RandomStep;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：答卷随机规则服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface RandomItemServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public RandomItem insertRandomitemEntity(RandomItem entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public RandomItem editRandomitemEntity(RandomItem entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteRandomitemEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public RandomItem getRandomitemEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createRandomitemSimpleQuery(DataQuery query);

	/**
	 * 獲得所有可用的隨機項
	 * 
	 * @return
	 */
	public List<RandomItem> getLiveRandomItems();

	/**
	 * 獲得规则步骤
	 * 
	 * @param itemid
	 * @return
	 */
	public List<RandomStep> getSteps(String itemid);
}