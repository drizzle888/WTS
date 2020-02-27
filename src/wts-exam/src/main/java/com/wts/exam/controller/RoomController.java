package com.wts.exam.controller;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.Room;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.RoomServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考试控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/room")
@Controller
public class RoomController extends WebUtils {
	private final static Logger log = Logger.getLogger(RoomController.class);
	@Resource
	private RoomServiceInter roomServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private PaperServiceInter paperServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request, HttpSession session) {
		try {
			if (StringUtils.isNotBlank(query.getRule("B.TREECODE")) && query.getRule("B.TREECODE").equals("NONE")) {
				query.getAndRemoveRule("B.TREECODE");
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			{
				// 过滤权限
				String typeids_Rule = DataQuerys
						.parseSqlValues(examTypeServiceImpl.getUserPopTypeids(getCurrentUser(session).getId(), "1"));
				if (typeids_Rule != null) {
					query.addSqlRule(" and b.id in (" + typeids_Rule + ")");
				} else {
					query.addSqlRule(" and b.id ='NONE'");
				}
			}
			DataResult result = roomServiceImpl.createRoomSimpleQuery(query).search();
			result.runDictionary("1:永久,2:限时", "TIMETYPE");
			result.runDictionary("1:指定人员,0:任何人员,2:匿名答题", "WRITETYPETITLE");
			result.runDictionary("1:自动/人工,2:自动,3:人工", "COUNTTYPE");
			result.runDictionary("1:新建,2:发布,0:禁用", "PSTATETITLE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 檢查考場狀態
	 * 
	 * @return
	 */
	@RequestMapping("/examValid")
	@ResponseBody
	public Map<String, Object> examValid(String roomid, HttpSession session) {
		try {
			boolean isWarning = false;
			StringBuffer info = new StringBuffer();
			StringBuffer warning = new StringBuffer();
			info.append("答题室中");//《" + room.getName() + "》"); 
			info.append("答题室中");//《" + room.getName() + "》");
			// 检查试卷有几张
			List<Paper> papers = roomServiceImpl.getLivePapers(roomid);
			info.append("含" + papers.size() + "张答卷;");
			if (papers.size() <= 0) {
				isWarning = true;
				warning.append("无试卷;");
			}
			// 检查试卷是否配置得分
			for (Paper paper : papers) {
				if (!paperServiceImpl.isAllSubjectHavePoint(paper.getId())) {
					isWarning = true;
					warning.append("《" + paper.getName() + "》未设置得分;");
				}
			}
			// 检查试卷是否全部设置了客观题答案
			for (Paper paper : papers) {
				if (!paperServiceImpl.isHaveSubjects(paper.getId())) {
					isWarning = true;
					warning.append("《" + paper.getName() + "》未包含问题;");
				}
				if (!paperServiceImpl.isAllHaveObjectiveSubjectAnswer(paper.getId())) {
					isWarning = true;
					warning.append("《" + paper.getName() + "》有客观题未设置答案;");
				}
			}
			// 检查考场是否有答题人
			if (!roomServiceImpl.isHaveEffectiveAnswer(roomid)) {
				isWarning = true;
				warning.append("无有效答题人;");
			}
			// 检查考场是否有判卷人
			if (!roomServiceImpl.isHaveAdjudger(roomid)) {
				isWarning = true;
				warning.append("无阅卷人;");
			}

			ViewMode view = ViewMode.getInstance();
			view.putAttr("info", info.toString());
			if (isWarning) {
				view.putAttr("warning", warning.toString());
			}
			return view.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Room entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = roomServiceImpl.editRoomEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Room entity, HttpSession session) {
		try {
			entity = roomServiceImpl.insertRoomEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				roomServiceImpl.deleteRoomEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 考试禁用
	 * 
	 * @return
	 */
	@RequestMapping("/examPrivate")
	@ResponseBody
	public Map<String, Object> examPrivate(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				roomServiceImpl.editState(id, "0", getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 考试发布
	 * 
	 * @return
	 */
	@RequestMapping("/examPublic")
	@ResponseBody
	public Map<String, Object> examPublic(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				roomServiceImpl.editState(id, "2", getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 设置考试分类
	 * 
	 * @return
	 */
	@RequestMapping("/examtypeSetting")
	@ResponseBody
	public Map<String, Object> examtypeSetting(String ids, String examtypeId, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				roomServiceImpl.examTypeSetting(id, examtypeId, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/RoomResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			ExamType examtype = null;
			Room room = null;
			if (StringUtils.isNotBlank(ids)) {
				room = roomServiceImpl.getRoomEntity(ids);
				if (StringUtils.isNotBlank(room.getExamtypeid())) {
					examtype = examTypeServiceImpl.getExamtypeEntity(room.getExamtypeid());
				}
			}
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", room)
						.putAttr("examType", examtype).returnModelAndView("exam/RoomForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("exam/RoomForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("examType", examtype)
						.putAttr("entity", room).returnModelAndView("exam/RoomForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/RoomForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/RoomForm");
		}
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/popinfo")
	public ModelAndView popinfo(RequestMode pageset, String ids) {
		try {
			Room room = roomServiceImpl.getRoomEntity(ids);
			ExamType type = examTypeServiceImpl.getExamtypeEntity(room.getExamtypeid());
			// 查找房间的答题人
			List<Map<String, Object>> examUsers = new ArrayList<>();
			if (room.getWritetype().equals("0")) {
				// 0所有人均可
				Map<String, Object> node = new HashMap<>();
				node.put("USERNAME", "<span class='feild_green'>所有人</span>");
				node.put("ROOMNAME", "<span class='feild_green'>" + room.getName() + "</span>");
				examUsers.add(node);
			} else {
				examUsers = roomServiceImpl.getRoomUsers(room.getId());
			}
			// 查找房间的管理人1所有人,2指定人3无权限
			List<Map<String, Object>> mngUsers = new ArrayList<>();
			if (type.getMngpop().equals("1")) {
				Map<String, Object> node = new HashMap<>();
				node.put("USERNAME", "<span class='feild_green'>所有人</span>");
				node.put("TYPENAME", "<span class='feild_green'>" + type.getName() + "</span>");
				mngUsers.add(node);
			}
			if (type.getMngpop().equals("2")) {
				mngUsers = examTypeServiceImpl.getTypePopUsers(room.getExamtypeid(), "1");
			}
			if (type.getMngpop().equals("3")) {
				Map<String, Object> node = new HashMap<>();
				node.put("USERNAME", "<span class='feild_red'>未授权</span>");
				node.put("TYPENAME", "<span class='feild_red'>" + type.getName() + "</span>");
				mngUsers.add(node);
			}
			// 查找房间的阅卷人1所有人,2指定人3无权限
			List<Map<String, Object>> adjudgeUsers = new ArrayList<>();
			if (type.getAdjudgepop().equals("1")) {
				Map<String, Object> node = new HashMap<>();
				node.put("USERNAME", "<span class='feild_green'>所有人</span>");
				node.put("TYPENAME", "<span class='feild_green'>" + type.getName() + "</span>");
				adjudgeUsers.add(node);
			}
			if (type.getAdjudgepop().equals("2")) {
				adjudgeUsers = examTypeServiceImpl.getTypePopUsers(room.getExamtypeid(), "2");
			}
			if (type.getAdjudgepop().equals("3")) {
				Map<String, Object> node = new HashMap<>();
				node.put("USERNAME", "<span class='feild_red'>未授权</span>");
				node.put("TYPENAME", "<span class='feild_red'>" + type.getName() + "</span>");
				adjudgeUsers.add(node);
			}
			return ViewMode.getInstance().putAttr("examUsers", examUsers).putAttr("mngUsers", mngUsers)
					.putAttr("adjudgeUsers", adjudgeUsers).returnModelAndView("exam/RoomPop");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/RoomPop");
		}
	}
}
