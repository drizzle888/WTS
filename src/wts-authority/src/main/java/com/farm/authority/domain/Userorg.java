package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：置顶文档类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "UserOrg")
@Table(name = "alone_auth_userorg")
public class Userorg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
 	@GenericGenerator(name = "systemUUID", strategy = "uuid")
 	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, nullable = false)
	private String id;
	@Column(name = "ORGANIZATIONID", length = 32, nullable = false)
	private String organizationid;
	@Column(name = "USERID", length = 32, nullable = false)
	private String userid;

	public Userorg() {
	}
	public Userorg(String id, String organizationid, String userid) {
		super();
		this.id = id;
		this.organizationid = organizationid;
		this.userid = userid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}