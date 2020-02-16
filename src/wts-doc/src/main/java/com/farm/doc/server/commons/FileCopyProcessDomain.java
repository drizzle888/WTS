package com.farm.doc.server.commons;

import java.util.Date;

/**
 * 用来缓存文件的拷贝进度
 * 
 * @author macpl
 *
 */
public class FileCopyProcessDomain {
	private Date ctime;
	private int percent;

	public FileCopyProcessDomain(Date ctime, int percent) {
		super();
		this.ctime = ctime;
		this.percent = percent;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

}
