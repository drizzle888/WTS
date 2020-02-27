package com.wts.exam.domain.ex;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wts.exam.service.subject.SubjectTipHandle;
import com.wts.exam.service.subject.handle.CheckboxHandle;
import com.wts.exam.service.subject.handle.FileupHandle;
import com.wts.exam.service.subject.handle.InterlocutionHandle;
import com.wts.exam.service.subject.handle.JudgeHandle;
import com.wts.exam.service.subject.handle.SelectHandle;
import com.wts.exam.service.subject.handle.VacancyHandle;

/**
 * 试题类型// 1.填空，2.单选，3.多选，4判断，5问答，6附件
 * 
 * @author
 *
 */
public enum TipType implements java.io.Serializable{
	Vacancy("1", "填空", "exam/SubjectViews/vacancy/VacancyView", "exam/SubjectViews/vacancy/VacancySubject",
			"exam/SubjectViews/vacancy/VacancyAnswer", new VacancyHandle()),
	// ————————————————————
	Select("2", "单选", "exam/SubjectViews/select/SelectView", "exam/SubjectViews/select/SelectSubject",
			"exam/SubjectViews/select/SelectAnswer", new SelectHandle()),
	// ————————————————————
	CheckBox("3", "多选", "exam/SubjectViews/checkbox/CheckboxView","exam/SubjectViews/checkbox/CheckboxSubject", 
			"exam/SubjectViews/checkbox/CheckboxAnswer",new CheckboxHandle()),
	// ————————————————————
	Judge("4", "判断", "exam/SubjectViews/judge/JudgeView","exam/SubjectViews/judge/JudgeSubject", 
			"exam/SubjectViews/judge/JudgeAnswer",new JudgeHandle()),
	// ————————————————————
	Interlocution("5", "问答", "exam/SubjectViews/interlocution/InterlocutionView","exam/SubjectViews/interlocution/InterlocutionSubject",
			"exam/SubjectViews/interlocution/InterlocutionAnswer",new InterlocutionHandle()),
	// ————————————————————
	Fileup("6", "附件", "exam/SubjectViews/fileup/FileupView","exam/SubjectViews/fileup/FileupSubject",
				"exam/SubjectViews/fileup/FileupAnswer",new FileupHandle());
	/**
	 * 题目类型// 1.填空，2.单选，3.多选，4判断，5问答,6附件
	 */
	private String type;

	/**
	 * 试题类型的特殊处理逻辑
	 */
	private SubjectTipHandle handle;

	private String title;
	/**
	 * 试题查看页面
	 */
	private String veiwPage;
	/**
	 * 试题编辑页面
	 */
	private String subjectPage;
	/**
	 * 答案编辑页面
	 */
	private String answerPage;

	TipType(String type, String title, String viewp, String subjectp, String answerp, SubjectTipHandle tipHandle) {
		this.type = type;
		this.title = title;
		this.veiwPage = viewp;
		this.subjectPage = subjectp;
		this.answerPage = answerp;
		this.handle = tipHandle;
	}

	public static TipType getTipType(String type) {
		TipType[] tips = TipType.values();
		for (TipType tip : tips) {
			if (tip.getType().equals(type)) {
				return tip;
			}
		}
		throw new RuntimeException("the type is not defind : " + type);
	}

	/**
	 * 清空一個題目頭部的數字和點
	 * 
	 * @param text
	 * @return
	 */
	public static String clearSubjectTextHead(String text) {
		text=text.trim();
		while (text.length() > 1) {
			if (StringUtils.isNumeric(text.substring(0, 1))) {
				text = text.substring(1);
			} else {
				if (text.length() > 1 && text.substring(0, 1).equals(".")) {
					text = text.substring(1);
				}
				if (text.length() > 1 && text.substring(0, 1).equals("、")) {
					text = text.substring(1);
				}
				if (text.length() > 1 && text.substring(0, 1).equals("．")) {
					text = text.substring(1);
				}
				return text;
			}
		}
		return text;
	}

	/**
	 * 清空一個选项頭部的字母和點
	 * 
	 * @param text
	 * @return
	 */
	public static String clearOptionTextHead(String text) {
		text=text.trim();
		while (text.length() > 1) {
			if (StringUtils.isAllUpperCase(text.substring(0, 1))) {
				text = text.substring(1);
			} else {
				if (text.length() > 1 && text.substring(0, 1).equals(".")) {
					text = text.substring(1);
				}
				if (text.length() > 1 && text.substring(0, 1).equals("、")) {
					text = text.substring(1);
				}
				if (text.length() > 1 && text.substring(0, 1).equals("．")) {
					text = text.substring(1);
				}
				return text;
			}
		}
		return text;
	}

	public String getVeiwPage() {
		return veiwPage;
	}

	public String getSubjectPage() {
		return subjectPage;
	}

	public String getAnswerPage() {
		return answerPage;
	}

	public SubjectTipHandle getHandle() {
		return handle;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static Map<String, String> getDictionary() {
		TipType[] tips = TipType.values();
		Map<String, String> map = new HashMap<>();
		for (TipType tip : tips) {
			map.put(tip.getType(), tip.getTitle());
		}
		return map;
	}

}
