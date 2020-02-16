package com.farm.wcp.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.farm.core.page.ViewMode;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.RoomServiceInter;

/**
 * 考试
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/utils")
@Controller
public class UtilsWebController extends WebUtils {
	@Resource
	private RoomServiceInter roomServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	private static final Logger log = Logger.getLogger(UtilsWebController.class);

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/**
	 * 判卷人强制收卷
	 * 
	 * @return
	 */
	@RequestMapping("/formatHtmlTag")
	@ResponseBody
	public Map<String, Object> formatHtmlTag(String html, HttpSession session) {
		try {
			ViewMode page = ViewMode.getInstance();
			// 处理多媒体嵌入式标签//增加图片的class和id和data-original
			Document doc = Jsoup.parse(html);
			// 处理多媒体标签
			doc = initMediaTag(doc);
			// 清理html标签
			doc = initHtmlTag(doc);
			// 处理代码高亮
			doc = initPreTag(doc);
			// 处理表格
			doc = initTable(doc);
			// 如果是链接分享，就开始替换url指向链接分享的地址
			Elements body = doc.getElementsByTag("body");
			if (body.size() > 0) {
				page.putAttr("html", body.html());
			} else {
				page.putAttr("html", doc.html());
			}
			return page.returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 将一个表格对象变为bootstrap响应式表格
	 * 
	 * @param doc
	 * @return
	 */
	private Document initTable(Document doc) {
		Elements tables = doc.getElementsByTag("table");
		for (Element table : tables) {
			table = table.addClass("table");
			// 1.创建div包裹table
			String div = "<div class='table-responsive' style='overflow:auto;'>" + table.outerHtml() + "</div>";
			// 2.将table移动到div下
			table.after(div);
			table.remove();
		}
		return doc;
	}

	/**
	 * 删除所有的class 属性
	 * 
	 * @param doc
	 * @return
	 */
	private Document initHtmlTag(Document doc) {
		for (Element node : doc.getElementsByTag("div")) {
			String classNmae = node.className();
			if (StringUtils.isNotBlank(classNmae) && (classNmae.indexOf("-wcp-") <= 0)) {
				node.removeAttr("class");
			}
		}
		return doc;
	}

	/**
	 * 寻找多媒体,转义多媒体标签
	 * 
	 * @param doc
	 * @return
	 */
	private Document initMediaTag(Document doc) {
		for (Element node : doc.getElementsByTag("embed")) {
			String urlStr = node.attr("src");
			String id = FarmDocFiles.getFileIdFromImgUrl(urlStr);
			FarmDocfile file = farmFileManagerImpl.getFile(id);
			String exname = null;
			if (file != null) {
				exname = file.getExname().toUpperCase();

			}
			if (exname == null && (urlStr.toUpperCase().indexOf(".MP4") > 0)) {
				exname = "MP4";
			}
			if (exname == null && (urlStr.toUpperCase().indexOf(".MP3") > 0)) {
				exname = "MP3";
			}
			if (exname == null) {
				// 没有找到附件，则原样输出
				return doc;
			}
			if (exname.equals("MP4")) {
				// <video id="documentViewer" src="VIDEO_SWF_PATH"
				// width="100%" controls preload></video>
				node.tagName("video");
				node.attr("controls", "true");
				node.attr("preload", "true");
				node.removeAttr("type");
				if ("true".equals(node.attr("autostart"))) {
					node.attr("autoplay", "true");
				}
				// node.attr("autoplay", "autoplay");
				node.attr("class", "img-responsive");//
				node.attr("src", "actionImg/PubPlayMedia.do?id=" + id);
			}
			if (exname.equals("MP3")) {
				// <audio src="AUDIO_SWF_PATH" width="100%" controls
				// preload></audio>
				node.tagName("audio");
				node.attr("controls", "true");
				node.attr("preload", "true");
				node.removeAttr("type");
				if ("true".equals(node.attr("autostart"))) {
					node.attr("autoplay", "true");
				}
				// node.attr("autoplay", "autoplay");
				node.attr("class", "img-responsive");
				node.attr("src", "actionImg/PubPlayMedia.do?id=" + id);
			}
		}
		return doc;
	}

	/**
	 * 处理代码高亮
	 * 
	 * @param doc
	 * @return
	 */
	private Document initPreTag(Document doc) {
		for (Element node : doc.getElementsByTag("pre")) {
			String classNmae = node.className();
			if (!node.hasAttr("class") || classNmae == null || (classNmae.toUpperCase().indexOf("TOOLBAR") <= 0)) {
				node.addClass("brush:other;toolbar:false");
			}
		}
		return doc;
	}
}
