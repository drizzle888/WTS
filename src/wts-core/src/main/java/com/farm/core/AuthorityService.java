package com.farm.core;

import java.util.List;
import java.util.Set;

import com.farm.core.auth.domain.AuthKey;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.auth.exception.LoginUserNoAuditException;
import com.farm.core.auth.exception.LoginUserNoExistException;

/**
 * 权限服务接口
 * 
 * @author wangdong
 * @version 2014-12
 * 
 */
public interface AuthorityService {

	/**
	 * 验证用户是否合法
	 * 
	 * @param loginName
	 *            用户登录名
	 * @param password
	 *            用户密码
	 * @return
	 * @throws LoginUserNoAuditException 
	 */
	public boolean isLegality(String loginName, String password) throws LoginUserNoExistException, LoginUserNoAuditException;

	/**
	 * 获得用户对象
	 * 
	 * @param loginName
	 * @return
	 */
	public LoginUser getUserByLoginName(String loginName);

	/**
	 * 获得用户岗位（用于工作流等应用中的对应KEY）
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserPostKeys(String userId);

	/**
	 * 获得用户组织机构KEY
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserOrgKey(String userId);

	/**
	 * 获得用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public LoginUser getUserById(String userId);

	/**
	 * 获得用户权限关键字
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> getUserAuthKeys(String userId);

	/**
	 * 获得key对象(用于检查key权限)
	 * 
	 * @param key
	 * @return
	 */
	public AuthKey getAuthKey(String key);

	/**
	 * 获得用户的菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<WebMenu> getUserMenu(String userId);

	/**
	 * 登录成功时会调用此方法
	 * 
	 * @param userId
	 * @return 上一次登录时间
	 */
	public String loginHandle(String userId);

	/**
	 * 通过外部用户id获得本地用户(如果没有则在关联表中创建对照关系，默认先不绑定用户)
	 * 
	 * @param outuserid
	 *            用户id
	 * @param name
	 *            用户名称（可不填）
	 * @param content
	 *            来源备注(可不填)
	 * @return
	 */
	public LoginUser getUserByOutUserId(String outuserid, String name, String content);

}
