package com.farm.doc.server;

import com.farm.doc.domain.Messagemodel;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：消息模板服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface MessagemodelServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Messagemodel insertMessagemodelEntity(Messagemodel entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Messagemodel editMessagemodelEntity(Messagemodel entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteMessagemodelEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Messagemodel getMessagemodelEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createMessagemodelSimpleQuery(DataQuery query);

	/**
	 * 初始化消息模板
	 * 
	 * @param currentUser
	 */
	public void initModel(LoginUser currentUser);

	/**
	 * 修改模板状态
	 * 
	 * @param modelid
	 * @param isAble
	 * @param currentUser
	 */
	public void editState(String modelid, boolean isAble, LoginUser currentUser);

	/**
	 * 修改模板
	 * 
	 * @param titlemodel
	 *            标题模板
	 * @param contentmodel
	 *            内容模板
	 * @param content
	 *            备注
	 * @param currentUser
	 * @return
	 */
	public Messagemodel editMessagemodelEntity(String id, String titlemodel, String contentmodel, String pcontent,
			LoginUser currentUser);

	/**
	 * 创建一个基本查询用来查询模型的抄送人
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createMessagemodelSendersQuery(DataQuery query);

	/**
	 * 为模型添加一个抄送人
	 * 
	 * @param modelid
	 * @param userid
	 */
	public void addModelSender(String modelid, String userid);

	/**
	 * 为模型删除一个抄送人
	 * 
	 * @param id
	 *            FARM_MESSAGE_SENDER記錄id
	 */
	public void delModelSender(String id);
}