package com.wts.exam.domain.ex;

import java.util.List;

import com.wts.exam.domain.CardPoint;
import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectType;
import com.wts.exam.domain.SubjectVersion;

/**
 * 试题的完整封装
 * 
 * @author wangdong
 *
 */
public class SubjectUnit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5290830427511453703L;

	private Subject subject;
	private SubjectVersion version;
	private SubjectType subjectType;
	private TipType tipType;
	/**
	 * 題目選項
	 */
	private List<AnswerUnit> answers;

	/**
	 * 用户答题得分
	 */
	private CardPoint cardSubject;
	/**
	 * 题目得满分
	 */
	private int point;

	// 题的值，如果是问答题会用到（其他类型的值是存在答案中的）
	private String val;
	private String valtitle;
	// 题是否完成(考試時候判斷用戶的題是否已經答過了)
	private boolean finishIs = false;

	// -----------------------------------------------------------------------
	public boolean isFinishIs() {
		return finishIs;
	}

	public String getValtitle() {
		return valtitle;
	}

	public void setValtitle(String valtitle) {
		this.valtitle = valtitle;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public CardPoint getCardSubject() {
		return cardSubject;
	}

	public void setCardSubject(CardPoint cardSubject) {
		this.cardSubject = cardSubject;
	}

	public void setFinishIs(boolean finishIs) {
		this.finishIs = finishIs;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Subject getSubject() {
		return subject;
	}

	public List<AnswerUnit> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerUnit> answers) {
		this.answers = answers;
	}

	public SubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public SubjectVersion getVersion() {
		return version;
	}

	public void setVersion(SubjectVersion version) {
		this.version = version;
	}

	public TipType getTipType() {
		return tipType;
	}

	public void setTipType(TipType tipType) {
		this.tipType = tipType;
	}
}
