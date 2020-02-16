package com.farm.wcp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.ViewMode;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;

@RequestMapping("/toolweb")
@Controller
public class ToolWebController extends WebUtils {
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(ToolWebController.class);

	@RequestMapping("/sysbackup")
	public ModelAndView home(HttpServletRequest request, HttpSession session) {
		// 取附件地址
		String filepath = FarmParameterService.getInstance().getParameter("config.doc.dir");
		String database = FarmParameterService.getInstance().getParameter("jdbc.url");
		database = database.substring(database.indexOf("//") >= 0 ? database.indexOf("//") + "//".length() : 0,
				database.indexOf("?") >= 0 ? database.indexOf("?") : database.length());
		// 取数据库配置文件地址
		String jdbcpath = Thread.currentThread().getContextClassLoader().getResource("/").getPath()+"jdbc.properties";
		return ViewMode.getInstance().putAttr("filepath", filepath).putAttr("jdbcpath", jdbcpath)
				.putAttr("database", database).returnModelAndView("toolpage/sysBackupGuide");
	}

}
