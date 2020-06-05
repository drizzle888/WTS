package com.wts.exam.service.impl;

import com.wts.exam.domain.PaperSubject;
import org.apache.log4j.Logger;
import com.wts.exam.dao.PaperSubjectDaoInter;
import com.wts.exam.service.PaperSubjectServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试卷试题服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class PaperSubjectServiceImpl implements PaperSubjectServiceInter {
	@Resource
	private PaperSubjectDaoInter papersubjectDaoImpl;

	private static final Logger log = Logger.getLogger(PaperSubjectServiceImpl.class);

	@Override
	@Transactional
	public PaperSubject insertPapersubjectEntity(PaperSubject entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		// entity.setCuser(user.getId());
		// entity.setCtime(TimeTool.getTimeDate14());
		// entity.setCusername(user.getName());
		// entity.setEuser(user.getId());
		// entity.setEusername(user.getName());
		// entity.setEtime(TimeTool.getTimeDate14());
		// entity.setPstate("1");
		return papersubjectDaoImpl.insertEntity(entity);
	}

	@Override
	@Transactional
	public PaperSubject editPapersubjectEntity(PaperSubject entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		PaperSubject entity2 = papersubjectDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPoint(entity.getPoint());
		entity2.setSort(entity.getSort());
		entity2.setChapterid(entity.getChapterid());
		entity2.setSubjectid(entity.getSubjectid());
		entity2.setVersionid(entity.getVersionid());
		entity2.setId(entity.getId());
		papersubjectDaoImpl.editEntity(entity2);
		return entity2;
	}

	@Override
	@Transactional
	public void deletePapersubjectEntity(String id, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		papersubjectDaoImpl.deleteEntity(papersubjectDaoImpl.getEntity(id));
	}

	@Override
	@Transactional
	public PaperSubject getPapersubjectEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return papersubjectDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createPapersubjectSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WTS_PAPER_SUBJECT a left join WTS_PAPER_CHAPTER b on a.CHAPTERID=b.ID LEFT JOIN WTS_SUBJECT_VERSION c on a.VERSIONID=c.ID left join WTS_SUBJECT d on a.subjectid=d.id left join WTS_MATERIAL e on d.MATERIALID =e.id left join wts_subject_type f on f.id=d.typeid",
				"a.ID AS ID, a.POINT AS POINT, a.SORT AS SORT, c.TIPTYPE AS TIPTYPE, c.TIPSTR AS TIPSTR,e.title as MTITLE,f.name as TYPENAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public PaperSubject getPapersubjectEntity(String paperid, String chapterid, String subjectid) {
		List<PaperSubject> subjects = papersubjectDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("CHAPTERID", chapterid, "=")).add(new DBRule("PAPERID", paperid, "="))
				.add(new DBRule("SUBJECTID", subjectid, "=")).toList());
		if (subjects.size() > 0) {
			return subjects.get(0);
		} else {
			return null;
		}
	}

}
