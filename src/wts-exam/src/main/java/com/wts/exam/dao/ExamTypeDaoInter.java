package com.wts.exam.dao;

import com.wts.exam.domain.ExamType;
import org.hibernate.Session;

import com.farm.authority.domain.Organization;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* *
 *功能：考试分类数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface ExamTypeDaoInter {
	/**
	 * 删除一个考试分类实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(ExamType examtype);

	/**
	 * 由考试分类id获得一个考试分类实体
	 * 
	 * @param id
	 * @return
	 */
	public ExamType getEntity(String examtypeid);

	/**
	 * 插入一条考试分类数据
	 * 
	 * @param entity
	 */
	public ExamType insertEntity(ExamType examtype);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个考试分类记录
	 * 
	 * @param entity
	 */
	public void editEntity(ExamType examtype);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条考试分类查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除考试分类实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询考试分类实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<ExamType> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改考试分类实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计考试分类:count(*)
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
	public List<ExamType> getAllSubNodes(String string);

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
	public Set<String> getUserPopTypeids(String userId, String... funtype);
	/**获得分类权限的用户
	 * @param examtypeid
	 * @param funtype
	 * @return <USERID,USERNAME,ORGID,ORGNAME,TYPEID,TYPENAME>
	 */
	public List<Map<String, Object>>  getTypePopUsers(String examtypeid, String funtype);
}