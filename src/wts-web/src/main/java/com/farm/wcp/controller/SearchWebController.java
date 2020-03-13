package com.farm.wcp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.query.DataQuerys;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;
import com.wts.exam.service.CardHisServiceInter;
import com.wts.exam.service.ExamTypeServiceInter;

/**
 * 查询
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/search")
@Controller
public class SearchWebController extends WebUtils {
	@Resource
	private UsermessageServiceInter usermessageServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private CardHisServiceInter cardHisServiceImpl;
	private static final Logger log = Logger.getLogger(SearchWebController.class);

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/***
	 * 成绩查询
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/pointSearch")
	public ModelAndView index(Integer page, String word, HttpServletRequest request, HttpSession session) {
		try {
			if (page == null) {
				page = 1;
			}
			ViewMode view = ViewMode.getInstance();
			DataQuery query = DataQuery.getInstance();
			query.setCurrentPage(page);
			DataQuerys.wipeVirus(word);
			if (StringUtils.isNotBlank(word)) {
				query.addSqlRule(" and (ROOMNAME like '%" + word + "%' OR PAPERNAME like '%" + word + "%')");
			}
			query = EasyUiUtils.formatGridQuery(request, query);
			//query.addRule(new DBRule("PSTATE", "6", "="));
			query.addRule(new DBRule("USERID", getCurrentUser(session).getId(), "="));
			query.addSqlRule("and ROOMNAME is not null and PAPERNAME is not null");
			DataResult result = cardHisServiceImpl.createUserCardQuery(query).search();
			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm");
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:完成阅卷,6:发布成绩,7:历史存档", "PSTATE");
			return view.putAttr("result", result).putAttr("word", word)
					.returnModelAndView(getThemePath() + "/search/userPoints");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}
}
