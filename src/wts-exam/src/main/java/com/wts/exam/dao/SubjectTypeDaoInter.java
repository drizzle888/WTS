package com.wts.exam.dao;

import com.wts.exam.domain.ExamType;
import com.wts.exam.domain.SubjectType;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* *
 *功能：考题分类数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface SubjectTypeDaoInter {
	/**
	 * 删除一个考题分类实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(SubjectType subjecttype);

	/**
	 * 由考题分类id获得一个考题分类实体
	 * 
	 * @param id
	 * @return
	 */
	public SubjectType getEntity(String subjecttypeid);

	/**
	 * 插入一条考题分类数据
	 * 
	 * @param entity
	 */
	public SubjectType insertEntity(SubjectType subjecttype);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个考题分类记录
	 * 
	 * @param entity
	 */
	public void editEntity(SubjectType subjecttype);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条考题分类查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除考题分类实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询考题分类实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<SubjectType> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改考题分类实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计考题分类:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 获得所有子节点
	 * 
	 * @param string
	 * @return
	 */
	public List<SubjectType> getAllSubNodes(String string);

	/**
	 * 获得分类的子分类
	 * 
	 * @param typeIds
	 * @return
	 */
	public List<String> getAllSubType(List<String> typeIds);

	/**
	 * 獲得用戶在擁有權限的分類id
	 * 
	 * @param userId
	 * @param funtype
	 *            權限類型
	 * @return
	 */
	public Set<String> getUserPopTypeids(String userId, String funtype);
}