package com.wts.exam.domain.ex;

import java.util.List;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.Room;

/**
 * 考卷章节
 * 
 * @author wd
 *
 */
public class RoomUnit implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7847564306659999880L;
	private Room room;
	private ExamType type;
	private List<PaperUnit> papers;
	//用户答题权限
	private boolean currentUserAble;
	//时间可用权限
	private boolean currentTimeAble;
	//管理可用权限
	private boolean currentMngpopAble;
	//判卷可用权限
	private boolean currentAdjudgepopAble;
	
	public boolean isCurrentTimeAble() {
		return currentTimeAble;
	}

	public void setCurrentTimeAble(boolean currentTimeAble) {
		this.currentTimeAble = currentTimeAble;
	}

	public boolean isCurrentMngpopAble() {
		return currentMngpopAble;
	}

	public void setCurrentMngpopAble(boolean currentMngpopAble) {
		this.currentMngpopAble = currentMngpopAble;
	}

	public boolean isCurrentAdjudgepopAble() {
		return currentAdjudgepopAble;
	}

	public void setCurrentAdjudgepopAble(boolean currentAdjudgepopAble) {
		this.currentAdjudgepopAble = currentAdjudgepopAble;
	}

	public List<PaperUnit> getPapers() {
		return papers;
	}

	public void setPapers(List<PaperUnit> papers) {
		this.papers = papers;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public boolean isCurrentUserAble() {
		return currentUserAble;
	}

	public void setCurrentUserAble(boolean currentUserAble) {
		this.currentUserAble = currentUserAble;
	}


}
