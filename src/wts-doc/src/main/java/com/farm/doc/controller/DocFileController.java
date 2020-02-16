package com.farm.doc.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.doc.domain.Docfiletext;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.IMG_TYPE;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;

/**
 * 附件文件管理
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/docfile")
@Controller
public class DocFileController extends WebUtils {
	private static final Logger log = Logger.getLogger(DocFileController.class);
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;

	/**
	 * 格式化起始和结束时间查询条件
	 * 
	 * @param query
	 * @return
	 */
	private DataQuery formateTimeLimit(DataQuery query) {
		{
			// 处理时间查询
			DBRule statTime = query.getAndRemoveRule("STARTTIME");
			DBRule endTime = query.getAndRemoveRule("ENDTIME");
			String startT = null;
			String endT = null;
			if (statTime != null) {
				startT = StringUtils.isNotBlank(statTime.getValue()) ? statTime.getValue() : null;
			}
			if (endTime != null) {
				endT = StringUtils.isNotBlank(endTime.getValue()) ? endTime.getValue() : null;
			}
			if (startT != null) {
				startT = startT.replaceAll("-", "") + "000000";
				query.addRule(new DBRule("a.ctime", startT, ">="));
			}
			if (endT != null) {
				endT = endT.replaceAll("-", "") + "999999";
				query.addRule(new DBRule("a.ctime", endT, "<="));
			}
		}
		return query;
	}

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query, HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query = formateTimeLimit(query);
			query.addDefaultSort(new DBSort("a.ctime", "desc"));
			if (query.getQueryRule().size() <= 0) {
				query.addRule(new DBRule("a.PSTATE", "1", "="));
			}
			DBRule dbrule = query.getAndRemoveRule("PSTATE");
			if (dbrule != null && !dbrule.getValue().equals("all")) {
				query.addRule(dbrule);
			}
			DataResult result = getDataQuery(query).search();
			result.runDictionary("1:使用,0:临时,3:永久", "PSTATE");
			result.runformatTime("CTIME", "yyyy-MM-dd HH:mm:ss");
			result.runDictionary("1:图片,2:资源,3:压缩,0:其他", "TYPE");
			return ViewMode.getInstance().putAttrs(EasyUiUtils.formatGridData(result)).returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	private DataQuery getDataQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "farm_docfile  a left join farm_docfile_text b on a.id=b.fileid",
				"a.id as ID,a.DIR as DIR,a.DOWNUM as DOWNUM,a.TYPE as TYPE,a.ctime as CTIME,a.NAME as NAME,a.EXNAME as EXNAME,a.LEN as LEN,a.FILENAME as FILENAME,a.PSTATE as PSTATE,a.PCONTENT as PCONTENT,b.DESCRIBESMIN as DESCRIBESMIN,B.DOCID AS DOCID");
		return dbQuery;
	}

	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance().returnModelAndView("doc/DocfileResult");
	}

	/**
	 * 提交修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(FarmDocfile entity, HttpSession session) {
		try {
			return ViewMode.getInstance().setOperate(OperateType.ADD).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(HttpSession session) {
		try {
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
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
				// farmFileManagerImpl.cancelFile(id);
				farmFileManagerImpl.delFile(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	/**
	 * 清空附件缓存
	 * 
	 * @return
	 */
	@RequestMapping("/clearCache")
	@ResponseBody
	public Map<String, Object> clearCache(String ids, HttpSession session) {
		try {
			farmFileManagerImpl.clearCache();
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
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("doc/DocfileForm");
			}
			case (0): {// 展示
				ViewMode view = ViewMode.getInstance();
				FarmDocfile entity = farmFileManagerImpl.getFile(ids);
				Docfiletext filetext = farmFileManagerImpl.getFiletext(entity.getId());
				entity.setUrl(farmFileManagerImpl.getFileURL(ids));
				entity.setMinurl(farmFileManagerImpl.getImgURL(ids, IMG_TYPE.MIN));
				String realPath = entity.getFile().getPath();
				view.putAttr("realPath", realPath);
				return view.putAttr("pageset", pageset)
						.putAttr("text", filetext != null ? filetext.getDescribes() : null).putAttr("entity", entity)
						.returnModelAndView("doc/DocfileForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", farmFileManagerImpl.getFile(ids)).returnModelAndView("doc/DocfileForm");
			}
			default:
				break;
			}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(), e).returnModelAndView("doc/DocfileForm");
		}
		return ViewMode.getInstance().returnModelAndView("parameter/DocfileForm.jsp");
	}
}
