package com.farm.wcp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.service.ExamTypeServiceInter;
import com.wts.exam.service.RoomServiceInter;

/**
 * 考试
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/exam")
@Controller
public class ExamWebController extends WebUtils {
	@Resource
	private RoomServiceInter roomServiceImpl;
	@Resource
	private ExamTypeServiceInter examTypeServiceImpl;

	private static final Logger log = Logger.getLogger(ExamWebController.class);

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}

	/***
	 * 考场首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/roompage")
	public ModelAndView index(String roomid, HttpServletRequest request, HttpSession session) {
		try {
			ViewMode view = ViewMode.getInstance();
			if (roomServiceImpl.getRoomEntity(roomid) == null) {
				throw new RuntimeException("该房间不存在!");
			}
			RoomUnit roomunit = roomServiceImpl.getRoomUnit(roomid, getCurrentUser(session), true);
			// 进入答题室：1.判断时间，判断人员
			if (!roomServiceImpl.isLiveTimeRoom(roomunit.getRoom())) {
				throw new RuntimeException("该房间不可用，未到答题时间!");
			}
			if (!roomServiceImpl.isUserAbleRoom(roomid, getCurrentUser(session))) {
				throw new RuntimeException("当前用户无进入权限!");
			}
			return view.putAttr("room", roomunit)
					.returnModelAndView(ThemesUtil.getThemePage("room-indexPage", request));
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}

	/***
	 * 匿名考场首页
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/Pubroompage")
	public ModelAndView pubRoompage(String roomid, HttpServletRequest request, HttpSession session) {
		try {
			LoginUser user = null;
			{
				// 匿名考场的话，匿名用户要存在session中
				if (!roomServiceImpl.isHaveAnonymous(session)) {
					// 创建匿名用户信息
					user = roomServiceImpl.initAnonymous(session, getCurrentIp(request));
				}
				user = roomServiceImpl.getAnonymous(session);
			}
			ViewMode view = ViewMode.getInstance();
			RoomUnit roomunit = roomServiceImpl.getRoomUnit(roomid, user, true);
			// 进入答题室：1.判断时间，判断人员
			if (!roomServiceImpl.isLiveTimeRoom(roomunit.getRoom())) {
				throw new RuntimeException("该房间不可用，未到答题时间!");
			}
			if (!roomServiceImpl.isUserAbleRoom(roomid, user)) {
				throw new RuntimeException("当前用户无进入权限!");
			}
			return view.putAttr("room", roomunit)
					.returnModelAndView(ThemesUtil.getThemePage("room-indexPage", request));
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}
}
