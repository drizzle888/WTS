package com.wts.exam.service;

import com.wts.exam.domain.SubjectVersion;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：考题版本服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectVersionServiceInter{
  /**
   *新增实体管理实体
   * 
   * @param entity
   */
  public SubjectVersion insertSubjectversionEntity(SubjectVersion entity,LoginUser user);
  /**
   *修改实体管理实体
   * 
   * @param entity
   */
  public SubjectVersion editSubjectversionEntity(SubjectVersion entity,LoginUser user);
  /**
   *删除实体管理实体
   * 
   * @param entity
   */
  public void deleteSubjectversionEntity(String id,LoginUser user);
  /**
   *获得实体管理实体
   * 
   * @param id
   * @return
   */
  public SubjectVersion getSubjectversionEntity(String id);
  /**
   * 创建一个基本查询用来查询当前实体管理实体
   * 
   * @param query
   *            传入的查询条件封装
   * @return
   */
  public DataQuery createSubjectversionSimpleQuery(DataQuery query);
}