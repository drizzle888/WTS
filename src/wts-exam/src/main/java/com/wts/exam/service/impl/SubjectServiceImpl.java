package com.wts.exam.service.impl;

import com.wts.exam.domain.PaperSubject;
import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.SubjectAnswer;
import com.wts.exam.domain.SubjectType;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.AnswerUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.farm.core.time.TimeTool;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_APPLICATION_TYPE;
import com.farm.doc.server.commons.DocMessageCache;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wts.exam.dao.PaperSubjectDaoInter;
import com.wts.exam.dao.SubjectAnalysisDaoInter;
import com.wts.exam.dao.SubjectDaoInter;
import com.wts.exam.dao.SubjectAnswerDaoInter;
import com.wts.exam.dao.SubjectCommentDaoInter;
import com.wts.exam.dao.SubjectTypeDaoInter;
import com.wts.exam.dao.SubjectUserOwnDaoInter;
import com.wts.exam.dao.SubjectVersionDaoInter;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.utils.SubjectUnitCaches;
import com.wts.exam.service.SubjectAnswerServiceInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import com.farm.core.FarmUtils;
import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考题服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class SubjectServiceImpl implements SubjectServiceInter {
	@Resource
	private SubjectAnalysisDaoInter SubjectAnalysisDaoImpl;
	@Resource
	private SubjectCommentDaoInter SubjectCommentDaoImpl;
	@Resource
	private SubjectUserOwnDaoInter subjectuserownDaoImpl;

	@Resource
	private SubjectDaoInter subjectDaoImpl;
	@Resource
	private SubjectVersionDaoInter subjectversionDaoImpl;
	@Resource
	private SubjectAnswerDaoInter subjectanswerDaoImpl;
	@Resource
	private SubjectTypeDaoInter subjecttypeDaoImpl;
	@Resource
	private SubjectAnswerServiceInter subjectAnswerServiceImpl;
	@Resource
	private PaperSubjectDaoInter papersubjectDaoImpl;
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	private static final Logger log = Logger.getLogger(SubjectServiceImpl.class);

	@Override
	@Transactional
	public SubjectVersion editSubjectEntity(SubjectVersion entity, String tipanalysis, LoginUser currentUser) {
		SubjectVersion entity2 = subjectversionDaoImpl.getEntity(entity.getId());
		String oldText = entity2.getTipnote();
		entity2.setTipstr(entity.getTipstr());
		entity2.setTipnote(entity.getTipnote());
		if (entity2.getTipstr() == null) {
			entity2.setTipstr("");
		}
		subjectversionDaoImpl.editEntity(entity2);
		Subject subject = subjectDaoImpl.getEntity(entity2.getSubjectid());
		subject.setPstate("1");
		subjectDaoImpl.editEntity(subject);
		// --------------------------------------------------
		updateAnswered(entity2.getSubjectid());
		farmFileManagerImpl.updateFileByAppHtml(oldText, entity.getTipnote(), entity.getId(),
				FILE_APPLICATION_TYPE.SUBJECTNOTE);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
		return entity2;
	}

	@Override
	@Transactional
	public void deleteSubjectEntity(String subjectId, LoginUser user) {
		List<PaperSubject> paperSubjects = papersubjectDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("SUBJECTID", subjectId, "=")).toList());
		if (paperSubjects.size() > 0) {
			throw new RuntimeException("题被答卷(ID:" + paperSubjects.get(0).getPaperid() + ")引用，无法删除!");
		}
		// 獲得所有版本
		List<SubjectVersion> versions = subjectversionDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("SUBJECTID", subjectId, "=")).toList());
		for (SubjectVersion version : versions) {
			// 删除答案
			List<SubjectAnswer> answers = subjectanswerDaoImpl.selectEntitys(
					DBRuleList.getInstance().add(new DBRule("VERSIONID", version.getId(), "=")).toList());
			for (SubjectAnswer answer : answers) {
				// 删除答案附件
				farmFileManagerImpl.cancelFilesByApp(answer.getId());
			}
			subjectanswerDaoImpl.deleteEntitys(
					DBRuleList.getInstance().add(new DBRule("VERSIONID", version.getId(), "=")).toList());
			// 删除版本
			subjectversionDaoImpl.deleteEntity(version);
			// 删除题附件
			farmFileManagerImpl.cancelFilesByApp(version.getId());
		}
		// 删除引用
		DBRuleList dbRuleList = DBRuleList.getInstance().add(new DBRule("SUBJECTID", subjectId, "="));
		SubjectAnalysisDaoImpl.deleteEntitys(dbRuleList.toList());
		SubjectCommentDaoImpl.deleteEntitys(dbRuleList.toList());
		subjectuserownDaoImpl.deleteEntitys(dbRuleList.toList());
		// 删除试题
		subjectDaoImpl.deleteEntity(subjectDaoImpl.getEntity(subjectId));
	}

	@Override
	@Transactional
	public Subject getSubjectEntity(String id) {
		if (id == null) {
			return null;
		}
		return subjectDaoImpl.getEntity(id);
	}

	@Override
	@Transactional
	public DataQuery createSubjectSimpleQuery(DataQuery query) {
		query.addRule(new DBRule("A.PSTATE", "1", "="));
		query.addDefaultSort(new DBSort("b.ctime", "desc"));
		DataQuery dbQuery = DataQuery.init(query,
				"WTS_SUBJECT a left join WTS_SUBJECT_VERSION b on a.VERSIONID=b.id left join WTS_SUBJECT_TYPE c on a.TYPEID =c.ID left join WTS_MATERIAL d on a.MATERIALID=d.id",
				"a.ID as ID,a.uuid as UUID,b.TIPNOTE as TIPNOTE,a.ANALYSISNUM as ANALYSISNUM,a.DONUM as DONUM,a.RIGHTNUM as RIGHTNUM,b.id as VID,b.TIPSTR as TIPSTR,C.NAME as TYPENAME,b.TIPTYPE as TIPTYPE,b.ANSWERED as ANSWERED,d.title as title");
		return dbQuery;
	}

	@Override
	@Transactional
	public SubjectUnit initSubjectUnit(TipType tipType, String subjectTypeid, LoginUser user) {
		SubjectUnit unit = new SubjectUnit();
		// 创建一个试题临时对象-----------------------------------
		Subject subject = new Subject();
		subject.setPstate("0");
		subject.setTypeid(subjectTypeid);
		subject.setAnalysisnum(0);
		subject.setCommentnum(0);
		subject.setPraisenum(0);
		subject.setDonum(0);
		subject.setRightnum(0);
		subject = subjectDaoImpl.insertEntity(subject);
		// 创建一个试题临时版本------------------------------------
		SubjectVersion version = new SubjectVersion();
		version.setCtime(TimeTool.getTimeDate14());
		version.setCuser(user.getId());
		version.setCusername(user.getName());
		// version.setPcontent(pcontent);
		version.setPstate("1");
		version.setSubjectid(subject.getId());
		// version.setTipnote(tipnote);
		version.setTipstr("题目");
		version.setAnswered("3");
		version.setTiptype(tipType.getType());
		version = subjectversionDaoImpl.insertEntity(version);
		// ------------------------------------------------------
		subject.setVersionid(version.getId());
		subjectDaoImpl.editEntity(subject);
		// -----------------------------
		SubjectType subjectType = subjecttypeDaoImpl.getEntity(subjectTypeid);
		// -----------------------------
		tipType.getHandle().subjectInitHandle(subject, version, subjectanswerDaoImpl, subjectDaoImpl,
				subjectversionDaoImpl, user);
		// --------------
		unit.setSubject(subject);
		unit.setVersion(version);
		unit.setSubjectType(subjectType);
		unit.setTipType(tipType);
		// -------------------------------
		return unit;
	}

	@Override
	@Transactional
	public SubjectUnit getSubjectUnit(String versionId) {
		if (SubjectUnitCaches.get(versionId) != null) {
			// 抓取缓存
			return SubjectUnitCaches.get(versionId);
		}
		SubjectUnit unit = new SubjectUnit();
		unit.setVersion(subjectversionDaoImpl.getEntity(versionId));
		unit.setSubject(subjectDaoImpl.getEntity(unit.getVersion().getSubjectid()));
		unit.setSubjectType(subjecttypeDaoImpl.getEntity(unit.getSubject().getTypeid()));
		unit.setTipType(TipType.getTipType(unit.getVersion().getTiptype()));
		List<AnswerUnit> answerunits = new ArrayList<>();
		List<SubjectAnswer> answers = subjectanswerDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("VERSIONID", versionId, "=")).toList());
		for (SubjectAnswer answer : answers) {
			AnswerUnit answerunit = new AnswerUnit();
			answerunit.setAnswer(answer);
			// answerunit.setVal(val);
			answerunits.add(answerunit);
		}
		unit.setAnswers(answerunits);
		Collections.sort(unit.getAnswers(), new Comparator<AnswerUnit>() {
			@Override
			public int compare(AnswerUnit o1, AnswerUnit o2) {
				return o1.getAnswer().getSort() - o2.getAnswer().getSort();
			}
		});
		// 写入缓存
		SubjectUnitCaches.put(versionId, unit);
		return SubjectUnitCaches.get(versionId);
	}

	@Override
	@Transactional
	public void subjectTypeSetting(String subjectId, String typeId, LoginUser currentUser) {
		if (StringUtils.isNotBlank(typeId)) {
			Subject entity2 = subjectDaoImpl.getEntity(subjectId);
			entity2.setTypeid(typeId);
			subjectDaoImpl.editEntity(entity2);
			// 更新缓存
			SubjectUnitCaches.refresh(entity2.getVersionid());
		}
	}

	@Override
	@Transactional
	public List<SubjectUnit> addTextSubjects(String typeid, String texts, LoginUser currentUser) {
		// texts按照换行符断行
		texts = texts.replaceAll(" +", "").replaceAll("（", "(").replaceAll("）", ")")
		// .replaceAll("。", ".").replaceAll("，", ",")
		;
		String[] subNode = texts.split("\\[SUB:");
		List<SubjectUnit> units = new ArrayList<>();
		for (String node : subNode) {
			if (StringUtils.isBlank(node)) {
				continue;
			}
			if (node.indexOf("填空题]") == 0 || node.indexOf("单选题]") == 0 || node.indexOf("多选题]") == 0
					|| node.indexOf("判断题]") == 0 || node.indexOf("问答题]") == 0) {
				node = "[SUB:" + node;
				units.add(expressSubject(node, typeid, currentUser));
			}
		}
		return units;
	}

	/**
	 * 从一个字符串中接新出试题
	 * 
	 * @param subText
	 *            试题描述字符串
	 * @param typeid
	 *            题库分类
	 * @param currentUser
	 * @return
	 */
	private SubjectUnit expressSubject(String subText, String typeid, LoginUser currentUser) {
		String analysisText = null;
		if (subText.indexOf("[ANALYSIS]") > 0) {
			analysisText = subText.substring(subText.indexOf("[ANALYSIS]") + "[ANALYSIS]".length());
			subText = subText.substring(0, subText.indexOf("[ANALYSIS]"));
		}
		SubjectUnit unit = null;
		// 1.填空，2.单选，3.多选，4判断，5问答
		if (subText.indexOf("[SUB:填空题]") == 0) {
			subText = subText.replaceFirst("\\[SUB:\\S+题\\]", "");
			unit = TipType.Vacancy.getHandle().expressTextSubject(TipType.clearSubjectTextHead(subText), typeid,
					currentUser, this, subjectanswerDaoImpl, subjectDaoImpl, subjectversionDaoImpl);
		}
		if (subText.indexOf("[SUB:单选题]") == 0) {
			subText = subText.replaceFirst("\\[SUB:\\S+题\\]", "");
			unit = TipType.Select.getHandle().expressTextSubject(TipType.clearSubjectTextHead(subText), typeid,
					currentUser, this, subjectanswerDaoImpl, subjectDaoImpl, subjectversionDaoImpl);
		}
		if (subText.indexOf("[SUB:多选题]") == 0) {
			subText = subText.replaceFirst("\\[SUB:\\S+题\\]", "");
			unit = TipType.CheckBox.getHandle().expressTextSubject(TipType.clearSubjectTextHead(subText), typeid,
					currentUser, this, subjectanswerDaoImpl, subjectDaoImpl, subjectversionDaoImpl);
		}
		if (subText.indexOf("[SUB:判断题]") == 0) {
			subText = subText.replaceFirst("\\[SUB:\\S+题\\]", "");
			unit = TipType.Judge.getHandle().expressTextSubject(TipType.clearSubjectTextHead(subText), typeid,
					currentUser, this, subjectanswerDaoImpl, subjectDaoImpl, subjectversionDaoImpl);
		}
		if (subText.indexOf("[SUB:问答题]") == 0) {
			subText = subText.replaceFirst("\\[SUB:\\S+题\\]", "");
			unit = TipType.Interlocution.getHandle().expressTextSubject(TipType.clearSubjectTextHead(subText), typeid,
					currentUser, this, subjectanswerDaoImpl, subjectDaoImpl, subjectversionDaoImpl);
		}
		if (unit == null) {
			throw new RuntimeException("无法解析的题类型：" + subText);
		}
		if (analysisText != null) {
			SubjectAnalysis analysisEntity = new SubjectAnalysis();
			analysisEntity.setCtime(TimeTool.getTimeDate14());
			analysisEntity.setCuser(currentUser.getId());
			analysisEntity.setCusername(currentUser.getName());
			analysisEntity.setPcontent("批量导入");
			analysisEntity.setPstate("1");
			analysisEntity.setSubjectid(unit.getSubject().getId());
			analysisEntity.setText(analysisText);
			SubjectAnalysisDaoImpl.insertEntity(analysisEntity);
		}
		return unit;
	}

	@Override
	@Transactional
	public int clearSubject(LoginUser currentUser) {
		List<Subject> subjects = subjectDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("PSTATE", "0", "=")).toList());
		for (Subject node : subjects) {
			deleteSubjectEntity(node.getId(), currentUser);
		}
		log.info("clear:" + subjects.size());
		return subjects.size();
	}

	@Override
	@Transactional
	public void updateAnswered(String subjectId) {
		// 查找题，查找版本，取出版本题答案，判断是否有正确答案
		Subject subject = getSubjectEntity(subjectId);
		String versionId = subject.getVersionid();
		SubjectVersion version = subjectversionDaoImpl.getEntity(versionId);
		List<SubjectAnswer> answers = subjectanswerDaoImpl
				.selectEntitys(DBRuleList.getInstance().add(new DBRule("VERSIONID", versionId, "=")).toList());
		if (TipType.getTipType(version.getTiptype()).getHandle().isHaveRightAnswer(answers)) {
			version.setAnswered("1");
		} else {
			version.setAnswered("0");
		}
		subjectversionDaoImpl.editEntity(version);
		// 更新缓存
		SubjectUnitCaches.refresh(versionId);
	}

	@Override
	@Transactional
	public void bindMaterial(String subjectId, String materialId, LoginUser currentUser) {
		Subject subject = subjectDaoImpl.getEntity(subjectId);
		subject.setMaterialid(materialId);
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
	}

	@Override
	@Transactional
	public void clearMaterial(String subjectId, LoginUser currentUser) {
		Subject subject = subjectDaoImpl.getEntity(subjectId);
		subject.setMaterialid(null);
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
	}

	@Override
	@Transactional
	public SubjectUnit parseSubjectJsonVal(String jsons) {
		SubjectUnit backUnit = null;
		JsonParser parse = new JsonParser();
		JsonArray jsonArray = (JsonArray) parse.parse(jsons);
		for (JsonElement obj : jsonArray) {
			if (obj.isJsonObject()) {
				JsonObject sjonObj = obj.getAsJsonObject();
				String versionid = sjonObj.get("versionid").getAsString();
				String answerid = sjonObj.get("answerid").getAsString();
				String value = sjonObj.get("value").getAsString();
				if (backUnit == null) {
					backUnit = getSubjectUnit(versionid);
				}
				if (StringUtils.isBlank(answerid) || answerid.toUpperCase().equals("NONE")) {
					backUnit.setVal(value);
				} else {
					for (AnswerUnit answer : backUnit.getAnswers()) {
						if (answer.getAnswer().getId().equals(answerid)) {
							answer.setVal(value);
						}
					}
				}
			}
		}
		return backUnit;
	}

	@Override
	@Transactional
	public int refrashAnalysisnum(String subjectid) {
		int num = SubjectAnalysisDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("subjectid", subjectid, "=")).toList());
		Subject subject = subjectDaoImpl.getEntity(subjectid);
		subject.setAnalysisnum(num);
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
		return num;
	}

	@Override
	@Transactional
	public String getSubjectVersionId(String subjectId) {
		return subjectDaoImpl.getEntity(subjectId).getVersionid();
	}

	@Override
	@Transactional
	public int doPraise(String subjectId, LoginUser user) {
		Subject subject = subjectDaoImpl.getEntity(subjectId);
		if (DocMessageCache.getInstance().add(user.getId(), subjectId, "YERS")) {
			// 赞
			subject.setPraisenum(subject.getPraisenum() + 1);
		} else {
			// 取消讚
			DocMessageCache.getInstance().remove(user.getId(), subjectId, "YERS");
			subject.setPraisenum(subject.getPraisenum() - 1);
		}
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
		return subject.getPraisenum();
	}

	@Override
	@Transactional
	public int refrashCommentnum(String subjectid) {
		int num = SubjectCommentDaoImpl
				.countEntitys(DBRuleList.getInstance().add(new DBRule("subjectid", subjectid, "=")).toList());
		Subject subject = subjectDaoImpl.getEntity(subjectid);
		subject.setCommentnum(num);
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
		return num;
	}

	@Override
	@Transactional
	public void addDoNum(String subjectid) {
		Subject subject = subjectDaoImpl.getEntity(subjectid);
		subject.setDonum(subject.getDonum() + 1);
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
	}

	@Override
	@Transactional
	public void addRightNum(String subjectid) {
		Subject subject = subjectDaoImpl.getEntity(subjectid);
		subject.setRightnum(subject.getRightnum() + 1);
		subjectDaoImpl.editEntity(subject);
		// 更新缓存
		SubjectUnitCaches.refresh(subject.getVersionid());
	}
}
