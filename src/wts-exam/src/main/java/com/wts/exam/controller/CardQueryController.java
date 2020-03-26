package com.wts.exam.controller;

import com.wts.exam.domain.CardHis;
import com.wts.exam.service.CardHisServiceInter;
import com.wts.exam.service.CardServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Organization;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.report.FarmReport;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：答题卡历史记录控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/cardquery")
@Controller
public class CardQueryController extends WebUtils {
	private final static Logger log = Logger.getLogger(CardQueryController.class);
	@Resource
	private CardHisServiceInter cardHisServiceImpl;
	@Resource
	private CardServiceInter cardServiceImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;

	/**
	 * 归档成绩查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/hisQuery")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			{
				// 处理时间查询
				DBRule statTime = query.getAndRemoveRule("STARTTIME");
				DBRule endTime = query.getAndRemoveRule("ENDTIME");
				String startT = null;
				String endT = null;
				if (statTime != null) {
					startT = StringUtils.isNotBlank(statTime.getValue()) ? statTime.getValue() : null;
				}
				if (endTime != null) {
					endT = StringUtils.isNotBlank(endTime.getValue()) ? endTime.getValue() : null;
				}
				if (startT != null) {
					startT = startT.replaceAll("-", "") + "000000";
					query.addRule(new DBRule("A.STARTTIME", startT, ">="));
				}
				if (endT != null) {
					endT = endT.replaceAll("-", "") + "999999";
					query.addRule(new DBRule("A.STARTTIME", endT, "<="));
				}
			}
			{
				DBRule orgid = query.getAndRemoveRule("PARENTID");
				if (orgid != null) {
					Organization org = organizationServiceImpl.getOrganizationEntity(orgid.getValue());
					if (org != null) {
						query.addRule(new DBRule("c.TREECODE", org.getTreecode(), "like-"));
					}
				}
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = cardHisServiceImpl.createHisQuery(query).search();
			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm");
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:完成阅卷,6:发布成绩,7:历史存档", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 当前成绩查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/liveQuery")
	@ResponseBody
	public Map<String, Object> liveQuery(DataQuery query, HttpServletRequest request) {
		try {
			{
				// 处理时间查询
				DBRule statTime = query.getAndRemoveRule("STARTTIME");
				DBRule endTime = query.getAndRemoveRule("ENDTIME");
				String startT = null;
				String endT = null;
				if (statTime != null) {
					startT = StringUtils.isNotBlank(statTime.getValue()) ? statTime.getValue() : null;
				}
				if (endTime != null) {
					endT = StringUtils.isNotBlank(endTime.getValue()) ? endTime.getValue() : null;
				}
				if (startT != null) {
					startT = startT.replaceAll("-", "") + "000000";
					query.addRule(new DBRule("A.STARTTIME", startT, ">="));
				}
				if (endT != null) {
					endT = endT.replaceAll("-", "") + "999999";
					query.addRule(new DBRule("A.STARTTIME", endT, "<="));
				}
			}
			{
				DBRule orgid = query.getAndRemoveRule("PARENTID");
				if (orgid != null) {
					Organization org = organizationServiceImpl.getOrganizationEntity(orgid.getValue());
					if (org != null) {
						query.addRule(new DBRule("c.TREECODE", org.getTreecode(), "like-"));
					}
				}
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = cardHisServiceImpl.createLiveQuery(query).search();
			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm");
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:完成阅卷,6:发布成绩,7:历史存档", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	@RequestMapping("/exportHisExcel")
	public void exportHisExcel(String ruleText, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			DataQuery query = new DataQuery();
			query.setPagesize(5000);
			query.setRuleText(ruleText);
			Map<String, Object> result = queryall(query, request);
			FarmReport.newInstance("hisCard.xls").addParameter("result", result.get("rows")).generateForHttp(response,
					"hisCardReport");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	@RequestMapping("/exportLiveExcel")
	public void exportLiveExcel(String ruleText, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			DataQuery query = new DataQuery();
			query.setPagesize(5000);
			query.setRuleText(ruleText);
			Map<String, Object> result = liveQuery(query, request);
			FarmReport.newInstance("liveCard.xls").addParameter("result", result.get("rows")).generateForHttp(response,
					"liveCardReport");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@RequestMapping("/hislist")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/CardhisResult");
	}

	@RequestMapping("/livelist")
	public ModelAndView liveIndex(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("exam/CardLiveResult");
	}

}
