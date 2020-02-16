package com.farm.core.inter.domain;

import java.util.ArrayList;
import java.util.List;

import com.farm.core.message.MessageTypeFactory.TYPE_KEY;

/**
 * 用戶消息類型
 * 
 * @author macpl
 *
 */
public class MessageType {
	/**
	 * 所有可用得消息类型
	 */
	private TYPE_KEY key;// 类型key
	private String typeName;// 类型名称
	private String titleModel;// 标题模板
	private String contentModel;// 内容模板
	private String titleDescribe;// 参数说明
	private String contentDescribe;// 内容模板參數説明
	private String senderDescribe;// 接收人描述
	private boolean isAble;
	private List<String> senders = new ArrayList<>();// 消息抄送人

	@SuppressWarnings("unused")
	private MessageType() {
		// 私有化無參構造函數
	}

	public MessageType(String typeName, TYPE_KEY key, String titleModel, String contentModel, String titleDescribe,
			String contentDescribe, String senderDescribe) {
		this.key = key;
		this.typeName = typeName;
		this.titleModel = titleModel;
		this.contentModel = contentModel;
		this.titleDescribe = titleDescribe;
		this.contentDescribe = contentDescribe;
		this.senderDescribe = senderDescribe;
	}
	// -------------------------------

	/**
	 * 获得key
	 * 
	 * @return
	 */
	public TYPE_KEY getKey() {
		return key;
	}

	/**
	 * 获得类型名称
	 * 
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 获得模型标题
	 * 
	 * @return
	 */
	public String getTitleModel() {
		return titleModel;
	}

	/**
	 * 获得模型内容
	 * 
	 * @return
	 */
	public String getContentModel() {
		return contentModel;
	}

	/**
	 * 获得标题参数描述
	 * 
	 * @return
	 */
	public String getTitleDescribe() {
		return titleDescribe;
	}

	/**
	 * 获得内容参数描述
	 * 
	 * @return
	 */
	public String getContentDescribe() {
		return contentDescribe;
	}

	/**
	 * 消息是否啓用，允许发送？
	 * 
	 * @return
	 */
	public boolean isAble() {
		return isAble;
	}

	public void SetAble(boolean able) {
		isAble = able;
	}

	public String getSenderDescribe() {
		return senderDescribe;
	}

	public void setTitleModel(String titleModel) {
		this.titleModel = titleModel;
	}

	public void setContentModel(String contentModel) {
		this.contentModel = contentModel;
	}

	public List<String> getSenders() {
		return senders;
	}

	public void setSenders(List<String> senders) {
		this.senders = senders;
	}

}
