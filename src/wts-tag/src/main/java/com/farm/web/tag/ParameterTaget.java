package com.farm.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.core.auth.domain.LoginUser;
import com.farm.parameter.FarmParameterService;
import com.farm.web.constant.FarmConstant;

public class ParameterTaget extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(ParameterTaget.class);
	private String key;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		LoginUser currentUser = (LoginUser) request.getSession().getAttribute(
				FarmConstant.SESSION_USEROBJ);
		JspWriter jspw = this.pageContext.getOut();
		try {
			if (currentUser == null) {
				jspw.print(FarmParameterService.getInstance().getParameter(
						key.trim()));
			} else {
				jspw.print(FarmParameterService.getInstance().getParameter(
						key.trim(), currentUser.getId()));
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
