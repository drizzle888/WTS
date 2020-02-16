package com.farm.parameter.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.parameter.dao.AloneparameterlocalDaoInter;
import com.farm.parameter.dao.ParameterDaoInter;
import com.farm.parameter.domain.AloneParameter;
import com.farm.parameter.domain.Aloneparameterlocal;
import com.farm.parameter.exception.KeyExistException;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.parameter.service.ParameterServiceInter;
import com.farm.util.cache.FarmCaches;
import com.farm.util.web.FarmHtmlUtils;

@Service
public class ParameterServiceImpl implements ParameterServiceInter {
	@Resource
	private ParameterDaoInter parameterDao;
	@Resource
	private AloneparameterlocalDaoInter aloneparameterlocalDao;
	// 参数值缓存
	private static final Map<String, String> parameterCache = new HashMap<String, String>();
	// 参数实体缓存
	private static final Map<String, AloneParameter> parameterEntityCache = new HashMap<String, AloneParameter>();
	private static final Logger log = Logger.getLogger(ParameterServiceImpl.class);

	public boolean initConfig() {
		return false;
	}

	public boolean refreshCache() {
		parameterEntityCache.clear();
		parameterCache.clear();
		return true;
	}

	@Override
	@Transactional
	public void deleteEntity(String entity, LoginUser user) {
		parameterDao.deleteEntity(parameterDao.getEntity(entity));
	}

	@Override
	@Transactional
	public AloneParameter insertEntity(AloneParameter entity, LoginUser aloneUser) throws KeyExistException {
		// 如果“参数键”重复，抛异常
		if (isRepeatKey(entity.getPkey())) {
			throw new RuntimeException("参数键：" + entity.getPkey() + "，已存在！");
		}

		// 如果参数类型选择的是“文本”，则“枚举规则“为空。出现的情况为：选择”枚举“，
		// 填入”枚举规则“，后又选择文本，”枚举规则“在页面看不见，但有值。
		if (entity.getVtype().equals("1")) {
			entity.setRules("");
		}

		// 新增实体
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setCuser(aloneUser.getId());
		entity.setMuser(aloneUser.getId());
		entity.setState("1");// 默认启用（暂无用）
		parameterDao.insertEntity(entity);
		return entity;
	}

	@Override
	@Transactional
	public AloneParameter editEntity(AloneParameter entity, LoginUser aloneUser) {
		// 如果key被修改，并且key已存在，抛异常。
		AloneParameter entity2 = getEntity(entity.getId());
		if (!entity2.getPkey().equals(entity.getPkey()) && isRepeatKey(entity.getPkey())) {
			throw new RuntimeException("参数键：" + entity.getPkey() + "，已存在！");
		}
		// 如果参数类型选择的是“文本”，则“枚举规则“为空。为什么？参考insertEntity()方法
		if (entity.getVtype().equals("1")) {
			entity.setRules("");
		}
		// 修改实体
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setMuser(aloneUser.getId());
		entity2.setName(entity.getName());
		// entity2.setState(entity.getState());//暂无用
		entity2.setPkey(entity.getPkey());
		entity2.setPvalue(entity.getPvalue());// 系统参数管理页面编辑
		entity2.setRules(entity.getRules());
		entity2.setDomain(entity.getDomain());
		entity2.setUserable(entity.getUserable());
		entity2.setComments(entity.getComments());
		entity2.setVtype(entity.getVtype());
		parameterDao.editEntity(entity2);
		return entity2;
	}

	public boolean isRepeatKey(String paramKey, String excludeParamId) {
		List<AloneParameter> paramList = null;
		if (excludeParamId == null || excludeParamId.equals("")) {
			paramList = parameterDao.findListByKey(paramKey.trim());
		} else {
			paramList = parameterDao.findListByKeyAndExcludeParamId(paramKey.trim(), excludeParamId);
		}
		return paramList.size() > 0;
	}

	public boolean isRepeatKey(String paramKey) {
		return isRepeatKey(paramKey, null);
	}

	@Override
	@Transactional
	public AloneParameter getEntity(String id) {
		if (id == null) {
			return null;
		}
		return parameterDao.getEntity(id);
	}

	public ParameterDaoInter getParameterDao() {
		return parameterDao;
	}

	public void setParameterDao(ParameterDaoInter parameterDao) {
		this.parameterDao = parameterDao;
	}

	public AloneparameterlocalDaoInter getAloneparameterlocalDao() {
		return aloneparameterlocalDao;
	}

	public void setAloneparameterlocalDao(AloneparameterlocalDaoInter aloneparameterlocalDao) {
		this.aloneparameterlocalDao = aloneparameterlocalDao;
	}

	@Override
	@Transactional
	/**
	 * 返回格式：[{VTYPE=2, NAME=中文2, ID=402881eb396c880101396c8ab7ef0001,
	 * RULES=早班:zhao,中班:zhong,夜班:ye, PVALUE=无, ENUMVALUE=[ [早班, zhao], [中班,
	 * zhong], [夜班, ye]] }, {VTYPE=1, NAME=中文注释,
	 * ID=402881eb39676b6b013967b2c4bf0009, RULES=null, PVALUE=none}, null,
	 * null, null, null, null, null, null, null]
	 */
	public List<Map<String, Object>> getTransParamList(String domainType) {
		// 获取参数list
		String type = "";
		if (domainType.equals("1")) {
			type = "alone";
		} else if (domainType.equals("2")) {
			type = "app";
		} else if (domainType.equals("3")) {
			type = "ccs";
		}
		List<Map<String, Object>> list = parameterDao.getListByDomainType(type);

		// 遍历集合，如果参数类型（ALONE_PARAMETER.VTYPE）是枚举值，取出枚举值转换成list集合并放入map中
		for (Map<String, Object> map : list) {
			if (map.get("VTYPE").equals('2')) {// 如果是枚举值；数据库类型为“CHAR(1)”，hibernate返回的是字符
				ArrayList<List<String>> enumList = new ArrayList<List<String>>();

				for (String enumStr : (map.get("RULES") + "").split(",")) {
					ArrayList<String> kvList = new ArrayList<String>();
					for (String kvStr : enumStr.split(":")) {
						kvList.add(kvStr);
					}
					enumList.add(kvList);
				}
				map.put("ENUMVALUE", enumList);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public AloneParameter findEntityByKey(String paramKey) {
		paramKey = paramKey.trim();
		AloneParameter parameter = parameterEntityCache.get(paramKey);
		if (!parameterEntityCache.containsKey(paramKey)) {
			parameter = parameterDao.getEntityByKey(paramKey.trim());
			parameterEntityCache.put(paramKey.trim(), parameter);
		}
		return parameter;
	}

	@Override
	@Transactional
	public void setValue(String key, String pValue, LoginUser aloneUser) {
		AloneParameter entity = parameterDao.getEntityByKey(key);
		String value = XmlConfigFileService.getValue(entity.getPkey());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setMuser(aloneUser.getId());
		entity.setPvalue(pValue);
		if (!pValue.equals(value)) {
			log.info("修改参数" + key + "的值" + value + "为" + pValue);
		}
		parameterDao.editEntity(entity);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getAllParameters() {
		DataQuery query = DataQuery.init(//
				null, //
				"Alone_Parameter a", //
				"id,domain,name,pkey,pvalue,vtype,comments,rules");
		query.addSort(new DBSort("a.name", "asc"));// 按最后修改事件排序
		query.setPagesize(200);
		DataResult result = null;
		try {
			result = query.search();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result.getResultList();
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getUserParameters(String userid) {
		DataQuery query = DataQuery.init(
				//
				null, //
				"Alone_Parameter a ", //
				"a.id as id,a.domain as domain,a.name as name,a.pkey as pkey,a.pvalue as pvalue,a.vtype as vtype,a.comments as comments,a.rules as rules");
		query.addSort(new DBSort("a.name", "asc"));
		query.setPagesize(200);
		query.addSqlRule(" and USERABLE='1'");
		DataResult result = null;
		try {
			result = query.search();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		for (Map<String, Object> node : result.getResultList()) {
			// 获取每个用户的个性化值
			Object pkey = node.get("PKEY");
			if (pkey != null) {
				node.put("USERVALUE", getValue((String) pkey, userid));
			}
			if (node.get("USERVALUE") != null && node.get("USERVALUE").toString().trim().length() > 0) {
				node.put("PVALUE", node.get("USERVALUE"));
			}
		}
		return result.getResultList();
	}

	@Override
	@Transactional
	public void setUserValue(String key, String pValue, LoginUser aloneUser) {
		AloneParameter entity = findEntityByKey(key);
		if (entity == null) {
			throw new RuntimeException("参数不存在!");
		}
		if (entity.getUserable().equals("0")) {
			throw new RuntimeException("该参数不允许用户自定义！");
		}
		Aloneparameterlocal localPara = aloneparameterlocalDao.getEntityByUser(aloneUser.getId(), entity.getId());
		if (localPara == null) {
			localPara = new Aloneparameterlocal();
			localPara.setEuser(aloneUser.getId());
			localPara.setParameterid(entity.getId());
			localPara.setPvalue(pValue);
			aloneparameterlocalDao.insertEntity(localPara);
		} else {
			localPara.setPvalue(pValue);
			aloneparameterlocalDao.editEntity(localPara);
		}
		parameterCache.clear();
	}

	@Override
	@Transactional
	public String getValue(String key) {
		if (parameterCache.containsKey(key)) {
			return parameterCache.get(key);
		} else {
			AloneParameter entity = findEntityByKey(key);
			if (entity == null) {
				parameterCache.put(key, null);
				return null;
			}
			if (parameterCache.size() > 1000) {
				parameterCache.clear();
			}
			parameterCache.put(key, entity.getPvalue());
			return entity.getPvalue();
		}
	}

	@Override
	@Transactional
	public String getValue(String key, String userId) {
		AloneParameter entity = findEntityByKey(key);
		if (entity == null) {
			return null;
		}
		// 如果允许，且有，取1.用户个性化参数.或
		if (entity.getUserable().equals("1")) {
			Aloneparameterlocal localPara = aloneparameterlocalDao.getEntityByUser(userId, entity.getId());
			if (localPara != null) {
				return localPara.getPvalue();
			}
		}
		// 没有用户参数取2.默认参数
		return getValue(key);
	}

	@Override
	public Map<String, Object> getCacheInfo() {
		Map<String, Object> map = DataQuery.getCacheInfo();
		map.putAll(FarmCaches.getInstance().getCacheInfo());
		return map;
	}

	@Override
	public void flashAllCache() {
		DataQuery.clearCache();
		FarmCaches.getInstance().clearAllCache();
	}

	@Override
	@Transactional
	public void loadXmlParasToDatabase(LoginUser aloneUser) {
		List<Entry<String, String>> paras = XmlConfigFileService.getEntrys();
		for (Entry<String, String> node : paras) {
			if (parameterDao.getEntityByKey(node.getKey().trim()) == null) {
				// 没有
				AloneParameter entity = new AloneParameter();
				String groupname = XmlConfigFileService.getGroupName(node.getKey());
				String note = XmlConfigFileService.getNote(node.getKey());
				if (StringUtils.isNotBlank(groupname)) {
					entity.setDomain(groupname);
				}
				entity.setPkey(node.getKey());
				entity.setPvalue(node.getValue());
				entity.setVtype("1");
				entity.setUserable("0");
				entity.setName(node.getKey());
				entity.setComments(note);
				// 判断值中有没有html，有html的不加载
				String val = node.getValue();
				if (val.equals(FarmHtmlUtils.HtmlRemoveTag(val))) {
					try {
						insertEntity(entity, aloneUser);
					} catch (KeyExistException e) {
						throw new RuntimeException("KeyExistException");
					}
				} else {
					log.warn("加载配置参数时遇到，html脚本值得参数，不进行加载！" + node.getKey() + ":" + node.getValue());
				}
			} else {
				// 已经有了
			}
		}
	}
}
