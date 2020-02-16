package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：用户类
 *详细：
 *
 *版本：v0.1
 *作者：王东
 *日期：20141119144919
 *说明：
 */
@Entity(name = "User")
@Table(name = "ALONE_AUTH_USER")
public class User implements java.io.Serializable, LoginUser {
	private static final long serialVersionUID = 1L;
	@Column(name = "LOGINTIME", length = 16)
	private String logintime;
	@Column(name = "LOGINNAME", length = 64, nullable = false)
	private String loginname;
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
	@Column(name = "TYPE", length = 1, nullable = false)
	private String type;
	@Column(name = "PASSWORD", length = 32, nullable = false)
	private String password;
	@Column(name = "COMMENTS", length = 128)
	private String comments;
	@Column(name = "NAME", length = 64, nullable = false)
	private String name;
	@Column(name = "IMGID", length = 32)
	private String imgid;
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Transient
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogintime() {
		return this.logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
}