package com.wts.exam.utils;

import java.util.List;
import java.util.Set;

import com.farm.parameter.FarmParameterService;

/**
 * 作弊模式辅助程序
 * 
 * @author macpl
 *
 */
public class CheatUtils {

	/**
	 * 获得随机模式答題室内的固定答卷
	 * 
	 * @param roomid
	 *            答题室id
	 * @param paperids
	 *            有效答卷id集合
	 * @return 答卷id
	 */
	public static String getOnlyCheatPaper(String roomid, Set<String> paperids) {
		List<String> roomPapers = FarmParameterService.getInstance()
				.getParameterStringList("config.exam.cheat.roomrandom.onlypaper");
		for (String roomPaper : roomPapers) {
			String[] rp = roomPaper.split(":");
			// 参数数组为2参数有效/参数房间id和入参房间id一致/参数答卷id为有效答卷id
			if (rp.length == 2 && rp[0].equals(roomid) && paperids.contains(rp[1])) {
				return rp[1];
			}
		}
		return null;
	}

	/**
	 * 强制作弊为非随机答卷（通过配置作弊参数设置）
	 * 
	 * @return
	 */
	public static boolean isNoRandomPaper(String roomid, String paperid) {
		List<String> roomPapers = FarmParameterService.getInstance()
				.getParameterStringList("config.exam.cheat.roomrandom.onlypaper");
		for (String roomPaper : roomPapers) {
			String[] rp = roomPaper.split(":");
			if (rp.length == 2 && rp[0].equals(roomid) && rp[1].equals(paperid)) {
				return true;
			}
		}
		return false;
	}

}
