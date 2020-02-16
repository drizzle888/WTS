package com.farm.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;

public class DefaultIndexPageTaget extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(DefaultIndexPageTaget.class);

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		String Path = getDefaultIndexPage();
		JspWriter jspw = this.pageContext.getOut();
		try {
			jspw.print(Path);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	/**
	 * 获得系统默认登录页面
	 * 
	 * @return
	 */
	public static String getDefaultIndexPage() {
		return WebUtils.getDefaultIndexPage(FarmParameterService.getInstance());
	}
}
