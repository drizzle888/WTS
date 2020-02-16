package com.farm.wcp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.Usermessage;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.parameter.FarmParameterService;
import com.farm.wcp.util.ThemesUtil;
import com.farm.web.WebUtils;

/**
 * 用户消息
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/webusermessage")
@Controller
public class UserMsgWebController extends WebUtils {
	@Resource
	private UsermessageServiceInter usermessageServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;

	public static String getThemePath() {
		return FarmParameterService.getInstance().getParameter("config.sys.web.themes.path");
	}


	/**
	 * 查看用户信息
	 * 
	 * @param id
	 *            消息ID
	 * @param num
	 *            分页参数（返回消息列表需要）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/showMessage")
	public ModelAndView showMessage(String id, Integer num) {
		try {
			usermessageServiceImpl.setRead(id);
			Usermessage usermessage = usermessageServiceImpl.getUsermessageEntity(id);
			String readstatename = "";
			if (usermessage.getReadstate().equals("0")) {// 0未读、1已读
				readstatename = "未读";
			} else if (usermessage.getReadstate().equals("1")) {
				readstatename = "已读";
			}
			return ViewMode.getInstance().putAttr("usermessage", usermessage).putAttr("readstatename", readstatename)
					.putAttr("num", num).returnModelAndView(ThemesUtil.getThemePath() + "/user/userMessage");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView(getThemePath() + "/error");
		}
	}


	/**
	 * 显示首页未读消息
	 * 
	 * @return Map<String,Object>
	 */
	@RequestMapping("/showHomeMessage")
	@ResponseBody
	public Map<String, Object> showHomeMessage(HttpSession session) {
		try {
			DataResult result = usermessageServiceImpl.getUnreadMessagesByUser(getCurrentUser(session).getId(), 50, 1);
			if (result == null) {
				result = DataResult.getInstance();
			}
			List<Map<String, Object>> list = result.getResultList();
			String newMessage = "";
			if (list != null && list.size() > 0) {
				newMessage = list.get(0).get("TITLE").toString();
			}
			return ViewMode.getInstance().putAttr("newMessage", newMessage).putAttr("unReadCount", list.size())
					.returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().returnObjMode();
		}
	}
}
