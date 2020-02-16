package com.wts.exam.controller;

import com.wts.exam.domain.ExamType;
import com.wts.exam.service.ExamTypeServiceInter;
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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考试分类控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/examtype")
@Controller
public class ExamTypeController extends WebUtils {
	private final static Logger log = Logger.getLogger(ExamTypeController.class);
	@Resource
	ExamTypeServiceInter examTypeServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (query.getQueryRule().size() == 0) {
				query.addRule(new DBRule("PARENTID", "NONE", "="));
			}
			DataResult result = examTypeServiceImpl.createExamtypeSimpleQuery(query).search();
			result.runDictionary("1:可用,0:禁用", "STATE");
			result.runDictionary("1:<span class=\"feild_green\">所有人</span>,2:<span class=\"feild_red\">指定人</span>,3:未授权",
					"MNGPOP");
			result.runDictionary("1:<span class=\"feild_green\">所有人</span>,2:<span class=\"feild_red\">指定人</span>,3:未授权",
					"ADJUDGEPOP");
			result.runDictionary("1:<span class=\"feild_green\">所有人</span>,2:<span class=\"feild_red\">指定人</span>,3:未授权",
					"QUERYPOP");
			result.runDictionary("1:<span class=\"feild_green\">所有人</span>,2:<span class=\"feild_red\">指定人</span>,3:未授权",
					"SUPERPOP");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 组织机构节点
	 */
	@RequestMapping("/examtypeTree")
	@ResponseBody
	public Object examtypeTree(String id, String funtype, HttpSession session) {
		try {
			if (id == null) {
				// 如果是未传入id，就是根节点，就构造一个虚拟的上级节点
				id = "NONE";
				List<EasyUiTreeNode> list = new ArrayList<>();
				EasyUiTreeNode nodes = new EasyUiTreeNode("NONE", "业务分类", "open", "icon-customers");
				nodes.setChildren(EasyUiTreeNode.formatAsyncAjaxTree(
						EasyUiTreeNode.queryTreeNodeOne(id, "SORT", "WTS_EXAM_TYPE", "ID", "PARENTID", "NAME", "CTIME")
								.getResultList(),
						EasyUiTreeNode.queryTreeNodeTow(id, "SORT", "WTS_EXAM_TYPE", "ID", "PARENTID", "NAME", "CTIME")
								.getResultList(),
						"PARENTID", "ID", "NAME", "CTIME"));
				list.add(nodes);
				list = examTypeServiceImpl.RunPopFilter(list, funtype, getCurrentUser(session));
				return list;
			} else {
				List<EasyUiTreeNode> list = new ArrayList<>();
				list = EasyUiTreeNode.formatAsyncAjaxTree(
						EasyUiTreeNode.queryTreeNodeOne(id, "SORT", "WTS_EXAM_TYPE", "ID", "PARENTID", "NAME", "CTIME")
								.getResultList(),
						EasyUiTreeNode.queryTreeNodeTow(id, "SORT", "WTS_EXAM_TYPE", "ID", "PARENTID", "NAME", "CTIME")
								.getResultList(),
						"PARENTID", "ID", "NAME", "CTIME");
				list = examTypeServiceImpl.RunPopFilter(list, funtype, getCurrentUser(session));
				return list;
			}

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
	public Map<String, Object> editSubmit(ExamType entity, HttpSession session) {
		try {
			entity = examTypeServiceImpl.editExamtypeEntity(entity, getCurrentUser(session));
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
	public Map<String, Object> addSubmit(ExamType entity, HttpSession session) {
		try {
			entity = examTypeServiceImpl.insertExamtypeEntity(entity, getCurrentUser(session));
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
				examTypeServiceImpl.deleteExamtypeEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/ExamtypeResult");
	}

	/**
	 * 跳转
	 * 
	 * @return
	 */
	@RequestMapping("/examTypeTreeView")
	public ModelAndView forSend(String ids,String funtype) {
		return ViewMode.getInstance().putAttr("ids", ids).putAttr("funtype", funtype).returnModelAndView("exam/ExamTypeChooseTreeWin");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids, String parentId) {
		try {
			ExamType parent = null;
			if (StringUtils.isNotBlank(parentId)) {
				parent = examTypeServiceImpl.getExamtypeEntity(parentId);
			}
			switch (pageset.getOperateType()) {
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("parent", parent)
						.returnModelAndView("exam/ExamtypeForm");
			}
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", examTypeServiceImpl.getExamtypeEntity(ids))
						.returnModelAndView("exam/ExamtypeForm");
			}
			case (2): {// 修改
				ExamType type = examTypeServiceImpl.getExamtypeEntity(ids);
				parent = examTypeServiceImpl.getExamtypeEntity(type.getParentid());
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", type)
						.putAttr("parent", parent).returnModelAndView("exam/ExamtypeForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/ExamtypeForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/ExamtypeForm");
		}
	}

	/**
	 * 移动节点
	 * 
	 * @return
	 */
	@RequestMapping("/moveNodeSubmit")
	@ResponseBody
	public Object moveTreeNodeSubmit(String ids, String id, HttpSession session) {
		try {
			examTypeServiceImpl.moveTreeNode(ids, id, getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
