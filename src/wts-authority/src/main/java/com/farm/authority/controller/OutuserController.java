package com.farm.authority.controller;

import com.farm.authority.domain.Outuser;
import com.farm.authority.service.OutuserServiceInter;
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
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：外部账户控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/outuser")
@Controller
public class OutuserController extends WebUtils {
	private final static Logger log = Logger.getLogger(OutuserController.class);
	@Resource
	OutuserServiceInter outUserServiceImpl;

	public OutuserServiceInter getOutuserServiceImpl() {
		return outUserServiceImpl;
	}

	public void setOutuserServiceImpl(OutuserServiceInter outUserServiceImpl) {
		this.outUserServiceImpl = outUserServiceImpl;
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
			DataResult result = outUserServiceImpl.createOutuserSimpleQuery(query).search();
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
	public Map<String, Object> editSubmit(Outuser entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = outUserServiceImpl.editOutuserEntity(entity);
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
	public Map<String, Object> addSubmit(Outuser entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = outUserServiceImpl.insertOutuserEntity(entity, getCurrentUser(session));
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
				outUserServiceImpl.deleteOutuserEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("authority/OutuserResult");
	}

	/**
	 * 绑定到用户
	 *
	 * @return
	 */
	@RequestMapping("/bindUser")
	public ModelAndView bindUser(HttpSession session) {
		try {
			return ViewMode.getInstance().returnModelAndView("authority/ChooseUserResult");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/ChooseUserResult");
		}
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/doBind")
	@ResponseBody
	public Map<String, Object> doBind(String outid, String id, HttpSession session) {
		try {
			Outuser outuser = outUserServiceImpl.getOutuserEntity(outid);
			outuser.setUserid(id);
			outUserServiceImpl.editOutuserEntity(outuser);
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
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
			case (0): {// 查看
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", outUserServiceImpl.getOutuserEntity(ids))
						.returnModelAndView("authority/OutuserForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("authority/OutuserForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", outUserServiceImpl.getOutuserEntity(ids))
						.returnModelAndView("authority/OutuserForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("authority/OutuserForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("authority/OutuserForm");
		}
	}
}
