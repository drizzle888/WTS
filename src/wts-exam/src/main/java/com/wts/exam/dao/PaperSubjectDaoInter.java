package com.wts.exam.dao;

import com.wts.exam.domain.PaperSubject;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：试卷试题数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface PaperSubjectDaoInter {
	/**
	 * 删除一个试卷试题实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(PaperSubject papersubject);

	/**
	 * 由试卷试题id获得一个试卷试题实体
	 * 
	 * @param id
	 * @return
	 */
	public PaperSubject getEntity(String papersubjectid);

	/**
	 * 插入一条试卷试题数据
	 * 
	 * @param entity
	 */
	public PaperSubject insertEntity(PaperSubject papersubject);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个试卷试题记录
	 * 
	 * @param entity
	 */
	public void editEntity(PaperSubject papersubject);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条试卷试题查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除试卷试题实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询试卷试题实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<PaperSubject> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改试卷试题实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计试卷试题:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 获得试卷的试题
	 * 
	 * @param paperid
	 * @return
	 */
	public int countPaperSubjectNum(String paperid);

	/**
	 * 獲得答卷的所有題目
	 * 
	 * @param paperid
	 * @return
	 */
	public List<String> getAllSubjectVersionids(String paperid);
}