package com.farm.quartz.server;

import java.text.ParseException;

import org.quartz.SchedulerException;

import com.farm.quartz.domain.FarmQzScheduler;
import com.farm.quartz.domain.FarmQzTask;
import com.farm.quartz.domain.FarmQzTrigger;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;

/**
 * 计划任务管理
 * 
 * @author MAC_wd
 * 
 */
public interface FarmQzSchedulerManagerInter {
	/**
	 *新增实体
	 * 
	 * @param entity
	 */
	public FarmQzScheduler insertSchedulerEntity(FarmQzScheduler entity,
			LoginUser user);

	/**
	 * 启动一个任务
	 * 
	 * @param SchedulerId
	 * @throws ClassNotFoundException
	 *             类class无法解析
	 * @throws ParseException
	 *             计划字符串脚本无法解析
	 * @throws SchedulerException
	 */
	public void startTask(String SchedulerId) throws ClassNotFoundException,
			ParseException, SchedulerException;

	/**
	 * 停止一个任务
	 * 
	 * @param SchedulerId
	 * @throws SchedulerException
	 */
	public void stopTask(String SchedulerId) throws SchedulerException;

	/**
	 * 启动调度引擎
	 * 
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public void start() throws SchedulerException, ParseException,
			ClassNotFoundException;

	/**
	 *修改实体
	 * 
	 * @param entity
	 */
	public FarmQzScheduler editSchedulerEntity(FarmQzScheduler entity,
			LoginUser user);

	/**
	 *删除实体
	 * 
	 * @param entity
	 */
	public void deleteSchedulerEntity(String entity, LoginUser user);

	/**
	 *获得实体
	 * 
	 * @param id
	 * @return
	 */
	public FarmQzScheduler getSchedulerEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSchedulerSimpleQuery(DataQuery query);

	/**
	 * 判断一个任务计划是否启动
	 * 
	 * @param SchedulerId
	 * @return
	 * @throws SchedulerException
	 */
	public boolean isRunningFindScheduler(String SchedulerId)
			throws SchedulerException;

	/**
	 *新增触发器定义实体
	 * 
	 * @param entity
	 */
	public FarmQzTrigger insertTriggerEntity(FarmQzTrigger entity,
			LoginUser user);

	/**
	 *修改触发器定义实体
	 * 
	 * @param entity
	 */
	public FarmQzTrigger editTriggerEntity(FarmQzTrigger entity, LoginUser user);

	/**
	 *删除触发器定义实体
	 * 
	 * @param entity
	 */
	public void deleteTriggerEntity(String entity, LoginUser user);

	/**
	 *获得触发器定义实体
	 * 
	 * @param id
	 * @return
	 */
	public FarmQzTrigger getTriggerEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前触发器定义实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createTriggerSimpleQuery(DataQuery query);

	/**
	 *新增任务定义实体
	 * 
	 * @param entity
	 */
	public FarmQzTask insertTaskEntity(FarmQzTask entity, LoginUser user);

	/**
	 *修改任务定义实体
	 * 
	 * @param entity
	 */
	public FarmQzTask editTaskEntity(FarmQzTask entity, LoginUser user);

	/**
	 *删除任务定义实体
	 * 
	 * @param entity
	 */
	public void deleteTaskEntity(String entity, LoginUser user);

	/**
	 *获得任务定义实体
	 * 
	 * @param id
	 * @return
	 */
	public FarmQzTask getTaskEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前任务定义实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createTaskSimpleQuery(DataQuery query);

	/**
	 * 由一个实例获得任务对象
	 * 
	 * @param schedulerId
	 * @return
	 */
	public FarmQzTask getTaskBySchedulerId(String schedulerId);
}