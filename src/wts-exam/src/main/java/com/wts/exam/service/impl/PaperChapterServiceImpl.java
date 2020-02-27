package com.wts.exam.service.impl;

import com.wts.exam.domain.PaperChapter;
import com.wts.exam.domain.PaperSubject;
import com.wts.exam.domain.Subject;
import org.apache.log4j.Logger;
import com.wts.exam.dao.PaperChapterDaoInter;
import com.wts.exam.dao.PaperSubjectDaoInter;
import com.wts.exam.dao.SubjectDaoInter;
import com.wts.exam.service.PaperChapterServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DataQuery;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_APPLICATION_TYPE;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：试卷章节服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class PaperChapterServiceImpl implements PaperChapterServiceInter {
	@Resource
	private PaperChapterDaoInter paperchapterDaoImpl;
	@Resource
	private SubjectDaoInter subjectDaoImpl;
	@Resource
	private PaperSubjectDaoInter papersubjectDaoImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	private static final Logger log = Logger.getLogger(PaperChapterServiceImpl.class);

	@Override
	@Transactional
	public PaperChapter insertPaperchapterEntity(PaperChapter entity, LoginUser user) {
		entity.setTreecode("NONE");
		entity = paperchapterDaoImpl.insertEntity(entity);
		initTreeCode(entity.getId());
		// 添加章节时限制不要超过3级别
		if (entity.getTreecode().length() > 96) {
			throw new RuntimeException("试卷章节不能超过3级！");
		}
		// --------------------------------------------------
		farmFileManagerImpl.submitFileByAppHtml(entity.getTextnote(), entity.getId(),
				FILE_APPLICATION_TYPE.PAPER_CHAPTERNOTE);
		return entity;
	}

	private void initTreeCode(String treeNodeId) {
		PaperChapter node = getPaperchapterEntity(treeNodeId);
		if (node.getParentid().equals("NONE")) {
			node.setTreecode(node.getId());
		} else {
			node.setTreecode(paperchapterDaoImpl.getEntity(node.getParentid()).getTreecode() + node.getId());
		}
		paperchapterDaoImpl.editEntity(node);

	}

	@Override
	@Transactional
	public PaperChapter editPaperchapterEntity(PaperChapter entity, LoginUser user) {
		// TODO 自动生成代码,修改后请去除本注释
		PaperChapter entity2 = paperchapterDaoImpl.getEntity(entity.getId());
		// entity2.setEuser(user.getId());
		// entity2.setEusername(user.getName());
		// entity2.setEtime(TimeTool.getTimeDate14());
		entity2.setTextnote(entity.getTextnote());
		entity2.setPaperid(entity.getPaperid());
		entity2.setParentid(entity.getParentid());
		entity2.setName(entity.getName());
		entity2.setSubjectpoint(entity.getSubjectpoint());
		entity2.setSubjectnum(entity.getSubjectnum());
		entity2.setSubjecttypeid(entity.getSubjecttypeid());
		entity2.setInitpoint(entity.getInitpoint());
		entity2.setPtype(entity.getPtype());
		entity2.setStype(entity.getStype());
		entity2.setId(entity.getId());
		entity2.setSort(entity.getSort());
		paperchapterDaoImpl.editEntity(entity2);
		initTreeCode(entity2.getId());
		farmFileManagerImpl.submitFileByAppHtml(entity2.getTextnote(), entity2.getId(),
				FILE_APPLICATION_TYPE.PAPER_CHAPTERNOTE);
		return entity2;
	}

	@Override
	@Transactional
	public void deletePaperchapterEntity(String id, LoginUser user) {
		// 是否有子节点
		if (paperchapterDaoImpl.selectEntitys(DBRuleList.getInstance().add(new DBRule("PARENTID", id, "=")).toList())
				.size() > 0) {
			throw new RuntimeException("请先删除该章节得子节点!");
		}
		paperchapterDaoImpl.deleteEntity(paperchapterDaoImpl.getEntity(id));
		farmFileManagerImpl.cancelFilesByApp(id);
	}

	@Override
	@Transactional
	public PaperChapter getPaperchapterEntity(String id) {
		if (id == null) {
			return null;
		}
		return paperchapterDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createPaperchapterSimpleQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		DataQuery dbQuery = DataQuery.init(query, "WTS_PAPER_CHAPTER",
				"ID,TEXTNOTE,PAPERID,PARENTID,NAME,SUBJECTPOINT,SUBJECTNUM,SUBJECTTYPEID,INITPOINT,PTYPE,STYPE");
		return dbQuery;
	}

	@Override
	@Transactional
	public void addSubject(String subjectId, String chapterId, LoginUser currentUser) {
		PaperChapter chapter = paperchapterDaoImpl.getEntity(chapterId);
		Subject subject = subjectDaoImpl.getEntity(subjectId);
		if (chapter == null) {
			throw new RuntimeException("该章节不存在！");
		}
		if (chapter.getPtype().equals("1")) {
			throw new RuntimeException("结构章节下不能放置试题！");
		}
		if (subject == null) {
			throw new RuntimeException("该试题不存在！");
		}
		if (isHasSubject(subjectId, chapter.getPaperid())) {
			throw new RuntimeException("该试题已经存在试卷中！");
		}
		int num = papersubjectDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("CHAPTERID", chapterId, "=")).toList());
		PaperSubject papersubject = new PaperSubject();
		papersubject.setChapterid(chapterId);
		papersubject.setPoint(0);
		papersubject.setSort(num + 1);
		papersubject.setPaperid(chapter.getPaperid());
		papersubject.setSubjectid(subject.getId());
		papersubject.setVersionid(subject.getVersionid());
		papersubjectDaoImpl.insertEntity(papersubject);
	}

	@Override
	@Transactional
	public boolean isHasSubject(String subjectId, String paperid) {
		List<PaperSubject> sameSubjects = papersubjectDaoImpl.selectEntitys(DBRuleList.getInstance()
				.add(new DBRule("PAPERID", paperid, "=")).add(new DBRule("SUBJECTID", subjectId, "=")).toList());
		if (sameSubjects.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void subjectSortUp(String id, LoginUser currentUser) {
		PaperSubject subrela = papersubjectDaoImpl.getEntity(id);
		// 查出章节下的所有题目
		List<PaperSubject> relas = papersubjectDaoImpl.selectEntitys(
				DBRuleList.getInstance().add(new DBRule("CHAPTERID", subrela.getChapterid(), "=")).toList());
		// 顺序排好// 和上一个交换位置
		int sit1 = 0;
		int sit2 = 0;
		Collections.sort(relas, new Comparator<PaperSubject>() {
			@Override
			public int compare(PaperSubject o1, PaperSubject o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		for (int i = 0; i < relas.size(); i++) {
			if (relas.get(i).getId().equals(id)) {
				if (i == 0) {
					return;
				}
				sit2 = i;
				sit1 = i - 1;
			}
			relas.get(i).setSort(i + 1);
		}
		for (int i = 0; i < relas.size(); i++) {
			if (i == sit1) {
				relas.get(i).setSort(i + 2);
			}
			if (i == sit2) {
				relas.get(i).setSort(i);
			}
			papersubjectDaoImpl.editEntity(relas.get(i));
		}
	}

	@Override
	@Transactional
	public PaperChapter insertPaperchapter(String paperid, String name, int sort, LoginUser user) {
		PaperChapter chapter = new PaperChapter();
		chapter.setName(name);
		chapter.setSort(sort);
		chapter.setPaperid(paperid);
		chapter.setStype("1");
		chapter.setParentid("NONE");
		chapter.setInitpoint(0);
		chapter.setPtype("2");
		return insertPaperchapterEntity(chapter, user);
	}

	@Override
	@Transactional
	public PaperChapter getEntity(String paperid, String name) {
		List<PaperChapter> list = paperchapterDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PAPERID", paperid, "="))
						.add(new DBRule("PTYPE", "2", "=")).add(new DBRule("NAME", name, "like")).toList());
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
