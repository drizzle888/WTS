package com.farm.core.sql.result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * 结果集合封装类,提供分页支持，对结果集合进行常见操作
 * 
 * @author 王东 2010年底编写 2012-08-17 增加注释
 */
public class DataResult {
	private static final Logger log = Logger.getLogger(DataResult.class);
	private long runtime;
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	/**
	 * 每页记录数
	 */
	private int pageSize = 10;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 总记录数
	 */
	private int totalSize;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 结果集合
	 */
	private List<Map<String, Object>> resultList;
	/**
	 * 结果集合
	 */
	private List<List<Object>> resultListArray;
	/**
	 * 创建时间
	 */
	private Date ctime;
	/**
	 * 结果集字段名
	 */
	private List<String> titles;

	private String message;// 页面消息
	// 排序字段
	private String sortTitleText;
	// 排序类型
	private String sortTypeText;
	// 开始页（显示5页）
	private int startPage;
	// 结束页（显示5页）
	private int endPage;

	/**
	 * 私有构造方法
	 */
	private DataResult() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DataResult getInstance() {
		return getInstance(new ArrayList(), 0, 1, 0);
	}

	/**
	 * 获得类实例
	 * 
	 * @param data
	 *            记录集合
	 * @param _totalSize
	 *            总记录数
	 * @param _currentPage
	 *            当前页
	 * @param _PageSize
	 *            每页记录数
	 * @return 结果集合封装对象
	 */
	public static DataResult getInstance(List<Map<String, Object>> data, int _totalSize, int _currentPage,
			int _PageSize) {
		DataResult result = new DataResult();
		result.resultList = data;
		result.totalSize = _totalSize;
		result.currentPage = _currentPage;
		result.pageSize = _PageSize;
		if (result.totalSize == 0) {
			result.totalPage = 0;
			result.startPage = 0;
			result.endPage = 0;
			return result;
		}
		result.totalPage = result.totalSize / result.pageSize + 1;
		if (result.totalSize % result.pageSize == 0) {
			result.totalPage = result.totalPage - 1;
		}
		// 计算起始页
		result.startPage = result.currentPage - 2;
		// 计算结束页
		result.endPage = result.currentPage + 2;
		if (result.startPage < 1) {
			int cha = 1 - result.startPage;
			result.startPage = 1;
			result.endPage = result.endPage + cha;
		}
		if (result.endPage > result.totalPage) {
			result.endPage = result.totalPage;
		}
		return result;
	}

	/**
	 * 加载列表类型的结果集合 resultListArray 保存一个数组类型的数据副本
	 */
	public void LoadListArray() {
		resultListArray = new ArrayList<List<Object>>();
		for (Map<String, Object> node : this.getResultList()) {
			List<Object> list = new ArrayList<Object>();
			for (String key : titles) {
				list.add(node.get(key));
			}
			resultListArray.add(list);
		}
	}

	/**
	 * 合并列（在结果集合中增加标志位（titleKey + "ROWSPAN"）如CIDROWSPAN,其值： 0代表已处理被合并
	 * n>0代表合并的行数） 合并逻辑：titleKey字段值相同则合并
	 * 
	 * @param titleKey
	 * @return
	 */
	public DataResult mergeCells(String titleKey) {
		String key = titleKey + "ROWSPAN";
		for (int i = 0; i < resultList.size(); i++) {
			// System.out.println(list.get(i).get("CNAME"));
			Object cellsVal = resultList.get(i).get(titleKey);// 合并内容
			Object rowspan = resultList.get(i).get(key);// 合并行数
			if (rowspan == null) {
				// 合并信息放入rowspan中null代表未处理0代表已处理被合并 n>0代表已处理已经合并
				int rowspanNum = 0;
				for (int n = i; n < resultList.size(); n++) {
					Object tempVal = resultList.get(n).get(titleKey);
					if (tempVal != null && tempVal.equals(cellsVal)) {
						rowspanNum++;
						resultList.get(i).put(key, 0);
					} else {
						break;
					}
				}
				// 设置合并数
				resultList.get(i).put(key, rowspanNum);
			} else {
				continue;
			}
		}
		return this;
	}

	/**
	 * 加载列表类型的结果集合 resultListArray 保存一个数组类型的数据副本
	 */
	public void LoadListArray(String titless) {
		resultListArray = new ArrayList<List<Object>>();
		for (Map<String, Object> node : this.getResultList()) {
			List<Object> list = new ArrayList<Object>();
			for (String key : DataResults.getTitles(titless)) {
				list.add(node.get(key));
			}
			resultListArray.add(list);
		}
	}

	/**
	 * 设置结果集合的消息参数
	 * 
	 * @param dataResult
	 *            结果集合对象
	 * @param messager
	 *            消息文本
	 * @return 结果集合对象
	 */
	public static DataResult setMessager(DataResult dataResult, String messager) {
		if (dataResult == null) {
			dataResult = new DataResult();
			dataResult.resultList = new ArrayList<Map<String, Object>>();
		}
		dataResult.setMessage(messager);
		return dataResult;
	}

	/**
	 * 打印结果集合内容
	 */
	public void printDataInfo() {
		System.out.println(
				"############       debug开始         #############################################################");
		DataResults.printMaps(this.getResultList());
		System.out.println("当前页:" + this.getCurrentPage());
		System.out.println("全部页:" + this.getTotalPage());
		System.out.println("全部记录:" + this.getTotalSize());
		System.out.println("每页数:" + this.getPageSize());
		System.out.println("消息:" + this.message);
		System.out.println(
				"############       debug结束         #############################################################");
	}

	/**
	 * 对结果集合中某个字段值进行替换，用于数据字典的翻译
	 * 
	 * @param dictionary
	 *            字典map
	 * @param title
	 *            翻译字段名 如：TYPE
	 */
	public DataResult runDictionary(Map<String, String> dictionary, String title) {
		for (Map<String, Object> node : resultList) {
			String key = String.valueOf(node.get(title));
			Object value = dictionary.get(key);
			if (value != null) {
				node.put(title, value);
			}
		}
		return this;
	}

	/**
	 * 对结果集合中某个字段值进行替换，用于数据字典的翻译
	 * 
	 * @param str
	 *            字典描述字符串 "1:可用,2:禁用"
	 * @param title
	 *            翻译字段名 如：TYPE
	 */
	public DataResult runDictionary(String str, String title) {
		String[] mapStr = str.trim().split(",");
		Map<String, String> dictionary = new HashMap<String, String>();
		for (String keyValue : mapStr) {
			String[] node = keyValue.split(":");
			try {
				dictionary.put(node[0], node[1]);
			} catch (Exception e) {
				throw new RuntimeException(e + ",请检查字典项：'" + keyValue + "'");
			}
		}
		runDictionary(dictionary, title);
		return this;
	}

	/**
	 * 翻译结果集合中的时间格式 yyyyMMddHHmmss
	 * 前提是数据库中的字段值必须是如201208220408的格式yyyyMMddHHmmss支持14或12位的
	 * 
	 * @param title
	 *            翻译字段名 如：TYPE
	 * @param yyyyMMddHHmmss
	 *            翻译成的格式
	 */
	public void runformatTime(String title, String yyyyMMddHHmmss) {
		List<Map<String, Object>> list = this.getResultList();
		for (Map<String, Object> node : list) {
			try {
				String time = null;
				if (node.get(title) instanceof java.sql.Date) {
					java.util.Date d = new java.util.Date(((java.sql.Date) node.get(title)).getTime());
					SimpleDateFormat ormat = new SimpleDateFormat("yyyyMMdd");
					time = ormat.format(d);
				} else {
					time = (String) node.get(title);
				}
				if (time == null || time.trim().length() <= 0) {
					continue;
				}
				if(time.indexOf("-")+time.indexOf(":")>0){
					//只要有-和：就算是已经被转义过的时间，就不用再转换了
					continue;
				}
				if (12 == time.length()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
					SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
					Date date = sdf.parse(time);
					node.put(title, newSdf.format(date));
				} else {
					try {
						time = (time + "00000000000000").substring(0, 14);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
						Date date = sdf.parse(time);
						node.put(title, newSdf.format(date));
					} catch (ParseException e) {
						node.put(title, null);
					}
				}
			} catch (Exception e) {
				log.warn(e);
			}
		}
	}

	/**
	 * 从一个结果集合中去掉另一个结果集合(去重字段和id字段必须为字符串)
	 * 
	 * @param result
	 *            结果集合
	 * @param mapTitle
	 *            除去时依照的重复字段
	 * @return 结果集合对象
	 */
	public DataResult removeDataResult(DataResult result, String mapTitle) {
		Set<String> keyset = new HashSet<String>();
		// 所有已有的重复字段
		for (Map<String, Object> node : result.getResultList()) {
			keyset.add((String) node.get(mapTitle));
		}
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> node : resultList) {
			String key = (String) node.get(mapTitle);
			if (key != null && !"".equals(key)) {
				if (!keyset.add(key)) {
					// resultList.remove(node);
					temp.add(node);
					totalSize = totalSize - 1;
				}
			}
		}
		for (Map<String, Object> node : temp) {
			resultList.remove(node);
		}
		return result;
	}

	/**
	 * 按字段去重
	 * 
	 * @param title
	 *            去重字段名 如：TYPE
	 * @return 结果集合对象
	 */
	public DataResult runDistinct(String title) {
		List<Map<String, Object>> list = this.getResultList();
		List<Map<String, Object>> listresult = new ArrayList<Map<String, Object>>();
		Set<String> keySet = new HashSet<String>();
		for (Map<String, Object> node : list) {
			String key = node.get(title).toString();
			if (keySet.add(key)) {
				listresult.add(node);
			}
		}
		this.setResultList(listresult);
		return this;
	}

	/**
	 * 获得每页记录数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获得当前页
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}


	/**
	 * 获得结果集合
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}

	/**
	 * 获得结果集消息
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置结果集消息
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 设置结果集中数据对象
	 * 
	 * @param resultList
	 */
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 为数据添加一个字段
	 * 
	 * @param titel
	 *            要添加的字段名
	 * @param value
	 *            要填充的值
	 */
	public void addTitle(String titel, String value) {
		for (Map<String, Object> node : resultList) {
			node.put(titel, value);
		}
	}

	/**
	 * 获得LIst类型的数据封装
	 * 
	 * @return
	 */
	public List<List<Object>> getResultListArray() {
		return resultListArray;
	}

	/**
	 * 填充LIst类型的数据封装
	 * 
	 * @param resultListArray
	 */
	public void setResultListArray(List<List<Object>> resultListArray) {
		this.resultListArray = resultListArray;
	}

	/**
	 * 获得记录总数
	 * 
	 * @return
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * 获得数据总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 获得字段名序列
	 * 
	 * @return
	 */
	public List<String> getTitles() {
		return titles;
	}

	/**
	 * 设置字段名序列
	 * 
	 * @param titles字段名序列
	 */
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	/**
	 * 获得排序字段
	 * 
	 * @return
	 */
	public String getSortTitleText() {
		return sortTitleText;
	}

	public void setSortTitleText(String sortTitleText) {
		this.sortTitleText = sortTitleText;
	}

	/**
	 * 获得创建时间
	 * 
	 * @return
	 */
	public Date getCtime() {
		return ctime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @return
	 */
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	/**
	 * 获得排序类型字段
	 * 
	 * @return
	 */
	public String getSortTypeText() {
		return sortTypeText;
	}

	/**
	 * 设置排序类型字段
	 * 
	 * @param sortTypeText
	 */
	public void setSortTypeText(String sortTypeText) {
		this.sortTypeText = sortTypeText;
	}

	/**
	 * 获得一个对象集合
	 * 
	 * @param class1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getObjectList(@SuppressWarnings("rawtypes") Class class1) {
		@SuppressWarnings("rawtypes")
		List list = new ArrayList<Object>();
		for (Map<String, Object> node : getResultList()) {
			Object obj = null;
			try {
				obj = class1.newInstance();

				for (Entry<String, Object> field : node.entrySet()) {
					BeanUtils.setProperty(obj, field.getKey().toLowerCase(), field.getValue());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			list.add(obj);
		}
		return list;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public long getRuntime() {
		return runtime;
	}

	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}

	public void runHandle(ResultsHandle handle) {
		for (Map<String, Object> row : this.getResultList()) {
			handle.handle(row);
		}
	}
}
