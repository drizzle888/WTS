package com.wts.exam.service.impl;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.Card;
import com.wts.exam.domain.Room;
import com.wts.exam.domain.RoomPaper;
import com.wts.exam.domain.RoomUser;
import com.wts.exam.domain.ex.ChapterUnit;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.farm.core.time.TimeTool;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_APPLICATION_TYPE;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wts.exam.dao.CardPointDaoInter;
import com.wts.exam.dao.ExamTypeDaoInter;
import com.wts.exam.dao.PaperDaoInter;
import com.wts.exam.dao.PaperUserOwnDaoInter;
import com.wts.exam.dao.RoomDaoInter;
import com.wts.exam.dao.RoomPaperDaoInter;
import com.wts.exam.dao.RoomUserDaoInter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.CardHisServiceInter;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.ExamPopsServiceInter;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.RoomServiceInter;
import com.wts.exam.service.SubjectUserOwnServiceInter;
import com.wts.exam.utils.CheatUtils;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.User;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.AnonymousUser;
import com.farm.core.auth.domain.LoginUser;

/* *
 *?????????????????????????????????
 *?????????
 *
 *?????????v0.1
 *?????????FarmCode????????????
 *?????????20150707114057
 *?????????
 */
@Service
public class RoomServiceImpl implements RoomServiceInter {
	@Resource
	private RoomDaoInter roomDaoImpl;
	@Resource
	private ExamPopsServiceInter examPopsServiceImpl;
	@Resource
	private RoomPaperDaoInter roompaperDaoImpl;
	@Resource
	private RoomUserDaoInter roomuserDaoImpl;
	@Resource
	private ExamTypeDaoInter examtypeDaoImpl;
	@Resource
	private PaperServiceInter paperServiceImpl;
	@Resource
	private PaperDaoInter paperDaoImpl;
	@Resource
	private CardServiceInter cardServiceImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private CardHisServiceInter cardHisServiceImpl;
	@Resource
	private PaperUserOwnDaoInter paperuserownDaoImpl;
	@Resource
	private SubjectUserOwnServiceInter subjectUserOwnServiceImpl;
	@Resource
	private CardPointDaoInter cardPointDaoImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	private static final Logger log = Logger.getLogger(RoomServiceImpl.class);

	@Override
	@Transactional
	public Room insertRoomEntity(Room entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		if (StringUtils.isBlank(entity.getExamtypeid())) {
			entity.setExamtypeid(null);
		}
		if (StringUtils.isBlank(entity.getPstate())) {
			entity.setPstate("1");
		}
		entity = roomDaoImpl.insertEntity(entity);
		// --------------------------------------------------
		farmFileManagerImpl.submitFileByAppHtml(entity.getRoomnote(), entity.getId(), FILE_APPLICATION_TYPE.ROOMNOTE);
		farmFileManagerImpl.submitFile(entity.getImgid(), FILE_APPLICATION_TYPE.ROOMNOTE.getValue(), entity.getId());
		return entity;
	}

	@Override
	@Transactional
	public void editDoTimes(String roomid, String timetype, String starttime, String endtime) {
		Room entity2 = roomDaoImpl.getEntity(roomid);
		if (timetype.equals("1")) {
			entity2.setStarttime(null);
			entity2.setEndtime(null);
		} else {
			entity2.setStarttime(starttime);
			entity2.setEndtime(endtime);
		}

		entity2.setTimetype(timetype);
		roomDaoImpl.editEntity(entity2);
	}

	@Override
	@Transactional
	public Room editRoomEntity(Room entity, LoginUser user) {
		Room entity2 = roomDaoImpl.getEntity(entity.getId());
		String oldtext = entity2.getRoomnote();
		entity2.setCounttype(entity.getCounttype());
		entity2.setRoomnote(entity.getRoomnote());
		entity2.setTimelen(entity.getTimelen());
		entity2.setWritetype(entity.getWritetype());
		{
			// ???????????????????????????
			entity2.setTimetype(entity.getTimetype());
			if (entity2.getTimetype().equals("1")) {
				entity2.setStarttime(null);
				entity2.setEndtime(null);
			} else {
				entity2.setStarttime(entity.getStarttime());
				entity2.setEndtime(entity.getEndtime());
			}
		}
		entity2.setName(entity.getName());
		entity2.setExamtypeid(entity.getExamtypeid());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setEusername(user.getName());
		entity2.setEuser(user.getId());
		if (StringUtils.isNotBlank(entity.getPstate())) {
			entity2.setPstate(entity.getPstate());
		}
		entity2.setPshowtype(entity.getPshowtype());
		entity2.setSsorttype(entity.getSsorttype());
		entity2.setOsorttype(entity.getOsorttype());
		entity2.setDusername(entity.getDusername());
		entity2.setImgid(entity.getImgid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setDtime(entity.getDtime());
		entity2.setRestarttype(entity.getRestarttype());
		entity2.setDuser(entity.getDuser());
		entity2.setId(entity.getId());
		if (StringUtils.isBlank(entity2.getExamtypeid())) {
			entity2.setExamtypeid(null);
		}
		roomDaoImpl.editEntity(entity2);
		farmFileManagerImpl.updateFileByAppHtml(oldtext, entity.getRoomnote(), entity2.getId(),
				FILE_APPLICATION_TYPE.ROOMNOTE);
		farmFileManagerImpl.submitFile(entity2.getImgid(), FILE_APPLICATION_TYPE.ROOMIMG.getValue(), entity2.getId());
		return entity2;
	}

	@Override
	@Transactional
	public Room getRoomEntity(String id) {
		// TODO ??????????????????,???????????????????????????
		if (id == null) {
			return null;
		}
		return roomDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createRoomSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WTS_ROOM a left join WTS_EXAM_TYPE b on a.EXAMTYPEID=b.id left join (select COUNT(*) NUM,ROOMID from WTS_ROOM_USER GROUP BY ROOMID )  ROOMUSERNUM on ROOMUSERNUM.ROOMID=a.ID",
				"a.SSORTTYPE as SSORTTYPE,a.RESTARTTYPE as RESTARTTYPE,a.UUID as UUID,a.OSORTTYPE as OSORTTYPE,a.PSHOWTYPE as PSHOWTYPE,a.ID as ID,a.NAME as NAME,a.COUNTTYPE as COUNTTYPE,a.ROOMNOTE as ROOMNOTE,a.TIMELEN as TIMELEN,a.WRITETYPE as WRITETYPETITLE,a.WRITETYPE as WRITETYPE,a.STARTTIME as STARTTIME,a.ENDTIME as ENDTIME,a.TIMETYPE as TIMETYPE,a.EXAMTYPEID as EXAMTYPEID,a.CUSER as CUSER,a.CUSERNAME as CUSERNAME,a.ETIME as ETIME,a.CTIME as CTIME,a.EUSERNAME as EUSERNAME,a.EUSER as EUSER,a.PSTATE as PSTATETITLE,a.PSTATE as PSTATE,a.DUSERNAME as DUSERNAME,a.PCONTENT as PCONTENT,a.DTIME as DTIME,a.DUSER as DUSER,b.name as TYPENAME,ROOMUSERNUM.NUM as USERNUM");
		return dbQuery;
	}

	@Override
	@Transactional
	public void examTypeSetting(String roomid, String examtypeId, LoginUser currentUser) {
		if (StringUtils.isNotBlank(examtypeId)) {
			Room entity2 = roomDaoImpl.getEntity(roomid);
			entity2.setExamtypeid(examtypeId);
			entity2.setEtime(TimeTool.getTimeDate14());
			entity2.setEuser(currentUser.getId());
			entity2.setEusername(currentUser.getName());
			roomDaoImpl.editEntity(entity2);
		}
	}

	@Override
	@Transactional
	public RoomUnit getRoomUnit(String roomid, LoginUser currentUser, boolean isShuffle) {
		Room entity = roomDaoImpl.getEntity(roomid);
		RoomUnit roomunit = new RoomUnit();
		roomunit.setRoom(entity);
		ExamType examtype = examtypeDaoImpl.getEntity(entity.getExamtypeid());
		roomunit.setType(examtype);
		List<RoomPaper> papers = roompaperDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", roomid, "=")).toList());
		// ?????????????????????id??????
		Set<String> allPaperSet = new HashSet<>();
		for (RoomPaper rpaper : papers) {
			allPaperSet.add(rpaper.getPaperid());
		}
		List<PaperUnit> paperUnits = new ArrayList<>();
		// ???????????????????????????ID?????????
		String onlyPaperId = null;
		if (entity.getPshowtype().equals("2") && isShuffle) {
			// ????????????
			// ???????????????????????????????????????id
			List<String> paperids = cardServiceImpl.getUserPaperidsByRoom(roomid, currentUser.getId());
			if (paperids == null || paperids.size() <= 0) {
				// ????????????????????????????????????
				paperids = new ArrayList<>();
				for (RoomPaper paper : papers) {
					paperids.add(paper.getPaperid());
				}
				{// ??????????????????
					// ?????????id??????????????????
					User user = userServiceImpl.getUserEntity(currentUser.getId());
					int orderId = 100;
					if (user == null) {
						// ?????????????????????????????????????????????
						orderId = (int) (Math.random() * (100 - 1) + 1);
					} else {
						// ???????????????????????????????????????????????????
						try {
							orderId = Integer.valueOf(user.getLogintime().hashCode());
						} catch (Exception e) {
							e.printStackTrace();
							orderId = (int) (Math.random() * (100 - 1) + 1);
						}
					}
					orderId = orderId < 0 ? -orderId : orderId;
					orderId = orderId == 0 ? 1 : orderId;
					// ??????????????????
					int index = orderId % paperids.size();
					if (paperids.size() > 0) {
						onlyPaperId = paperids.get(index);
					}
				}
			} else {
				onlyPaperId = paperids.get(0);
			}
			{// ???????????????????????????????????????????????????????????????
				String cheatPaperId = CheatUtils.getOnlyCheatPaper(roomid, allPaperSet);
				if (cheatPaperId != null) {
					onlyPaperId = cheatPaperId;
				}
			}
		}
		for (RoomPaper paper : papers) {
			PaperUnit paperUnit = paperServiceImpl.getPaperUnit(paper.getPaperid());
			paperUnit.setRoomPaper(paper);
			if (currentUser != null && (entity.getPshowtype().equals("2") || entity.getPshowtype().equals("1"))) {
				Card card = cardServiceImpl.loadCard(paper.getPaperid(), roomid, currentUser.getId());
				if (card != null) {
					paperUnit.setCard(card);
				}
			}
			paperUnit.setRoom(entity);
			if (entity.getPshowtype().equals("2") && isShuffle) {
				// ??????????????????????????????
				if (onlyPaperId != null && onlyPaperId.equals(paper.getPaperid())) {
					paperUnits.add(paperUnit);
				}
			} else {
				// ??????????????????????????????
				paperUnits.add(paperUnit);
			}
		}
		roomunit.setPapers(paperUnits);
		return roomunit;
	}

	@Override
	@Transactional
	public boolean isLiveTimeRoom(Room room) {
		if (room.getTimetype().equals("1")) {
			return true;
		}
		// 2018-11-27 10:40
		String startTime = room.getStarttime().replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
		String endTime = room.getEndtime().replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
		String ctime = TimeTool.getTimeDate14().substring(0, endTime.length());
		return ctime.compareTo(endTime) <= 0 && ctime.compareTo(startTime) >= 0;
	}

	@Override
	@Transactional
	public boolean isUserAbleRoom(String roomid, LoginUser currentUser) {
		Room room = roomDaoImpl.getEntity(roomid);
		if (room.getWritetype().equals("0") || room.getWritetype().equals("2")) {
			return true;
		}
		if (currentUser == null) {
			return false;
		}
		List<RoomUser> users = roomuserDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("ROOMID", roomid, "=")).add(new DBRule("USERID", currentUser.getId(), "=")).toList());
		return users.size() > 0;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getRoomUsers(String roomid) {
		return roomuserDaoImpl.getRoomUsers(roomid);
	}

	@Override
	public boolean isHaveAnonymous(HttpSession session) {
		return session.getAttribute("anonymous") != null;
	}

	@Override
	public LoginUser initAnonymous(HttpSession session, String userip) {
		LoginUser user = null;
		if (session.getAttribute("anonymous") == null) {
			// ??????????????????????????????????????????session???
			AnonymousUser anonymous = new AnonymousUser();
			anonymous.setIp(userip);
			session.setAttribute("anonymous", anonymous);
			user = anonymous;
		} else {
			user = getAnonymous(session);
		}
		return user;
	}

	@Override
	public LoginUser getAnonymous(HttpSession session) {
		return (LoginUser) session.getAttribute("anonymous");
	}

	@Override
	@Transactional
	public List<Paper> getLivePapers(String roomid) {
		DataQuery dbQuery = DataQuery.getInstance(1,
				"paper.ID AS ID, paper.EXAMTYPEID AS EXAMTYPEID, paper.CTIME AS CTIME, paper.ETIME AS ETIME, paper.CUSERNAME AS CUSERNAME, paper.CUSER AS CUSER, paper.EUSERNAME AS EUSERNAME, paper.EUSER AS EUSER, paper.PSTATE AS PSTATE, paper.PCONTENT AS PCONTENT, paper. NAME AS NAME, paper.SUBJECTNUM AS SUBJECTNUM, paper.POINTNUM AS POINTNUM, paper.COMPLETETNUM AS COMPLETETNUM, paper.AVGPOINT AS AVGPOINT, paper.TOPPOINT AS TOPPOINT, paper.LOWPOINT AS LOWPOINT, paper.ADVICETIME AS ADVICETIME, paper.PAPERNOTE AS PAPERNOTE",
				"WTS_PAPER paper left join WTS_ROOM_PAPER REROOM on REROOM.PAPERID=paper.ID");
		dbQuery.setPagesize(100);
		dbQuery.setNoCount();
		dbQuery.addRule(new DBRule("REROOM.ROOMID", roomid, "="));
		dbQuery.addRule(new DBRule("PAPER.PSTATE", "2", "="));
		try {
			List<Paper> list = dbQuery.search().getObjectList(Paper.class);
			return list;
		} catch (SQLException e) {
			log.error(e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public boolean isHaveEffectiveAnswer(String roomid) {
		// ????????????????????????????????????????????????????????????1?????????0?????????2.????????????
		Room room = getRoomEntity(roomid);
		if (room.getWritetype().equals("0") || room.getWritetype().equals("2")) {
			return true;
		}
		if (getRoomUsers(roomid).size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean isHaveAdjudger(String roomid) {
		Room room = getRoomEntity(roomid);
		if (examTypeServiceImpl.getTypePopUsers(room.getExamtypeid(), "2").size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void disorganizePaper(PaperUnit paper, String roomId) {
		Room room = getRoomEntity(roomId);
		if (room.getSsorttype().equals("2")) {
			// ??????????????????
			disorganizeSubjects(paper.getChapters());
		}
		if (room.getOsorttype().equals("2")) {
			// ??????????????????
			disorganizeOptions(paper.getChapters());
		}
	}

	/**
	 * ???????????????
	 * 
	 * @param chapters
	 */
	private void disorganizeSubjects(List<ChapterUnit> chapters) {
		for (ChapterUnit chapter : chapters) {
			if (chapter.getSubjects() != null && chapter.getSubjects().size() > 0) {
				Collections.shuffle(chapter.getSubjects());
			}
			if (chapter.getChapters() != null && chapter.getChapters().size() > 0) {
				disorganizeSubjects(chapter.getChapters());
			}
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param chapters
	 */
	private void disorganizeOptions(List<ChapterUnit> chapters) {
		for (ChapterUnit chapter : chapters) {
			for (SubjectUnit subject : chapter.getSubjects()) {
				if (subject.getAnswers() != null && subject.getAnswers().size() > 0) {
					// 1.?????????2.?????????3.?????????4?????????5??????,6??????
					if (subject.getVersion().getTiptype().equals("2")
							|| subject.getVersion().getTiptype().equals("3")) {
						Collections.shuffle(subject.getAnswers());
					}
				}
			}
			if (chapter.getChapters() != null && chapter.getChapters().size() > 0) {
				disorganizeSubjects(chapter.getChapters());
			}
		}
	}

	@Override
	@Transactional
	public void deleteRoomEntity(String id, LoginUser user) {
		Room room = roomDaoImpl.getEntity(id);
		// 1?????????0?????????2?????????3?????????4??????
		if (room.getPstate().equals("1") || room.getPstate().equals("3") || room.getPstate().equals("4")) {
			// ?????????????????????????????????
			roompaperDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "=")).toList());
			roomuserDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "=")).toList());
			cardServiceImpl.deleteCardsByRoom(id, user);
			paperuserownDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "=")).toList());
			roomDaoImpl.deleteEntity(roomDaoImpl.getEntity(id));
			farmFileManagerImpl.cancelFilesByApp(id);
		} else {
			throw new RuntimeException("?????????????????????????????????????????????????????????!");
		}
	}

	@Override
	@Transactional
	public void editState(String id, String state, LoginUser currentUser) {
		Room entity2 = roomDaoImpl.getEntity(id);
		// 1?????????0?????????2?????????3?????????4??????
		if (state.equals("1")) {
			// ?????????????????????????????????
		}
		if (state.equals("0")) {
			// 0????????????????????????????????????
			if (!entity2.getPstate().equals("2") && !entity2.getPstate().equals("3")) {
				throw new RuntimeException("????????????????????????????????????!");
			}
		}
		if (state.equals("2")) {
			// 2??????(???????????????,???????????????)
			if (!entity2.getPstate().equals("1") && !entity2.getPstate().equals("4")
					&& !entity2.getPstate().equals("0")) {
				throw new RuntimeException("????????????????????????????????????!");
			}
		}
		if (state.equals("3")) {
			// 3??????(???????????????)
			if (!entity2.getPstate().equals("2") && !entity2.getPstate().equals("0")) {
				throw new RuntimeException("????????????????????????????????????!");
			}
		}
		if (state.equals("4")) {
			// 4??????(????????????????????????)
			if (!entity2.getPstate().equals("3")) {
				throw new RuntimeException("???????????????????????????!");
			}
		}
		entity2.setPstate(state);
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setEuser(currentUser.getId());
		entity2.setEusername(currentUser.getName());
		roomDaoImpl.editEntity(entity2);
	}

	// ????????????
	@Override
	@Transactional
	public void finishRoom(String roomid, LoginUser currentUser) {
		editState(roomid, "3", currentUser);
		for (Card card : cardServiceImpl.getRoomCards(roomid)) {
			// 1:????????????,2:????????????,3:???????????????,4:??????????????????,5:???????????????,6:???????????????,7:????????????
			if (card.getPstate().equals("1") || card.getPstate().equals("3")) {
				// ??????????????????
				cardServiceImpl.finishExamNoPop(card.getId(), currentUser);
			}
		}
	}

	// ??????????????????
	@Override
	@Transactional
	public void backupRoom(String roomid, LoginUser currentUser) throws Exception {
		// ????????????
		editState(roomid, "4", currentUser);
		// ??????????????????????????????????????? POINT,MPOINT,SUBJECTID,USERID
		{
			List<Map<String, Object>> cardPoints = cardPointDaoImpl.getAllRoomCardPoints(roomid);
			for (Map<String, Object> node : cardPoints) {
				try {// ?????????????????????????????????????????????
					int point = (int) node.get("POINT");
					int mpoint = (int) node.get("MPOINT");
					String subjectid = (String) node.get("SUBJECTID");
					if (subjectid != null) {
						subjectUserOwnServiceImpl.addFinishStandardSubject(subjectid, (point == mpoint && mpoint > 0),
								FarmAuthorityService.getInstance().getUserById((String) node.get("USERID")));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// ??????????????????
		cardHisServiceImpl.backup(roomid, currentUser);
		// ?????????????????????
		cardServiceImpl.clearRoomCard(roomid, currentUser);
	}

	@Override
	@Transactional
	public List<String> getPapers(String roomid) {
		List<String> ids = new ArrayList<>();
		List<RoomPaper> papers = roompaperDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", roomid, "=")).toList());
		for (RoomPaper paper : papers) {
			ids.add(paper.getPaperid());
		}
		return ids;
	}

	@Override
	@Transactional
	public boolean testAble(String roomid, String paperid) {
		// ??????id?????????id????????????
		if (StringUtils.isBlank(roomid) || StringUtils.isBlank(paperid)) {
			return false;
		}
		// ?????????????????????
		Room entity = roomDaoImpl.getEntity(roomid);
		if (!entity.getPshowtype().equals("3")) {
			return false;
		}
		// ????????????????????????
		List<Paper> papers = getLivePapers(roomid);
		for (Paper paper : papers) {
			if (paper.getId().equals(paperid.trim())) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean learnAble(String roomid, String paperid) {
		// ??????id?????????id????????????
		if (StringUtils.isBlank(roomid) || StringUtils.isBlank(paperid)) {
			return false;
		}
		// ?????????????????????
		Room entity = roomDaoImpl.getEntity(roomid);
		if (!entity.getPshowtype().equals("4")) {
			return false;
		}
		// ????????????????????????
		List<Paper> papers = getLivePapers(roomid);
		for (Paper paper : papers) {
			if (paper.getId().equals(paperid.trim())) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public RoomPaper getRoomPaper(String roomId, String paperid) {
		List<RoomPaper> roompapers = roompaperDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("PAPERID", paperid, "=")).add(new DBRule("ROOMID", roomId, "=")).toList());
		if (roompapers.size() > 0) {
			return roompapers.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void editRestartAble(String id, Boolean ableType) {
		Room entity2 = roomDaoImpl.getEntity(id);
		entity2.setRestarttype(ableType ? "2" : "1");
		roomDaoImpl.editEntity(entity2);
	}

	@Override
	@Transactional
	public int getRoomAnsersNum(String roomid) {
		return roomDaoImpl.getRoomAnsersNum(roomid);
	}

}
