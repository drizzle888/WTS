package com.farm.quartz.server.impl;

import java.text.ParseException;

import javax.annotation.Resource;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.time.TimeTool;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.quartz.dao.FarmQzSchedulerDaoInter;
import com.farm.quartz.dao.FarmQzTaskDaoInter;
import com.farm.quartz.dao.FarmQzTriggerDaoInter;
import com.farm.quartz.domain.FarmQzScheduler;
import com.farm.quartz.domain.FarmQzTask;
import com.farm.quartz.domain.FarmQzTrigger;
import com.farm.quartz.server.FarmQzSchedulerManagerInter;
import com.farm.core.sql.query.DataQuery;

/**
 * 计划任务管理
 * 
 * @author MAC_wd
 */
@Service
public class FarmQzSchedulerManagerImpl implements FarmQzSchedulerManagerInter {
	@Resource
	private FarmQzSchedulerDaoInter farmQzSchedulerDao;
	@Resource
	private FarmQzTaskDaoInter farmQzTaskDao;
	@Resource
	private FarmQzTriggerDaoInter farmQzTriggerDao;

	private static final Logger log = Logger
			.getLogger(FarmQzSchedulerManagerImpl.class);

	private static Scheduler scheduler = null;

	@Override
	@Transactional
	public void start() throws SchedulerException, ParseException,
			ClassNotFoundException {
		if (scheduler == null) {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		}
		scheduler.start();
		for (FarmQzScheduler node : farmQzSchedulerDao.getAutoSchedulers()) {
			FarmQzTask task = getTaskEntity(node.getTaskid());
			FarmQzTrigger trigger = getTriggerEntity(node.getTriggerid());
			JobDetail job = new QuartzImpl().getJobDetail(node, task);
			Trigger qtrigger = new QuartzImpl().getTrigger(node, trigger);
			scheduler.scheduleJob(job, qtrigger);
		}
	}

	@Override
	@Transactional
	public boolean isRunningFindScheduler(String SchedulerId)
			throws SchedulerException {
		FarmQzScheduler node = getSchedulerEntity(SchedulerId);
		FarmQzTask task = getTaskEntity(node.getTaskid());
		if (scheduler.getJobDetail(new QuartzImpl().getJobKey(node, task)) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public void startTask(String SchedulerId) throws ClassNotFoundException,
			ParseException, SchedulerException {
		FarmQzScheduler node = getSchedulerEntity(SchedulerId);
		FarmQzTask task = getTaskEntity(node.getTaskid());
		FarmQzTrigger trigger = getTriggerEntity(node.getTriggerid());
		JobDetail job = new QuartzImpl().getJobDetail(node, task);
		Trigger qtrigger = new QuartzImpl().getTrigger(node, trigger);
		scheduler.scheduleJob(job, qtrigger);
	}

	@Override
	@Transactional
	public void stopTask(String SchedulerId) throws SchedulerException {
		FarmQzScheduler node = getSchedulerEntity(SchedulerId);
		FarmQzTask task = getTaskEntity(node.getTaskid());
		scheduler.deleteJob(new QuartzImpl().getJobKey(node, task));
	}

	@Override
	@Transactional
	public FarmQzScheduler insertSchedulerEntity(FarmQzScheduler entity,
			LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		entity = farmQzSchedulerDao.insertEntity(entity);
		if (entity.getAutois().equals("1")) {
			try {
				startTask(entity.getId());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return farmQzSchedulerDao.insertEntity(entity);
	}

	@Override
	@Transactional
	public FarmQzScheduler editSchedulerEntity(FarmQzScheduler entity,
			LoginUser user) {
		FarmQzScheduler entity2 = farmQzSchedulerDao.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setAutois(entity.getAutois());
		entity2.setTaskid(entity.getTaskid());
		entity2.setTriggerid(entity.getTriggerid());
		farmQzSchedulerDao.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteSchedulerEntity(String entity, LoginUser user) {
		try {
			stopTask(entity);
		} catch (SchedulerException e) {
			log.error(e);
		}
		farmQzSchedulerDao.deleteEntity(farmQzSchedulerDao.getEntity(entity));
	}

	@Override
	@Transactional
	public FarmQzScheduler getSchedulerEntity(String id) {
		if (id == null) {
			return null;
		}
		return farmQzSchedulerDao.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSchedulerSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery
				.init(query,
						"FARM_QZ_SCHEDULER A LEFT JOIN FARM_QZ_TASK B ON A.TASKID=B.ID LEFT JOIN FARM_QZ_TRIGGER C ON A.TRIGGERID=C.ID",
						"A.ID AS ID,A.AUTOIS AS AUTOIS,A.AUTOIS AS AUTOISTYPE,B.NAME AS TASKNAME,C.NAME AS CTRIGGERNAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public FarmQzTask insertTaskEntity(FarmQzTask entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		entity.setJobkey("NONE");
		try {
			for (@SuppressWarnings("rawtypes")
			Class cla : Class.forName(entity.getJobclass().trim())
					.getInterfaces()) {
				if (cla.equals(Job.class)) {
					return farmQzTaskDao.insertEntity(entity);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("类转化异常");
		}
		throw new RuntimeException("类为实现Job接口");
	}

	@Override
	@Transactional
	public FarmQzTask editTaskEntity(FarmQzTask entity, LoginUser user) {
		FarmQzTask entity2 = farmQzTaskDao.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setJobclass(entity.getJobclass());
		entity2.setName(entity.getName());
		entity2.setJobkey(entity.getJobkey());
		entity2.setJobparas(entity.getJobparas());
		entity2.setJobkey("NONE");
		entity2.setPcontent(entity.getPcontent());
		try {
			for (@SuppressWarnings("rawtypes")
			Class cla : Class.forName(entity2.getJobclass().trim())
					.getInterfaces()) {
				if (cla.equals(Job.class)) {
					farmQzTaskDao.editEntity(entity2);
					return entity2;
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("类转化异常");
		}
		throw new RuntimeException("类为实现Job接口");

	}

	@Override
	@Transactional
	public void deleteTaskEntity(String entity, LoginUser user) {
		farmQzTaskDao.deleteEntity(farmQzTaskDao.getEntity(entity));
	}

	@Override
	@Transactional
	public FarmQzTask getTaskEntity(String id) {
		if (id == null) {
			return null;
		}
		return farmQzTaskDao.getEntity(id);
	}

	@Override
	public DataQuery createTaskSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "farm_qz_task",
				"id,JOBCLASS,NAME,JOBKEY");
		return dbQuery;
	}

	@Override
	@Transactional
	public FarmQzTrigger insertTriggerEntity(FarmQzTrigger entity,
			LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		return farmQzTriggerDao.insertEntity(entity);
	}

	@Override
	@Transactional
	public FarmQzTrigger editTriggerEntity(FarmQzTrigger entity, LoginUser user) {
		FarmQzTrigger entity2 = farmQzTriggerDao.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPcontent(entity.getPcontent());
		entity2.setDescript(entity.getDescript());
		entity2.setName(entity.getName());
		farmQzTriggerDao.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteTriggerEntity(String entity, LoginUser user) {
		farmQzTriggerDao.deleteEntity(farmQzTriggerDao.getEntity(entity));
	}

	@Override
	@Transactional
	public FarmQzTrigger getTriggerEntity(String id) {
		if (id == null) {
			return null;
		}
		return farmQzTriggerDao.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createTriggerSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "farm_qz_trigger",
				"id,PCONTENT,DESCRIPT,NAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public FarmQzTask getTaskBySchedulerId(String schedulerId) {
		return farmQzTaskDao.getEntity(farmQzSchedulerDao
				.getEntity(schedulerId).getTaskid());
	}

}
