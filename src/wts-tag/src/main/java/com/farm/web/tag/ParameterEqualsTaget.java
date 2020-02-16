package com.farm.web.tag;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.farm.core.auth.domain.LoginUser;
import com.farm.parameter.FarmParameterService;
import com.farm.web.constant.FarmConstant;

public class ParameterEqualsTaget extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(ParameterEqualsTaget.class);
	private String key;
	private String val;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) super.pageContext
				.getRequest();
		String pval = null;
		LoginUser user = (LoginUser) request.getSession().getAttribute(
				FarmConstant.SESSION_USEROBJ);
		if (user == null) {
			pval = FarmParameterService.getInstance().getParameter(key);
		} else {
			pval = FarmParameterService.getInstance().getParameter(key,
					user.getId());
		}
		// 显示
		if (pval != null && pval.equals(val)) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
