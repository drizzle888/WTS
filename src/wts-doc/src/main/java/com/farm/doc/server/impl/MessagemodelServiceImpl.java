package com.farm.doc.server.impl;

import com.farm.doc.domain.Messagemodel;
import com.farm.doc.domain.Messagesender;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.farm.doc.dao.MessagemodelDaoInter;
import com.farm.doc.dao.MessagesenderDaoInter;
import com.farm.doc.server.MessagemodelServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.MessageType;
import com.farm.core.message.MessageTypeFactory;

/* *
 *功能：消息模板服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class MessagemodelServiceImpl implements MessagemodelServiceInter {
	@Resource
	private MessagemodelDaoInter messagemodelDaoImpl;
	@Resource
	private MessagesenderDaoInter messagesenderDaoImpl;
	private static final Logger log = Logger.getLogger(MessagemodelServiceImpl.class);

	@Override
	@Transactional
	public Messagemodel insertMessagemodelEntity(Messagemodel entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return messagemodelDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Messagemodel editMessagemodelEntity(Messagemodel entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		Messagemodel entity2 = messagemodelDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setContentmodel(entity.getContentmodel());
		entity2.setTypekey(entity.getTypekey());
		entity2.setOverer(entity.getOverer());
		entity2.setTitlemodel(entity.getTitlemodel());
		entity2.setPcontent(entity.getPcontent());
		entity2.setTitle(entity.getTitle());
		entity2.setPstate(entity.getPstate());
		entity2.setEuser(entity.getEuser());
		entity2.setEusername(entity.getEusername());
		entity2.setCuser(entity.getCuser());
		entity2.setCusername(entity.getCusername());
		entity2.setCtime(entity.getCtime());
		entity2.setEtime(entity.getEtime());
		entity2.setId(entity.getId());
		messagemodelDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteMessagemodelEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		messagemodelDaoImpl.deleteEntity(messagemodelDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Messagemodel getMessagemodelEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return messagemodelDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createMessagemodelSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"( SELECT ID, CONTENTMODEL, TYPEKEY, OVERER, TITLEMODEL, PCONTENT, TITLE, PSTATE, EUSER, EUSERNAME, CUSER, CUSERNAME, CTIME, ETIME, ( SELECT count(id) FROM FARM_MESSAGE_SENDER WHERE modelid = FARM_MESSAGE_MODEL.id ) num FROM FARM_MESSAGE_MODEL ) a",
				"ID,CONTENTMODEL,TYPEKEY,OVERER,TITLEMODEL,PCONTENT,TITLE,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,CTIME,ETIME,NUM");
		return dbQuery;
	}

	@Override
	@Transactional
	public void initModel(LoginUser currentUser) {
		// 先删除表中全部数据
		messagesenderDaoImpl.deleteEntitys(DBRuleList.getInstance().toList());
		messagemodelDaoImpl.deleteEntitys(DBRuleList.getInstance().toList());
		// 从系统中加载默认数据
		for (String key : MessageTypeFactory.getInstance().getKeys()) {
			MessageType type = MessageTypeFactory.getInstance().getBaseType(key);
			Messagemodel model = new Messagemodel();
			model.setContentmodel(type.getContentModel());
			model.setCtime(TimeTool.getTimeDate14());
			model.setCuser(currentUser.getId());
			model.setCusername(currentUser.getName());
			model.setEtime(TimeTool.getTimeDate14());
			model.setEuser(currentUser.getId());
			model.setEusername(currentUser.getName());
			model.setOverer(type.getSenderDescribe());
			// model.setPcontent(pcontent);
			model.setPstate("1");
			model.setTitle(type.getTypeName());
			model.setTitlemodel(type.getTitleModel());
			model.setTypekey(type.getKey().name());
			messagemodelDaoImpl.insertEntity(model);
		}

	}

	@Override
	@Transactional
	public void editState(String modelid, boolean isAble, LoginUser currentUser) {
		Messagemodel model = messagemodelDaoImpl.getEntity(modelid);
		model.setPstate(isAble ? "1" : "0");
		messagemodelDaoImpl.editEntity(model);
	}

	@Override
	@Transactional
	public Messagemodel editMessagemodelEntity(String id, String titlemodel, String contentmodel, String pcontent,
			LoginUser currentUser) {
		Messagemodel entity2 = messagemodelDaoImpl.getEntity(id);
		entity2.setEuser(currentUser.getId());
		entity2.setEusername(currentUser.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setContentmodel(contentmodel);
		entity2.setTitlemodel(titlemodel);
		entity2.setPcontent(pcontent);
		messagemodelDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	public DataQuery createMessagemodelSendersQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"FARM_MESSAGE_SENDER a left join ALONE_AUTH_USER b on a.APPID=b.id left join ALONE_AUTH_USERORG c on b.id=c.userid left join ALONE_AUTH_ORGANIZATION d on c.organizationid=d.id",
				"a.id as ID,b.NAME as NAME,d.NAME as ORGNAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public void addModelSender(String modelid, String userid) {
		messagesenderDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("APPID", userid, "="))
				.add(new DBRule("MODELID", modelid, "=")).toList());
		Messagesender sender = new Messagesender();
		sender.setAppid(userid);
		sender.setModelid(modelid);
		sender.setType("1");
		messagesenderDaoImpl.insertEntity(sender);
	}

	@Override
	@Transactional
	public void delModelSender(String id) {
		messagesenderDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("ID", id, "=")).toList());
	}
}
