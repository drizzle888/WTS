package com.farm.parameter.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.parameter.FarmParameterService;
import com.farm.parameter.domain.AloneParameter;
import com.farm.parameter.service.ParameterServiceInter;
import com.farm.parameter.service.impl.ConstantVarService;
import com.farm.parameter.service.impl.PropertiesFileService;
import com.farm.parameter.service.impl.XmlConfigFileService;
import com.farm.parameter.util.InfoUtil;
import com.farm.util.spring.HibernateSessionFactory;
import com.farm.util.web.WaterCodeUtils;
import com.farm.core.auth.util.DesUtil;
import com.farm.core.config.ReadKey;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.web.WebUtils;
import com.farm.web.constant.FarmConstant;
import com.farm.web.easyui.EasyUiUtils;

/**
 * 系统参数Action
 * 
 * @author zhang_hc
 * @time 2012-8-31 上午11:47:25
 * @author wangdong
 * @time 2015-7-03 上午10:19:25
 */
@RequestMapping("/parameter")
@Controller
public class ParameterController extends WebUtils {

	private static final Logger log = Logger.getLogger(ParameterController.class);

	@Resource
	ParameterServiceInter parameterServiceImpl;

	/**
	 * 查询结果集合
	 *
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			query = DataQuery.init(//
					query, //
					"Alone_Parameter a", //
					"id,domain,name,pkey,pvalue,vtype,comments");

			query.addSort(new DBSort("a.utime", "desc"));// 按最后修改事件排序
			DataResult result = query.search();
			// 状态转义
			HashMap<String, String> transMap = new HashMap<String, String>();
			transMap.put("null", "");
			result.runDictionary(transMap, "COMMENTS");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 进入参数定义界面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("parameter/pAloneParameterLayout");
	}

	/**
	 * 进入参数配置界面
	 * 
	 * @return
	 */
	@RequestMapping("/editlist")
	public ModelAndView showPara(HttpSession session) {
		ViewMode page = ViewMode.getInstance();
		{
			// ------------------------------缓存--------------------------------------------------
			Set<Entry<String, Object>> cacheSet = parameterServiceImpl.getCacheInfo().entrySet();
			List<Entry<String, Object>> cachePropertys = new ArrayList<Entry<String, Object>>(cacheSet);

			Collections.sort(cachePropertys, new Comparator<Entry<String, Object>>() {
				@Override
				public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			page.putAttr("cachePropertys", cachePropertys);
		}

		// ------------------------------配置文件--------------------------------------------------
		{
			List<Entry<String, String>> filePropertys = PropertiesFileService.getEntrys();
			List<Entry<String, String>> xmlPropertys = XmlConfigFileService.getEntrys();
			filePropertys.addAll(xmlPropertys);
			for (Entry<String, String> node : filePropertys) {
				if (node.getKey().toUpperCase().indexOf("jdbc.password".toUpperCase()) >= 0) {
					node.setValue("******");
				}
			}
			Collections.sort(filePropertys, new Comparator<Entry<String, String>>() {
				@Override
				public int compare(Entry<String, String> o1, Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			page.putAttr("filePropertys", filePropertys);
		}
		// ------------------------------常量--------------------------------------------------
		{
			Map<String, String> sysConstant = new HashMap<>();
			DecimalFormat df = new DecimalFormat("0.00");
			long totalMem = Runtime.getRuntime().totalMemory();
			long maxMem = Runtime.getRuntime().maxMemory();
			long freeMem = Runtime.getRuntime().freeMemory();
			sysConstant.put("farm.jvm.memory.total", "已用内存" + df.format(totalMem / 1024 / 1024) + " MB");
			sysConstant.put("farm.jvm.memory.max", "最大内存" + df.format(maxMem / 1024 / 1024) + " MB");
			sysConstant.put("farm.jvm.memory.free", "可用内存" + df.format(freeMem / 1024 / 1024) + " MB");
			List<Entry<String, String>> constantPropertys = ConstantVarService.getEntrys();
			constantPropertys.addAll(sysConstant.entrySet());
			Collections.sort(constantPropertys, new Comparator<Entry<String, String>>() {
				@Override
				public int compare(Entry<String, String> o1, Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			page.putAttr("constantPropertys", constantPropertys);
		}
		// ------------------------------servletContext-----------------------------------------------------
		{
			List<Entry<String, String>> ServletContextPropertys = getServletContextParas(session);
			page.putAttr("servletContextPropertys", ServletContextPropertys);
		}
		return page.returnModelAndView("parameter/pAloneParameterConf");
	}

	/**
	 * 从servletContext中获取参数
	 * 
	 * @return
	 */
	private List<Entry<String, String>> getServletContextParas(HttpSession session) {
		Map<String, String> ServletContextMap = new HashMap<>();
		@SuppressWarnings("unchecked")
		Enumeration<Object> enums = session.getServletContext().getAttributeNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement().toString();
			if (key.indexOf("config_") >= 0) {
				ServletContextMap.put(key, session.getServletContext().getAttribute(key).toString());
			}
		}
		List<Entry<String, String>> ServletContextPropertys = new ArrayList<Entry<String, String>>(
				ServletContextMap.entrySet());
		Collections.sort(ServletContextPropertys, new Comparator<Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		return ServletContextPropertys;
	}

	@RequestMapping("/userelist")
	public ModelAndView showUserPara() {
		return ViewMode.getInstance().returnModelAndView("parameter/pAloneParameterConfForUser");
	}

	/**
	 * 注冊授權碼
	 * 
	 * @param key
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Publicenceup")
	public ModelAndView licenceup(String key, HttpServletRequest request, HttpSession session) {
		if (key.indexOf("//:") > 0) {
			String[] keys = key.split("//:");
			key = keys[0];
			if (StringUtils.isNotBlank(keys[1])) {
				try {
					WaterCodeUtils.getInctance().decode(keys[1]);
					registEnameBylicence(keys[1]);
				} catch (ValidationException e) {
					return ViewMode.getInstance().returnRedirectOnlyUrl("/index.jsp");
				}
			}
		}
		ReadKey.write(key.trim(), FarmParameterService.getInstance().getParameter("farm.constant.webroot.path"));
		ReadKey.read(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path"));
		ConstantVarService.registConstant("log.info.noKeys", request.getSession().getServletContext());
		return ViewMode.getInstance().returnRedirectOnlyUrl("/index.jsp");
	}

	/**
	 * 如果有该参数就修改，如果没有就创建
	 *
	 * @param ename
	 * @param session
	 */
	private void registEnameBylicence(String ename) {
		//Oschina
	}

	/**
	 * 清理系统全部缓存
	 *
	 * @return
	 */
	@RequestMapping("/clearCache")
	@ResponseBody
	public Map<String, Object> clearCache(HttpSession session) {
		parameterServiceImpl.flashAllCache();
		parameterServiceImpl.refreshCache();
		XmlConfigFileService.loadXmlToApplication(session.getServletContext());
		return ViewMode.getInstance().returnObjMode();
	}

	/**
	 * @param query
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryForEU")
	@ResponseBody
	public List<Map<String, Object>> queryallForEasyUi(DataQuery query, HttpServletRequest request) {
		try {

			List<Map<String, Object>> data = parameterServiceImpl.getAllParameters();

			for (Map<String, Object> node : data) {
				String name = (String) node.get("NAME");
				String note = (String) node.get("COMMENTS");

				node.put("NAME", name + "</br><div class='parameterTip'>" + note + "</div>");
			}

			List<Map<String, Object>> propertys = EasyUiUtils.formatPropertygridData(data, "NAME", "PVALUE", "DOMAIN",
					"VTYPE", "RULES", "ID");
			return propertys;
		} catch (Exception e) {
			log.error(e);
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping("/userqueryForEU")
	@ResponseBody
	public List<Map<String, Object>> userQueryallForEasyUi(DataQuery query, HttpServletRequest request,
			HttpSession session) {
		try {
			List<Map<String, Object>> propertys = EasyUiUtils.formatPropertygridData(
					parameterServiceImpl.getUserParameters(getCurrentUser(session).getId()), "NAME", "PVALUE", "DOMAIN",
					"VTYPE", "RULES", "ID");
			return propertys;
		} catch (Exception e) {
			log.error(e);
			return new ArrayList<Map<String, Object>>();
		}
	}

	/**
	 * 提交修改数据
	 *
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(AloneParameter entity, HttpSession session) {
		try {
			entity = parameterServiceImpl.editEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 *
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(AloneParameter entity, HttpSession session) {
		try {
			entity = parameterServiceImpl.insertEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除数据
	 *
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				parameterServiceImpl.deleteEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 从配置文件中加载参数
	 *
	 * @return
	 */
	@RequestMapping("/loadxml")
	@ResponseBody
	public Map<String, Object> loadxmlConf(String ids, HttpSession session) {
		try {
			parameterServiceImpl.loadXmlParasToDatabase(getCurrentUser(session));
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 读取配置参数
	 *
	 * @return
	 */
	@RequestMapping("/findPara")
	@ResponseBody
	public Map<String, Object> findPara(String key, HttpSession session) {
		try {
			return ViewMode.getInstance().putAttr("val", FarmParameterService.getInstance().getParameter(key.trim()))
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 修改系统参数的值
	 *
	 * @return
	 */
	@RequestMapping("/editEU")
	@ResponseBody
	public Map<String, Object> editSubmitByPValue(String ids, HttpSession session) {
		if (ids != null) {
			try {
				String[] paraArrays = ids.split("S2582E");
				for (String para : paraArrays) {
					String[] paraEntry = para.split("S2581E");
					if (paraEntry.length == 2) {
						String id = paraEntry[0].replaceAll("\n", "");
						String value = paraEntry[1];
						parameterServiceImpl.setValue(parameterServiceImpl.getEntity(id).getPkey(), value,
								getCurrentUser(session));
					} else {
						log.warn("配置项" + para + "参数异常!");
					}
				}
			} catch (Exception e) {
				log.error(e);
				return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
			}
		}
		return ViewMode.getInstance().returnObjMode();
	}

	/**
	 * 修改系统参数的值
	 *
	 * @return
	 */
	@RequestMapping("/editUserEU")
	@ResponseBody
	public Map<String, Object> editSubmitByUserPValue(String ids, HttpSession session) {
		try {
			String[] paraArrays = ids.split("&2582&");
			for (int i = 0; i < paraArrays.length; i++) {
				String[] paraEntry = paraArrays[i].split("&2581&");
				String id = paraEntry[0];
				String value = paraEntry[1];
				parameterServiceImpl.setUserValue(parameterServiceImpl.getEntity(id).getPkey(), value,
						getCurrentUser(session));
			}
		} catch (Exception e) {
			log.error(e);
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
		return ViewMode.getInstance().returnObjMode();
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.returnModelAndView("parameter/pAloneParameterEntity");
			}
			case (0): {// 展示
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", parameterServiceImpl.getEntity(ids))
						.returnModelAndView("parameter/pAloneParameterEntity");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", parameterServiceImpl.getEntity(ids))
						.returnModelAndView("parameter/pAloneParameterEntity");
			}
			default:
				break;
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("parameter/pAloneParameterEntity");
		}
		return ViewMode.getInstance().returnModelAndView("parameter/pAloneParameterEntity");
	}
}
