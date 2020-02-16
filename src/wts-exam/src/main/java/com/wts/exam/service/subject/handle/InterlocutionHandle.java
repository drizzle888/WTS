package com.wts.exam.service.subject.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.farm.core.auth.domain.LoginUser;
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
 * 问答题的逻辑处理
 * 
 * @author macpl
 *
 */
public class InterlocutionHandle implements SubjectTipHandle {

	@Override
	public void subjectInitHandle(Subject subject, SubjectVersion version, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl, LoginUser user) {
		// TODO 不做处理

	}

	@Override
	public SubjectUnit expressTextSubject(String text, String subjectTypeId, LoginUser currentUser,
			SubjectServiceInter subjectServer, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl) {
		SubjectUnit unit = null;
		String subjectText = text.replaceAll("\\[ANS:\\S+?\\]", "");
		unit = subjectServer.initSubjectUnit(TipType.Interlocution, subjectTypeId, currentUser);
		unit.getSubject().setPstate("1");
		unit.getVersion().setTipstr(subjectText);
		
		{// ----答案部分--------------------------------------------------------------
			String regex = "\\[ANS:\\S+?\\]";// [ANS:关键字:windows]
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			int n = 0;
			while (m.find()) {
				String answerStr = m.group();
				answerStr = answerStr.replace("[ANS:关键字:", "").replace("]", "");
				SubjectAnswer subjectanswer = new SubjectAnswer();
				subjectanswer.setAnswer(answerStr);
				subjectanswer.setCtime(TimeTool.getTimeDate14());
				subjectanswer.setCuser(currentUser.getId());
				subjectanswer.setCusername(currentUser.getName());
				subjectanswer.setPointweight(10);
				subjectanswer.setPstate("1");
				subjectanswer.setRightanswer("2");
				subjectanswer.setSort(++n);
				subjectanswer.setVersionid(unit.getVersion().getId());
				subjectanswerDaoImpl.insertEntity(subjectanswer);
				unit.getVersion().setAnswered("1");
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
			if (StringUtils.isNotBlank(answer.getValstr())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void loadVal(SubjectUnit nuit, List<CardAnswer> list) {
		for (CardAnswer val : list) {
			// 题的版本id和答案的版本id一致就是问答题的答案
			if (nuit.getVersion().getId().equals(val.getVersionid())) {
				if (StringUtils.isNotBlank(val.getValstr())) {
					nuit.setVal(val.getValstr());
					nuit.setFinishIs(true);
				}
			}
		}
	}

	@Override
	public int runPointWeight(SubjectUnit unit) {
		if (unit == null || unit.getVal() == null) {
			return 0;
		}
		int rightWeight = 0;
		int allweight = 0;
		for (AnswerUnit node : unit.getAnswers()) {
			if (node.getAnswer().getPointweight() != null) {
				allweight = allweight + node.getAnswer().getPointweight();
				// 正确答案
				List<String> sameRightAnswer = new ArrayList<>();
				// 用户答案
				String userAnswer = unit.getVal().toUpperCase();
				// --------------------------
				if (StringUtils.isNotBlank(node.getAnswer().getAnswer().toUpperCase())) {
					sameRightAnswer = Arrays.asList(node.getAnswer().getAnswer().toUpperCase().split("\\|"));
				}
				boolean isRight = false;
				for (String rightNode : sameRightAnswer) {
					if (userAnswer.indexOf(rightNode) >= 0) {
						isRight = true;
						break;
					}
				}
				if (isRight) {
					rightWeight = rightWeight + node.getAnswer().getPointweight();
				}
			}
		}
		if (allweight > 0) {
			return (100 * rightWeight / allweight);
		} else {
			return 0;
		}
	}

	@Override
	public boolean isHaveRightAnswer(List<SubjectAnswer> answers) {
		if (answers != null && answers.size() > 0) {
			return true;
		}
		return false;
	}
}
