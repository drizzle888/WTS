package com.farm.wcp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.Card;
import com.wts.exam.domain.Room;
import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.MaterialServiceInter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.ExamPopsServiceInter;
import com.wts.exam.service.RoomServiceInter;
import com.wts.exam.service.SubjectAnalysisServiceInter;
import com.wts.exam.service.SubjectServiceInter;

/**
 * 判卷
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/adjudge")
@Controller
public class adjudgeWebController extends WebUtils {
	@Resource
	private RoomServiceInter roomServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private PaperServiceInter paperServiceImpl;
	@Resource
	private CardServiceInter cardServiceImpl;
	@Resource
	private ExamPopsServiceInter examPopsServiceImpl;
	@Resource
	private SubjectServiceInter subjectServiceImpl;
	@Resource
	private MaterialServiceInter materialServiceImpl;
	@Resource
	private SubjectAnalysisServiceInter SubjectAnalysisServiceImpl;
	private static final Logger log = Logger.getLogger(adjudgeWebController.class);

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/***
	 * 考场判卷首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/roompage")
	public ModelAndView index(String roomid, HttpServletRequest request, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			// 进入答题室：1.判断时间，判断人员
			if (getCurrentUser(session) == null) {
				throw new RuntimeException("请先登陆系统!");
			}
			if (!examPopsServiceImpl.isJudger(roomid, getCurrentUser(session))) {
				throw new RuntimeException("当前非判卷用户!");
			}
			RoomUnit roomunit = roomServiceImpl.getRoomUnit(roomid, getCurrentUser(session), false);
			cardServiceImpl.loadPaperUserNum(roomunit);
			PaperUnit allPaper = new PaperUnit();
			for (PaperUnit paper : roomunit.getPapers()) {
				allPaper.setAdjudgeUserNum(allPaper.getAdjudgeUserNum() + paper.getAdjudgeUserNum());
				allPaper.setCurrentUserNum(allPaper.getCurrentUserNum() + paper.getCurrentUserNum());
				allPaper.setAllUserNum(paper.getAllUserNum());
			}
			return view.putAttr("room", roomunit).putAttr("allPaperNum", allPaper)
					.returnModelAndView(getThemePath() + "/adjudge/roomPaper");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/***
	 * 加载题解析
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadSubjectinfo")
	public ModelAndView loadSubjectinfo(String subjectId, HttpServletRequest request, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			SubjectUnit subjectUnit = subjectServiceImpl
					.getSubjectUnit(subjectServiceImpl.getSubjectVersionId(subjectId));

			if (StringUtils.isNotBlank(subjectUnit.getSubject().getMaterialid())) {
				// 如有有引用材料的话f
				view.putAttr("material",
						materialServiceImpl.getMaterialEntity(subjectUnit.getSubject().getMaterialid()));
			}
			if (subjectUnit.getSubject().getAnalysisnum() > 0) {
				// 如有解析的話
				view.putAttr("analysis", (List<SubjectAnalysis>) SubjectAnalysisServiceImpl
						.getSubjectAnalysies(subjectUnit.getSubject().getId()));
			}

			return view.putAttr("subjectu", subjectUnit)
					.returnModelAndView(getThemePath() + "/adjudge/commons/loadSubjectInfo");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 试题预览页面
	 *
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView viewLoad(String versionid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		SubjectUnit subjectUnit = subjectServiceImpl.getSubjectUnit(versionid);
		return view.putAttr("subjectu", subjectUnit).returnModelAndView(subjectUnit.getTipType().getVeiwPage());
	}

	/***
	 * 考卷答题人页面(判卷)
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/paperUser")
	public ModelAndView paperUser(String roomId, String paperid, DataQuery query, String state,
			HttpServletRequest request, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			{// 构造答题卡状态条件
				if (StringUtils.isNotBlank(state)) {
					query.addSqlRule(getInSql(state, "B.PSTATE"));
				} else {
					state = "all";
				}
				view.putAttr("state", state);
			}
			// 用户名称，得分，判卷人，判卷时间，答题开始时间，答题交卷时间，状态
			Room room = roomServiceImpl.getRoomEntity(roomId);
			Paper paper = paperServiceImpl.getPaperEntity(paperid);
			query.setPagesize(1000);
			query.setNoCount();
			query.addSort(new DBSort("B.PSTATE", "ASC"));
			query.addSort(new DBSort("B.STARTTIME", "ASC"));
			DataResult result = cardServiceImpl.getRoomPaperUsers(roomId, paperid, query);
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					if (row.get("COMPLETENUM") != null && row.get("ALLNUM") != null && ((int) row.get("ALLNUM") != 0)) {
						int percent = (int) row.get("COMPLETENUM") * 100 / (int) row.get("ALLNUM");
						row.put("COMPLETEPERCENT", percent);
					}
				}
			});
			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm:ss");
			result.runformatTime("ENDTIME", "HH:mm:ss");
			result.runformatTime("ADJUDGETIME", "yyyy-MM-dd HH:mm:ss");
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩", "PSTATETITLE");
			return view.putAttr("room", room).putAttr("paper", paper).putAttr("result", result)
					.returnModelAndView(getThemePath() + "/adjudge/paperUser");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 构造一个in sql语句 如 and B.PSTATE in('1','2','3')
	 * 
	 * @param state
	 * @param title
	 * @return
	 */
	private String getInSql(String state, String title) {
		String stateSql = null;
		for (char sta : state.toCharArray()) {
			if (stateSql == null) {
				stateSql = " and " + title + " in (" + "'" + sta + "'";
			} else {
				stateSql = stateSql + ",'" + sta + "'";
			}
		}
		return stateSql = stateSql + ")";
	}

	/***
	 * 答题室所有答题人页面(只查看不判卷)
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/roomUser")
	public ModelAndView roomUser(String roomId, DataQuery query, String state, HttpServletRequest request,
			HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			{// 构造答题卡状态条件
				if (StringUtils.isNotBlank(state)) {
					query.addSqlRule(getInSql(state, "B.PSTATE"));
				} else {
					state = "all";
				}
				view.putAttr("state", state);
			}
			// 用户名称，得分，判卷人，判卷时间，答题开始时间，答题交卷时间，状态
			Room room = roomServiceImpl.getRoomEntity(roomId);
			query.setPagesize(1000);
			query.setNoCount();
			DataResult result = cardServiceImpl.getRoomUsers(roomId, query);
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					if (row.get("COMPLETENUM") != null && row.get("ALLNUM") != null && ((int) row.get("ALLNUM") != 0)) {
						int percent = (int) row.get("COMPLETENUM") * 100 / (int) row.get("ALLNUM");
						row.put("COMPLETEPERCENT", percent);
					}
				}
			});
			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm:ss");
			result.runformatTime("ENDTIME", "HH:mm:ss");
			result.runformatTime("ADJUDGETIME", "yyyy-MM-dd HH:mm:ss");
			// 1.开始答题2.手动交卷3.超时未交卷,4.超时自动交卷, 5已自动阅卷 6已完成阅卷7.发布成绩
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩", "PSTATETITLE");
			return view.putAttr("room", room).putAttr("result", result)
					.returnModelAndView(getThemePath() + "/adjudge/roomUser");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 判卷页面
	 * 
	 * @param roomId
	 * @param paperid
	 * @param query
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/adjudgePage")
	public ModelAndView adjudgePage(String cardId, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			// 创建答题卡
			Card card = cardServiceImpl.getCardEntity(cardId);
			if (card == null) {
				throw new RuntimeException("未找到答题卡，请确认答题卡是否存在!" + cardId);
			}
			Room room = roomServiceImpl.getRoomEntity(card.getRoomid());
			// 从答题卡中加载用户答案
			PaperUnit paper = paperServiceImpl.getPaperUnit(card.getPaperid());
			LoginUser user = FarmAuthorityService.getInstance().getUserById(card.getUserid());
			cardServiceImpl.loadCardVal(paper, card);
			// 加载考卷的得分到试卷中，判卷的时候会用
			cardServiceImpl.loadCardPoint(paper, card);
			return view.putAttr("paper", paper).putAttr("user", user).putAttr("flag", "adjudge").putAttr("room", room)
					.putAttr("card", card).returnModelAndView(getThemePath() + "/adjudge/adjudgeCard");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 判卷人强制收卷
	 * 
	 * @return
	 */
	@RequestMapping("/recovery")
	@ResponseBody
	public Map<String, Object> recovery(String cardid, HttpSession session) {
		try {
			ViewMode page = ViewMode.getInstance();
			Card card = cardServiceImpl.getCardEntity(cardid);
			if (card.getPstate().equals("1") || card.getPstate().equals("3")) {
				// 1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩
				cardServiceImpl.finishExam(cardid, getCurrentUser(session));
			} else {
				throw new RuntimeException("考卷状态异常，无法计算分数!");
			}
			return page.returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 自动计算得分
	 * 
	 * @return
	 */
	@RequestMapping("/autoCount")
	@ResponseBody
	public Map<String, Object> autoCount(String cardid, HttpSession session) {
		try {
			ViewMode page = ViewMode.getInstance();
			Card card = cardServiceImpl.getCardEntity(cardid);
			if (!card.getPstate().equals("2") && !card.getPstate().equals("3") && !card.getPstate().equals("4")) {
				// 1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩
				Map<String, String> stateMpa = new HashMap<>();
				stateMpa.put("1", "开始答题");
				stateMpa.put("2", "手动交卷");
				stateMpa.put("3", "超时未交卷");
				stateMpa.put("4", "超时自动交卷");
				stateMpa.put("5", "已自动阅卷");
				stateMpa.put("6", "已完成阅卷");
				stateMpa.put("7", "发布成绩");
				throw new RuntimeException("答题卡状态：(" + stateMpa.get(card.getPstate()) + ")，当前无法计算分数!");
			}
			cardServiceImpl.autoCountCardPoint(cardid);
			return page.returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 发布分数
	 * 
	 * @param cardId
	 * @param session
	 * @return
	 */
	@RequestMapping("/publicPoint")
	public ModelAndView publicPoint(String cardId, HttpSession session) {
		try {
			ViewMode page = ViewMode.getInstance();
			Card card = cardServiceImpl.getCardEntity(cardId);
			if (examPopsServiceImpl.isNotJudger(card.getRoomid(), getCurrentUser(session))) {
				throw new RuntimeException("当前用户无权变更答题卡状态!");
			}
			if (!card.getPstate().equals("6") && !card.getPstate().equals("5")) {
				// 1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩
				throw new RuntimeException("考卷未完成阅卷，无法发布分数!");
			}
			cardServiceImpl.publicPoint(cardId, getCurrentUser(session));
			return page.returnRedirectOnlyUrl(
					"/adjudge/paperUser.do?paperid=" + card.getPaperid() + "&roomId=" + card.getRoomid());
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 结束阅卷，提交分数
	 * 
	 * @param cardId
	 * @param session
	 * @return
	 */
	@RequestMapping("/submitAdjudge")
	public ModelAndView submitAdjudge(String cardId, String jsons, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			Card card = cardServiceImpl.getCardEntity(cardId);
			Set<String> usertypeids = examTypeServiceImpl.getUserPopTypeids(getCurrentUser(session).getId(), "2");
			String examTypeid = roomServiceImpl.getRoomEntity(card.getRoomid()).getExamtypeid();
			if (!usertypeids.contains(examTypeid)) {
				throw new RuntimeException("当前用户无权提交分数!");
			}
			if (card.getPstate().equals("1")) {
				// 1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩
				throw new RuntimeException("考卷状态异常，无法提交分数!");
			}
			JsonParser parse = new JsonParser(); // 创建json解析器
			JsonArray jsonArray = (JsonArray) parse.parse(jsons);
			Map<String, Integer> points = new HashMap<>();
			for (JsonElement obj : jsonArray) {
				if (obj.isJsonObject()) {
					JsonObject sjonObj = obj.getAsJsonObject();
					String versionid = sjonObj.get("versionid").getAsString();
					Integer value = sjonObj.get("value").getAsInt();
					points.put(versionid, value);
				}
			}
			cardServiceImpl.finishAdjudge(card, points, getCurrentUser(session));
			return view.putAttr("MESSAGE", "试卷提交成功!").returnRedirectOnlyUrl(
					"/adjudge/paperUser.do?paperid=" + card.getPaperid() + "&roomId=" + card.getRoomid());
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

}
