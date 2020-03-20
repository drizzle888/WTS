package com.wts.exam.service.impl;

import com.wts.exam.domain.RoomPaper;
import org.apache.log4j.Logger;
import com.wts.exam.dao.RoomPaperDaoInter;
import com.wts.exam.service.RoomPaperServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考试试卷服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class RoomPaperServiceImpl implements RoomPaperServiceInter {
	@Resource
	private RoomPaperDaoInter roompaperDaoImpl;

	private static final Logger log = Logger.getLogger(RoomPaperServiceImpl.class);

	@Override
	@Transactional
	public RoomPaper insertRoompaperEntity(RoomPaper entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return roompaperDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public RoomPaper editRoompaperEntity(RoomPaper entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		RoomPaper entity2 = roompaperDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPaperid(entity.getPaperid());
		entity2.setRoomid(entity.getRoomid());
		entity2.setId(entity.getId());
		roompaperDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteRoompaperEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		roompaperDaoImpl.deleteEntity(roompaperDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public RoomPaper getRoompaperEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return roompaperDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createRoompaperSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_ROOM_PAPER A LEFT JOIN WTS_PAPER B ON A.PAPERID=B.ID",
				"A.ID AS ID,A.PAPERID AS PAPERID,A.NAME as NAME,A.ROOMID AS ROOMID,B.NAME AS PAPERNAME,B.PSTATE AS PAPERSTATE,B.CTIME as PAPERTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public void addRoomPaper(String roomid, String paperid, LoginUser currentUser) {
		// 先删除
		roompaperDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", roomid, "="))
				.add(new DBRule("PAPERID", paperid, "=")).toList());
		// 在添加
		RoomPaper entity = new RoomPaper();
		entity.setPaperid(paperid);
		entity.setRoomid(roomid);
		roompaperDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public void editOtherName(String roompaperid, String name, LoginUser currentUser) {
		RoomPaper entity = getRoompaperEntity(roompaperid);
		entity.setName(name);
		roompaperDaoImpl.editEntity(entity);
	}

	@Override
	@Transactional
	public void clearOtherName(String roompaperid, LoginUser currentUser) {
		RoomPaper entity = getRoompaperEntity(roompaperid);
		entity.setName(null);
		roompaperDaoImpl.editEntity(entity);
	}
}
