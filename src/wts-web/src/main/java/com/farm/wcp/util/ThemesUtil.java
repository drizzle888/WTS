package com.farm.wcp.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.farm.parameter.FarmParameterService;
import com.farm.parameter.service.impl.XmlConfigFileService;

/**
 * 获得展示页面工具
 * 
 * @author wangdong
 *
 */
public class ThemesUtil {
	private static final Logger log = Logger.getLogger(ThemesUtil.class);

	/**
	 * 前期打算通过文件夹进行样式的隔离，但是后期发现所有页面整体替换成本太高，修改为配置文件的方式进行页面风格控制，固不建议使用此方法
	 * 
	 * @return
	 */
	@Deprecated
	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/**
	 * 浏览器类型
	 */
	public static final List<String> MOBILEAGENTS = new ArrayList<>();

	/**
	 * 风格样式<样式id，<页面id,页面值>>
	 */
	private static final Map<String, Map<String, String>> THEMS_DIC = new HashMap<>();
	/**
	 * 当前风格的id
	 */
	private static String CURRENT_THEMES_PC;
	/**
	 * 当前风格的id
	 */
	private static String CURRENT_THEMES_MOBILE;

	private static Boolean IS_ALWAYSLOAD = false;

	/**
	 * 通过页面id获得页面文件路径
	 * 
	 * @param pageId
	 * @return
	 */
	public static String getThemePage(String pageId) {
		return getThemePage(pageId, null);
	}

	/**
	 * 通过页面id获得页面文件路径
	 * 
	 * @param pageId
	 *            页面id
	 * @param session
	 * @return
	 */
	public static String getThemePage(String pageId, HttpServletRequest request) {
		HttpSession session = request == null ? null : request.getSession();
		// 判断是否每次都加载配置文件（true则立即加载配置文件）
		if (IS_ALWAYSLOAD) {
			loadXmlConf();
		}
		// 风格KEY
		String themes_key = null;
		// 风格页面
		String pagePath = null;
		// 分PC和移动端的配置文件
		if (!isMobile(request)) {
			// PC
			// 2.加载配置文件中的自定义样式
			if (StringUtils.isNotBlank(CURRENT_THEMES_PC) && (!"NONE".equals(CURRENT_THEMES_PC))) {
				themes_key = CURRENT_THEMES_PC;
			}
			// 3.加载session中的样式集
			if (session != null && session.getAttribute("CURRENT_THEMES_PC") != null) {
				//
			}
			// 查找当前样式所配置的风格页面
			Map<String, String> themeMap = THEMS_DIC.get(themes_key);
			if (themeMap != null) {
				pagePath = themeMap.get(pageId);
			}
		} else {
			// Mobile
			// 3.加载配置文件中的自定义样式
			if (StringUtils.isNotBlank(CURRENT_THEMES_MOBILE) && (!"NONE".equals(CURRENT_THEMES_MOBILE))) {
				themes_key = CURRENT_THEMES_MOBILE;
			}
			// 2.加载session中的样式集
			if (session != null && session.getAttribute("CURRENT_THEMES_MOBILE") != null) {
				//themes_key = SysBackType.getThemes();
			}
			// 查找当前样式所配置的风格页面
			Map<String, String> themeMap = THEMS_DIC.get(themes_key);
			if (themeMap != null) {
				pagePath = themeMap.get(pageId);
			}
		}
		// 加载默认---------------------------------------------------------------------
		// 如果当前样式中没有配置过风格页面则读取默认风格样式
		if (pagePath == null) {
			// 使用默认样式的路径
			pagePath = THEMS_DIC.get("baseThemes").get(pageId);
		}
		return pagePath;
	}

	/**
	 * 加载风格配置文件
	 */
	public static void loadXmlConf() {
		log.info("注册页面风格XML配置文件themesConf.xml");
		URL url = XmlConfigFileService.class.getClassLoader().getResource("config/themesConf.xml");
		File file = new File(url.getFile());
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			Elements eles = document.getElementsByTag("currentThemes");
			// 当前使用的风格ID
			{
				CURRENT_THEMES_PC = eles.get(0).getElementsByTag("pc").text();
				CURRENT_THEMES_MOBILE = eles.get(0).getElementsByTag("mobile").text();
			}
			// 是否总是加载配置文件
			IS_ALWAYSLOAD = eles.get(0).attr("alwaysLoad").toUpperCase().equals("TRUE");
			// 加载默认配置baseThemes
			{
				Elements basePages = document.getElementsByTag("baseThemes");
				Map<String, String> pages = new HashMap<>();
				for (Element node : basePages.get(0).getElementsByTag("page")) {
					pages.put(node.attr("id"), node.text());
				}
				THEMS_DIC.put("baseThemes", pages);
			}
			// 加载自定义样式
			{
				Elements themes = document.getElementsByTag("themes");
				for (Element theme : themes) {
					Map<String, String> pages = new HashMap<>();
					for (Element node : theme.getElementsByTag("page")) {
						pages.put(node.attr("id"), node.text());
					}
					THEMS_DIC.put(theme.attr("id"), pages);
				}
			}
			// 加载浏览器UserAgent
			{
				Elements agents = document.getElementsByTag("agentKey");
				MOBILEAGENTS.clear();
				for (Element agent : agents) {
					MOBILEAGENTS.add(agent.text());
				}
			}
		} catch (Exception e) {
			log.error("注册页面风格XML配置文件themesConf.xml" + e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: 判断是否是手机浏览器
	 */
	public static boolean isMobile(HttpServletRequest request) {
		if (request == null) {
			return false;
		}
		boolean isMobile = false;
		List<String> mobileAgents = MOBILEAGENTS;
		if (request.getHeader("User-Agent") != null) {
			String userAgent = request.getHeader("User-Agent");
			for (String mobileAgent : mobileAgents) {
				// request.getHeader("User-Agent")
				if (userAgent.toLowerCase().indexOf(mobileAgent) > 0) {
					isMobile = true;
					log.debug("isMobile--------" + mobileAgent + ":" + userAgent);
					break;
				}
			}
		}
		return isMobile;
	}

	public static void main(String[] args) {
		String userAgent = "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/604.3.5 (KHTML, like Gecko) Version/11.0.1 Safari/604.3.5";
		loadXmlConf();
		boolean isMobile = false;
		for (String mobileAgent : MOBILEAGENTS) {
			// request.getHeader("User-Agent")
			if (userAgent.toLowerCase().indexOf(mobileAgent) > 0) {
				isMobile = true;
				log.debug("isMobile--------" + mobileAgent + ":" + userAgent);
				break;
			}
		}
		if (isMobile) {
			System.out.println("is Mobile");
		} else {
			System.out.println("Not is Mobile");
		}
	}

}
