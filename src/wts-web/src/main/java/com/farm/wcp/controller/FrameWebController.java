package com.farm.wcp.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiTreeNode;
import com.farm.web.easyui.EasyUiUtils;

@RequestMapping("/frame")
@Controller
public class FrameWebController extends WebUtils {
	private final static Logger log = Logger.getLogger(FrameWebController.class);
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpSession session) {
		return ViewMode.getInstance().putAttr("menus", getCurrentUserMenus(session)).returnModelAndView("frame/frame");
	}

	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request, HttpSession session) {
		Map<String, Integer> map = null;
		map = new HashMap<>();
		return ViewMode.getInstance().putAttr("STAT", map).returnModelAndView("frame/home");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/service")
	@ResponseBody
	public List<EasyUiTreeNode> allUrl(DataQuery query, HttpServletRequest request) {
		log.info("正式系统请 关闭该服务");
		ServletContext servletContext = request.getSession().getServletContext();
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 获取所有的RequestMapping
		Map<String, Object> allRequestMappings = appContext.getBeansWithAnnotation(Controller.class);
		int n = 1;
		for (Object obj : allRequestMappings.values()) {
			RequestMapping classRequest = obj.getClass().getAnnotation(RequestMapping.class);
			if (classRequest != null) {
				// 封装父节点
				Map<String, Object> superNode = new HashMap<String, Object>();
				int m = ++n;
				superNode.put("SID", n);
				superNode.put("PID", "none");
				if (classRequest.value() == null || classRequest.value().length == 0) {
					superNode.put("NA", "NoGroup");
				} else {
					superNode.put("NA", classRequest.value()[0]);
				}
				list.add(superNode);
				for (Method method : obj.getClass().getMethods()) {
					RequestMapping methodRequest = method.getAnnotation(RequestMapping.class);
					if (methodRequest != null) {
						// 封装子节点
						Map<String, Object> childeNode = new HashMap<String, Object>();
						n++;
						childeNode.put("SID", n);
						childeNode.put("PID", m);

						if (classRequest.value() == null || classRequest.value().length == 0) {
							childeNode.put("NA", methodRequest.value()[0].replaceAll("/", ""));
						} else {
							childeNode.put("NA",
									classRequest.value()[0].replaceAll("/", "") + methodRequest.value()[0]);
						}

						list.add(childeNode);
					}
				}
			}
		}
		return (List<EasyUiTreeNode>) ViewMode.returnListObjMode(EasyUiUtils.formatAjaxTree(list, "PID", "SID", "NA"));
	}
}
