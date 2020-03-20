package com.wts.exam.controller;

import com.wts.exam.domain.RoomPaper;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.RoomPaperServiceInter;
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
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：考试试卷控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/roompaper")
@Controller
public class RoomPaperController extends WebUtils {
	private final static Logger log = Logger.getLogger(RoomPaperController.class);
	@Resource
	RoomPaperServiceInter roomPaperServiceImpl;
	@Resource
	private CardServiceInter cardServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, final String roomid, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("ROOMID", roomid, "="));
			DataResult result = roomPaperServiceImpl.createRoompaperSimpleQuery(query).search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					PaperUnit paper = cardServiceImpl.getRoomPaperUserNums(roomid, (String) row.get("PAPERID"));
					row.put("CURRENTUSERNUM", paper.getCurrentUserNum());
					row.put("ADJUDGEUSERNUM", paper.getAdjudgeUserNum());
					row.put("ALLUSERNUM", paper.getAllUserNum());
				}
			});
			result.runDictionary("1:新建,0:停用,2:发布", "PAPERSTATE");
			result.runformatTime("PAPERTIME", "yyyy-MM-dd HH:mm");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(RoomPaper entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = roomPaperServiceImpl.editRoompaperEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/editOtherName")
	@ResponseBody
	public Map<String, Object> editOtherName(String ids, String name, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				roomPaperServiceImpl.editOtherName(id, name, getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/clearOtherName")
	@ResponseBody
	public Map<String, Object> clearOtherName(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				roomPaperServiceImpl.clearOtherName(id,getCurrentUser(session));
			}
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}
	
	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(RoomPaper entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = roomPaperServiceImpl.insertRoompaperEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
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
				roomPaperServiceImpl.deleteRoompaperEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 清空用户试卷下的答题卡
	 * 
	 * @return
	 */
	@RequestMapping("/clearUserCard")
	@ResponseBody
	public Map<String, Object> clearUserCard(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				RoomPaper roomPaper = roomPaperServiceImpl.getRoompaperEntity(id);
				cardServiceImpl.clearRoomCard(roomPaper.getRoomid(), roomPaper.getPaperid(), getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(String roomid, HttpSession session) {
		return ViewMode.getInstance().putAttr("roomid", roomid).returnModelAndView("exam/RoompaperResult");
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
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", roomPaperServiceImpl.getRoompaperEntity(ids))
						.returnModelAndView("exam/RoompaperForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("exam/RoompaperForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", roomPaperServiceImpl.getRoompaperEntity(ids))
						.returnModelAndView("exam/RoompaperForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/RoompaperForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/RoompaperForm");
		}
	}

	/**
	 * 添加试卷到考场
	 * 
	 * @return
	 */
	@RequestMapping("/addPaperSubmit")
	@ResponseBody
	public Object addPaperSubmit(String roomid, String paperids, HttpSession session) {
		try {
			for (String paperid : parseIds(paperids)) {
				roomPaperServiceImpl.addRoomPaper(roomid, paperid, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
