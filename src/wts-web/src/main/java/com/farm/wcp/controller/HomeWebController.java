package com.farm.wcp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.wcp.util.ThemesUtil;
import com.farm.wcp.util.ZxingTowDCode;
import com.farm.web.WebUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.wts.exam.domain.ex.ExamTypeUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.service.ExamTypeServiceInter;

/**
 * 文件
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/home")
@Controller
public class HomeWebController extends WebUtils {
	@Resource
	private UsermessageServiceInter usermessageServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;
	private static final Logger log = Logger.getLogger(HomeWebController.class);

	/***
	 * 首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/Pubindex")
	public ModelAndView pubindex(HttpServletRequest request, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		// 查询所有的root分类
		List<ExamTypeUnit> types = examTypeServiceImpl.getRootTypeUnits(getCurrentUser(session));
		String ctype = null;
		for (ExamTypeUnit type : types) {
			// 默认当前分类是第一个分类，但是如果分类下有考场的话就把这个分类做成默认分类
			if (ctype == null) {
				ctype = type.getType().getId();
			}
			if (type.getRooms().size() > 0) {
				ctype = type.getType().getId();
				break;
			}
		}
		boolean isHaveRoome = false;
		for (ExamTypeUnit typeUnit : types) {
			List<RoomUnit> rooms = typeUnit.getRooms();
			if (rooms.size() > 0) {
				isHaveRoome = true;
				break;
			}
		}
		return view.putAttr("types", types).putAttr("isHaveRoome", isHaveRoome).putAttr("ctype", ctype)
				.returnModelAndView(ThemesUtil.getThemePage("home-indexPage", request));
	}

	/***
	 * 首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpSession session) {
		return pubindex(request, session);
	}

	/**
	 * 展示二维码
	 */
	@RequestMapping("/PubQRCode")
	public void QRCode(HttpServletRequest request, HttpServletResponse response) {
		OutputStream outp = null;
		try {
			// 站点主页 String text = request.getScheme() + "://" +
			// request.getServerName() + ":" + request.getServerPort() +
			// request.getContextPath() +
			// "/";
			// 来访页面 request.getHeader("Referer");
			String text = request.getHeader("Referer");
			int width = 150;
			int height = 150;
			// 二维码的图片格式
			String format = "gif";
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 内容所使用编码
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			// 关于文件下载时采用文件流输出的方式处理：
			response.setContentType("application/x-download");
			// String filedownload = "想办法找到要提供下载的文件的物理路径＋文件名";
			String filedisplay = "给用户提供的下载文件名";
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			outp = response.getOutputStream();
			ZxingTowDCode.writeToStream(bitMatrix, format, outp);

		} catch (Exception e) {
			log.error(e + e.getMessage(), e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
				} catch (IOException e) {
					log.error(e + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 加载机构
	 * 
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping("/PubFPloadOrgs")
	public ModelAndView userInfo(HttpSession session) {
		try {
			DataQuery query = DataQuery.getInstance(1, "ID,NAME,PARENTID", "alone_auth_organization");
			query.setPagesize(1000);
			query.addRule(new DBRule("STATE", "1", "="));
			query.addSort(new DBSort("SORT", "ASC"));
			DataResult result = query.search();
			return ViewMode.getInstance().putAttr("result", result)
					.returnModelAndView(ThemesUtil.getThemePath() + "/user/commons/includePubOrgImpl");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("");
		}
	}

	/**
	 * 授權信息頁面
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public ModelAndView info(HttpSession session, HttpServletRequest request) {
		return ViewMode.getInstance().returnModelAndView("/licence/index");
	}

	/**
	 * 抓走用户session中的一条数据(抓取并从session中删除) home/carrySession
	 * 
	 * @return
	 */
	@RequestMapping("/carrySession")
	@ResponseBody
	public Map<String, Object> carrySession(String attrName, HttpSession session) {
		ViewMode page = ViewMode.getInstance();
		Object val = session.getAttribute(attrName);
		if (val != null) {
			session.removeAttribute(attrName);
			page.putAttr("val", val);
		}
		return page.returnObjMode();
	}

}
