package com.wts.exam.service.impl;

import com.wts.exam.domain.Paper;
import com.wts.exam.domain.CardAnswer;
import com.wts.exam.domain.Card;
import com.wts.exam.domain.CardPoint;
import com.wts.exam.domain.PaperSubject;
import com.wts.exam.domain.Room;
import com.wts.exam.domain.SubjectAnswer;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.AnswerUnit;
import com.wts.exam.domain.ex.CardInfo;
import com.wts.exam.domain.ex.ChapterUnit;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wts.exam.dao.PaperDaoInter;
import com.wts.exam.dao.CardAnswerDaoInter;
import com.wts.exam.dao.CardDaoInter;
import com.wts.exam.dao.CardPointDaoInter;
import com.wts.exam.dao.PaperSubjectDaoInter;
import com.wts.exam.dao.RoomDaoInter;
import com.wts.exam.dao.RoomPaperDaoInter;
import com.wts.exam.dao.RoomUserDaoInter;
import com.wts.exam.dao.SubjectVersionDaoInter;
import com.wts.exam.service.ExamPopsServiceInter;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.RoomServiceInter;
import com.wts.exam.service.SubjectUserOwnServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：答题卡服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class CardServiceImpl implements CardServiceInter {
	@Resource
	private ExamPopsServiceInter examPopsServiceImpl;
	@Resource
	private CardAnswerDaoInter cardAnswerDaoImpl;
	@Resource
	private PaperSubjectDaoInter papersubjectDaoImpl;
	@Resource
	private CardDaoInter cardDaoImpl;
	@Resource
	private RoomServiceInter roomServiceImpl;
	@Resource
	private RoomDaoInter roomDaoImpl;
	@Resource
	private PaperDaoInter paperDaoImpl;
	@Resource
	private SubjectVersionDaoInter subjectversionDaoImpl;
	@Resource
	private RoomPaperDaoInter roompaperDaoImpl;
	@Resource
	private RoomUserDaoInter roomuserDaoImpl;
	@Resource
	private CardPointDaoInter cardPointDaoImpl;
	@Resource
	private SubjectUserOwnServiceInter subjectUserOwnServiceImpl;
	private static final Logger log = Logger.getLogger(CardServiceImpl.class);

	@Override
	@Transactional
	public Card insertCardEntity(Card entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return cardDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Card editCardEntity(Card entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		Card entity2 = cardDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPcontent(entity.getPcontent());
		entity2.setEndtime(entity.getEndtime());
		entity2.setPstate(entity.getPstate());
		entity2.setStarttime(entity.getStarttime());
		entity2.setAdjudgetime(entity.getAdjudgetime());
		entity2.setAdjudgeuser(entity.getAdjudgeuser());
		entity2.setPoint(entity.getPoint());
		entity2.setUserid(entity.getUserid());
		entity2.setRoomid(entity.getRoomid());
		entity2.setPaperid(entity.getPaperid());
		entity2.setId(entity.getId());
		cardDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteCardEntity(String id, LoginUser user) {
		List<DBRule> cardrules = DBRuleList.getInstance().add(new DBRule("CARDID", id, "=")).toList();
		// WTS_CARD_ANSWER
		cardAnswerDaoImpl.deleteEntitys(cardrules);
		// WTS_CARD_POINT
		cardPointDaoImpl.deleteEntitys(cardrules);
		cardDaoImpl.deleteEntity(cardDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Card getCardEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return cardDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createCardSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_CARD a left join alone_auth_user b on a.USERID=b.ID",
				"a.ID as ID,a.USERID as USERID,b.name as USERNAME,a.STARTTIME as STARTTIME,a.ENDTIME as ENDTIME,a.POINT as POINT,a.ADJUDGETIME as ADJUDGETIME,a.ADJUDGEUSERNAME as ADJUDGEUSERNAME,a.PSTATE as PSTATE");
		return dbQuery;
	}

	@Override
	@Transactional
	public Card creatOrGetCard(String paperid, String roomId, LoginUser currentUser) {
		// 房间是否超时，是否有答题权限
		Room room = roomServiceImpl.getRoomEntity(roomId);
		if (!roomServiceImpl.isLiveTimeRoom(room)) {
			throw new RuntimeException("该房间不可用，未到答题时间!");
		}
		if (currentUser == null) {
			throw new RuntimeException("请先登陆系统!");
		}
		if (!roomServiceImpl.isUserAbleRoom(room.getId(), currentUser)) {
			throw new RuntimeException("当前用户无进入权限!");
		}

		Paper paper = paperDaoImpl.getEntity(paperid);
		if (paper == null) {
			throw new RuntimeException("该试卷不存在!id:" + paperid);
		}
		List<Card> cards = cardDaoImpl.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "="))
				.add(new DBRule("USERID", currentUser.getId(), "=")).add(new DBRule("ROOMID", roomId, "=")).toList());
		// 查询答题卡
		if (cards.size() > 0) {
			Card card = cards.get(0);
			String endTime = card.getEndtime();
			if (TimeTool.getTimeDate14().compareTo(endTime) >= 0) {
				card.setPstate("3");
				cardDaoImpl.editEntity(card);
			}
			return card;
		} else {// 创建答题卡
			String startTime = null;
			String endTime = null;
			{
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				startTime = TimeTool.getTimeDate14();
				Date date;
				try {
					date = format.parse(startTime);
				} catch (ParseException e) {
					throw new RuntimeException("时间格式化错误！");
				}
				Date endtime = TimeTool.getTimeDate12ForMinute(room.getTimelen(), date);
				endTime = format.format(endtime);
			}
			{
				Card card = new Card();
				// card.setAdjudgetime(adjudgetime);
				// card.setAdjudgeuser(adjudgeuser);
				card.setStarttime(startTime);
				card.setEndtime(endTime);
				card.setPaperid(paperid);
				// card.setPcontent(pcontent);
				card.setPoint((float) 0);
				card.setPstate("1");
				card.setRoomid(roomId);
				card.setUserid(currentUser.getId());
				card = cardDaoImpl.insertEntity(card);
				return card;
			}
		}
	}

	@Override
	@Transactional
	public Card loadCard(String paperid, String roomid, String userid) {
		DBRuleList dbs = DBRuleList.getInstance();
		dbs.add(new DBRule("PAPERID", paperid, "="));
		dbs.add(new DBRule("USERID", userid, "="));
		dbs.add(new DBRule("ROOMID", roomid, "="));
		List<Card> cards = cardDaoImpl.selectEntitys(dbs.toList());
		if (cards.size() > 0) {
			Card card = cards.get(0);
			String endTime = card.getEndtime();
			if (TimeTool.getTimeDate14().compareTo(endTime) >= 0 && card.getPstate().equals("1")) {
				card.setPstate("3");
				cardDaoImpl.editEntity(card);
			}
			return cards.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public boolean saveCardVal(Card card, String versionid, String answerid, String value) {
		// 删除已有的答案
		if (card == null) {
			throw new RuntimeException("答题卡获取失败：答题卡");
		}
		cardAnswerDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("CARDID", card.getId(), "="))
				.add(new DBRule("VERSIONID", versionid, "=")).add(new DBRule("ANSWERID", answerid, "=")).toList());

		List<CardAnswer> answers = cardAnswerDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("CARDID", card.getId(), "=")).add(new DBRule("VERSIONID", versionid, "=")).toList());
		// 添加新的答案
		CardAnswer answer = new CardAnswer();
		answer.setAnswerid(answerid);
		answer.setCardid(card.getId());
		answer.setCtime(TimeTool.getTimeDate14());
		answer.setCuser(card.getUserid());
		answer.setPstate("1");
		answer.setValstr(value);
		answer.setVersionid(versionid);
		answer = cardAnswerDaoImpl.insertEntity(answer);
		answers.add(answer);
		// ---------------------------------------------
		SubjectVersion version = subjectversionDaoImpl.getEntity(versionid);
		TipType tiptype = TipType.getTipType(version.getTiptype());
		return tiptype.getHandle().isHaveAnswer(answers);
	}

	@Override
	@Transactional
	public void finishExam(String cardId, LoginUser currentUser) {
		Card card = cardDaoImpl.getEntity(cardId);
		{
			// 数据校验
			if (!card.getUserid().equals(currentUser.getId())
					&& examPopsServiceImpl.isNotJudger(card.getRoomid(), currentUser)) {
				throw new RuntimeException("答题卡用户非当前用户和判卷人!");
			}
			if (!card.getPstate().equals("1") && !card.getPstate().equals("3")) {
				// 非答题状态，不用交卷
				return;
			}
		}

		if (isAnswerAble(card)) {
			// 在答题时间内，手动交卷
			card.setPstate("2");
			card.setEndtime(TimeTool.getTimeDate14());
		} else {
			// 非答题时间内，自动强制交卷
			card.setPstate("4");
		}
		{// 计算得分(回填用戶完成題的數量)
			CardInfo info = autoCountCardPoint(cardId, currentUser);
			card.setCompletenum(info.getCompleteNum());
			card.setAllnum(info.getAllNum());
		}
		cardDaoImpl.editEntity(card);
	}

	@Override
	@Transactional
	public float getCardPointSum(Card card) {
		List<CardPoint> cardSubs = cardPointDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CARDID", card.getId(), "=")).toList());
		float point = 0;
		for (CardPoint cardSub : cardSubs) {
			point = point + (cardSub.getPoint() == null ? 0 : cardSub.getPoint());
		}
		return point;
	}

	@Override
	@Transactional
	public int countSubjectPoint(SubjectUnit subjectUnit) {
		int pointWeight = Math.abs(subjectUnit.getTipType().getHandle().runPointWeight(subjectUnit));
		if (pointWeight > 100) {
			pointWeight = 100;
		}
		return pointWeight;
	}

	@Override
	@Transactional
	public boolean runPointCount(List<SubjectUnit> userSubjects, Card card) {
		Map<String, Integer> points = new HashMap<>();
		boolean isAllComplete = true;
		{
			// 先获得试卷得分得配置
			List<PaperSubject> standPoints = papersubjectDaoImpl.selectEntitys(
					DBRuleList.getInstance().add(new DBRule("PAPERID", card.getPaperid(), "=")).toList());
			for (PaperSubject standPoint : standPoints) {
				points.put(standPoint.getVersionid(), standPoint.getPoint());
			}
		}
		for (SubjectUnit unit : userSubjects) {
			// 题的得分权重，可以得分得百分比（填空题和问答题都是返回百分比得）
			int pointWeight = countSubjectPoint(unit);
			if (card.getUserid() != null && unit != null && unit.getVersion() != null) {
				// 2.把错题加入错题集合// 3.用户答题历史存入，答题历史记录
				LoginUser user = FarmAuthorityService.getInstance().getUserById(card.getUserid());
				if (user != null) {
					subjectUserOwnServiceImpl.addFinishSubject(unit.getVersion().getSubjectid(), pointWeight == 100,
							user);
				}
			}
			Integer basePoint = points.get(unit.getVersion().getId());
			int point = (basePoint == null ? 0 : basePoint) * pointWeight / 100;
			List<CardPoint> cardPoints = cardPointDaoImpl.selectEntitys(
					DBRuleList.getInstance().add(new DBRule("CARDID", unit.getCardSubject().getCardid(), "="))
							.add(new DBRule("VERSIONID", unit.getVersion().getId(), "=")).toList());
			if (cardPoints.size() > 1) {
				throw new RuntimeException("用戶答題卡cardPoints中的，題數量" + cardPoints.size() + "错误:cardid("
						+ unit.getCardSubject().getCardid() + "),versionid(" + unit.getVersion().getId() + ")");
			}
			{
				CardPoint cardsub = null;
				if (cardPoints.size() == 0) {
					// 无就创建
					cardsub = new CardPoint();
					cardsub.setCardid(unit.getCardSubject().getCardid());
					cardsub.setVersionid(unit.getVersion().getId());
				} else {
					// 有就修改
					cardsub = cardPointDaoImpl.getEntity(cardPoints.get(0).getId());
				}
				if (unit.getTipType().equals(TipType.CheckBox) || unit.getTipType().equals(TipType.Select)
						|| unit.getTipType().equals(TipType.Judge)) {
					cardsub.setComplete("1");
				} else {
					cardsub.setComplete("0");
					isAllComplete = false;
				}
				cardsub.setPoint(point);
				if (cardPoints.size() == 0) {
					cardPointDaoImpl.insertEntity(cardsub);
				} else {
					cardPointDaoImpl.editEntity(cardsub);
				}
			}
		}
		return isAllComplete;
	}

	@Override
	@Transactional
	public List<SubjectUnit> loadUserSubjects(Card card) {
		List<SubjectVersion> versions = null;
		List<SubjectAnswer> sanswers = null;
		List<CardAnswer> panswers = null;
		// 查詢試題版本
		try {
			DataQuery query = DataQuery.getInstance(1,
					"SELECT C.ID AS ID, C.CTIME AS CTIME, C.CUSERNAME AS CUSERNAME, C.CUSER AS CUSER, C.PSTATE AS PSTATE, C.PCONTENT AS PCONTENT, C.TIPSTR AS TIPSTR, C.TIPNOTE AS TIPNOTE, C.TIPTYPE AS TIPTYPE, C.SUBJECTID AS SUBJECTID FROM WTS_CARD a LEFT JOIN WTS_PAPER_SUBJECT b ON a.PAPERID = b.PAPERID LEFT JOIN WTS_SUBJECT_VERSION c ON b.VERSIONID = c.ID WHERE a.ID = '"
							+ card.getId() + "'");
			query.setPagesize(1000);
			query.setDistinct(true);
			versions = query.search().getObjectList(SubjectVersion.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// 試題答案
		try {
			DataQuery query = DataQuery.getInstance(1,
					"SELECT b.ID AS ID, b.CTIME AS CTIME, b.CUSERNAME AS CUSERNAME, b.CUSER AS CUSER, b.PSTATE AS PSTATE, b.PCONTENT AS PCONTENT, b.VERSIONID AS VERSIONID, b.ANSWER AS ANSWER, b.ANSWERNOTE AS ANSWERNOTE, b.SORT AS SORT, b.RIGHTANSWER AS RIGHTANSWER, b.POINTWEIGHT AS POINTWEIGHT FROM WTS_PAPER_SUBJECT a LEFT JOIN WTS_SUBJECT_ANSWER b ON a.VERSIONID = b.VERSIONID WHERE a.PAPERID = '"
							+ card.getPaperid() + "' and b.id is not null");
			query.setPagesize(1000);
			sanswers = query.search().getObjectList(SubjectAnswer.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// 答題卡答案
		try {
			DataQuery query = DataQuery.getInstance(1,
					"select ID,CARDID,ANSWERID,VERSIONID,CUSER,VALSTR,CTIME,PCONTENT,PSTATE from WTS_CARD_ANSWER where CARDID='"
							+ card.getId() + "'");
			query.setPagesize(1000);
			panswers = query.search().getObjectList(CardAnswer.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// --------------------------------------------------
		List<SubjectUnit> subjects = new ArrayList<>();
		{
			// <题版本id，题对象>
			Map<String, SubjectUnit> subjectMap = new HashMap<>();
			for (SubjectVersion version : versions) {
				// 构造试题字典
				SubjectUnit unit = new SubjectUnit();
				unit.setVersion(version);
				unit.setTipType(TipType.getTipType(version.getTiptype()));
				unit.setAnswers(new ArrayList<AnswerUnit>());
				subjectMap.put(version.getId(), unit);
			}
			Map<String, List<CardAnswer>> answerValsMap = new HashMap<>();
			for (CardAnswer answer : panswers) {
				// 構造需填充參數需要用到的Map<String, List<CardAnswer>>
				List<CardAnswer> answers = answerValsMap.get(answer.getVersionid());
				if (answers == null) {
					answers = new ArrayList<>();
					answerValsMap.put(answer.getVersionid(), answers);
				}
				answers.add(answer);
			}
			for (SubjectAnswer answer : sanswers) {
				// 將題選項填充到題對象中
				SubjectUnit unit = subjectMap.get(answer.getVersionid());
				AnswerUnit answerUnit = new AnswerUnit();
				answerUnit.setAnswer(answer);
				if (unit == null) {
					System.out.println("this subject is null:" + answer.getVersionid());
				} else {
					unit.getAnswers().add(answerUnit);
				}
			}
			for (Entry<String, SubjectUnit> entry : subjectMap.entrySet()) {
				// 构造用户题和答案得封装
				SubjectUnit unit = entry.getValue();
				{
					CardPoint cardsub = new CardPoint();
					cardsub.setCardid(card.getId());
					cardsub.setComplete("0");
					cardsub.setPoint(0);
					cardsub.setVersionid(unit.getVersion().getId());
					unit.setCardSubject(cardsub);
				}
				List<CardAnswer> panswerList = answerValsMap.get(unit.getVersion().getId());
				if (unit == null || panswerList == null) {
					continue;
				}
				TipType.getTipType(unit.getVersion().getTiptype()).getHandle().loadVal(unit, panswerList);
				subjects.add(entry.getValue());
			}
		}
		return subjects;
	}

	@Override
	@Transactional
	public void loadCardPoint(PaperUnit paper, Card card) {
		// 取得所有答题卡的值
		List<CardPoint> answerPoints = cardPointDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CARDID", card.getId(), "=")).toList());
		if (answerPoints.size() <= 0) {
			return;
		}
		Map<String, CardPoint> pointMap = new HashMap<>();
		for (CardPoint paperSub : answerPoints) {
			pointMap.put(paperSub.getVersionid(), paperSub);
		}
		// 填充道试卷中
		for (ChapterUnit chapter1 : paper.getChapters()) {
			// 第一層章節
			List<SubjectUnit> subjects = chapter1.getSubjects();
			loadSubjectPoint(subjects, pointMap);
			for (ChapterUnit chapter2 : chapter1.getChapters()) {
				// 第二層章節
				List<SubjectUnit> subjects2 = chapter2.getSubjects();
				loadSubjectPoint(subjects2, pointMap);
				for (ChapterUnit chapter3 : chapter2.getChapters()) {
					// 第三層章節
					List<SubjectUnit> subjects3 = chapter3.getSubjects();
					loadSubjectPoint(subjects3, pointMap);
				}
			}
		}
	}

	/**
	 * 将考题值填充到题目中
	 * 
	 * @param subjects
	 *            试题序列
	 * @param answerVals
	 *            map<试题版本id,用户答案序列>
	 */
	private void loadSubjectPoint(List<SubjectUnit> subjects, Map<String, CardPoint> pointMap) {
		for (SubjectUnit nuit : subjects) {
			CardPoint point = pointMap.get(nuit.getVersion().getId());
			if (point == null) {
				point = new CardPoint();
			}
			nuit.setCardSubject(point);
		}
	}

	@Override
	@Transactional
	public void loadCardVal(PaperUnit paper, Card card) {
		// 取得所有答题卡的值
		List<CardAnswer> answerVals = cardAnswerDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CARDID", card.getId(), "=")).toList());
		if (answerVals.size() <= 0) {
			return;
		}
		Map<String, List<CardAnswer>> answerValsMap = new HashMap<>();
		for (CardAnswer answer : answerVals) {
			List<CardAnswer> panswers = answerValsMap.get(answer.getVersionid());
			if (panswers == null) {
				panswers = new ArrayList<>();
				answerValsMap.put(answer.getVersionid(), panswers);
			}
			panswers.add(answer);
		}
		// 填充道试卷中
		for (ChapterUnit chapter1 : paper.getChapters()) {
			// 第一層章節
			List<SubjectUnit> subjects = chapter1.getSubjects();
			loadSubjectVal(subjects, answerValsMap);
			for (ChapterUnit chapter2 : chapter1.getChapters()) {
				// 第二層章節
				List<SubjectUnit> subjects2 = chapter2.getSubjects();
				loadSubjectVal(subjects2, answerValsMap);
				for (ChapterUnit chapter3 : chapter2.getChapters()) {
					// 第三層章節
					List<SubjectUnit> subjects3 = chapter3.getSubjects();
					loadSubjectVal(subjects3, answerValsMap);
				}
			}
		}
	}

	/**
	 * 将考题值填充到题目中
	 * 
	 * @param subjects
	 *            试题序列
	 * @param answerVals
	 *            map<试题版本id,用户答案序列>
	 */
	private void loadSubjectVal(List<SubjectUnit> subjects, Map<String, List<CardAnswer>> answerVals) {
		for (SubjectUnit nuit : subjects) {
			List<CardAnswer> answers = answerVals.get(nuit.getVersion().getId());
			if (answers == null) {
				answers = new ArrayList<>();
			}
			nuit.getTipType().getHandle().loadVal(nuit, answers);
		}
	}

	@Override
	@Transactional
	public boolean isAnswerAble(Card card) {
		String endTime = card.getEndtime();
		if (TimeTool.getTimeDate14().compareTo(endTime) >= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public DataResult getRoomPaperUsers(final String roomId, final String paperid, DataQuery query) {
		final Room room = roomDaoImpl.getEntity(roomId);
		// 用户名称，得分，判卷人，判卷时间，答题开始时间，答题交卷时间，状态
		DataQuery thisQuery = null;
		if (room.getWritetype().equals("1")) {
			// /1指定人
			thisQuery = DataQuery.init(query,
					"WTS_ROOM_USER a LEFT JOIN WTS_CARD b ON a.USERID = b.USERID AND a.ROOMID = b.ROOMID and B.PAPERID = '"
							+ paperid + "'  LEFT JOIN alone_auth_user c ON c.ID = a.USERID",
					"c. NAME AS NAME,b.COMPLETENUM AS COMPLETENUM,b.ALLNUM as ALLNUM,c.ID AS USERID,B.ID as CARDID, b.POINT AS point, b.ADJUDGEUSERNAME AS ADJUDGEUSERNAME, b.ADJUDGETIME AS ADJUDGETIME, b.STARTTIME AS STARTTIME, b.ENDTIME AS endtime, b.PSTATE AS PSTATE, b.PSTATE AS PSTATETITLE");
			thisQuery.addRule(new DBRule("A.ROOMID", roomId, "="));
			DataQuerys.wipeVirus(paperid);
		}
		if (room.getWritetype().equals("0")) {
			// 0任何人
			thisQuery = DataQuery.init(query, "WTS_CARD b  LEFT JOIN alone_auth_user c ON c.ID = b.USERID",
					"c. NAME AS NAME,b.COMPLETENUM AS COMPLETENUM,b.ALLNUM as ALLNUM,c.ID AS USERID,B.ID as CARDID, b.POINT AS point, b.ADJUDGEUSERNAME AS ADJUDGEUSERNAME, b.ADJUDGETIME AS ADJUDGETIME, b.STARTTIME AS STARTTIME, b.ENDTIME AS endtime, b.PSTATE AS PSTATE, b.PSTATE AS PSTATETITLE");
			thisQuery.addRule(new DBRule("B.PAPERID", paperid, "="));
			thisQuery.addRule(new DBRule("B.ROOMID", roomId, "="));
		}
		if (room.getWritetype().equals("2")) {
			// 2匿名用戶（userid是一個伪id）
			thisQuery = DataQuery.init(query, "WTS_CARD b ",
					"b.USERID AS NAME,b.COMPLETENUM AS COMPLETENUM,b.ALLNUM as ALLNUM,B.USERID AS USERID,B.ID as CARDID, b.POINT AS point, b.ADJUDGEUSERNAME AS ADJUDGEUSERNAME, b.ADJUDGETIME AS ADJUDGETIME, b.STARTTIME AS STARTTIME, b.ENDTIME AS endtime, b.PSTATE AS PSTATE, b.PSTATE AS PSTATETITLE");
			thisQuery.addRule(new DBRule("B.PAPERID", paperid, "="));
			thisQuery.addRule(new DBRule("B.ROOMID", roomId, "="));
		}
		try {
			DataResult roomPaperUsers = thisQuery.search();
			roomPaperUsers.runHandle(new ResultsHandle() {
				// 处理答题且超时的答题卡，设置未超时未提交的状态
				@Override
				public void handle(Map<String, Object> row) {
					if (row.get("PSTATE") != null && row.get("PSTATE").equals("1")
							&& StringUtils.isNotBlank((String) row.get("USERID"))) {
						Card card = loadCard(paperid, roomId, (String) row.get("USERID"));
						row.put("PSTATE", card.getPstate());
						row.put("PSTATETITLE", card.getPstate());
					}
					if (room.getWritetype().equals("2")) {
						row.put("NAME", ((String) row.get("NAME")).replaceAll("ANONYMOUS", "匿名"));
					}
				}
			});
			return roomPaperUsers;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public CardInfo autoCountCardPoint(String cardid, LoginUser loginUser) {
		Card card = getCardEntity(cardid);
		Room room = roomDaoImpl.getEntity(card.getRoomid());
		List<SubjectUnit> subjects = loadUserSubjects(card);
		boolean isAllComplete = runPointCount(subjects, card);
		// 如果题目全部判完，就把考卷状态设置为已经阅卷完成的状态
		if (isAllComplete) {
			// 1.开始答题2.手动交卷3.超时未交卷,4.超时自动交卷,5完成阅卷6.发布成绩,7历史存档
			if (room != null && room.getCounttype() != null && room.getCounttype().equals("2")) {
				card.setPstate("6");
			} else {
				card.setPstate("5");
			}
			card.setAdjudgetime(TimeTool.getTimeDate14());
			card.setAdjudgeusername("自动");
		}
		card.setPoint(getCardPointSum(card));
		editCardEntity(card, loginUser);
		// ------------------統計用戶答題數量-----------------------
		CardInfo info = new CardInfo();
		{
			int completeNum = 0;
			info.setAllNum(papersubjectDaoImpl.countPaperSubjectNum(card.getPaperid()));
			for (SubjectUnit subject : subjects) {
				if (subject.isFinishIs()) {
					completeNum++;
				}
			}
			info.setCompleteNum(completeNum);
		}
		return info;
	}

	@Override
	@Transactional
	public void finishAdjudge(Card card, Map<String, Integer> points, LoginUser currentUser) {
		List<CardPoint> cardPoints = cardPointDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("CARDID", card.getId(), "=")).toList());
		for (CardPoint cardSub : cardPoints) {
			Integer point = points.get(cardSub.getVersionid());
			if (point != null) {
				CardPoint cardSubEntity = cardPointDaoImpl.getEntity(cardSub.getId());
				cardSubEntity.setPoint(point);
				// cardSubEntity.setComplete("1");
				cardPointDaoImpl.editEntity(cardSubEntity);
			}
		}
		card = getCardEntity(card.getId());
		card.setAdjudgetime(TimeTool.getTimeDate14());
		card.setAdjudgeuser(currentUser.getId());
		card.setAdjudgeusername(currentUser.getName());
		card.setPoint(getCardPointSum(card));
		// 1.开始答题2.手动交卷3.超时未交卷,4.超时自动交卷,5完成阅卷6.发布成绩,7历史存档
		card.setPstate("5");
		editCardEntity(card, currentUser);
	}

	@Override
	@Transactional
	public void publicPoint(String cardid, LoginUser currentUser) {
		Card card = getCardEntity(cardid);
		// 1.开始答题2.手动交卷3.超时未交卷,4.超时自动交卷,5完成阅卷6.发布成绩,7历史存档
		card.setPstate("6");
		editCardEntity(card, currentUser);
	}

	@Override
	@Transactional
	public void loadPaperUserNum(RoomUnit roomunit) {
		// 查找考場的指定人數
		for (PaperUnit paper : roomunit.getPapers()) {
			PaperUnit paperUnit = getRoomPaperUserNums(roomunit.getRoom().getId(), paper.getInfo().getId());
			if (roomunit.getRoom().getWritetype().equals("1")) {
				paper.setAllUserNum(paperUnit.getAllUserNum());
			}
			paper.setCurrentUserNum(paperUnit.getCurrentUserNum());
			paper.setAdjudgeUserNum(paperUnit.getAdjudgeUserNum());
		}
	}

	@Override
	@Transactional
	public PaperUnit getRoomPaperUserNums(String roomid, String paperid) {
		PaperUnit paperUnit = new PaperUnit();
		int currentUsers = cardDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "="))
				.add(new DBRule("ROOMID", roomid, "=")).toList());
		int adjudgeUserNum1 = cardDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "="))
				.add(new DBRule("ROOMID", roomid, "=")).add(new DBRule("PSTATE", "5", "=")).toList());
		int adjudgeUserNum2 = cardDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "="))
				.add(new DBRule("ROOMID", roomid, "=")).add(new DBRule("PSTATE", "6", "=")).toList());
		int adjudgeUserNum3 = cardDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "="))
				.add(new DBRule("ROOMID", roomid, "=")).add(new DBRule("PSTATE", "7", "=")).toList());
		int allUser = roomuserDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("ROOMID", roomid, "=")).toList());
		paperUnit.setCurrentUserNum(currentUsers);
		paperUnit.setAdjudgeUserNum(adjudgeUserNum1 + adjudgeUserNum2 + adjudgeUserNum3);
		paperUnit.setAllUserNum(allUser);
		return paperUnit;
	}

	@Override
	@Transactional
	public void clearRoomCard(String roomid, String paperid, LoginUser currentUser) {
		List<DBRule> rules = DBRuleList.getInstance().add(new DBRule("ROOMID", roomid, "="))
				.add(new DBRule("PAPERID", paperid, "=")).toList();
		List<Card> cards = cardDaoImpl.selectEntitys(rules);
		for (Card card : cards) {
			deleteCardEntity(card.getId(), currentUser);
		}
	}

	@Override
	@Transactional
	public void clearPaperUserCard(String roomid, String paperid, LoginUser user) {
		List<DBRule> rules = DBRuleList.getInstance().add(new DBRule("ROOMID", roomid, "="))
				.add(new DBRule("PAPERID", paperid, "=")).add(new DBRule("USERID", user.getId(), "=")).toList();
		List<Card> cards = cardDaoImpl.selectEntitys(rules);
		if (cards.size() > 1) {
			throw new RuntimeException("the cards is many card!");
		} else {
			for (Card card : cards) {
				deleteCardEntity(card.getId(), user);
			}
		}
	}
}
