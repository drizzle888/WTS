package com.wts.exam.domain.ex;

import java.util.List;

import com.wts.exam.domain.Material;
import com.wts.exam.domain.PaperChapter;

/**
 * 考卷章节
 * 
 * @author wd
 *
 */
public class ChapterUnit implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5223058154995137492L;
	private PaperChapter chapter;
	/**
	 * 考卷章节
	 */
	private List<ChapterUnit> chapters;

	
	/**
	 * 章节的引用材料
	 */
	private List<Material> materials;
	
	
	/**
	 * 考题
	 */
	private List<SubjectUnit> subjects;

	/**
	 * 考题数量
	 */
	private int subjectNum;

	/**
	 * 总分
	 */
	private int allpoint;
	
	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public PaperChapter getChapter() {
		return chapter;
	}

	public int getSubjectNum() {
		return subjectNum;
	}

	public void setSubjectNum(int subjectNum) {
		this.subjectNum = subjectNum;
	}

	public int getAllpoint() {
		return allpoint;
	}

	public void setAllpoint(int allpoint) {
		this.allpoint = allpoint;
	}

	public void setChapter(PaperChapter chapter) {
		this.chapter = chapter;
	}

	public List<ChapterUnit> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterUnit> chapters) {
		this.chapters = chapters;
	}

	public List<SubjectUnit> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectUnit> subjects) {
		this.subjects = subjects;
	}
}
