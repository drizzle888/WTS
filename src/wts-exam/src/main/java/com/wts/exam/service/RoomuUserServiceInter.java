package com.wts.exam.service;

import com.wts.exam.domain.RoomUser;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：参考人服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface RoomuUserServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	@Deprecated
	public RoomUser insertRoomuserEntity(RoomUser entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	@Deprecated
	public RoomUser editRoomuserEntity(RoomUser entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteRoomuserEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public RoomUser getRoomuserEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createRoomuserSimpleQuery(DataQuery query);

	/**
	 * 插入一个用户到考试中
	 * 
	 * @param id
	 * @param userid
	 * @param currentUser
	 */
	public void insertRoomUser(String id, String userid, LoginUser currentUser);
}