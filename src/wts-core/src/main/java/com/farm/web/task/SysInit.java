package com.farm.web.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.farm.util.spring.BeanFactory;

public class SysInit extends HttpServlet {

	/**
	 * 任务集合
	 */
	private static List<ServletInitJobInter> list = new ArrayList<ServletInitJobInter>();
	private static final long serialVersionUID = 1L;

	// 配置系统所有默认启动任务

	public SysInit() {
		super();
	}

	private final Logger log = Logger.getLogger(this.getClass());

	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {
		list = ((TaskListInter) BeanFactory.getBean("startServerTasksId", getServletContext())).getTasks();
		log.info("--系统准备运行" + list.size() + "项");
		if (list == null) {
			list = new ArrayList<ServletInitJobInter>();
		}
		int n = 0;
		try {
			for (Iterator<ServletInitJobInter> iterator = list.iterator(); iterator.hasNext();) {
				n++;
				ServletInitJobInter name = (ServletInitJobInter) iterator.next();
				name.execute(this.getServletContext());
			}
		} catch (Exception e) {
			log.error("第" + n + "项任务启动失败：" + e.getMessage());
		}
	}
}
