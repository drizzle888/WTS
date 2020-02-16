package com.farm.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;

/**
 * @author Administrator
 * 
 */
public class RunDictionaryByKey extends TagSupport {
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
			jspw.print(FarmParameterService.getInstance()
					.getDictionary(key.trim()).get(val.trim()));
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
