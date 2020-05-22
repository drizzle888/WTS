package com.wts.exam.utils;

import java.util.List;

import com.wts.exam.domain.Paper;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.RoomServiceInter;
import com.wts.exam.service.SubjectServiceInter;

public class RoomSubjectLoadCacheUtils {

	// 0准备，1运行中
	private static int state = 0;
	private static int allroom = 0;
	private static int completeRoom = 0;

	public static void doload(final List<String> roomids, final RoomServiceInter roomServiceImpl,
			final PaperServiceInter paperServiceImpl, final SubjectServiceInter subjectServiceImpl) {
		if (state == 1) {
			throw new RuntimeException("正在运行中:" + completeRoom + "/" + allroom);
		} else {
			state = 1;
			completeRoom = 0;
		}
		// 实现runnable借口，创建多线程并启动
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					allroom = roomids.size();
					for (String roomid : roomids) {
						completeRoom++;
						for (Paper paper : roomServiceImpl.getLivePapers(roomid)) {
							List<String> subjectVersionids = paperServiceImpl.getAllSubjectVersionids(paper.getId());
							for (String subjectVersionid : subjectVersionids) {
								subjectServiceImpl.getSubjectUnit(subjectVersionid);
							}
						}
					}
				} finally {
					state = 0;
				}
			}
		}).start();
	}

	/**
	 * 总量
	 * 
	 * @return
	 */
	public static int getAllNums() {
		return allroom;
	}

	/**
	 * 完成量
	 * 
	 * @return
	 */
	public static int getCompleteNums() {
		return completeRoom;
	}

	/**
	 * 0准备，1运行中
	 * 
	 * @return
	 */
	public static int getState() {
		return state;
	}
}
