package com.wts.exam.service.impl;

import com.wts.exam.domain.RandomStep;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.RandomStepDaoInter;
import com.wts.exam.service.RandomStepServiceInter;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：答卷随机步骤服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class RandomStepServiceImpl implements RandomStepServiceInter {
	@Resource
	private RandomStepDaoInter randomstepDaoImpl;

	private static final Logger log = Logger.getLogger(RandomStepServiceImpl.class);

	@Override
	@Transactional
	public RandomStep insertRandomstepEntity(RandomStep entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return randomstepDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public RandomStep editRandomstepEntity(RandomStep entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		RandomStep entity2 = randomstepDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setItemid(entity.getItemid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setName(entity.getName());
		entity2.setSort(entity.getSort());
		entity2.setSubpoint(entity.getSubpoint());
		entity2.setSubnum(entity.getSubnum());
		entity2.setTiptype(entity.getTiptype());
		entity2.setTypeid(entity.getTypeid());
		entity2.setId(entity.getId());
		randomstepDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteRandomstepEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		randomstepDaoImpl.deleteEntity(randomstepDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public RandomStep getRandomstepEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return randomstepDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createRandomstepSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_RANDOM_STEP a left join WTS_SUBJECT_TYPE b on a.typeid=b.id left join WTS_RANDOM_ITEM c on c.id=a.ITEMID",
				"a.ID as ID,a.ITEMID as ITEMID,c.name as ITEMNAME,b.name as typename,a.PCONTENT as PCONTENT,a.NAME as NAME,a.SORT as SORT,a.SUBPOINT as SUBPOINT,a.SUBNUM as SUBNUM,a.TIPTYPE as TIPTYPE,a.TYPEID as TYPEID");
		dbQuery.addDefaultSort(new DBSort("a.sort", "asc"));
		return dbQuery;
	}

}
