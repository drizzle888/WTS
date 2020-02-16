package com.farm.parameter.service;

import com.farm.parameter.domain.AloneDictionaryType;
import com.farm.core.auth.domain.LoginUser;


public interface DictionaryTypeServiceInter {
	/**
	 * 通过一个键获得一个应用配置字符串值
	 */
	public String getConfigValue(String key);


	public void deleteEntity(String entity, LoginUser user);

	public AloneDictionaryType editEntity(AloneDictionaryType entity,
			LoginUser user);

	public AloneDictionaryType getEntity(String id);

	public AloneDictionaryType insertEntity(AloneDictionaryType entity,
			LoginUser user);

}
