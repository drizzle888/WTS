package com.wts.exam.controller;

import com.wts.exam.domain.Paper;
import com.wts.exam.domain.PaperChapter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.PaperChapterServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;

import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：试卷章节控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/paperchapter")
@Controller
public class PaperChapterController extends WebUtils {
	private final static Logger log = Logger.getLogger(PaperChapterController.class);
	@Resource
	private PaperChapterServiceInter paperChapterServiceImpl;
	@Resource
	private PaperServiceInter paperServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = paperChapterServiceImpl.createPaperchapterSimpleQuery(query).search();
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 考卷章节
	 */
	@RequestMapping("/chapterTypeTree")
	@ResponseBody
	public Object chapterTypeTree(String id, String paperId) {
		try {
			if (id == null) {
				Paper paper = paperServiceImpl.getPaperEntity(paperId);
				// 如果是未传入id，就是根节点，就构造一个虚拟的上级节点
				id = "NONE";
				List<EasyUiTreeNode> list = new ArrayList<>();
				EasyUiTreeNode nodes = new EasyUiTreeNode("NONE", paper.getName(), "open", "icon-customers");
				nodes.setChildren(
						EasyUiTreeNode.formatAsyncAjaxTree(
								EasyUiTreeNode
										.queryTreeNodeOne(id, "SORT",
												"(SELECT * from WTS_PAPER_CHAPTER where PAPERID='" + paper.getId()
														+ "')",
												"ID", "PARENTID", "NAME", "STYPE")
										.getResultList(),
								EasyUiTreeNode
										.queryTreeNodeTow(id, "SORT",
												"(SELECT * from WTS_PAPER_CHAPTER where PAPERID='" + paper.getId()
														+ "')",
												"ID", "PARENTID", "NAME", "STYPE")
										.getResultList(),
								"PARENTID", "ID", "NAME", "STYPE"));

				list.add(nodes);
				return list;
			}
			return EasyUiTreeNode.formatAsyncAjaxTree(
					EasyUiTreeNode.queryTreeNodeOne(id, "SORT", "WTS_PAPER_CHAPTER", "ID", "PARENTID", "NAME", "STYPE")
							.getResultList(),
					EasyUiTreeNode.queryTreeNodeTow(id, "SORT", "WTS_PAPER_CHAPTER", "ID", "PARENTID", "NAME", "STYPE")
							.getResultList(),
					"PARENTID", "ID", "NAME", "STYPE");
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
	public Map<String, Object> editSubmit(PaperChapter entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = paperChapterServiceImpl.editPaperchapterEntity(entity, getCurrentUser(session));
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
	public Map<String, Object> addSubmit(PaperChapter entity, HttpSession session) {
		try {
			entity = paperChapterServiceImpl.insertPaperchapterEntity(entity, getCurrentUser(session));
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
				paperChapterServiceImpl.deletePaperchapterEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 题在章节内的排序上移
	 * 
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping("/sortUp")
	@ResponseBody
	public Map<String, Object> sortUp(String paperSubjectIds, HttpSession session) {
		try {
			for (String id : parseIds(paperSubjectIds)) {
				paperChapterServiceImpl.subjectSortUp(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 进入题分数设置页面
	 * 
	 * @param paperSubjectIds
	 * @param session
	 * @return
	 */
	@RequestMapping("/pointSetPage")
	public ModelAndView pointSetPage(String paperSubjectIds, HttpSession session) {
		List<String> idlist = parseIds(paperSubjectIds);
		RequestMode pageset = new RequestMode();
		pageset.setOperateType(OperateType.UPDATE.value);
		return ViewMode.getInstance().putAttr("paperSubjectIds", paperSubjectIds).putAttr("lenght", idlist.size())
				.putAttr("pageset", pageset).returnModelAndView("exam/PaperChapterPointSetting");
	}

	@RequestMapping("/list")
	public ModelAndView index(String paperId, HttpSession session) {
		Paper paper = paperServiceImpl.getPaperEntity(paperId);
		return ViewMode.getInstance().putAttr("paperId", paperId).putAttr("paper", paper)
				.returnModelAndView("exam/PaperchapterResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String parentid, String paperid, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", paperChapterServiceImpl.getPaperchapterEntity(ids))
						.returnModelAndView("exam/PaperchapterForm");
			}
			case (1): {// 新增
				PaperChapter chapter = new PaperChapter();
				chapter.setParentid(parentid);
				chapter.setPaperid(paperid);
				chapter.setInitpoint(0);
				String parentName = getParentName(parentid);
				return ViewMode.getInstance().putAttr("parentName", parentName).putAttr("entity", chapter)
						.putAttr("pageset", pageset).returnModelAndView("exam/PaperchapterForm");
			}
			case (2): {// 修改
				PaperChapter chapter = paperChapterServiceImpl.getPaperchapterEntity(ids);
				String parentName = getParentName(chapter.getParentid());
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("parentName", parentName)
						.putAttr("entity", chapter).returnModelAndView("exam/PaperchapterForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/PaperchapterForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/PaperchapterForm");
		}
	}

	private String getParentName(String parentId) {
		String parentName = null;
		{
			if (parentId != null) {
				if (parentId.equals("NONE")) {
					parentName = "无";
				} else {
					PaperChapter parent = paperChapterServiceImpl.getPaperchapterEntity(parentId);
					if (parent != null) {
						parentName = parent.getName();
					}
				}
			}
		}
		return parentName;
	}

	/**
	 * 添加考题到试卷章节下
	 * 
	 * @return
	 */
	@RequestMapping("/addSubject")
	@ResponseBody
	public Map<String, Object> addSubjectToPaper(String ids, String chapterId, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				paperChapterServiceImpl.addSubject(id, chapterId, getCurrentUser(session));
			}
			PaperChapter chapter = paperChapterServiceImpl.getPaperchapterEntity(chapterId);
			paperServiceImpl.refreshSubjectNum(chapter.getPaperid());
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

}
