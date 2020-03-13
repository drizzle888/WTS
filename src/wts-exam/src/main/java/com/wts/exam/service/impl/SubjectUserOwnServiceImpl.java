package com.wts.exam.service.impl;

import com.wts.exam.domain.SubjectUserOwn;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.wts.exam.dao.SubjectUserOwnDaoInter;
import com.wts.exam.service.ExamStatServiceInter;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.SubjectUserOwnServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

import com.farm.authority.FarmAuthorityService;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户题库服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectUserOwnServiceImpl implements SubjectUserOwnServiceInter {
	@Resource
	private SubjectUserOwnDaoInter subjectuserownDaoImpl;
	@Resource
	private ExamStatServiceInter examStatServiceImpl;
	@Resource
	private SubjectServiceInter subjectServiceImpl;
	private static final Logger log = Logger.getLogger(SubjectUserOwnServiceImpl.class);

	@Override
	@Transactional
	public void addFinishSubject(String subjectid, Boolean isRight, String cardId, LoginUser user) {
		if (isRight != null && !isRight&&StringUtils.isBlank(cardId)) {
			// 记录错题[如果答题卡为空，表示不是从考卷而来的题才记录错题]
			insertSubjectuserownEntity(subjectid, "3", cardId, user);
		}
		{
			// 记录答题记录
			insertSubjectuserownEntity(subjectid, "1", cardId, user);
		}
		if (user != null) {
			// 統計用戶答題
			examStatServiceImpl.addFinishSubjectNum(subjectid, isRight, user);
		}
		{
			// 更新题统计
			subjectServiceImpl.addDoNum(subjectid);
			if (isRight != null && isRight) {
				subjectServiceImpl.addRightNum(subjectid);
			}
		}
	}

	/**
	 * 创建一个用户题
	 * 
	 * @param subjectid
	 * @param modeltype
	 *            1.历史答题,2:收藏,3:错题
	 * @param cardId
	 *            可以为空
	 * @param user
	 * @return
	 */
	private SubjectUserOwn insertSubjectuserownEntity(String subjectid, String modeltype, String cardId,
			LoginUser user) {
		if (subjectuserownDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("CUSER", user.getId(), "="))
				.add(new DBRule("MODELTYPE", modeltype, "=")).toList()) > 1000) {
			// 如果大于20条记录，就删除5条最早的
			subjectuserownDaoImpl.delEarliestSubject(user.getId(), modeltype);
		}
		subjectuserownDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("SUBJECTID", subjectid, "="))
				.add(new DBRule("MODELTYPE", modeltype, "=")).add(new DBRule("CUSER", user.getId(), "=")).toList());
		SubjectUserOwn userOwn = new SubjectUserOwn();
		userOwn.setCuser(user.getId());
		userOwn.setCtime(TimeTool.getTimeDate14());
		userOwn.setCusername(user.getName());
		if (StringUtils.isNotBlank(cardId)) {
			userOwn.setCardid(cardId);
		}
		userOwn.setPstate("1");
		// 1.历史答题,2:收藏,3:错题
		userOwn.setModeltype(modeltype);
		userOwn.setSubjectid(subjectid);
		return subjectuserownDaoImpl.insertEntity(userOwn);
	}

	@Override
	@Transactional
	public void deleteSubjectuserownEntity(String id, LoginUser user) {
		subjectuserownDaoImpl.deleteEntity(subjectuserownDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public SubjectUserOwn getSubjectuserownEntity(String id) {
		if (id == null) {
			return null;
		}
		return subjectuserownDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjectuserownSimpleQuery(DataQuery query) {
		query.addDefaultSort(new DBSort("A.CTIME", "DESC"));
		DataQuery dbQuery = DataQuery.init(query,
				"WTS_SUBJECT_USEROWN a left join WTS_SUBJECT b on a.subjectid=b.id left join WTS_SUBJECT_VERSION c on b.versionid=c.id left join WTS_CARD d on d.id=a.cardid",
				"B.ID AS SUBJECTID, C.ID AS VERSIONID, C.TIPSTR AS TITLE, a.CTIME AS CTIME,A.ID AS ID,d.PSTATE AS CARDSTATE");
		return dbQuery;
	}

	@Override
	@Transactional
	public int getBookNum(String subjectId, String userid) {
		return subjectuserownDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("MODELTYPE", "2", "="))
				.add(new DBRule("SUBJECTID", subjectId, "=")).toList());
	}

	@Override
	@Transactional
	public boolean doBook(String subjectId, String userid) {
		boolean isBook = subjectuserownDaoImpl.countEntitys(DBRuleList.getInstance()
				.add(new DBRule("MODELTYPE", "2", "=")).add(new DBRule("SUBJECTID", subjectId, "="))
				.add(new DBRule("CUSER", userid, "=")).toList()) > 0;
		if (isBook) {
			// 取消
			subjectuserownDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("MODELTYPE", "2", "="))
					.add(new DBRule("SUBJECTID", subjectId, "=")).add(new DBRule("CUSER", userid, "=")).toList());
			return false;
		} else {
			// 訂閲
			SubjectUserOwn userOwn = new SubjectUserOwn();
			userOwn.setCuser(userid);
			userOwn.setCtime(TimeTool.getTimeDate14());
			userOwn.setCusername(FarmAuthorityService.getInstance().getUserById(userid).getName());
			userOwn.setPstate("1");
			userOwn.setModeltype("2");
			userOwn.setSubjectid(subjectId);
			subjectuserownDaoImpl.insertEntity(userOwn);
			return true;
		}
	}

	@Override
	@Transactional
	public boolean isBook(String subjectId, String userid) {
		return subjectuserownDaoImpl.countEntitys(DBRuleList.getInstance().add(new DBRule("MODELTYPE", "2", "="))
				.add(new DBRule("SUBJECTID", subjectId, "=")).add(new DBRule("CUSER", userid, "=")).toList()) > 0;
	}
}