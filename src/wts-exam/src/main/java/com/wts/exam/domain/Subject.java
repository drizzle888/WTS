package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：考题类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Subject")
@Table(name = "wts_subject")
public class Subject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "VERSIONID", length = 32)
	private String versionid;
	@Column(name = "TYPEID", length = 32)
	private String typeid;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "MATERIALID", length = 32)
	private String materialid;
	@Column(name = "PRAISENUM", length = 10)
	private Integer praisenum;
	@Column(name = "COMMENTNUM", length = 10)
	private Integer commentnum;
	@Column(name = "ANALYSISNUM", length = 10)
	private Integer analysisnum;
	@Column(name = "DONUM", length = 10)
	private Integer donum;	
	@Column(name = "RIGHTNUM", length = 10)
	private Integer rightnum;
	
	
	public Integer getAnalysisnum() {
		return analysisnum;
	}
	
	public Integer getDonum() {
		return donum;
	}

	public void setDonum(Integer donum) {
		this.donum = donum;
	}

	public Integer getRightnum() {
		return rightnum;
	}

	public void setRightnum(Integer rightnum) {
		this.rightnum = rightnum;
	}

	public void setAnalysisnum(Integer analysisnum) {
		this.analysisnum = analysisnum;
	}

	public String getMaterialid() {
		return materialid;
	}

	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}

	public Integer getPraisenum() {
		return praisenum;
	}

	public void setPraisenum(Integer praisenum) {
		this.praisenum = praisenum;
	}

	public Integer getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(Integer commentnum) {
		this.commentnum = commentnum;
	}

	public String getVersionid() {
		return this.versionid;
	}

	public String getPstate() {
		return pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}