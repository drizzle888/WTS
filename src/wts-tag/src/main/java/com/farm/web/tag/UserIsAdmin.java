package com.farm.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.farm.authority.domain.User;
import com.farm.authority.service.UserServiceInter;
import com.farm.util.spring.BeanFactory;

/**
 * 如果当前用户是管理员则展示标签内的内容
 * 
 * @author wangdong
 *
 */
public class UserIsAdmin extends TagSupport {
	private String userid;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static UserServiceInter userIMP = (UserServiceInter) BeanFactory.getBean("userServiceImpl");

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(userid)){
			return SKIP_BODY;
		}
		User user = userIMP.getUserEntity(userid);
		if (user.getType().equals("3")){
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
