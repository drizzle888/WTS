package com.wts.exam.service;

import com.wts.exam.domain.PaperUserOwn;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户答卷服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface PaperUserOwnServiceInter {

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public PaperUserOwn editPaperuserownEntity(PaperUserOwn entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deletePaperuserownEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public PaperUserOwn getPaperuserownEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPaperuserownSimpleQuery(DataQuery query);

	/**
	 * 添加用户随机答卷记录
	 * 
	 * @param paperId
	 * @param roomId
	 * @param user
	 */
	public void addDoPaperInfo(String paperId, int RPcent, LoginUser user);

	/**
	 * 添加用户考试答卷记录
	 * 
	 * @param cardId
	 * @param user
	 */
	public void addDoPaperInfo(String cardId, LoginUser user);

	/**
	 * 更新用户答卷得分()
	 * 
	 * @param allnum
	 * @param rightnum
	 * @param score
	 * @param allscore
	 */
	public void refreshScore(String cardId, Float allnum);

	/**執行收藏或取消收藏
	 * @param roomid
	 * @param paperid
	 * @param userid
	 * @return
	 */
	public boolean doBook(String roomid, String paperid, LoginUser user);
	/**是否收藏
	 * @param paperid
	 * @param userid
	 * @return
	 */
	public boolean isBook(String paperid, String userid);

}