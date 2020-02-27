package com.wts.exam.service.impl;

import com.wts.exam.domain.SubjectVersion;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_APPLICATION_TYPE;

import org.apache.log4j.Logger;
import com.wts.exam.dao.SubjectVersionDaoInter;
import com.wts.exam.service.SubjectVersionServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考题版本服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectVersionServiceImpl implements SubjectVersionServiceInter {
	@Resource
	private SubjectVersionDaoInter subjectversionDaoImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	private static final Logger log = Logger.getLogger(SubjectVersionServiceImpl.class);

	@Override
	@Transactional
	public SubjectVersion insertSubjectversionEntity(SubjectVersion entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		entity = subjectversionDaoImpl.insertEntity(entity);
		// --------------------------------------------------
		farmFileManagerImpl.submitFileByAppHtml(entity.getTipnote(), entity.getId(),FILE_APPLICATION_TYPE.SUBJECTNOTE);
		return entity;
	}

	@Override
	@Transactional
	public SubjectVersion editSubjectversionEntity(SubjectVersion entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		SubjectVersion entity2 = subjectversionDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setTiptype(entity.getTiptype());
		entity2.setSubjectid(entity.getSubjectid());
		entity2.setTipnote(entity.getTipnote());
		entity2.setTipstr(entity.getTipstr());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		entity2.setCuser(entity.getCuser());
		entity2.setCusername(entity.getCusername());
		entity2.setCtime(entity.getCtime());
		entity2.setId(entity.getId());
		subjectversionDaoImpl.editEntity(entity2);
		farmFileManagerImpl.submitFileByAppHtml(entity2.getTipnote(), entity.getId(),FILE_APPLICATION_TYPE.SUBJECTNOTE);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteSubjectversionEntity(String id, LoginUser user) {
		subjectversionDaoImpl.deleteEntity(subjectversionDaoImpl.getEntity(id));
		farmFileManagerImpl.cancelFilesByApp(id);
	}

	@Override
	@Transactional
	public SubjectVersion getSubjectversionEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return subjectversionDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjectversionSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WTS_SUBJECT_VERSION",
				"ID,TIPTYPE,SUBJECTID,TIPNOTE,TIPSTR,PCONTENT,PSTATE,CUSER,CUSERNAME,CTIME");
		return dbQuery;
	}

}
