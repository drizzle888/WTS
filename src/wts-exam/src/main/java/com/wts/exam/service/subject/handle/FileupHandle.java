package com.wts.exam.service.subject.handle;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.time.TimeTool;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.util.spring.BeanFactory;
import com.wts.exam.dao.SubjectDaoInter;
import com.wts.exam.dao.SubjectAnswerDaoInter;
import com.wts.exam.dao.SubjectVersionDaoInter;
import com.wts.exam.domain.CardAnswer;
import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectAnswer;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.wts.exam.service.SubjectServiceInter;
import com.wts.exam.service.subject.SubjectTipHandle;

/**
 * 附件题的逻辑处理
 * 
 * @author macpl
 *
 */
public class FileupHandle implements SubjectTipHandle {
	private final static FarmFileManagerInter fileServerIMP = (FarmFileManagerInter) BeanFactory
			.getBean("farmFileManagerImpl");

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
			// 答案的id等于题的id，就是该题的答案
			if (nuit.getVersion().getId().equals(val.getVersionid())) {
				if (StringUtils.isNotBlank(val.getValstr())) {
					nuit.setVal(val.getValstr());
					FarmDocfile dfile=	fileServerIMP.getFile(val.getValstr());
					if(dfile!=null){
						nuit.setValtitle(fileServerIMP.getFile(val.getValstr()).getName());
					}else{
						nuit.setValtitle("未发现附件记录");    
					}
					fileServerIMP.submitFile(val.getValstr(), "附件题答案");
					nuit.setFinishIs(true);
				}
			}
		}
	}

	@Override
	public int runPointWeight(SubjectUnit unit) {
		return 0;
	}

	@Override
	public boolean isHaveRightAnswer(List<SubjectAnswer> answers) {
		return true;
	}
}
