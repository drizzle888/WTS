package com.wts.exam.service.impl;

import com.wts.exam.domain.SubjectComment;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.SubjectCommentDaoInter;
import com.wts.exam.service.SubjectCommentServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试题评论服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectCommentServiceImpl implements SubjectCommentServiceInter {
	@Resource
	private SubjectCommentDaoInter SubjectCommentDaoImpl;

	private static final Logger log = Logger.getLogger(SubjectCommentServiceImpl.class);

	@Override
	@Transactional
	public void deleteSubjectCommentEntity(String id, LoginUser user) {
		SubjectCommentDaoImpl.deleteEntity(SubjectCommentDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public SubjectComment getSubjectCommentEntity(String id) {
		if (id == null) {
			return null;
		}
		return SubjectCommentDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjectCommentSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query, "WTS_SUBJECT_COMMENT",
				"ID,SUBJECTID,TEXT,PCONTENT,PSTATE,CUSERNAME,CTIME");
		return dbQuery;
	}

	@Override
	@Transactional
	public List<SubjectComment> getSubjectComments(String subjectid) {
		List<SubjectComment> list = SubjectCommentDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("SUBJECTID", subjectid, "=")).toList());
		Collections.sort(list, new Comparator<SubjectComment>() {
			@Override
			public int compare(SubjectComment o1, SubjectComment o2) {
				return o1.getCtime().compareTo(o2.getCtime());
			}
		});
		return list;
	}

	@Override
	@Transactional
	public SubjectComment insertSubjectComment(String text, String subjectid, LoginUser currentUser) {
		SubjectComment entity = new SubjectComment();
		entity.setCuser(currentUser.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(currentUser.getName());
		entity.setText(text);
		entity.setSubjectid(subjectid);
		entity.setPstate("1");
		return SubjectCommentDaoImpl.insertEntity(entity);
	}

}
