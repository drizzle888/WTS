package com.farm.core.sql.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 查询条件的封装类，配合DataQuery使用
 * 
 * @author 王东
 * @date 2012-12-30
 */
public class DBRule {
	private String key;// where key
	private Object value;// where value
	private String comparaSign;// like,}��% = > < >= <=
	private List<DBRule> rules = new ArrayList<DBRule>();

	/**
	 * 将一个条件对象添加到条件序列中
	 * 
	 * @param ruleList
	 * @param key
	 * @param value
	 * @param comString
	 * @return
	 */
	public static List<DBRule> addRule(List<DBRule> ruleList, String key, String value, String comString) {
		DBRule cRule = new DBRule(key, value, comString);
		ruleList.add(cRule);
		return ruleList;
	}

	/**
	 * 将一个条件对象添加到条件序列中
	 * 
	 * @param key
	 * @param value
	 * @param comString
	 * @return
	 */
	public DBRule addRule(String key, String value, String comString) {
		DBRule cRule = new DBRule(key, value, comString);
		rules.add(cRule);
		return this;
	}

	/**
	 * 获得条件序列
	 * 
	 * @return
	 */
	public List<DBRule> getDBRules() {
		return rules;
	}

	/**
	 * 将条件序列转义为条件字符串
	 * 
	 * @param ruleList
	 *            条件序列
	 * @return
	 */
	public static String makeWhereStr(List<DBRule> ruleList) {
		StringBuffer str = new StringBuffer();
		for (DBRule node : ruleList) {
			str.append(node.getThisLimit());
		}
		return str.toString();
	}

	/**
	 * 构造一个查询条件
	 * 
	 * @param key
	 *            字段名
	 * @param value
	 *            字段值
	 * @param comString
	 *            匹配类型 like(-like坐标自由匹配) IS NOT = > < >= <=
	 */
	public DBRule(String key, Object value, String comString) {
		comString = StringEscapeUtils.unescapeHtml(comString);
		DataQuerys.wipeVirus(key);
		DataQuerys.wipeVirus(value.toString());
		DataQuerys.wipeVirus(comString);
		this.key = key.trim().toUpperCase();
		this.value = value;
		this.comparaSign = comString.trim().toUpperCase();
		this.rules.add(this);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value.toString();
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComparaSign() {
		return comparaSign;
	}

	public void setComparaSign(String comparaSign) {
		this.comparaSign = comparaSign;
	}

	public String getThisLimit() {
		StringBuffer where_ = new StringBuffer();
		comparaSign = comparaSign.toUpperCase();
		if (key != null && value != null && comparaSign != null) {
			where_.append("  AND  ");
			where_.append(key);
			if (comparaSign.indexOf("LIKE") >= 0) {
				where_.append(" ");
				where_.append("LIKE");
			} else {
				where_.append(" ");
				where_.append(comparaSign);
			}
			where_.append(expendVal());
			where_.append(" ");
		}
		return where_.toString();
	}

	private String expendVal() {
		StringBuffer valStr = new StringBuffer();
		if (comparaSign.equals("LIKE")) {
			valStr.append(" '%");
			valStr.append(value.toString());
			valStr.append("%'");
		} else if (comparaSign.equals("-LIKE")) {
			valStr.append(" '%");
			valStr.append(value.toString());
			valStr.append("'");
		} else if (comparaSign.equals("LIKE-")) {
			valStr.append(" '");
			valStr.append(value.toString());
			valStr.append("%'");
		} else if (comparaSign.equals("IS NOT")) {
			valStr.append(" ");
			valStr.append(value.toString());
			valStr.append(" ");
		} else {
			if (value instanceof String) {
				valStr.append(" '");
				valStr.append(value.toString());
				valStr.append("'");
			} else if (value instanceof BigDecimal) {
				valStr.append(" ");
				valStr.append(value.toString());
				valStr.append("");
			} else {
				valStr.append(" ");
				valStr.append(value.toString());
				valStr.append("");
			}
		}
		return valStr.toString();
	}
}
