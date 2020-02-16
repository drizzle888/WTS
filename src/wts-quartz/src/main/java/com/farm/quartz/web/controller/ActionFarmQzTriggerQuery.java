package com.farm.quartz.web.controller;

import com.farm.quartz.domain.FarmQzTrigger;
import com.farm.quartz.server.FarmQzSchedulerManagerInter;
import com.farm.web.easyui.EasyUiUtils;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.WebUtils;

/**
 * 触发器定义
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/qzTrigger")
@Controller
public class ActionFarmQzTriggerQuery extends WebUtils {
	private static final Logger log = Logger
			.getLogger(ActionFarmQzTriggerQuery.class);
	@Resource
	FarmQzSchedulerManagerInter farmQzSchedulerManagerImpl;

	// /**
	// * 查询结果集合
	// *
	// * @return
	// */
	// public String queryall() {
	// try {
	// query = EasyUiUtils.formatGridQuery(getRequest(), query);
	// DataResult result = aloneIMP.createTriggerSimpleQuery(query)
	// .search();
	// jsonResult = EasyUiUtils.formatGridData(result);
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// return SUCCESS;
	// }
	
	/**
	 * 查询结果集合
	 *
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query,
			HttpServletRequest request) {
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			query = farmQzSchedulerManagerImpl.createTriggerSimpleQuery(query);
			DataResult result = query.search();
			return ViewMode.getInstance()
					.putAttrs(EasyUiUtils.formatGridData(result))
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}

	// /**
	// * 提交修改数据
	// *
	// * @return
	// */
	// public String editSubmit() {
	// try {
	// entity = aloneIMP.editTriggerEntity(entity, getCurrentUser());
	// pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.UPDATE,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(FarmQzTrigger entity,
			HttpSession session) {
		try {
			entity = farmQzSchedulerManagerImpl.editTriggerEntity(entity,
					getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD)
					.putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setOperate(OperateType.ADD)
					.setError(e.getMessage(),e).returnObjMode();
		}
	}

	// /**
	// * 提交新增数据
	// *
	// * @return
	// */
	// public String addSubmit() {
	// try {
	// entity = aloneIMP.insertTriggerEntity(entity, getCurrentUser());
	// pageset = new PageSet(PageType.ADD, CommitType.TRUE);
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.ADD,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(FarmQzTrigger entity,
			HttpSession session) {
		try {
			entity = farmQzSchedulerManagerImpl.insertTriggerEntity(entity,
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
				farmQzSchedulerManagerImpl.deleteTriggerEntity(id,
						getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}

	// /**
	// * 删除数据
	// *
	// * @return
	// */
	// public String delSubmit() {
	// try {
	// for (String id : parseIds(ids)) {
	// aloneIMP.deleteTriggerEntity(id, getCurrentUser());
	// }
	// pageset = new PageSet(PageType.ADD, CommitType.TRUE);
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.DEL,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }

	// /**
	// * 跳转
	// *
	// * @return
	// */
	// public String forSend() {
	// return SUCCESS;
	// }
	/**
	 * 跳转
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView(
				"quartz/pFarmQzTriggerLayout");
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
						.returnModelAndView("quartz/pFarmQzTriggerEntity");
			}
			case (0): {// 展示
				return ViewMode
						.getInstance()
						.putAttr("pageset", pageset)
						.putAttr(
								"entity",
								farmQzSchedulerManagerImpl
										.getTriggerEntity(ids))
						.returnModelAndView("quartz/pFarmQzTriggerEntity");
			}
			case (2): {// 修改
				return ViewMode
						.getInstance()
						.putAttr("pageset", pageset)
						.putAttr(
								"entity",
								farmQzSchedulerManagerImpl
										.getTriggerEntity(ids))
						.returnModelAndView("quartz/pFarmQzTriggerEntity");
			}
			default:
				break;
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnModelAndView("parameter/pFarmQzTriggerEntity");
		}
		return ViewMode.getInstance().returnModelAndView(
				"parameter/pFarmQzTriggerEntity");
	}

	// /**
	// * 显示详细信息（修改或浏览时）
	// *
	// * @return
	// */
	// public String view() {
	// try {
	// switch (pageset.getPageType()) {
	// case (1): {// 新增
	// return SUCCESS;
	// }
	// case (0): {// 展示
	// entity = aloneIMP.getTriggerEntity(ids);
	// return SUCCESS;
	// }
	// case (2): {// 修改
	// entity = aloneIMP.getTriggerEntity(ids);
	// return SUCCESS;
	// }
	// default:
	// break;
	// }
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.OTHER,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }

}
