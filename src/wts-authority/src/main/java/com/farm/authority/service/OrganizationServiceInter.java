package com.farm.authority.service;

import java.util.List;
import java.util.Map;

import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DataQuery;
import com.farm.web.easyui.EasyUiTreeNode;

/* *
 *功能：组织机构服务类接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
public interface OrganizationServiceInter {
	/**
	 * 新增实体管理实体(需要填入的参数有：备注、名称、状态1、排序、类型1)
	 * 
	 * @param entity
	 */
	public Organization insertOrganizationEntity(Organization entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Organization editOrganizationEntity(Organization entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteOrganizationEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Organization getOrganizationEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createOrganizationSimpleQuery(DataQuery query);

	/**
	 * 新增岗位
	 * 
	 * @param orgId
	 * @param postname
	 * @param extendis
	 * @param user
	 * @return
	 */
	public Post insertPost(String orgId, String postname, String extendis, LoginUser user);

	/**
	 * 修改岗位
	 * 
	 * @param postId
	 * @param postname
	 * @param posttype
	 * @param user
	 * @return
	 */
	public Post editPost(String postId, String postname, String extendis, LoginUser user);

	/**
	 * 获得所有父亲组织机构
	 * 
	 * @return
	 */
	public List<Organization> getParentOrgs(String orgid);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deletePostEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Post getPostEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPostSimpleQuery(DataQuery query);

	/**
	 * 移动组织机构
	 * 
	 * @param orgId
	 * @param targetOrgId
	 */
	public void moveOrgTreeNode(String orgId, String targetOrgId, LoginUser currentUser);

	/**
	 * 加载岗位树
	 * 
	 * @param ids
	 * @return
	 */
	public List<EasyUiTreeNode> loadPostTree(String ids);

	/**
	 * 为用户添加岗位（如果是标准岗则替换掉用户已有标准岗，如果是临时岗则添加给用户）
	 * 
	 * @param userId
	 * @param postId
	 */
	public void addUserPost(String userId, String postId, LoginUser currentUser);

	/**
	 * 移除岗位下的用户
	 * 
	 * @param postId
	 * @param Userid
	 * @param currentUser
	 */
	public void removePostUsers(String postId, String userid, LoginUser currentUser);

	/**
	 * 设置岗位权限
	 * 
	 * @param actionTreeIds
	 * @param postId
	 */
	public void setPostActionTree(List<String> actionTreeIds, String postId);

	/**
	 * 获取机构列表
	 * 
	 * @return List<Organization>
	 */
	public List<Organization> getTree();

	/**
	 * 获取岗位
	 * 
	 * @param orgId
	 * @return List<Organization>
	 */
	public List<Map<String, Object>> getPostList(String orgId);

	/**
	 * 获取岗位，带父机构的岗位
	 * 
	 * @param orgId
	 * @return List<Organization>
	 */
	public List<Map<String, Object>> getPostListWithPOrgPost(String orgId);

	/**
	 * 获得组织机构下的所有人
	 * 
	 * @param orgid
	 * @return
	 */
	public List<String> getOrgUsers(String orgid);

	/**
	 * 获得岗位下的所有人
	 * 
	 * @param oid
	 * @return
	 */
	public List<String> getPostUser(String postid);

	/**
	 * 获取所有组织机构
	 * 
	 * @return List<Organization>
	 */
	public List<Organization> getList();

	/**
	 * 通过副键查找组织机构
	 * 
	 * @param appid
	 * @return
	 */
	public Organization getOrganizationByAppid(String appid);

	/**
	 * 删除组织机构和用户对应关系
	 * 
	 * @param orgid
	 * @param userids
	 * @param currentUser
	 */
	public void removeOrgUsers(String orgid, String userids, LoginUser currentUser);

	/**
	 * 同步远程机构到本地
	 * 
	 * @param remoteOrgs
	 */
	public void syncRemotOrgs(List<Organization> remoteOrgs);

	/**
	 * 獲取所有組織機構備注
	 * 
	 * @return
	 */
	public List<String> getAllOrgComments();

	/**
	 * 通过备注获得组织机构
	 * 
	 * @param orgComment
	 * @return
	 */
	public List<Organization> getOrganizationByComments(String orgComment);

}