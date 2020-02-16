package com.farm.quartz.server.impl;

import static org.quartz.JobBuilder.newJob;

import java.text.ParseException;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.farm.quartz.domain.FarmQzScheduler;
import com.farm.quartz.domain.FarmQzTask;
import com.farm.quartz.domain.FarmQzTrigger;
import com.farm.quartz.server.QuartzInter;

public class QuartzImpl implements QuartzInter {

	@Override
	public JobDetail getJobDetail(FarmQzScheduler node, FarmQzTask task)
			throws ClassNotFoundException {
		return getJobDetail(node.getId(), task);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Trigger getTrigger(FarmQzScheduler node, FarmQzTrigger trigger)
			throws ParseException {
		Trigger qtrigger = new CronTriggerImpl(node.getId() + trigger.getId(),
				null, trigger.getDescript().trim());
		return qtrigger;
	}

	@Override
	public JobKey getJobKey(FarmQzScheduler node, FarmQzTask task) {
		return new JobKey(node.getId() + task.getId());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JobDetail getJobDetail(String schedulerId, FarmQzTask task)
			throws ClassNotFoundException {
		Class cla = Class.forName(task.getJobclass().trim());
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("PARA", task.getJobparas());
		JobDetail job = newJob(cla).withIdentity(schedulerId + task.getId())
				.setJobData(dataMap).build();
		return job;
	}

}
