package com.farm.core.message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.farm.core.inter.domain.MessageType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

/**
 * 消息类型得工厂类
 * 
 * @author macpl
 *
 */
public class MessageTypeFactory {
	private Map<String, MessageType> allType;// 所有的默认类型
	private Map<String, MessageType> dbTypes = new HashMap<>();// 数据中加载的用户类型
	private Map<String, List<String>> senders = new HashMap<>();// 抄送人
	private static final Logger log = Logger.getLogger(MessageType.class);
	private static MessageTypeFactory obj = null;

	/**
	 * 获得原始类型信息
	 * 
	 * @param key
	 * @return
	 */
	public MessageType getBaseType(String key) {
		return allType.get(key);
	}

	/**
	 * 获得所有得消息类型KEY
	 * 
	 * @return
	 */
	public Set<String> getKeys() {
		return allType.keySet();
	}

	public enum TYPE_KEY {
		/**
		 * 用户申请加入小组 ,所有小组管理员 OK--OK
		 */
		group_join_apply,
		//
		/**
		 * 用户加入小组 ,所有小组管理员 OK--OK
		 */
		group_joined, //
		/**
		 * 用户退出小组 ,所有小组管理员 OK--OK
		 */
		group_unjoined, //
		/**
		 * 小组加入申请通过审核 ,加入小组人员 OK--OK
		 */
		group_joinpass, //
		/**
		 * 推送知识 ,被推送者 OK--OK
		 */
		pushknow, //
		/**
		 * 知识审核通过 ,知识提交者 OK--OK
		 */
		audit_knowpass, //
		/**
		 * 知识审核未通过 ,知识提交者 OK--OK
		 */
		audit_knowpunpass, //
		/**
		 * 知识有更新 ,知识关注者 OK--OK
		 */
		know_update, //
		/**
		 * 知识发布 ,无人接受，只发送给抄送人
		 */
		know_created, //
		/**
		 * 新的知识审核任务 ,审核人员OK--OK
		 */
		audit_tast, //
		/**
		 * 一条评论被回复 ,评论发布者OK--OK
		 */
		comment_reply, //
		/**
		 * 收到知识评论 ,知识关注者 OK--OK
		 */
		comment_know, //
		/**
		 * 一条问题回答被回复 ,回答发布者 --OK
		 */
		answer_comment, //
		/**
		 * 邀请回答问题 ,被邀请人--OK
		 */
		invite_answer, //
		/**
		 * 问题收到回答 ,提问者--OK
		 */
		question_answer, //
		/**
		 * 一条留言被回复 ,留言发布者(如果回复的是自己的留言则不发消息)--OK
		 */
		usermsg_reply, //
		/**
		 * 用户消息（后台手动发送） ,手动设置接收人--OK
		 */
		user_message, //
		/**
		 * 留言板收到留言 ,留言板主人--OK
		 */
		usermsg_comment, //
		/**
		 * 用户首次登录收到消息 ,登录人--OK
		 */
		user_login, //
		/**
		 * 新用户注册待审核 ,超级管理员
		 */
		user_registe, //
		/**
		 * 越权审核，提交审核
		 */
		op_audit_apply, //
		/**
		 * 越权审核，完成审核
		 */
		op_audited;
	}

	/**
	 * 获得一个消息类型得实例（加载用户配置参数得类型）
	 * 
	 * @param key
	 * @return
	 */
	public MessageType getType(String key) {
		// 获得默认的消息类型
		MessageType type = allType.get(key.toLowerCase());
		type.SetAble(true);
		MessageType returnType = new MessageType(type.getTypeName(), type.getKey(), type.getTitleModel(),
				type.getContentModel(), type.getTitleDescribe(), type.getContentDescribe(), type.getSenderDescribe());
		returnType.SetAble(type.isAble());
		{// 加载数据中的消息类型（被用户配置覆盖的）
			MessageType dbtype = dbTypes.get(key);
			if (dbtype != null) {
				returnType.SetAble(dbtype.isAble());
				returnType.setContentModel(dbtype.getContentModel());
				returnType.setTitleModel(dbtype.getTitleModel());
			}
			// 加载消息的抄送人
			List<String> sendrs = senders.get(key);
			if (sendrs != null) {
				returnType.setSenders(sendrs);
			}
		}

		return returnType;
	}

	/**
	 * 重新加载数据库配置
	 * 
	 * @throws SQLException
	 */
	public void reLoadDbCache() throws SQLException {
		{
			DataQuery query = DataQuery.getInstance(1, "TYPEKEY,TITLEMODEL,CONTENTMODEL,PSTATE", "FARM_MESSAGE_MODEL");
			DataResult result = query.setPagesize(1000).search();
			dbTypes.clear();
			for (Map<String, Object> node : result.getResultList()) {
				String STATE = (String) node.get("PSTATE");
				String CONTENTMODEL = (String) node.get("CONTENTMODEL");
				String TITLEMODEL = (String) node.get("TITLEMODEL");
				String TYPEKEY = (String) node.get("TYPEKEY");
				MessageType type = new MessageType(null, TYPE_KEY.valueOf(TYPEKEY), TITLEMODEL, CONTENTMODEL, null,
						null, null);
				type.SetAble(STATE.equals("1"));
				dbTypes.put((String) node.get("TYPEKEY"), type);
			}
		}
		{
			DataQuery query = DataQuery.getInstance(1, "a.APPID as APPID,a.TYPE as TYPE,b.TYPEKEY as TYPEKEY",
					"FARM_MESSAGE_SENDER a left join FARM_MESSAGE_MODEL b on a.MODELID=b.id");
			DataResult result = query.setPagesize(1000).search();
			senders.clear();
			for (Map<String, Object> node : result.getResultList()) {
				String key = (String) node.get("TYPEKEY");
				String apptype = (String) node.get("TYPE");
				String appid = (String) node.get("APPID");
				List<String> list = senders.get(key);
				if (list == null) {
					list = new ArrayList<>();
					senders.put(key, list);
				}
				if (apptype.equals("1")) {
					list.add(appid);
				} else {
					throw new RuntimeException("该消息类型没有实现方法，请完善程序!FARM_MESSAGE_SENDER.TYPE:" + apptype);
				}
			}
		}
	}

	/**
	 * 获得工厂实例
	 * 
	 * @return
	 */
	public static MessageTypeFactory getInstance() {
		if (obj == null) {
			obj = new MessageTypeFactory();
			try {
				obj.reLoadDbCache();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			log.info("初始化MEssageType對象的alltype...");
			obj.allType = new HashMap<>();
			// 用户申请加入小组 ,所有小组管理员 OK--OK
			obj.addMessageType(new MessageType("申请加入小组", TYPE_KEY.group_join_apply, "用户申请加入小组?",
					"有用户?申请加入?小组,申请备注：?。请进入小组首页\\成员管理\\进行审核", "?小组名称", "?用户名称，?小组名称,?申请备注", "所有小组管理员"));
			// 用户加入小组 ,所有小组管理员 OK--OK
			obj.addMessageType(new MessageType("用户加入小组", TYPE_KEY.group_joined, "用户加入小组?", "有用户?加入?小组", "?小组名称",
					"?用户名称,?小组名称", "所有小组管理员"));
			// 用户退出小组 ,所有小组管理员 OK--OK
			obj.addMessageType(new MessageType("用户退出小组", TYPE_KEY.group_unjoined, "用户退出小组?", "有用户?退出?小组", "?小组名称",
					"?用户名称，?小组名称", "所有小组管理员"));
			// 小组加入申请通过审核 ,加入小组人员 OK--OK
			obj.addMessageType(new MessageType("加入小组申请通过", TYPE_KEY.group_joinpass, "加入?小组申请通过", "小组?已经同意你的加入申请",
					"?小组名称", "?小组名称", "申请人"));
			// 推送知识 ,被推送者 OK--OK
			obj.addMessageType(new MessageType("知识推送", TYPE_KEY.pushknow, "知识推送来自?:?", "收到推送知识:?",
					"?用户名称，?被推送的多条知识名称逗号分隔", "?被推送的多条知识名称逗号分隔", "所有被推送人"));
			// 知识审核通过 ,知识提交者 OK--OK
			obj.addMessageType(new MessageType("知识审核通过", TYPE_KEY.audit_knowpass, "知识审核通过?", "知识?的一个版本审核通过", "?知识名称",
					"?知识名称", "知识提交者"));
			// 知识审核未通过 ,知识提交者 OK--OK
			obj.addMessageType(new MessageType("知识审核未通过", TYPE_KEY.audit_knowpunpass, "知识审核未通过?", "知识?的一个版本审核未通过",
					"?知识名称", "?知识名称", "知识提交者"));
			// 知识有更新 ,知识关注者 OK--OK
			obj.addMessageType(
					new MessageType("知识更新", TYPE_KEY.know_update, "知识更新?", "知识?有新版本提交", "?知识名称", "?知识名称", "知识关注者"));
			// 新知识发布 ,无人接收，只发送给抄送者
			obj.addMessageType(
					new MessageType("新知识发布", TYPE_KEY.know_created, "知识发布?", "新的知识?发布", "?知识名称", "?知识名称", "无"));
			// 新的知识审核任务 ,审核人员OK--OK
			obj.addMessageType(
					new MessageType("新审核任务", TYPE_KEY.audit_tast, "新审核任务?", "知识?的一个版本需要审核", "?知识名称", "?知识名称", "审核人员"));
			// 一条评论被回复 ,评论发布者OK--OK
			obj.addMessageType(new MessageType("知识评论被回复", TYPE_KEY.comment_reply, "知识评论回复?", "知识?收到一条回复:?", "?知识名称",
					"?知识名称，?回复内容", "评论发布者"));
			// 收到知识评论 ,知识关注者 OK--OK
			obj.addMessageType(new MessageType("知识被评论", TYPE_KEY.comment_know, "知识评论?", "知识?收到一条评论:?", "?知识名称",
					"?知识名称，?评论内容", "知识关注者"));
			// 一条问题回答被回复 ,回答发布者 --OK
			obj.addMessageType(new MessageType("问答被回复", TYPE_KEY.answer_comment, "问答回复?", "问题?的回答收到一条回复:?", "?问答名称",
					"?问答名称,?回复内容", "回答发布者"));
			// 邀请回答问题 ,被邀请人--OK
			obj.addMessageType(new MessageType("邀请回答问题", TYPE_KEY.invite_answer, "问答邀请?", "?邀请您回答问题：?", "?问答名称",
					"?用户名称，?问答名称", "被邀请人"));
			// 问题收到回答 ,提问者--OK
			obj.addMessageType(new MessageType("问题有新回答", TYPE_KEY.question_answer, "问题新回答?", "问题?,收到一条新的回答", "?问答名称",
					"?问答名称", "提问者"));
			// 一条留言被回复 ,留言发布者(如果回复的是自己的留言则不发消息)--OK
			obj.addMessageType(new MessageType("留言被回复", TYPE_KEY.usermsg_reply, "留言回复?", "关于留言?的回复信息:?", "?留言类型（小组/用户）",
					"?留言类型（小组/用户），?信息内容", "留言发布者"));
			// 用户消息（后台手动发送） ,手动设置接收人--OK
			obj.addMessageType(
					new MessageType("手动用户消息", TYPE_KEY.user_message, "消息来自?:?", "?", "?用户姓名，?信息标题", "?实际消息内容", "接收人"));
			// 留言板收到留言 ,留言板主人--OK
			obj.addMessageType(new MessageType("用戶留言板新留言", TYPE_KEY.usermsg_comment, "来自?的用戶留言", "内容:?", "?留言者名称",
					"?留言内容", "留言板主人"));
			// 用户首次登录收到消息 ,登录人--OK
			obj.addMessageType(new MessageType("新用户首次登陆", TYPE_KEY.user_login, "欢迎您首次登录，请阅仔细读本消息", "?", "无",
					"?配置文件中读取的信息", "登录人"));
			// 新用户注册待审核 ,超级管理员
			obj.addMessageType(new MessageType("新用户等待审核", TYPE_KEY.user_registe, "新注册用户等待审核",
					"有一个注册用户等待审核，登录系统后台【用户管理】菜单可修改用户状态为可用。", "无", "无", "超级管理员"));
			// 越权审核，超级管理员接收消息 OK（内部处理）
			obj.addMessageType(
					new MessageType("越权操作审核提交", TYPE_KEY.op_audit_apply, "操作权限等待审核中", "等待审核：?", "无", "?审核类型", "超级管理员"));
			// 越权审核，超级管理员接收消息 OK（内部处理）
			obj.addMessageType(new MessageType("越权操作审核完成", TYPE_KEY.op_audited, "操作申请审核? ", "请进入<我的授权申请>菜单查看相关信息。",
					"?通过|被拒绝", "无", "超级管理员"));
		}
		return obj;
	}

	private void addMessageType(MessageType type) {
		allType.put(type.getKey().name().toLowerCase(), type);
	}

}
