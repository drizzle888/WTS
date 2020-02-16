package com.farm.wcp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.farm.authority.service.PopServiceInter;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;
import com.wts.exam.domain.Room;
import com.wts.exam.service.RoomServiceInter;

/* *
 *功能：业务权限控组件
 *详细：
 *【1】.在本类的getTargetNames（）方法中配置对应的类型实现方法
 *
 *
 *【2】. 在grid页面创建权限设置按钮：{
 *		id : 'del',
 *		text : '设置显示权限',
 *		iconCls : 'icon-eye',
 *		handler : setPop
 *	}
 *
 *
 *【3】.引入jsp文件<jsp:include page="../authority/PopComponent.jsp"></jsp:include>
 *
 *
 *【4】.创建引用权限设置窗口的js实现方法
 *	function setPop() {
 *		var selectedArray = $(gridWeburl).datagrid('getSelections');
 *		if (selectedArray.length > 0) {
 *			var ids = $.farm.getCheckedIds(gridWeburl, 'ID');
 *			openPopWindow(ids,'FARM_WEBURL','设置显示权限');
 *		} else {
 *			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
 *			'info');
 *		}
 *	}
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/popcom")
@Controller
public class PopComponentController extends WebUtils {
	private final static Logger log = Logger.getLogger(PopComponentController.class);
	@Resource
	private PopServiceInter popServiceImpl;
	@Resource
	private RoomServiceInter roomServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, String targetIds, String targetType,
			HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			String ids = null;
			for (String id : parseIds(targetIds)) {
				if (ids == null) {
					ids = "'" + id + "'";
				} else {
					ids = ids + ",'" + id + "'";
				}
			}
			query.addSqlRule(" and TARGETID in(" + ids + ") and TARGETTYPE='" + targetType + "'");
			DataResult result = popServiceImpl.createPopSimpleQuery(query).search()
					.runDictionary("1:用户,2:组织机构,3:岗位", "POPTYPE").runDictionary(TARGETTYPE.getDicMap(), "TARGETTYPE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/setingPage")
	public ModelAndView setingPage(String targetIds, String targetType, HttpSession session) {
		String names = null;
		for (String id : parseIds(targetIds)) {
			if (names == null) {
				names = getTargetNames(id, targetType);
			} else {
				names = names + "," + getTargetNames(id, targetType);
			}
		}
		return ViewMode.getInstance().putAttr("targetIds", targetIds).putAttr("targetType", targetType)
				.putAttr("targetNames", names).returnModelAndView("authority/PopSetingWin");
	}

	/**
	 * 在分类权限中加入用户
	 * 
	 * @param userid
	 *            用户id
	 * @param typeid
	 *            分类id
	 * @param type
	 *            类型READ、WRITE、AUDIT
	 * @param request
	 * @return
	 */
	@RequestMapping("/addUserToPop")
	@ResponseBody
	public Map<String, Object> addUserToPop(String userids, String targetIds, String targetType, HttpSession session) {
		try {
			for (String targetId : parseIds(targetIds)) {
				for (String oid : parseIds(userids)) {
					popServiceImpl.addUserPop(targetId, getTargetNames(targetId, targetType), oid, targetType,
							getCurrentUser(session));
				}
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/addOrgToPop")
	@ResponseBody
	public Map<String, Object> addOrgToPop(String orgids, String targetIds, String targetType, HttpSession session) {
		try {
			if (orgids == null || orgids.equals("NONE")) {
				throw new RuntimeException("请选择一个可用的组织机构");
			}
			for (String targetId : parseIds(targetIds)) {
				for (String oid : parseIds(orgids)) {
					popServiceImpl.addOrgPop(targetId, getTargetNames(targetId, targetType), oid, targetType,
							getCurrentUser(session));
				}
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/addPostToPop")
	@ResponseBody
	public Map<String, Object> addPostToPop(String postids, String targetIds, String targetType, HttpSession session) {
		try {
			for (String targetId : parseIds(targetIds)) {
				for (String oid : parseIds(postids)) {
					popServiceImpl.addPostPop(targetId, getTargetNames(targetId, targetType), oid, targetType,
							getCurrentUser(session));
				}
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/delPop")
	@ResponseBody
	public Map<String, Object> delPop(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				popServiceImpl.delPop(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 通过类型获得(通过业务ID获得业务名称,用于数据库中保存业务名称)
	 * 
	 * @param targetId
	 * @param targetType
	 * @return
	 */
	private String getTargetNames(String targetId, String targetType) {
		// 考试判卷人
		if (TARGETTYPE.getType(targetType).equals(TARGETTYPE.ROOM_ADMIN)) {
			Room room = roomServiceImpl.getRoomEntity(targetId);
			if (room != null) {
				return room.getName();
			} else {
				return "考试管理员";
			}
		}
		// 考试管理员
		if (TARGETTYPE.getType(targetType).equals(TARGETTYPE.ROOM_LEAD)) {
			Room room = roomServiceImpl.getRoomEntity(targetId);
			if (room != null) {
				return room.getName();
			} else {
				return "考试评审员";
			}
		}
		// 考试评审人
		if (TARGETTYPE.getType(targetType).equals(TARGETTYPE.ROOM_COUNT)) {
			Room room = roomServiceImpl.getRoomEntity(targetId);
			if (room != null) {
				return room.getName();
			} else {
				return "考试判卷员";
			}
		}
		throw new RuntimeException("can't find the key '" + targetType + "'");
	}

	/**
	 * 授权类型
	 * 
	 * @author wangdong
	 *
	 */
	enum TARGETTYPE {
		ROOM_ADMIN("ROOM_ADMIN", "考试管理员"), ROOM_LEAD("ROOM_LEAD", "考试评审员"), ROOM_COUNT("ROOM_COUNT", "考试判卷员");
		private String key;
		private String title;

		TARGETTYPE(String keyStr, String titleStr) {
			key = keyStr;
			title = titleStr;
		}

		/**
		 * 通过常数获得枚举项
		 * 
		 * @param targettype
		 * @return
		 */
		public static TARGETTYPE getType(String targettype) {
			for (TARGETTYPE node : TARGETTYPE.values()) {
				if (node.key.equals(targettype)) {
					return node;
				}
			}
			throw new RuntimeException("can't find the key '" + targettype + "'");
		}

		public static Map<String, String> getDicMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (TARGETTYPE node : TARGETTYPE.values()) {
				map.put(node.key, node.title);
			}
			return map;
		}
	}
}
