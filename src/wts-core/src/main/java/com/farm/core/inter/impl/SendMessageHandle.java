package com.farm.core.inter.impl;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.BusinessHandleInter;
import com.farm.core.inter.UserMessageHandleInter;
import com.farm.core.inter.domain.BusinessHandler;
import com.farm.core.inter.domain.Message;

/**
 * 消息发送事件
 * 
 * @author wangdong
 *
 */
public class SendMessageHandle extends BusinessHandleServer {
	/**
	 * 服务ID，用来获取实现类的关键字
	 */
	private static String serverId = "UserMessageHandleInterId";

	/**
	 * 获得实例
	 * 
	 * @param serverId
	 * @return
	 */
	public static SendMessageHandle getInstance() {
		SendMessageHandle obj = new SendMessageHandle();
		return obj;
	}

	/**
	 * 调用所有发送消息处理类
	 * 
	 * @param typeKey
	 *            消息类型的key
	 * @param message
	 * @param readUserId
	 * @param note
	 * @param sendUser
	 */
	public void sendMessageHandlers(final Message message, final String readUserId,
			final String note, final LoginUser sendUser) {
		runAll(serverId, new BusinessHandleInter() {
			@Override
			public void execute(BusinessHandler impl) {
				UserMessageHandleInter hander = (UserMessageHandleInter) impl.getBeanImpl();
				hander.sendMessageHandler(message, readUserId, note, sendUser, impl.getContext());
			}
		});
	}
}
