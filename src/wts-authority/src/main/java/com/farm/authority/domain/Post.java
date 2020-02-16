package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：岗位类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20141124152033
 *说明：
 */
@Entity(name = "Post")
@Table(name = "ALONE_AUTH_POST")
public class Post implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3393948958969271827L;
	@Column(name = "EXTENDIS", length = 2, nullable = false)
	private String extendis;
	@Column(name = "NAME", length = 64, nullable = false)
	private String name;
	@Column(name = "ORGANIZATIONID", length = 32, nullable = false)
	private String organizationid;
	@Column(name = "PSTATE", length = 1, nullable = false)
	private String pstate;
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
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;

	public String getExtendis() {
		return extendis;
	}

	public void setExtendis(String extendis) {
		this.extendis = extendis;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}