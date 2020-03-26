package com.wts.exam.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.ViewMode;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_TYPE;
import com.farm.doc.server.FarmFileManagerInter.IMG_TYPE;
import com.farm.doc.server.utils.FileDownloadUtils;
import com.farm.parameter.FarmParameterService;
import com.farm.util.latex.LatexUtils;
import com.farm.web.WebUtils;

/**
 * 文件上传下载
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/latex")
@Controller
public class LateXController extends WebUtils {
	private static final Logger log = Logger.getLogger(LateXController.class);
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;

	/**
	 * 公式設置表單
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/inputcom")
	public ModelAndView index(HttpSession session) {
		List<Entry<String, String>> types = FarmParameterService.getInstance().getDictionaryList("LATEX_MODE");
		return ViewMode.getInstance().putAttr("types", types).returnModelAndView("exam/comment/latexInput");
	}

	/**
	 * 验证公式是否可用
	 * 
	 * @return
	 */
	@RequestMapping(value = "/valid")
	@ResponseBody
	public Map<String, Object> valid(String latex, HttpServletRequest request, HttpSession session) {
		int error;
		log.debug("go to ImgUpload");
		String message;
		try {
			latex = URLDecoder.decode(latex, "utf-8");
			LatexUtils.latex2Bytes(latex);
			error = 0;
			message = "";
		} catch (Exception e) {
			error = 1;
			message = e.getMessage();
		}
		return ViewMode.getInstance().putAttr("error", error).putAttr("message", message).returnObjMode();
	}

	/**
	 * 預覽公式
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public void loadimg(String latex, HttpServletRequest request, HttpServletResponse response) {
		FileDownloadUtils.simpleDownloadFile(LatexUtils.latex2Bytes(latex), "科学公式.png", "application/octet-stream",
				response);
	}

	/**
	 * 下載公式圖片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/latexpng.do")
	@ResponseBody
	public Map<String, Object> downLoadLateXpng(String latex, HttpServletRequest request, HttpSession session) {
		int error;
		log.debug("go to ImgUpload");
		String message;
		String url = null;
		String id = null;
		String fileName = null;
		try {
			latex = URLDecoder.decode(latex, "utf-8");
			fileName = URLEncoder.encode("科学公式.png", "utf-8");
			id = farmFileManagerImpl.saveFile(LatexUtils.latex2Bytes(latex), FILE_TYPE.RESOURCE_IMG, "科学公式.png",
					getCurrentUser(session));
			farmFileManagerImpl.submitFile(id, "数学公式");
			error = 0;
			url = farmFileManagerImpl.getImgURL(id, IMG_TYPE.MAX);
			message = "";
		} catch (Exception e) {
			error = 1;
			message = e.getMessage();
		}
		return ViewMode.getInstance().putAttr("error", error).putAttr("url", url).putAttr("message", message)
				.putAttr("id", id).putAttr("fileName", fileName).returnObjMode();
	}
}
