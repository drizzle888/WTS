package com.farm.tip.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;

import com.farm.web.tag.BasePathTaget;

public class HtmlEscape extends TagSupport {
	private String text;
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BasePathTaget.class);

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter jspw = this.pageContext.getOut();
		try {
			jspw.print(HtmlUtils.htmlEscape(text));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
