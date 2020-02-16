package com.farm.parameter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "AloneApplog")
@Table(name = "alone_applog")
public class AloneApplog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5377999273280487349L;
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "DESCRIBES", length = 1024, nullable = false)
	private String describes;
	@Column(name = "APPUSER", length = 32, nullable = false)
	private String appuser;
	@Column(name = "LEVELS", length = 32)
	private String levels;
	@Column(name = "METHOD", length = 128)
	private String method;
	@Column(name = "CLASSNAME", length = 128)
	private String classname;
	@Column(name = "IP", length = 32)
	private String ip;

	public AloneApplog() {
	}

	public AloneApplog(String ctime, String describes, String appuser) {
		this.ctime = ctime;
		this.describes = describes;
		this.appuser = appuser;
	}

	public AloneApplog(String ctime, String describes, String appuser,
			String levels, String method, String classname, String ip) {
		this.ctime = ctime;
		this.describes = describes;
		this.appuser = appuser;
		this.levels = levels;
		this.method = method;
		this.classname = classname;
		this.ip = ip;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getDescribes() {
		return this.describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getAppuser() {
		return this.appuser;
	}

	public void setAppuser(String appuser) {
		this.appuser = appuser;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}