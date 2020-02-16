package com.farm.web.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;

public class DictionaryHandle extends SimpleTagSupport {
	private String var;
	private String key;
	private static final Logger log = Logger.getLogger(DictionaryHandle.class);

	@Override
	public void doTag() throws JspException, IOException {
		List<Entry<String, String>> list = null;
		list = FarmParameterService.getInstance().getDictionaryList(key);
		// 进行迭代
		for (Entry<String, String> node : list) {
			Map<String, String> nodeMap = new HashMap<>();
			nodeMap.put("key", node.getKey());
			nodeMap.put("KEY", node.getKey());
			nodeMap.put("value", node.getValue());
			nodeMap.put("VALUE", node.getValue());
			getJspContext().setAttribute(var, nodeMap);
			// 输出标签体
			getJspBody().invoke(null);
		}
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
