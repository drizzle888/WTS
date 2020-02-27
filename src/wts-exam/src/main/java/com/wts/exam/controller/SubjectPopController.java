package com.wts.exam.controller;

import com.wts.exam.domain.SubjectPop;
import com.wts.exam.service.SubjectPopServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;

import java.util.List;
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
 *功能：题库权限控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/subjectpop")
@Controller
public class SubjectPopController extends WebUtils {
	private final static Logger log = Logger.getLogger(SubjectPopController.class);
	@Resource
	private SubjectPopServiceInter subjectPopServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(String functype, String typeids, DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			String inIds = null;
			for (String id : parseIds(typeids)) {
				if (inIds == null) {
					inIds = "'" + id + "'";
				} else {
					inIds = inIds + ",'" + id + "'";
				}
			}
			query.addSqlRule(" and a.TYPEID IN (" + inIds + ")");
			query.addRule(new DBRule("a.FUNTYPE", functype, "="));
			DataResult result = subjectPopServiceImpl.createSubjectpopSimpleQuery(query).search();
			result.runDictionary("1:使用权,2:维护权", "FUNTYPE");
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
	public Map<String, Object> editSubmit(SubjectPop entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = subjectPopServiceImpl.editSubjectpopEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @param userids
	 *            用户id
	 * @param functype
	 *            权限类型
	 * @param typeids
	 *            分类id
	 * @param session
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(String userids, String functype, String typeids, HttpSession session) {
		try {
			List<String> userIds = parseIds(userids);
			List<String> typeIds = parseIds(typeids);
			subjectPopServiceImpl.insertPop(userIds, typeIds, functype, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).returnObjMode();
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
				subjectPopServiceImpl.deleteSubjectpopEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(String functype, String typeids, HttpSession session) {
		return ViewMode.getInstance().putAttr("functype", functype).putAttr("typeids", typeids)
				.returnModelAndView("exam/SubjectPopResult");
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
						.putAttr("entity", subjectPopServiceImpl.getSubjectpopEntity(ids))
						.returnModelAndView("exam/SubjectPopForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("exam/SubjectPopForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", subjectPopServiceImpl.getSubjectpopEntity(ids))
						.returnModelAndView("exam/SubjectPopForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/SubjectPopForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/SubjectPopForm");
		}
	}
}
