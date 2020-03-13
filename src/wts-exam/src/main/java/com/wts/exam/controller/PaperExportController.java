package com.wts.exam.controller;

import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.service.ExamPopsServiceInter;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.PaperServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import com.farm.web.log.WcpLog;

import java.io.File;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;

import com.farm.core.time.TimeTool;
import com.farm.doc.server.utils.FileDownloadUtils;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;

/* *
 *功能：考卷导出word
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/paperExport")
@Controller
public class PaperExportController extends WebUtils {
	private final static Logger log = Logger.getLogger(PaperExportController.class);
	@Resource
	private PaperServiceInter paperServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private ExamPopsServiceInter examPopsServiceImpl;

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/**
	 * 下载word答卷
	 * 
	 * @return
	 */
	@RequestMapping("/exportWord")
	public void loadimg(String paperid, String roomid, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		PaperUnit paper = paperServiceImpl.getPaperUnit(paperid);
		if (!examPopsServiceImpl.isJudger(roomid, getCurrentUser(session))) {
			throw new RuntimeException("当前用户无此答题室判卷权限!");
		}
		WcpLog.info("导出答卷" + paperid + "/" + paper.getInfo().getName(), getCurrentUser(session).getName(),
				getCurrentUser(session).getId());
		log.info(getCurrentUser(session).getLoginname() + "/" + getCurrentUser(session).getName() + "导出答卷" + paperid);
		File file = paperServiceImpl.exprotWord(paper, getCurrentUser(session));
		FileDownloadUtils.simpleDownloadFile(file, "paper" + TimeTool.getTimeDate12() + ".docx",
				"application/octet-stream", response);
	}

}
