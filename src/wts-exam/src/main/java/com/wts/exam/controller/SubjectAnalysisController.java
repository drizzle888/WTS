package com.wts.exam.controller;

import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.service.SubjectAnalysisServiceInter;
import com.wts.exam.service.SubjectServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
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
 *功能：试题解析控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/subjectanalysis")
@Controller
public class SubjectAnalysisController extends WebUtils {
	private final static Logger log = Logger.getLogger(SubjectAnalysisController.class);
	@Resource
	private SubjectAnalysisServiceInter SubjectAnalysisServiceImpl;
	@Resource
	private SubjectServiceInter subjectServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(String subjectid, DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("subjectid", subjectid, "="));
			DataResult result = SubjectAnalysisServiceImpl.createSubjectAnalysisSimpleQuery(query).search();
			result.runDictionary("1:可用,0:禁用", "PSTATE");
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
	public Map<String, Object> editSubmit(SubjectAnalysis entity, HttpSession session) {
		try {
			entity = SubjectAnalysisServiceImpl.editSubjectAnalysisEntity(entity, getCurrentUser(session));
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
	public Map<String, Object> addSubmit(SubjectAnalysis entity, HttpSession session) {
		try {
			entity = SubjectAnalysisServiceImpl.insertSubjectAnalysisEntity(entity, getCurrentUser(session));
			subjectServiceImpl.refrashAnalysisnum(entity.getSubjectid());
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
				SubjectAnalysisServiceImpl.deleteSubjectAnalysisEntity(id, getCurrentUser(session));
				subjectServiceImpl
						.refrashAnalysisnum(SubjectAnalysisServiceImpl.getSubjectAnalysisEntity(id).getSubjectid());
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(String subjectid, HttpSession session) {
		return ViewMode.getInstance().putAttr("subjectid", subjectid).returnModelAndView("exam/SubjectanalysisResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids, String subjectid) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", SubjectAnalysisServiceImpl.getSubjectAnalysisEntity(ids))
						.returnModelAndView("exam/SubjectanalysisForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("subjectid", subjectid)
						.returnModelAndView("exam/SubjectanalysisForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", SubjectAnalysisServiceImpl.getSubjectAnalysisEntity(ids))
						.returnModelAndView("exam/SubjectanalysisForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/SubjectanalysisForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e)
					.returnModelAndView("exam/SubjectanalysisForm");
		}
	}
}