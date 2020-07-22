package com.wts.exam.controller;

import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.MaterialServiceInter;
import com.wts.exam.service.SubjectAnalysisServiceInter;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.SubjectTypeServiceInter;
import com.wts.exam.service.SubjectUserOwnServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.log.WcpLog;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.doc.util.HtmlUtils;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考题控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/subject")
@Controller
public class SubjectController extends WebUtils {
	private final static Logger log = Logger.getLogger(SubjectController.class);
	@Resource
	private SubjectServiceInter subjectServiceImpl;
	@Resource
	private CardServiceInter cardServiceImpl;
	@Resource
	private SubjectTypeServiceInter subjectTypeServiceImpl;
	@Resource
	private MaterialServiceInter materialServiceImpl;
	@Resource
	private SubjectAnalysisServiceInter SubjectAnalysisServiceImpl;
	@Resource
	private SubjectUserOwnServiceInter subjectUserOwnServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, String funtype, HttpServletRequest request,
			HttpSession session) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			if (StringUtils.isNotBlank(query.getRule("C.TREECODE")) && query.getRule("C.TREECODE").equals("NONE")) {
				query.getAndRemoveRule("C.TREECODE");
			}
			{
				// 过滤权限
				String typeids_Rule = DataQuerys.parseSqlValues(
						subjectTypeServiceImpl.getUserPopTypeids(getCurrentUser(session).getId(), funtype));
				if (typeids_Rule != null) {
					query.addSqlRule(" and c.id in (" + typeids_Rule + ")");
				}
			}
			query.addDefaultSort(new DBSort("b.ctime", "DESC"));
			DataResult result = subjectServiceImpl.createSubjectSimpleQuery(query).search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					if (StringUtils.isBlank((String) row.get("TIPSTR"))
							&& StringUtils.isNotBlank((String) row.get("TIPNOTE"))) {
						// 如果题目为空就用描述填充题目
						row.put("TIPSTR", "[无题目:显示描述]" + HtmlUtils.HtmlRemoveTagAndMarkImg((String) row.get("TIPNOTE")));
					}
					row.put("TIPSTR",StringEscapeUtils.escapeHtml4((String)row.get("TIPSTR")));
				}
			});
			result.runDictionary(TipType.getDictionary(), "TIPTYPE");
			result.runDictionary("1:已设置,0:未设置,3:未知", "ANSWERED");
			result.runDictionary("0:无", "ANALYSISNUM");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 绑定一个材料到问题
	 * 
	 * @return
	 */
	@RequestMapping("/bindMaterial")
	@ResponseBody
	public Map<String, Object> bindMaterial(String subjectIds, String materialId, HttpSession session) {
		try {
			for (String id : parseIds(subjectIds)) {
				WcpLog.info("修改题目["+id+"]:绑定材料",getCurrentUser(session).getName(),getCurrentUser(session).getId());
				subjectServiceImpl.bindMaterial(id, materialId, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/clearMaterial")
	@ResponseBody
	public Map<String, Object> clearMaterial(String subjectIds, HttpSession session) {
		try {
			for (String id : parseIds(subjectIds)) {
				WcpLog.info("修改题目["+id+"]:清空材料绑定",getCurrentUser(session).getName(),getCurrentUser(session).getId());
				subjectServiceImpl.clearMaterial(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).returnObjMode();
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
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(SubjectVersion entity, String tipanalysis, HttpSession session) {
		try {
			WcpLog.info("修改题目["+entity.getId()+"]:表单提交",getCurrentUser(session).getName(),getCurrentUser(session).getId());
			entity = subjectServiceImpl.editSubjectEntity(entity, tipanalysis, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 计算题目分数
	 * 
	 * @return
	 */
	@RequestMapping("/countPoint")
	@ResponseBody
	public Map<String, Object> countPoint(String jsons, HttpSession session) {
		try {
			SubjectUnit unit = subjectServiceImpl.parseSubjectJsonVal(jsons);
			int pointWeight = unit == null ? 0 : cardServiceImpl.countSubjectPoint(unit);
			// if (getCurrentUser(session) != null && unit != null &&
			// unit.getVersion() != null) {
			// // 2.把错题加入错题集合// 3.用户答题历史存入，答题历史记录
			// subjectUserOwnServiceImpl.addFinishSubject(unit.getVersion().getSubjectid(),
			// pointWeight == 100,
			// getCurrentUser(session));
			// }
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("point", pointWeight).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 批量提交题
	 * 
	 * @return
	 */
	@RequestMapping("/addTextSubjects")
	@ResponseBody
	public Map<String, Object> addTextSubjects(String typeid, String texts, HttpSession session) {
		try {
			List<SubjectUnit> lists = subjectServiceImpl.addTextSubjects(typeid, texts, getCurrentUser(session));
			for (SubjectUnit unit : lists) {
				WcpLog.info("创建题目["+unit.getSubject().getId()+"]:批量创建",getCurrentUser(session).getName(),getCurrentUser(session).getId());
				subjectServiceImpl.refrashAnalysisnum(unit.getSubject().getId());
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("lists", lists).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
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
				WcpLog.info("刪除题目["+id+"]:表单刪除",getCurrentUser(session).getName(),getCurrentUser(session).getId());
				if (!subjectTypeServiceImpl.isHavePop(subjectServiceImpl.getSubjectEntity(id).getTypeid(), "2",
						getCurrentUser(session).getId())) {
					throw new RuntimeException("当前用户无该分类题库编辑权限!");
				}
				subjectServiceImpl.deleteSubjectEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 清理作废的题
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/clearSubject")
	@ResponseBody
	public Map<String, Object> clearSubject(HttpSession session) {
		try {
			int num = subjectServiceImpl.clearSubject(getCurrentUser(session));
			return ViewMode.getInstance().putAttr("num", num).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 更新题的答案状态
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateAnswered")
	@ResponseBody
	public Map<String, Object> updateAnswered(String ids, HttpSession session) {
		try {
			for (String subjectId : parseIds(ids)) {
				subjectServiceImpl.updateAnswered(subjectId);
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/SubjectResult");
	}

	@RequestMapping("/addSubjects")
	public ModelAndView addSubjects(String typeid, HttpSession session) {
		RequestMode pageset = new RequestMode(OperateType.ADD);
		return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("typeid", typeid)
				.returnModelAndView("exam/SubjectsForm");
	}

	@RequestMapping("/chooselist")
	public ModelAndView chooselist(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/SubjectChooseResult");
	}

	/**
	 * 试题预览页面
	 *
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView viewLoad(String versionid, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		SubjectUnit subjectUnit = subjectServiceImpl.getSubjectUnit(versionid);
		return view.putAttr("subjectu", subjectUnit).returnModelAndView(subjectUnit.getTipType().getVeiwPage());
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String typeid, String ids, String tiptype, HttpSession session) {
		try {
			SubjectUnit subjectUnit = null;
			String versionId = null;
			if (StringUtils.isNotBlank(ids)) {
				versionId = subjectServiceImpl.getSubjectEntity(ids).getVersionid();
			}
			switch (pageset.getOperateType()) {
			case (0): {// 查看
				ViewMode view = ViewMode.getInstance();
				subjectUnit = subjectServiceImpl.getSubjectUnit(versionId);
				if (StringUtils.isNotBlank(subjectUnit.getSubject().getMaterialid())) {
					// 如有有引用材料的话f
					view.putAttr("material",
							materialServiceImpl.getMaterialEntity(subjectUnit.getSubject().getMaterialid()));
				}
				if (subjectUnit.getSubject().getAnalysisnum() > 0) {
					// 如有解析的話
					view.putAttr("analysis", (List<SubjectAnalysis>) SubjectAnalysisServiceImpl
							.getSubjectAnalysies(subjectUnit.getSubject().getId()));
				}
				return view.putAttr("pageset", pageset).putAttr("subjectu", subjectUnit)
						.returnModelAndView("exam/SubjectView");
				// .returnModelAndView(subjectUnit.getTipType().getVeiwPage());
			}
			case (1): {// 新增
				// 1使用权、2维护权
				if (!subjectTypeServiceImpl.isHavePop(typeid, "2", getCurrentUser(session).getId())) {
					throw new RuntimeException("当前用户无该分类题库编辑权限!");
				}
				TipType tip = TipType.getTipType(tiptype);
				subjectUnit = subjectServiceImpl.initSubjectUnit(tip, typeid, getCurrentUser(session));
				WcpLog.info("创建题目["+subjectUnit.getSubject().getId()+"]:表单临时创建",getCurrentUser(session).getName(),getCurrentUser(session).getId());
				break;
			}
			case (2): {// 修改
				subjectUnit = subjectServiceImpl.getSubjectUnit(versionId);
				// 1使用权、2维护权
				if (!subjectTypeServiceImpl.isHavePop(subjectUnit.getSubjectType().getId(), "2",
						getCurrentUser(session).getId())) {
					throw new RuntimeException("当前用户无该分类题库编辑权限!");
				}
				break;
			}
			default:
				break;
			}
			return ViewMode.getInstance().putAttr("operateType", "2").putAttr("subjectu", subjectUnit)
					.returnModelAndView(subjectUnit.getTipType().getSubjectPage());
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("commons/InnerError");
		}
	}

	/**
	 * 移动分类
	 * 
	 * @return
	 */
	@RequestMapping("/subjectTypeSetting")
	@ResponseBody
	public Map<String, Object> examtypeSetting(String ids, String typeId, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				WcpLog.info("修改题目["+id+"]:移动分类",getCurrentUser(session).getName(),getCurrentUser(session).getId());
				subjectServiceImpl.subjectTypeSetting(id, typeId, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
