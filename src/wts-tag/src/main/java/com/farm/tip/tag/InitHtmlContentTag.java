package com.farm.tip.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.util.spring.BeanFactory;

public class InitHtmlContentTag extends TagSupport {
	private String html;
	private String isFormatHtml;
	private String isOnlyBodyTag;
	private String isShare;

	/**
	 * 是否启用bootstrap响应式表格
	 */
	private String isResponsiveTable;

	private final static FarmFileManagerInter aloneIMP = (FarmFileManagerInter) BeanFactory
			.getBean("farmFileManagerImpl");
	private static final long serialVersionUID = 1895493540131375513L;
	private static final Logger log = Logger.getLogger(InitHtmlContentTag.class);

	@Override
	public int doEndTag() throws JspException {
		if (StringUtils.isBlank(isFormatHtml)) {
			isFormatHtml = "true";
		}
		if (StringUtils.isBlank(isOnlyBodyTag)) {
			isOnlyBodyTag = "true";
		}
		if (StringUtils.isBlank(isResponsiveTable)) {
			isResponsiveTable = "true";
		}
		if (StringUtils.isBlank(isShare)) {
			isShare = "false";
		}
		JspWriter jspw = this.pageContext.getOut();
		// 处理多媒体嵌入式标签//增加图片的class和id和data-original
		try {
			Document doc = Jsoup.parse(html);
			if (isFormatHtml.toLowerCase().equals("true")) {
				// 处理多媒体标签
				doc = initMediaTag(doc);
				// 清理html标签
				doc = initHtmlTag(doc);
				// 处理代码高亮
				doc = initPreTag(doc);
			}

			if (isResponsiveTable.toLowerCase().equals("true")) {
				// 处理表格
				doc = initTable(doc);
			}

			if (isShare.toLowerCase().equals("true")) {
				// 如果是链接分享，就开始替换url指向链接分享的地址
				doc = initShareUrl(doc);
			}
			if (isOnlyBodyTag.toLowerCase().equals("true")) {
				Elements body = doc.getElementsByTag("body");
				if (body.size() > 0) {
					jspw.println(body.html());
				} else {
					jspw.println(doc.html());
				}
			} else {
				jspw.println(doc.html());
			}
		} catch (IOException e) {
			log.error(e + e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 如果是分享链接，就开始替换图片/附件/多媒体资源地址
	 * 
	 * @param doc
	 * @return
	 */
	private Document initShareUrl(Document doc) {
		for (Element node : doc.getElementsByTag("video")) {
			String urlStr = node.attr("src");
			node.attr("src", urlStr.replace("actionImg", "webshare/open"));
		}
		for (Element node : doc.getElementsByTag("audio")) {
			String urlStr = node.attr("src");
			node.attr("src", urlStr.replace("actionImg", "webshare/open"));
		}
		for (Element node : doc.getElementsByClass("ke-wcp-file")) {
			Elements links = node.getElementsByTag("a");
			if (links.size() == 2) {
				links.get(0).attr("href", links.get(1).attr("href").replace("actionImg", "webshare/open"));
			}
		}
		return doc;
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
			FarmDocfile file = aloneIMP.getFile(id);
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
				String src = "actionImg/PubPlayMedia.do?id=" + id;
				String audit_html = "<audio controls>";
				audit_html = audit_html + "<source src=\"" + src + "\" type=\"audio/mpeg\">";
				audit_html = audit_html + "您的浏览器不支持 audio 元素。";
				audit_html = audit_html + "</audio>";
				node.after(audit_html);
				node.remove();
			}
		}
		return doc;
	}

	@Override
	public int doStartTag() throws JspException {

		return 0;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getIsFormatHtml() {
		return isFormatHtml;
	}

	public void setIsFormatHtml(String isFormatHtml) {
		this.isFormatHtml = isFormatHtml;
	}

	public String getIsOnlyBodyTag() {
		return isOnlyBodyTag;
	}

	public void setIsOnlyBodyTag(String isOnlyBodyTag) {
		this.isOnlyBodyTag = isOnlyBodyTag;
	}

	public String getIsResponsiveTable() {
		return isResponsiveTable;
	}

	public void setIsResponsiveTable(String isResponsiveTable) {
		this.isResponsiveTable = isResponsiveTable;
	}

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

}
