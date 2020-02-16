package com.wts.exam.domain.ex;

import java.util.List;

import com.wts.exam.domain.ExamType;

public class ExamTypeUnit implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6569378733984612947L;
	private ExamType type;
	private List<RoomUnit> rooms;

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
		this.type = type;
	}

	public List<RoomUnit> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomUnit> rooms) {
		this.rooms = rooms;
	}
}
