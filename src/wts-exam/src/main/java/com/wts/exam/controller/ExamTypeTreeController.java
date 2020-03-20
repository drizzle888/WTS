package com.wts.exam.controller;

import com.wts.exam.service.ExamTypeServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;

import com.farm.web.easyui.EasyUiTreeNode;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考试分类树得查询方法（单独拿出来是因为有时候题库管理或答卷管理时并没有分配分类管理得权限，因此如果放在一起会被权限系统过滤掉）
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/examTypeTree")
@Controller
public class ExamTypeTreeController extends WebUtils {
	private final static Logger log = Logger.getLogger(ExamTypeTreeController.class);
	@Resource
	ExamTypeServiceInter examTypeServiceImpl;

	/**
	 * 业务分类树
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
	 * 跳转
	 * 
	 * @return
	 */
	@RequestMapping("/examTypeTreeView")
	public ModelAndView forSend(String ids, String funtype) {
		return ViewMode.getInstance().putAttr("ids", ids).putAttr("funtype", funtype)
				.returnModelAndView("exam/ExamTypeChooseTreeWin");
	}

}
