package com.farm.authority.dao;

import com.farm.authority.domain.Action;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：权限资源数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141119144919
 *说明：
 */
public interface ActionDaoInter {
	/**
	 * 删除一个权限资源实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Action action);

	/**
	 * 由权限资源id获得一个权限资源实体
	 * 
	 * @param id
	 * @return
	 */
	public Action getEntity(String actionid);

	/**
	 * 插入一条权限资源数据
	 * 
	 * @param entity
	 */
	public Action insertEntity(Action action);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个权限资源记录
	 * 
	 * @param entity
	 */
	public void editEntity(Action action);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条权限资源查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除权限资源实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询权限资源实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Action> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改权限资源实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计权限资源:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * @param authkey
	 * @return
	 */
	public Action getEntityByKey(String authkey);

	/**
	 * @return
	 */
	public List<Action> getAllEntity();
}