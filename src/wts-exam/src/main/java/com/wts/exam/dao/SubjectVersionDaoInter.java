package com.wts.exam.dao;

import com.wts.exam.domain.SubjectVersion;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：考题版本数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface SubjectVersionDaoInter {
	/**
	 * 删除一个考题版本实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(SubjectVersion subjectversion);

	/**
	 * 由考题版本id获得一个考题版本实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectVersion getEntity(String subjectversionid);

	/**
	 * 插入一条考题版本数据
	 * 
	 * @param entity
	 */
	public SubjectVersion insertEntity(SubjectVersion subjectversion);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个考题版本记录
	 * 
	 * @param entity
	 */
	public void editEntity(SubjectVersion subjectversion);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条考题版本查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除考题版本实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询考题版本实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<SubjectVersion> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改考题版本实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计考题版本:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**获得答卷的试题版本
	 * @param paperId
	 * @return
	 */
	public List<SubjectVersion> getVersionsByPaperId(String paperId);
}