package com.farm.parameter.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.parameter.service.AloneApplogServiceInter;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.easyui.EasyUiUtils;

/**
 * 系统日志
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/log")
@Controller
public class ApplogController {
	private final static Logger log = Logger
			.getLogger(ApplogController.class);
	@Resource
	AloneApplogServiceInter aloneApplogServiceImpl;

	public AloneApplogServiceInter getAloneApplogServiceImpl() {
		return aloneApplogServiceImpl;
	}

	public void setAloneApplogServiceImpl(
			AloneApplogServiceInter aloneApplogServiceImpl) {
		this.aloneApplogServiceImpl = aloneApplogServiceImpl;
	}
	/**进入日志界面
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView(
				"parameter/pAloneApplogLayout");
	}
	/**
	 * 查询结果集合
	 *
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query,
			HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query = DataQuery
					.init(query,
							"alone_applog a left join alone_auth_user b on b.id=a.APPUSER",
							"a.id as id,a.CTIME as ctime,a.DESCRIBES as describes,b.name as APPUSER,a.LEVELS as levels,a.METHOD as method,a.CLASSNAME as classname,a.IP as ip");
			query.addDefaultSort(new DBSort("a.CTIME", "DESC"));
			DataResult result = query.search();
			result.LoadListArray();
			result.runformatTime("CTIME", "yyyy-MM-dd_HH/mm/ss");
			return ViewMode.getInstance()
					.putAttrs(EasyUiUtils.formatGridData(result))
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
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
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.returnModelAndView("parameter/pAloneApplogEntity");
			}
			case (0): {// 展示
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", aloneApplogServiceImpl.getEntity(ids))
						.returnModelAndView("parameter/pAloneApplogEntity");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", aloneApplogServiceImpl.getEntity(ids))
						.returnModelAndView("parameter/pAloneApplogEntity");
			}
			default:
				break;
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnModelAndView("parameter/pAloneApplogEntity");
		}
		return ViewMode.getInstance().returnModelAndView(
				"parameter/pAloneParameterEntity");
	}
	// /**
	// * 提交修改数据
	// *
	// * @return
	// */
	// public String editSubmit() {
	// try {
	// entity = aloneIMP.editEntity(entity, getCurrentUser());
	// pageset = new PageSet(PageType.UPDATE, CommitType.TRUE);
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.UPDATE,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 提交新增数据
	// *
	// * @return
	// */
	// public String addSubmit() {
	// try {
	// entity = aloneIMP.insertEntity(entity, getCurrentUser());
	// pageset = new PageSet(PageType.ADD, CommitType.TRUE);
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.ADD,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 删除数据
	// *
	// * @return
	// */
	// public String delSubmit() {
	// try {
	// for (String id : parseIds(ids)) {
	// aloneIMP.deleteEntity(id, getCurrentUser());
	// }
	// pageset = new PageSet(PageType.ADD, CommitType.TRUE);
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.DEL,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 跳转
	// *
	// * @return
	// */
	// public String forSend() {
	// return SUCCESS;
	// }
	//
	// /**
	// * 显示详细信息（修改或浏览时）
	// *
	// * @return
	// */
	// public String view() {
	// try {
	// switch (pageset.getPageType()) {
	// case (1): {// 新增
	// return SUCCESS;
	// }
	// case (0): {// 展示
	// entity = aloneIMP.getEntity(ids);
	// return SUCCESS;
	// }
	// case (2): {// 修改
	// entity = aloneIMP.getEntity(ids);
	// return SUCCESS;
	// }
	// default:
	// break;
	// }
	// } catch (Exception e) {
	// pageset = PageSet.initPageSet(pageset, PageType.OTHER,
	// CommitType.FALSE, e);
	// }
	// return SUCCESS;
	// }
	//
	// private final static AloneApplogManagerInter aloneIMP =
	// (AloneApplogManagerInter) BEAN("alone_applogProxyId");
	//
	// //
	// ----------------------------------------------------------------------------------
	// public DataQuery getQuery() {
	// return query;
	// }
	//
	// public void setQuery(DataQuery query) {
	// this.query = query;
	// }
	//
	// public DataResult getResult() {
	// return result;
	// }
	//
	// public void setResult(DataResult result) {
	// this.result = result;
	// }
	//
	// public AloneApplog getEntity() {
	// return entity;
	// }
	//
	// public void setEntity(AloneApplog entity) {
	// this.entity = entity;
	// }
	//
	// public PageSet getPageset() {
	// return pageset;
	// }
	//
	// public void setPageset(PageSet pageset) {
	// this.pageset = pageset;
	// }
	//
	// public String getIds() {
	// return ids;
	// }
	//
	// public void setIds(String ids) {
	// this.ids = ids;
	// }
	//
	// public InputStream getInputStream() {
	// return inputStream;
	// }
	//
	// public void setInputStream(InputStream inputStream) {
	// this.inputStream = inputStream;
	// }
	//
	// public Map<String, Object> getJsonResult() {
	// return jsonResult;
	// }
	//
	// public void setJsonResult(Map<String, Object> jsonResult) {
	// this.jsonResult = jsonResult;
	// }
	//
	// private static final Logger log = Logger
	// .getLogger(ActionAloneApplogQuery.class);
	// private static final long serialVersionUID = 1L;
	// /**
	// * 加载树节点 public String loadTreeNode() { treeNodes =
	// * EasyUiTreeNode.formatAjaxTree(EasyUiTreeNode .queryTreeNodeOne(id,
	// * "SORT", "alone_menu", "ID", "PARENTID", "NAME").getResultList(),
	// * EasyUiTreeNode .queryTreeNodeTow(id, "SORT", "alone_menu", "ID",
	// * "PARENTID", "NAME").getResultList(), "PARENTID", "ID", "NAME"); return
	// * SUCCESS; }
	// *
	// * @return
	// */
}
