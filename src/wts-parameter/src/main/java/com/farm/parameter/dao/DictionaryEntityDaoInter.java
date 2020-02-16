package com.farm.parameter.dao;

import java.util.List;

import com.farm.parameter.domain.AloneDictionaryEntity;



public interface DictionaryEntityDaoInter  {
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneDictionaryEntity  entity) ;
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
	public AloneDictionaryEntity  getEntity(String id) ;
	/** 插入一条数据
	 * @param entity
	 */
	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity  entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneDictionaryEntity  entity);
	
	/**
	 * 根据key查找实体
	 * 
	 * @param key 字段（ENTITYINDEX）
	 * @return
	 * @author zhang_hc
	 * @time 2012-12-20 下午05:41:25
	 */
	public List<AloneDictionaryEntity> findEntityByKey(String key);
	
	/**
	 * 根据key查找实体
	 * 
	 * @param key 字段（ENTITYINDEX）
	 * @param exId 要排除的ID
	 * @return
	 * @author zhang_hc
	 * @time 2012-12-20 下午05:41:54
	 */
	public List<AloneDictionaryEntity> findEntityByKey(String key, String exId);
	/**查找所有的
	 * @return
	 */
	public List<AloneDictionaryEntity> getAllEntity();
}
