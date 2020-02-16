package com.farm.web.task.impl;

import java.util.List;

import com.farm.web.task.ServletInitJobInter;
import com.farm.web.task.TaskListInter;

public class TaskListImpl implements TaskListInter {
	private List<ServletInitJobInter> tasks;

	@Override
	public List<ServletInitJobInter> getTasks() {
		return tasks;
	}

	public void setTasks(List<ServletInitJobInter> tasks) {
		this.tasks = tasks;
	}

}
