package com.wts.exam.service.impl;

import com.wts.exam.domain.RoomUser;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.RoomUserDaoInter;
import com.wts.exam.service.RoomuUserServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：参考人服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class RoomUserServiceImpl implements RoomuUserServiceInter {
	@Resource
	private RoomUserDaoInter roomuserDaoImpl;

	private static final Logger log = Logger.getLogger(RoomUserServiceImpl.class);

	@Override
	@Transactional
	public RoomUser insertRoomuserEntity(RoomUser entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return roomuserDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public RoomUser editRoomuserEntity(RoomUser entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		RoomUser entity2 = roomuserDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setId(entity.getId());
		entity2.setUserid(entity.getUserid());
		entity2.setRoomid(entity.getRoomid());
		roomuserDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteRoomuserEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		roomuserDaoImpl.deleteEntity(roomuserDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public RoomUser getRoomuserEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return roomuserDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createRoomuserSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WTS_ROOM_USER A LEFT JOIN ALONE_AUTH_USER B ON A.USERID=B.ID LEFT JOIN WTS_ROOM C ON A.ROOMID=C.ID LEFT JOIN ALONE_AUTH_USERORG d on d.USERID=a.USERID LEFT JOIN ALONE_AUTH_ORGANIZATION e on e.ID=d.ORGANIZATIONID",
				"C.NAME AS ROOMNAME,B.NAME AS USERNAME,A.ID as ID,E.NAME as ORGNAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public void insertRoomUser(String id, String userid, LoginUser currentUser) {
		// 先删除
		roomuserDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "="))
				.add(new DBRule("USERID", userid, "=")).toList());
		// 在添加
		RoomUser entity = new RoomUser();
		entity.setUserid(userid);
		entity.setRoomid(id);
		roomuserDaoImpl.insertEntity(entity);
	}

}
