package com.wts.exam.domain.ex;

/**
 * 用户答题卡得信息对象(数据信息 )
 * 
 * @author macpl
 *
 */
public class CardInfo {
	/**
	 * 用户完成题目数量
	 */
	private int completeNum;

	/**
	 * 题目总数
	 */
	private int allNum;

	public int getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(int completeNum) {
		this.completeNum = completeNum;
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}
	
}
