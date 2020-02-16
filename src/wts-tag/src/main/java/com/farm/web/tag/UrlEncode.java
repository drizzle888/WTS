package com.farm.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class UrlEncode extends TagSupport {
	private String val;
	static final Logger log = Logger.getLogger(UrlEncode.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter jspw = this.pageContext.getOut();
		try {
			jspw.print(java.net.URLEncoder.encode(java.net.URLEncoder.encode(val, "utf-8"), "utf-8"));
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

}
