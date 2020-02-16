package com.wts.exam.service.impl;

import com.wts.exam.domain.SubjectAnalysis;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.SubjectAnalysisDaoInter;
import com.wts.exam.service.SubjectAnalysisServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试题解析服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectAnalysisServiceImpl implements SubjectAnalysisServiceInter {
	@Resource
	private SubjectAnalysisDaoInter SubjectAnalysisDaoImpl;

	private static final Logger log = Logger.getLogger(SubjectAnalysisServiceImpl.class);

	@Override
	@Transactional
	public SubjectAnalysis insertSubjectAnalysisEntity(SubjectAnalysis entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		return SubjectAnalysisDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public SubjectAnalysis editSubjectAnalysisEntity(SubjectAnalysis entity, LoginUser user) {
		SubjectAnalysis entity2 = SubjectAnalysisDaoImpl.getEntity(entity.getId());
		entity2.setText(entity.getText());
		entity2.setPcontent(entity.getPcontent());
		entity2.setPstate(entity.getPstate());
		SubjectAnalysisDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deleteSubjectAnalysisEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		SubjectAnalysisDaoImpl.deleteEntity(SubjectAnalysisDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public SubjectAnalysis getSubjectAnalysisEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return SubjectAnalysisDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjectAnalysisSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_SUBJECT_ANALYSIS",
				"ID,SUBJECTID,TEXT,PCONTENT,PSTATE,CUSERNAME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<SubjectAnalysis> getSubjectAnalysies(String subjectid) {
		DBRuleList dblist = DBRuleList.getInstance().add(new DBRule("SUBJECTID", subjectid, "="));
		return SubjectAnalysisDaoImpl.selectEntitys(dblist.toList());
	}
}
