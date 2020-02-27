package com.farm.authority.dao;

import com.farm.authority.domain.Organization;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：组织机构持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
public interface OrganizationDaoInter {
	/**
	 * 删除一个组织机构实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Organization organization);

	/**
	 * 由组织机构id获得一个组织机构实体
	 * 
	 * @param id
	 * @return
	 */
	public Organization getEntity(String organizationid);

	/**
	 * 插入一条组织机构数据
	 * 
	 * @param entity
	 */
	public Organization insertEntity(Organization organization);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个组织机构记录
	 * 
	 * @param entity
	 */
	public void editEntity(Organization organization);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条组织机构查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除组织机构实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询组织机构实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Organization> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改组织机构实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计组织机构:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 获得所有子节点
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Organization> getAllSubNodes(String orgId);

	/**
	 * 获取所有组织机构
	 * 
	 * @return List<Organization>
	 */
	public List<Organization> getList();

	/**
	 * 通过sql插入组织机构
	 * 
	 * @param org
	 */
	public void insertSqlEntity(Organization org);
}