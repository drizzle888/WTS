package com.farm.doc.controller;

import com.farm.doc.domain.Messagemodel;
import com.farm.doc.server.MessagemodelServiceInter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.message.MessageTypeFactory;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：消息模板控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/messagemodel")
@Controller
public class MessagemodelController extends WebUtils {
	private final static Logger log = Logger.getLogger(MessagemodelController.class);
	@Resource
	MessagemodelServiceInter messageModelServiceImpl;

	public MessagemodelServiceInter getMessagemodelServiceImpl() {
		return messageModelServiceImpl;
	}

	public void setMessagemodelServiceImpl(MessagemodelServiceInter messageModelServiceImpl) {
		this.messageModelServiceImpl = messageModelServiceImpl;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = messageModelServiceImpl.createMessagemodelSimpleQuery(query).search();
			result.runDictionary("0:禁用,1:可用", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/querySenders")
	@ResponseBody
	public Map<String, Object> querySenders(String modelId, DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addRule(new DBRule("MODELID", modelId, "="));
			DataResult result = messageModelServiceImpl.createMessagemodelSendersQuery(query).search();
			result.runDictionary("0:禁用,1:可用", "PSTATE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(Messagemodel entity, HttpSession session) {
		try {
			entity = messageModelServiceImpl.editMessagemodelEntity(entity.getId(), entity.getTitlemodel(),
					entity.getContentmodel(), entity.getPcontent(), getCurrentUser(session));
			MessageTypeFactory.getInstance().reLoadDbCache();
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 初始化消息模板
	 * 
	 * @return
	 */
	@RequestMapping("/initModel")
	@ResponseBody
	public Map<String, Object> initModel(HttpSession session) {
		try {
			messageModelServiceImpl.initModel(getCurrentUser(session));
			MessageTypeFactory.getInstance().reLoadDbCache();
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 刷新消息类型缓存
	 * 
	 * @return
	 */
	@RequestMapping("/reLoadCache")
	@ResponseBody
	public Map<String, Object> reLoadCache(HttpSession session) {
		try {
			MessageTypeFactory.getInstance().reLoadDbCache();
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 设置模板状态
	 * 
	 * @return
	 */
	@RequestMapping("/resetState")
	@ResponseBody
	public Map<String, Object> resetState(String ids, boolean isAble, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				messageModelServiceImpl.editState(id, isAble, getCurrentUser(session));
			}
			MessageTypeFactory.getInstance().reLoadDbCache();
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("doc/MessagemodelResult");
	}

	/**
	 * 设置抄送人
	 *
	 * @return
	 */
	@RequestMapping("/senders")
	public ModelAndView senders(RequestMode pageset, String id) {
		try {
			return ViewMode.getInstance().putAttr("modelId", id).returnModelAndView("doc/MessagemodelSenders");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("doc/MessagemodelSenders");
		}
	}

	/**
	 * 显示详细信息（修改或浏览时）
	 *
	 * @return
	 */
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
			switch (pageset.getOperateType()) {
			case (2): {// 修改模板
				Messagemodel model = messageModelServiceImpl.getMessagemodelEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("messageType",
								MessageTypeFactory.getInstance()
										.getBaseType(TYPE_KEY.valueOf(model.getTypekey()).name()))
						.putAttr("entity", model).returnModelAndView("doc/MessagemodelForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("doc/MessagemodelForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("doc/MessagemodelForm");
		}
	}

	/**
	 * 添加用户到抄送人
	 * 
	 * @return
	 */
	@RequestMapping("/addUserToModel")
	@ResponseBody
	public Map<String, Object> addUserToModel(String userids, String modelid, HttpSession session) {
		try {
			for (String id : parseIds(userids)) {
				messageModelServiceImpl.addModelSender(modelid,id);
			}
			MessageTypeFactory.getInstance().reLoadDbCache();
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
	/**
	 * 删除用户到抄送人
	 * 
	 * @return
	 */
	@RequestMapping("/delUserToModel")
	@ResponseBody
	public Map<String, Object> delUserToModel(String ids
			, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				messageModelServiceImpl.delModelSender(id);
			}
			MessageTypeFactory.getInstance().reLoadDbCache();
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}
}
