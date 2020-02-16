package com.farm.doc.server;

import com.farm.doc.domain.Usermessage;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.Message;

/* *
 *功能：用户消息服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface UsermessageServiceInter {

	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Usermessage insertUsermessageEntity(Usermessage entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Usermessage editUsermessageEntity(Usermessage entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteUsermessageEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Usermessage getUsermessageEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return ID,SENDUSERNAME,USERMESSAGECTIME,
	 *         READUSERID,CONTENT,TITLE,READSTATE,PCONTENT,PSTATE,CUSERNAME,
	 *         CUSER,CTIME,READUSERNAME
	 */
	public DataQuery createUsermessageSimpleQuery(DataQuery query);

	/**
	 * 设置为阅读
	 * 
	 * @param id
	 *            void
	 */
	public void setRead(String id);

	/**
	 * 获取我的消息
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageSize
	 *            每页多少条
	 * @param currPage
	 *            当前第几页
	 * @return DataResult
	 */
	public DataResult getMyMessageByUser(String userId, int pageSize, Integer currPage);

	/**
	 * 获取未读的消息
	 * 
	 * @param userId
	 * @param pageSize
	 * @param currPage
	 * @return
	 */
	public DataResult getUnreadMessagesByUser(String userId, int pageSize, int currPage);

	/**
	 * 设置用户的所有消息为已读状态
	 * 
	 * @param currentUser
	 * @return
	 */
	public int setReadAllMessageByUser(LoginUser currentUser);

	/**
	 * 发送消息
	 * 
	 * @param type
	 * @param message
	 *            消息
	 * @param readUserId
	 *            读者
	 * @param note
	 *            备注
	 * @param sendUser
	 *            触发者
	 */
	public void sendMessage(Message message, String readUserId, String note, LoginUser sendUser);

	/**
	 * 发送消息
	 * 
	 * @param message
	 *            消息
	 * @param readUserId
	 *            读者
	 * @param sendUser
	 *            触发者
	 */
	public void sendMessage(Message message, String readUserId, LoginUser sendUser);

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @param readUserId
	 *            收件人
	 */
	public void sendMessage(Message message, String readUserId);

	/**
	 * 发送消息给一些人
	 * 
	 * @param type
	 * @param message
	 * @param readUserIds
	 *            收件人列表
	 */
	public void sendMessage(Message message, List<String> readUserIds);

	/**
	 * 发送消息给一些人
	 * 
	 * @param message
	 * @param userids
	 *            收件人列表
	 */
	public void sendMessage(Message message, List<String> readUserIds, LoginUser curentUser);

	/**
	 * 查询用户信息，用于消息发送（包含用户、组织机构、岗位、小组信息等）
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery createUserConsolesQuery(DataQuery query);

}