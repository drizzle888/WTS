package com.farm.parameter.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.farm.parameter.domain.AloneDictionaryEntity;
import com.farm.core.auth.domain.LoginUser;

public interface DictionaryEntityServiceInter {

	public void deleteEntity(String entity, LoginUser user);

	public AloneDictionaryEntity editEntity(AloneDictionaryEntity entity,
			LoginUser user);

	public AloneDictionaryEntity getEntity(String id);

	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity entity,
			LoginUser user);

	/**
	 * 验证key是否重复
	 * 
	 * @param key
	 *            字段（ENTITYINDEX）
	 * @param exId
	 *            要排除的ID
	 * @return
	 * @author liuchao
	 * @time 2012-12-20 下午05:36:09
	 */
	public boolean validateIsRepeatKey(String key, String exId);

	/**
	 * 修改备注。如果备注内容为空，则用“字典类型”的值填充备注。 格式为json，例：{采煤:1, 掘进:2, 开拓:3, 机电:4}
	 * 
	 * @param id
	 *            数据字典ID
	 * @author liuchao
	 * @time 2012-12-21 上午10:55:53
	 */
	public void editComments(String id);

	/**
	 * 查询所有的数据字典对象
	 * 
	 * @return
	 */
	public List<AloneDictionaryEntity> getAllEntity();

	/**
	 * 获得数据字典
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getDictionary(String key);

	/**
	 * 获得字典列表
	 * 
	 * @param key
	 * @return
	 */
	public List<Entry<String, String>> getDictionaryList(String key);

	/**
	 * 由字典id获得key
	 * 
	 * @param dicId
	 * @return
	 */
	public String getDicKey(String dicId);

}
