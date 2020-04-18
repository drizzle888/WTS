package com.farm.wcp.userfunc;

import java.util.List;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.User;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.util.spring.BeanFactory;

/**
 * 客户定制功能，通过组织机构别名和用户姓名进行登陆
 * 
 * @author macpl
 *
 */
public class JlscUtils {

	public static String getUserLoginName(String orgBNAMEAndUserName) {
		if (orgBNAMEAndUserName == null) {
			return orgBNAMEAndUserName;
		}
		orgBNAMEAndUserName = orgBNAMEAndUserName.trim().replaceAll(" ", "");
		OrganizationServiceInter orgServer = (OrganizationServiceInter) BeanFactory.getBean("organizationServiceImpl");
		UserServiceInter userServer = (UserServiceInter) BeanFactory.getBean("userServiceImpl");
		List<String> orgComments = orgServer.getAllOrgComments();
		// 1判断是否用了组织机构别名开头
		String username = null;
		Organization org = null;
		for (String orgComment : orgComments) {
			if (orgBNAMEAndUserName.startsWith(orgComment)) {
				username = orgBNAMEAndUserName.replace(orgComment, "");
				// 1.1查询组织机构
				List<Organization> orgs = orgServer.getOrganizationByComments(orgComment);
				if (orgs.size() > 1) {
					throw new RuntimeException("別名对应多个组织机构：" + orgComment);
				}
				if (orgs.size() <= 0) {
					throw new RuntimeException("別名未对应到组织机构：" + orgComment);
				} else {
					org = orgs.get(0);
				}
				break;
			}
		}
		if (username == null) {
			// 非组织机构+用户名登陆
			return null;
		}
		// 2如果是已组织机构开头则通过用户名查询用户
		List<User> users = userServer.getUsersByName(username);
		if (users.size() == 0) {
			throw new RuntimeException("用户 " + username + "不存在");
		}
		// 3比对所有用户是否在此组织机构下
		String usernames = null;
		for (User user : users) {
			Organization userOrg = userServer.getUserOrganization(user.getId());
			if (userOrg.getId().equals(org.getId())) {
				// 4返回用户登陆名进行登陆
				return user.getLoginname();
			}
			usernames = usernames == null ? user.getName() : (usernames + user.getName());
		}
		// 未用户和组织机构不匹配
		throw new RuntimeException("用户(" + usernames + ")和机构(" + org.getName() + ")不匹配");
	}

}
