package com.farm.core.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * ajax请求时的json模型
 * 
 * @author wangdong
 *
 */
public class ViewMode {
	private final static Logger log = Logger.getLogger(ViewMode.class);
	private Map<String, Object> attrs = new HashMap<String, Object>();

	public static ViewMode getInstance() {
		ViewMode obj = new ViewMode();
		obj.attrs.put("STATE", StateType.SUCCESS.value);
		obj.attrs.put("OPERATE", OperateType.OTHER.value);
		return obj;
	}

	/**
	 * 装入json返回值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ViewMode putAttr(String key, String value) {
		attrs.put(key, value);
		return this;
	}

	public ViewMode putAttr(String key, Object value) {
		attrs.put(key, value);
		return this;
	}

	public ViewMode putAttrs(Map<String, Object> map) {
		attrs.putAll(map);
		return this;
	}

	/**
	 * 装入错误
	 * 
	 * @param message
	 *            错误消息
	 * @return
	 */
	public ViewMode setError(String message, Exception exception) {
		if (message == null || message.isEmpty()) {
			message = "该错误无具体信息，可能是一个空指针异常";
		}
		attrs.put("MESSAGE", message);
		attrs.put("STATE", StateType.ERROR.value);
		if (exception != null) {
			log.error(message,exception);
		} else {
			log.error(message);
		}
		return this;
	}
	/**
	 * 装入文字消息
	 * 
	 * @param message
	 *            错误消息
	 * @return
	 */
	public ViewMode setMessage(String message) {
		if (message == null || message.isEmpty()) {
			message = "该错误无具体信息，可能是一个空指针异常";
		}
		attrs.put("MESSAGE", message);
		return this;
	}
	

	public ViewMode setOperate(OperateType type) {
		attrs.put("OPERATE", type.value);
		return this;
	}

	/**
	 * 返回map格式json
	 * 
	 * @return
	 */
	public Map<String, Object> returnObjMode() {
		return attrs;
	}

	/**
	 * 返回spring的ModelAndView(如jsp)
	 * 
	 * @param path
	 * @return
	 */
	public ModelAndView returnModelAndView(String path) {
		log.debug(path);
		return new ModelAndView(path, this.returnObjMode());
	}

	/**
	 * 重定向到URL地址
	 * 
	 * @param path
	 * @return
	 */
	public ModelAndView returnRedirectUrl(String path) {
		String paras = null;
		for (String name : attrs.keySet()) {
			String val = attrs.get(name).toString();
			if (paras == null) {
				paras = name + "=" + val;
			} else {
				paras = paras + "&" + name + "=" + val;
			}
		}
		if (paras == null) {
			paras = "";
		}
		if (path.indexOf("?") > 0) {
			path = path + "&" + paras;
		} else {
			if (!paras.equals("")) {
				path = path + "?" + paras;
			}
		}
		return new ModelAndView("redirect:" + path);
	}

	/**
	 * 重定向到URL地址(不带入参path以外的参数)
	 * 
	 * @param path
	 * @return
	 */
	public ModelAndView returnRedirectOnlyUrl(String path) {
		return new ModelAndView("redirect:" + path);
	}

	/**
	 * 返回文本格式json(暂未实现)
	 * 
	 * @return
	 */
	@Deprecated
	public String returnStrMode() {
		// TODO
		return "String";
	}

	public static List<?> returnListObjMode(List<?> list) {
		return list;
	}

}
