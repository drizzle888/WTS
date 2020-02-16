package com.farm.wcp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

@RequestMapping("/utils")
@Controller
public class UtilWebController extends WebUtils {
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(UtilWebController.class);

	@RequestMapping("/editor")
	public ModelAndView home(HttpServletRequest request, HttpSession session) {
		// 取附件地址
		return ViewMode.getInstance().returnModelAndView("exam/comment/KindEditor");
	}

}
