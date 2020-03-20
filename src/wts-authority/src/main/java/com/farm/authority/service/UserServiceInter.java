package com.farm.authority.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.farm.authority.domain.Action;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.domain.User;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.auth.domain.WebMenu;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

/* *
 *功能：用户服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141119144919
 *说明：
 */
public interface UserServiceInter {
	/**
	 * 新增实体管理实体(密码为空时，系统取默认密码)
	 * 
	 * @param entity
	 */
	public User insertUserEntity(User entity, LoginUser user, String orgId, String postIds);

	/**
	 * 新增实体管理实体(密码为空时，系统取默认密码)
	 * 
	 * @param entity
	 *            用户实例
	 * @param user
	 *            操作人
	 * @return
	 */
	public User insertUserEntity(User entity, LoginUser user);

	/**
	 * 新增实体管理实体
	 * 
	 * @param name
	 *            用户名
	 * @param loginname
	 *            登录名
	 * @return
	 */
	public User insertUserEntity(String name, String loginname, String password);

	/**
	 * @param name
	 *            用户名
	 * @param loginname
	 *            登录名
	 * @param password
	 *            密碼
	 * @param imgid
	 *            頭像
	 * @return
	 */
	public User insertUserEntity(String name, String loginname, String password, String imgid);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public User editUserEntity(User entity, LoginUser user, String orgId, String postIds);

	/**
	 * 修改实体
	 * 
	 * @param entity
	 * @param user
	 * @return
	 */
	public User editUserEntity(User entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteUserEntity(String id, LoginUser user);

	/**
	 * 修改用户类型
	 * 
	 * @param entity
	 */
	public void editUserType(String userid, String type, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public User getUserEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createUserSimpleQuery(DataQuery query, LoginUser currentUser);

	/**
	 * 验证登录名是否重复
	 * 
	 * @param loginname
	 *            登录名
	 * @param userId
	 *            用户id（修改时判断是不是本用户的登录名，是自己的不算重复）
	 * @return
	 */
	public boolean validateIsRepeatLoginName(String loginname, String userId);

	/**
	 * 初始化用户密码
	 * 
	 * @param userid
	 * @param currentUser
	 */
	public void initDefaultPassWord(String userid, LoginUser currentUser);

	/**
	 * 获得用户
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName);

	/**
	 * 设置用户登录时间
	 * 
	 * @param userId
	 * @return 上一次登录时间
	 */
	public String setLoginTime(String userId);

	/**
	 * 查询岗位用户
	 * 
	 * @param query
	 * @return
	 */
	public DataQuery createUserPostQuery(DataQuery query);

	/**
	 * 获得用户所有的权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<Action> getUserActions(String userId);

	/**
	 * 获得用户菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<WebMenu> getUserMenus(String userId);

	/**
	 * 获得用户岗位序列
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserPostIds(String userId);

	/**
	 * 获得用户岗位序列
	 * 
	 * @param userId
	 * @return
	 */
	public List<Post> getUserPosts(String userId);

	

	/**
	 * 获得用户的组织机构
	 * 
	 * @param userId
	 * @return
	 */
	public Organization getUserOrganization(String userId);

	/**
	 * 用户注册(请)
	 * 
	 * @param user
	 * @param orgid
	 * @return
	 */
	public User registUser(User user, String orgid);

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	public User registUser(User user);

	/**
	 * 获取组织机构
	 * 
	 * @param userid
	 * @return Organization
	 */
	public Organization getOrg(String userid);

	/**
	 * 获取岗位
	 * 
	 * @param userid
	 * @return List<Post>
	 */
	public List<Post> getPost(String userid);

	/**
	 * 获取机构下的用户
	 * 
	 * @param query
	 * @return DataQuery
	 */
	public DataQuery createOrgUserQuery(DataQuery query);

	/**
	 * 更新当前登录用户信息
	 * 
	 * @param id
	 * @param name
	 * @param photoid
	 * @param orgid
	 */
	public void editCurrentUser(String id, String name, String photoid, String orgid);

	/**
	 * 编辑当前登录用户密码
	 * 
	 * @param userid
	 * @param oldPassword
	 * @param newPassword
	 */
	public void editUserPassword(String userid, String oldPassword, String newPassword);

	/**
	 * 编辑当前登录用户密码
	 * @param userid
	 * @param newPassword
	 */
	public void editUserPassword(String userid, String newPassword);
	/**
	 * 修改密码
	 * 
	 * @param loginname
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public boolean editUserPasswordByLoginName(String loginname, String oldPassword, String newPassword);
	/**
	 * 校验当前登录用户密码是否有效
	 * 
	 * @param userid
	 * @param password
	 * @return
	 */
	public boolean validCurrentUserPwd(String userid, String password);

	/**
	 * 系统当前可用用户数
	 * 
	 * @return
	 */
	public Integer getUsersNum();

	/**
	 * 完成用户导入 v1.0 zhanghc 2016年8月4日下午2:37:50
	 * 
	 * @param file
	 * @param currentUser
	 *            void
	 */
	public void doUserImport(MultipartFile file, LoginUser currentUser);

	/**
	 * 查询用户，通过用户名或者组织机构名称
	 * 
	 * @param word
	 *            查询条件，用户名称或者组织机构名称
	 * @param pagenum
	 *            当前查询页
	 * @return
	 */
	public DataResult searchUserByUsernameAndOrgname(String word, Integer pagenum);

	/**
	 * 为用户设置组织机构
	 * 
	 * @param userid
	 * @param orgid
	 */
	public void setUserOrganization(String userid, String orgid, LoginUser currentUser);

	/**
	 * 为用户设置岗位
	 * 
	 * @param userid
	 *            用户id
	 * @param postids
	 *            岗位id，多个岗位用逗号分隔
	 */
	public void setUserPost(String userid, String postids, LoginUser currentUser);

	/**
	 * 获得所有超级用户
	 * 
	 * @return
	 */
	public List<User> getSuperUsers();

	/**
	 * 获得所有超级用户
	 * 
	 * @return
	 */
	public List<String> getSuperUserids();

	/**
	 * 修改用户状态
	 * 
	 * @param userid
	 *            用户id
	 * @param state
	 *            用户状态 0:禁用,1:可用,2:删除,3.待审核
	 * @return
	 */
	public User editUserState(String userid, String state, LoginUser currentUser);

	/**
	 * 一个用户空间被访问
	 * 
	 * @param userid
	 * @param currentUser
	 * @param currentIp
	 */
	public void visitUserHomePage(String userid, LoginUser currentUser, String currentIp);

	/**同步遠程用戶到本地
	 * @param remoteUser
	 * @return 如果用户之前没有，被新增则返回true
	 */
	public User syncRemoteUser(User remoteUser);
}