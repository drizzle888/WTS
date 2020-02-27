package com.wts.exam.service.impl;

import com.wts.exam.domain.ExamStat;
import com.farm.core.time.TimeTool;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.wts.exam.dao.ExamStatDaoInter;
import com.wts.exam.service.ExamStatServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

import com.farm.authority.domain.User;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户答题统计服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class ExamStatServiceImpl implements ExamStatServiceInter {
	@Resource
	private ExamStatDaoInter examstatDaoImpl;

	private static final Logger log = Logger.getLogger(ExamStatServiceImpl.class);

	@Override
	@Transactional
	public ExamStat insertExamstatEntity(ExamStat entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return examstatDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public ExamStat editExamstatEntity(ExamStat entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		ExamStat entity2 = examstatDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPapernum(entity.getPapernum());
		entity2.setErrorsubnum(entity.getErrorsubnum());
		entity2.setSubjectnum(entity.getSubjectnum());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		entity2.setEuser(entity.getEuser());
		entity2.setEusername(entity.getEusername());
		entity2.setCuser(entity.getCuser());
		entity2.setCusername(entity.getCusername());
		entity2.setEtime(entity.getEtime());
		entity2.setCtime(entity.getCtime());
		entity2.setId(entity.getId());
		examstatDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteExamstatEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		examstatDaoImpl.deleteEntity(examstatDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public ExamStat getExamstatEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return examstatDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createExamstatSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WTS_EXAM_STAT",
				"ID,PAPERNUM,ERRORSUBNUM,SUBJECTNUM,PCONTENT,PSTATE,EUSER,EUSERNAME,CUSER,CUSERNAME,ETIME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public ExamStat addFinishSubjectNum(String subjectid, Boolean isRight, LoginUser user) {
		if (user == null || StringUtils.isBlank(subjectid)) {
			return null;
		}
		// 取出用戶答題量
		ExamStat stat = examstatDaoImpl.getEntity(user);
		if (stat == null) {
			stat = insertStat(user);
		}
		if (isRight != null && !isRight) {
			stat.setErrorsubnum(stat.getErrorsubnum() + 1);
		}
		stat.setSubjectnum(stat.getSubjectnum() + 1);
		examstatDaoImpl.editEntity(stat);
		return stat;
	}

	@Override
	@Transactional
	public ExamStat getExamstatEntity(User user) {
		ExamStat stat = examstatDaoImpl.getEntity(user);
		return stat;
	}

	@Override
	@Transactional
	public ExamStat addStartCardNum(String cardid, LoginUser user) {
		if (user == null || StringUtils.isBlank(cardid)) {
			return null;
		}
		// 取出用戶答題量
		ExamStat stat = examstatDaoImpl.getEntity(user);
		if (stat == null) {
			stat = insertStat(user);
		}
		if (stat.getPapernum() == null) {
			stat.setPapernum(0);
		}
		stat.setPapernum(stat.getPapernum() + 1);
		examstatDaoImpl.editEntity(stat);
		return stat;
	}

	@Override
	@Transactional
	public ExamStat addStartTestNum(String paperid, LoginUser user) {
		if (user == null || StringUtils.isBlank(paperid)) {
			return null;
		}
		// 取出用戶答題量
		ExamStat stat = examstatDaoImpl.getEntity(user);
		if (stat == null) {
			stat = insertStat(user);
		}
		if (stat.getTestnum() == null) {
			stat.setTestnum(0);
		}
		stat.setTestnum(stat.getTestnum() + 1);
		examstatDaoImpl.editEntity(stat);
		return stat;
	}

	private ExamStat insertStat(LoginUser user) {
		ExamStat stat = new ExamStat();
		stat.setCtime(TimeTool.getTimeDate14());
		stat.setCuser(user.getId());
		stat.setCusername(user.getName());
		stat.setErrorsubnum(0);
		stat.setEtime(TimeTool.getTimeDate14());
		stat.setEuser(user.getId());
		stat.setEusername(user.getName());
		stat.setPapernum(0);
		stat.setPstate("1");
		stat.setSubjectnum(0);
		stat.setTestnum(0);
		stat.setPapernum(0);
		stat = examstatDaoImpl.insertEntity(stat);
		return stat;
	}
}
