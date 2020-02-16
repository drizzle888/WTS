package com.farm.doc.dao;

import com.farm.doc.domain.Usermessage;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：用户消息数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface UsermessageDaoInter  {
 /** 删除一个用户消息实体
 * @param entity 实体
 */
 public void deleteEntity(Usermessage usermessage) ;
 /** 由用户消息id获得一个用户消息实体
 * @param id
 * @return
 */
 public Usermessage getEntity(String usermessageid) ;
 /** 插入一条用户消息数据
 * @param entity
 */
 public  Usermessage insertEntity(Usermessage usermessage);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个用户消息记录
 * @param entity
 */
 public void editEntity(Usermessage usermessage);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条用户消息查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除用户消息实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询用户消息实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Usermessage> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改用户消息实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计用户消息:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}