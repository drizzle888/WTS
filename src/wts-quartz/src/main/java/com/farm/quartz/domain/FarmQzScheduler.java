package com.farm.quartz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * FarmQzScheduler entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "FarmQzScheduler")
@Table(name = "farm_qz_scheduler")
public class FarmQzScheduler implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "TRIGGERID", length = 32, nullable = false)
	private String triggerid;
	@Column(name = "TASKID", length = 32, nullable = false)
	private String taskid;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "EUSER", length = 32, nullable = false)
	private String euser;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "AUTOIS", length = 2, nullable = false)
	private String autois;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "ETIME", length = 16, nullable = false)
	private String etime;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "EUSERNAME", length = 64, nullable = false)
	private String eusername;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;

	public String getTriggerid() {
		return this.triggerid;
	}

	public void setTriggerid(String triggerid) {
		this.triggerid = triggerid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
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

	public String getAutois() {
		return this.autois;
	}

	public void setAutois(String autois) {
		this.autois = autois;
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

	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}