package com.wts.exam.service;

import com.wts.exam.domain.Paper;
import com.wts.exam.domain.Room;
import com.wts.exam.domain.ex.RoomUnit;
import com.farm.core.sql.query.DataQuery;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考试服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface RoomServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Room insertRoomEntity(Room entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Room editRoomEntity(Room entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteRoomEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Room getRoomEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createRoomSimpleQuery(DataQuery query);

	/**
	 * 设置考试得分类
	 * 
	 * @param roomid
	 *            考试id
	 * @param examtypeId
	 *            考试分类id
	 * @param currentUser
	 */
	public void examTypeSetting(String roomid, String examtypeId, LoginUser currentUser);

	/**
	 * 修改考试状态
	 * 
	 * @param id
	 * @param string
	 * @param currentUser
	 */
	public void editState(String id, String string, LoginUser currentUser);

	/**
	 * 获得考场的封装对象
	 * 
	 * @param roomid
	 * @param loginUser
	 * @return
	 */
	public RoomUnit getRoomUnit(String roomid, LoginUser loginUser);

	/**
	 * 房间是否在答题时间
	 * 
	 * @param room
	 *            房间
	 * @return
	 */
	public boolean isLiveTimeRoom(Room room);

	/**
	 * 房间是否对该用户开放（占用资源）
	 * 
	 * @param roomid
	 *            房间id
	 * @param currentUser
	 *            当前用户
	 * @return
	 */
	public boolean isUserAbleRoom(String roomid, LoginUser currentUser);

	/**
	 * 获得考试用户
	 * 
	 * @param roomid
	 * @return USERID,USERNAME,ORGID,ORGNAME,ROOMID,ROOMNAME
	 */
	public List<Map<String, Object>> getRoomUsers(String roomid);

	/**
	 * 當前session中是否存在匿名用戶
	 * 
	 * @param session
	 * @return
	 */
	public boolean isHaveAnonymous(HttpSession session);

	/**
	 * 给当前session中初始化匿名用户
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	public LoginUser initAnonymous(HttpSession session, String userip);

	/**
	 * 获得session中的匿名用户
	 * 
	 * @param session
	 * @return
	 */
	public LoginUser getAnonymous(HttpSession session);

	/**
	 * 獲得房間的可用試卷(发布状态)
	 * 
	 * @param roomid
	 * @return
	 */
	public List<Paper> getLivePapers(String roomid);

	/**
	 * 检查考场是否有答题人
	 * 
	 * @param roomid
	 * @return
	 */
	public boolean isHaveEffectiveAnswer(String roomid);

	/**检查考场是否有阅卷人
	 * @param roomid
	 * @return
	 */
	public boolean isHaveAdjudger(String roomid);
}