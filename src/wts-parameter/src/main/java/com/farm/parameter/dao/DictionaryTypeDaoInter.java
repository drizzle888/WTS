package com.farm.parameter.dao;

import java.util.List;

import com.farm.parameter.domain.AloneDictionaryType;



public interface DictionaryTypeDaoInter  {
	/**通过entityID获得一个类型序列
	 * @param entityId
	 */
	public List<AloneDictionaryType> getListByEntityId(String entityId);
	/** 删除一个实体
 	 * @param entity 实体
 	 */
 	public void deleteEntity(AloneDictionaryType entity) ;
 	/**
	 * 根据树索引码逻辑删除组织机构。 逻辑删除 ：修改状态为2
	 * 
	 * @param entityId
	 */
 	public void deleteEntityByTreecode(String entityId) ;
	/** 由id获得一个实体
	 * @param id
	 * @return
	 */
	public AloneDictionaryType getEntity(String id) ;
	/** 插入一条数据
	 * @param entity
	 */
	public void insertEntity(AloneDictionaryType entity);
	/** 获得记录数量
	 * @return
	 */
	public int getAllListNum();
	/**修改一个记录
	 * @param entity
	 */
	public void editEntity(AloneDictionaryType entity);
}
