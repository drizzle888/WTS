package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：试题评论类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "SubjectComment")
@Table(name = "wts_subject_comment")
public class SubjectComment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "SUBJECTID", length = 32, nullable = false)
	private String subjectid;
	@Column(name = "TEXT", length = 65535, nullable = false)
	private String text;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;

	public String getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}
	
	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}