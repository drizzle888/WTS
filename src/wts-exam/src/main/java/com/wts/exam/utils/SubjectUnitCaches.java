package com.wts.exam.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.farm.core.FarmUtils;
import com.wts.exam.domain.ex.SubjectUnit;

/**
 * 题缓存
 * 
 * @author macpl
 *
 */
public class SubjectUnitCaches {
	private static final Logger log = Logger.getLogger(SubjectUnitCaches.class);
	public static Map<String, SubjectUnit> UNITS_CACHE = new HashMap<String, SubjectUnit>();

	/**
	 * 更新題目緩存
	 * 
	 * @param versionid
	 */
	public static void refresh(String versionid) {
		UNITS_CACHE.remove(versionid);
	}

	public static SubjectUnit get(String versionId) {
		if (UNITS_CACHE.size() > 20000) {
			UNITS_CACHE.clear();
			log.warn("缓存超出20000上限，全部清空!");
		}
		SubjectUnit newunit = (SubjectUnit) FarmUtils.deepCopy(UNITS_CACHE.get(versionId));
		return newunit;
	}

	public static void put(String versionId, SubjectUnit newunit) {
		UNITS_CACHE.put(versionId, newunit);
	}

}
