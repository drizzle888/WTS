package com.wts.exam.service;

import com.wts.exam.domain.RoomPaper;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考试试卷服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface RoomPaperServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public RoomPaper insertRoompaperEntity(RoomPaper entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public RoomPaper editRoompaperEntity(RoomPaper entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteRoompaperEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public RoomPaper getRoompaperEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createRoompaperSimpleQuery(DataQuery query);

	/**
	 * 添加试卷到考场
	 * 
	 * @param roomid
	 * @param paperid
	 * @param currentUser
	 */
	public void addRoomPaper(String roomid, String paperid, LoginUser currentUser);

	/**
	 * 修改答卷在答题室中的别名
	 * 
	 * @param id
	 * @param name
	 * @param currentUser
	 */
	public void editOtherName(String roompaperid, String name, LoginUser currentUser);

	/**
	 * 删除答题室中答卷别名
	 * 
	 * @param id
	 * @param currentUser
	 */
	public void clearOtherName(String roompaperid, LoginUser currentUser);

	/**
	 * 获得答卷别名
	 * 
	 * @param roomid
	 * @param paperId
	 * @return
	 */
	public String getPaperAlias(String roomid, String paperId);

	/**
	 * 获得答卷别名(只能獲得未歸檔的答題卡)
	 * 
	 * @param cardId
	 * @return
	 */
	public String getPaperAlias(String cardId);
}