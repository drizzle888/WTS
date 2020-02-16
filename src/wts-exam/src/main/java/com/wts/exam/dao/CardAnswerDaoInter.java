package com.wts.exam.dao;

import com.wts.exam.domain.CardAnswer;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：问卷答案数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface CardAnswerDaoInter  {
 /** 删除一个问卷答案实体
 * @param entity 实体
 */
 public void deleteEntity(CardAnswer cardAnswer) ;
 /** 由问卷答案id获得一个问卷答案实体
 * @param id
 * @return
 */
 public CardAnswer getEntity(String cardAnswerid) ;
 /** 插入一条问卷答案数据
 * @param entity
 */
 public  CardAnswer insertEntity(CardAnswer cardAnswer);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个问卷答案记录
 * @param entity
 */
 public void editEntity(CardAnswer cardAnswer);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条问卷答案查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除问卷答案实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询问卷答案实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<CardAnswer> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改问卷答案实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计问卷答案:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}