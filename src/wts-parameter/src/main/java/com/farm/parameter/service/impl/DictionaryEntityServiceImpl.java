package com.farm.parameter.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.parameter.dao.DictionaryEntityDaoInter;
import com.farm.parameter.dao.DictionaryTypeDaoInter;
import com.farm.parameter.domain.AloneDictionaryEntity;
import com.farm.parameter.domain.AloneDictionaryType;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.time.TimeTool;
import com.farm.parameter.service.DictionaryEntityServiceInter;
@Service
public class DictionaryEntityServiceImpl implements
		DictionaryEntityServiceInter {
	@Resource
	private DictionaryEntityDaoInter dictionaryentityDao;
	@Resource
	private DictionaryTypeDaoInter dictionarytypeDao;
	protected static final Map<String, List<AloneDictionaryType>> dicCache = new HashMap<String, List<AloneDictionaryType>>();

	@Override
	@Transactional
	public void deleteEntity(String entity, LoginUser user) {
		List<AloneDictionaryType> list = dictionarytypeDao
				.getListByEntityId(entity);
		for (Iterator<AloneDictionaryType> iterator = list.iterator(); iterator
				.hasNext();) {
			AloneDictionaryType aloneDictionaryType = (AloneDictionaryType) iterator
					.next();
			dictionarytypeDao.deleteEntity(aloneDictionaryType);
		}
		dictionaryentityDao.deleteEntity(dictionaryentityDao.getEntity(entity));
	}

	@Override
	@Transactional
	public AloneDictionaryEntity editEntity(AloneDictionaryEntity entity,
			LoginUser user) {
		dicCache.remove(entity.getEntityindex());
		if (validateIsRepeatKey(entity.getEntityindex(), entity.getId())) {
			throw new IllegalArgumentException("字典KEY已经存在");
		}
		AloneDictionaryEntity entity2 = getEntity(entity.getId());
		entity2.setComments(entity.getComments());
		entity2.setName(entity.getName().trim());
		entity2.setEntityindex(entity.getEntityindex().trim());// 必须去空格
		entity2.setUtime(TimeTool.getTimeDate12());
		entity2.setMuser(user.getId());
		entity2.setType(entity.getType());
		dictionaryentityDao.editEntity(entity2);
		return entity2;
	}

	public List<AloneDictionaryEntity> getAllEntity() {
		return dictionaryentityDao.getAllEntity();
	}

	public int getAllListNum() {
		return 0;
	}

	@Override
	@Transactional
	public AloneDictionaryEntity getEntity(String id) {
		if (id == null)
			return null;
		return dictionaryentityDao.getEntity(id);
	}
	@Override
	@Transactional
	public AloneDictionaryEntity insertEntity(AloneDictionaryEntity entity,
			LoginUser user) {
		if (validateIsRepeatKey(entity.getEntityindex(), null)) {
			throw new IllegalArgumentException("字典KEY已经存在");
		}
		entity.setCtime(TimeTool.getTimeDate12());
		entity.setCuser(user.getId());
		entity.setUtime(TimeTool.getTimeDate12());
		entity.setMuser(user.getId());
		entity.setState("1");
		entity.setName(entity.getName().trim());
		entity.setEntityindex(entity.getEntityindex().trim());// 必须去空格
		return dictionaryentityDao.insertEntity(entity);
	}

	public DictionaryEntityDaoInter getdictionaryentityDao() {
		return dictionaryentityDao;
	}

	@Override
	@Transactional
	public boolean validateIsRepeatKey(String key, String exId) {
		List<AloneDictionaryEntity> list = null;
		if (exId == null || exId.trim().equals("")) {
			list = dictionaryentityDao.findEntityByKey(key.trim());
		} else {
			list = dictionaryentityDao.findEntityByKey(key.trim(), exId.trim());
		}
		return list.size() > 0;
	}

	@Override
	public void editComments(String id) {
		if (id == null || id.equals("")) {
			return;
		}

		AloneDictionaryEntity dicEntity = dictionaryentityDao.getEntity(id);
		// if(dicEntity.getComments()!=null&&!dicEntity.getComments().equals("")){
		// return;
		// }

		List<AloneDictionaryType> dictypeList = dictionarytypeDao
				.getListByEntityId(id);
		if (dictypeList.isEmpty()) {
			return;
		}

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("{");
		for (AloneDictionaryType itDictype : dictypeList) {
			String name = itDictype.getName();
			String entitytype = itDictype.getEntitytype();
			sBuilder.append(name).append(":").append(entitytype).append(", ");
		}
		sBuilder.delete(sBuilder.length() - 2, sBuilder.length());
		sBuilder.append("}");
		if (sBuilder.toString() != null && sBuilder.toString().length() > 100) {
			dicEntity
					.setComments(sBuilder.toString().substring(0, 100) + "...");
		} else {
			dicEntity.setComments(sBuilder.toString());
		}
		dictionaryentityDao.editEntity(dicEntity);
	}

	public void setdictionaryentityDao(
			DictionaryEntityDaoInter dictionaryentityDao) {
		this.dictionaryentityDao = dictionaryentityDao;
	}

	public DictionaryTypeDaoInter getDictionarytypeDao() {
		return dictionarytypeDao;
	}

	public void setDictionarytypeDao(DictionaryTypeDaoInter dictionarytypeDao) {
		this.dictionarytypeDao = dictionarytypeDao;
	}

	@Override
	public Map<String, String> getDictionary(String key) {
		Map<String, String> dic = new HashMap<String, String>();
		for (AloneDictionaryType node : loadDics(key)) {
			dic.put(node.getEntitytype(), node.getName());
		}
		return dic;
	}

	private List<AloneDictionaryType> loadDics(String key) {
		List<AloneDictionaryType> types = dicCache.get(key);
		if (types == null) {
			types = new ArrayList<AloneDictionaryType>();
			DataQuery query = DataQuery
					.getInstance(1, "b.NAME,b.ENTITYTYPE",
							"ALONE_DICTIONARY_ENTITY a LEFT JOIN  ALONE_DICTIONARY_TYPE b ON a.ID=b.ENTITY");
			query.addRule(new DBRule("b.STATE", "1", "="));
			query.addRule(new DBRule("a.ENTITYINDEX", key, "="));
			query.addSort(new DBSort("b.SORT", "asc"));
			DataResult result = null;
			try {
				result = query.search();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			for (Map<String, Object> node : result.getResultList()) {
				AloneDictionaryType type = new AloneDictionaryType();
				type.setEntitytype(node.get("B_ENTITYTYPE").toString());
				type.setName(node.get("B_NAME").toString());
				types.add(type);
			}
			dicCache.put(key, types);
		}
		return types;
	}

	@Override
	public List<Entry<String, String>> getDictionaryList(String key) {
		List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
		for (AloneDictionaryType node : loadDics(key)) {
			Entry<String, String> entry = new SimpleEntry<String, String>(node
					.getEntitytype(), node.getName());
			list.add(entry);
		}
		return list;
	}

	@Override
	public String getDicKey(String dicId) {
		return getEntity(dicId).getEntityindex();
	}
}
