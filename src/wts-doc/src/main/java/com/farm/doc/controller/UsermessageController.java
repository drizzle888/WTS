package com.farm.doc.controller;

import com.farm.doc.domain.Usermessage;
import com.farm.doc.server.UsermessageServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;

import com.farm.web.easyui.EasyUiUtils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import com.farm.authority.FarmAuthorityService;
import com.farm.authority.domain.Organization;
import com.farm.authority.domain.Post;
import com.farm.authority.service.OrganizationServiceInter;
import com.farm.authority.service.UserServiceInter;
import com.farm.core.page.RequestMode;
import com.farm.core.auth.domain.LoginUser;
import com.farm.core.inter.domain.Message;
import com.farm.core.message.MessageTypeFactory.TYPE_KEY;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：用户消息控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/usermessage")
@Controller
public class UsermessageController extends WebUtils {
	private final static Logger log = Logger.getLogger(UsermessageController.class);
	@Resource
	private UsermessageServiceInter usermessageServiceImpl;
	@Resource
	private UserServiceInter userServiceImpl;
	@Resource
	private OrganizationServiceInter organizationServiceImpl;

	public UsermessageServiceInter getUsermessageServiceImpl() {
		return usermessageServiceImpl;
	}

	public void setUsermessageServiceImpl(UsermessageServiceInter usermessageServiceImpl) {
		this.usermessageServiceImpl = usermessageServiceImpl;
	}

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addDefaultSort(new DBSort("USERMESSAGECTIME", "DESC"));
			DataResult result = usermessageServiceImpl.createUsermessageSimpleQuery(query).search();
			result.runDictionary("0:未读,1:已读", "READSTATE");
			result.runformatTime("USERMESSAGECTIME", "yyyy-MM-dd HH:mm");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 查询结果集合(查询消息发送人员筛选)
	 * 
	 * @return
	 */
	@RequestMapping("/queryConsoles")
	@ResponseBody
	public Map<String, Object> queryConsoles(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			DBRule parentidRule = query.getAndRemoveRule("PARENTID");
			if (parentidRule != null && !StringUtils.isBlank(parentidRule.getValue())) {
				Organization org = organizationServiceImpl.getOrganizationEntity(parentidRule.getValue());
				if (org != null) {
					query.addRule(new DBRule("C.TREECODE", org.getTreecode(), "like-"));
				}
			}
			DataResult result = usermessageServiceImpl.createUserConsolesQuery(query).search();
			result.runDictionary("0:禁用,1:可用,2:删除,3:待审核", "STATE");
			result.runDictionary("1:系统用户,2:其他,3:超级用户,4:只读用户,5:知识用户,6:问答用户", "TYPE");
			final boolean isLimitPost = (query.getRule("E.NAME") != null);
			result.runHandle(new ResultsHandle() {
				@Override
				public void handle(Map<String, Object> row) {
					// 当查询条件中含有岗位时才展示用户岗位，为了提升效率
					if (isLimitPost) {
						try {
							List<Post> posts = userServiceImpl.getUserPosts(row.get("ID").toString());
							String postName = "";
							for (Post post : posts) {
								if (post != null) {
									postName = postName
											+ (postName.equals("") ? post.getName() : ("," + post.getName()));
								}
							}
							row.put("POSTNAME", postName);
						} catch (Exception e) {
							log.error(e + e.getMessage(), e);
							row.put("POSTNAME", "NONE");
						}
					} else {
						row.put("POSTNAME", "未查询岗位");
					}
					row.put("GROUPNAME", "未查询小组");
				}
			});
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
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
	public Map<String, Object> editSubmit(Usermessage entity, HttpSession session) {

		try {
			entity = usermessageServiceImpl.editUsermessageEntity(entity, getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(Usermessage entity, HttpSession session) {
		try {
			// entity = usermessageServiceImpl.insertUsermessageEntity(entity,
			// getCurrentUser(session));
			Message message = new Message(TYPE_KEY.user_message);
			message.initTitle().setString(getCurrentUser(session) == null ? "无" : getCurrentUser(session).getName())
					.setString(entity.getTitle());
			message.initText().setString(entity.getContent());
			usermessageServiceImpl.sendMessage(message, entity.getReaduserid(), getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				usermessageServiceImpl.deleteUsermessageEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 消息管理
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("doc/UsermessageResult");
	}

	/**
	 * 消息发送台
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/msConsole")
	public ModelAndView msConsole(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("doc/UsermessageConsoles");
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
			case (0): {// 查看
				Usermessage entity = usermessageServiceImpl.getUsermessageEntity(ids);

				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.putAttr("readusername", userServiceImpl.getUserEntity(entity.getReaduserid()).getName())
						.returnModelAndView("doc/UsermessageForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("doc/UsermessageForm");
			}
			case (2): {// 修改
				Usermessage entity = usermessageServiceImpl.getUsermessageEntity(ids);
				return ViewMode.getInstance().putAttr("pageset", pageset).putAttr("entity", entity)
						.putAttr("readusername", userServiceImpl.getUserEntity(entity.getReaduserid()).getName())
						.returnModelAndView("doc/UsermessageForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("doc/UsermessageForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("doc/UsermessageForm");
		}
	}

	/**
	 * 显示消息发送页面
	 *
	 * @return
	 */
	@RequestMapping("/sendpage")
	public ModelAndView sendpage(RequestMode pageset, String ids) {
		try {
			// 解析ids
			List<String> users = parseIds(ids);
			LoginUser user = FarmAuthorityService.getInstance().getUserById(users.get(0));
			return ViewMode.getInstance().putAttr("userDemo", user).putAttr("userIds", ids)
					.putAttr("userNum", users.size()).putAttr("pageset", pageset)
					.returnModelAndView("doc/UsermessageSendPage");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).putAttr("pageset", pageset)
					.returnModelAndView("doc/UsermessageSendPage");
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/sendMessage")
	@ResponseBody
	public Map<String, Object> sendMessage(Usermessage entity, String userids, HttpSession session) {
		try {
			// entity = usermessageServiceImpl.insertUsermessageEntity(entity,
			// getCurrentUser(session));
			// 校验数据, 校验主题, 校验用户id
			if (StringUtils.isBlank(entity.getTitle()) || parseIds(userids).size() <= 0) {
				throw new RuntimeException("参数校验失败：" + entity.getTitle() + ",ids=" + userids);
			}
			Message message = new Message(TYPE_KEY.user_message);
			message.initTitle().setString(0, getCurrentUser(session).getName()).setString(1, entity.getTitle());
			message.initText().setString(entity.getContent());
			usermessageServiceImpl.sendMessage(message, parseIds(userids), getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD).putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.ADD).setError(e.getMessage(), e).returnObjMode();
		}
	}
}
