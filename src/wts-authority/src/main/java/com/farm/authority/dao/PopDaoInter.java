package com.farm.authority.dao;

import com.farm.authority.domain.Pop;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：业务权限数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface PopDaoInter  {
 /** 删除一个业务权限实体
 * @param entity 实体
 */
 public void deleteEntity(Pop pop) ;
 /** 由业务权限id获得一个业务权限实体
 * @param id
 * @return
 */
 public Pop getEntity(String popid) ;
 /** 插入一条业务权限数据
 * @param entity
 */
 public  Pop insertEntity(Pop pop);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个业务权限记录
 * @param entity
 */
 public void editEntity(Pop pop);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条业务权限查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除业务权限实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询业务权限实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Pop> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改业务权限实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计业务权限:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}