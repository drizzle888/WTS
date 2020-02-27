package com.wts.exam.service.subject.handle;

import java.util.Arrays;
import java.util.List;

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
 * 复选的逻辑处理
 * 
 * @author macpl
 *
 */
public class CheckboxHandle implements SubjectTipHandle {

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
		unit = subjectServer.initSubjectUnit(TipType.CheckBox, subjectTypeId, currentUser);
		unit.getSubject().setPstate("1");
		unit.getVersion().setTipstr(list.get(0));
		subjectDaoImpl.editEntity(unit.getSubject());
		subjectversionDaoImpl.editEntity(unit.getVersion());
		list = list.subList(1, list.size());
		// ------------------------------------------------------------------
		int n = 0;
		for (String option : list) {
			n++;
			// A.文字
			// B 图形
			// C.符号
			// D一切说明物
			SubjectAnswer subjectanswer = new SubjectAnswer();
			if (option.indexOf("[ANS:答案:正确]") > 0 ) {
				subjectanswer.setRightanswer("1");
				unit.getVersion().setAnswered("1");
				option = option.replace("[ANS:答案:正确]", "");
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
		for (CardAnswer val : list) {
			for (AnswerUnit answer : nuit.getAnswers()) {
				// 答案的id等于题的id，就是该题的答案
				if (answer.getAnswer().getId().equals(val.getAnswerid())) {
					if (StringUtils.isNotBlank(val.getValstr())) {
						// 每个答案都有true活false用来控制复选框是否选中
						answer.setVal(val.getValstr());
						if (val.getValstr().equals("true")) {
							nuit.setFinishIs(true);
						}
					}
				}
			}
		}
	}

	@Override
	public int runPointWeight(SubjectUnit unit) {
		int rightNum = 0;
		int userRightNum = 0;
		for (AnswerUnit node : unit.getAnswers()) {
			if (node.getAnswer().getRightanswer().equals("1")) {
				// 计数正确答案数量和用户选择的正确答案数量,最后如果一致就是正确
				rightNum++;
				if (node.getVal() != null && node.getVal().toUpperCase().equals("TRUE")) {
					userRightNum++;
				}
			} else {
				if (node.getVal() != null && node.getVal().toUpperCase().equals("TRUE")) {
					// 如果选了错误答案就直接返回错误
					userRightNum = -1;
					break;
				}
			}
		}
		return rightNum == userRightNum ? 100 : 0;
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
