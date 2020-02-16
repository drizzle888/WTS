package com.farm.authority.service.impl;

import com.farm.authority.domain.Outuser;
import com.farm.authority.domain.User;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.authority.dao.OutuserDaoInter;
import com.farm.authority.service.OutuserServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：外部账户服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class OutuserServiceImpl implements OutuserServiceInter {
	@Resource
	private OutuserDaoInter outuserDaoImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(OutuserServiceImpl.class);

	@Override
	@Transactional
	public Outuser insertOutuserEntity(Outuser entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return outuserDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Outuser editOutuserEntity(Outuser entity) {
		Outuser entity2 = outuserDaoImpl.getEntity(entity.getId());
		entity2.setAccountname(entity.getAccountname());
		entity2.setAccountid(entity.getAccountid());
		entity2.setUserid(entity.getUserid());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		entity2.setCtime(entity.getCtime());
		entity2.setId(entity.getId());
		outuserDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteOutuserEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		outuserDaoImpl.deleteEntity(outuserDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Outuser getOutuserEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return outuserDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createOutuserSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "ALONE_AUTH_OUTUSER a left join ALONE_AUTH_USER b on a.userid=b.id",
				"a.ID as ID,ACCOUNTNAME,ACCOUNTID,USERID,a.PCONTENT as PCONTENT,a.PSTATE as PSTATE,a.CTIME as CTIME,NAME");
		return dbQuery;
	}

	// ----------------------------------------------------------------------------------
	public OutuserDaoInter getOutuserDaoImpl() {
		return outuserDaoImpl;
	}

	public void setOutuserDaoImpl(OutuserDaoInter dao) {
		this.outuserDaoImpl = dao;
	}

	@Override
	@Transactional
	public LoginUser getUserByAccountId(String outuserid, String name, String content) {
		// 通过外部用户id获得本地用户(如果没有则在关联表中创建对照关系，默认先不绑定用户)
		// 在表中找关联用户
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("ACCOUNTID", outuserid, "="));
		List<Outuser> outusers = outuserDaoImpl.selectEntitys(rules);
		User user = null;
		if (outusers != null && outusers.size() > 0) {
			// 存在，查找用户id
			Outuser outuser = outusers.get(0);
			if (outuser.getUserid() != null && !outuser.getUserid().isEmpty()) {
				user = userServiceImpl.getUserEntity(outuser.getUserid());
			}
		} else {
			// 创建关联关系实体，但是不绑定用户（用户后期绑定）
			Outuser outuser = new Outuser();
			outuser.setAccountid(outuserid);
			outuser.setAccountname(name);
			outuser.setCtime(TimeTool.getTimeDate14());
			outuser.setPcontent(content);
			outuser.setPstate("1");
			insertOutuserEntity(outuser, null);
		}
		if (user != null && user.getState().equals("2")) {
			user = null;
		}
		return user;
	}

	@Override
	@Transactional
	public Outuser getOutuserByAccountId(String outuserid) {
		// 通过外部用户id获得本地用户(如果没有则在关联表中创建对照关系，默认先不绑定用户)
		// 在表中找关联用户
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("ACCOUNTID", outuserid, "="));
		List<Outuser> outusers = outuserDaoImpl.selectEntitys(rules);
		if (outusers.size() > 0) {
			return outusers.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public Outuser getOutuserByUserid(String readUserId, String contentLimitlike) {
		List<DBRule> rules = new ArrayList<DBRule>();
		rules.add(new DBRule("USERID", readUserId, "="));
		rules.add(new DBRule("PCONTENT", contentLimitlike, "like"));
		rules.add(new DBRule("PSTATE", "1", "="));
		List<Outuser> outusers = outuserDaoImpl.selectEntitys(rules);
		if (outusers.size() > 0) {
			return outusers.get(0);
		}
		return null;
	}

}
