package com.wts.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import com.wts.exam.dao.ExamPopDaoInter;
import com.wts.exam.dao.RoomDaoInter;
import com.wts.exam.domain.ExamPop;
import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.Room;
import com.wts.exam.service.ExamPopsServiceInter;
import com.wts.exam.service.ExamTypeServiceInter;

@Service
public class ExamPopsServiceImpl implements ExamPopsServiceInter {
	@Resource
	private ExamPopDaoInter exampopDaoImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private RoomDaoInter roomDaoImpl;

	@Override
	@Transactional
	public boolean isNotJudger(String roomId, LoginUser currentUser) {
		return !isJudger(roomId, currentUser);
	}

	@Override
	@Transactional
	public boolean isJudger(String roomId, LoginUser currentUser) {
		Set<String> typeids = examTypeServiceImpl.getUserPopTypeids(currentUser.getId(), "2");
		Room room = roomDaoImpl.getEntity(roomId);
		if (room == null) {
			throw new RuntimeException("the room[" + roomId + "] is not exist!");
		}
		String typeid = room.getExamtypeid();
		return typeids.contains(typeid);
	}

	@Override
	@Transactional
	public boolean isManager(String roomId, LoginUser currentUser) {
		Set<String> typeids = examTypeServiceImpl.getUserPopTypeids(currentUser.getId(), "1");
		String typeid = roomDaoImpl.getEntity(roomId).getExamtypeid();
		return typeids.contains(typeid);
	}

	@Override
	@Transactional
	public ExamPop insertExampopEntity(ExamPop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return exampopDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public ExamPop editExampopEntity(ExamPop entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		ExamPop entity2 = exampopDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setTypeid(entity.getTypeid());
		entity2.setUsername(entity.getUsername());
		entity2.setUserid(entity.getUserid());
		entity2.setFuntype(entity.getFuntype());
		entity2.setId(entity.getId());
		exampopDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteExampopEntity(String id, LoginUser user) {
		ExamPop pop = exampopDaoImpl.getEntity(id);
		{
			List<String> ids = new ArrayList<>();
			ids.add(pop.getTypeid());
			List<String> typeIds = examTypeServiceImpl.getAllSubType(ids);
			for (String typeid : typeIds) {
				exampopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", typeid, "="))
						.add(new DBRule("USERID", pop.getUserid(), "="))
						.add(new DBRule("FUNTYPE", pop.getFuntype(), "=")).toList());

				List<ExamPop> pops = exampopDaoImpl
						.selectEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", typeid, "="))
								.add(new DBRule("FUNTYPE", pop.getFuntype(), "=")).toList());
				if (pops.size() == 0) {
					ExamType type = examTypeServiceImpl.getExamtypeEntity(typeid);
					// 1:管理权限.2:判卷权限.3:查询权限.4:超级权限
					if (pop.getFuntype().equals("1")) {
						type.setMngpop("1");
					}
					if (pop.getFuntype().equals("2")) {
						type.setAdjudgepop("3");
					}
					if (pop.getFuntype().equals("3")) {
						type.setQuerypop("3");
					}
					if (pop.getFuntype().equals("4")) {
						type.setSuperpop("3");
					}
					examTypeServiceImpl.editExamtypeEntity(type, user);
				}
			}
		}

	}

	@Override
	@Transactional
	public ExamPop getExampopEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return exampopDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createExampopSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_EXAM_POP a left join WTS_EXAM_TYPE b on a.TYPEID=b.ID",
				"a.FUNTYPE as FUNTYPE,a.USERNAME as USERNAME,b.NAME as TYPENAME ,a.ID as ID");
		return dbQuery;
	}

	@Override
	@Transactional
	public void insertPop(List<String> userIds, List<String> typeIds, String functype, LoginUser currentUser) {
		typeIds = examTypeServiceImpl.getAllSubType(typeIds);
		for (String typeid : typeIds) {
			for (String userid : userIds) {
				ExamPop pop = new ExamPop();
				pop.setFuntype(functype);
				pop.setTypeid(typeid);
				pop.setUserid(userid);
				LoginUser user = FarmAuthorityService.getInstance().getUserById(userid);
				if (user != null) {
					pop.setUsername(user.getName());
					exampopDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("TYPEID", typeid, "="))
							.add(new DBRule("USERID", userid, "=")).add(new DBRule("FUNTYPE", functype, "=")).toList());
					exampopDaoImpl.insertEntity(pop);
					ExamType type = examTypeServiceImpl.getExamtypeEntity(typeid);
					// 1:管理权限.2:判卷权限.3:查询权限.4:超级权限
					if (functype.equals("1")) {
						type.setMngpop("2");
					}
					if (functype.equals("2")) {
						type.setAdjudgepop("2");
					}
					if (functype.equals("3")) {
						type.setQuerypop("2");
					}
					if (functype.equals("4")) {
						type.setSuperpop("2");
					}
					examTypeServiceImpl.editExamtypeEntity(type, currentUser);
				}
			}
		}
	}

}
