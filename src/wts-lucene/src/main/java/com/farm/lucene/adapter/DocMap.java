package com.farm.lucene.adapter;

import java.util.HashMap;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

/**
 * 封装被索引的文档为索引可用格式
 * 
 * @author wangdong
 * 
 */
public class DocMap extends HashMap<String, Object[]> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6469151698334975041L;

	public DocMap(String id) {
		super();
		Object[] obj = { id, Store.YES, Index.ANALYZED };
		super.put("ID", obj);
	}

	@Deprecated
	public Object[] get(Object key) {
		return super.get(key);
	}

	@Deprecated
	public Object[] put(String key, Object[] value) {
		return super.get(key);
	}

	/**
	 * 插入一个文档的属性
	 * 
	 * @param feild
	 *            字段名
	 * @param value
	 *            字段值
	 * @param store
	 *            存储配置
	 * @param index
	 *            索引状态
	 */
	public void put(String feild, String value, Store store, Index index) {
		Object[] obj = { value, store, index };
		super.put(feild, obj);
	}

	/**
	 * 获得一个文档属性的值
	 * 
	 * @param feild
	 * @return
	 */
	public String getValue(String feild) {
		Object[] obj = super.get(feild);
		if (obj == null) {
			return null;
		}
		return (String) obj[0];
	}

	/**
	 * 获得一个文档属性的存储配置
	 * 
	 * @param feild
	 * @return
	 */
	public Store getStore(String feild) {
		Object[] obj = super.get(feild);
		if (obj == null) {
			return null;
		}
		return (Store) obj[1];
	}

	/**
	 * 获得一个文档属性的索引配置
	 * 
	 * @param feild
	 * @return
	 */
	public Index getIndex(String feild) {
		Object[] obj = super.get(feild);
		if (obj == null) {
			return null;
		}
		return (Index) obj[2];
	}

	/**
	 * 获得信息
	 * 
	 * @return
	 */
	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		for (String key : this.keySet()) {
			String value = this.get(key)[0]==null?"":this.get(key)[0].toString();
			if (value.length() > 50) {
				value = value.substring(0, 50);
			}
			sb.append(key + ":" + value + "|");
		}
		return sb.toString();
	}
}
