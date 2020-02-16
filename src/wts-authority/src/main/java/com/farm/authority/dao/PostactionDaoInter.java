package com.farm.authority.dao;

import com.farm.authority.domain.Postaction;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;


/* *
 *功能：岗位权限数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141204174206
 *说明：
 */
public interface PostactionDaoInter  {
 /** 删除一个岗位权限实体
 * @param entity 实体
 */
 public void deleteEntity(Postaction postaction) ;
 /** 由岗位权限id获得一个岗位权限实体
 * @param id
 * @return
 */
 public Postaction getEntity(String postactionid) ;
 /** 插入一条岗位权限数据
 * @param entity
 */
 public  Postaction insertEntity(Postaction postaction);
 /** 获得记录数量
 * @return
 */
 public int getAllListNum();
 /**修改一个岗位权限记录
 * @param entity
 */
 public void editEntity(Postaction postaction);
 /**获得一个session
 */
 public Session getSession();
 /**执行一条岗位权限查询语句
 */
 public DataResult runSqlQuery(DataQuery query);
 /**
 * 条件删除岗位权限实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param rules
 *            删除条件
 */
 public void deleteEntitys(List<DBRule> rules);

 /**
 * 条件查询岗位权限实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
 * 
 * @param rules
 *            查询条件
 * @return
 */
 public List<Postaction> selectEntitys(List<DBRule> rules);

 /**
 * 条件修改岗位权限实体，依据对象字段值(一般不建议使用该方法)
 * 
 * @param values
 *            被修改的键值对
 * @param rules
 *            修改条件
 */
 public void updataEntitys(Map<String, Object> values, List<DBRule> rules);
 /**
 * 条件合计岗位权限:count(*)
 * 
 * @param rules
 *            统计条件
 */
 public int countEntitys(List<DBRule> rules);
}