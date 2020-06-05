package com.wts.exam.service;

import com.wts.exam.domain.CardHis;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：答题卡历史记录服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface CardHisServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public CardHis insertCardhisEntity(CardHis entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public CardHis editCardhisEntity(CardHis entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteCardhisEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public CardHis getCardhisEntity(String id);

	/**
	 * 查询归档数据
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery createHisQuery(DataQuery query);

	/**
	 * 查询非归档数据
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery createLiveQuery(DataQuery query);

	/**
	 * 备份答题室
	 * 
	 * @param roomid
	 * @param currentUser
	 * @throws Exception
	 */
	public void backup(String roomid, LoginUser currentUser) throws Exception;

	/**
	 * 查詢用戶得分記錄
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery createUserCardQuery(DataQuery query);

	/**删除某条成绩
	 * @param hiscardid
	 * @param currentUser
	 */
	public void deleteCardhis(String cardhisid, LoginUser currentUser);
}