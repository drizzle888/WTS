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
 * 空的逻辑处理
 * 
 * @author macpl
 *
 */
public class SelectHandle implements SubjectTipHandle {

	@Override
	public void subjectInitHandle(Subject subject, SubjectVersion version, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl, LoginUser user) {
		// TODO 不做处理

	}

	@Override
	public SubjectUnit expressTextSubject(String subjectText, String subjectTypeId, LoginUser currentUser,
			SubjectServiceInter subjectServer, SubjectAnswerDaoInter subjectanswerDaoImpl,
			SubjectDaoInter subjectDaoImpl, SubjectVersionDaoInter subjectversionDaoImpl) {
		String[] options = subjectText.split("\n");
		if (options == null) {
			return null;
		}
		List<String> list = Arrays.asList(options);

		SubjectUnit unit = null;
		// 1.食品标签包括了食品包装上的( )
		unit = subjectServer.initSubjectUnit(TipType.Select, subjectTypeId, currentUser);
		unit.getSubject().setPstate("1");
		unit.getVersion().setTipstr(list.get(0));
		subjectDaoImpl.editEntity(unit.getSubject());
		list = list.subList(1, list.size());
		// ------------------------------------------------------------------
		int n = 0;
		boolean isRightAnswer = false;
		for (String option : list) {
			n++;
			// A.文字
			// B 图形
			// C.符号[ANS:答案:正确]
			// D一切说明物
			SubjectAnswer subjectanswer = new SubjectAnswer();
			if (option.indexOf("[ANS:答案:正确]") > 0 && !isRightAnswer) {
				subjectanswer.setRightanswer("1");
				unit.getVersion().setAnswered("1");
				option = option.replace("[ANS:答案:正确]", "");
				isRightAnswer = true;
			} else {
				subjectanswer.setRightanswer("0");
			}
			subjectanswer.setAnswer(TipType.clearOptionTextHead(option));
			subjectanswer.setCtime(TimeTool.getTimeDate14());
			subjectanswer.setCuser(currentUser.getId());
			subjectanswer.setCusername(currentUser.getName());
			subjectanswer.setPstate("1");

			subjectanswer.setSort(n);
			subjectanswer.setVersionid(unit.getVersion().getId());
			subjectanswerDaoImpl.insertEntity(subjectanswer);
		}
		// --------------------------------------------------------------------
		subjectversionDaoImpl.editEntity(unit.getVersion());
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
