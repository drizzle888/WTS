package com.wts.exam.domain.ex;

import java.util.List;

import com.wts.exam.domain.Paper;
import com.wts.exam.domain.Room;
import com.wts.exam.domain.Card;

/**
 * 一套试卷的封装对象
 * 
 * @author wd
 *
 */
public class PaperUnit implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6709706178714557272L;
	/**
	 * 考卷信息
	 */
	private Paper info;
	/**
	 * 考场
	 */
	private Room room;
	/**
	 * 考卷章节
	 */
	private List<ChapterUnit> chapters;

	/**
	 * 大题数量
	 */
	private int rootChapterNum;
	/**
	 * 小题数量
	 */
	private int subjectNum;
	/**
	 * 总分
	 */
	private int allPoint;

	/**
	 * 用户答题信息
	 */
	private Card card;

	/**
	 * 所有指定參考人員
	 */
	private int allUserNum;

	/**
	 * 当前参考人员
	 */
	private int currentUserNum;
	
	/**
	 * 已经完成阅卷人员
	 */
	private int adjudgeUserNum;
	
	

	public int getAdjudgeUserNum() {
		return adjudgeUserNum;
	}

	public void setAdjudgeUserNum(int adjudgeUserNum) {
		this.adjudgeUserNum = adjudgeUserNum;
	}

	public int getAllUserNum() {
		return allUserNum;
	}

	public void setAllUserNum(int allUserNum) {
		this.allUserNum = allUserNum;
	}

	public int getCurrentUserNum() {
		return currentUserNum;
	}

	public void setCurrentUserNum(int currentUserNum) {
		this.currentUserNum = currentUserNum;
	}

	public int getRootChapterNum() {
		return rootChapterNum;
	}

	public void setRootChapterNum(int rootChapterNum) {
		this.rootChapterNum = rootChapterNum;
	}

	public int getSubjectNum() {
		return subjectNum;
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setSubjectNum(int subjectNum) {
		this.subjectNum = subjectNum;
	}

	public int getAllPoint() {
		return allPoint;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setAllPoint(int allPoint) {
		this.allPoint = allPoint;
	}

	public Paper getInfo() {
		return info;
	}

	public void setInfo(Paper info) {
		this.info = info;
	}

	public List<ChapterUnit> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterUnit> chapters) {
		this.chapters = chapters;
	}

}
