package com.farm.doc.handle;

import java.util.Map;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.UserMessageHandleInter;
import com.farm.core.inter.domain.Message;
import com.farm.core.time.TimeTool;
import com.farm.doc.domain.Usermessage;
import com.farm.doc.server.UsermessageServiceInter;
import com.farm.util.spring.BeanFactory;

public class WcpMessageHandle implements UserMessageHandleInter {

	@Override
	public void sendMessageHandler(Message message, String readUserId, String note, LoginUser sendUser,
			Map<String, String> context) {
		UsermessageServiceInter docRunInfoIMP = (UsermessageServiceInter) BeanFactory.getBean("usermessageServiceImpl");
		{// 知识库内部用户消息记录
			Usermessage usermessage = new Usermessage();
			usermessage.setContent(message.getText() + message.getLinkHtml());
			usermessage.setCtime(TimeTool.getTimeDate14());
			if (sendUser != null) {
				usermessage.setCuser(sendUser.getId());
				usermessage.setCusername(sendUser.getName());
			} else {
				usermessage.setCuser("SYS");
				usermessage.setCusername("系统消息");
			}
			if (note != null) {
				usermessage.setPcontent(note);
			}
			usermessage.setPstate("1");
			usermessage.setReadstate("0");
			usermessage.setReaduserid(readUserId);
			usermessage.setTitle(message.getTitle());
			docRunInfoIMP.insertUsermessageEntity(usermessage, sendUser);
		}

	}

}
