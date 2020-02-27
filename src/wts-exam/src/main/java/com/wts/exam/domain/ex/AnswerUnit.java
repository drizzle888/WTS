package com.wts.exam.domain.ex;

import com.wts.exam.domain.SubjectAnswer;

/**
 * 试题的完整封装
 * 
 * @author wangdong
 *
 */
public class AnswerUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5651120806931586865L;
	private SubjectAnswer answer;
	/**
	 * 填空題和選擇題的答案在answer中記錄
	 */
	private String val;

	public SubjectAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(SubjectAnswer answer) {
		this.answer = answer;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
