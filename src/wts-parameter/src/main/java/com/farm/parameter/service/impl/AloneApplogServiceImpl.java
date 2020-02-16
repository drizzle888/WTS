package com.farm.parameter.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.parameter.dao.AloneApplogDaoInter;
import com.farm.parameter.domain.AloneApplog;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.time.TimeTool;
import com.farm.parameter.service.AloneApplogServiceInter;

/**
 * 系统日志
 * 
 * @author MAC_wd
 */
@Service
public class AloneApplogServiceImpl implements AloneApplogServiceInter {
	@Resource
	private AloneApplogDaoInter aloneApplogDao;

	public AloneApplog insertEntity(AloneApplog entity, LoginUser user) {
		// entity.setCtime(TimeTool.getTimeDate12());
		// entity.setEtime(TimeTool.getTimeDate12());
		// entity.setCuser(user.getId());
		// entity.setEuser(user.getId());
		return aloneApplogDao.insertEntity(entity);
	}

	public AloneApplog editEntity(AloneApplog entity, LoginUser user) {
		AloneApplog entity2 = aloneApplogDao.getEntity(entity.getId());
		// entity2.setEtime(TimeTool.getTimeDate12());
		// entity2.setEuser(user.getId());
		entity2.setCtime(entity.getCtime());
		entity2.setDescribes(entity.getDescribes());
		entity2.setAppuser(entity.getAppuser());
		entity2.setLevels(entity.getLevels());
		entity2.setMethod(entity.getMethod());
		entity2.setClassname(entity.getClassname());
		aloneApplogDao.editEntity(entity2);
		return entity2;
	}

	public void deleteEntity(String entity, LoginUser user) {
		aloneApplogDao.deleteEntity(aloneApplogDao.getEntity(entity));
	}
	
	@Transactional
	public AloneApplog getEntity(String id) {
		if (id == null) {
			return null;
		}
		return aloneApplogDao.getEntity(id);
	}

	// ----------------------------------------------------------------------------------
	public AloneApplogDaoInter getaloneApplogDao() {
		return aloneApplogDao;
	}

	public void setaloneApplogDao(AloneApplogDaoInter dao) {
		this.aloneApplogDao = dao;
	}

	@Override
	@Transactional
	public AloneApplog log(String describes, String appuser, String level,
			String method, String classname, String ip) {
		return aloneApplogDao.insertEntity(new AloneApplog(TimeTool
				.getTimeDate14(), describes, appuser, level, method, classname,
				ip));
	}

}
