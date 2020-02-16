package com.farm.quartz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * FarmQzTask entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "FarmQzTask")
@Table(name = "farm_qz_task")
public class FarmQzTask implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "JOBKEY", length = 128, nullable = false)
	private String jobkey;
	@Column(name = "JOBPARAS", length = 1024)
	private String jobparas;
	@Column(name = "NAME", length = 128, nullable = false)
	private String name;
	@Column(name = "JOBCLASS", length = 128, nullable = false)
	private String jobclass;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "EUSERNAME", length = 64, nullable = false)
	private String eusername;
	@Column(name = "EUSER", length = 32, nullable = false)
	private String euser;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "ETIME", length = 16, nullable = false)
	private String etime;

	public String getJobkey() {
		return this.jobkey;
	}

	public void setJobkey(String jobkey) {
		this.jobkey = jobkey;
	}

	public String getJobparas() {
		return this.jobparas;
	}

	public void setJobparas(String jobparas) {
		this.jobparas = jobparas;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobclass() {
		return this.jobclass;
	}

	public void setJobclass(String jobclass) {
		this.jobclass = jobclass;
	}

	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getEusername() {
		return this.eusername;
	}

	public void setEusername(String eusername) {
		this.eusername = eusername;
	}

	public String getEuser() {
		return this.euser;
	}

	public void setEuser(String euser) {
		this.euser = euser;
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

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getEtime() {
		return this.etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}