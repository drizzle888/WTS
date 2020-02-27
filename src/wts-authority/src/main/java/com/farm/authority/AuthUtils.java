package com.farm.authority;

import javax.servlet.http.HttpSession;

import com.farm.authority.domain.Organization;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.auth.domain.LoginUser;
import com.farm.util.spring.BeanFactory;
import com.farm.web.constant.FarmConstant;

public class AuthUtils {
	public static Organization getCurrentOrganization(HttpSession session) {
		UserServiceInter users = (UserServiceInter) BeanFactory.getBean("userServiceImpl");
		LoginUser user = (LoginUser) session.getAttribute(FarmConstant.SESSION_USEROBJ);
		return users.getOrg(user.getId());
	}
}
