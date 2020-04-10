package com.wts.exam.domain.ex;

import java.util.ArrayList;
import java.util.List;

import com.wts.exam.domain.Material;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.PaperChapter;
import com.wts.exam.domain.PaperSubject;
import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectAnalysis;
import com.wts.exam.domain.SubjectAnswer;
import com.wts.exam.domain.SubjectVersion;

/**
 * 用来备份答卷时封装答卷信息
 * 
 * @author macpl
 *
 */
public class PaperJsonBean {
	// --答卷3张表
	private Paper paper;
	private List<PaperChapter> chapters;
	private List<PaperSubject> paperSubjects;
	// --题目5张表
	private List<Subject> subjects;
	private List<SubjectVersion> subjectVersions;
	private List<SubjectAnswer> subjectAnswers;
	private List<SubjectAnalysis> subjectAnalysies;
	private List<Material> materials;
	// --附件表(含附件的base64数据)
	private List<FileJsonBean> files;

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public List<PaperChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<PaperChapter> chapters) {
		this.chapters = chapters;
	}

	public List<PaperSubject> getPaperSubjects() {
		return paperSubjects;
	}

	public void setPaperSubjects(List<PaperSubject> paperSubjects) {
		this.paperSubjects = paperSubjects;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<SubjectVersion> getSubjectVersions() {
		return subjectVersions;
	}

	public void setSubjectVersions(List<SubjectVersion> subjectVersions) {
		this.subjectVersions = subjectVersions;
	}

	public List<SubjectAnswer> getSubjectAnswers() {
		return subjectAnswers;
	}

	public void setSubjectAnswers(List<SubjectAnswer> subjectAnswers) {
		this.subjectAnswers = subjectAnswers;
	}

	public List<SubjectAnalysis> getSubjectAnalysies() {
		return subjectAnalysies;
	}

	public void setSubjectAnalysies(List<SubjectAnalysis> subjectAnalysies) {
		this.subjectAnalysies = subjectAnalysies;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public List<FileJsonBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileJsonBean> files) {
		this.files = files;
	}
	//获得查询附件时需要用到的APPID
	public List<String> getAllAppid() {
		List<String> appids = new ArrayList<>();
		// --答卷3张表
		// private Paper paper;
		appids.add(paper.getId());
		// private List<PaperChapter> chapters;
		for (PaperChapter paperChapter : chapters) {
			appids.add(paperChapter.getId());
		}
		// private List<PaperSubject> paperSubjects;
		for (PaperSubject paperSubject : paperSubjects) {
			appids.add(paperSubject.getId());
		}
		// --题目5张表
		// private List<Subject> subjects;
		for (Subject subject : subjects) {
			appids.add(subject.getId());
		}
		// private List<SubjectVersion> subjectVersions;
		for (SubjectVersion subjectVersion : subjectVersions) {
			appids.add(subjectVersion.getId());
		}
		// private List<SubjectAnswer> subjectAnswers;
		for (SubjectAnswer subjectAnswer : subjectAnswers) {
			appids.add(subjectAnswer.getId());
		}
		// private List<SubjectAnalysis> subjectAnalysies;
		for (SubjectAnalysis subjectAnalysis : subjectAnalysies) {
			appids.add(subjectAnalysis.getId());
		}
		// private List<Material> materials;
		for (Material material : materials) {
			appids.add(material.getId());
		}
		return appids;
	}
}
