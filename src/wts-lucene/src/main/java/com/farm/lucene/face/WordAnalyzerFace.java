package com.farm.lucene.face;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


/**分词服务
 * @author Administrator
 *
 */
public class WordAnalyzerFace {
	private static final Logger log = Logger.getLogger(WordAnalyzerFace.class);
	/**
	 * 将文件内容分词
	 * 
	 * @param file
	 * @return List<Object[0：词元，1：词频]>
	 */
	public static List<Object[]> parseWordCaseForSortList(File file) {
		return turnSortList(parseWordCase(file));
	}

	/**
	 * 将html文件内容分词
	 * 
	 * @param file
	 * @return List<Object[0：词元，1：词频]>
	 */
	public static List<Object[]> parseHtmlWordCaseForSortList(String Html) {
		return turnSortList(parseHtmlWordCase(Html));
	}

	/**
	 * 将文件内容分词
	 * 
	 * @param file
	 *            文本文件
	 * @return Map<String词元, Integer词频>
	 */
	public static Map<String, Integer> parseWordCase(File file) {
		IKSegmenter iks = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			iks = new IKSegmenter(new InputStreamReader(new FileInputStream(
					file)), false);
			while (true) {
				Lexeme lex = iks.next();
				if (lex == null) {
					break;
				} else {
					Integer num = map.get(lex.getLexemeText());
					// System.out.println(lex.getLexemeText()+"-------"+lex.getLexemeType());
					if (lex.getLexemeType() >= 4
							&& lex.getLexemeText().length() < 2) {
						continue;
					}
					if (lex.getLexemeType() < 4
							&& lex.getLexemeText().length() < 6) {
						continue;
					}
					if (num == null) {
						map.put(lex.getLexemeText(), 1);
					} else {
						map.put(lex.getLexemeText(), map.get(lex
								.getLexemeText()) + 1);
					}
				}
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return map;
	}

	/**
	 * 将文件内容分词
	 * 
	 * @param file
	 *            文本文件
	 * @return Map<String词元, Integer词频>
	 */
	public static Map<String, Integer> parseHtmlWordCase(String html) {
		IKSegmenter iks = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			iks = new IKSegmenter(new InputStreamReader(
					new ByteArrayInputStream(html.getBytes("UTF-8")),"UTF-8"), false);
			while (true) {
				Lexeme lex = iks.next();
				if (lex == null) {
					break;
				} else {
					Integer num = map.get(lex.getLexemeText());
					// System.out.println(lex.getLexemeText()+"-------"+lex.getLexemeType());
					if (lex.getLexemeType() >= 4
							&& lex.getLexemeText().length() < 2) {
						continue;
					}
					if (lex.getLexemeType() < 4
							&& lex.getLexemeText().length() < 6) {
						continue;
					}
					if (num == null) {
						map.put(lex.getLexemeText(), 1);
					} else {
						map.put(lex.getLexemeText(), map.get(lex
								.getLexemeText()) + 1);
					}
				}
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return map;
	}

	private static List<Object[]> turnSortList(Map<String, Integer> WordCaseMap) {
		Map<String, Integer> map = WordCaseMap;
		List<Object[]> list = new ArrayList<Object[]>();
		for (String key : map.keySet()) {
			Object[] obj = { key, map.get(key) };
			list.add(obj);
		}
		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				Object[] obj1 = (Object[]) o1;
				Object[] obj2 = (Object[]) o2;
				Integer int1 = (Integer) obj1[1];
				Integer int2 = (Integer) obj2[1];
				return -int1.compareTo(int2);
			}
		});
		return list;
	}
}
