package com.farm.core.inter;

import java.util.Map;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.Message;

/**
 * 发送用户消息接口
 * 
 * @author wangdong
 *
 */
public interface UserMessageHandleInter {
	/**
	 * 发送消息
	 * 
	 * @param readUserId
	 *            收件人
	 * @param text
	 *            内容
	 * @param title
	 *            主题
	 * @param note
	 *            备注
	 * @param sendUser
	 *            发送人
	 * @param context
	 *            配置文件中传入的参数
	 */
	public void sendMessageHandler(Message message, String readUserId, String note, LoginUser sendUser,
			Map<String, String> context);

}
