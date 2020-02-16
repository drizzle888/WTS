package com.farm.authority.dao;

import com.farm.authority.domain.Userpost;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：用户岗位数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141204174206
 *说明：
 */
public interface UserpostDaoInter {
	/**
	 * 删除一个用户岗位实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Userpost userpost);

	/**
	 * 由用户岗位id获得一个用户岗位实体
	 * 
	 * @param id
	 * @return
	 */
	public Userpost getEntity(String userpostid);

	/**
	 * 插入一条用户岗位数据
	 * 
	 * @param entity
	 */
	public Userpost insertEntity(Userpost userpost);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个用户岗位记录
	 * 
	 * @param entity
	 */
	public void editEntity(Userpost userpost);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条用户岗位查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除用户岗位实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询用户岗位实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Userpost> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改用户岗位实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计用户岗位:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 *获得用户所有的临时岗位
	 * 
	 * @param userId
	 */
	public List<Userpost> getTempUserPost(String userId);
}