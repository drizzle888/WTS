package com.farm.web.task;

import java.util.List;

/**由spring注入启动任务，被sysinit类调用来启动配置的任务
 * @author Administrator
 *
 */
public interface TaskListInter {
	public List<ServletInitJobInter> getTasks();
}
