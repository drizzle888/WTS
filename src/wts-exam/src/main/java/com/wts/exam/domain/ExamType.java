package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：考试分类类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "ExamType")
@Table(name = "wts_exam_type")
public class ExamType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "MUSER", length = 32, nullable = false)
	private String muser;
	@Column(name = "PARENTID", length = 32)
	private String parentid;
	@Column(name = "SORT", length = 10)
	private Integer sort;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "STATE", length = 1, nullable = false)
	private String state;
	@Column(name = "UTIME", length = 14, nullable = false)
	private String utime;
	@Column(name = "TREECODE", length = 256, nullable = false)
	private String treecode;
	@Column(name = "COMMENTS", length = 128)
	private String comments;
	@Column(name = "NAME", length = 64, nullable = false)
	private String name;
	@Column(name = "CTIME", length = 14, nullable = false)
	private String ctime;
	@Column(name = "MNGPOP", length = 1, nullable = false)
	private String mngpop;
	@Column(name = "ADJUDGEPOP", length = 1, nullable = false)
	private String adjudgepop;
	@Column(name = "QUERYPOP", length = 1, nullable = false)
	private String querypop;
	@Column(name = "SUPERPOP", length = 1, nullable = false)
	private String superpop;

	
	
	
	public String getMngpop() {
		return mngpop;
	}

	public void setMngpop(String mngpop) {
		this.mngpop = mngpop;
	}

	public String getAdjudgepop() {
		return adjudgepop;
	}

	public void setAdjudgepop(String adjudgepop) {
		this.adjudgepop = adjudgepop;
	}

	public String getQuerypop() {
		return querypop;
	}

	public void setQuerypop(String querypop) {
		this.querypop = querypop;
	}

	public String getSuperpop() {
		return superpop;
	}

	public void setSuperpop(String superpop) {
		this.superpop = superpop;
	}

	public String getMuser() {
		return this.muser;
	}

	public void setMuser(String muser) {
		this.muser = muser;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUtime() {
		return this.utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getTreecode() {
		return this.treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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