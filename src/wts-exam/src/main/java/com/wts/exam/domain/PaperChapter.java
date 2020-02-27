package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：试卷章节类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "PaperChapter")
@Table(name = "wts_paper_chapter")
public class PaperChapter implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "TEXTNOTE", length = 65535)
	private String textnote;
	@Column(name = "PAPERID", length = 32, nullable = false)
	private String paperid;
	@Column(name = "PARENTID", length = 32, nullable = false)
	private String parentid;
	@Column(name = "NAME", length = 64, nullable = false)
	private String name;
	@Column(name = "SORT", length = 10, nullable = false)
	private Integer sort;
	@Column(name = "SUBJECTPOINT", length = 10)
	private Integer subjectpoint;
	@Column(name = "SUBJECTNUM", length = 10)
	private Integer subjectnum;
	@Column(name = "SUBJECTTYPEID", length = 32)
	private String subjecttypeid;
	@Column(name = "INITPOINT", length = 10, nullable = false)
	private Integer initpoint;
	@Column(name = "PTYPE", length = 2, nullable = false)
	private String ptype;
	@Column(name = "STYPE", length = 2, nullable = false)
	private String stype;
	@Column(name = "TREECODE", length = 256, nullable = false)
    private String treecode;

	public String getTextnote() {
		return this.textnote;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getTreecode() {
		return treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}

	public void setTextnote(String textnote) {
		this.textnote = textnote;
	}

	public String getPaperid() {
		return this.paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSubjectpoint() {
		return this.subjectpoint;
	}

	public void setSubjectpoint(Integer subjectpoint) {
		this.subjectpoint = subjectpoint;
	}

	public Integer getSubjectnum() {
		return this.subjectnum;
	}

	public void setSubjectnum(Integer subjectnum) {
		this.subjectnum = subjectnum;
	}

	public String getSubjecttypeid() {
		return this.subjecttypeid;
	}

	public void setSubjecttypeid(String subjecttypeid) {
		this.subjecttypeid = subjecttypeid;
	}

	public Integer getInitpoint() {
		return this.initpoint;
	}

	public void setInitpoint(Integer initpoint) {
		this.initpoint = initpoint;
	}

	public String getPtype() {
		return this.ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getStype() {
		return this.stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}