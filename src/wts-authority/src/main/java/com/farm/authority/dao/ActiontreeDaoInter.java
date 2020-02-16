package com.farm.authority.dao;

import com.farm.authority.domain.Actiontree;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：构造权限持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
public interface ActiontreeDaoInter {
	/**
	 * 删除一个构造权限实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Actiontree actiontree);

	/**
	 * 由构造权限id获得一个构造权限实体
	 * 
	 * @param id
	 * @return
	 */
	public Actiontree getEntity(String actiontreeid);

	/**
	 * 插入一条构造权限数据
	 * 
	 * @param entity
	 */
	public Actiontree insertEntity(Actiontree actiontree);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个构造权限记录
	 * 
	 * @param entity
	 */
	public void editEntity(Actiontree actiontree);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条构造权限查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除构造权限实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询构造权限实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Actiontree> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改构造权限实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计构造权限:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 获得所有子节点
	 * 
	 * @param treeNodeId
	 * @return
	 */
	public List<Actiontree> getAllSubNodes(String treeNodeId);
}