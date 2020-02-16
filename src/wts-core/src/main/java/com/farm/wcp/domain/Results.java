package com.farm.wcp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Results implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1218336783285073477L;
	private List<Map<String, Object>> list = new ArrayList<>();
	private int totalsize;
	private int currentpage;
	private int pagesize;

	public static Results getResults(List<Map<String, Object>> list, int totalsize, int currentpage, int pagesize) {
		Results obj = new Results();
		obj.list = list;
		obj.totalsize = totalsize;
		obj.currentpage = currentpage;
		obj.pagesize = pagesize;
		return obj;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public int getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

}
