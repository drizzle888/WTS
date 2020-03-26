package com.farm.wcp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.Card;
import com.wts.exam.domain.Room;
import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.MaterialServiceInter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.ExamPopsServiceInter;
import com.wts.exam.service.RoomServiceInter;
import com.wts.exam.service.SubjectAnalysisServiceInter;
import com.wts.exam.service.SubjectServiceInter;

/**
 * 判卷
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/demo")
@Controller
public class demoWebController extends WebUtils {

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/***
	 * 考场判卷首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/PubHome")
	public ModelAndView index(String roomid, HttpServletRequest request, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			return view.returnModelAndView("/demo/index");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

}
