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
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.AnonymousUser;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考试服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
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
	public Room editRoomEntity(Room entity, LoginUser user) {
		Room entity2 = roomDaoImpl.getEntity(entity.getId());
		String oldtext = entity2.getRoomnote();
		entity2.setCounttype(entity.getCounttype());
		entity2.setRoomnote(entity.getRoomnote());
		entity2.setTimelen(entity.getTimelen());
		entity2.setWritetype(entity.getWritetype());
		entity2.setStarttime(entity.getStarttime());
		entity2.setEndtime(entity.getEndtime());
		entity2.setTimetype(entity.getTimetype());
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
		// TODO 自动生成代码,修改后请去除本注释
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
				"a.SSORTTYPE as SSORTTYPE,a.UUID as UUID,a.OSORTTYPE as OSORTTYPE,a.PSHOWTYPE as PSHOWTYPE,a.ID as ID,a.NAME as NAME,a.COUNTTYPE as COUNTTYPE,a.ROOMNOTE as ROOMNOTE,a.TIMELEN as TIMELEN,a.WRITETYPE as WRITETYPETITLE,a.WRITETYPE as WRITETYPE,a.STARTTIME as STARTTIME,a.ENDTIME as ENDTIME,a.TIMETYPE as TIMETYPE,a.EXAMTYPEID as EXAMTYPEID,a.CUSER as CUSER,a.CUSERNAME as CUSERNAME,a.ETIME as ETIME,a.CTIME as CTIME,a.EUSERNAME as EUSERNAME,a.EUSER as EUSER,a.PSTATE as PSTATETITLE,a.PSTATE as PSTATE,a.DUSERNAME as DUSERNAME,a.PCONTENT as PCONTENT,a.DTIME as DTIME,a.DUSER as DUSER,b.name as TYPENAME,ROOMUSERNUM.NUM as USERNUM");
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
		List<PaperUnit> paperUnits = new ArrayList<>();

		// 如果是随机抽卷则该ID不为空
		String onlyPaperId = null;
		if (entity.getPshowtype().equals("2") && isShuffle) {
			// 随机抽卷
			// 获取用户在房间内做过的答卷id
			List<String> paperids = cardServiceImpl.getUserPaperidsByRoom(roomid, currentUser.getId());
			if (paperids == null || paperids.size() <= 0) {
				paperids = new ArrayList<>();
				for (RoomPaper paper : papers) {
					paperids.add(paper.getPaperid());
				}
			}
			{
				// 用用户id获得一个数字
				Integer orderId = currentUser.getId().hashCode();
				orderId = orderId < 0 ? -orderId : orderId;
				orderId = orderId == 0 ? 1 : orderId;
				// 用卷数量取余
				int index = orderId % paperids.size();
				if (paperids.size() > 0) {
					onlyPaperId = paperids.get(index);
				}
			}
		}
		for (RoomPaper paper : papers) {
			PaperUnit paperUnit = paperServiceImpl.getPaperUnit(paper.getPaperid());
			paperUnit.setRoomPaper(paper);
			if (currentUser != null) {
				Card card = cardServiceImpl.loadCard(paper.getPaperid(), roomid, currentUser.getId());
				if (card != null) {
					paperUnit.setCard(card);
				}
			}
			paperUnit.setRoom(entity);
			if (entity.getPshowtype().equals("2") && isShuffle) {
				// 随机抽题，只取一套卷
				if (onlyPaperId != null && onlyPaperId.equals(paper.getPaperid())) {
					paperUnits.add(paperUnit);
				}
			} else {
				// 普通考场，展示全部卷
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
			// 匿名考試，需要把匿名用戶存入session中
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
		// 考场类型，如果时匿名和任何人就是有答题人1指定人0任何人2.匿名答题
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
			// 打乱题得顺序
			disorganizeSubjects(paper.getChapters());
		}
		if (room.getOsorttype().equals("2")) {
			// 打乱选项顺序
			disorganizeOptions(paper.getChapters());
		}
	}

	/**
	 * 打乱题顺序
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
	 * 打乱选项顺序
	 * 
	 * @param chapters
	 */
	private void disorganizeOptions(List<ChapterUnit> chapters) {
		for (ChapterUnit chapter : chapters) {
			for (SubjectUnit subject : chapter.getSubjects()) {
				if (subject.getAnswers() != null && subject.getAnswers().size() > 0) {
					// 1.填空，2.单选，3.多选，4判断，5问答,6附件
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
		// 1新建，0停用，2发布，3结束，4归档
		if (room.getPstate().equals("1")|| room.getPstate().equals("3") || room.getPstate().equals("4")) {
			// 删除参考人员和考场试卷
			roompaperDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "=")).toList());
			roomuserDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "=")).toList());
			cardServiceImpl.deleteCardsByRoom(id, user);
			paperuserownDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", id, "=")).toList());
			roomDaoImpl.deleteEntity(roomDaoImpl.getEntity(id));
			farmFileManagerImpl.cancelFilesByApp(id);
		} else {
			throw new RuntimeException("只有新建、结束、归档状态的答题室可删除!");
		}
	}

	@Override
	@Transactional
	public void editState(String id, String state, LoginUser currentUser) {
		Room entity2 = roomDaoImpl.getEntity(id);
		// 1新建，0停用，2发布，3结束，4归档
		if (state.equals("1")) {
			// 不存在修改为新建的情况
		}
		if (state.equals("0")) {
			// 0停用（发布和结束可停用）
			if (!entity2.getPstate().equals("2") && !entity2.getPstate().equals("3")) {
				throw new RuntimeException("只有发布和结束状态可停用!");
			}
		}
		if (state.equals("2")) {
			// 2发布(新建，停用,歸檔可发布)
			if (!entity2.getPstate().equals("1") && !entity2.getPstate().equals("4")
					&& !entity2.getPstate().equals("0")) {
				throw new RuntimeException("只有新建和停用状态可发布!");
			}
		}
		if (state.equals("3")) {
			// 3结束(发布可结束)
			if (!entity2.getPstate().equals("2")&&!entity2.getPstate().equals("0")) {
				throw new RuntimeException("只有发布和停用状态可结束!");
			}
		}
		if (state.equals("4")) {
			// 4归档(结束和停用可归档)
			if (!entity2.getPstate().equals("3")) {
				throw new RuntimeException("只有结束状态可归档!");
			}
		}
		entity2.setPstate(state);
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setEuser(currentUser.getId());
		entity2.setEusername(currentUser.getName());
		roomDaoImpl.editEntity(entity2);
	}

	// 结束答题
	@Override
	@Transactional
	public void finishRoom(String roomid, LoginUser currentUser) {
		editState(roomid, "3", currentUser);
		for (Card card : cardServiceImpl.getRoomCards(roomid)) {
			// 1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩
			if (card.getPstate().equals("1") || card.getPstate().equals("3")) {
				// 自动提交试卷
				cardServiceImpl.finishExamNoPop(card.getId(), currentUser);
			}
		}
	}

	// 归档数据（）
	@Override
	@Transactional
	public void backupRoom(String roomid, LoginUser currentUser) throws Exception {
		// 将用户答题信息进行统计汇总 POINT,MPOINT,SUBJECTID,USERID
		List<Map<String, Object>> cardPoints = cardPointDaoImpl.getAllRoomCardPoints(roomid);
		for (Map<String, Object> node : cardPoints) {
			try {// 统计答题记录，题记录和用户记录
				int point = (int) node.get("POINT");
				int mpoint = (int) node.get("MPOINT");
				subjectUserOwnServiceImpl.addFinishStandardSubject((String) node.get("SUBJECTID"),
						(point == mpoint && mpoint > 0),
						FarmAuthorityService.getInstance().getUserById((String) node.get("USERID")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 归档状态
		editState(roomid, "4", currentUser);
		// 归档用户成绩
		cardHisServiceImpl.backup(roomid, currentUser);
		// 清理用户答题卡
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
		// 房间id和答卷id是否为空
		if (StringUtils.isBlank(roomid) || StringUtils.isBlank(paperid)) {
			return false;
		}
		// 房间是否练习间
		Room entity = roomDaoImpl.getEntity(roomid);
		if (!entity.getPshowtype().equals("3")) {
			return false;
		}
		// 答卷是否在房间中
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
		// 房间id和答卷id是否为空
		if (StringUtils.isBlank(roomid) || StringUtils.isBlank(paperid)) {
			return false;
		}
		// 房间是否练习间
		Room entity = roomDaoImpl.getEntity(roomid);
		if (!entity.getPshowtype().equals("4")) {
			return false;
		}
		// 答卷是否在房间中
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
}
