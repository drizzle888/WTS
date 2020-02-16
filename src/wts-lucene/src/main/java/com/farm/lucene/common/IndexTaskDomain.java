package com.farm.lucene.common;

public class IndexTaskDomain {
	// 当前处理进度
	private int processNum;
	// 当前总任务数量
	private int maxNum;
	// 任务状态 0初始化/工作中，1(索引任务中为删除原索引|知识导入导出任务中为完成)，2添加新索引，3完成，-1无任务,-2发生错误
	private int state;
	// 任务消息
	private String message;
	// 任务关键字
	private String taskKey;

	public IndexTaskDomain(int processNum, int maxNum, int state, String message, String taskKey) {
		super();
		this.processNum = processNum;
		this.maxNum = maxNum;
		this.state = state;
		this.message = message;
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public int getProcessNum() {
		return processNum;
	}

	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
