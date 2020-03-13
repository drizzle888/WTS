package com.wts.exam.service.impl;

import com.wts.exam.domain.RandomItem;
import com.wts.exam.domain.RandomStep;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.RandomItemDaoInter;
import com.wts.exam.dao.RandomStepDaoInter;
import com.wts.exam.service.RandomItemServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：答卷随机规则服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class RandomItemServiceImpl implements RandomItemServiceInter {
	@Resource
	private RandomItemDaoInter randomitemDaoImpl;
	@Resource
	private RandomStepDaoInter randomstepDaoImpl;
	private static final Logger log = Logger.getLogger(RandomItemServiceImpl.class);

	@Override
	@Transactional
	public RandomItem insertRandomitemEntity(RandomItem entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		return randomitemDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public RandomItem editRandomitemEntity(RandomItem entity, LoginUser user) {
		RandomItem entity2 = randomitemDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setName(entity.getName());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		// entity2.setCuser(entity.getCuser());
		// entity2.setCtime(entity.getCtime());
		// entity2.setId(entity.getId());
		randomitemDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteRandomitemEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		randomitemDaoImpl.deleteEntity(randomitemDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public RandomItem getRandomitemEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return randomitemDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createRandomitemSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"(select a.ID as ID,a.NAME as NAME,a.PCONTENT as PCONTENT,a.PSTATE as PSTATE,a.CUSER as CUSER,a.CTIME as CTIME,count(b.id) as NUM from WTS_RANDOM_ITEM a left join WTS_RANDOM_STEP b on b.itemid=a.id group by a.id) t ",
				"ID,NAME,PCONTENT,PSTATE,CUSER,CTIME,NUM");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<RandomItem> getLiveRandomItems() {
		return randomitemDaoImpl.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "1", "=")).toList());
	}

	@Override
	@Transactional
	public List<RandomStep> getSteps(String itemid) {
		return randomstepDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("ITEMID", itemid, "=")).toList());
	}

}
