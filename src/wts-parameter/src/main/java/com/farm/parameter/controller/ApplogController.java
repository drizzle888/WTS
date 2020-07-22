package com.farm.parameter.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.parameter.FarmParameterService;
import com.farm.parameter.service.AloneApplogServiceInter;
import com.farm.parameter.util.ZipUtils;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.easyui.EasyUiUtils;

/**
 * 系统日志
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/log")
@Controller
public class ApplogController {
	private final static Logger log = Logger.getLogger(ApplogController.class);
	@Resource
	AloneApplogServiceInter aloneApplogServiceImpl;

	public AloneApplogServiceInter getAloneApplogServiceImpl() {
		return aloneApplogServiceImpl;
	}

	public void setAloneApplogServiceImpl(AloneApplogServiceInter aloneApplogServiceImpl) {
		this.aloneApplogServiceImpl = aloneApplogServiceImpl;
	}

	/**
	 * 进入日志界面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("parameter/pAloneApplogLayout");
	}

	/**
	 * 查询结果集合
	 *
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query = DataQuery.init(query, "alone_applog a left join alone_auth_user b on b.id=a.APPUSER",
					"a.id as id,a.CTIME as ctime,a.DESCRIBES as describes,b.name as APPUSER,a.LEVELS as levels,a.METHOD as method,a.CLASSNAME as classname,a.IP as ip");
			query.addDefaultSort(new DBSort("a.CTIME", "DESC"));
			DataResult result = query.search();
			result.LoadListArray();
			result.runformatTime("CTIME", "yyyy-MM-dd_HH/mm/ss");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
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
						.returnModelAndView("parameter/pAloneApplogEntity");
			}
			case (0): {// 展示
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", aloneApplogServiceImpl.getEntity(ids))
						.returnModelAndView("parameter/pAloneApplogEntity");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", aloneApplogServiceImpl.getEntity(ids))
						.returnModelAndView("parameter/pAloneApplogEntity");
			}
			default:
				break;
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("parameter/pAloneApplogEntity");
		}
		return ViewMode.getInstance().returnModelAndView("parameter/pAloneParameterEntity");
	}

	/**
	 * 删除日志表单
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/delform")
	public ModelAndView delform(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("parameter/pAloneApplogDelForm");
	}

	/**
	 * 提交刪除日志
	 *
	 * @return
	 */
	@RequestMapping("/delsubmit")
	@ResponseBody
	public Map<String, Object> delsubmit(String ctime, String etime, HttpServletRequest request) {
		try {
			ctime = ctime.replaceAll("-", "") + "000000";
			etime = etime.replaceAll("-", "") + "999999";
			if (ctime.length() != 14 || etime.length() != 14) {
				throw new RuntimeException("时间参数格式错误！" + ctime + "/" + etime);
			}
			aloneApplogServiceImpl.deleteByDate(ctime, etime);
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
		return ViewMode.getInstance().returnObjMode();
	}

	/**
	 * 下载日志
	 * 
	 * @param response
	 */
	@RequestMapping("/downloadlog")
	public void downloadlog(HttpServletResponse response) {
		InputStream is = null;
		OutputStream os = null;
		try {
			ResourceBundle config = ResourceBundle.getBundle("log4j");
			String path = config.getString("log4j.appender.Filelog.File");
			File file = new File(path.replace("${catalina.base}", System.getProperty("catalina.base")));
			log.info("下载日志文件：" + file.getPath());

			File zipFile = new File(FarmParameterService.getInstance().getParameter("config.doc.dir") + File.separator
					+ "export" + File.separator + "logs.zip");
			if (zipFile.exists()) {
				zipFile.delete();
			}
			zipFile.mkdirs();
			zipFile = ZipUtils.exportZipFile(file.getParentFile(), zipFile);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + new String(zipFile.getName().getBytes("gbk"), "iso-8859-1"));
			is = new FileInputStream(zipFile);
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = is.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (Exception e) {
			log.error("下载日志", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				log.error("下载日志", e);
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				log.error("下载日志", e);
			}
		}
	}
}
