package com.farm.web.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;

public class DictionaryOptionTaget extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String isTextValue;
	private String index;
	static final Logger log = Logger.getLogger(DictionaryOptionTaget.class);

	@Override
	public int doEndTag() throws JspException {
		List<Entry<String, String>> list = null;
		list = FarmParameterService.getInstance().getDictionaryList(index);
		JspWriter jspw = this.pageContext.getOut();
		for (Entry<String, String> entry : list) {
			try {
				if (isTextValue.trim().equals("1")) {
					jspw.println("<option value='" + entry.getValue() + "'>"
							+ entry.getValue() + "</option>");
				} else {
					jspw.println("<option value='" + entry.getKey() + "'>"
							+ entry.getValue() + "</option>");
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		return 0;
	}

	@Override
	public int doStartTag() throws JspException {

		return 0;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIsTextValue() {
		return isTextValue;
	}

	public void setIsTextValue(String isTextValue) {
		this.isTextValue = isTextValue;
	}
}
