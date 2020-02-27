package com.wts.exam.service.subject.handle;

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
 * 填空题的逻辑处理
 * 
 * @author macpl
 *
 */
public class VacancyHandle implements SubjectTipHandle {

	@Override
	public void subjectInitHandle(Subject subject, SubjectVersion version, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl, LoginUser user) {
		// TODO 不需要执行
	}

	@Override
	public SubjectUnit expressTextSubject(String text, String subjectTypeId, LoginUser currentUser,
			SubjectServiceInter subjectServer, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl) {
		SubjectUnit unit = null;
		String subjectText = null;
		{// ----预处理
			subjectText = text.replaceAll("\\[ANS:\\S+?\\]", "____").replaceAll("\\[SUB:\\S+题\\]", "");
			unit = subjectServer.initSubjectUnit(TipType.Vacancy, subjectTypeId, currentUser);
		}
		{// ----答案部分--------------------------------------------------------------
			String regex = "\\[ANS:\\S+?\\]";// [ANS:答案:windows]
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(text);
			int n = 0;
			while (m.find()) {
				String answerStr = m.group();
				answerStr = answerStr.replace("[ANS:答案:", "").replace("]", "");
				SubjectAnswer subjectanswer = new SubjectAnswer();
				subjectanswer.setAnswer(answerStr);
				subjectanswer.setCtime(TimeTool.getTimeDate14());
				subjectanswer.setCuser(currentUser.getId());
				subjectanswer.setCusername(currentUser.getName());
				subjectanswer.setPointweight(0);
				subjectanswer.setPstate("1");
				subjectanswer.setRightanswer("2");
				subjectanswer.setSort(++n);
				subjectanswer.setVersionid(unit.getVersion().getId());
				subjectanswerDaoImpl.insertEntity(subjectanswer);
				unit.getVersion().setAnswered("1");
			}
		}
		{// ----题干部分--------------------------------------------------------------
			unit.getSubject().setPstate("1");
			unit.getVersion().setTipstr(subjectText);
			subjectDaoImpl.editEntity(unit.getSubject());
			subjectversionDaoImpl.editEntity(unit.getVersion());
		}
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
			for (AnswerUnit answer : nuit.getAnswers()) {
				// 答案的id等于题的id，就是该题的答案
				if (answer.getAnswer().getId().equals(val.getAnswerid())) {
					if (StringUtils.isNotBlank(val.getValstr())) {
						answer.setVal(val.getValstr());
						nuit.setFinishIs(true);
					}
				}
			}
		}
	}

	@Override
	public int runPointWeight(SubjectUnit unit) {
		int rightNum = 0;
		int userRightNum = 0;
		boolean isAllRight = true;
		for (AnswerUnit node : unit.getAnswers()) {
			if (node.getAnswer().getPointweight() != null) {
				// 正确空的数量
				rightNum = rightNum + node.getAnswer().getPointweight();
				List<String> sameRightAnswer = Arrays.asList(node.getAnswer().getAnswer().toUpperCase().split("\\|"));
				if (node.getVal() != null) {
					String userAnser = node.getVal().toUpperCase().replaceAll("\\s*", "");
					// 用户答案 是正确答案
					if (sameRightAnswer.contains(userAnser)) {
						userRightNum = userRightNum + node.getAnswer().getPointweight();
					} else {
						isAllRight = false;
					}
				} else {
					isAllRight = false;
				}
			}
		}
		if (isAllRight) {
			// 都是正确的答案，之间返回100%的得分权重
			return 100;
		}
		if (rightNum > 0) {
			return (100 * userRightNum / rightNum);
		} else {
			return 0;
		}
	}

	@Override
	public boolean isHaveRightAnswer(List<SubjectAnswer> answers) {
		for (SubjectAnswer answer : answers) {
			if (!answer.getAnswer().trim().equals("NONE")) {
				return true;
			}
		}
		return false;
	}
}
