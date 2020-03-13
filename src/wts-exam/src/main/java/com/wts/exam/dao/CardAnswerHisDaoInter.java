package com.wts.exam.dao;

import com.wts.exam.domain.CardAnswerHis;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：用户答案历史记录数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface CardAnswerHisDaoInter {
	/**
	 * 删除一个用户答案历史记录实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(CardAnswerHis cardanswerhis);

	/**
	 * 由用户答案历史记录id获得一个用户答案历史记录实体
	 * 
	 * @param id
	 * @return
	 */
	public CardAnswerHis getEntity(String cardanswerhisid);

	/**
	 * 插入一条用户答案历史记录数据
	 * 
	 * @param entity
	 */
	public CardAnswerHis insertEntity(CardAnswerHis cardanswerhis);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个用户答案历史记录记录
	 * 
	 * @param entity
	 */
	public void editEntity(CardAnswerHis cardanswerhis);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条用户答案历史记录查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除用户答案历史记录实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询用户答案历史记录实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<CardAnswerHis> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改用户答案历史记录实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计用户答案历史记录:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	public void backup(String cardid);
}