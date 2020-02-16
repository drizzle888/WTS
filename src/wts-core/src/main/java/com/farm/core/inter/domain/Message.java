package com.farm.core.inter.domain;

import java.util.ArrayList;
import java.util.List;

import com.farm.core.message.MessageTypeFactory;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;
import com.farm.util.web.FarmHtmlUtils;

/**
 * 用户消息
 * 
 * @author lenovo
 *
 */
public class Message {
	/**
	 * 主题
	 */
	private FormatString title;
	/**
	 * 描述
	 */
	private FormatString text;
	/**
	 * 超链接
	 */
	private List<LinkMessage> links;

	/**
	 * 消息类型
	 */
	private MessageType type;

	// ----------------------------------
	/**
	 * 获得消息类型
	 * 
	 * @return
	 */
	public String getTypeKey() {
		return type.getKey().name();
	}

	/**
	 * 获得所有该消息的抄送人
	 * 
	 * @return
	 */
	public List<String> getSenders() {
		return type.getSenders();
	}

	/**
	 * 消息类型key ，与messageType类中得key对应
	 * 
	 * @param key
	 */
	public Message(TYPE_KEY key) {
		type = MessageTypeFactory.getInstance().getType(key.name());
	}

	/**
	 * 获得标题
	 * 
	 * @return
	 */
	public String getTitle() {
		String returnTitle = title.toString();
		if (returnTitle.length() > 64) {
			returnTitle = FarmHtmlUtils.HtmlRemoveTag(returnTitle);
			if (returnTitle.length() > 64) {
				returnTitle = returnTitle.substring(0, 60) + "...";
			}
		}
		return returnTitle;
	}

	/**
	 * 该消息是否启用，是否发送
	 * 
	 * @return
	 */
	public boolean isSendAble() {
		return type.isAble();
	}

	/**
	 * 从messageType中加载标题模板
	 * 
	 * @return
	 */
	public FormatString initTitle() {
		this.title = new FormatString(type.getTitleModel());
		return this.title;
	}

	public String getText() {
		return text.toString();
	}

	/**
	 * 从messageType中加载内容模板
	 * 
	 * @return
	 */
	public FormatString initText() {
		this.text = new FormatString(type.getContentModel());
		return this.text;
	}

	public List<LinkMessage> getLinks() {
		return links;
	}

	public void setLinks(List<LinkMessage> links) {
		this.links = links;
	}

	public void addLink(LinkMessage link) {
		if (links == null) {
			links = new ArrayList<>();
		}
		links.add(link);
	}

	public String getLinkHtml() {
		String text = "";
		if (links != null) {
			for (LinkMessage link : links) {
				text = text + "<a class='wcp_doc_link' href='" + link.getUrl()
						+ "'><span class='glyphicon glyphicon-link'></span>&nbsp;" + link.getText() + "</a>&nbsp;";
			}
			text = "<br/><b>[" + text + "]</b>";
		}
		return text;
	}
}
