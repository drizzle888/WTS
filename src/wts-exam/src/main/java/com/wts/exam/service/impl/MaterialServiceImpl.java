package com.wts.exam.service.impl;

import com.wts.exam.domain.Material;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.MaterialDaoInter;
import com.wts.exam.service.MaterialServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：引用材料服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class MaterialServiceImpl implements MaterialServiceInter {
	@Resource
	private MaterialDaoInter materialDaoImpl;

	private static final Logger log = Logger.getLogger(MaterialServiceImpl.class);

	@Override
	@Transactional
	public Material insertMaterialEntity(Material entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setEuser(user.getId());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setPstate("1");
		return materialDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Material editMaterialEntity(Material entity, LoginUser user) {
		Material entity2 = materialDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setTitle(entity.getTitle());
		entity2.setText(entity.getText());
		entity2.setPcontent(entity.getPcontent());
		//entity2.setPstate(entity.getPstate());
		materialDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteMaterialEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		materialDaoImpl.deleteEntity(materialDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Material getMaterialEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return materialDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createMaterialSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "( SELECT A.ID AS ID, A.TITLE AS TITLE, COUNT(B.ID) AS RFNUM, A.CTIME AS CTIME, A.CUSER AS CUSER, C. NAME AS USERNAME FROM WTS_MATERIAL A LEFT JOIN WTS_SUBJECT B ON A.ID = B.MATERIALID LEFT JOIN ALONE_AUTH_USER C ON C.ID = A.CUSER GROUP BY A.ID ) T",
				"ID,TITLE,RFNUM,CTIME,USERNAME");
		return dbQuery;
	}

}
