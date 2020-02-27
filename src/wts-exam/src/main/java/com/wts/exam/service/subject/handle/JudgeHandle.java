package com.wts.exam.service.subject.handle;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBRuleList;
import com.farm.core.time.TimeTool;
import com.wts.exam.dao.SubjectDaoInter;
import com.wts.exam.dao.SubjectAnswerDaoInter;
import com.wts.exam.dao.SubjectVersionDaoInter;
import com.wts.exam.domain.CardAnswer;
import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectAnswer;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.AnswerUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.subject.SubjectTipHandle;

/**
 * 判断题的逻辑处理
 * 
 * @author macpl
 *
 */
public class JudgeHandle implements SubjectTipHandle {

	@Override
	public void subjectInitHandle(Subject subject, SubjectVersion version, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl, LoginUser user) {
		// 生成两条判断项目
		SubjectAnswer subjectanswerYes = new SubjectAnswer();
		subjectanswerYes.setAnswer("对");
		// subjectanswerYes.setAnswernote(answernote);
		subjectanswerYes.setCtime(TimeTool.getTimeDate14());
		subjectanswerYes.setCuser(user.getId());
		subjectanswerYes.setCusername(user.getName());
		// subjectanswerYes.setId(id);
		// subjectanswerYes.setPcontent(pcontent);
		subjectanswerYes.setPstate("1");
		subjectanswerYes.setRightanswer("0");
		subjectanswerYes.setSort(1);
		subjectanswerYes.setVersionid(version.getId());
		subjectanswerDaoImpl.insertEntity(subjectanswerYes);
		// ------------------------------------------------------------------
		SubjectAnswer subjectanswerNo = new SubjectAnswer();
		subjectanswerNo.setAnswer("错");
		// subjectanswerNo.setAnswernote(answernote);
		subjectanswerNo.setCtime(TimeTool.getTimeDate14());
		subjectanswerNo.setCuser(user.getId());
		subjectanswerNo.setCusername(user.getName());
		// subjectanswerNo.setId(id);
		// subjectanswerNo.setPcontent(pcontent);
		subjectanswerNo.setPstate("1");
		subjectanswerNo.setRightanswer("0");
		subjectanswerNo.setSort(2);
		subjectanswerNo.setVersionid(version.getId());
		subjectanswerDaoImpl.insertEntity(subjectanswerNo);
	}

	@Override
	public SubjectUnit expressTextSubject(String text, String subjectTypeId, LoginUser currentUser,
			SubjectServiceInter subjectServer, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl) {
		SubjectUnit unit = null;
		String subjectText = text.replaceAll("\\[ANS:\\S+?\\]", "");
		unit = subjectServer.initSubjectUnit(TipType.Judge, subjectTypeId, currentUser);
		unit.getSubject().setPstate("1");
		unit.getVersion().setTipstr(subjectText);
		if (text.indexOf("[ANS:答案") > 0) {
			List<SubjectAnswer> answers = subjectanswerDaoImpl.selectEntitys(
					DBRuleList.getInstance().add(new DBRule("VERSIONID", unit.getVersion().getId(), "=")).toList());
			for (SubjectAnswer answer : answers) {
				if (answer.getSort() == 1) {
					// 对
					if (text.indexOf("[ANS:答案:正确]") > 0) {
						answer.setRightanswer("1");
						unit.getVersion().setAnswered("1");
						subjectanswerDaoImpl.editEntity(answer);
					}
				}
				if (answer.getSort() == 2) {
					// 错
					if (text.indexOf("[ANS:答案:错误]") > 0) {
						answer.setRightanswer("1");
						unit.getVersion().setAnswered("1");
						subjectanswerDaoImpl.editEntity(answer);
					}
				}
			}
		}
		subjectDaoImpl.editEntity(unit.getSubject());
		subjectversionDaoImpl.editEntity(unit.getVersion());
		// --------------------------------------------------------------------
		unit = subjectServer.getSubjectUnit(unit.getVersion().getId());
		return unit;
	}

	@Override
	public boolean isHaveAnswer(List<CardAnswer> answers) {
		for (CardAnswer answer : answers) {
			if (StringUtils.isNotBlank(answer.getValstr()) && answer.getValstr().toUpperCase().equals("TRUE")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void loadVal(SubjectUnit nuit, List<CardAnswer> list) {
		if (list.size() > 0) {
			String chooseAnswerId = "NONE";
			String choosecTime = "00000000000000";
			for (CardAnswer node : list) {
				if (node.getCtime().compareTo(choosecTime) > 0 && node.getValstr().equals("true")) {
					// 值为true且最近的答案为，单选题的答案
					chooseAnswerId = node.getAnswerid();
				}
			}

			for (AnswerUnit answer : nuit.getAnswers()) {
				// 答案的id等于题的id，就是该题的答案
				if (answer.getAnswer().getId().equals(chooseAnswerId)) {
					// 每个答案都有true活false用来控制复选框是否选中
					answer.setVal("true");
					nuit.setFinishIs(true);
				} else {
					answer.setVal("false");
				}
			}
		}
	}

	@Override
	public int runPointWeight(SubjectUnit unit) {
		for (AnswerUnit node : unit.getAnswers()) {
			if (node.getAnswer().getRightanswer().equals("1")) {
				if (node.getVal() != null && node.getVal().toUpperCase().equals("TRUE")) {
					return 100;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isHaveRightAnswer(List<SubjectAnswer> answers) {
		for (SubjectAnswer answer : answers) {
			if (answer.getRightanswer().equals("1")) {
				return true;
			}
		}
		return false;
	}
}
