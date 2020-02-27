package com.farm.wcp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.wts.exam.domain.Material;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.wts.exam.service.MaterialServiceInter;

/**
 * 创建一场随机测试，或者从一场测试中取出题
 * 
 * @author macpl
 *
 */
public class SubjectTestUtils {
	/**
	 * 創建一場測試
	 * 
	 * @param index
	 * @param testid
	 *            和subjects只需要傳入一個
	 * @param subjects
	 *            只需要在創建test時傳入
	 * @param session
	 * 
	 */
	public static String creatTest(List<SubjectUnit> subjects, String title, HttpSession session) {
		try {
			// 1.生成一个随机数代表本次练习
			String testid = UUID.randomUUID().toString().replaceAll("-", "");
			Map<String, Object> ctest = new HashMap<String, Object>();
			Collections.shuffle(subjects);
			// 2.找的考卷所有的题，做一个随机排序
			ctest.put("subjects", subjects);
			ctest.put("title", title);
			ctest.put("allnum", subjects.size());
			session.setAttribute(testid, ctest);
			return testid;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 抓取測試題
	 * 
	 * @param index
	 * @param testid
	 * @param session
	 * @return STATE[0:正常返回题目，1：session过期，2：参数错误，3题目全部完成],TESTID,INDEX,TITLE,
	 *         ALLNUM,YESNUM,NONUM,YESPEN,MATERIAL,SUBJECTU，TIPTYPETITLE【题类型】
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getTest(Integer index, String testid, MaterialServiceInter materialServiceImpl,
			HttpSession session) {
		Map<String, Object> test = new HashMap<>();
		try {
			Map<String, Object> ctest = (Map<String, Object>) session.getAttribute(testid);
			if (ctest == null) {
				// session过期，重新初始化页面
				test.put("STATE", 1);
				return test;
			}
			test.putAll(ctest);
			test.put("TESTID", testid);
			test.put("TITLE", ctest.get("title"));
			test.put("ALLNUM", ctest.get("allnum"));
			List<SubjectUnit> subjects = (List<SubjectUnit>) ctest.get("subjects");
			if (index == null) {
				if (subjects != null) {
					Collections.shuffle(subjects);
					for (SubjectUnit node : subjects) {
						node.setVal(null);
					}
				}
				index = 1;
			}
			if (index < 1 || subjects == null) {
				// 錯誤重置數據
				test.put("STATE", 2);
				return test;
			}
			// flag=='answer'
			int yesNum = 0;
			int noNum = 0;
			for (SubjectUnit node : subjects) {
				// 把结果放回缓存中，Y正确，N表示错误或者半对
				if (node.getVal() == null) {

				} else if (node.getVal().equals("Y")) {
					yesNum++;
				} else if (node.getVal().equals("N")) {
					noNum++;
				}
			}
			test.put("YESNUM", yesNum);
			test.put("NONUM", noNum);
			test.put("YESPEN", (yesNum + noNum) == 0 ? "0" : (100 * yesNum / (yesNum + noNum)));
			if (index > subjects.size()) {
				// 題目全部完成
				test.put("STATE", 3);
				return test;
			}
			SubjectUnit subjectunit = subjects.get(index - 1);
			if (StringUtils.isNotBlank(subjectunit.getSubject().getMaterialid())) {
				Material material = materialServiceImpl.getMaterialEntity(subjectunit.getSubject().getMaterialid());
				test.put("MATERIAL", material);
			}
			// 返回一道题
			test.put("STATE", 0);
			test.put("SUBJECTU", subjectunit);
			test.put("INDEX", index);
			return test;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 给test设置一个参数
	 * 
	 * @param testid
	 * @param key
	 * 
	 * @param value
	 */
	public static void putAttribute(String testid, String key, Object value, HttpSession session) {
		Map<String, Object> ctest = (Map<String, Object>) session.getAttribute(testid);
		ctest.put(key, value);
	}
}
