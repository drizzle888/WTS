package com.farm.doc.server.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.Message;
import com.farm.core.inter.impl.SendMessageHandle;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.core.time.TimeTool;
import com.farm.doc.dao.UsermessageDaoInter;
import com.farm.doc.domain.Usermessage;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.doc.util.HtmlUtils;

/* *
 *功能：用户消息服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class UsermessageServiceImpl implements UsermessageServiceInter {
	private static final Logger log = Logger.getLogger(UsermessageServiceImpl.class);
	@Resource
	private UsermessageDaoInter usermessageDaoImpl;

	@Override
	@Transactional
	public Usermessage insertUsermessageEntity(Usermessage entity, LoginUser user) {
		entity.setCtime(TimeTool.getTimeDate12());
		if (user != null) {
			entity.setCuser(user.getId());
			entity.setCusername(user.getName());
		}
		entity.setPstate("1");
		return usermessageDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public Usermessage editUsermessageEntity(Usermessage entity, LoginUser user) {
		Usermessage entity2 = usermessageDaoImpl.getEntity(entity.getId());
		entity2.setContent(entity.getContent());
		entity2.setReadstate(entity.getReadstate());
		entity2.setReaduserid(entity.getReaduserid());
		entity2.setTitle(entity.getTitle());
		usermessageDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteUsermessageEntity(String id, LoginUser user) {

		usermessageDaoImpl.deleteEntity(usermessageDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public Usermessage getUsermessageEntity(String id) {

		if (id == null) {
			return null;
		}
		return usermessageDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createUsermessageSimpleQuery(DataQuery query) {

		DataQuery dbQuery = DataQuery.init(query,
				"FARM_USERMESSAGE USERMESSAGE " + "LEFT JOIN ALONE_AUTH_USER USER ON USERMESSAGE.READUSERID = USER.ID",
				"USERMESSAGE.ID AS ID,USERMESSAGE.CUSERNAME AS SENDUSERNAME,USERMESSAGE.CTIME AS USERMESSAGECTIME, USERMESSAGE.READUSERID AS READUSERID,USERMESSAGE.CONTENT AS CONTENT,"
						+ "USERMESSAGE.TITLE AS TITLE,USERMESSAGE.READSTATE AS READSTATE,USERMESSAGE.PCONTENT AS PCONTENT,"
						+ "USERMESSAGE.PSTATE AS PSTATE,USERMESSAGE.CUSERNAME AS CUSERNAME,USERMESSAGE.CUSER AS CUSER,"
						+ "USERMESSAGE.CTIME AS CTIME, USER.NAME AS READUSERNAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public void setRead(String id) {
		Usermessage entity = usermessageDaoImpl.getEntity(id);
		entity.setReadstate("1");
	}

	@Override
	@Transactional
	public void sendMessage(Message message, String readUserId, String note, LoginUser sendUser) {
		List<String> readUsers = new ArrayList<>();
		readUsers.add(readUserId);
		finalSendMessage(message, readUsers, note, sendUser);
	}

	@Override
	@Transactional
	public void sendMessage(Message message, String readUserId, LoginUser sendUser) {
		List<String> readUsers = new ArrayList<>();
		readUsers.add(readUserId);
		finalSendMessage(message, readUsers, null, sendUser);
	}

	@Override
	@Transactional
	public void sendMessage(Message message, String readUserId) {
		List<String> readUsers = new ArrayList<>();
		readUsers.add(readUserId);
		finalSendMessage(message, readUsers, null, null);
	}

	@Override
	@Transactional
	public void sendMessage(Message message, List<String> readUserIds, LoginUser curentUser) {
		finalSendMessage(message, readUserIds, null, curentUser);
	}

	@Override
	@Transactional
	public void sendMessage(Message message, List<String> userids) {
		finalSendMessage(message, userids, null, null);
	}

	/**
	 * 所有的发送消息都直接调用这个方法，为了防止抄送人的消息被重复发送
	 * 
	 * @param message
	 * @param readUserIds
	 * @param note
	 * @param sendUser
	 */
	private void finalSendMessage(Message message, List<String> readUserIds, String note, LoginUser sendUser) {
		if (message.isSendAble()) {
			for (String readUserId : readUserIds) {
				SendMessageHandle.getInstance().sendMessageHandlers(message, readUserId, note, sendUser);
			}
			for (String readUserId : message.getSenders()) {
				SendMessageHandle.getInstance().sendMessageHandlers(message, readUserId, "消息抄送", sendUser);
			}
		}
	}

	@Override
	public DataResult getMyMessageByUser(String userId, int pageSize, Integer currPage) {
		DataQuery query = createUsermessageSimpleQuery(null);
		query.addRule(new DBRule("USERMESSAGE.READUSERID", userId, "="));
		query.addSort(new DBSort("USERMESSAGE.CTIME", "DESC"));
		query.setCurrentPage(currPage);
		query.setPagesize(pageSize);
		try {
			return query.search();
		} catch (SQLException e) {
			log.error(e + e.getMessage(), e);
		}
		return null;
	}

	@Override
	@Transactional
	public int setReadAllMessageByUser(LoginUser currentUser) {
		DataResult result = getUnreadMessagesByUser(currentUser.getId(), 1000, 1);
		for (Map<String, Object> node : result.getResultList()) {
			setRead((String) node.get("ID"));
		}
		return result.getTotalSize();
	}

	@Override
	public DataResult getUnreadMessagesByUser(String userId, int pageSize, int currPage) {
		DataQuery query = createUsermessageSimpleQuery(null);
		query.addRule(new DBRule("USERMESSAGE.READUSERID", userId, "="));
		query.addRule(new DBRule("USERMESSAGE.READSTATE", "0", "="));
		query.addSort(new DBSort("USERMESSAGE.CTIME", "DESC"));
		query.setCurrentPage(currPage);
		query.setPagesize(pageSize);
		try {
			DataResult result = query.search();
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					String title = (String) row.get("TITLE");
					row.put("NOTAGTITLE", HtmlUtils.HtmlRemoveTag(title));
				}
			});
			return result;
		} catch (SQLException e) {
			log.error(e + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public DataQuery createUserConsolesQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"ALONE_AUTH_USER A LEFT JOIN ALONE_AUTH_USERORG B ON A.ID = B.USERID LEFT JOIN ALONE_AUTH_ORGANIZATION C ON B.ORGANIZATIONID = C.ID LEFT JOIN ALONE_AUTH_USERPOST D ON D.USERID = A.ID LEFT JOIN ALONE_AUTH_POST E ON E.ID = D.POSTID",
				"A.ID AS ID, A. NAME AS NAME, A.STATE AS STATE, A.LOGINNAME AS LOGINNAME, A.TYPE AS TYPE, C. NAME AS ORGNAME, C.ID AS ORGID,C.TREECODE AS TREECODE");
		dbQuery.addRule(new DBRule("A.STATE", "1", "="));
		dbQuery.setDistinct(true);
		return dbQuery;
	}
}
