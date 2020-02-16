package com.farm.web.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;
import com.farm.util.validate.ValidUtils;

/**
 * @author Administrator
 * 
 */
public class RunDictionaryByConstantKey extends TagSupport {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(ParameterTaget.class);
	private String val;
	private String key;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter jspw = this.pageContext.getOut();
		try {
			key = FarmParameterService.getInstance().getParameter(
					key);
			key = key.replaceAll("，", ",").replaceAll("、", ",");
			String[] ditEntrys = key.split(",");
			Map<String, String> dicMap = new HashMap<String, String>();
			for (String entryStr : ditEntrys) {
				if (!ValidUtils.isEmptyString(entryStr)) {
					String[] entryA = entryStr.replaceAll("：", ":").split(":");
					if (entryA.length == 2
							&& !ValidUtils.isEmptyString(entryA[0])
							&& !ValidUtils.isEmptyString(entryA[1])) {
						dicMap.put(entryA[0].trim(), entryA[1].trim());
					}
				}
			}
			jspw.print(dicMap.get(val.trim()));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
