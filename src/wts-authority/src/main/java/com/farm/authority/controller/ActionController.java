package com.farm.authority.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import com.farm.authority.domain.Action;
import com.farm.authority.service.ActionServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;

@RequestMapping("/action")
@Controller
public class ActionController extends WebUtils {
	private final static Logger log = Logger.getLogger(ActionController.class);
	@Resource
	ActionServiceInter actionServiceImpl;

	public ActionServiceInter getActionServiceImpl() {
		return actionServiceImpl;
	}

	public void setActionServiceImpl(ActionServiceInter actionServiceImpl) {
		this.actionServiceImpl = actionServiceImpl;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query,
			HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = actionServiceImpl
					.createActionSimpleQuery(query).search();
			result.runDictionary("1:是,0:否", "CHECKIS");
			result.runDictionary("1:是,0:否", "LOGINIS");
			result.runDictionary("1:可用,0:不可用", "STATE");
			result.runformatTime("UTIME", "yyyy-MM-dd HH:mm");
			return ViewMode.getInstance()
					.putAttrs(EasyUiUtils.formatGridData(result))
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}
	
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance()
				.returnModelAndView("authority/ActionResult");
	}
	

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Action entity, HttpSession session) {
		try {
			entity = actionServiceImpl.editActionEntity(entity,
					getCurrentUser(session));
			
			return ViewMode.getInstance().setOperate(OperateType.ADD)
					.putAttr("entity", entity).returnObjMode();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD)
					.setError(e.getMessage(),e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Action entity, HttpSession session) {
		try {
			entity = actionServiceImpl.insertActionEntity(entity,
					getCurrentUser(session));
			return ViewMode.getInstance().putAttr("entity", entity)
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
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
				actionServiceImpl.deleteActionEntity(id,
						getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	@ResponseBody
	public Map<String, Object> view(String ids) {
		try {
			return ViewMode.getInstance()
					.putAttr("entity", actionServiceImpl.getActionEntity(ids))
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
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
			case (0):{//查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", actionServiceImpl.getActionEntity(ids))
						.returnModelAndView("authority/ActionForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.returnModelAndView("authority/ActionForm");
			}
			case (2):{//修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", actionServiceImpl.getActionEntity(ids))
						.returnModelAndView("authority/ActionForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("authority/ActionForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(),e)
					.returnModelAndView("authority/UserForm");
		}
	}

}
