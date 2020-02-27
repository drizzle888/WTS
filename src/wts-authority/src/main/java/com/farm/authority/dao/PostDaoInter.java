package com.farm.authority.dao;

import com.farm.authority.domain.Post;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：岗位数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141124152033
 *说明：
 */
public interface PostDaoInter {
	/**
	 * 删除一个岗位实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Post post);

	/**
	 * 由岗位id获得一个岗位实体
	 * 
	 * @param id
	 * @return
	 */
	public Post getEntity(String postid);

	/**
	 * 插入一条岗位数据
	 * 
	 * @param entity
	 */
	public Post insertEntity(Post post);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个岗位记录
	 * 
	 * @param entity
	 */
	public void editEntity(Post post);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条岗位查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除岗位实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询岗位实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Post> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改岗位实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计岗位:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);
}