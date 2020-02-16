package com.farm.parameter.dao;

import java.util.List;
import java.util.Map;

import com.farm.parameter.domain.AloneParameter;



public interface ParameterDaoInter {
	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(AloneParameter entity);

	/**
	 * 由id获得一个实体
	 * 
	 * @param id
	 * @return
	 */
	public AloneParameter getEntity(String id);

	public List<AloneParameter> getAllEntity();

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 */
	public void insertEntity(AloneParameter entity);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个记录
	 * 
	 * @param entity
	 */
	public void editEntity(AloneParameter entity);

	/**
	 * 根据参数域类型查找实体集合
	 * 
	 * @param domainType
	 *            ：1:系统配置；2:应用参数
	 * @return
	 * @author zhang_hc
	 * @time 2012-8-30 下午05:56:27
	 */
	public List<Map<String, Object>> getListByDomainType(String domainType);

	/**
	 * 根据key查找实体集合
	 * 
	 * @param paramKey ：参数键
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-7 下午04:30:02
	 */
	public List<AloneParameter> findListByKey(String paramKey);

	/**
	 * 根据key和要排除的ID查找实体集合
	 * 
	 * @param paramKey ：参数键
	 * @param excludeParamId ：要排除的系统参数ID
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-7 下午04:30:18
	 */
	public List<AloneParameter> findListByKeyAndExcludeParamId(String paramKey,
			String excludeParamId);

	/**
	 * 根据key查找实体
	 * 
	 * @param paramKey 参数键
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-12 下午05:59:18
	 */
	public AloneParameter getEntityByKey(String key);
}
