package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：考卷类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Paper")
@Table(name = "wts_paper")
public class Paper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "PAPERNOTE", length = 65535)
	private String papernote;
	@Column(name = "ADVICETIME", length = 10, nullable = false)
	private Integer advicetime;
	@Column(name = "LOWPOINT", length = 10, nullable = false)
	private Integer lowpoint;
	@Column(name = "TOPPOINT", length = 10, nullable = false)
	private Integer toppoint;
	@Column(name = "AVGPOINT", length = 10, nullable = false)
	private Integer avgpoint;
	@Column(name = "COMPLETETNUM", length = 10, nullable = false)
	private Integer completetnum;
	@Column(name = "POINTNUM", length = 10, nullable = false)
	private Integer pointnum;
	@Column(name = "SUBJECTNUM", length = 10, nullable = false)
	private Integer subjectnum;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "NAME", length = 128, nullable = false)
	private String name;
	@Column(name = "EUSER", length = 32, nullable = false)
	private String euser;
	@Column(name = "EUSERNAME", length = 64, nullable = false)
	private String eusername;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "ETIME", length = 16, nullable = false)
	private String etime;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "EXAMTYPEID", length = 32)
	private String examtypeid;
	@Column(name = "MODELTYPE", length = 2, nullable = false)
	private String modeltype;
	
	public String getModeltype() {
		return modeltype;
	}

	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}

	public String getPapernote() {
		return this.papernote;
	}

	public void setPapernote(String papernote) {
		this.papernote = papernote;
	}

	public Integer getAdvicetime() {
		return this.advicetime;
	}

	public void setAdvicetime(Integer advicetime) {
		this.advicetime = advicetime;
	}

	public Integer getLowpoint() {
		return this.lowpoint;
	}

	public void setLowpoint(Integer lowpoint) {
		this.lowpoint = lowpoint;
	}

	public Integer getToppoint() {
		return this.toppoint;
	}

	public void setToppoint(Integer toppoint) {
		this.toppoint = toppoint;
	}

	public Integer getAvgpoint() {
		return this.avgpoint;
	}

	public void setAvgpoint(Integer avgpoint) {
		this.avgpoint = avgpoint;
	}

	public Integer getCompletetnum() {
		return this.completetnum;
	}

	public void setCompletetnum(Integer completetnum) {
		this.completetnum = completetnum;
	}

	public Integer getPointnum() {
		return this.pointnum;
	}

	public void setPointnum(Integer pointnum) {
		this.pointnum = pointnum;
	}

	public Integer getSubjectnum() {
		return this.subjectnum;
	}

	public void setSubjectnum(Integer subjectnum) {
		this.subjectnum = subjectnum;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEuser() {
		return this.euser;
	}

	public void setEuser(String euser) {
		this.euser = euser;
	}

	public String getEusername() {
		return this.eusername;
	}

	public void setEusername(String eusername) {
		this.eusername = eusername;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getEtime() {
		return this.etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getExamtypeid() {
		return this.examtypeid;
	}

	public void setExamtypeid(String examtypeid) {
		this.examtypeid = examtypeid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}