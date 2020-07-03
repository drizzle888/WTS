package com.wts.exam.controller;

import com.wts.exam.service.RoomuUserServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：参考人控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/roomuser")
@Controller
public class RoomUserController extends WebUtils {
	private final static Logger log = Logger.getLogger(RoomUserController.class);
	@Resource
	RoomuUserServiceInter roomUserServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, String roomid, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("A.ROOMID", roomid, "="));
			DataResult result = roomUserServiceImpl.createRoomuserSimpleQuery(query).search();
			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm");
			result.runDictionary("0:开始答题,1:按时交卷,2:答题超时", "OVERTIME");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
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
	public Map<String, Object> addSubmit(String roomid, String userids, HttpSession session) {
		try {
			for (String userid : parseIds(userids)) {
				roomUserServiceImpl.insertRoomUser(roomid, userid, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.ADD).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
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
				roomUserServiceImpl.deleteRoomuserEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(String roomid, HttpSession session) {
		return ViewMode.getInstance().putAttr("roomid", roomid).returnModelAndView("exam/RoomuserResult");
	}
}
