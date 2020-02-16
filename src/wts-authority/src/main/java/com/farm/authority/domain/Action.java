package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：权限资源类
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141119144919
 *说明：
 */
@Entity(name = "Action")
@Table(name = "alone_auth_action")
public class Action implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "LOGINIS", length = 1, nullable = false)
	private String loginis;
	@Column(name = "CHECKIS", length = 1, nullable = false)
	private String checkis;
	@Column(name = "STATE", length = 1, nullable = false)
	private String state;
	@Column(name = "MUSER", length = 32, nullable = false)
	private String muser;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "UTIME", length = 16, nullable = false)
	private String utime;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "COMMENTS", length = 128)
	private String comments;
	@Column(name = "NAME", length = 64, nullable = false)
	private String name;
	@Column(name = "AUTHKEY", length = 128)
	private String authkey;
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;

	public String getLoginis() {
		return this.loginis;
	}

	public void setLoginis(String loginis) {
		this.loginis = loginis;
	}

	public String getCheckis() {
		return this.checkis;
	}

	public void setCheckis(String checkis) {
		this.checkis = checkis;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMuser() {
		return this.muser;
	}

	public void setMuser(String muser) {
		this.muser = muser;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getUtime() {
		return this.utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
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

	public String getAuthkey() {
		return this.authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}