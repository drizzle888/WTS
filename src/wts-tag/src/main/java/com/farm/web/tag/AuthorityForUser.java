package com.farm.web.tag;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.AuthorityService;
import com.farm.core.auth.domain.AuthKey;
import com.farm.web.constant.FarmConstant;

public class AuthorityForUser extends TagSupport {
	private String actionName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) super.pageContext
				.getRequest();
		Set<String> usraction = ((Set<String>) request.getSession()
				.getAttribute(FarmConstant.SESSION_USERACTION));
		AuthorityService  farmAuthService= FarmAuthorityService.getInstance();
		AuthKey authKey = farmAuthService.getAuthKey(actionName);
		// 权限未注册或用户有权限或权限不检查
		if (authKey == null || !authKey.isCheck()
				|| (usraction.contains(actionName) && authKey.isUseAble())) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}
