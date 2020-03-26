package com.wts.exam.controller;

import com.wts.exam.domain.Card;
import com.wts.exam.domain.RoomPaper;
import com.wts.exam.service.CardServiceInter;
import com.wts.exam.service.RoomPaperServiceInter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import com.farm.web.easyui.EasyUiUtils;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpSession;
import com.farm.core.page.RequestMode;
import com.farm.core.page.OperateType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.result.ResultsHandle;
import com.farm.core.page.ViewMode;
import com.farm.web.WebUtils;

/* *
 *功能：答题卡控制层
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@RequestMapping("/card")
@Controller
public class CardController extends WebUtils {
	private final static Logger log = Logger.getLogger(CardController.class);
	@Resource
	private CardServiceInter cardServiceImpl;
	@Resource
	private RoomPaperServiceInter roomPaperServiceImpl;

	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(String roompaperId, DataQuery query, HttpServletRequest request) {
		try {
			RoomPaper roompaper = roomPaperServiceImpl.getRoompaperEntity(roompaperId);
			query.addRule(new DBRule("A.ROOMID", roompaper.getRoomid(), "="));
			query.addRule(new DBRule("A.PAPERID", roompaper.getPaperid(), "="));
			query = EasyUiUtils.formatGridQuery(request, query);
			DataResult result = cardServiceImpl.createCardSimpleQuery(query).search();
			result.runHandle(new ResultsHandle() {
				// 处理答题且超时的答题卡，设置未超时未提交的状态
				@Override
				public void handle(Map<String, Object> row) {
					if(StringUtils.isBlank((String) row.get("USERNAME"))){
						row.put("USERNAME", ((String) row.get("USERID")).replaceAll("ANONYMOUS", "匿名"));
					}
				}
			});

			result.runformatTime("STARTTIME", "yyyy-MM-dd HH:mm:ss");
			result.runformatTime("ENDTIME", "HH:mm:ss");
			result.runformatTime("ADJUDGETIME", "yyyy-MM-dd HH:mm:ss");
			result.runDictionary("1:开始答题,2:手动交卷,3:超时未交卷,4:超时自动交卷,5:已自动阅卷,6:已完成阅卷,7:发布成绩", "PSTATE");
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
	public Map<String, Object> editSubmit(Card entity, HttpSession session) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			entity = cardServiceImpl.editCardEntity(entity);
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).putAttr("entity", entity).returnObjMode();

		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setOperate(OperateType.UPDATE).setError(e.getMessage(), e).returnObjMode();
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
				cardServiceImpl.deleteCardEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(), e).returnObjMode();
		}
	}

	@RequestMapping("/list")
	public ModelAndView index(String roompaperId, HttpSession session) {
		return ViewMode.getInstance().putAttr("roompaperId", roompaperId).returnModelAndView("exam/CardResult");
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
						.putAttr("entity", cardServiceImpl.getCardEntity(ids)).returnModelAndView("exam/CardForm");
			}
			case (1): {// 新增
				return ViewMode.getInstance().putAttr("pageset", pageset).returnModelAndView("exam/CardForm");
			}
			case (2): {// 修改
				return ViewMode.getInstance().putAttr("pageset", pageset)
						.putAttr("entity", cardServiceImpl.getCardEntity(ids)).returnModelAndView("exam/CardForm");
			}
			default:
				break;
			}
			return ViewMode.getInstance().returnModelAndView("exam/CardForm");
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e + e.getMessage(), e).returnModelAndView("exam/CardForm");
		}
	}
}
