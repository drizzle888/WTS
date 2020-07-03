package com.wts.exam.controller;

import com.wts.exam.domain.SubjectAnswer;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.TipType;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.SubjectAnswerServiceInter;
import com.wts.exam.service.SubjectVersionServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.doc.util.HtmlUtils;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考题答案控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/subjectanswer")
@Controller
public class SubjecTanswerController extends WebUtils {
	private final static Logger log = Logger.getLogger(SubjecTanswerController.class);
	@Resource
	SubjectAnswerServiceInter subjectAnswerServiceImpl;
	@Resource
	SubjectVersionServiceInter subjectVersionServiceImpl;
	@Resource
	SubjectServiceInter subjectServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, String versionid, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("VERSIONID", versionid, "="));
			query.addDefaultSort(new DBSort("SORT", "ASC"));
			query.setPagesize(100);
			DataResult result = subjectAnswerServiceImpl.createSubjectanswerSimpleQuery(query).search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					if (StringUtils.isBlank((String) row.get("ANSWER"))
							&& StringUtils.isNotBlank((String) row.get("ANSWERNOTE"))) {
						row.put("ANSWER", HtmlUtils.HtmlRemoveTagAndMarkImg((String) row.get("ANSWERNOTE")));
					}
					row.put("ANSWER",StringEscapeUtils.escapeHtml4((String)row.get("ANSWER")));
				}
			});
			result.runDictionary("1:正确答案,0:错误答案", "RIGHTANSWER");
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
	public Map<String, Object> editSubmit(SubjectAnswer entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = subjectAnswerServiceImpl.editSubjectanswerEntity(entity, getCurrentUser(session));
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
	public Map<String, Object> addSubmit(SubjectAnswer entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = subjectAnswerServiceImpl.insertSubjectanswerEntity(entity, getCurrentUser(session));
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
				subjectAnswerServiceImpl.deleteSubjectanswerEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 设置唯一正确答案
	 * 
	 * @return
	 */
	@RequestMapping("/onlyRight")
	@ResponseBody
	public Map<String, Object> onlyRight(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				subjectAnswerServiceImpl.setOnlyRight(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 设置為正確或錯誤答案
	 * 
	 * @return
	 */
	@RequestMapping("/rightVal")
	@ResponseBody
	public Map<String, Object> rightVal(String flag, String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				subjectAnswerServiceImpl.setRightAnswer(id, flag.equals("1"), getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/SubjectanswerResult");
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids, String versionid) {
		try {
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				SubjectAnswer answer = subjectAnswerServiceImpl.getSubjectanswerEntity(ids);
				SubjectVersion version = subjectVersionServiceImpl.getSubjectversionEntity(answer.getVersionid());
				TipType tipType = TipType.getTipType(version.getTiptype());
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", answer)
						.returnModelAndView(tipType.getAnswerPage());
			}
			case (1): {// 新增
				SubjectVersion version = subjectVersionServiceImpl.getSubjectversionEntity(versionid);
				TipType tipType = TipType.getTipType(version.getTiptype());
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("versionid", versionid)
						.returnModelAndView(tipType.getAnswerPage());
			}
			case (2): {// 修改
				SubjectAnswer answer = subjectAnswerServiceImpl.getSubjectanswerEntity(ids);
				SubjectVersion version = subjectVersionServiceImpl.getSubjectversionEntity(answer.getVersionid());
				TipType tipType = TipType.getTipType(version.getTiptype());
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", answer)
						.returnModelAndView(tipType.getAnswerPage());
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/SubjectViews/VacancyAnswer");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/SubjectanswerForm");
		}
	}
}
