package com.farm.core.page;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * 封装一次页面请求的状态
 * 
 * @author wangdong
 * 
 */
public class RequestMode implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(RequestMode.class);
	private Map<String, String> data;
	/**
	 * 页面类型
	 */
	private int operateType;
	/**
	 * 消息
	 */
	private String message = "";
	/**
	 * 记录集id集合
	 */
	private String ids;
	/**
	 * 当前实体id
	 */
	private String currentKeyid;

	// ------------------------------------------------------------------------------

	/**
	 * 当前主键
	 * 
	 * @return
	 */
	public String getCurrentKeyid() {
		return currentKeyid;
	}

	/**
	 * 设置参数
	 * 
	 * @return
	 */
	public void putParameters(String key, String val) {
		if (this.data == null) {
			this.data = new HashMap<String, String>();
		}
		this.data.put(key, val);
	}

	/**
	 * 获得参数
	 * 
	 * @param key
	 * @return
	 */
	public String getParameters(String key) {
		return this.data.get(key);
	}

	/**
	 * 获得当前业务id
	 * 
	 * @param currentKeyid
	 */
	public void setCurrentKeyid(String currentKeyid) {
		this.currentKeyid = currentKeyid;
	}

	public RequestMode() {

	}

	public RequestMode(OperateType operate) {
		operateType = operate.value;
	}

	public RequestMode(OperateType operate,  String message) {
		operateType = operate.value;
		this.message = message;
	}

	/**
	 * 设置一个PageSet 而且PageSet可以为空
	 * 
	 * @param pageSet
	 *            页面状态对象
	 * @param pageType
	 *            页面类型
	 * @param commitType
	 *            提交状态
	 * @param e
	 *            异常
	 * @return pageSet 页面状态对象
	 */
	public static RequestMode initPageSet(RequestMode pageSet,
			OperateType operateType, Exception e) {
		if (pageSet == null) {
			pageSet = new RequestMode(OperateType.OTHER);
		}
		if (e != null) {
			pageSet.setMessage(e.getMessage());
			log.error(pageSet.getMessage());
		}
		if (operateType != null) {
			pageSet.setOperateType(operateType.value);
		}
		return pageSet;
	}



	/**
	 * 设置状态
	 * 
	 * @param pageType
	 *            页面类型对象
	 * @param commitType
	 *            提交状态对象
	 */
	public void SetVar(OperateType operateType) {
		if (operateType != null)
			this.operateType = operateType.value;
	}

	/**
	 * 设置状态
	 * 
	 * @param pageType
	 *            页面类型对象
	 * @param commitType
	 *            提交状态对象
	 * @param message
	 *            消息内容
	 */
	public void SetVar(OperateType operateType,
			String message) {
		if (operateType != null)
			this.operateType = operateType.value;
		if (message != null)
			this.message = message;
	}

	/**
	 * 获得消息
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}


	/**
	 * 获得id集合
	 * 
	 * @return
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * 设置id集合
	 * 
	 * @param ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
