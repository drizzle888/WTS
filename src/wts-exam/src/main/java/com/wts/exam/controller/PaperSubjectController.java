package com.wts.exam.controller;

import com.wts.exam.domain.PaperSubject;
import com.wts.exam.domain.ex.TipType;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.PaperSubjectServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.log.WcpLog;

import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：试卷试题控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/papersubject")
@Controller
public class PaperSubjectController extends WebUtils {
	private final static Logger log = Logger.getLogger(PaperSubjectController.class);
	@Resource
	private PaperSubjectServiceInter paperSubjectServiceImpl;
	@Resource
	private PaperServiceInter paperServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, String chapterId, String paperId, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (chapterId != null && !chapterId.equals("NONE")) {
				query.addRule(new DBRule("b.TREECODE", chapterId, "like"));
			}
			query.addRule(new DBRule("a.PAPERID", paperId, "="));
			query.addDefaultSort(new DBSort("a.SORT", "ASC"));
			DataResult result = paperSubjectServiceImpl.createPapersubjectSimpleQuery(query).search();
			result.runDictionary(TipType.getDictionary(), "TIPTYPE");
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
	public Map<String, Object> editSubmit(PaperSubject entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = paperSubjectServiceImpl.editPapersubjectEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/editPoint")
	@ResponseBody
	public Map<String, Object> editPoint(String paperSubjectIds, Integer point, HttpSession session) {
		try {
			for (String id : parseIds(paperSubjectIds)) {
				PaperSubject psub = paperSubjectServiceImpl.getPapersubjectEntity(id);
				WcpLog.info("修改答卷[" + psub.getPaperid() + "]:设置题目得分", getCurrentUser(session).getName(),
						getCurrentUser(session).getId());
				paperServiceImpl.editPoint(psub.getPaperid(), psub.getChapterid(), psub.getSubjectid(), point,
						getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).returnObjMode();
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
	public Map<String, Object> addSubmit(PaperSubject entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = paperSubjectServiceImpl.insertPapersubjectEntity(entity, getCurrentUser(session));
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
			String paperId = null;
			for (String id : parseIds(ids)) {
				paperId = paperSubjectServiceImpl.getPapersubjectEntity(id).getPaperid();
				WcpLog.info("修改答卷[" + paperId + "]:移除题目", getCurrentUser(session).getName(),
						getCurrentUser(session).getId());
				paperSubjectServiceImpl.deletePapersubjectEntity(id, getCurrentUser(session));
			}
			paperServiceImpl.refreshSubjectNum(paperId);
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/PapersubjectResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", paperSubjectServiceImpl.getPapersubjectEntity(ids))
						.returnModelAndView("exam/PapersubjectForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("exam/PapersubjectForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", paperSubjectServiceImpl.getPapersubjectEntity(ids))
						.returnModelAndView("exam/PapersubjectForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/PapersubjectForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/PapersubjectForm");
		}
	}
}
