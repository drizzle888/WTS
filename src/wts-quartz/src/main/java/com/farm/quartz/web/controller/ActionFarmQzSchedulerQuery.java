package com.farm.quartz.web.controller;

import com.farm.quartz.domain.FarmQzScheduler;
import com.farm.quartz.domain.FarmQzTask;
import com.farm.quartz.server.DemoJobExecutionContext;
import com.farm.quartz.server.FarmQzSchedulerManagerInter;
import com.farm.quartz.server.impl.QuartzImpl;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;
import com.farm.web.log.WcpLog;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

/**
 * 计划任务管理
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/qzScheduler")
@Controller
public class ActionFarmQzSchedulerQuery extends WebUtils {
	private static final Logger log = Logger.getLogger(ActionFarmQzSchedulerQuery.class);
	@Resource
	FarmQzSchedulerManagerInter farmQzSchedulerManagerImpl;

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			query = farmQzSchedulerManagerImpl.createSchedulerSimpleQuery(query);
			DataResult result = query.search().runDictionary("1:自动,0:手动", "AUTOIS");
			for (Map<String, Object> node : result.getResultList()) {
				try {
					node.put("RUN", farmQzSchedulerManagerImpl.isRunningFindScheduler(node.get("ID").toString()));
					node.put("RUNTYPE", node.get("RUN"));
				} catch (Exception e) {
					log.error(e);
				}
			}
			result.runDictionary("true:是,false:否", "RUN");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/chooseTask")
	public ModelAndView chooseTask(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("quartz/pFarmQzTaskGrid");
	}

	@RequestMapping("/chooseTrigger")
	public ModelAndView chooseTrigger(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("quartz/pFarmQzTriggerGrid");
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("quartz/pFarmQzSchedulerLayout");
	}

	/**
	 * start
	 * 
	 * @return
	 */
	@RequestMapping("/start")
	@ResponseBody
	public Map<String, Object> start(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				farmQzSchedulerManagerImpl.startTask(id);
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * stop
	 * 
	 * @return
	 */
	@RequestMapping("/stop")
	@ResponseBody
	public Map<String, Object> stop(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				farmQzSchedulerManagerImpl.stopTask(id);
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * runOnec
	 * 
	 * @return
	 */
	@RequestMapping("/runOnec")
	@ResponseBody
	public Map<String, Object> runOnec(final String ids, HttpSession session) {
		try {
			Thread t = new Thread(new Runnable() {
				public void run() {
					for (String id : parseIds(ids)) {
						FarmQzTask task = farmQzSchedulerManagerImpl.getTaskBySchedulerId(id);
						if (task != null) {
							try {
								JobDetail jobDet = new QuartzImpl().getJobDetail("none", task);
								Job job = (Job) Class.forName(jobDet.getJobClass().getName()).newInstance();
								JobExecutionContext jobContext = new DemoJobExecutionContext(jobDet.getJobDataMap());
								job.execute(jobContext);
								WcpLog.info("定时任务执行完毕! " + task.getName(), "定时任务", "AUTO-RUN");
							} catch (Exception e) {
								log.error(e.toString(), e);
								WcpLog.error("执行定时任务" + task.getName() + ":" + e.getMessage(), "定时任务", "AUTO-RUN");
							}
						} else {
							log.warn("定时任务ID:" + id + "不存在!");
						}
					}
				}
			});
			t.start();
			return ViewMode.getInstance().putAttr("INFO", parseIds(ids).size() + "条任务被启动,执行情况请在日志中查看").returnObjMode();
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
	@RequestMapping("/addSubmit")
	@ResponseBody
	public Map<String, Object> addSubmit(FarmQzScheduler entity, HttpSession session) {
		try {
			entity = farmQzSchedulerManagerImpl.insertSchedulerEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().putAttr("entity", entity).returnObjMode();
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
	@RequestMapping("/editSubmit")
	@ResponseBody
	public Map<String, Object> editSubmit(FarmQzScheduler entity, HttpSession session) {
		try {
			entity = farmQzSchedulerManagerImpl.editSchedulerEntity(entity, getCurrentUser(session));

			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
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
				farmQzSchedulerManagerImpl.deleteSchedulerEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
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
						.returnModelAndView("quartz/pFarmQzSchedulerEntity");
			}
			case (0): {// 展示
				FarmQzScheduler entity = farmQzSchedulerManagerImpl.getSchedulerEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.putAttr("taskStr", farmQzSchedulerManagerImpl.getTaskEntity(entity.getTaskid()).getName())
						.putAttr("triggerStr",
								farmQzSchedulerManagerImpl.getTriggerEntity(entity.getTriggerid()).getName())
						.returnModelAndView("quartz/pFarmQzSchedulerEntity");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", farmQzSchedulerManagerImpl.getSchedulerEntity(ids))
						.returnModelAndView("quartz/pFarmQzSchedulerEntity");
			}
			default:
				break;
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e)
					.returnModelAndView("quartz/pFarmQzSchedulerEntity");
		}
		return ViewMode.getInstance().returnModelAndView("quartz/pFarmQzSchedulerEntity");
	}
}
