package com.farm.quartz.job.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.farm.core.FarmUtils;
import com.farm.core.config.AppConfig;

public class CommandRuningJob implements Job {
	private static final Logger log = Logger.getLogger(CommandRuningJob.class);

	@SuppressWarnings("static-access")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			String cmdAble = AppConfig.getString("config.quartz.task.cmd.able");
			if (!cmdAble.toUpperCase().equals("TRUE")) {
				throw new RuntimeException("the cmd can't run!");
			}
		} catch (Exception e) {
			log.error(e.getMessage() + "update config.properties setting 'config.quartz.task.cmd.able' please!");
			throw new RuntimeException(e.getMessage() + "update config.properties setting 'config.quartz.task.cmd.able' please!");
		}
		String commandStr = arg0.getMergedJobDataMap().get("PARA").toString();
		if (FarmUtils.getValidUtils().isEmptyString(commandStr)) {
			throw new RuntimeException("空命令行参数");
		}
		InputStream stderr = null; // 获取输入流
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String cmd = commandStr.trim();
			log.info("执行命令行：" + cmd);
			Runtime rt = Runtime.getRuntime(); // 获取运行时系统
			Process proc = rt.exec(cmd); // 执行命令
			stderr = proc.getInputStream(); // 获取输入流
			isr = new InputStreamReader(stderr);
			br = new BufferedReader(isr);
			String line = null;
			StringBuffer lineBuffer = new StringBuffer();
			while ((line = br.readLine()) != null) { // 打印出命令执行的结果
				lineBuffer.append(line);
				log.info(line);
			}
		} catch (Exception t) {
			log.error(t + t.getMessage());
			throw new RuntimeException(t);
		} finally {
			try {
				stderr.close();
				isr.close();
				br.close();
			} catch (IOException e) {
				log.error(e + e.getMessage());
				throw new RuntimeException(e);
			}
		}
	}
}
