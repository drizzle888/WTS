package com.wts.exam.service.impl;

import com.wts.exam.domain.Material;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.PaperChapter;
import com.wts.exam.domain.PaperSubject;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.ChapterUnit;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.farm.core.time.TimeTool;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_APPLICATION_TYPE;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.wts.exam.dao.PaperDaoInter;
import com.wts.exam.dao.PaperChapterDaoInter;
import com.wts.exam.dao.PaperSubjectDaoInter;
import com.wts.exam.dao.RoomPaperDaoInter;
import com.wts.exam.dao.SubjectAnswerDaoInter;
import com.wts.exam.dao.SubjectVersionDaoInter;
import com.wts.exam.service.MaterialServiceInter;
import com.wts.exam.service.PaperServiceInter;
import com.wts.exam.service.SubjectAnswerServiceInter;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.SubjectVersionServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考卷服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class PaperServiceImpl implements PaperServiceInter {
	@Resource
	private PaperDaoInter paperDaoImpl;
	@Resource
	private PaperSubjectDaoInter papersubjectDaoImpl;
	@Resource
	private PaperChapterDaoInter paperchapterDaoImpl;
	@Resource
	private RoomPaperDaoInter roompaperDaoImpl;
	@Resource
	private SubjectServiceInter subjectServiceImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private SubjectVersionDaoInter subjectversionDaoImpl;
	@Resource
	private SubjectAnswerServiceInter subjectAnswerServiceImpl;
	@Resource
	private SubjectAnswerDaoInter subjectanswerDaoImpl;
	@Resource
	private SubjectVersionServiceInter subjectVersionServiceImpl;
	@Resource
	private MaterialServiceInter materialServiceImpl;
	private static final Logger log = Logger.getLogger(PaperServiceImpl.class);

	@Override
	@Transactional
	public Paper insertPaperEntity(Paper entity, LoginUser user) {
		entity.setCuser(user.getId());
		entity.setCtime(TimeTool.getTimeDate14());
		entity.setCusername(user.getName());
		entity.setEuser(user.getId());
		entity.setEusername(user.getName());
		entity.setEtime(TimeTool.getTimeDate14());
		entity.setAvgpoint(0);
		entity.setCompletetnum(0);
		entity.setLowpoint(0);
		entity.setToppoint(0);
		entity.setSubjectnum(0);
		entity.setPointnum(0);
		if (StringUtils.isBlank(entity.getExamtypeid())) {
			entity.setExamtypeid(null);
		}
		entity = paperDaoImpl.insertEntity(entity);
		// --------------------------------------------------
		farmFileManagerImpl.submitFileByAppHtml(entity.getPapernote(), entity.getId(), FILE_APPLICATION_TYPE.PAPERNOTE);
		return entity;
	}

	@Override
	@Transactional
	public Paper editPaperEntity(Paper entity, LoginUser user) {
		Paper entity2 = paperDaoImpl.getEntity(entity.getId());
		entity2.setEuser(user.getId());
		entity2.setEusername(user.getName());
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setPapernote(entity.getPapernote());
		entity2.setAdvicetime(entity.getAdvicetime());
		entity2.setPstate(entity.getPstate());
		entity2.setPcontent(entity.getPcontent());
		entity2.setModeltype(entity.getModeltype());
		entity2.setName(entity.getName());
		entity2.setExamtypeid(entity.getExamtypeid());
		if (StringUtils.isBlank(entity2.getExamtypeid())) {
			entity2.setExamtypeid(null);
		}
		paperDaoImpl.editEntity(entity2);
		farmFileManagerImpl.submitFileByAppHtml(entity2.getPapernote(), entity2.getId(),
				FILE_APPLICATION_TYPE.PAPERNOTE);
		return entity2;
	}

	@Override
	@Transactional
	public void deletePaperEntity(String id, LoginUser user) {
		// 删除 题/章节/考场总结表
		papersubjectDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", id, "=")).toList());
		paperchapterDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", id, "=")).toList());
		roompaperDaoImpl.deleteEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", id, "=")).toList());
		paperDaoImpl.deleteEntity(paperDaoImpl.getEntity(id));
		farmFileManagerImpl.cancelFilesByApp(id);
	}

	@Override
	@Transactional
	public Paper getPaperEntity(String id) {
		// TODO 自动生成代码,修改后请去除本注释
		if (id == null) {
			return null;
		}
		return paperDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createPaperSimpleQuery(DataQuery query) {
		DataQuery dbQuery = DataQuery.init(query,
				"WTS_PAPER a LEFT JOIN WTS_EXAM_TYPE b ON a.EXAMTYPEID = b.id left join alone_auth_user c on a.CUSER=c.ID",
				"a.ID as ID,a.MODELTYPE as MODELTYPE,c.name as USERNAME,a.PAPERNOTE as PAPERNOTE,a.ADVICETIME as ADVICETIME,a.LOWPOINT as LOWPOINT,a.TOPPOINT as TOPPOINT,a.AVGPOINT as AVGPOINT,a.COMPLETETNUM as COMPLETETNUM,a.POINTNUM as POINTNUM,a.SUBJECTNUM as SUBJECTNUM,a.PSTATE as PSTATE,a.PCONTENT as PCONTENT,a.NAME as NAME,a.EUSER as EUSER,a.EUSERNAME as EUSERNAME,a.CUSER as CUSER,a.CUSERNAME as CUSERNAME,a.ETIME as ETIME,a.CTIME as CTIME,a.EXAMTYPEID as EXAMTYPEID,b.name as TYPENAME");
		return dbQuery;
	}

	@Override
	@Transactional
	public void examTypeSetting(String roomid, String examtypeId, LoginUser currentUser) {
		if (StringUtils.isNotBlank(examtypeId)) {
			Paper entity2 = paperDaoImpl.getEntity(roomid);
			entity2.setExamtypeid(examtypeId);
			entity2.setEtime(TimeTool.getTimeDate14());
			entity2.setEuser(currentUser.getId());
			entity2.setEusername(currentUser.getName());
			paperDaoImpl.editEntity(entity2);
		}
	}

	@Override
	@Transactional
	public void editState(String id, String state, LoginUser currentUser) {
		Paper entity2 = paperDaoImpl.getEntity(id);
		entity2.setPstate(state);
		entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setEuser(currentUser.getId());
		entity2.setEusername(currentUser.getName());
		paperDaoImpl.editEntity(entity2);
	}

	@Override
	@Transactional
	public PaperUnit getPaperUnit(String paperId) {
		PaperUnit paper = new PaperUnit();
		paper.setInfo(getPaperEntity(paperId));
		// 获得所有的章节对象
		List<PaperChapter> chapters = paperchapterDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperId, "=")).toList());

		// 获得所有的题对象关系
		List<PaperSubject> subjectRelations = papersubjectDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperId, "=")).toList());

		// 给关系排序就等于给题排序
		Collections.sort(subjectRelations, new Comparator<PaperSubject>() {
			@Override
			public int compare(PaperSubject o1, PaperSubject o2) {
				return o1.getSort() - o2.getSort();
			}
		});

		// 题
		Map<String, SubjectUnit> subjects = new HashMap<>();
		for (PaperSubject sub : subjectRelations) {
			SubjectUnit subjectUnit = subjectServiceImpl.getSubjectUnit(sub.getVersionid());
			subjects.put(sub.getVersionid(), subjectUnit);
		}
		// 组合起来
		List<ChapterUnit> rootChapteUnits = new ArrayList<>();
		int allPoint = 0;
		for (PaperChapter chapter : chapters) {
			if (chapter.getParentid().equals("NONE")) {
				// 递归获得所有子章节对象
				ChapterUnit chapterUnit = getChapterUnit(subjectRelations, subjects, chapter, chapters);
				rootChapteUnits.add(chapterUnit);
				allPoint = allPoint + chapterUnit.getAllpoint();
			}
		}
		// 章节排序
		Collections.sort(rootChapteUnits, new Comparator<ChapterUnit>() {
			@Override
			public int compare(ChapterUnit o1, ChapterUnit o2) {
				return o1.getChapter().getSort() - o2.getChapter().getSort();
			}
		});
		paper.setChapters(rootChapteUnits);
		paper.setAllPoint(allPoint);
		paper.setSubjectNum(subjectRelations.size());
		paper.setRootChapterNum(rootChapteUnits.size());
		return paper;
	}

	/**
	 * 递归构造一个考卷章节
	 * 
	 * @param subjects
	 *            考卷所有的题
	 * @param chapter
	 *            考卷当前章节
	 * @param chapters
	 *            考卷所有章节
	 * @return
	 */
	private ChapterUnit getChapterUnit(List<PaperSubject> subjectRelations, Map<String, SubjectUnit> subjects,
			PaperChapter chapter, List<PaperChapter> chapters) {
		ChapterUnit unit = new ChapterUnit();
		int allpoint = 0;
		int subjectNum = 0;
		unit.setChapter(chapter);
		// 章節所有的子章节
		List<ChapterUnit> chapterUnits = new ArrayList<>();
		{// 递归获取子章节unit对象
			for (PaperChapter chapternode : chapters) {
				if (chapternode.getParentid().equals(chapter.getId())) {
					ChapterUnit chapterunit = getChapterUnit(subjectRelations, subjects, chapternode, chapters);
					chapterUnits.add(chapterunit);
					subjectNum = subjectNum + chapterunit.getSubjectNum();
					allpoint = allpoint + chapterunit.getAllpoint();
				}
			}
		}
		Collections.sort(chapterUnits, new Comparator<ChapterUnit>() {
			@Override
			public int compare(ChapterUnit o1, ChapterUnit o2) {
				return o1.getChapter().getSort() - o2.getChapter().getSort();
			}
		});
		unit.setChapters(chapterUnits);
		// 章节所有的题
		List<SubjectUnit> subjectunits = new ArrayList<>();
		{
			for (PaperSubject relation : subjectRelations) {
				if (relation.getChapterid().equals(chapter.getId())) {
					SubjectUnit subjectunit = subjects.get(relation.getVersionid());
					subjectunit.setPoint(relation.getPoint());
					subjectunits.add(subjectunit);
					allpoint = allpoint + relation.getPoint();
				}
			}
		}
		// 章节的引用材料
		List<Material> materials = new ArrayList<>();
		Set<String> materialids = new HashSet<>();
		for (SubjectUnit subunit : subjectunits) {
			if (StringUtils.isNotBlank(subunit.getSubject().getMaterialid())) {
				Material material = materialServiceImpl.getMaterialEntity(subunit.getSubject().getMaterialid());
				if (!materialids.contains(material.getId())) {
					materials.add(material);
					materialids.add(material.getId());
				}
			}
		}
		unit.setMaterials(materials);
		unit.setSubjects(subjectunits);
		unit.setAllpoint(allpoint);
		unit.setSubjectNum(subjectNum + subjectunits.size());
		return unit;
	}

	@Override
	@Transactional
	public void refreshSubjectNum(String paperid) {
		int num = papersubjectDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "=")).toList());
		Paper paper = paperDaoImpl.getEntity(paperid);
		paper.setSubjectnum(num);
		paperDaoImpl.editEntity(paper);
	}

	@Override
	@Transactional
	public boolean isAllSubjectHavePoint(String paperid) {
		boolean isAllSubjectHavePoint = true;
		// 找到每一条中间表，验证是否设置得分
		List<PaperSubject> subjects = papersubjectDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "=")).toList());
		for (PaperSubject subject : subjects) {
			if (subject.getPoint() == null || subject.getPoint() == 0) {
				// 没有设置得分
				isAllSubjectHavePoint = false;
			}
		}
		return isAllSubjectHavePoint;
	}

	@Override
	@Transactional
	public boolean isAllHaveObjectiveSubjectAnswer(String paperid) {
		boolean isAllHaveObjectiveSubjectAnswer = true;
		List<PaperSubject> subjects = papersubjectDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "=")).toList());
		for (PaperSubject subject : subjects) {
			// 找到对应版本
			SubjectVersion version = subjectVersionServiceImpl.getSubjectversionEntity(subject.getVersionid());
			// TIPTYPE:1.填空，2.单选，3.多选，4判断，5问答
			if (version.getTiptype().equals("2") || version.getTiptype().equals("3")
					|| version.getTiptype().equals("4")) {
				// 找到版本的所有答案
				int answerNum = subjectanswerDaoImpl.countEntitys(
						DBRuleList.getInstance().add(new DBRule("VERSIONID", version.getId(), "=")).toList());
				if (answerNum <= 0) {
					isAllHaveObjectiveSubjectAnswer = false;
				}
			}
		}
		return isAllHaveObjectiveSubjectAnswer;
	}

	@Override
	@Transactional
	public boolean isHaveSubjects(String paperid) {
		List<PaperSubject> subjects = papersubjectDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "=")).toList());
		return subjects.size() > 0;
	}

	@Override
	public List<SubjectUnit> getPaperSubjects(List<ChapterUnit> chapterUnits) {
		List<SubjectUnit> list = new ArrayList<>();
		for (ChapterUnit unit : chapterUnits) {
			List<SubjectUnit> chapterSubs = getPaperSubjects(unit.getChapters());
			if (chapterSubs != null && chapterSubs.size() > 0) {
				list.addAll(chapterSubs);
			}
			if (unit.getSubjects() != null && unit.getSubjects().size() > 0) {
				list.addAll(unit.getSubjects());
			}
		}
		return list;
	}
}
