package com.wts.exam.controller;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.PaperServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考卷控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/paper")
@Controller
public class PaperController extends WebUtils {
	private final static Logger log = Logger.getLogger(PaperController.class);
	@Resource
	private PaperServiceInter paperServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;

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
			DataResult result = paperServiceImpl.createPaperSimpleQuery(query).search();
			result.runDictionary("1:答卷模式,3:练习模式", "MODELTYPE");
			result.runDictionary("1:新建,0:停用,2:发布", "PSTATE");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询结果集合(考场选择试卷)
	 * 
	 * @return
	 */
	@RequestMapping("/chooseQuery")
	@ResponseBody
	public Map<String, Object> chooseQuery(DataQuery query, HttpServletRequest request, HttpSession session) {
		try {
			if (StringUtils.isNotBlank(query.getRule("B.TREECODE")) && query.getRule("B.TREECODE").equals("NONE")) {
				query.getAndRemoveRule("B.TREECODE");
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("a.PSTATE", "2", "="));
			{
				// 过滤权限
				String typeids_Rule = DataQuerys
						.parseSqlValues(examTypeServiceImpl.getUserPopTypeids(getCurrentUser(session).getId(), "1"));
				if (typeids_Rule != null) {
					query.addSqlRule(" and b.id in (" + typeids_Rule + ")");
				}
			}
			DataResult result = paperServiceImpl.createPaperSimpleQuery(query).search();
			result.runDictionary("1:答卷模式,3:练习模式", "MODELTYPE");
			result.runDictionary("1:新建,0:停用,2:发布", "PSTATE");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
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
	public Map<String, Object> editSubmit(Paper entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = paperServiceImpl.editPaperEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 进入随机出题界面
	 *
	 * @return
	 */
	@RequestMapping("/addRandomSubjects")
	public ModelAndView addRandomSubjects(String paperids, HttpSession session) {
		try {
			List<Paper> papers = new ArrayList<>();
			for (String id : parseIds(paperids)) {
				Paper paper = paperServiceImpl.getPaperEntity(id);
				if (paper != null) {
					papers.add(paper);
				}
			}
			return ViewMode.getInstance().putAttr("papers", papers).putAttr("paperids", paperids)
					.returnModelAndView("exam/PaperRandomSubjectsForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e)
					.returnModelAndView("exam/PaperRandomSubjectsForm");
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Paper entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = paperServiceImpl.insertPaperEntity(entity, getCurrentUser(session));
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
				paperServiceImpl.deletePaperEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 批量加载答卷信息
	 * 
	 * @return
	 */
	@RequestMapping("/loadPapersInfo")
	@ResponseBody
	public Map<String, Object> loadPapersInfo(String ids, HttpSession session) {
		try {
			List<PaperUnit> paperunits = new ArrayList<>();
			for (String id : parseIds(ids)) {
				paperunits.add(paperServiceImpl.getPaperUnit(id));
			}
			return ViewMode.getInstance().putAttr("papers", paperunits).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 添加随机题到答卷中
	 * 
	 * @return
	 */
	@RequestMapping("/doAddRandomSubjects")
	@ResponseBody
	public Map<String, Object> doAddRandomSubjects(String ids, String typeid, String tiptype, Integer subnum,
			Integer point, HttpSession session) {
		try {
			String warn = "";
			for (String paperid : parseIds(ids)) {
				warn = warn + paperServiceImpl.addRandomSubjects(paperid, typeid, tiptype, subnum, point,
						getCurrentUser(session));
				paperServiceImpl.refreshSubjectNum(paperid);
			}
			return ViewMode.getInstance().putAttr("warn", warn).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
	
	
	@RequestMapping("/doClearSubjects")
	@ResponseBody
	public Map<String, Object> doClearSubjects(String ids, HttpSession session) {
		try {
			String warn = "";
			for (String paperid : parseIds(ids)) {
				paperServiceImpl.clearPaper(paperid);
				paperServiceImpl.refreshSubjectNum(paperid);
			}
			return ViewMode.getInstance().putAttr("warn", warn).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
	

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/PaperResult");
	}

	@RequestMapping("/view")
	public ModelAndView view(String paperId, HttpSession session) {
		PaperUnit paper = paperServiceImpl.getPaperUnit(paperId);
		return ViewMode.getInstance().putAttr("paper", paper).returnModelAndView("exam/PaperView");
	}

	@RequestMapping("/chooselist")
	public ModelAndView chooselist(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/PaperChooseResult");
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
			Paper paper = null;
			if (StringUtils.isNotBlank(ids)) {
				paper = paperServiceImpl.getPaperEntity(ids);
				if (StringUtils.isNotBlank(paper.getExamtypeid())) {
					examtype = examTypeServiceImpl.getExamtypeEntity(paper.getExamtypeid());
				}
			}
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("examType", examtype)
						.putAttr("entity", paper).returnModelAndView("exam/PaperForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("exam/PaperForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("examType", examtype)
						.putAttr("entity", paper).returnModelAndView("exam/PaperForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/PaperForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/PaperForm");
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
				paperServiceImpl.editState(id, "0", getCurrentUser(session));
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
				paperServiceImpl.editState(id, "2", getCurrentUser(session));
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
				paperServiceImpl.examTypeSetting(id, examtypeId, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
