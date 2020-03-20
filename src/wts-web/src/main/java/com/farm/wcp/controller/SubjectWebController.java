package com.farm.wcp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.SubjectTestUtils;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;
import com.wts.exam.domain.Card;
import com.wts.exam.domain.RoomPaper;
import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.SubjectComment;
import com.wts.exam.domain.SubjectUserOwn;
import com.wts.exam.domain.ex.AnswerUnit;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.MaterialServiceInter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.PaperUserOwnServiceInter;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.ExamStatServiceInter;
import com.wts.exam.service.RoomServiceInter;
import com.wts.exam.service.SubjectAnalysisServiceInter;
import com.wts.exam.service.SubjectCommentServiceInter;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.SubjectUserOwnServiceInter;

/**
 * 考题
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/websubject")
@Controller
public class SubjectWebController extends WebUtils {
	@Resource
	private PaperServiceInter paperServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private RoomServiceInter roomServiceImpl;
	@Resource
	private CardServiceInter cardServiceImpl;
	@Resource
	private SubjectServiceInter subjectServiceImpl;
	@Resource
	private SubjectAnalysisServiceInter SubjectAnalysisServiceImpl;
	@Resource
	private MaterialServiceInter materialServiceImpl;
	@Resource
	private SubjectUserOwnServiceInter subjectUserOwnServiceImpl;
	@Resource
	private SubjectCommentServiceInter SubjectCommentServiceImpl;
	@Resource
	private PaperUserOwnServiceInter paperUserOwnServiceImpl;
	@Resource
	private ExamStatServiceInter examStatServiceImpl;

	private static final Logger log = Logger.getLogger(SubjectWebController.class);

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/***
	 * 随机测试答题卡(匿名答题，继续答题)
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubRandomSubject")
	public ModelAndView PubRandomSubject(String testid, String roomid, Integer index, String paperid,
			HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> test = null;
			if (StringUtils.isBlank(testid)) {
				if (!roomServiceImpl.testAble(roomid, paperid)) {
					throw new RuntimeException("当前答卷不支持在线练习!");
				}
				// 首次創建test
				PaperUnit paper = paperServiceImpl.getPaperUnit(paperid);
				List<SubjectUnit> subjects = paperServiceImpl.getPaperSubjects(paper.getChapters());
				String testTitle = paper.getInfo().getName();
				RoomPaper roompaper = roomServiceImpl.getRoomPaper(roomid, paperid);
				if (roompaper != null && StringUtils.isNotBlank(roompaper.getName())) {
					// 使用别名
					testTitle = roompaper.getName();
				}
				testid = SubjectTestUtils.creatTest(subjects, testTitle, session);
				if (StringUtils.isNotBlank(paperid)) {
					// 把paperid存入测试参数中
					SubjectTestUtils.putAttribute(testid, "paperid", paperid, session);
					// 测试计数
					examStatServiceImpl.addStartTestNum(paperid, getCurrentUser(session));
				}
				index = 1;
			}
			test = SubjectTestUtils.getTest(index, testid, materialServiceImpl, session);
			{// 处理答题记录
				if (getCurrentUser(session) != null && test.get("paperid") != null && test.get("YESPEN") != null) {
					// 记录用户答卷记录(匿名房间不记录)
					String spen = "0";
					if (test.get("YESPEN") != null) {
						spen = test.get("YESPEN").toString();
					}
					paperUserOwnServiceImpl.addDoPaperInfo((String) test.get("paperid"), Integer.valueOf(spen),
							getCurrentUser(session));
				}
			}
			if (test.get("STATE").equals(3)) {
				index = (int) test.get("ALLNUM") + 1;
			}
			if (index == null) {
				index = (int) test.get("INDEX");
			}
			if ((int) test.get("STATE") == 1 || (int) test.get("STATE") == 2) {
				// 重置测试
				String message = "该测试结束，请重新开始一场测试!";
				throw new RuntimeException(message);
			}
			// 传出题目
			return ViewMode.getInstance().putAttr("test", test).putAttr("subjectu", test.get("SUBJECTU"))
					.putAttr("flag", "answer").putAttr("index", index).putAttr("testid", testid)
					.returnModelAndView(ThemesUtil.getThemePage("test-cardPage", request));
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 单独开辟一个随机测试，入参为随机测试的题集合（题ID）
	 * 
	 * @param subjectids
	 * @param testid
	 * @param index
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubSubject")
	public ModelAndView PubSubject(String subjectids, String testid, Integer index, HttpServletRequest request,
			HttpSession session) {
		try {
			Map<String, Object> test = null;
			if (StringUtils.isBlank(testid)) {
				// 首次創建test
				List<SubjectUnit> subjects = new ArrayList<>();
				for (String subjectId : parseIds(subjectids)) {
					Subject subject = subjectServiceImpl.getSubjectEntity(subjectId);
					if (subject != null) {
						subjects.add(subjectServiceImpl.getSubjectUnit(subject.getVersionid()));
					}
				}
				testid = SubjectTestUtils.creatTest(subjects, "", session);
				index = 1;
			}
			test = SubjectTestUtils.getTest(index, testid, materialServiceImpl, session);
			if (index == null) {
				index = (int) test.get("INDEX");
			}
			if ((int) test.get("STATE") == 1 || (int) test.get("STATE") == 2) {
				// 重置测试
				String message = "该测试结束，请重新开始一场测试!";
				throw new RuntimeException(message);
			}
			// 传出题目
			return ViewMode.getInstance().putAttr("test", test).putAttr("subjectu", test.get("SUBJECTU"))
					.putAttr("flag", "answer").putAttr("index", index).putAttr("testid", testid)
					.returnModelAndView(ThemesUtil.getThemePage("test-cardPage", request));
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 单独开辟一个随机测试，入参为随机测试的题集合（用户题库）
	 * 
	 * @param subjectOwnIds
	 * @param testid
	 * @param index
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubOwnSubject")
	public ModelAndView PubOwnSubject(String subjectOwnIds, String testid, Integer index, HttpServletRequest request,
			HttpSession session) {
		try {
			String subjectIds = "";
			for (String id : parseIds(subjectOwnIds)) {
				SubjectUserOwn ownEntity = subjectUserOwnServiceImpl.getSubjectuserownEntity(id);
				if (ownEntity != null) {
					if (StringUtils.isBlank(ownEntity.getCardid())) {
						// 不是来源答卷的直接可见
						subjectIds = subjectIds + "," + ownEntity.getSubjectid();
					} else {
						Card card = cardServiceImpl.getCardEntity(ownEntity.getCardid());
						// 1.开始答题2.手动交卷3.超时未交卷,4.超时自动交卷,5完成阅卷6.发布成绩,7历史存档
						if (card == null || card.getPstate().equals("6") || card.getPstate().equals("7")) {
							// 不是来源答卷的直接可见
							subjectIds = subjectIds + "," + ownEntity.getSubjectid();
						}
					}
				}
			}
			// 传出题目
			return PubSubject(subjectIds, testid, index, request, session);
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 计算一道题得分
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/PubRunPoint")
	@ResponseBody
	public Map<String, Object> PubRunPoint(String testid, String versionId, String val, HttpSession session) {
		try {
			ViewMode page = ViewMode.getInstance();
			LoginUser user = getCurrentUser(session);
			String jsons = StringEscapeUtils.unescapeHtml(val);
			// 1.对题进行打分，并返回结果
			SubjectUnit unit = subjectServiceImpl.parseSubjectJsonVal(jsons);
			int pointWeight = unit == null ? 0 : cardServiceImpl.countSubjectPoint(unit);
			Map<String, Object> ctest = (Map<String, Object>) session.getAttribute(testid);
			if (ctest == null) {
				throw new RuntimeException("当前测试过期，请重新开始答题!");
			}
			List<SubjectUnit> subjects = (List<SubjectUnit>) ctest.get("subjects");
			for (SubjectUnit node : subjects) {
				// 把结果放回缓存中，Y正确，N表示错误或者半对
				if (node.getVersion().getId().equals(versionId)) {
					node.setVal(pointWeight == 100 ? "Y" : "N");
				}
			}
			if (user != null && unit != null && unit.getSubject() != null) {
				// 2.把错题加入错题集合// 3.用户答题历史存入，答题历史记录
				subjectUserOwnServiceImpl.addFinishTestSubject(unit.getSubject().getId(), pointWeight == 100, user);
			}
			return page.putAttr("point", pointWeight).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/***
	 * 传出题的解析(loadPage)
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubAnalysis")
	@ResponseBody
	public Map<String, Object> PubAnalysis(String versionId, HttpServletRequest request, HttpSession session) {
		try {
			LoginUser user = getCurrentUser(session);
			// 获得解析，传出解析
			ViewMode view = ViewMode.getInstance();
			SubjectUnit subject = subjectServiceImpl.getSubjectUnit(versionId);
			List<SubjectAnalysis> analyses = SubjectAnalysisServiceImpl
					.getSubjectAnalysies(subject.getSubject().getId());
			List<AnswerUnit> answers = subject.getAnswers();
			List<AnswerUnit> rightAnswers = new ArrayList<>();
			for (AnswerUnit node : answers) {
				if (node.getAnswer().getRightanswer().equals("1") || node.getAnswer().getRightanswer().equals("2")) {
					rightAnswers.add(node);
				}
			}
			return view.putAttr("analyses", analyses).putAttr("answers", rightAnswers).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 收藏题（/取消收藏）
	 * 
	 * @return
	 */
	@RequestMapping("/book")
	@ResponseBody
	public Map<String, Object> book(String subjectId, Boolean isDo, HttpSession session) {
		try {
			LoginUser user = getCurrentUser(session);
			ViewMode page = ViewMode.getInstance();
			if (user == null) {
				throw new RuntimeException("请先登陆用户!");
			}
			boolean isBook = false;
			if (isDo) {
				// 执行收藏
				isBook = subjectUserOwnServiceImpl.doBook(subjectId, user.getId());
			} else {
				// 获取状态
				isBook = subjectUserOwnServiceImpl.isBook(subjectId, user.getId());
			}
			int bookNum = subjectUserOwnServiceImpl.getBookNum(subjectId, user.getId());
			return page.putAttr("num", bookNum).putAttr("isBook", isBook).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 点赞题（/取消点赞）
	 * 
	 * @return
	 */
	@RequestMapping("/praise")
	@ResponseBody
	public Map<String, Object> praise(String subjectId, Boolean isPraise, HttpSession session) {
		try {
			LoginUser user = getCurrentUser(session);
			ViewMode page = ViewMode.getInstance();
			if (user == null) {
				throw new RuntimeException("请先登陆用户!");
			}
			int praisenum = subjectServiceImpl.doPraise(subjectId, user);
			return page.putAttr("num", praisenum).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 进入试题评论页
	 * 
	 * @param subjectids
	 * @param testid
	 * @param index
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/comment")
	public ModelAndView comment(String subjectid, HttpServletRequest request, HttpSession session) {
		try {
			SubjectUnit subjectUnit = subjectServiceImpl
					.getSubjectUnit(subjectServiceImpl.getSubjectVersionId(subjectid));
			List<SubjectComment> comments = SubjectCommentServiceImpl.getSubjectComments(subjectid);
			// 传出题目
			return ViewMode.getInstance().putAttr("subjectu", subjectUnit).putAttr("comments", comments)
					.returnModelAndView(getThemePath() + "/subject/loadComments");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/**
	 * 发布评论
	 * 
	 * @param docid
	 * @param content
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/addComment")
	@ResponseBody
	public Map<String, Object> addComment(String subjectid, String text, HttpSession session,
			HttpServletRequest request) {
		try {
			SubjectCommentServiceImpl.insertSubjectComment(text, subjectid, getCurrentUser(session));
			int num = subjectServiceImpl.refrashCommentnum(subjectid);
			return ViewMode.getInstance().putAttr("subjectid", subjectid).putAttr("num", num).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 发布评论
	 * 
	 * @param docid
	 * @param content
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/delComment")
	@ResponseBody
	public Map<String, Object> delComment(String id, HttpSession session, HttpServletRequest request) {
		try {
			SubjectComment comment = SubjectCommentServiceImpl.getSubjectCommentEntity(id);
			SubjectCommentServiceImpl.deleteSubjectCommentEntity(id, getCurrentUser(session));
			int num = subjectServiceImpl.refrashCommentnum(comment.getSubjectid());
			return ViewMode.getInstance().putAttr("num", num).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
