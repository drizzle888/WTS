package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：构造权限类
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
@Entity(name = "Actiontree")
@Table(name = "ALONE_AUTH_ACTIONTREE")
public class Actiontree implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "PARAMS", length = 128)
	private String params;
	@Column(name = "IMGID", length = 32)
	private String imgid;
	@Column(name = "ICON", length = 64)
	private String icon;
	@Column(name = "DOMAIN", length = 64)
	private String domain;
	@Column(name = "ACTIONID", length = 32)
	private String actionid;
	@Column(name = "STATE", length = 1, nullable = false)
	private String state;
	@Column(name = "UUSER", length = 32, nullable = false)
	private String uuser;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "UTIME", length = 16, nullable = false)
	private String utime;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "TYPE", length = 1, nullable = false)
	private String type;
	@Column(name = "COMMENTS", length = 128)
	private String comments;
	@Column(name = "TREECODE", length = 256)
	private String treecode;
	@Column(name = "NAME", length = 64, nullable = false)
	private String name;
	@Column(name = "PARENTID", length = 32, nullable = false)
	private String parentid;
	private int sort;
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getImgid() {
		return this.imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getActionid() {
		return this.actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUuser() {
		return this.uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTreecode() {
		return this.treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}