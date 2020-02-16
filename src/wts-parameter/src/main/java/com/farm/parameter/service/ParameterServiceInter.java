package com.farm.parameter.service;

import java.util.List;
import java.util.Map;

import com.farm.parameter.domain.AloneParameter;
import com.farm.parameter.exception.KeyExistException;
import com.farm.core.auth.domain.LoginUser;

public interface ParameterServiceInter {
	public final String CURRENCY_YES = "0";
	public final String CURRENCY_USER = "1";
	public final String TYPE_GENERAL = "0";
	public final String TYPE_DECORDE = "1";
	public final String TYPE_MD5 = "2";

	/**
	 * 修改系统参数的默认值
	 * 
	 * @param paramId
	 *            ：系统参数ID
	 * @param pValue
	 *            ：参数值
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 上午09:41:08
	 */
	public void setValue(String key, String pValue, LoginUser aloneUser);

	/**
	 * 修改系统参数的个性化值
	 * 
	 * @param paramId
	 *            ：系统参数ID
	 * @param pValue
	 *            ：参数值
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-9 上午09:41:08
	 */
	public void setUserValue(String key, String pValue, LoginUser aloneUser);

	/**
	 * 获得所有系统参数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAllParameters();

	/**
	 * 获得允许用户自定义的系统参数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getUserParameters(String userid);

	/**
	 * 获得参数值(1.用户个性化参数.或2.默认参数.)
	 * 
	 * @param key
	 *            参数键
	 * @param userId
	 *            用户id
	 * @return
	 */
	public String getValue(String key, String userId);

	public String getValue(String key);

	// -----------------------------------------------------------------------------

	/**
	 * 新增一条参数定义
	 * 
	 * @param entity
	 * @param domain
	 *            可以为参数选择一个域（相当于一个参数分类）
	 * @throws HaveIndexException
	 */
	public AloneParameter insertEntity(AloneParameter entity,  LoginUser aloneUser)
			throws KeyExistException;

	/**
	 * 删除一个参数定义
	 * 
	 * @param entity
	 * @param user
	 */
	public void deleteEntity(String parameterId, LoginUser user);

	/**
	 * 修改一个参数定义
	 * 
	 * @param entity
	 * @param aloneUser
	 * @return
	 */
	public AloneParameter editEntity(AloneParameter entity, LoginUser aloneUser);

	/**
	 * 强制刷新配置集合(刷新参数缓存)
	 */
	public boolean refreshCache();

	/**
	 * 根据参数域类型获取转换好的list集合。如：将字符串解析为list，页面组合为下拉选。
	 * 
	 * @author zhang_hc
	 * @param domainType
	 *            ：参数域类型（1：系统配置页；2：应用参数页）。说明：“系统参数管理”页面按导航栏分开
	 * @time 2012-8-28 下午02:59:08
	 */
	public List<Map<String, Object>> getTransParamList(String domainType);

	/**
	 * 验证是否是重复key
	 * 
	 * @param paramKey
	 *            ：“参数键”名称
	 * @param excludeParamId
	 *            ：要排除的系统参数ID
	 * @return 重复：true；不重复：false
	 * @author zhang_hc
	 * @time 2012-9-7 下午01:00:14
	 */
	public boolean isRepeatKey(String paramKey, String excludeParamId);

	/**
	 * 根据key查找实体
	 * 
	 * @param paramKey
	 *            参数键
	 * @return
	 * @author zhang_hc
	 * @time 2012-9-12 下午05:59:18
	 */
	public AloneParameter findEntityByKey(String paramKey);

	/**
	 * @param id
	 * @return
	 */
	public AloneParameter getEntity(String parameterId);

	/**
	 * 获得系统缓存信息
	 * 
	 * @return
	 */
	public Map<String, Object> getCacheInfo();

	/**
	 * 刷新系统所有缓存（dataquery和cacheUtils）
	 */
	public void flashAllCache();

	/**
	 * 加载xml配置文件到数据库中
	 */
	public void loadXmlParasToDatabase(LoginUser aloneUser );

}
